package com.rogers.rmcdouga.fitg.basegame.units;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpaceshipTest {

	private static final int GALACTIC_FRIEGHTER_LIMIT = 16;
	Spaceship underTest = BaseGameRebelSpaceship.Galactic_Freighter;

	@Test
	void testOverLimit_under() {
		assertFalse(underTest.overLimit(GALACTIC_FRIEGHTER_LIMIT - 1));
	}

	@Test
	void testOverLimit_equal() {
		assertFalse(underTest.overLimit(GALACTIC_FRIEGHTER_LIMIT));
	}

	@Test
	void testOverLimit_over() {
		assertTrue(underTest.overLimit(GALACTIC_FRIEGHTER_LIMIT + 1));
	}


}
