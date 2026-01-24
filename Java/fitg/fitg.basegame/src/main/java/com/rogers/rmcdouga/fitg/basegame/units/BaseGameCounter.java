package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class BaseGameCounter {

	public static boolean containsFactionCounters(Faction faction, Collection<Counter> counters) {
		return counters.stream().anyMatch(counter -> isFactionCounter(faction, counter));
	}

	public static boolean isFactionCounter(Faction faction, Counter counter) {
		return switch (faction) {
			case REBEL -> isRebelCounter(counter);
			case IMPERIAL -> isImperialCounter(counter);
			// There are no ADMIN Counters and Counters should not be NEUTRAL if they are on the map (I think)
			case ADMIN, NEUTRAL -> throw new UnsupportedOperationException("IsFactionCounter should not be called this faction: " + faction);
		};
	}

	private static boolean isRebelCounter(Counter counter) {
		return switch (counter) {
			 case BaseGameCharacter character when character.allegience() == Faction.REBEL -> true;
			 case RebelMilitaryUnit _ -> true;
			 case Stack stack -> containsFactionCounters(Faction.REBEL, stack);
			default -> false;
		};
	}

	private static boolean isImperialCounter(Counter counter) {
		return switch (counter) {
			case BaseGameCharacter character when character.allegience() == Faction.IMPERIAL -> true;
			case ImperialMilitaryUnit _ -> true;
			case BaseGameImperialSpaceship _ -> true;
			case Stack stack -> containsFactionCounters(Faction.IMPERIAL, stack);
			default -> false;
		};
	}
}
