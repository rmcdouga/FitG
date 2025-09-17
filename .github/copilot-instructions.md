# Copilot Instructions for FitG Project

## Project Overview

FitG is a modular Java project organized as a multi-module Maven workspace. It is designed to provide a flexible, extensible framework for the FitG game, supporting multiple interfaces and integration options. The project is structured into several submodules, each with a distinct responsibility:

- **fitg.basegame**: Contains the core game logic, domain model, and rules for FitG.
- **fitg.cli**: Provides a command-line interface for interacting with the game.
- **fitg.renderer**: Handles rendering and visualization, potentially for graphical or GUI-based output.
- **fitg-spring-boot-autoconfigure**: Offers Spring Boot auto-configuration for seamless integration of FitG components into Spring Boot applications.
- **fitg-spring-boot-starter**: Bundles dependencies and configuration for easy Spring Boot integration.
- **svg-viewer**: Utility for viewing SVG images, likely used for game assets or rendering support.

Each module is self-contained, with its own Maven configuration, source code, and tests. Shared code is placed in common modules. The project follows best practices for code quality, testing, and documentation, as detailed below.

---

## General Guidelines
- Use Java 17 or higher for all code and tests.
- Follow standard Java naming conventions for classes, methods, and variables.
- Organize code into appropriate packages reflecting the domain (e.g., `com.rogers.rmcdouga.fitg.basegame`).
- Write clear, concise, and well-documented code.
- Use interfaces and abstractions where appropriate to promote testability and flexibility.

## Unit Testing
- Use **JUnit 5 (Jupiter Engine)** for all unit tests.
- Place test classes in the corresponding `src/test/java` package structure.
- Name test classes with the `*Test` suffix (e.g., `GameTest`).
- Use descriptive test method names that explain the scenario being tested.
- Mock dependencies using Mockito or similar libraries when needed.
- Ensure tests are independent and repeatable.

## Build & Dependencies
- Use Maven for dependency management and builds.
- Define dependencies in the appropriate `pom.xml` for each module.
- Keep dependencies up to date and avoid unnecessary libraries.

## Documentation
- Document public classes and methods with Javadoc.
- Provide high-level module and package overviews where helpful.
- Maintain a `README.md` at the root of each module for module-specific information.

## Code Quality
- Use static analysis tools (e.g., Checkstyle, PMD, SpotBugs) to maintain code quality.
- Address warnings and errors reported by these tools.
- Perform code reviews for all significant changes.

## Project Structure
- Each submodule (e.g., `fitg.basegame`, `fitg.cli`, `fitg.renderer`) should be self-contained and follow the above guidelines.
- Shared code should be placed in common modules or packages.

## Additional Notes
- For Spring Boot modules, follow Spring Boot best practices for configuration and component scanning.
- For CLI and renderer modules, ensure clear separation of concerns between UI, logic, and data.
- Use GitMooji for commit messages to maintain a clean and informative commit history.
---

*This file provides coding and contribution guidelines for the FitG project. Please update as the project evolves.*