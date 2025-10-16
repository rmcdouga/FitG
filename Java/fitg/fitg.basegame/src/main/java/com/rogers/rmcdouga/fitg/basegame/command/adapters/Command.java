package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public sealed interface Command {

	public sealed interface MoveCommand extends Command  {
		record CharacterMove(Character character, Location destination) implements MoveCommand {};
		record StackMove(Stack stack, Location destination) implements MoveCommand {};
		record UnitMove(Unit unit, Location destination) implements MoveCommand {};
	};
}
