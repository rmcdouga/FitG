package com.rogers.rmcdouga.fitg.cli;

import org.springframework.shell.command.annotation.Command;

@Command()
public class GameCommands {

	@Command()
	public String createGame() {
		return "Done.";
	}
}
