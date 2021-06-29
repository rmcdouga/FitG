package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

/**
 * This is a data structure that maintains the locations of counters on the map
 *
 */
public class CounterLocations implements GameState {
	
	private HashSetValuedHashMap<Location, Counter> locationMap = new HashSetValuedHashMap<>();
	private Map<Counter, Location> counterMap = new HashMap<>();

	/**
	 * Adds a new Counter to the map.
	 * 
	 * @param counter
	 * @param location
	 * @return
	 */
	public CounterLocations add(Counter counter, Location location) {
		if (counterMap.containsKey(counter)) {
			throw new UnsupportedOperationException("Counter (" + counter + ") is already on the map, use move() instead.");
		}
		counterMap.put(counter, location);
		locationMap.put(location, counter);
		return this;
	}
	
	/**
	 * Moves an existing counter to a new location
	 * 
	 * @param counter
	 * @param newLocation
	 * @return
	 */
	public CounterLocations move(Counter counter, Location newLocation) {
		Location oldLocation = counterMap.replace(counter, newLocation);
		if (oldLocation == null) {
			throw new IllegalArgumentException("Couldn't find counter (" + counter + ").");
		} else if (locationMap.containsMapping(oldLocation, counter)) {
			locationMap.removeMapping(oldLocation, counter);
		    locationMap.put(newLocation, counter);
		} else {
			// This should never happen. 
			throw new IllegalStateException("Couldn't find counter (" + counter + ") at location (" + oldLocation + ") when moving to location (" + newLocation + ").");
		}
		return this;
	}
	
	/**
	 * Returns a Collection of all the counters at a particular location on the map.
	 * 
	 * @param location
	 * @return
	 */
	public Collection<Counter> countersAt(Location location) {
		return locationMap.get(location);
	}

	/**
	 * Returns the location of a particular counter.
	 * 
	 * @param counter
	 * @return
	 */
	public Location location(Counter counter) {
		return counterMap.get(counter);
	}

	/**
	 * Remove the counter from the map.
	 * 
	 * @param counter
	 * @return
	 */
	public CounterLocations remove(Counter counter) {
		Location counterLocation = counterMap.remove(counter);
		if (counterLocation == null) {
			throw new IllegalArgumentException("Couldn't find counter (" + counter + ").");
		}
		if (locationMap.removeMapping(counterLocation, counter) == false) {
			// This should never happen. 
			throw new IllegalStateException("Couldn't find counter's location (" + counter + "/" + counterLocation + ").");
		}
		return this;
	}

	@Override
	public Map<String, Object> getState() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public void setState(Map<String, Object> state) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
