package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public interface Scenario {
	public Collection<StarSystem> createMap();
	public CounterLocations setupCounters(CounterLocations counterLocations, CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions);
	
	public Type type();
	public Rules rules();
	public int numberOfGameTurns();

	public interface PlayerDecisions {
		Collection<StarSystem> setPdbs(Collection<StarSystem> map);
		CounterLocations placeCounters(CounterLocations counterLocations, Collection<Counter> counters);	
	}
	
	public enum Type {
		StartRebellion, Armegeddon;
	}
	
	public enum Rules {
		StarSystem, Province, Galactic;
	}
}
