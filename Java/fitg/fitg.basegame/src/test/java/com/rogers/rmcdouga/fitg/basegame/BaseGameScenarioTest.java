package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.MockPlayerStrategies.FlightToEgrixStrategies.ScenarioRebelDecisions;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.MockPlayerStrategies.FlightToEgrixStrategies.ScenarioImperialDecisions;


class BaseGameScenarioTest {
	private final PlayerDecisions fteRebelDecisions = new ScenarioRebelDecisions();
	private final PlayerDecisions fteImperialDecisions = new ScenarioImperialDecisions();
	
	private final StackManager stackMgr = new StackManager();
	private final CounterPool counterPool = BaseGameBox.create();

	@Test
	void testFlightToEgrixGame_createMap() {
		Collection<StarSystem> map = FlightToEgrix.createMap();
		assertNotNull(map);
		assertEquals(1, map.size());
		assertTrue(map.contains(Egrix));
	}

	@Test
	void testFlightToEgrixGame_setupCounters() {
		Collection<PlaceCountersInstruction> result = FlightToEgrix.setupCounters(counterPool, stackMgr, fteRebelDecisions, fteImperialDecisions );
		assertNotNull(result);
		Optional<Location> location = result.stream()
											.filter(i->contains(i, Jon_Kidu))
											.findAny()
											.map(PlaceCountersInstruction::location);
		assertTrue(location.isPresent());
		assertEquals(Quibron.environ(0), location.get());
	}

	@Test
	void testFlightToEgrixGame_simpleProperties() {
		assertAll(
				()->assertEquals(FlightToEgrix.type(), Scenario.Type.StartRebellion),
				()->assertEquals(FlightToEgrix.rules(), Scenario.Rules.StarSystem),
				()->assertEquals(FlightToEgrix.numberOfGameTurns(), 6)
				);

	}

	private static boolean contains(PlaceCountersInstruction instruction, Counter targetCounter) {
		Counter examinedCounter = instruction.counter();
		if (examinedCounter instanceof Stack stack) {
			return contains(stack, targetCounter);
		}
		return examinedCounter == targetCounter;
	}

	private static boolean contains(Stack stack, Counter counter) {
//		System.out.println("Testing stack with:");
//		stack.stream().forEach(System.out::println);
		for (Counter c : stack) {
			if (c instanceof Stack containedStack) {
				if (contains(containedStack, counter)) {
					return true;
				}
			} else {
				if (c == counter) {
					return true;
				}
			}
		}
		return false;
	}
}
