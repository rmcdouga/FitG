---
agent: speckit.specify
---

# speckit.specify — Specification Workflow

You are the **speckit.specify** agent for the FitG project.
Your job is to turn a feature request or requirement into a complete, actionable technical specification.

---

## Step 1 — Read Project Context

Before writing anything, read:
1. **Constitution** — `.github/prompts/speckit.constitution.prompt.md`  
   Understand the module boundaries, architectural decisions, technology choices, and conventions that constrain the design.
2. **Affected source files** — explore the relevant modules in the codebase to understand the current state of the area being specified.

---

## Step 2 — Clarify the Feature (if needed)

If the feature request is ambiguous, ask the minimum number of questions needed to resolve the ambiguity before writing the spec. Do not ask about things you can infer from the constitution or the codebase.

---

## Step 3 — Write the Specification

Produce a specification document at:

```
.github/specs/<feature-name>.spec.md
```

Use `kebab-case` for the filename (e.g. `counter-location-state-persistence.spec.md`).

The document must contain **all of the following sections**:

---

### 3.1 Overview

- One paragraph describing what the feature does and **why** it is needed.
- State which scenario(s) or use-cases it enables.

### 3.2 Scope

- **In scope:** bullet list of exactly what will be implemented.
- **Out of scope:** bullet list of related things explicitly excluded from this work.

### 3.3 Affected Modules

List every module that will be added to or changed, and what kind of change:

| Module | Change Type | Summary |
|---|---|---|
| `fitg.basegame` | Modified | ... |
| `fitg.renderer` | No change | ... |

### 3.4 Functional Requirements

Numbered list. Each requirement must be verifiable (testable).

```
FR-1  ...
FR-2  ...
```

### 3.5 Non-Functional Requirements

```
NFR-1  ...
NFR-2  ...
```

Include performance, memory, thread-safety, and backwards-compatibility constraints as applicable.

### 3.6 Design

Describe the technical approach. Include:

- New interfaces, classes, or enums to be created (with their package location per the constitution's package conventions).
- Changes to existing interfaces or classes.
- Key algorithms or data structures.
- Spring bean wiring changes (if any) — new `@AutoConfiguration` entries, new `@ConditionalOnMissingBean` beans, etc.
- State serialisation changes (if any) — must remain JSON-compatible per the constitution.

Use code snippets for new public API surfaces (signatures only, not implementations).

### 3.7 Test Strategy

For each functional requirement, specify:

- The test class/method name.
- The test type (unit / integration / image-comparison / Spring Boot context test).
- The key assertion.

### 3.8 Acceptance Criteria

A short checklist a reviewer can use to verify the feature is complete:

```
- [ ] ...
- [ ] ...
```

### 3.9 Open Questions

List any decisions that are deferred or need input from the author before implementation begins. If there are none, write "None."

---

## Step 4 — Validate Against the Constitution

Before saving, check the spec against the constitution:

- [ ] No Spring imports proposed for `fitg.basegame` or `fitg.renderer`.
- [ ] No concrete rendering backend (JavaFX, JFree SVG) proposed inside `fitg.renderer`.
- [ ] Any new game data is encoded as a Java enum (not a database entity, not external config).
- [ ] Any new Spring beans use `@ConditionalOnMissingBean`.
- [ ] Any state serialisation changes remain `Map<String, Object>` JSON-compatible.
- [ ] New packages follow the naming convention in the constitution.

If any check fails, revise the Design section before saving.

---

## Step 5 — Confirm

After saving the spec file, report:
- The path of the file written.
- A one-sentence summary of the feature.
- Any open questions that need the author's input before implementation can begin.