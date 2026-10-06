package com.rogers.rmcdouga.fitg.basegame.command;

import java.util.List;
import java.util.function.Consumer;

import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;

/**
 * This class is responsible for processing user commands and dispatching them to all the 
 * appropriate handlers. It serves as a bridge between the user interface and the command 
 * execution logic.
 */
public class UserCommandProcessing {
	private final List<Consumer<Command>> commandHandlers;

	public UserCommandProcessing(CommandValidator commandValidator,
								 CommandDispatcher commandDispatcher, 
								 MoveCommandTracker moveCommandTracker
								 ) {		
		this(List.of(
				command -> commandValidator.validate(command),		// Validation must occur before changing game state
				command -> commandDispatcher.dispatch(command),		// Modifies game state, so must occur after validation
				command -> moveCommandTracker.track(command)		// Must occur after validation (used by validation) and probably after game state changes
				));
	}

	private UserCommandProcessing(List<Consumer<Command>> commandHandlers) {
		this.commandHandlers = commandHandlers;
	}
	
	public void processCommand(Command command) {
		commandHandlers.forEach(handler->handler.accept(command));
	}
}
