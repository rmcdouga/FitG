package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public class FlightToEgrixRebelStrategy extends AbstractStrategy {

	@Override
	public Collection<StarSystem> setPdbs(Collection<StarSystem> map) {
		return map;	// Nothing for the Rebel player to do.
	}

	@Override
	public CounterLocations placeCounters(CounterLocations counterLocations, Collection<Counter> counters) {
		return counterLocations;	// Nothing for the Rebel player to do.
	}

}
