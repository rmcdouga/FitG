package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

class BaseGameCounterPoolTest {

	private static final Unit invalidUnit = new Unit() {

		@Override
		public int environCombatStrength() {
			return 0;
		}

		@Override
		public int spaceCombatStrength() {
			return 0;
		}

		@Override
		public boolean isMobile() {
			return false;
		}
	};

	private static final ImperialSpaceship invalidSpaceship = new ImperialSpaceship() {
		
		@Override
		public int shields() {
			return 0;
		}
		
		@Override
		public int maxPassengers() {
			return 0;
		}
		
		@Override
		public int maneuver() {
			return 0;
		}
		
		@Override
		public int cannons() {
			return 0;
		}
	};

	private static final int EXPECTED_NUM_IMPERIAL_SPACESHIPS = 3;
	
	private CounterPool underTest = BaseGameBox.create();
	
	@Test
	void testGetUnit() {
		Unit result = underTest.getCounter(ImperialMilitaryUnit.Line).get();
		assertNotNull(result);
	}

	@Test
	void testReturnCounter() {
		Unit first = underTest.getCounter(RebelMilitaryUnit.Fire_2_3).get();
		assertNotNull(first, "Should be able to get one Fire_2_3");
		assertAll(
				()->assertEquals(2, first.environCombatStrength()),
				()->assertEquals(3, first.spaceCombatStrength()),
				()->assertEquals(true, first.isMobile())
				);
		
		Optional<Unit> second = underTest.getCounter(RebelMilitaryUnit.Fire_2_3);
		assertTrue(second.isEmpty(), "Shouldn't be able to get two Fire_2_3s");
		
		CounterPool fluent = underTest.returnCounter(first);
		assertEquals(underTest, fluent);	// fluent interface should return the underTest.

		Unit firstAgain = underTest.getCounter(RebelMilitaryUnit.Fire_2_3).get();
		assertNotNull(firstAgain, "Should be able to get one Fire_2_3 again, after return");  
	}

	@Test
	void testGetSpaceship_ImperialSpaceship() {
		for(int i = 0; i < EXPECTED_NUM_IMPERIAL_SPACESHIPS; i++) {
			assertNotNull(underTest.getSpaceship(BaseGameImperialSpaceship.Imperial_Spaceship).get());			
		}
		assertTrue(underTest.getSpaceship(BaseGameImperialSpaceship.Imperial_Spaceship).isEmpty());
	}
	
	@Test
	void testGetSpaceship_RedjacSpaceship() {
		assertNotNull(underTest.getSpaceship(BaseGameImperialSpaceship.Redjacs_Spaceship).get());
		assertTrue(underTest.getSpaceship(BaseGameImperialSpaceship.Redjacs_Spaceship).isEmpty());
	}

	@Test
	void testGetUnit_InvalidUnit() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getCounter(invalidUnit));
		String msg = ex.getMessage();
		assertNotNull(msg);
		String expectedMessage = "Invalid unit type supplied";
		assertTrue(msg.contains(expectedMessage), ()->"Expected msg to contain '" + expectedMessage + "' but was '" + msg + "'.");
	}

	@Test
	void testReturnUnit_InvalidUnit() {
		String expectedMessage = "Invalid unit supplied";
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.returnCounter(invalidUnit));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertTrue(msg.contains(expectedMessage), ()->"Expected msg to contain '" + expectedMessage + "' but was '" + msg + "'.");
	}

	@Test
	void testGetSpaceship_InvalidSpaceship() {
		String expectedMessage = "Invalid spaceship type supplied";
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getSpaceship(invalidSpaceship));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertTrue(msg.contains(expectedMessage), ()->"Expected msg to contain '" + expectedMessage + "' but was '" + msg + "'.");
	}

}
