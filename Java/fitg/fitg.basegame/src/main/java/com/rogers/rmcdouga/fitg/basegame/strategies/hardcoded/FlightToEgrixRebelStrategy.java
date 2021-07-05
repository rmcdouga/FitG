package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public class FlightToEgrixRebelStrategy extends AbstractStrategy {

	@Override
	public PlayerDecisions setPdbs(Collection<StarSystem> map, PdbManager pdbManager) {
		return this;	// Nothing for the Rebel player to do.
	}

	@Override
	public PlayerDecisions placeCounters(CounterLocations counterLocations, Collection<Counter> counters) {
		return this;	// Nothing for the Rebel player to do.
	}

}
