package com.rogers.rmcdouga.fitg.basegame.command;

import java.util.HashSet;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

/**
 * This class is responsible for tracking the MoveCommands issued by the user. 
 * It maintains a record of all Counters that have been moved so that the CommandValidator can
 * ensure that each Counter only moves once.
 */
public class MoveCommandTracker {
	private final Set<Counter> movedCounters = new HashSet<>();

	public void track(Command command) {
		switch (command) {
			case Command.MoveCommand moveCommand -> recordCounter(moveCommand.getCounter());
		}
	}

	private void recordCounter(Counter counter) {
		movedCounters.add(counter);
	}
	
	public boolean hasMoved(Counter counter) {
		return movedCounters.contains(counter);
	}
}
