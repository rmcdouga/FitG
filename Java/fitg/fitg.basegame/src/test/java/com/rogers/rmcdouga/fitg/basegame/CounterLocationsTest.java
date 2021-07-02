package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

class CounterLocationsTest {

	private final StackManager stackMgr = new StackManager();
	private CounterLocations underTest = new CounterLocations(stackMgr);
	private final Stack stack = stackMgr.of(Doctor_Sontag, Drakir_Grebb);
	private final List<Counter> counters = List.of(Adam_Starlight, Agan_Rafa, stack);
	private final BaseGameEnviron location = Adare.listEnvirons().get(0);
	
	@BeforeEach
	void setup() {
		counters.forEach(c->underTest.add(c, location));
	}
	
	@Test
	void testCountersAt() {
		Collection<Counter> result = underTest.countersAt(location);

		assertNotNull(result);
		assertAll(
				()->assertEquals(counters.size(),result.size()),
				()->assertTrue(counters.containsAll(result)),
				()->assertTrue(result.containsAll(counters))
				);
	}

	@Test
	void testLocation_present() {
		Optional<Location> result = underTest.location(Agan_Rafa);
		
		assertTrue(result.isPresent());
		assertEquals(location, result.get());
	}

	@Test
	void testLocation_notPresent() {
		Optional<Location> result = underTest.location(Professor_Mareg);
		
		assertTrue(result.isEmpty());
	}

	@Test
	void testLocation_presentInStack() {
		Optional<Location> result = underTest.location(Doctor_Sontag);
		
		assertTrue(result.isPresent());
		assertEquals(location, result.get());
	}

	@Test
	void testMove() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(Agan_Rafa, newLocation);
		assertNotNull(result);
		assertAll(
				()->assertEquals(Set.of(Adam_Starlight, stack), Set.of(underTest.countersAt(location).toArray())),
				()->assertIterableEquals(List.of(Agan_Rafa), underTest.countersAt(newLocation))
				);
	}
	
	@Test
	void testMove_stack() {
		Location newLocation = Adare.listEnvirons().get(1);
		
		CounterLocations result = underTest.move(stack, newLocation);
		assertNotNull(result);
		assertAll(
				()->assertIterableEquals(List.of(Adam_Starlight, Agan_Rafa), underTest.countersAt(location)),
				()->assertIterableEquals(List.of(stack), underTest.countersAt(newLocation)),
				()->assertEquals(newLocation, underTest.location(stack).get()),
				()->assertEquals(newLocation, underTest.location(Drakir_Grebb).get())
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
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, ()->underTest.add(Adam_Starlight, Adare.environ(0)));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertEquals("Counter (Adam_Starlight) is already on the map, use move() instead.", msg);
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

}
