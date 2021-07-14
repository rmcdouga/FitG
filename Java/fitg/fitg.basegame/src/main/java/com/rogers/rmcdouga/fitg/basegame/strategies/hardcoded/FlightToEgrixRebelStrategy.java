package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class FlightToEgrixRebelStrategy extends AbstractStrategy {

	@Override
	public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
		throw new UnsupportedOperationException("Rebels have not decisions to be made in FlightToEgeix scenario");
	}

	@Override
	public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
		throw new UnsupportedOperationException("Rebels have not decisions to be made in FlightToEgeix scenario");
	}

}
