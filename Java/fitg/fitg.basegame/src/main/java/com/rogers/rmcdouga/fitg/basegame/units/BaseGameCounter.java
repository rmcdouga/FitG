package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class BaseGameCounter {

	public static boolean containsFactionCounters(Faction faction, Collection<Counter> counters) {
		return counters.stream().anyMatch(counter -> isFactionCounter(faction, counter));
	}

	public static boolean isFactionCounter(Faction faction, Counter counter) {
		// There are no ADMIN Counters and Counters should not be NEUTRAL if they are on the map (I think)
		if (faction == Faction.ADMIN || faction == Faction.NEUTRAL) {
			throw new UnsupportedOperationException("IsFactionCounter should not be called for this faction: " + faction);
		}
		return factionOfCounter(counter) == faction;
	}

	public static Faction factionOfCounter(Counter counter) {
		return switch (counter) {
		 case BaseGameCharacter character -> character.allegience();
		 case RebelMilitaryUnit _ -> Faction.REBEL;
		 case ImperialMilitaryUnit _ -> Faction.IMPERIAL;
		 case BaseGameImperialSpaceship _ -> Faction.IMPERIAL;
		 case Stack stack -> stack.stream().findAny().map(c->factionOfCounter(c)).orElseThrow(()-> new IllegalArgumentException("Cannot determine faction of an empty Stack"));
		 default -> throw new IllegalArgumentException("Cannot determine faction of counter: " + counter);
		};
	}
}
