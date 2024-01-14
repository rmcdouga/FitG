package com.rogers.rmcdouga.fitg.basegame;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations.CounterLocationState;
import com.rogers.rmcdouga.fitg.basegame.CounterLocations.CounterLocationsState;
import com.rogers.rmcdouga.fitg.basegame.CounterLocations.CounterState;
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
	private final BaseGameEnviron location = Adare.listEnvirons().get(0);
	private final List<Counter> countersList = List.of(Adam_Starlight, Agan_Rafa, stack);

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
	void testLocation_present() {
		Location result = underTest.location(Agan_Rafa);
		
		assertEquals(location, result);
	}

	@Test
	void testLocation_notPresent() {
		NullPointerException ex = assertThrows(NullPointerException.class, ()->underTest.location(Professor_Mareg));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("Couldn't find counter"), containsString(Professor_Mareg.toString())));
		
	}

	@Test
	void testLocation_presentInStack() {
		Location result = underTest.location(Doctor_Sontag);
		
		assertEquals(location, result);
	}

	@Test
	void testMove() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(Agan_Rafa, newLocation);
		assertNotNull(result);
		assertAll(
				()->shouldBeEquivilent(Set.of(Adam_Starlight, stack), underTest.countersAt(location)),
				()->shouldBeEquivilent(Set.of(Agan_Rafa), underTest.countersAt(newLocation))
				);
	}
	
	@Test
	void testMove_stack() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(stack, newLocation);
		assertNotNull(result);
		assertAll(
				()->shouldBeEquivilent(Set.of(Adam_Starlight, Agan_Rafa), underTest.countersAt(location)),	// any order
				()->shouldBeEquivilent(Set.of(stack), underTest.countersAt(newLocation)),
				()->assertEquals(newLocation, underTest.location(stack)),
				()->assertEquals(newLocation, underTest.location(Drakir_Grebb))
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
	void testGetState() {
		CounterLocationsState state = underTest.getState();
		
		List<CounterLocationState> locationList = state.counterLocations();
		assertEquals(1, locationList.size());
		List<String> countersResult = locationList.get(0).counters().stream().map(CounterState::asString).toList();
		List<String> expectedResult = countersList.stream().map(Counter::toString).toList();
		assertThat(countersResult, containsInAnyOrder(expectedResult.toArray()));
	}
	
	@Disabled("Not implemented sufficiently yet.")
	@Test
	void testSetState() {
		CounterLocations underTest2 = new CounterLocations(gameBox);
		
		CounterLocationsState state = underTest.getState();
		underTest2.setState(state);
		assertEquals(underTest, underTest2);
	}
	
	private Collection<Counter> toCounter(Collection<PlacedCounter<Counter>> placed) {
		return placed.stream().map(PlacedCounter::counter).toList();
	}
	
	@SuppressWarnings("unchecked")
	private void shouldBeEquivilent(Collection<Counter> expected, Collection<Counter> actual) {
		Predicate<Counter> isStack = c->c instanceof Stack;
		Predicate<Counter> notStack = Predicate.not(isStack);
		Set<Counter> expectedCounters = toStream(expected, notStack).collect(Collectors.toSet());
		Set<Counter> actualCounters =  toStream(actual, notStack).collect(Collectors.toSet());
		assertEquals(expectedCounters, actualCounters);
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
