package io.github.rmcdouga.fitg.tui4jviewer.view;

import java.util.Collection;
import java.util.stream.Collectors;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCounter;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;

public class BaseGameCounterRenderer {
	// Use some colors to represent different faction types.
	// Colors chosen from ANSI 256 color chart: https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
	// Better examples here: https://hexdocs.pm/color_palette/ansi_color_codes.html
	private static final Color COLOR_STRONG_YELLOW = Color.color("#d7d700");	// ANSI Yellow #184
	private static final Color COLOR_STRONG_RED = Color.color("#d70000"); 		// ANSI Red #160

	private final static Style STYLE_IMPERIAL = Style.newStyle().foreground(COLOR_STRONG_YELLOW);
	private final static Style STYLE_REBEL = Style.newStyle().foreground(COLOR_STRONG_RED);

	// BaseGameCharacterRenderer would go here.
	// BaseGameImperialSpaceshipRenderer would go here.
	// BaseGameUnitRenderer would go here.
	// BaseGameImperialMilitaryUnitRenderer would go here.
	// BaseGameRebelMilitaryUnitRenderer would go here.
	// BaseGameStackRenderer would go here.
	// BaseGameSpaceshipStackRenderer would go here.

	static String renderCounters(ZoomLevel zoomLevel, Collection<Counter> counters) {
		return switch (zoomLevel) {
			case PLANETARY -> renderCounters_Planetary(counters);
			case SYSTEM -> renderCounters_System(counters);
			case GALACTIC -> "";
		};
	}

	// System level rendering involves just indicating whether there are counters present and which faction they belong to.
	private static String renderCounters_System(Collection<Counter> counters) {
		StringBuilder sb = new StringBuilder();
		if (containsImperialCounters(counters)) {
			sb.append(STYLE_IMPERIAL.render("I"));
		}
		if (containsRebelCounters(counters)) {
			sb.append(STYLE_REBEL.render("R"));
		}
		return sb.toString();
	}
	
	private static boolean containsRebelCounters(Collection<Counter> counters) {
		return counters.stream().anyMatch(counter -> isRebelCounter(counter));
	}

	private static boolean isRebelCounter(Counter counter) {
		return switch (counter) {
			 case BaseGameCharacter character when character.allegience() == Faction.REBEL -> true;
			 case RebelMilitaryUnit _ -> true;
			 case Stack stack -> containsRebelCounters(stack);
			default -> false;
		};
	}

	private static boolean containsImperialCounters(Collection<Counter> counters) {
		return counters.stream().anyMatch(counter -> BaseGameCounter.isFactionCounter(Faction.IMPERIAL, counter));
	}

	private static String renderCounters_Planetary(Collection<Counter> counters) {
		return counters.stream()
					   .map(counter -> renderCounter_Planetary(counter))
					   .collect(Collectors.joining(", "));
	}

	// Planetary level rendering shows full details of each counter.
	private static String renderCounter_Planetary(Counter counter) {
		// TODO: Implement rendering for each counter type.
		return switch (counter) {
			// case BaseGameCharacter character -> BaseGameCharacterRenderer.renderCharacter(zoomLevel, character);
			// case BaseGameImperialSpaceship ship -> BaseGameImperialSpaceshipRenderer.renderImperialSpaceship(zoomLevel, ship);
			// case BaseGameUnit unit -> BaseGameUnitRenderer.renderUnit(zoomLevel, unit);
			// case BaseGameImperialMilitaryUnit impUnit -> BaseGameImperialMilitaryUnitRenderer.renderImperialMilitaryUnit(zoomLevel, impUnit);
			// case BaseGameRebelMilitaryUnit rebUnit -> BaseGameRebelMilitaryUnitRenderer.renderRebelMilitaryUnit(zoomLevel, rebUnit);
			// case BaseGameStack stack -> BaseGameStackRenderer.renderStack(zoomLevel, stack);
			// case BaseGameSpaceshipStack shipStack -> BaseGameSpaceshipStackRenderer.renderSpaceshipStack(zoomLevel, shipStack);
			default -> counter.id();
		};
	}
}
