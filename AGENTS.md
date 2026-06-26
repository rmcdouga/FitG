# FitG Developer Instructions

## Build & Test

```
mvn -B verify --file Java/fitg/pom.xml -Dglass.platform=Headless
```

This is the **only** command needed for CI-style verification. Maven root is `Java/fitg/pom.xml`, not the repo root. Uses Java 25 (Oracle JDK).

## Architecture Overview

Maven multi-module project (all under `Java/fitg/`):

- **fitg.basegame** — Core game logic, domain model, and rules
- **fitg.cli** — Command-line interface
- **fitg.renderer** — Rendering/visualization
- **fitg-spring-boot-autoconfigure** — Spring Boot auto-configuration
- **fitg-spring-boot-starter** — Spring Boot starter bundle
- **fitg-ai-client**, **fitg-ai-model-eval** — AI integration and evaluation
- **svg-viewer**, **fx-viewer** — SVG/FX asset viewing utilities

## Testing

- JUnit 5 (Jupiter) + Mockito. Tests in `src/test/java` with `*Test` suffix.
- Use AssertJ for test assertions

## Existing Instructions

See `.github/copilot-instructions.md` for coding guidelines (Java style, Javadoc, code quality). AGENTS.md covers execution/building specifics that Copilot instructions omit.

## graphify

This project has a knowledge graph at graphify-out/ with god nodes, community structure, and cross-file relationships.

When the user types `/graphify`, invoke the `skill` tool with `skill: "graphify"` before doing anything else.

Rules:
- For codebase questions, first run `graphify query "<question>"` when graphify-out/graph.json exists. Use `graphify path "<A>" "<B>"` for relationships and `graphify explain "<concept>"` for focused concepts. These return a scoped subgraph, usually much smaller than GRAPH_REPORT.md or raw grep output.
- Dirty graphify-out/ files are expected after hooks or incremental updates; dirty graph files are not a reason to skip graphify. Only skip graphify if the task is about stale or incorrect graph output, or the user explicitly says not to use it.
- If graphify-out/wiki/index.md exists, use it for broad navigation instead of raw source browsing.
- Read graphify-out/GRAPH_REPORT.md only for broad architecture review or when query/path/explain do not surface enough context.
- After modifying code, run `graphify update .` to keep the graph current (AST-only, no API cost).
