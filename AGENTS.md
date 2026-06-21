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
