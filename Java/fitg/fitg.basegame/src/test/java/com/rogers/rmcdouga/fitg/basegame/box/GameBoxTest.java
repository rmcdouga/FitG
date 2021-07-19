package com.rogers.rmcdouga.fitg.basegame.box;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.Redjacs_Spaceship;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship.Interstellar_Sloop;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

class GameBoxTest {
	
	private final static Counter FAKE_COUNTER = new Counter() {};
	private final static Spaceship FAKE_SPACESHIP = new Spaceship() {
		@Override
		public int cannons() {
			return 0;
		}

		@Override
		public int shields() {
			return 0;
		}

		@Override
		public int maneuver() {
			return 0;
		}

		@Override
		public int maxPassengers() {
			return 5;
		}
	};

	private final GameBox underTest = BaseGameBox.create();
	
	// Character, ImperialSpaceship, Unit, Stack
	@Test
	void testGetCounterCounter_Character() {
		Optional<Character> counter = underTest.get((Character)Agan_Rafa);
		assertTrue(counter.isPresent());
		assertThat(counter.get().description(), containsString("Agan Rafa"));
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(Agan_Rafa));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
	}

	@Test
	void testGetCounterCounter_ImpSpaceship() {
		Optional<ImperialSpaceship> counter = underTest.get((ImperialSpaceship)Redjacs_Spaceship);
		assertTrue(counter.isPresent());
		assertEquals(4, counter.get().maxPassengers());
		assertTrue(underTest.get(Redjacs_Spaceship).isEmpty());
	}

	@Test
	void testGetCounterCounter_Unit() {
		Optional<Unit> counter = underTest.getCounter((Unit)ImperialMilitaryUnit.Militia);
		assertTrue(counter.isPresent());
		assertFalse(counter.get().isMobile());
	}

	// Stack that includes Characters and units
	@Test
	void testGetCounterCounter_ExistingStack() {
		StackManager stackMgr = new StackManager();
		Stack targetStack = stackMgr.of(Adam_Starlight, Agan_Rafa, ImperialMilitaryUnit.Militia);
		Optional<Stack> resultStack = underTest.get(targetStack);
		assertTrue(resultStack.isPresent());
		assertEquals(3, resultStack.get().size());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(Agan_Rafa));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
	}

	// Stack that includes Characters and units
	@Test
	void testGetCounterCounter_ExistingStackWithUnavailableCounter() {
		underTest.get(RebelMilitaryUnit.Liquid_2_3);	// Make this unit unavailable
		StackManager stackMgr = new StackManager();
		Stack targetStack = stackMgr.of(Adam_Starlight, Agan_Rafa, RebelMilitaryUnit.Liquid_2_3);
		Optional<Stack> resultStack = underTest.get(targetStack);
		assertTrue(resultStack.isEmpty());
	}

	// Stack that contains no counters
	// Not sure if this is a valid case.  Perhaps we shouldn't allow stacks with 0 contents,
	@Test
	void testGetCounterCounter_EmptyStack() {
		StackManager stackMgr = new StackManager();
		Stack targetStack = stackMgr.of();
		Optional<Stack> resultStack = underTest.get(targetStack);
		assertTrue(resultStack.isPresent());
		assertEquals(0, resultStack.get().size());
	}

	// Spaceship stack that includes characters
	@Test
	void testGetCounterCounter_ExistingSpaceshipStack() {
		StackManager stackMgr = new StackManager();
		SpaceshipStack targetStack = stackMgr.of(Interstellar_Sloop, Agan_Rafa, Adam_Starlight);
		Optional<SpaceshipStack> resultStack = underTest.get(targetStack);
		assertTrue(resultStack.isPresent());
		assertEquals(2, resultStack.get().size());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(Agan_Rafa));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));		
	}

	// Spaceship stack that includes characters
	@Test
	void testGetCounterCounter_ExistingSpaceshipStack_SpaceshipUnavailable() {
		underTest.get(Redjacs_Spaceship);
		StackManager stackMgr = new StackManager();
		SpaceshipStack targetStack = stackMgr.of((Spaceship)Redjacs_Spaceship, Agan_Rafa, Adam_Starlight);
		Optional<SpaceshipStack> resultStack = underTest.get(targetStack);
		assertTrue(resultStack.isEmpty());
	}

	// Stack that includes Characters and units
	@Test
	void testGetCounterCounter_NewStack() {
		StackManager stackMgr = new StackManager();
		Optional<Stack> resultStack = underTest.get(stackMgr, Adam_Starlight, Agan_Rafa, ImperialMilitaryUnit.Militia);
		assertTrue(resultStack.isPresent());
		assertEquals(3, resultStack.get().size());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(Agan_Rafa));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
	}

	// Spaceship stack that includes characters
	@Test
	void testGetCounterCounter_NewSpaceshipStack() {
		StackManager stackMgr = new StackManager();
		Optional<SpaceshipStack> resultStack = underTest.get(stackMgr, Interstellar_Sloop, Agan_Rafa, Adam_Starlight);
		assertTrue(resultStack.isPresent());
		assertEquals(2, resultStack.get().size());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(Agan_Rafa));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));		
	}

	// Stack that includes Characters and units
	@Test
	void testGetCounterCounter_NewStack_Unavailable() {
		underTest.get(RebelMilitaryUnit.Liquid_2_3);	// Make this unit unavailable
		StackManager stackMgr = new StackManager();
		Optional<Stack> resultStack = underTest.get(stackMgr, Adam_Starlight, Agan_Rafa, RebelMilitaryUnit.Liquid_2_3);
		assertFalse(resultStack.isPresent());
	}

	// Spaceship stack that includes characters
	@Test
	void testGetCounterCounter_NewSpaceshipStack_Unavailable() {
		underTest.get(Redjacs_Spaceship);		// Make the spaceship unavailable
		StackManager stackMgr = new StackManager();
		Optional<SpaceshipStack> resultStack = underTest.get(stackMgr, (Spaceship)Redjacs_Spaceship, Agan_Rafa, Adam_Starlight);
		assertFalse(resultStack.isPresent());
	}

	// fake Counter
	@Test
	void testGetCounterCounter_fail() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(FAKE_COUNTER));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("Unable to get"), containsString("from box"), containsString(FAKE_COUNTER.getClass().getName())));
	}

	// Stack that includes Characters and units with fake counter
	// Spaceship stack that includes characters and fake counter
	@ParameterizedTest
	@MethodSource("failStacks")
	void testGetCounterStack_fail(Stack testStack) {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.get(testStack));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("Unable to get"), containsString("from box"), anyOf(containsString(FAKE_COUNTER.getClass().getName()),containsString(FAKE_SPACESHIP.getClass().getName()))));
	}
	
	static Stream<Stack> failStacks() {
		StackManager stackMgr = new StackManager();

		return Stream.of(
						stackMgr.of(Adam_Starlight, Agan_Rafa, FAKE_COUNTER),
						stackMgr.of(Interstellar_Sloop, Agan_Rafa, FAKE_COUNTER),
						stackMgr.of(FAKE_SPACESHIP, Agan_Rafa)
						);
	}
}
