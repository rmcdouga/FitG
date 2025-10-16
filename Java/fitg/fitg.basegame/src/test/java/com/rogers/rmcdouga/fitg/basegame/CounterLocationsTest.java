package com.rogers.rmcdouga.fitg.basegame;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations.PlacedCounter;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

class CounterLocationsTest {

	private final GameBox gameBox = BaseGameBox.create();
	private CounterLocations underTest = new CounterLocations(gameBox);
	private final StackManager stackMgr = new StackManager();	// Create my own stack manager
	private final Stack stack = stackMgr.of(Doctor_Sontag, Drakir_Grebb);
	private final BaseGameEnviron location = Adare.listEnvirons().getFirst();
	private final List<Counter> countersList = List.of(Adam_Starlight, Agan_Rafa, stack, Air_1_0, Air_1_0);

	private final List<PlacedCounter<Counter>> counters = countersList.stream()
																	  .map(c->underTest.placeCounter(location, c))
																	  .map(Optional::get)
																	  .toList();
	// TODO: Alter StackManager to disallow stacks within stacks.
	
	@BeforeEach
	void setup() {
	}
	
	@Test
	void testCountersAt() {
		Collection<Counter> result = underTest.countersAt(location);
		Collection<Counter> expectedResult = toCounter(counters);
		
		assertNotNull(result);
		assertAll(
				()->assertEquals(expectedResult.size(),result.size()),
				()->assertTrue(expectedResult.containsAll(result)),
				()->assertTrue(result.containsAll(expectedResult))
				);
	}

	@Test
	void testLocationOf_present() {
		Location result = underTest.locationOf(Agan_Rafa).orElseThrow();
		
		assertEquals(location, result);
	}

	@Test
	void testLocationOf_notPresent() {
		assertTrue(underTest.locationOf(Professor_Mareg).isEmpty());
		
	}

	@Test
	void testLocationOf_presentInStack() {
		Location result = underTest.locationOf(Doctor_Sontag).orElseThrow();
		
		assertEquals(location, result);
	}

	@Test
	void testMove() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(Agan_Rafa, newLocation);
		assertNotNull(result);
		assertAll(
				()->shouldBeEquivilent(List.of(Adam_Starlight, stack, Air_1_0, Air_1_0), underTest.countersAt(location)),
				()->shouldBeEquivilent(List.of(Agan_Rafa), underTest.countersAt(newLocation))
				);
	}
	
	@Test
	void testMove_stack() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(stack, newLocation);
		assertNotNull(result);
		assertAll(
				()->shouldBeEquivilent(List.of(Adam_Starlight, Agan_Rafa, Air_1_0, Air_1_0), underTest.countersAt(location)),	// any order
				()->shouldBeEquivilent(List.of(stack), underTest.countersAt(newLocation)),
				()->assertSame(newLocation, underTest.locationOf(stack).orElseThrow()),
				()->assertSame(newLocation, underTest.locationOf(Drakir_Grebb).orElseThrow())
				);
	}
	
	@Test
	void testRemove() {
		CounterLocations result = underTest.remove(Adam_Starlight);
		assertNotNull(result);
		
		Collection<Counter> counterLeft = result.countersAt(location);
		assertAll(
				()->assertEquals(counters.size() - 1,counterLeft.size()),
				()->assertTrue(counterLeft.contains(Agan_Rafa)),
				()->assertFalse(counterLeft.contains(Adam_Starlight))
				);
		
	}

	@Test
	void testRemove_stack() {
		CounterLocations result = underTest.remove(stack);
		assertNotNull(result);
		
		Collection<Counter> counterLeft = result.countersAt(location);
		assertAll(
				()->assertEquals(counters.size() - 1,counterLeft.size()),
				()->assertTrue(counterLeft.contains(Agan_Rafa)),
				()->assertFalse(counterLeft.contains(stack)),
				()->assertFalse(counterLeft.contains(Doctor_Sontag))
				);
		
	}
	

	@Test
	void testDuplicateAdd() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.placeCounter(Adare.environ(0), Adam_Starlight));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("IN_PLAY"),containsString(Adam_Starlight.toString())));
	}

	@Test
	void testBadMove_WrongCounter() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.move(Ly_Mantok, Adare.environ(0)));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertEquals("Couldn't find counter (Ly_Mantok).", msg);
	}

	@Test
	void testBadRemove_WrongCounter() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.remove(Ly_Mantok));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertEquals("Couldn't find counter (Ly_Mantok).", msg);
	}

	@Test
	void testLocationOfByType_present() {
		List<Location> result = underTest.locationOfByType(Air_1_0).toList();
		
		assertAll(
				()->assertThat(result, hasSize(2)),
				()->assertThat(result, everyItem(equalTo(location)))
				);
	}
	
	@Test
	void testLocationOfByType_notPresent() {
		List<Location> result = underTest.locationOfByType(Professor_Mareg).toList();
		
		assertThat(result, is(empty()));
	}

	@Test
	void testStackContaining_present() {
		Counter result = underTest.stackContaining(Doctor_Sontag).orElseThrow();
		
		if (result instanceof Stack resultStack) {
			assertTrue(stack.isEquivalent(resultStack));
		} else {
			fail("Expected a Stack but got a " + result.getClass().getSimpleName());
		}
	}
	
	@Test
	void testStackContaining_notPresent() {
		assertTrue(underTest.stackContaining(Adam_Starlight).isEmpty());
	}
	
	private Collection<Counter> toCounter(Collection<PlacedCounter<Counter>> placed) {
		return placed.stream().map(PlacedCounter::counter).toList();
	}
	
	@SuppressWarnings("unchecked")
	private void shouldBeEquivilent(Collection<Counter> expected, Collection<Counter> actual) {
		Predicate<Counter> isStack = c->c instanceof Stack;
		Predicate<Counter> notStack = Predicate.not(isStack);
		assertThat(actual, everyItem(notNullValue()));
		List<String> expectedCounters = toStream(expected, notStack).map(Counter::id).toList();
		List<String> actualCounters =  toStream(actual, notStack).map(Counter::id).toList();
		assertThat(actualCounters, containsInAnyOrder(expectedCounters.toArray(new String[0])));
		List<Stack> expectedStacks = (List<Stack>)(List<?>)toStream(expected, isStack).toList();
		List<Stack> actualStacks =  (List<Stack>)(List<?>)toStream(actual, isStack).toList();
		assertEquals(expectedStacks.size(), actualStacks.size());
		for(int i = 0; i < expectedStacks.size(); i++) {
			shouldBeEquivilent(expectedStacks.get(i), actualStacks.get(i));
		}
	}
	
	private static <T> Stream<T> toStream(Collection<T> counters, Predicate<T> filter) {
		return counters.stream().filter(filter);
	}
}
