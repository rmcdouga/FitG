package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public interface Scenario {
	public Collection<StarSystem> createMap();
	public CounterLocations setupCounters(CounterLocations counterLocations, CounterPool counterPool);
	
}
