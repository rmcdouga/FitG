# Freedom In The Galaxy (FitG) — Project Research Report

> Generated: 2026-05-29

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Repository Structure](#2-repository-structure)
3. [Technology Stack](#3-technology-stack)
4. [Module Deep-Dives](#4-module-deep-dives)
   - 4.1 [fitg.basegame — Core Game Engine](#41-fitgbasegame--core-game-engine)
   - 4.2 [fitg.renderer — Graphics Rendering Library](#42-fitgrenderer--graphics-rendering-library)
   - 4.3 [svg-viewer — SVG Map Application](#43-svg-viewer--svg-map-application)
   - 4.4 [fitg.cli — Command-Line Interface](#44-fitgcli--command-line-interface)
   - 4.5 [fitg-spring-boot-autoconfigure — Auto-Configuration](#45-fitg-spring-boot-autoconfigure--auto-configuration)
   - 4.6 [fitg-spring-boot-starter — Spring Boot Starter](#46-fitg-spring-boot-starter--spring-boot-starter)
5. [Domain Model In Depth](#5-domain-model-in-depth)
   - 5.1 [The Galaxy Map](#51-the-galaxy-map)
   - 5.2 [Characters](#52-characters)
   - 5.3 [Military Units](#53-military-units)
   - 5.4 [Spaceships](#54-spaceships)
   - 5.5 [Missions](#55-missions)
   - 5.6 [Action Cards](#56-action-cards)
   - 5.7 [Races](#57-races)
   - 5.8 [Scenarios](#58-scenarios)
6. [Key Architectural Patterns](#6-key-architectural-patterns)
7. [Game Tables & Mechanics](#7-game-tables--mechanics)
8. [State Management](#8-state-management)
9. [Testing Approach](#9-testing-approach)
10. [Known TODOs & Gaps](#10-known-todos--gaps)

---

## 1. Project Overview

**Freedom In The Galaxy (FitG)** is a Java reimplementation of the classic SPI board game of the same name (originally published 1979). The board game is a two-player asymmetric war game: one player controls the **Rebel** alliance trying to overthrow a galactic empire, the other controls the **Imperial** forces trying to suppress them.

The project is authored by Rob McDougall (`com.rogers.rmcdouga`) and is currently at version `0.0.1-SNAPSHOT`. It is an ambitious personal project that faithfully encodes the original game's rules, map, characters, creatures, races, tables, and card decks in Java.

---

## 2. Repository Structure

```
fitg/                                   ← Root multi-module Maven project
├── pom.xml                             ← Parent POM (Java 25, dependency management)
├── fitg.basegame/                      ← Core game engine (all game logic & data)
├── fitg.renderer/                      ← Java2D rendering library
├── svg-viewer/                         ← Spring Boot app – renders the map as SVG
├── fitg.cli/                           ← Spring Shell CLI app (early stage)
├── fitg-spring-boot-autoconfigure/     ← Spring Boot auto-configuration
└── fitg-spring-boot-starter/          ← Spring Boot starter (aggregates autoconfigure)
```

---

## 3. Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 25 (root & svg-viewer), Java 21 (fitg.cli) |
| Build | Apache Maven (multi-module) |
| Core framework | Spring Boot 4.0.6 |
| CLI | Spring Shell 4.0.2 |
| SVG rendering | JFree SVG 5.0.7 |
| Graphics | Java2D (`java.awt.Graphics2D`) |
| Collections | Apache Commons Collections 4 (`commons-collections4`) |
| Utilities | Apache Commons Lang 3, Commons IO |
| Markdown | CommonMark (`commonmark` 0.28) |
| Testing | JUnit Jupiter 6.1, Hamcrest 3, Spring Boot Test, jcabi-xml, image-comparison |
| Code generation (test) | JavaPoet 1.13 |

---

## 4. Module Deep-Dives

### 4.1 `fitg.basegame` — Core Game Engine

The largest and most important module. Contains all game rules, game data, and game mechanics entirely in Java. It has **no Spring dependency**; it is a plain Java library.

#### Package layout

```
com.rogers.rmcdouga.fitg.basegame
├── (root)          ← Top-level game concepts: Game, GameState, Scenario, ActionDeck, Mission, etc.
├── box/            ← Game box / counter pool management
├── command/
│   ├── api/        ← Mover interface
│   └── adapters/   ← Command adapter
├── map/            ← Galaxy map data: StarSystem, Planet, Environ, SpaceRoute, Loyalty, PDB
├── query/
│   ├── api/        ← Finder interfaces (CounterFinder, PlanetFinder, StarSystemFinder, …)
│   └── adapters/   ← BaseGame implementations of finders
├── rules/          ← Rules interface + BaseGameRules
├── strategies/
│   ├── AbstractStrategy.java
│   └── hardcoded/  ← Concrete scenario setup strategies
├── tables/         ← All game tables (combat, detection, hyperjump, etc.)
├── units/          ← All counter types: Character, Spaceship, Military Unit, etc.
└── utils/          ← MarkdownString wrapper
```

#### Central class: `Game`

`Game` is the top-level game object. It implements three interfaces simultaneously:

- **`GameState`** – serialise/deserialise game state to/from `Map<String, Object>`
- **`GameBoard`** – access and mutate the board state (loyalty, PDB levels, star systems)
- **`CounterLocator`** – track where every counter is on the map

Construction requires a `Scenario` and two `PlayerDecisions` objects (one for Rebels, one for Imperials). On construction, it:
1. Creates the game board from the scenario's map.
2. Sets up all PDB (Planetary Defence Battery) levels via `setupPdb()`.
3. Places all counters on the map via `placeCounter()`.

#### `ActionDeck`

The action deck is a shuffled deck of 30 cards (cards 68–97). It supports:
- `draw()` – draw from the top, move to discard pile.
- `peekDiscard()` – look at the top (or a specific position) in the discard pile.
- `reset()` – reshuffle all cards back into the draw pile.
- Full `getState()`/`setState()` serialisation by card number.

#### `CounterLocations`

A bidirectional data structure that tracks every counter's location. It uses:
- `HashSetValuedHashMap<Location, Counter>` (from Apache Commons Collections 4) for the location-to-counter direction.
- `HashMap<Counter, Location>` for the counter-to-location direction.

It supports stacks of counters (via `StackManager`), "shallow" lookup (`countersAt()`), and "deep" lookup (`locationOf()` which follows into a stack to find a contained counter).

---

### 4.2 `fitg.renderer` — Graphics Rendering Library

A pure Java2D rendering library that draws the game map onto a `Graphics2D` surface. It depends on `fitg.basegame` but not on Spring.

Key classes:

| Class | Purpose |
|---|---|
| `Map` | Top-level renderer — draws background image, then iterates star systems |
| `StarSystem` (enum) | Pixel coordinates for each of the 25 star systems on the map image |
| `Planet` (enum) | Pixel offsets + arc sizes for each planet's ring sectors (orbit, loyalty, environs) |
| `EnvironRenderer` | Renders counters at each environ |
| `LoyaltyRenderer` | Renders loyalty markers on the planet ring |
| `LoyaltyTrackMarkerRenderer` | Renders the loyalty track marker |
| `PdbRenderer` | Renders PDB markers |
| `CounterRenderer` | Renders counter images |
| `ImageStore` (interface) | Abstracts image loading |
| `BaseGameImageStore` | Default image store using classpath resources |
| `BaseGameImageStoreAdapter` | Adapter between a concrete store and the `ImageStore` interface |

The map is 4986 × 3216 pixels. Each planet is rendered as a circular ring with arcs for:
- An **orbit box** (spaceships in orbit)
- Five **loyalty spaces** (Patriotic → Loyal → Neutral → Dissent → Unrest/Rebellion)
- One arc per **environ** (the remaining degrees of the circle)

A `displayGuidelines` flag (currently `true`) draws debugging circles, labels, and sector lines.

---

### 4.3 `svg-viewer` — SVG Map Application

A Spring Boot application that wires everything together to produce an SVG file of the game map. It uses **JFree SVG**, which provides a `SVGGraphics2D` that implements `Graphics2D` but outputs SVG XML.

Flow:
1. `SvgViewerApplication` is a `CommandLineRunner`.
2. On startup it calls `drawMap()` which retrieves a `Map` renderer bean from the context.
3. `Map.draw()` calls the Java2D rendering pipeline via the `SVGGraphics2D` backend.
4. The resulting SVG string is written to `src/test/resources/actualResults/FitgMap.svg`.

Spring beans provided locally:
- `SVGGraphics2D graphics2d()` — the JFree SVG canvas.
- `ImageStore classPathImageStore()` — loads images from the classpath.

The `fitg-spring-boot-starter` dependency (see §4.5/4.6) automatically provides the `Game`, `Map`, and query finder beans.

---

### 4.4 `fitg.cli` — Command-Line Interface

Very early-stage Spring Shell application. Currently contains only a single command:

```java
@Command(name = "create-game", help = "Creates a new game.")
public String createGame() { return "Done."; }
```

The application entry point is a standard `@SpringBootApplication`. The intent is clearly to build a full interactive CLI for playing the game, but almost no logic has been implemented yet.

---

### 4.5 `fitg-spring-boot-autoconfigure` — Auto-Configuration

Provides three `@AutoConfiguration` classes that are registered via
`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`:

| Configuration class | Beans registered |
|---|---|
| `GameAutoConfiguration` | `BaseGameScenario` (default: `FlightToEgrix`), `rebelDecisions`, `imperialDecisions`, `Game` |
| `RendererAutoConfiguration` | `Map` (the renderer bean, conditional on a `Graphics2D` bean being present) |
| `QueryAutoConfiguration` | `StarSystemFinder`, `PlanetFinder`, `CounterFinder`, `LocationFinder` |

All beans use `@ConditionalOnMissingBean` so that consuming applications can override any of them. This is idiomatic Spring Boot autoconfigure design.

---

### 4.6 `fitg-spring-boot-starter` — Spring Boot Starter

An empty starter module (no code, no resources beyond the standard Maven project structure). Its sole purpose is to be the user-facing dependency that brings in `fitg-spring-boot-autoconfigure` transitively.

---

## 5. Domain Model In Depth

### 5.1 The Galaxy Map

The galaxy is divided into **5 provinces**, each containing 4–6 star systems. In total there are **25 star systems** and approximately **50 planets**.

```
Province 1: Tardyn, Uracus, Zamorax, Atriard, Bex, Osirius
Province 2: Phisaria, Egrix, Ancore, Gellas
Province 3: Pycius, Ribex, Rorth, Aziza, Luine
Province 4: Erwind, Wex, Varu, Deblon, Martigna
Province 5: Zakir, Eudox, Corusa, Irajeba, Moda
```

IDs are computed hierarchically: Province ID × 10 + offset = Star System ID; Star System ID × 10 + offset = Planet ID. This mirrors the original board game's numbering convention.

**Space Routes** (43 total) connect star systems and each has a `navigationStars` value (0 or 1) indicating the navigation difficulty of the route.

**Planets** each belong to a star system and have:
- A `CapitalType`: `None`, `Provincial`, or `Throne`
- Starting loyalty values (`startingLoyaltyS` and `startingLoyaltyA`)
- Planetary control (`ImperialControlled`, `RebelControlled`, `Neutral`)
- An optional home race
- A `hasSecret` flag
- 1–3 **Environs**

**Environs** are the sub-regions of a planet where characters operate. Each environ has:
- `EnvironType`: `Urban`, `Wild`, `Air`, `Fire`, `Liquid`, `Subterranian`
- `size` — affects how many action cards are drawn
- `resources` (optional) and `starResources` flag
- `coupRating` (optional) — needed for Coup missions
- One or more `RaceType`s inhabiting it
- An optional `creature` (hostile animal)
- An optional `sovereign` (local ruler)

**Loyalty** is tracked per-planet on a five-step scale: `Patriotic → Loyal → Neutral → Dissent → Unrest`, plus a `Rebellion` state. Managed by `BaseGameLoyaltyManager`.

**PDB (Planetary Defence Battery)** has three levels (0, 1, 2) and an Up/Down status. Managed by `BaseGamePdbManager`.

**Drift** represents a location within a star system used for hyperjump drift events.

---

### 5.2 Characters

Characters are the primary agents of the game. There are **32 characters** total: **20 Rebel** and **12 Imperial**. All are encoded as the `BaseGameCharacter` enum, each with:

| Attribute | Type | Notes |
|---|---|---|
| `cardNumber` | `int` | 1–32 |
| `allegiance` | `Faction` | `REBEL` or `IMPERIAL` |
| `race` | `RaceType` | One of the 8 star-faring races |
| `title` | `Optional<String>` | e.g. "Princess of Adare" |
| `combat` | `int` | 0–6 |
| `endurance` | `int` | 0–6 |
| `intelligence` | `int` | 0–6 |
| `leadership` | `int` | 0–6 |
| `spaceLeadership` | `OptionalInt` | Present only for some characters |
| `diplomacy` | `int` | 0–6 |
| `navigation` | `int` | 0–6 |
| `homePlanet` | `Set<Planet>` | Some characters have multiple |
| `bonusDraws` | `Map<Mission, Integer>` | Extra action card draws for specific missions |
| `description` | `String` | Lore text (multi-line text blocks) |
| `specialAbilities` | `Set<SpecialAbility>` | e.g. `HEAL_CHARACTERS`, `OWNS_PLANETARY_PRIVATEER` |

Notable characters include:
- **Rayner Derban** — top Rebel leader (Combat 5, Intelligence 4, Leadership 4, Navigation 4)
- **Redjac** — top Imperial antagonist (Combat 6, Endurance 6, Navigation 5, Knight of the Empire)
- **Barca** — Imperial Grand Marshal (Combat 5, Leadership 4, Space Leadership 3)
- **Doctor Sontag** — Rebel healer (special ability: `HEAL_CHARACTERS`)
- **Kogus** — Rebel bodyguard (special ability: `INCREASE_ABILITIES_WITH_ZINA_ADORA`)

**Special Abilities** modelled (12 total): owning specific spaceships, healing, hiding bonuses, repairing ships/possessions, ignoring sentry robots or irate locals, revealing planet secrets, etc.

---

### 5.3 Military Units

Two factions of military units:

**Imperial Military Units** (`ImperialMilitaryUnit` enum):

| Unit | Environ Str | Space Str | Mobile | Count |
|---|---|---|---|---|
| Militia | 1 | 0 | No | 35 |
| Patrol | 1 | 2 | Yes | 24 |
| Line | 3 | 2 | Yes | 17 |
| Veteran | 3 | 4 | Yes | 13 |
| Elite Army | 1 | 2 | Yes | 8 |
| Elite Navy | 1 | 2 | Yes | 8 |
| Suicide Squad | 1 | 2 | Yes | 2 |

**Rebel Military Units** (`RebelMilitaryUnit`) follow a similar pattern.

---

### 5.4 Spaceships

**Imperial Spaceships** (`BaseGameImperialSpaceship` enum):

| Ship | Maneuver | Cannons | Shields | Max Passengers |
|---|---|---|---|---|
| Redjac's Spaceship | 4 | 2 | 3 | 4 |
| Imperial Spaceship | 2 | 1 | 1 | 4 |

Three Imperial Spaceships total (two generic `Imperial_Spaceship` + one `Redjacs_Spaceship`).

**Rebel Spaceships** (`BaseGameRebelSpaceship`) include the *Planetary Privateer* (owned by Boccanegra) and others.

---

### 5.5 Missions

There are **15 mission types** encoded in `BaseGameMission` (card numbers 53–67). Each mission has:
- A card number and a single-character **mnemonic** (used on action cards to identify which missions trigger an event)
- A name, a markdown-formatted **description** (when the mission can be performed)
- A markdown-formatted **result** (what happens when the mission letter appears in drawn action cards)

| # | Mnemonic | Mission |
|---|---|---|
| 53 | S | Sabotage |
| 54 | D | Diplomacy |
| 55 | R | Start/Stop Rebellion |
| 56 | P | Scavenge for Possessions |
| 57 | G | Gain Characters |
| 58 | I | Gather Information |
| 59 | F | Free Prisoners |
| 60 | A | Assassination |
| 61 | B | Start Rebel Camp |
| 62 | T | Subvert Troops |
| 63 | C | Coup |
| 64 | E | Summon Sovereign |
| 65 | J | Spaceship Quest |
| 66 | H | Steal Enemy Resources |
| 67 | Q | Question Prisoner |

The `MissionFactory` / `MissionEnumFactory` converts mnemonic characters (e.g. `'S'`) back to `Mission` objects, which is used when parsing action card event triggers.

---

### 5.6 Action Cards

**30 action cards** numbered 68–97, all encoded in `BaseGameAction`. Each card has **three event entries**, one per `EnvironType` category:

- **Urban** (Urban environs)
- **Special** (Air, Fire, Liquid, Subterranian)
- **Wild** (Wild environs)

Each entry consists of:
1. A string of **mission mnemonics** — which missions trigger this event.
2. A **result description** (markdown string) — the narrative event that fires.

For example, Card 68:
- Urban (`H R`): "Creature attacks one Mission Group."
- Special (`B`): "Locals raid Enemy forces."
- Wild (`P`): "Locals shelter character from Enemy."

The `ActionDeck` shuffles all 30 cards and manages draw/discard operations.

---

### 5.7 Races

**8 Star-Faring Races** (can travel between systems):

| Race | Home Planet | Notable Squad Strengths |
|---|---|---|
| Kayn | Mimulus | Wild 7/6, Subterranian 6/4 |
| Piorad | Ayod | Urban 4/4, Subterranian 6/6 |
| Rhone | (none — originated elsewhere) | Urban 5/4, Wild 4/4 |
| Saurian | Unarpha | Urban 5/6, Wild 5/4, Liquid 5/2 |
| Segunden | Bajukai | Urban 6/4, Liquid 5/2 |
| Suvan | Mrane | Urban 6/2, Liquid 7/4 |
| Xanthon | Xan | Fire 8/6 only |
| Yester | Cieson | Urban 6/2, Air 6/4 |

**22 Non-Star-Faring Races** (local to one planet/environ) are also encoded, e.g. Borks, Calma, Henone, Urgaks, etc. Non-star-faring races each have a single `EnvironType` and a squad rating.

Each race has a `Squad` value (strength/evasion combination, e.g. `SQUAD_7_6`) used when irate locals attack a mission group.

---

### 5.8 Scenarios

`BaseGameScenario` is an enum implementing `Scenario`:

#### `FlightToEgrix` (starter scenario)
- **Type**: `StartRebellion`
- **Rules**: `StarSystem` (single star system)
- **Turns**: 6
- **Map**: Only the Egrix star system (Quibron, Angoff, Charkhan)
- **PDB setup**: Quibron Level 1 Up, Angoff Level 2 Up, Charkhan Level 0 Up
- **Rebel counters**: Planetary Privateer with Boccanegra, Doctor Sontag, Frun Sentel — all placed in space
- **Imperial counters**: Player decides placement of Jon Kidu, Vans Ka-Tie-A, Imperial Spaceship, 1× Line, 3× Patrol, 3× Militia

#### `GalacticGame` (full game)
- **Type**: `StartRebellion`
- **Rules**: `Galactic`
- **Map**: All 25 star systems
- **Setup**: Delegates to player decisions for both sides
- **NOTE**: Counter setup currently delegates to `FlightToEgrix` (marked TODO for proper Galactic setup)

The `Rules` enum has three variants: `StarSystem`, `Province`, `Galactic` — representing increasing scope of play.

---

## 6. Key Architectural Patterns

### Enum-as-Data-Object
All game data is encoded as Java enums: characters, planets, star systems, missions, action cards, races, spaceships, military units, space routes. This is a distinctive design choice that makes all game data:
- Statically typed and IDE-navigable
- Impossible to add duplicates
- Serialisable via ordinal/name
- Directly usable as enum set/map keys

### Builder Pattern
`BaseGameCharacter.Builder` and `BaseGameEnviron.Builder` use a fluent builder API to construct complex value objects within the enum constant definitions, keeping the declarations readable.

### Strategy Pattern
`Scenario.PlayerDecisions` is a strategy interface. Two concrete implementations exist in `strategies/hardcoded/`:
- `FlightToEgrixRebelStrategy` — Rebels have no decisions in this scenario (throws UnsupportedOperationException)
- `FlightToEgrixImperialStrategy` — Implements the Imperial player's placement logic

The `AbstractStrategy` base class holds shared logic.

### Interface Segregation on `Game`
`Game` implements three distinct interfaces (`GameState`, `GameBoard`, `CounterLocator`), delegating internally to:
- `ActionDeck` (game state)
- `BaseGameGameBoard` (board state)
- `CounterLocations` (counter tracking)

### Spring Boot Auto-Configuration
The Spring Boot starter pattern is used to wire the game engine into any Spring Boot application. The three auto-configurations use `@ConditionalOnMissingBean` extensively, allowing consuming applications to override any bean.

### Markdown Strings
`MarkdownString` wraps mission and action card description text to distinguish it from plain strings and support future Markdown rendering.

---

## 7. Game Tables & Mechanics

All game tables are implemented as enums with static lookup methods:

| Table Class | Purpose |
|---|---|
| `CharacterCombatResultsTable` | 10-column table for character-vs-character combat. Returns attacker & defender losses (wounds or captures) based on roll and combat differential. |
| `DetectionTable` | 8-column table. Given an evasion value + d6 roll, returns one of: Undetected, Detected, Detected+fleet may attack, Detected+Damaged, Eliminated. |
| `HyperjumpTable` | Governs hyperjump results. Based on roll + (hyperjump distance − navigation rating): results range from no drift to Drift 2 + Eliminated. |
| `IrateLocalsChart` | Determines the strength of irate local attacks. |
| `MilitaryCombatResultsTable` | Military-unit combat resolution. |
| `SearchTable` | Governs success of Imperial searches for hidden Rebel characters. |
| `Squad` | Squad ratings (strength + evasion) used for irate locals and minor combat. |
| `SquadChart` | Chart for resolving squad combat. |
| `Dice` | `D6` enum — wraps `Math.random()` into a 1–6 roll. |

The convention for tables is to separate the roll from the result lookup so both can be logged independently.

---

## 8. State Management

`GameState` is a simple interface:

```java
Map<String, Object> getState();
void setState(Map<String, Object> state);
```

Implemented by `Game`, `ActionDeck`, and `CounterLocations`. The idea is that the entire game state can be serialised to a nested `Map<String, Object>` (JSON-compatible) and restored. 

**Current implementation status:**
- `ActionDeck.getState()` / `setState()` — **fully implemented** (serialises draw pile and discard pile as lists of card numbers).
- `Game.getState()` / `setState()` — **partially implemented** (only serialises the action deck).
- `CounterLocations.getState()` / `setState()` — **not implemented** (throws `UnsupportedOperationException`).

---

## 9. Testing Approach

- **JUnit Jupiter 6.1** with **Hamcrest 3** matchers used throughout.
- Tests are co-located per module in `src/test/java`.
- `svg-viewer` includes image-comparison testing: the rendered SVG is saved as `actualResults/FitgMap.svg` and can be compared against expected results.
- **jcabi-xml** is used to assert SVG structure via XPath.
- **JavaPoet** appears in test scope of `fitg.renderer`, suggesting some code generation tests (e.g. generating the `Planet` enum constants programmatically from data).
- Spring Boot Test and `awaitility` are used in `fitg.cli` for integration tests of Shell commands.

---

## 10. Known TODOs & Gaps

The project is clearly a work in progress. The following significant gaps and TODOs were identified:

| Area | Issue |
|---|---|
| `GalacticGame` scenario | `setupCounters()` delegates to `FlightToEgrix` — needs proper Galactic setup logic |
| Other scenarios | Comment says "TODO: Insert other scenarios here" between `FlightToEgrix` and `GalacticGame` |
| `CounterLocations.getState()`/`setState()` | `UnsupportedOperationException` — state persistence for counter positions is not implemented |
| `Game.getState()` | Only persists the action deck, not counter locations or board state |
| `CharacterCombatResultsTable` | `AttackerStrategy` and `DefenderStrategy` interfaces have TODO comments to be filled in |
| `Planet.environ(int)` | Method throws `UnsupportedOperationException("Not implemented yet.")` |
| `fitg.cli` | `createGame()` command just returns `"Done."` — no real game logic connected |
| `svg-viewer` mainline | `run()` has a `TODO Fill in this method` comment; commented-out code suggests game setup is pending |
| `displayGuidelines` flag | Hard-coded to `true` in both `StarSystem` and `Planet` renderers — intended to be removed |
| Possessions deck | Referenced in `BaseGameMission.SCAVENGE` and `BaseGamePossessionPool` but possession cards are not implemented |
| Sovereign logic | `BaseGameSovereign` entities (e.g. Nam_Nhuk, Megda_Sheels) referenced but sovereign summoning logic is incomplete |
| Creature attacks | Creatures are defined on environs but the `IrateLocalsChart`/creature attack resolution may be incomplete |
| Imperial atrocities | Card events reference "Section 35.0 Atrocity" — this section is not yet implemented |
| Planet secrets | Referenced in missions and `BaseGamePlanet.hasSecret` but reveal logic is incomplete |
| Player / TurnOrder | `Player`, `PlayerState`, `BaseGameTurnOrder` classes exist but turn sequencing logic is minimal |
