package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.GameMap.MapConsumer;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

public class MapWalkerTest {

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
		
		count.assertCounts(1, 3, 4);
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
		
		count.assertCounts(0, 0, 4);
	}
	
	@Test
	void testPlanetConsumer() {
		WalkerCounter count = new WalkerCounter();
	
		underTest.walk(MapConsumer.planetConsumer(count::planetConsumer));
		
		count.assertCounts(0, 3, 0);
	}

	@Test
	void testStarSystemConsumer() {
		WalkerCounter count = new WalkerCounter();
	
		underTest.walk(MapConsumer.starSystemConsumer(count::starSystemConsumer));
		
		count.assertCounts(1, 0, 0);
	}

	public static class WalkerCounter implements MapConsumer {
		private int numStarSystems;
		private int numPlanets;
		private int numEnvirons;

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
			numEnvirons++;
		}

		public void assertCounts(int expectedStarSystemCount, int expectedPlanetCount, int expectedEnvironCount) {
			assertAll(
					()->assertEquals(expectedStarSystemCount, numStarSystems, "Number of StarSystms different than expected."),
					()->assertEquals(expectedPlanetCount, numPlanets, "Number of Planets different than expected."),
					()->assertEquals(expectedEnvironCount, numEnvirons, "Number of Environs different than expected.")
					);
		}
	}
}
