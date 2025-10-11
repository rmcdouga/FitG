package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public interface CounterLocator {

	/**
	 * Returns the collection of counters at the given location.  If there are no counters at that location, an empty collection is returned.
	 * 
	 * @param location
	 * @return
	 */
	Collection<Counter> countersAt(Location location);

	/**
	 * Moves the given counter to the given destination location.
	 * 
	 * @param counter
	 * @param destination
	 * @return
	 */
	CounterLocator move(Counter counter, Location destination);
	
	/**
	 * Returns the location of the given counter, or throws an exception if the counter is not currently placed on the board.
	 * 
	 * @param counter
	 * @return
	 */
	Location locationOf(Counter counter);

	/**
	 * Returns the location of the given counter, or throws an exception if the counter is not currently placed on the board.
	 * 
	 * @param counter
	 * @return
	 */
	Stream<Location> locationOfByType(Counter counter);
}