package com.rogers.rmcdouga.fitg.renderer.text;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

/// Renders the visible game map state as deterministic plain text for AI consumption.
public final class TextMapRenderer {

	private static final String EMPTY_VALUE = "-";
	private static final String INDENT = "  ";

	private final GameBoard gameBoard;
	private final CounterLocator counterLocator;

	public TextMapRenderer(GameBoard gameBoard, CounterLocator counterLocator) {
		this.gameBoard = Objects.requireNonNull(gameBoard);
		this.counterLocator = Objects.requireNonNull(counterLocator);
	}

	public String render() {
		return render(gameBoard.getStarSystems());
	}

	public String render(Collection<StarSystem> starSystems) {
		Objects.requireNonNull(starSystems);
		var builder = new StringBuilder("map:\n");
		sortedStarSystems(starSystems).forEach(starSystem -> appendStarSystem(builder, starSystem));
		return builder.toString();
	}

	private void appendStarSystem(StringBuilder builder, StarSystem starSystem) {
		appendLine(builder, 1, "star-system: %d %s".formatted(starSystem.getId(), starSystem.getName()));
		appendLine(builder, 2, "drift: " + formatCounters(starSystem.drift()));
		appendLine(builder, 2, "drift2: " + formatCounters(starSystem.drift2()));
		sortedPlanets(starSystem).forEach(planet -> appendPlanet(builder, planet));
	}

	private void appendPlanet(StringBuilder builder, Planet planet) {
		appendLine(builder, 2, "planet: %d %s".formatted(planet.getId(), planet.getName()));
		appendLine(builder, 3, "loyalty: " + gameBoard.getLoyalty(planet).getName());
		appendLine(builder, 3, "control: " + formatControl(planet));
		appendLine(builder, 3, "pdb: " + formatPdb(gameBoard.getPdb(planet)));
		appendLine(builder, 3, "orbit: " + formatCounters(planet.inOrbit()));
		planet.listEnvirons().forEach(environ -> appendEnviron(builder, environ));
	}

	private void appendEnviron(StringBuilder builder, Environ environ) {
		appendLine(builder, 3,
				"environ: %s | size=%d | resources=%s | coup=%s | races=%s | creature=%s | sovereign=%s | counters=%s"
						.formatted(
								environ.getType().getName(),
								environ.getSize(),
								formatResources(environ),
								formatCoup(environ),
								formatRaces(environ),
								formatOptionalLabel(environ.getCreature()),
								formatOptionalLabel(environ.getSovereign()),
								formatCounters(environ)));
	}

	private String formatCounters(com.rogers.rmcdouga.fitg.basegame.map.Location location) {
		var counters = Optional.ofNullable(counterLocator.countersAt(location)).orElse(List.of());
		return counters.stream()
				.map(this::formatCounter)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList()
				.toString();
	}

	private String formatCounter(Counter counter) {
		if (counter instanceof StackManager.SpaceshipStack spaceshipStack) {
			return formatSpaceshipStack(spaceshipStack);
		}
		if (counter instanceof StackManager.Stack stack) {
			return formatStack(stack);
		}
		return counterLabel(counter);
	}

	private String formatSpaceshipStack(StackManager.SpaceshipStack spaceshipStack) {
		var passengers = spaceshipStack.stream()
				.map(this::counterLabel)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList();
		return "spaceship-stack[%s; passengers=%s]".formatted(spaceshipLabel(spaceshipStack), passengers);
	}

	private String spaceshipLabel(StackManager.SpaceshipStack spaceshipStack) {
		var spaceship = spaceshipStack.spaceship();
		return spaceship instanceof Counter counter ? counterLabel(counter) : String.valueOf(spaceship);
	}

	private String formatStack(StackManager.Stack stack) {
		var contents = stack.stream()
				.map(this::counterLabel)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList();
		return "stack" + contents;
	}

	private String counterLabel(Counter counter) {
		var id = counter.id();
		return id == null || id.isBlank() ? String.valueOf(counter) : id;
	}

	private String formatControl(Planet planet) {
		return Optional.ofNullable(planet.getCurrentControl())
				.orElse(planet.getControlA())
				.toString();
	}

	private String formatPdb(Pdb pdb) {
		return pdb.name();
	}

	private String formatResources(Environ environ) {
		if (environ.getResources().isEmpty()) {
			return EMPTY_VALUE;
		}
		var resourceText = Integer.toString(environ.getResources().getAsInt());
		return environ.isStarResources() ? resourceText + "*" : resourceText;
	}

	private String formatCoup(Environ environ) {
		return environ.getCoupRating().isPresent() ? Integer.toString(environ.getCoupRating().getAsInt()) : EMPTY_VALUE;
	}

	private String formatRaces(Environ environ) {
		return environ.getRaces().stream()
				.map(this::formatRace)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList()
				.toString();
	}

	private String formatRace(RaceType race) {
		return race.getName() + (race.isStarFaring() ? "*" : "");
	}

	private String formatOptionalLabel(Optional<?> value) {
		return value.map(this::formatDisplayValue).orElse(EMPTY_VALUE);
	}

	private String formatDisplayValue(Object value) {
		return String.valueOf(value).replace('_', ' ');
	}

	private List<StarSystem> sortedStarSystems(Collection<StarSystem> starSystems) {
		return starSystems.stream()
				.sorted(Comparator.comparingInt(StarSystem::getId)
						.thenComparing(StarSystem::getName, String.CASE_INSENSITIVE_ORDER))
				.toList();
	}

	private List<Planet> sortedPlanets(StarSystem starSystem) {
		return starSystem.listPlanets().stream()
				.map(Planet.class::cast)
				.sorted(Comparator.comparingInt(Planet::getId)
						.thenComparing(Planet::getName, String.CASE_INSENSITIVE_ORDER))
				.toList();
	}

	private void appendLine(StringBuilder builder, int indentLevel, String text) {
		builder.append(INDENT.repeat(indentLevel))
				.append(text)
				.append('\n');
	}

	// --- Compact output (suppresses empty fields, abbreviated labels) ---

	public String renderCompact() {
		return renderCompact(gameBoard.getStarSystems());
	}

	public String renderCompact(Collection<StarSystem> starSystems) {
		Objects.requireNonNull(starSystems);
		var builder = new StringBuilder("map:\n");
		sortedStarSystems(starSystems).forEach(starSystem -> appendCompactStarSystem(builder, starSystem));
		return builder.toString();
	}

	private void appendCompactStarSystem(StringBuilder builder, StarSystem starSystem) {
		appendLine(builder, 1, "[%d %s]".formatted(starSystem.getId(), starSystem.getName()));
		compactCounters(starSystem.drift())
				.ifPresent(c -> appendLine(builder, 2, "drift :: " + c));
		compactCounters(starSystem.drift2())
				.ifPresent(c -> appendLine(builder, 2, "drift2 :: " + c));
		sortedPlanets(starSystem).forEach(planet -> appendCompactPlanet(builder, planet));
	}

	private void appendCompactPlanet(StringBuilder builder, Planet planet) {
		appendLine(builder, 2, "[%d %s] loyalty=%s ctrl=%s pdb=%s".formatted(
				planet.getId(),
				planet.getName(),
				gameBoard.getLoyalty(planet).getName(),
				compactControl(planet),
				compactPdb(gameBoard.getPdb(planet))));
		compactCounters(planet.inOrbit())
				.ifPresent(c -> appendLine(builder, 3, "orbit :: " + c));
		planet.listEnvirons().forEach(environ -> appendCompactEnviron(builder, environ));
	}

	private void appendCompactEnviron(StringBuilder builder, Environ environ) {
		var parts = new java.util.ArrayList<String>();
		parts.add(environ.getType().getName());
		parts.add("sz=" + environ.getSize());
		environ.getResources().ifPresent(r -> parts.add("res=" + r + (environ.isStarResources() ? "*" : "")));
		environ.getCoupRating().ifPresent(c -> parts.add("coup=" + c));
		environ.getRaces().stream()
				.sorted(Comparator.comparing(RaceType::getName, String.CASE_INSENSITIVE_ORDER))
				.map(r -> r.getName() + (r.isStarFaring() ? "*" : ""))
				.forEach(parts::add);
		environ.getCreature().ifPresent(c -> parts.add("cr=" + c.name()));
		environ.getSovereign().ifPresent(s -> parts.add("sov=" + s.name()));

		var base = String.join(" ", parts);
		var counters = compactCounters(environ);
		appendLine(builder, 3, counters.map(c -> base + " :: " + c).orElse(base));
	}

	private Optional<String> compactCounters(com.rogers.rmcdouga.fitg.basegame.map.Location location) {
		var counters = Optional.ofNullable(counterLocator.countersAt(location)).orElse(List.of());
		if (counters.isEmpty()) {
			return Optional.empty();
		}
		var formatted = counters.stream()
				.map(this::compactCounter)
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.toList();
		return Optional.of(String.join(" ", formatted));
	}

	private String compactCounter(Counter counter) {
		if (counter instanceof StackManager.SpaceshipStack spaceshipStack) {
			var passengers = spaceshipStack.stream()
					.map(this::counterLabel)
					.sorted(String.CASE_INSENSITIVE_ORDER)
					.toList();
			return "%s[%s]".formatted(spaceshipLabel(spaceshipStack), String.join(" ", passengers));
		}
		if (counter instanceof StackManager.Stack stack) {
			var contents = stack.stream()
					.map(this::counterLabel)
					.sorted(String.CASE_INSENSITIVE_ORDER)
					.toList();
			return "stack[%s]".formatted(String.join(" ", contents));
		}
		return counterLabel(counter);
	}

	private String compactPdb(Pdb pdb) {
		return pdb.level().ordinal() + (pdb.isUp() ? "↑" : "↓");
	}

	private String compactControl(Planet planet) {
		return Optional.ofNullable(planet.getCurrentControl())
				.orElse(planet.getControlA())
				.toString()
				.replace("Controlled", "");
	}

	// --- Markdown output ---

	public String renderMarkdown() {
		return renderMarkdown(gameBoard.getStarSystems());
	}

	public String renderMarkdown(Collection<StarSystem> starSystems) {
		Objects.requireNonNull(starSystems);
		var builder = new StringBuilder("# FitG Map\n\n");
		sortedStarSystems(starSystems).forEach(starSystem -> appendMarkdownStarSystem(builder, starSystem));
		return builder.toString();
	}

	private void appendMarkdownStarSystem(StringBuilder builder, StarSystem starSystem) {
		builder.append("## Star System: ")
				.append(starSystem.getId())
				.append(" ")
				.append(starSystem.getName())
				.append("\n\n");

		appendMarkdownSection(builder, "Drift", formatCounters(starSystem.drift()));
		appendMarkdownSection(builder, "Drift2", formatCounters(starSystem.drift2()));

		sortedPlanets(starSystem).forEach(planet -> appendMarkdownPlanet(builder, planet));
	}

	private void appendMarkdownPlanet(StringBuilder builder, Planet planet) {
		builder.append("### Planet: ")
				.append(planet.getId())
				.append(" ")
				.append(planet.getName())
				.append("\n\n");

		builder.append("- **Loyalty:** ").append(gameBoard.getLoyalty(planet).getName()).append("\n");
		builder.append("- **Control:** ").append(formatControl(planet)).append("\n");
		builder.append("- **PDB:** ").append(formatPdb(gameBoard.getPdb(planet))).append("\n\n");

		appendMarkdownSection(builder, "Orbit", formatCounters(planet.inOrbit()));

		planet.listEnvirons().forEach(environ -> appendMarkdownEnviron(builder, environ));
	}

	private void appendMarkdownEnviron(StringBuilder builder, Environ environ) {
		builder.append("#### Environment: ").append(environ.getType().getName()).append("\n\n");
		builder.append("- **Size:** ").append(environ.getSize()).append("\n");
		builder.append("- **Resources:** ").append(formatResources(environ)).append("\n");
		builder.append("- **Coup:** ").append(formatCoup(environ)).append("\n");
		builder.append("- **Races:** ").append(formatRaces(environ)).append("\n");
		builder.append("- **Creature:** ").append(formatOptionalLabel(environ.getCreature())).append("\n");
		builder.append("- **Sovereign:** ").append(formatOptionalLabel(environ.getSovereign())).append("\n");
		appendMarkdownSection(builder, "Counters", formatCounters(environ));
		builder.append("\n");
	}

	private void appendMarkdownSection(StringBuilder builder, String heading, String content) {
		builder.append("**").append(heading).append(":**\n");
		if (content.equals("[]")) {
			builder.append("- *(empty)*\n\n");
		} else {
			// Extract items from list format [item1, item2]
			var items = parseListContent(content);
			for (var item : items) {
				builder.append("- ").append(item).append("\n");
			}
			builder.append("\n");
		}
	}

	private List<String> parseListContent(String content) {
		// Remove outer brackets
		if (content.startsWith("[") && content.endsWith("]")) {
			content = content.substring(1, content.length() - 1);
		}
		if (content.isBlank()) {
			return List.of();
		}
		// Simple split on comma for now; does not handle nested structures
		return List.of(content.split(",\\s*"));
	}
}