package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

/**
 * This is a data structure that maintains the locations of counters on the map
 *
 */
public class CounterLocations implements GameState, CounterLocator {
	
	private final HashSetValuedHashMap<Location, Counter> locationMap = new HashSetValuedHashMap<>();
	private final Map<Counter, Location> counterMap = new HashMap<>();
	private final StackManager stackMgr = new StackManager();
	private final GameBox gameBox;

	public CounterLocations(GameBox gameBox) {
		this.gameBox = gameBox;
	}
	
	/**
	 * Adds a new Counter to the map.
	 * 
	 * @param counter
	 * @param location
	 * @return
	 */
	private CounterLocations add(Counter counter, Location location) {
		if (counterMap.containsKey(counter)) {
			throw new UnsupportedOperationException("Counter (" + counter + ") is already on the map, use move() instead.");
		}
		counterMap.put(counter, location);
		locationMap.put(location, counter);
		return this;
	}

	public <T extends Counter> Optional<PlacedCounter<T>> placeCounter(Location location, T counter) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(transferStack(counter)).map(c->placeRetrievedCounter(location, c));
	}

	public <T extends Counter> Optional<PlacedCounter<Stack>> placeCounter(Location location, @SuppressWarnings("unchecked") T... counters) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(stackMgr, counters).map(c->placeRetrievedCounter(location, c));
	}

	public <T extends Counter> Optional<PlacedCounter<SpaceshipStack>> placeCounter(Location location, Spaceship spaceship, @SuppressWarnings("unchecked") T... counters) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(stackMgr, spaceship, counters).map(c->placeRetrievedCounter(location, c));
	}

	// Having gotten a counter from the GameBox, this places it (i.e. gives it a location).
	private <T extends Counter> PlacedCounter<T> placeRetrievedCounter(Location location, T counter) {
		this.add(counter, location);
		return new PlacedCounter<T>(counter);
	}
	
	@SuppressWarnings("unchecked")
	private  <T extends Counter> T transferStack(T maybeStack) {
		return maybeStack instanceof Stack definitelyStack ? (T) stackMgr.transfer(definitelyStack) : maybeStack;
	}

	/**
	 * Moves an existing counter to a new location
	 * 
	 * @param counter
	 * @param newLocation
	 * @return
	 */
	public CounterLocations move(Counter counterParam, Location newLocation) {
		Counter counter = fixIfStack(counterParam);
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

	// If the passed in counter is a stack , it may be a stack created with another StackManager.  If so, we find and return the equivilent stack 
	// within this stack manager.
	private Counter fixIfStack(Counter counter) {
		return counter instanceof Stack stack 
				? stackMgr.find(stack)
						  .map(Counter.class::cast)
						  .orElseThrow(()->new IllegalArgumentException("Couldn't find stack (" + stack + ")."))
				: counter;
	}
	
	/**
	 * Returns a Collection of all the counters at a particular location on the map.
	 * 
	 * This routine produces a "shallow" list of counters (i.e. stacks at a location are not expanded
	 * and so the counters within a stack do not directly appear in the returned Collection).
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
	 * This routine is a "deep" search which means that if a counter is in a stack, then this routine returns
	 * the stack's location.
	 * 
	 * @param counter
	 * @return
	 */
	public  Optional<Location> locationOf(Counter counterParam) {
		// If there's a stack containing the counter, then get the stack's location rather than the counter's location.
		Counter counter = fixIfStack(counterParam);
		Counter stackOrCounter = stackMgr.stackContaining(counter)
										 .map(Counter.class::cast)
										 .orElse(counter);
				
		return Optional.ofNullable(counterMap.get(stackOrCounter));
	}

	@Override
	public Stream<Location> locationOfByType(Counter counterType) {
		return counterMap.entrySet().stream()
									.filter(e->e.getKey().isA(counterType))
									.map(Map.Entry::getValue);
	}	

	public class PlacedCounter<T extends Counter> {
		private final T counter;

		private PlacedCounter(T counter) {
			this.counter = counter;
		}
		
		public T counter() {
			return counter;
		}

		public Location location() {
			return getEnclosingInstance().locationOf(counter).orElseThrow(()->new NullPointerException("Couldn't find counter (" + counter + ")."));
		}
		
		public PlacedCounter<T> move(Location newLocation) {
			getEnclosingInstance().move(counter, newLocation);
			return this;
		}
		
		public void returnToBox() {
			gameBox.returnToBox(this.counter);
		}

		public void removeFromPlay() {
			gameBox.removeFromPlay(this.counter);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(counter);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			PlacedCounter other = (PlacedCounter) obj;
			if (!(getEnclosingInstance() == other.getEnclosingInstance()))
				return false;
			return Objects.equals(counter, other.counter);
		}

		private CounterLocations getEnclosingInstance() {
			return CounterLocations.this;
		}
	}

	/**
	 * Remove the counter from the map.
	 * 
	 * @param counter
	 * @return
	 */
	public CounterLocations remove(Counter counterParam) {
		Counter counter = fixIfStack(counterParam);
		Location counterLocation = counterMap.remove(counter);
		if (counterLocation == null) {
			throw new IllegalArgumentException("Couldn't find counter (" + counterParam + ").");
		}
		if (locationMap.removeMapping(counterLocation, counter) == false) {
			// This should never happen. 
			throw new IllegalStateException("Couldn't find counter's location (" + counterParam + "/" + counterLocation + ").");
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

	@Override
	public Optional<Counter> stackContaining(Counter counter) {
		return stackMgr.stackContaining(counter).map(Counter.class::cast);
	}
}
