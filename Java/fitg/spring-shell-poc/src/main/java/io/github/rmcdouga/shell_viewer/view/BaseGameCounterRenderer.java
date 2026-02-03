package io.github.rmcdouga.shell_viewer.view;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCounter.factionOfCounter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCounter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class BaseGameCounterRenderer {
	private static final String CIRCLE_A = "\u24B6";	// Imperial Elite Army 5-4
	private static final String CIRCLE_I = "\u24BE";	// Imperial Units Present
	private static final String CIRCLE_L = "\u24C1";	// Imperial Line 3-2
	private static final String CIRCLE_M = "\u24C2";	// Imperial Militia 1-0
	private static final String CIRCLE_N = "\u24C3";	// Imperial Elite Navy 4-5
	private static final String CIRCLE_P = "\u24C5";	// Imperial Patrol 1-2
	private static final String CIRCLE_R = "\u24C7";	// Rebel Units Present
	private static final String CIRCLE_S = "\u24C8";	// Imperial Suicide Squad 1(5)
	private static final String CIRCLE_V = "\u24CB";	// Imperial Veteran 3-4
	
	// Rebel Air 1-0
	// Rebel Air 2-1
	// Rebel Air 2-3
	// Rebel Air 4-3
	// Rebel Fire 1-0
	// Rebel Fire 2-1
	// Rebel Fire 2-3
	// -- Rebel Fire 4-3
	// Rebel Liquid 1-0
	// Rebel Liquid 2-1
	// Rebel Liquid 2-3
	// Rebel Liquid 4-3
	// Rebel Subterranean 1-0
	// Rebel Subterranean 2-1
	// Rebel Subterranean 2-3
	// Rebel Subterranean 4-3
	// Rebel Urban 1-0
	// Rebel Urban 2-1
	// Rebel Urban 2-3
	// Rebel Urban 4-3
	// Rebel Elite Urban 4-4
	// Rebel Wild 1-0
	// Rebel Wild 2-1
	// Rebel Wild 2-3
	// Rebel Wild 4-3
	// Rebel Elite Wild 4-4
	
	
	// Interface that can render a counter (one implementation per counter type)
	@FunctionalInterface
	private interface Renderer {
		AttributedString render();
	}
	
	// Counter ID and Renderer tuple
	private record RendererEntry(String id, Renderer renderer) {};
	
	// Map Counter IDs to the associated renderer
	private static final Map<String, Renderer> RENDERERS_MAP = Stream.of(
																		ImperialMilitaryUnitRenderer.renderers()
																	  )
																	  .flatMap(Function.identity())
																	  .collect(Collectors.toMap(re->re.id(), re->re.renderer()));
	
	private enum ImperialMilitaryUnitRenderer implements Renderer {
		Elite_Army(CIRCLE_A), 	// Circle A
		Elite_Navy(CIRCLE_N), 	// Circle N
		Line(CIRCLE_L), 		// Circle L
		Militia(CIRCLE_M),		// Circle M
		Patrol(CIRCLE_P),		// Circle P
		SuicideSquad(CIRCLE_S),	// Circle S
		Veteran(CIRCLE_V);		// Circle V

		private final String symbol;

		private ImperialMilitaryUnitRenderer(String symbol) {
			this.symbol = symbol;
		}

		private static ImperialMilitaryUnitRenderer from(ImperialMilitaryUnit unit) {
			return switch (unit) {
				case ImperialMilitaryUnit.Elite_Army -> Elite_Army;
				case ImperialMilitaryUnit.Elite_Navy -> Elite_Navy;
				case ImperialMilitaryUnit.Line -> Line;
				case ImperialMilitaryUnit.Militia -> Militia;
				case ImperialMilitaryUnit.Patrol -> Patrol;
				case ImperialMilitaryUnit.SuicideSquad -> SuicideSquad;
				case ImperialMilitaryUnit.Veteran -> Veteran;
			};
		}

		@Override
		public AttributedString render() {
			return new AttributedString(symbol, ColorsAndStyles.STYLE_IMPERIAL);
		}

		static Stream<RendererEntry> renderers() {
			return ImperialMilitaryUnit.stream().map(imu->new RendererEntry(imu.id(), from(imu)));
		}
	};
	// BaseGameImperialSpaceshipRenderer would go here.

	// BaseGameCharacterRenderer would go here.
	// BaseGameUnitRenderer would go here.
	// BaseGameImperialMilitaryUnitRenderer would go here.
	// BaseGameRebelMilitaryUnitRenderer would go here.
	// BaseGameStackRenderer would go here.
	// BaseGameSpaceshipStackRenderer would go here.
	
	private static final Set<String> IMPERIAL_UNIT_IDS = Stream.of(
															ImperialMilitaryUnit.stream().map(ImperialMilitaryUnit::id),
															BaseGameImperialSpaceship.stream().map(BaseGameImperialSpaceship::id)
															)
														  .flatMap(Function.identity())
														  .collect(Collectors.toSet());

	private static final Set<String> REBEL_UNIT_IDS = Stream.of(
															RebelMilitaryUnit.stream().map(RebelMilitaryUnit::id)
															)
														  .flatMap(Function.identity())
														  .collect(Collectors.toSet());

	static AttributedString renderCounters(ZoomLevel zoomLevel, Collection<Counter> counters) {
		return switch (zoomLevel) {
			case PLANETARY -> renderCounters_Planetary(counters);
			case SYSTEM -> renderCounters_System(counters);
			case GALACTIC -> new AttributedString("");
		};
	}

	// System level rendering involves just indicating whether there are counters present and which faction they belong to.
	private static AttributedString renderCounters_System(Collection<Counter> counters) {
		AttributedStringBuilder sb = new AttributedStringBuilder();
		if (containsImperialCounters(counters)) {
			sb.append(CIRCLE_I, ColorsAndStyles.STYLE_IMPERIAL); // Circle I
		}
		if (containsRebelCounters(counters)) {
			sb.append(CIRCLE_R,ColorsAndStyles.STYLE_REBEL);	 // Circle R
		}
		return sb.toAttributedString();
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

	private static AttributedString renderCounters_Planetary(Collection<Counter> counters) {
		return AttributedString.join(new AttributedString(", "), ()->counters.stream()
																		 	  .map(counter -> renderCounter_Planetary(counter))
																		 	  .iterator());
	}

	// Planetary level rendering shows full details of each counter.
	private static AttributedString renderCounter_Planetary(Counter counter) {
		
		return RENDERERS_MAP.getOrDefault(counter.id(), ()->new AttributedString("*" + counter.id(), styleFor(factionOfCounter(counter)))).render();
		// TODO: Implement rendering for each counter type.
//		
//		return switch (counter) {
//			// case BaseGameCharacter character -> BaseGameCharacterRenderer.renderCharacter(zoomLevel, character);
//			// case BaseGameImperialSpaceship ship -> BaseGameImperialSpaceshipRenderer.renderImperialSpaceship(zoomLevel, ship);
//			// case BaseGameUnit unit -> BaseGameUnitRenderer.renderUnit(zoomLevel, unit);
////			case ImperialMilitaryUnit impUnit -> ImperialMilitaryUnitRenderer.render(impUnit);
//			// case BaseGameRebelMilitaryUnit rebUnit -> BaseGameRebelMilitaryUnitRenderer.renderRebelMilitaryUnit(zoomLevel, rebUnit);
//			// case BaseGameStack stack -> BaseGameStackRenderer.renderStack(zoomLevel, stack);
//			// case BaseGameSpaceshipStack shipStack -> BaseGameSpaceshipStackRenderer.renderSpaceshipStack(zoomLevel, shipStack);
//			default -> counter.id();
//		};
	}

	private static AttributedStyle styleFor(Faction faction) {
		return switch (faction) {
			case IMPERIAL -> ColorsAndStyles.STYLE_IMPERIAL;
			case REBEL -> ColorsAndStyles.STYLE_REBEL;
			default -> ColorsAndStyles.STYLE_NEUTRAL;
		};
	}
	
}