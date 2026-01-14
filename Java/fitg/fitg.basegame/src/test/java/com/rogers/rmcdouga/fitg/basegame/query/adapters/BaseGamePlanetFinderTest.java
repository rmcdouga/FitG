package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;

class BaseGamePlanetFinderTest {

	private final PlanetFinder planetFinder = new BaseGamePlanetFinder(GameTest.createFlightToEgrixGame());

	@Test
	void testFindByName() {
		var planet = planetFinder.findByName("Angoff").orElseThrow();
		assertSame(BaseGamePlanet.Angoff, planet);
	}

	@Test
	void testFindById() {
		var planet = planetFinder.findById(221).orElseThrow();
		assertSame(BaseGamePlanet.Quibron, planet);
	}

	@Test
	void testFindByName_Failure() {
		assertTrue(planetFinder.findByName("BadName").isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = {99, 399, 999})
	void testFindById_Failure(int badId) {
		assertTrue(planetFinder.findById(badId).isEmpty());
	}
}
