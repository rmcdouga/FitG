package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BaseGameRaceTypeTest {

	@Test
	void testGetDescription() {
//		System.out.println("{" + Yester.getDescription() + "}");
		assertEquals("{This bird-like race thrives in the clouds and wind currents of any hydrogen-rich atmosphere. With their high intuitive intelligence and curious nature, they learned the secrets of spaceflight long ago from other races and have colonized the skies of many planets in their beautiful stellar-sail spacecraft.}", "{" + Yester.getDescription() + "}");
	}

	@ParameterizedTest
	@EnumSource
	void testDescriptions(BaseGameRaceType race) {
		// Test for characters that might accidently be introduced in a raw string.
		// None of these characters appear in the descriptions, so they are likely artifacts that were introduced
		// by accident.
		assertFalse(race.getDescription().contains("\n"));
		assertFalse(race.getDescription().contains("\r"));
		assertFalse(race.getDescription().contains("\""));
		assertFalse(race.getDescription().contains("+"));
	}
}
