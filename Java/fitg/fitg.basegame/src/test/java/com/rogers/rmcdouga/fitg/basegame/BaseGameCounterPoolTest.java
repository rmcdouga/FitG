package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
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

	private CounterPool underTest = BaseGameCounterPool.create();
	
	@Test
	void testGetUnit() {
		Unit result = underTest.getUnit(ImperialMilitaryUnit.Line).get();
		assertNotNull(result);
	}

	@Test
	void testReturnCounter() {
		Unit first = underTest.getUnit(RebelMilitaryUnit.Fire_2_3).get();
		assertNotNull(first, "Should be able to get one Fire_2_3");
		assertAll(
				()->assertEquals(2, first.environCombatStrength()),
				()->assertEquals(3, first.spaceCombatStrength()),
				()->assertEquals(true, first.isMobile())
				);
		
		Optional<Unit> second = underTest.getUnit(RebelMilitaryUnit.Fire_2_3);
		assertTrue(second.isEmpty(), "Shouldn't be able to get two Fire_2_3s");
		
		CounterPool fluent = underTest.returnCounter(first);
		assertEquals(underTest, fluent);	// fluent interface should return the underTest.

		Unit firstAgain = underTest.getUnit(RebelMilitaryUnit.Fire_2_3).get();
		assertNotNull(firstAgain, "Should be able to get one Fire_2_3 again, after return");  
	}

	@Test
	void testGetUnit_InvalidUnit() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getUnit(invalidUnit));
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

}
