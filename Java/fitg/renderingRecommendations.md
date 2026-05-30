# Map Rendering — Performance Recommendations

> Generated: 2026-05-29

---

## Problem Statement

The current `svg-viewer` module renders the game map too slowly for end-user use.

---

## Root Causes

1. **JFree SVG intercepts every `Graphics2D` call** and converts it to an SVG XML string segment. On a 4986×3216 canvas with thousands of arc/rotate/translate/drawImage calls this is pure string-building overhead — there is no GPU or pixel buffer involved.
2. **The map background is a large raster image** being drawn via `drawImage()`, which JFree SVG embeds as a base64-encoded blob inside the SVG. Loading and encoding it is expensive.
3. **The `displayGuidelines` flag is `true`** — dozens of extra `drawOval`, `drawString`, and `drawLine` calls happen per planet during what should be a production render.
4. **`EnvironRenderer` prints to `System.out`** for every environ — minor overhead but symptomatic of dev-phase code.
5. **`CounterRenderer` is a stub** — counter layout is a TODO, so the current render doesn't even finish counter placement.
6. **Single-threaded on startup** — everything blocks the `CommandLineRunner` thread.

---

## Recommended Alternative Approaches

Ranked from **lowest effort / highest immediate gain** to **most powerful / most effort**.

---

### Option 1 — BufferedImage → PNG (Quickest Win, Zero Architecture Change)

**Key insight:** `fitg.renderer` only depends on `Graphics2D`. You can swap `SVGGraphics2D` for `BufferedImage.createGraphics()` with a single line change. PNG rasterisation via Java2D is **10–50× faster** than JFree SVG because it writes pixels directly rather than building an XML document.

```
svg-viewer  →  swap SVGGraphics2D for BufferedImage
                ↓
            ImageIO.write(image, "PNG", file)
```

**Implementation sketch:**

```java
// In SvgViewerApplication — replace the two beans:
@Bean
public BufferedImage bufferedImage() {
    return new BufferedImage(Map.MAP_WIDTH, Map.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
}

@Bean
public Graphics2D graphics2d(BufferedImage image) {
    return image.createGraphics();
}

// In run():
BufferedImage img = applicationContext.getBean(BufferedImage.class);
ImageIO.write(img, "PNG", outputPath.toFile());
```

| | |
|---|---|
| **Pros** | 5 lines of change, no new dependencies, immediate speedup, no architectural changes |
| **Cons** | Produces a static file (same as now), not interactive, still slow if you re-render the whole map on every state change |

---

### Option 2 — Split Static Background + Dynamic SVG Overlay (Best Balance)

The background map image never changes. Only the **counters, loyalty markers, and PDB markers** change during a game. Separating these two layers gives a massive speedup:

- **Static layer:** serve the map background image as a plain `<img>` tag or `<image>` in SVG.
- **Dynamic layer:** generate a small SVG containing only the moving pieces (counters, markers). Overlay in the browser via `position:absolute` or SVG layering.

The dynamic SVG would be **tiny** — maybe 30–100 elements vs. the full background — and would render in milliseconds. The `ImageStore` already exposes `getImagePath()` methods returning `Path` objects, making it easy to reference counter images by URL rather than embedding them.

**Architecture:**

```
fitg-spring-boot-autoconfigure  ←  add a new Spring MVC controller
                                    GET /map/background  →  serves the static map image
                                    GET /map/overlay     →  generates dynamic SVG (counters only)
svg-viewer (or new web module)  ←  Spring Boot web app serving a simple HTML page
                                    <img src="/map/background" />
                                    <img src="/map/overlay"    />  (SVG, refreshed on demand)
```

| | |
|---|---|
| **Pros** | Correct separation of concerns, background served once and cached by browser, overlay updates fast, does not change `fitg.renderer` at all |
| **Cons** | Requires adding `spring-boot-starter-web`; the overlay renderer needs a new Graphics2D-to-SVG path for just the dynamic elements |

---

### Option 3 — Spring MVC Web App with REST API + Client-Side Rendering (Most Scalable)

Expose game state as JSON via a REST API and render the map entirely in the browser using a JavaScript library (e.g. **Pixi.js**, **Konva.js**, or plain HTML5 Canvas). The Java backend becomes a pure game-state server.

```
Browser                            Spring Boot Backend
  │                                     │
  ├─ GET /api/map/starsystems    ──────► StarSystemFinder.findAll()
  ├─ GET /api/map/planets        ──────► PlanetFinder.findAll() + loyalty/PDB state
  ├─ GET /api/counters/locations ──────► CounterFinder + CounterLocator
  └─ GET /images/{counterId}     ──────► ImageStore.getImagePath()
```

The query finder beans (`StarSystemFinder`, `PlanetFinder`, `CounterFinder`, `LocationFinder`) defined in `QueryAutoConfiguration` **already provide exactly the right API surface** — they just need REST controllers wrapping them.

The frontend downloads the background image once, then draws counter sprites on a Canvas layer on top. Counter movements trigger a targeted AJAX/WebSocket update, not a full re-render.

| | |
|---|---|
| **Pros** | Near-instant updates (only deltas are re-rendered), works in any browser, naturally multi-user, scales to the Galactic game's 50 planets, natural foundation for the full game UI |
| **Cons** | Requires frontend development (JavaScript/TypeScript); the `Planet` arc geometry would need to be ported from Java to JS — or served from the backend as coordinate data |

---

### Option 4 — JavaFX Application (Best for a Desktop App)

JavaFX provides a `Canvas` node backed by **hardware-accelerated rendering** (GPU). The existing `Graphics2D` code in `fitg.renderer` cannot be used directly, but JavaFX's `GraphicsContext` has an almost identical API (`drawImage`, `strokeOval`, `translate`, `rotate`, etc.), so the renderer logic can be ported with minimal changes.

A thin `JavaFXGraphics2DAdapter` wrapping `Graphics2D` over a JavaFX `GraphicsContext` would let you reuse `fitg.renderer` entirely without modification. The open-source library **FXGraphics2D** (by the JFree author — the same author as JFree SVG) provides exactly this adapter.

```
fitg.renderer   ←  unchanged
JavaFX Canvas   ←  backed by FXGraphics2D (Graphics2D → JavaFX GraphicsContext bridge)
JavaFX Stage    ←  interactive map view with zoom/pan (ScrollPane or manual transforms)
```

| | |
|---|---|
| **Pros** | Full desktop app, fast GPU-accelerated rendering, interactive (zoom/pan), reuses `fitg.renderer` unchanged |
| **Cons** | JavaFX runtime dependency, not browser-based, can have JDK distribution complexity |

---

### Option 5 — Pre-Generated Static Tiles + Slippy-Map Viewer (Minimal Runtime Complexity)

Pre-render the background map + initial state as a set of image tiles (e.g. 256×256 px) and serve them with a slippy-map viewer (e.g. **Leaflet.js** or **OpenLayers**) in the browser. The dynamic overlay (counters only) is a separate thin SVG layer on top.

This sidesteps all rendering performance issues during gameplay because the tiles are pre-baked. Only counter positions need live updates.

| | |
|---|---|
| **Pros** | Extremely fast loading (tiles loaded lazily), zero server-side rendering at runtime, very smooth zoom/pan in browser |
| **Cons** | Pre-rendering tile pipeline needed; tiles must be regenerated when the map graphic changes |

---

## Summary Comparison

| Option | Effort | Speed Gain | Interactive | Best For |
|---|---|---|---|---|
| 1. BufferedImage → PNG | Minimal (5 lines) | 10–50× | No | Quick fix for file-output / testing |
| 2. Static image + dynamic SVG overlay | Low–Medium | ~100× | Partial (refresh) | Best balance of effort vs. result |
| 3. REST API + JS canvas | Medium–High | Near-instant updates | Full | Long-term game UI foundation |
| 4. JavaFX desktop app | Medium | Very fast (GPU) | Full | Standalone desktop application |
| 5. Pre-rendered tiles + Leaflet | Medium | Near-instant load | Zoom/pan | Map-browsing with polish |

---

## Recommended Path

Given the project's current state and that `fitg.renderer` already uses `Graphics2D` as its abstraction boundary:

1. **Immediate fix (Option 1):** Swap `SVGGraphics2D` for `BufferedImage` — fixes the current slow output in minutes, useful for testing and screenshots. Also set `displayGuidelines = false`.

2. **Next step (Option 2):** Add `spring-boot-starter-web` and a REST endpoint that returns a small SVG for just the dynamic elements (counters, loyalty markers, PDB markers). This is the correct architectural split and makes the existing `ImageStore.getImagePath()` methods finally useful.

3. **Endgame (Option 3):** Once the game has more playable mechanics, a browser-based UI driven by the existing query finder beans (`StarSystemFinder`, `PlanetFinder`, `CounterFinder`, `LocationFinder`) as a JSON API is the natural evolution toward a fully interactive game client.
