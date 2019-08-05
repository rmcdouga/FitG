package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaseGameCreatureTest {

	@Test
	void testGetName() {
		assertEquals("Ym-Barror", BaseGameCreature.Ym$Barror.getName());
		assertEquals("Snow Giants", BaseGameCreature.Snow_Giants.getName());
		assertEquals("Sentry Robot", BaseGameCreature.Sentry_Robot.getName());
		assertEquals("Frost Mist", BaseGameCreature.Frost_Mist.getName());
	}

}
