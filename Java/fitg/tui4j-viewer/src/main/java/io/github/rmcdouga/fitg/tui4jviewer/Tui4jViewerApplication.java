package io.github.rmcdouga.fitg.tui4jviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.rmcdouga.fitg.tui4jviewer.view.MainView;

@SpringBootApplication
public class Tui4jViewerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(Tui4jViewerApplication.class, args);
	}

	@Bean
	public Tui4jCommandLineRunner tui4jCommandLineRunner() {
		return new Tui4jCommandLineRunner(new MainView());
	}
}
