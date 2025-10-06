package com.rogers.rmcdouga.fitg.basegame;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public class GameTest {
	Game underTest = createFlightToEgrixGame();

	@Test
	void testCreateGame() {
		
		assertNotNull(underTest);
		assertEquals(BaseGameLoyaltyType.Loyal, underTest.getLoyalty(BaseGamePlanet.Quibron));
		assertEquals(Pdb.Level.ONE, underTest.getPdb(BaseGamePlanet.Quibron).level());
		assertTrue(underTest.getPdb(BaseGamePlanet.Quibron).isUp());
		
		Collection<Counter> countersInSpace = underTest.countersAt(BaseGameScenario.IN_SPACE);
		assertNotNull(countersInSpace);
		assertFalse(countersInSpace.isEmpty());
		
		Collection<Counter> countersOnAngoff = underTest.countersAt(BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).get());
		assertNotNull(countersOnAngoff);
		assertFalse(countersOnAngoff.isEmpty());
	}

	private static Game createFlightToEgrixGame() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		Game createdGame = Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
		return createdGame;
	}

	@Test
	void testMoveCounterLocation() {
		// Given
		Collection<Counter> countersInSpace = underTest.countersAt(BaseGameScenario.IN_SPACE);
		Environ destination = BaseGamePlanet.Charkhan.environ(BaseGameEnvironType.Wild).orElseThrow();	// Empty environ from scenario setup
		// Test preconditions
		assertAll(
				()->assertThat(countersInSpace, iterableWithSize(1)),		// Only one counter in space
				()->assertThat(underTest.countersAt(destination), empty())	// Nothing at destination
				);
		
		Counter stack = countersInSpace.stream().findAny().get();
		
		// When
		underTest.move(stack, destination);
		
		// Then
		Collection<Counter> countersAtDest = underTest.countersAt(destination);
		assertAll(
				()->assertThat(countersAtDest, iterableWithSize(1)),
				()->assertThat(countersAtDest, containsInAnyOrder(stack)),
				()->assertThat(underTest.countersAt(BaseGameScenario.IN_SPACE), empty())	// No longer at source
				);
	}
}
