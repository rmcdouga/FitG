---
agent: speckit.constitution
---

# FitG Project Constitution

## Project Identity

**Freedom In The Galaxy (FitG)** is a Java reimplementation of the classic SPI board game (1979).
It is a two-player asymmetric war game: **Rebels** vs. the **Empire**, set across a 25-star-system galaxy.
The project is authored by Rob McDougall (`com.rogers.rmcdouga` / `io.github.rmcdouga`).

---

## Module Structure

The project is a **Maven multi-module** build. Modules and their single responsibilities are:

| Module | Responsibility |
|---|---|
| `fitg.basegame` | All game rules, data, and mechanics. **No Spring, no UI.** Plain Java library. |
| `fitg.renderer` | Java2D rendering of the game map onto a `Graphics2D` surface. Depends on `fitg.basegame` only. |
| `fitg-spring-boot-autoconfigure` | Spring Boot `@AutoConfiguration` classes that wire game beans. |
| `fitg-spring-boot-starter` | User-facing Maven dependency that pulls in the autoconfigure module. |
| `fx-viewer` | JavaFX desktop application. Uses FXGraphics2D to bridge JavaFX Canvas to the `Graphics2D` renderer. |
| `svg-viewer` | Spring Boot app that renders the map to an SVG file using JFree SVG. |
| `fitg.cli` | Spring Shell CLI for interacting with the game. |

---

## Technology Decisions

| Concern | Choice | Rationale |
|---|---|---|
| Language | Java 25 | Use modern Java features (records, sealed classes, text blocks, pattern matching) |
| Build | Apache Maven (multi-module) | Standard Java build tool |
| Framework | Spring Boot 4.x | Dependency injection, autoconfiguration, CLI support |
| CLI | Spring Shell 4.x | Interactive command-line game interface |
| Desktop UI | JavaFX + FXGraphics2D | Hardware-accelerated canvas; FXGraphics2D bridges to existing `Graphics2D` renderer |
| SVG output | JFree SVG | Reuses the `Graphics2D` renderer for file-based SVG output |
| Collections | Apache Commons Collections 4 | `HashSetValuedHashMap` for bidirectional counter-location tracking |
| Utilities | Apache Commons Lang 3, Commons IO | General-purpose utilities |
| Markdown | CommonMark | Wraps mission/action-card description text as typed `MarkdownString` values |
| Testing | JUnit Jupiter 6.x, Hamcrest 3 | Unit and integration testing |

---

## Core Architectural Decisions

### 1. Enums as Rich Data Objects

All game data is encoded as **Java enums**: characters, planets, star systems, missions, action cards, races, spaceships, military units, and space routes.

**Rationale:** Statically typed, IDE-navigable, impossible to create duplicates, directly usable as `EnumSet`/`EnumMap` keys, and serialisable by ordinal or name.

**Do not** replace game-data enums with database entities, external config files, or reflection-based lookups.

### 2. `Graphics2D` as the Rendering Abstraction Boundary

`fitg.renderer` depends only on `java.awt.Graphics2D`. It must **never** import JavaFX, JFree SVG, or any other concrete rendering backend.

Concrete backends (`FXGraphics2D`, `SVGGraphics2D`, `BufferedImage.createGraphics()`) are provided by the application modules (`fx-viewer`, `svg-viewer`) via Spring beans.

### 3. `fitg.basegame` Has No Spring Dependency

The core game engine is a plain Java library. It must **never** import Spring classes.

Spring wiring lives exclusively in `fitg-spring-boot-autoconfigure`.

### 4. Spring Boot Auto-Configuration Pattern

All Spring beans are defined with `@ConditionalOnMissingBean` so consuming applications can override any bean. New beans must follow this pattern.

The three autoconfigure classes are:
- `GameAutoConfiguration` — `Game`, `Scenario`, `PlayerDecisions`
- `RendererAutoConfiguration` — `Map` renderer (conditional on a `Graphics2D` bean)
- `QueryAutoConfiguration` — `StarSystemFinder`, `PlanetFinder`, `CounterFinder`, `LocationFinder`

### 5. Static Map, Dynamic Counters

The background map image is **static** and must be treated as a resource, not re-rendered. Only counters, loyalty markers, and PDB markers change during gameplay. Rendering implementations should separate static and dynamic layers wherever possible.

### 6. Builder Pattern Inside Enums

Complex enum constants (e.g. `BaseGameCharacter`, `BaseGameEnviron`) use an inner `Builder` class with a fluent API. The `build()` method performs `Objects.requireNonNull` validation on every required field.

---

## Package Conventions

| Package suffix | Contents |
|---|---|
| `(root)` | Top-level interfaces and key domain objects |
| `.box` | Counter pool and game-box management |
| `.command.api` | Command interfaces (e.g. `Mover`) |
| `.command.adapters` | Implementations of command interfaces |
| `.map` | Galaxy map data (StarSystem, Planet, Environ, SpaceRoute, Loyalty, PDB) |
| `.query.api` | Finder interfaces (read-only queries) |
| `.query.adapters` | BaseGame implementations of finder interfaces |
| `.rules` | Game rules interface and implementation |
| `.strategies` | `PlayerDecisions` strategy implementations |
| `.strategies.hardcoded` | Hard-coded scenario setup strategies |
| `.tables` | All game tables (combat, detection, hyperjump, etc.) |
| `.units` | All counter types (Character, Spaceship, MilitaryUnit, etc.) |
| `.utils` | Utility types (e.g. `MarkdownString`) |

The `fitg.basegame` group ID/package root is `com.rogers.rmcdouga`.
The `fx-viewer` group ID/package root is `io.github.rmcdouga`.

---

## Scenarios

Scenarios are implemented as constants of the `BaseGameScenario` enum implementing the `Scenario` interface.

Currently defined:
- **`FlightToEgrix`** — Starter scenario. Single star system (Egrix). 6 turns. Rules: `StarSystem`.
- **`GalacticGame`** — Full galaxy game. All 25 star systems. Rules: `Galactic`. *(Counter setup is a TODO.)*

New scenarios must implement `createMap()`, `setupPdbs()`, and `setupCounters()` in full.

---

## State Serialisation Contract

Game state is serialised via:
```java
Map<String, Object> getState();
void setState(Map<String, Object> state);
```
This `Map<String, Object>` structure must remain JSON-compatible (no Java-specific types as values).

Currently implemented: `ActionDeck`.  
Not yet implemented: `CounterLocations`, full `Game` state.

---

## What Is Out of Scope

- **No database persistence** — game state is serialised to/from `Map<String, Object>` only.
- **No web backend** — `fitg.basegame` and `fitg.renderer` must remain framework-agnostic.
- **No reflection-based game data** — all game data is statically typed in enums.
- **No external game-rule DSL** — rules are implemented directly in Java.