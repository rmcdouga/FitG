package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

class StackManagerTest {
	private final StackManager underTest = new StackManager();
	private final Stack stack = underTest.of(Doctor_Sontag, Drakir_Grebb);

	
	@Test
	void testStackContaining_Present() {
		Optional<Stack> result = underTest.stackContaining(Drakir_Grebb);
		assertTrue(result.isPresent());
		assertEquals(stack, result.get());
	}

	@Test
	void testStackContaining_NotPresent() {
		Optional<Stack> result = underTest.stackContaining(Adam_Starlight);
		assertFalse(result.isPresent());
	}

	@Test
	void testEnsureNoStacks() {
		StackManager other = new StackManager();
		Stack otherStack = other.of(Adam_Starlight, Agan_Rafa);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.of((Counter)otherStack));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("Stacks are not allowed in this context"));
	}

	@Test
	void testTransfer() {
		StackManager other = new StackManager();
		Stack otherStack = other.of(Adam_Starlight, Agan_Rafa);
		assertTrue(underTest.stackContaining(Adam_Starlight).isEmpty());	// Make sure he's currently managed
		Stack transferredStack = underTest.transfer(otherStack);			// Transfer
		assertTrue(underTest.stackContaining(Adam_Starlight).isPresent());	// Make sure he's now managed
		assertIterableEquals(otherStack, transferredStack);					// Make sure all the counters were transferred.
	}

	@Test
	void testTransfer_SameStackManager() {
		Stack transferredStack = underTest.transfer(stack);			// Transfer
		assertSame(stack, transferredStack);
	}

	@Test
	void testFind() {
		StackManager other = new StackManager();
		Stack otherStack = other.of(Drakir_Grebb, Doctor_Sontag);	// different order, order shouldn't matter
		Stack foundStack = underTest.find(otherStack).get();
		assertEquals(Set.copyOf(stack), Set.copyOf(otherStack));
	}

	@ParameterizedTest
	@MethodSource("generateNonEquivalentStacks")
	void testFind_NotFound(Stack otherStack) {
		assertTrue(underTest.find(otherStack).isEmpty());
	}

	@Test
	void testFind_Same() {
		Optional<Stack> foundStack = underTest.find(stack);
		assertSame(stack, foundStack.get());
	}
	
	@Test
	void testFind_empty() {
		StackManager other = new StackManager();
		Stack otherStack = other.of();						// Empty should never match
		assertTrue(underTest.find(otherStack).isEmpty());
	}
	
	@Test
	void testStack_IsEquivalent() {
		StackManager other = new StackManager();
		Stack otherStack = other.of(Drakir_Grebb, Doctor_Sontag);	// different order, order shouldn't matter
		assertNotSame(stack, otherStack);
		assertEquals(stack, otherStack);
		assertEquals(otherStack, stack);
		assertEquals(stack, stack);
		assertEquals(otherStack, otherStack);
	}

	@ParameterizedTest
	@MethodSource("generateNonEquivalentStacks")
	void testStack_IsEquivalent_NotEquivilent(Stack otherStack) {
		assertNotSame(stack, otherStack);
		assertNotEquals(stack, otherStack);
		assertNotEquals(otherStack, stack);
		assertEquals(stack, stack);
		assertEquals(otherStack, otherStack);
	}
	static Stream<Stack> generateNonEquivalentStacks() {
		StackManager other = new StackManager();
		return Stream.of(
				other.of(Adam_Starlight, Agan_Rafa),					// disjoint
				other.of(Doctor_Sontag),								// subset
				other.of(Doctor_Sontag, Drakir_Grebb, Adam_Starlight),	// superset
				other.of()												// empty
				);
	}
}
