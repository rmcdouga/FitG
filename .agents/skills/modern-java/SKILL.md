---
name: modern-java
description: Generic, composable Java 25 code conventions — modern syntax, code style, naming, visibility, structure, methods, streams, exceptions, and documentation rules that apply across all Java contexts (single-file scripts, CLI apps, MicroProfile/Jakarta EE servers, libraries). Technology-neutral within the Java world; meant to be composed with context-specific skills (e.g. `java-cli-script`, `java-cli-app`, `microprofile-server`, `bce`). Use when writing, generating, or reviewing Java code anywhere the composed skill does not already specify style. Triggers on "Java conventions", "Java style", "Java code style", "modern Java", "Java 25", "idiomatic Java", or any request to write or review Java code where context-specific skills do not already cover style.
---

## Scope
- Language-level style plus common Spring Boot idioms. This is the general convention
  set; it is repo-agnostic by design.
- It does NOT cover repo-specific architecture (module boundaries, which class owns
  which responsibility). Those rules live in the repo's `CLAUDE.md`.
- A more specific instruction always wins: an explicit request, the repo `CLAUDE.md`,
  or a more specific skill overrides a rule here.
- This is a working baseline. Refine it as the team agrees on conventions.

## Java version and syntax
- Target Java 25; assume all features are GA. Never use preview flags.
- Use modern syntax naturally: `var`, records, sealed types, pattern matching, text
  blocks, switch expressions.
- Use `var` for locals when the type is obvious from the right-hand side.
- Prefer switch expressions with arrow syntax over `case:` with `break`.
- Use pattern matching for `instanceof` (no separate cast) and in `switch` for type dispatch.
- Use the diamond operator `<>`. Never use raw generic types; always parameterize.
- Prefer domain types over primitives and strings: `Path` over `String`, `URI` over
  `String`, `Duration` over `long`, `Instant`/`LocalDate` over `Date`.
- Favor the standard library (`java.util`, `java.nio.file`, `java.time`, `java.net.http`)
  over hand-rolled equivalents.

## Dependency injection (Spring)
- Use constructor injection. It makes dependencies explicit, lets you mark them `final`,
  and lets tests construct the class with plain `new`, no container required.
- Mark injected dependencies `final`.
- A class with a single constructor needs no `@Autowired`; Spring injects it.
- Do not use field injection (`@Autowired` on a field) or setter injection unless a
  framework genuinely requires it.
- No mutable static state.

## Visibility and modifiers
- Default to the most restrictive access that works: `private` fields and helper methods,
  package-private only with a concrete reason, `public` only for the intended API.
- Test through the public API. Do not widen visibility just to reach internals from a
  test. If an internal is genuinely hard to test, treat that as a design signal. When you
  must, relax a single member to package-private and mark it (`@VisibleForTesting` or a
  short comment) as a deliberate exception.
- Prefer `final` for fields that are set once (injected collaborators, immutable state).

## Types and classes
- Use records for value types and data carriers.
- Use sealed interfaces or classes for closed hierarchies; they pair with pattern matching.
- Create an interface only when there are multiple implementations or a real strategy
  seam. Reject single-implementation interfaces.
- Favor composition over inheritance.
- Prefer static factory methods (`of`, `from`) over passing `null` to a constructor.
- Avoid anonymous inner classes; extract them into named, testable types (for example a
  Spring `@Component` or a `@Bean` method).
- Add a class only when it reduces complexity or improves readability.

## Naming
- Name types after their responsibility, not a technical layer.
- Reserve Spring stereotype suffixes for the role they name: `Controller` for web
  endpoints, `Service` for service-layer beans, `Repository` for data access,
  `Configuration` for config classes.
- Avoid meaningless suffixes that hide responsibility: `*Impl`, `*Manager`, `*Creator`,
  `*Util`, `*Helper`.
- Prefer record-style accessors without the `get` prefix (`configuration()` not
  `getConfiguration()`), but keep JavaBean getters where serialization expects them
  (for example Jackson-bound types).

## Methods and lambdas
- Keep methods short, cohesive, and named for intent.
- No multi-statement lambdas; extract to a well-named method and use a method reference.
- Prefer method references over equivalent lambdas.
- Split a `.filter()` with multiple `&&`/`||` into chained `.filter()` calls, or extract a
  named predicate method.
- Extract non-trivial calculations and boolean conditions into named methods so call
  sites read as intent.
- Use guard clauses (early return) over deeply nested `if`/`else`.
- Do not write empty pass-through delegate methods.

## Streams and collections
- Prefer the Stream API over imperative `for` loops, and terminal operations that return
  a value over `forEach`.
- Return a value from a stream rather than mutating an output parameter inside `forEach`.
- Prefer `.toList()` over `.collect(Collectors.toList())`.
- Prefer `List.of()`, `Set.of()`, `Map.of()` for small immutable collections.
- Return empty collections, never `null`. Never put `null` into a collection.
- Use named intermediate variables when a chain becomes hard to read.
- For finite rule sets (for example validation), prefer a data table you stream over,
  not a long `if`/`else` ladder.

## Code style
- KISS and YAGNI. Implement the simplest thing that works; ask before adding speculative
  abstraction.
- When approaches tie, choose fewer lines; but prefer several simple lines over one dense
  line.
- Prefer text blocks over `+`-concatenated multi-line strings; prefer `"...".formatted(...)`
  over `String.format(...)`.
- Prefer `Files.readString`/`writeString`/`lines` over manual reader and writer boilerplate.
- Prefer try-with-resources over a manual `close()` on any `AutoCloseable`.
- Prefer enums over magic strings for finite, well-defined sets.
- Extract repeated literals into named constants; prefer named constants over bare numbers.
- Use `Optional` as a return type only when absence is meaningful; never as a parameter type.

## JSON and Jackson (Spring)
- Use the Spring-managed `ObjectMapper` (inject it). Never `new ObjectMapper()` in
  application code.
- Bind JSON to records or typed classes. Avoid ad-hoc tree walking such as
  `JsonNode.fields()` where a typed binding or a documented Jackson API does the job.

## Exceptions
- Prefer unchecked over checked exceptions.
- Never throw raw `Exception` or `RuntimeException`; throw a specific type.
- Never catch broad `Exception` or `Throwable` to swallow problems. Catch the specific
  type you can handle, then log with context or rethrow wrapped.
- Do not `throw e` to rethrow unchanged when it adds nothing.
- Use `_` for an unused catch parameter.
- Add a custom exception only when it meaningfully improves clarity or handling.

## Logging
- Use SLF4J (`org.slf4j.Logger` via `LoggerFactory`) so the backend stays swappable under
  Spring Boot. Never use `System.out`/`System.err`, `java.util.logging`, or `System.Logger`.
- Name the logger `log` and declare it `private static final`. Lombok's `@Slf4j` generates
  exactly this if the project uses Lombok.
- Use parameterized messages (`log.info("loaded {} forms", count)`), not string concatenation.

## HTTP client
- For outbound HTTP in a Spring app, prefer Spring's `RestClient` (or `WebClient` for
  reactive code). If you use `java.net.http.HttpClient` directly, prefer its synchronous
  API and reach for async only when explicitly needed.

## User-facing output
- User-facing strings (UI text, messages returned to end users) are plain text: no emoji.

## Testing
- Use AssertJ assertions rather than raw JUnit assertions.
- Do not prefix test methods with `test` or `should`; name them for the behavior.
- Write minimal, meaningful tests. Do not test code that cannot fail (records, enums,
  plain accessors).
- Aim for a few focused tests per class, not many trivial ones.
- A specific `isEqualTo` makes weaker checks (`isNotNull`, `startsWith`) on the same value
  redundant.

## Comments and JavaDoc
- Default to no comments; let names carry the meaning.
- Comment only the non-obvious why: a hidden constraint, a subtle invariant, a workaround,
  or surprising behavior.
- Do not restate the signature or describe what the code obviously does.
- When you do write JavaDoc, prefer Markdown JavaDoc (`///`) over HTML-tagged `/** */`.

---

Adapted from Adam Bien's airails `java-conventions`, with changes for Spring Boot:
constructor injection (not field), SLF4J `log` (not `System.Logger`/`LOGGER`), `private` by
default (not package-private for tests), `final` for injected dependencies, Spring
stereotypes and `RestClient` where they fit, plus 4Point rules for Jackson, exception
handling, and user-facing text.
