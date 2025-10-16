package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.*;
import static com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.SetPdbInstructions;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public enum BaseGameScenario implements Scenario {
	FlightToEgrix(Type.StartRebellion, Rules.StarSystem, 6) {

		@Override
		public Collection<StarSystem> createMap() {
			return List.of(Egrix);
		}

		@Override
		public Collection<SetPdbInstructions> setupPdbs() {
			return List.of(
					new SetPdbInstructions(Quibron, Pdb.UP_1),	// Level 1 at Quibron
					new SetPdbInstructions(Angoff, Pdb.UP_2),	// Level 2 at Angoff
					new SetPdbInstructions(Charkhan, Pdb.UP_0)	// Level 0 at Charkhan
					);
		}
		
		@Override
		public Collection<PlaceCountersInstruction> setupCounters(CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions) {
			
			Stack rebels = stackMgr.of(BaseGameRebelSpaceship.Planetary_Privateer, Boccanegra, Doctor_Sontag, Frun_Sentel);
			Collection<PlaceCountersInstruction> rebelInstructions = List.of(new PlaceCountersInstruction(rebels, IN_SPACE));

			// Player will place these:
			// Jon_Kidu, Vans_Ka_Tia_A, Imperial_Spaceship
			// One Line, three Patrol and three Militia
			Collection<PlaceCountersInstruction> imperialInstructions = imperialDecisions.placeCounters(List.of(Jon_Kidu, Vans_Ka_Tie_A, Imperial_Spaceship, Line, Patrol, Patrol, Patrol, Militia, Militia, Militia), stackMgr);
			// Remove remaining Imperial Spaceships from play
			counterPool.removeFromPlay((ImperialSpaceship)counterPool.getSpaceship(Imperial_Spaceship).get());
			counterPool.removeFromPlay((ImperialSpaceship)counterPool.getSpaceship(Imperial_Spaceship).get());
			counterPool.removeFromPlay((ImperialSpaceship)counterPool.getSpaceship(Redjacs_Spaceship).get());
			return Stream.concat(rebelInstructions.stream(), imperialInstructions.stream()).toList();
		}

	},
	// TODO:  Insert other scenarios here
	GalacticGame(Type.StartRebellion, Rules.Galactic, 6) {

		@Override
		public Collection<StarSystem> createMap() {
			return List.of(BaseGameStarSystem.values());
		}

		@Override
		public Collection<SetPdbInstructions> setupPdbs() {
			// TODO Auto-generated method stub
			return List.of();
		}
		
		@Override
		public Collection<PlaceCountersInstruction> setupCounters(CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions) {
			// TODO:  Set up Counter Locations
			
			return List.of();
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
