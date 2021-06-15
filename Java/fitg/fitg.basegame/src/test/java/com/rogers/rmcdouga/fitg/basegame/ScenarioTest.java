package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScenarioTest {

	@Test
	void testCreateFlightToEgrixGame() {
		assertNotNull(Scenario.FlightToEgrix.createGame());
	}

}
