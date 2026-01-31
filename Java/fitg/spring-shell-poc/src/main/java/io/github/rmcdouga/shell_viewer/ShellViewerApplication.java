package io.github.rmcdouga.shell_viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.core.command.annotation.Command;

@SpringBootApplication
public class ShellViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShellViewerApplication.class, args);
	}
	
	@Command
	public void displayGame() {
		System.out.println("Hello world!");
	}
}