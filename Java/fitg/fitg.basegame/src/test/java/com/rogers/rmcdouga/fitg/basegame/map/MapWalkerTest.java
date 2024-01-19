package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.MapWalker.MapConsumer;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

class MapWalkerTest {

	private MapWalker underTest;

	@BeforeEach
	void setup() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		Game createdGame = Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
		underTest = new MapWalker(createdGame);
	}

	@Test
	void testWalk() {
		WalkerCounter count = new WalkerCounter();
	
		underTest.walk(count);
		
		assertAll(
				()->assertEquals(1, count.numStarSystems, "Number of StarSystms different than expected."),
				()->assertEquals(3, count.numPlanets, "Number of Planets different than expected."),
				()->assertEquals(4, count.numEnvirone, "Number of Environs different than expected.")
				);
	}

	@Test
	void testDefaultConsumer() {
		// Should process without any errors.
		underTest.walk(new MapConsumer() {});
	}
	
	@Test
	void testEnvironConsumer() {
		WalkerCounter count = new WalkerCounter();
		
		underTest.walk(MapConsumer.environConsumer(count::environConsumer));
		
		assertAll(
				()->assertEquals(0, count.numStarSystems, "Number of StarSystms different than expected."),
				()->assertEquals(0, count.numPlanets, "Number of Planets different than expected."),
				()->assertEquals(4, count.numEnvirone, "Number of Environs different than expected.")
				);
	}
	
	@Test
	void testPlanetConsumer() {
		WalkerCounter count = new WalkerCounter();
	
		underTest.walk(MapConsumer.planetConsumer(count::planetConsumer));
		
		assertAll(
				()->assertEquals(0, count.numStarSystems, "Number of StarSystms different than expected."),
				()->assertEquals(3, count.numPlanets, "Number of Planets different than expected."),
				()->assertEquals(0, count.numEnvirone, "Number of Environs different than expected.")
				);
	}

	@Test
	void testStarSystemConsumer() {
		WalkerCounter count = new WalkerCounter();
	
		underTest.walk(MapConsumer.starSystemConsumer(count::starSystemConsumer));
		
		assertAll(
				()->assertEquals(1, count.numStarSystems, "Number of StarSystms different than expected."),
				()->assertEquals(0, count.numPlanets, "Number of Planets different than expected."),
				()->assertEquals(0, count.numEnvirone, "Number of Environs different than expected.")
				);
	}

	private static class WalkerCounter implements MapConsumer {
		private int numStarSystems;
		private int numPlanets;
		private int numEnvirone;

		@Override
		public void starSystemConsumer(StarSystem starSystem) {
			numStarSystems++;
		}

		@Override
		public void planetConsumer(Planet planet) {
			numPlanets++;
		}

		@Override
		public void environConsumer(Environ environ) {
			numEnvirone++;
		}
		
	}
}
