package com.rogers.rmcdouga.fitg.basegame.units;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameSpaceship.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;

class SpaceshipStackTest {

	private static final BaseGameSpaceship TEST_SHIP = Stellar_Courier;
	private static final int TEST_SHIP_CAPACITY = TEST_SHIP.maxPassengers();

	private final StackManager stackMgr= new StackManager();

	SpaceshipStack underTest = stackMgr.of(TEST_SHIP, Adam_Starlight, Agan_Rafa, Barca, Boccanegra);

//	@Test
//	void testOfSpaceshipCounterArray() {
//		fail("Not yet implemented");
//	}

	@Test
	void testOfSpaceshipCounterArray_TooManyChars() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,  ()->stackMgr.of(TEST_SHIP, Adam_Starlight, Agan_Rafa, Barca, Boccanegra, Drakir_Grebb));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("5"), containsString("4"), containsString("Stellar_Courier")));
	}

	@Test
	void testAdd_TooManyChars() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,  ()->underTest.add(Drakir_Grebb));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("at capacity"), containsString("4"), containsString("Stellar_Courier")));
	}

	@Test
	void testAddAll_TooManyChars() {
		underTest.remove(Boccanegra);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,  ()->underTest.addAll(List.of(Boccanegra, Drakir_Grebb)));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("3"), containsString("2"), containsString("4"), containsString("Stellar_Courier")));
	}

	@Test
	void testOverLimit_under() {
		assertFalse(underTest.overLimit(TEST_SHIP_CAPACITY - 1));
	}

	@Test
	void testOverLimit_equal() {
		assertFalse(underTest.overLimit(TEST_SHIP_CAPACITY));
	}

	@Test
	void testOverLimit_over() {
		assertTrue(underTest.overLimit(TEST_SHIP_CAPACITY + 1));
	}

}
