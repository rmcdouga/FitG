package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

class BaseGameRaceTypeTest {

	@Test
	void testGetDescription() {
//		System.out.println("{" + Yester.getDescription() + "}");
		assertEquals("{This bird-like race thrives in the clouds and wind currents of any hydrogen-rich atmosphere. With their high intuitive intelligence and curious nature, they learned the secrets of spaceflight long ago from other races and have colonized the skies of many planets in their beautiful stellar-sail spacecraft.}", "{" + Yester.getDescription().get() + "}");
	}

	@ParameterizedTest
	@EnumSource
	void testDescriptions(BaseGameRaceType race) {
		// Test for characters that might accidently be introduced in a raw string.
		// None of these characters appear in the descriptions, so they are likely artifacts that were introduced
		// by accident.
		if (race.isStarFaring()) {
			String description = race.getDescription().get();
			assertFalse(race.isStarFaring() && description.isBlank());	// All star-faring races should have a description.
			assertFalse(description.contains("\n"));
			assertFalse(description.contains("\r"));
			assertFalse(description.contains("\""));
			assertFalse(description.contains("+"));
		}
	}
	
	@ParameterizedTest
	@EnumSource
	void testEnvirons(BaseGameRaceType race) {
		// Test to make sure all the non-starfaring races are in their environ.
		if (!race.isStarFaring()) {
			BaseGamePlanet homePlanet = race.getHomePlanet().get();
			BaseGameEnvironType homeEnviron = race.getEnvironType().get();
			Optional<RaceType> filter = homePlanet.streamEnvirons()
													    .filter(e->e.getType() == homeEnviron)
													    .findAny()
													    .map(BaseGameEnviron::getRaces)
													    .filter(l->l.contains(race))
													    .map(l->l.get(0));
			assertTrue(filter.isPresent(), ()->"Planet '" + homePlanet + "' (" + homePlanet.getId() + ") does not have environ '" + homeEnviron + "' with race '" + race + "'.");
		}
	}
}
