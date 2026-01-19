package com.rogers.rmcdouga.fitg.cli;

import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;

@Component
public class GameCommands {

	@Command(name = "create-game", help = "Creates a new game.")
	public String createGame() {
		return "Done.";
	}
}
