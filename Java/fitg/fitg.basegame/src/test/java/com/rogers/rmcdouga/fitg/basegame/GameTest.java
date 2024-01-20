package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.MapWalkerTest;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.MapWalkerTest.WalkerCounter;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

class GameTest {

	@Test
	void testCreateGame() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		Game createdGame = Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
		
		assertNotNull(createdGame);
		assertEquals(BaseGameLoyaltyType.Loyal, createdGame.getLoyalty(BaseGamePlanet.Quibron));
		assertEquals(Pdb.Level.ONE, createdGame.getPdb(BaseGamePlanet.Quibron).level());
		assertTrue(createdGame.getPdb(BaseGamePlanet.Quibron).isUp());
		
		Collection<Counter> countersInSpace = createdGame.countersAt(BaseGameScenario.ScenarioLocation.IN_SPACE);
		assertNotNull(countersInSpace);
		assertFalse(countersInSpace.isEmpty());
		
		Collection<Counter> countersOnAngoff = createdGame.countersAt(BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).get());
		assertNotNull(countersOnAngoff);
		assertFalse(countersOnAngoff.isEmpty());
		
		// Validate the number of starsystems, planets and environs.
		WalkerCounter count = new MapWalkerTest.WalkerCounter();
		createdGame.walk(count);
		count.assertCounts(1, 3, 4);

	}

}
