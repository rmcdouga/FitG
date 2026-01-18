package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;

class BaseGamePlanetFinderTest {

	private final PlanetFinder planetFinder = new BaseGamePlanetFinder(GameTest.createGalacticGame());

	@ParameterizedTest
	@EnumSource(BaseGamePlanet.class)
	void testFindByName(BaseGamePlanet expectedPlanet) {
		var planet = planetFinder.findByName(expectedPlanet.getName().toUpperCase()).orElseThrow();
		assertSame(expectedPlanet, planet);
	}

	@ParameterizedTest
	@EnumSource(BaseGamePlanet.class)
	void testFindById(BaseGamePlanet expectedPlanet) {
		var planet = planetFinder.findById(expectedPlanet.getId()).orElseThrow();
		assertSame(expectedPlanet, planet);
	}

	@Test
	void testFindByName_Failure() {
		assertTrue(planetFinder.findByName("BadName").isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = {99, 110, 399, 999})
	void testFindById_Failure(int badId) {
		assertTrue(planetFinder.findById(badId).isEmpty());
	}
}
