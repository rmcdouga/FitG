package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.CounterMatcher;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

class BaseGameCounterFinderTest {

	CounterFinder underTest = new BaseGameCounterFinder(GameTest.createFlightToEgrixGame());
	
	static final List<Arguments> testFindCounterStringArgs = List.of(
				Arguments.of("Jon Kidu", BaseGameCharacter.Jon_Kidu),
				Arguments.of("Imperial Spaceship", BaseGameImperialSpaceship.Imperial_Spaceship),
				Arguments.of("Line", ImperialMilitaryUnit.Line)
				);
	
	@ParameterizedTest
	@FieldSource("testFindCounterStringArgs")
	void testFindCounterString(String name, Counter expected) {
		assertThat(underTest.findCounter(name).orElseThrow(), CounterMatcher.sameAs(expected));
	}

	@ParameterizedTest
	@ValueSource(strings = {"Galactic_Freighter", "foo"})
	void testFindCounterString_Failure(String name) {
		// Unknown names should throw IllegalArgumentException
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.findCounter(name));
		assertNotNull(ex.getMessage());
		assertThat(ex.getMessage(), containsString(name));
	}

	@Test
	void testFindCounterString_NotFound() {
		// Names that are known but not present in the game should return empty Optional
		assertTrue(underTest.findCounter("Zina Adora").isEmpty());
	}

	static final List<Arguments> testFindCounterStringStringArgs = List.of(
			Arguments.of("Militia", "Charkhan", "Air", ImperialMilitaryUnit.Militia),
			Arguments.of("Patrol", "Charkhan", "Air", ImperialMilitaryUnit.Patrol)
			);

	@ParameterizedTest
	@FieldSource("testFindCounterStringStringArgs")
	void testFindCounterStringString(String type, String starOrPlanet, String location, Counter expected) {
		assertThat(underTest.findCounter(type, starOrPlanet, location).orElseThrow(), CounterMatcher.sameAs(expected));
	}

	@ParameterizedTest
	@CsvSource({
		"foo, Charkhan, Air, Unknown unit type",
		"Patrol, foo, Air, No star system or planet found",
		"Patrol, Charkhan, foo, No location found"
	})
	void testFindCounterStringString_Failure(String type, String starOrPlanet, String location, String expectedError) {
		// Unknown names should throw IllegalArgumentException
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.findCounter(type, starOrPlanet, location));
		assertNotNull(ex.getMessage());
		assertThat(ex.getMessage(), allOf(containsString("foo"), containsString(expectedError)));
	}
	
	@Test
	void testFindCounterStringString_NotFound() {
		// Names that are known but not present in the game should return empty Optional
		assertTrue(underTest.findCounter("Zina Adora", "Angoff", "Urban").isEmpty());
	}
	
	@Test
	void testFindCounterStringString_Null() {
		// Null arguments should throw NullPointerException
		assertThrows(NullPointerException.class, ()->underTest.findCounter("Militia", null, "whatever"));
		assertThrows(NullPointerException.class, ()->underTest.findCounter("Militia", "whatever", null));
	}
	
	@Test
	void testFindStackWithCounter() {
		Counter foundStack = underTest.findStackWithCounter("Jon Kidu").orElseThrow();
		if (foundStack instanceof Stack stack) {
			assertTrue(stack.contains(BaseGameCharacter.Jon_Kidu));
		} else {
			fail("Expected to find Jon Kidu in a stack");
		}
	}
	
	@Test
	void testFindStackWithCounter_NotFound() {
		assertTrue(underTest.findStackWithCounter("Line").isEmpty());
	}
}
