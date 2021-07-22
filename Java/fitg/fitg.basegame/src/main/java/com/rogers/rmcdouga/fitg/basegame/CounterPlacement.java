package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class CounterPlacement {
	private final CounterLocations counterLocations;
	private final GameBox gameBox;
	
	CounterPlacement(CounterLocations counterLocations, GameBox gameBox) {
		super();
		this.counterLocations = counterLocations;
		this.gameBox = gameBox;
	}

	public <T extends Counter> Optional<PlacedCounter<T>> placeCounter(Location location, T counter) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(counter).map(c->placeRetrievedCounter(location, c));
	}

	public <T extends Counter> Optional<PlacedCounter<Stack>> placeCounter(Location location, StackManager stackMgr, @SuppressWarnings("unchecked") T... counters) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(stackMgr, counters).map(c->placeRetrievedCounter(location, c));
	}

	public <T extends Counter> Optional<PlacedCounter<SpaceshipStack>> placeCounter(Location location, StackManager stackMgr, Spaceship spaceship, @SuppressWarnings("unchecked") T... counters) {
		// Get the counter from the GameBox and then place it.
		return gameBox.get(stackMgr, spaceship, counters).map(c->placeRetrievedCounter(location, c));
	}

	// Having gotten a counter from the GameBox, this places it (i.e. gives it a location).
	private <T extends Counter> PlacedCounter<T> placeRetrievedCounter(Location location, T counter) {
		counterLocations.add(counter, location);
		return new PlacedCounter<T>(counter);
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
			return counterLocations.location(counter).get();
		}
		
		public PlacedCounter<T> move(Location newLocation) {
			counterLocations.move(counter, newLocation);
			return this;
		}
		
		public void returnToBox() {
			gameBox.returnToBox(this.counter);
		}

		public void removeFromPlay() {
			gameBox.removeFromPlay(this.counter);
		}
	}

}
