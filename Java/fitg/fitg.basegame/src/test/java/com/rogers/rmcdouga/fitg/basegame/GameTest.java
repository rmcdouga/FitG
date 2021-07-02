package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

class GameTest {

	@Test
	void testCreateGame() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		Game createdGame = Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
		
		assertNotNull(createdGame);
	}

}
