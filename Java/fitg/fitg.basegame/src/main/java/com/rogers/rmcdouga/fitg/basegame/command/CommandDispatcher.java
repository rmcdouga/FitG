package com.rogers.rmcdouga.fitg.basegame.command;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;

/**
 * This class accepts commands and then dispatches them to the appropriate handlers.  
 * It serves as the central point for command processing in the game engine.
 * 
 * This class assumes that any incoming commands have already been validated by the CommandValidator class, and it does not perform any validation itself.
 */
public class CommandDispatcher {
	private final Game game;
	
	public CommandDispatcher(Game game) {
		this.game = game;
		super();
	}

	public void dispatch(Command command) {
		switch (command) {
			case Command.MoveCommand moveCommand -> handleMoveCommand(moveCommand);
		}
	}

	private void handleMoveCommand(MoveCommand moveCommand) {
		switch (moveCommand) {
			case MoveCommand.CharacterMove characterMove -> game.move(characterMove.character(), characterMove.destination());
			case MoveCommand.StackMove stackMove -> game.move(stackMove.stack(), stackMove.destination());
			case MoveCommand.UnitMove unitMove -> game.move(unitMove.unit(), unitMove.destination());
		}
	}
}
