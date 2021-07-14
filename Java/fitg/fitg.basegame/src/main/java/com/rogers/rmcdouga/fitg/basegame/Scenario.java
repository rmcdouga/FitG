package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.SetPdbInstructions;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public interface Scenario {
	public Collection<StarSystem> createMap();
	public Collection<SetPdbInstructions> setupPdbs();
	public Collection<PlaceCountersInstruction> setupCounters(CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions);
	
	public Type type();
	public Rules rules();
	public int numberOfGameTurns();

	public interface PlayerDecisions {
		public record SetPdbInstructions(Planet p, Pdb pdb) {};
		public record PlaceCountersInstruction(Counter counter, Location location) {};
		
		Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map);
		/**
		 * Generates a set of instructions for the initial placement of counters
		 * 
		 * @param counters - Set of counters to be placed.  If several counters need to be placed together, then they should be passed
		 *                   in as a stack.
		 * @return 
		 */
		Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr);
	}
	
	public enum Type {
		StartRebellion, Armegeddon;
	}
	
	public enum Rules {
		StarSystem, Province, Galactic;
	}
}
