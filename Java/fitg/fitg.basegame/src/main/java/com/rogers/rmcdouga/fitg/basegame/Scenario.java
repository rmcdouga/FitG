package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.SetPdbInstructions;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public interface Scenario {
	public Collection<StarSystem> createMap();
	public Collection<SetPdbInstructions> setupPdbs(PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions);
	public Collection<PlaceCountersInstruction> setupCounters(CounterPool counterPool, StackManager stackMgr, PlayerDecisions rebelDecisons, PlayerDecisions imperialDecisions);
	
	public Type type();
	public Rules rules();
	public int numberOfGameTurns();

	public interface PlayerDecisions {
		public record SetPdbInstructions(Planet p, Pdb pdb) {};
		public record PlaceCountersInstruction(Counter counter, Location location) {};
		
		/**
		 * Generates a set of instructions for the initial PDB levels and status
		 * 
		 * @param map - Star Systems to be used to set PDBs
		 * @return
		 */
		Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map);

		/**
		 * Generates a set of instructions for the initial placement of counters
		 * 
		 * @param counters - Set of counters to be placed.  If several counters need to be placed together, then they should be passed
		 *                   in as a stack.
		 * @return 
		 */
		Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr);

		/**
		 * Generates a collection of movement commands during the Movement Segment of this player's turn.
		 * 
		 * @return Movement commands to be actioned during the Movement Segment of this player's turn.
		 */
		Collection<MoveCommand> movementSegment();
		
		/**
		 * Maybe generates a Move command as a reaction to an opponents detected characters or military units. 
		 * 
		 * This is called once per environ containing an opponents detected character or military unit, 
		 * and is called after the opponent has made their move.  It allows the non-phasing player to
		 * move one character or unit (optionally with their leader) from an environ on the same planet
		 * to the environ in question.
		 * 
		 * @param environ - the environ containing the opponents detected character or military unit
		 * @return An optional MoveCommand.  If the player does not want to move, then an empty 
		 * Optional should be returned.
		 */
		Optional<MoveCommand> reactionMove(Environ environ);
	}
	
	public enum Type {
		StartRebellion, Armegeddon;
	}
	
	public enum Rules {
		StarSystem, Province, Galactic;
	}
}
