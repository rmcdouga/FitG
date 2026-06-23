package com.rogers.rmcdouga.fitg.basegame.command.api.internal;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public sealed interface Command {

	public sealed interface MoveCommand extends Command  {
		
		Counter getCounter();

		record CharacterMove(Character character, Location destination) implements MoveCommand {
			@Override public Counter getCounter() { return character; }
		};
		
		record StackMove(Stack stack, Location destination) implements MoveCommand {
			@Override public Counter getCounter() { return stack; }
		};

		record UnitMove(Unit unit, Location destination) implements MoveCommand {
			@Override public Counter getCounter() { return unit; }
		};
	};
	
	// TODO: Add InitiateCombatCommand
	// TODO: Add PerformSearchCommand
	// TODO: Add AssignMissionsCommand, ResolveMissionsCommand
}
