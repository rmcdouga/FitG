package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb.Level;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb.State;

class PdbTest {

	@Test
	void testIncreaseLevel() {
		assertAll(
				()->assertEquals(Pdb.UP_1, Pdb.UP_0.increaseLevel()),
				()->assertEquals(Pdb.UP_2, Pdb.UP_1.increaseLevel()),
				()->assertEquals(Pdb.UP_2, Pdb.UP_2.increaseLevel()),
				()->assertEquals(Pdb.DOWN_1, Pdb.DOWN_0.increaseLevel()),
				()->assertEquals(Pdb.DOWN_2, Pdb.DOWN_1.increaseLevel()),
				()->assertEquals(Pdb.DOWN_2, Pdb.DOWN_2.increaseLevel())
				);
	}

	@Test
	void testDecreaseLevel() {
		assertAll(
				()->assertEquals(Pdb.UP_0, Pdb.UP_0.decreaseLevel()),
				()->assertEquals(Pdb.UP_0, Pdb.UP_1.decreaseLevel()),
				()->assertEquals(Pdb.UP_1, Pdb.UP_2.decreaseLevel()),
				()->assertEquals(Pdb.DOWN_0, Pdb.DOWN_0.decreaseLevel()),
				()->assertEquals(Pdb.DOWN_0, Pdb.DOWN_1.decreaseLevel()),
				()->assertEquals(Pdb.DOWN_1, Pdb.DOWN_2.decreaseLevel())
				);
	}

	@Test
	void testUp() {
		assertAll(
				()->assertEquals(Pdb.UP_0, Pdb.UP_0.up()),
				()->assertEquals(Pdb.UP_1, Pdb.UP_1.up()),
				()->assertEquals(Pdb.UP_2, Pdb.UP_2.up()),
				()->assertEquals(Pdb.UP_0, Pdb.DOWN_0.up()),
				()->assertEquals(Pdb.UP_1, Pdb.DOWN_1.up()),
				()->assertEquals(Pdb.UP_2, Pdb.DOWN_2.up())
				);
	}

	@Test
	void testDown() {
		assertAll(
				()->assertEquals(Pdb.DOWN_0, Pdb.UP_0.down()),
				()->assertEquals(Pdb.DOWN_1, Pdb.UP_1.down()),
				()->assertEquals(Pdb.DOWN_2, Pdb.UP_2.down()),
				()->assertEquals(Pdb.DOWN_0, Pdb.DOWN_0.down()),
				()->assertEquals(Pdb.DOWN_1, Pdb.DOWN_1.down()),
				()->assertEquals(Pdb.DOWN_2, Pdb.DOWN_2.down())
				);
	}

	@Test
	void testOf() {
		assertAll(
				()->assertEquals(Pdb.UP_0, Pdb.of(State.UP, Level.ZERO)),
				()->assertEquals(Pdb.UP_1, Pdb.of(State.UP, Level.ONE)),
				()->assertEquals(Pdb.UP_2, Pdb.of(State.UP, Level.TWO)),
				()->assertEquals(Pdb.DOWN_0, Pdb.of(State.DOWN, Level.ZERO)),
				()->assertEquals(Pdb.DOWN_1, Pdb.of(State.DOWN, Level.ONE)),
				()->assertEquals(Pdb.DOWN_2, Pdb.of(State.DOWN, Level.TWO))
				);
	}

	@Test
	void testIsUp() {
		assertAll(
				()->assertEquals(true, Pdb.of(State.UP, Level.ZERO).isUp()),
				()->assertEquals(false, Pdb.of(State.DOWN, Level.ZERO).isUp())
				);
	}
	
	@Test
	void testIsDown() {
		assertAll(
				()->assertEquals(false, Pdb.of(State.UP, Level.ZERO).isDown()),
				()->assertEquals(true, Pdb.of(State.DOWN, Level.ZERO).isDown())
				);
	}
	
	@Test
	void testLevel() {
		assertAll(
				()->assertEquals(Level.ZERO, Pdb.of(State.UP, Level.ZERO).level()),
				()->assertEquals(Level.ONE, Pdb.of(State.UP, Level.ONE).level()),
				()->assertEquals(Level.TWO, Pdb.of(State.UP, Level.TWO).level())
				);
	}
	

}
