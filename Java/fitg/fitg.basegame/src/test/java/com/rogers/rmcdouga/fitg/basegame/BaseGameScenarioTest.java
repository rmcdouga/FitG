package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaseGameScenarioTest {

	@Test
	void testCreateFlightToEgrixGame() {
		Game game = Game.createGame(BaseGameScenario.FlightToEgrix);
		assertNotNull(game);
	}

}
