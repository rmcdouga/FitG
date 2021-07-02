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
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.MockPlayerStrategies.FlightToEgrixStrategies.ScenarioRebelDecisions;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.MockPlayerStrategies.FlightToEgrixStrategies.ScenarioImperialDecisions;


class BaseGameScenarioTest {
	private final PlayerDecisions fteRebelDecisions = new ScenarioRebelDecisions();
	private final PlayerDecisions fteImperialDecisions = new ScenarioImperialDecisions();
	
	StackManager stackMgr = new StackManager();
	CounterLocations counterLocations = new CounterLocations(stackMgr);
	CounterPool counterPool = BaseGameBox.create();

	@Test
	void testFlightToEgrixGame_createMap() {
		Collection<StarSystem> map = FlightToEgrix.createMap();
		assertNotNull(map);
		assertEquals(1, map.size());
		assertTrue(map.contains(Egrix));
	}

	@Test
	void testFlightToEgrixGame_setupCounters() {
		CounterLocations result = FlightToEgrix.setupCounters(counterLocations, counterPool, stackMgr, fteRebelDecisions, fteImperialDecisions );
		assertNotNull(result);
		Optional<Location> location = result.location(Jon_Kidu);
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

}
