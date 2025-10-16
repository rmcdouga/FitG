package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

// This class doesn't test the trivial getters, just the non-trivial methods.
class BaseGameGameBoardTest {
	private static final BaseGameScenario SCENARIO = BaseGameScenario.FlightToEgrix;
	GameBoard underTest = BaseGameGameBoard.create(SCENARIO.createMap(), SCENARIO.type());
	
	@Test
	void testGetPlanetContaining() {
		Planet expectedPlanet = BaseGamePlanet.Quibron;
		Environ environ = expectedPlanet.environ(0);
		
		Planet actualPlanet = underTest.getPlanetContaining(environ);
		assertSame(expectedPlanet, actualPlanet);
	}

}
