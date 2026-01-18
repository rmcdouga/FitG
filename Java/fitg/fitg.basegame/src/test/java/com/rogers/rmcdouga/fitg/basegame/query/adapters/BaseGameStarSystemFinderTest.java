package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;

class BaseGameStarSystemFinderTest {

	private final StarSystemFinder starSystemFinder = new BaseGameStarSystemFinder(GameTest.createGalacticGame());

	@ParameterizedTest
	@EnumSource(BaseGameStarSystem.class)
	void testFindByName(BaseGameStarSystem expectedStarSystem) {
		var starSystem = starSystemFinder.findByName(expectedStarSystem.getName().toLowerCase()).orElseThrow();
		assertSame(expectedStarSystem, starSystem);
	}

	@ParameterizedTest
	@EnumSource(BaseGameStarSystem.class)
	void testFindById(BaseGameStarSystem expectedStarSystem) {
		var starSystem = starSystemFinder.findById(expectedStarSystem.getId()).orElseThrow();
		assertSame(expectedStarSystem, starSystem);
	}

	@Test
	void testFindByName_failure() {
		assertTrue(starSystemFinder.findByName("BadName").isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 10, 56, 956})
	void testFindById_failure(int badId) {
		assertTrue(starSystemFinder.findById(badId).isEmpty());
	}	
}
