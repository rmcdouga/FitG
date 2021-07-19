package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlanetTest {

	private final Planet underTest = BaseGamePlanet.Adare;
	
	@Test
	void testNumEnvirons() {
		assertEquals(2, underTest.numEnvirons());
	}

	@Test
	void testEnvironInt() {
		final int environIndex = 1;
		assertEquals(BaseGamePlanet.Adare.listEnvirons().get(environIndex), underTest.environ(environIndex));
	}

	@Test
	void testStreamEnvirons() {
		assertIterableEquals(BaseGamePlanet.Adare.listEnvirons(), underTest.streamEnvirons().toList());
	}

	@Test
	void testEnvironEnvironType() {
		assertEquals(BaseGamePlanet.Adare.listEnvirons().get(1), underTest.environ(BaseGameEnvironType.Wild).get());
	}

	@Test
	void testEnvironEnvironType_NotFound() {
		assertTrue(underTest.environ(BaseGameEnvironType.Fire).isEmpty());
	}

}
