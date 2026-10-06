package com.rogers.rmcdouga.fitg.basegame.command;

import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;

/**
 * This class accepts commands and then validates them against the rules.  
 * It serves as the central point for validating incoming commands.
 */
public class CommandValidator {
	private final MoveCommandTracker moveCommandTracker;

	public CommandValidator(MoveCommandTracker moveCommandTracker) {
		this.moveCommandTracker = moveCommandTracker;
	}

	public void validate(Command command) {
		// TODO: Implement additional validation logic based on game rules
		switch (command) {
			case Command.MoveCommand moveCommand -> validateMoveCommand(moveCommand);
		}
	}

	private void validateMoveCommand(MoveCommand moveCommand) {
		if (moveCommandTracker.hasMoved(moveCommand.getCounter())) {
			throw new MoveCommandValidationException("Counter has already moved this turn: " + moveCommand.getCounter());
		}
	}

	@SuppressWarnings("serial")
	public static class ValidationException extends RuntimeException {
		public ValidationException(String message, Throwable cause) {
			super(message, cause);
		}

		public ValidationException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	public static class MoveCommandValidationException extends ValidationException {
		public MoveCommandValidationException(String message, Throwable cause) {
			super(message, cause);
		}

		public MoveCommandValidationException(String message) {
			super(message);
		}
	}
	
}
