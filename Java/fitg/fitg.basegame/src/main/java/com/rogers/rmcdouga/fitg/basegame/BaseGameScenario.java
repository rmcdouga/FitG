package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public enum BaseGameScenario implements Scenario {
	FlightToEgrix(Type.StartRebellion, Rules.StarSystem, 6) {

		@Override
		public Collection<StarSystem> createMap() {
			// TODO:  Set the PDBs
			return List.of(Egrix);
		}

		@Override
		public CounterLocations setupCounters(CounterLocations counterLocations, CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions) {
			
			Stack rebels = stackMgr.of(BaseGameSpaceship.Planetary_Privateer, Boccanegra, Doctor_Sontag, Frun_Sentel);
			counterLocations.add(rebels, IN_SPACE);

			// This is temporary - player will place these
			Stack imperials = stackMgr.of(Jon_Kidu, Vans_Ka_Tia_A);	// plus one spaceship chosen at random
			// One Line, three Patrol and three Militia
			imperialDecisions.placeCounters(counterLocations, List.of(imperials));
			return counterLocations;
		}
		
	},
	// TODO:  Insert other scenarios here
	GalacticGame(Type.StartRebellion, Rules.Galactic, 6) {

		@Override
		public Collection<StarSystem> createMap() {
			// TODO:  Set the PDBs
			return List.of(BaseGameStarSystem.values());
		}

		@Override
		public CounterLocations setupCounters(CounterLocations counterLocations, CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions) {
			// TODO:  Set up Counter Locations
			
			return counterLocations;
		}
		
	}

	;
	
	public static final Location IN_SPACE = new Location() {};	// Used by Star System Scenarios 
	
	private final Type type;
	private final Rules rules;
	private final int numberOfGameTurns;

	private BaseGameScenario(Type type, Rules rules, int numberOfTurns) {
		this.type = type;
		this.rules = rules;
		this.numberOfGameTurns = numberOfTurns;
	}

	public Type type() {
		return type;
	}
	public Rules rules() {
		return rules;
	}
	public int numberOfGameTurns() {
		return numberOfGameTurns;
	}
}
