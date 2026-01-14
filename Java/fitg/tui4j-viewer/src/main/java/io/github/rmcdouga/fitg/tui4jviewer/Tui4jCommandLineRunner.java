package io.github.rmcdouga.fitg.tui4jviewer;

import com.williamcallahan.tui4j.compat.bubbletea.Program;

import io.github.rmcdouga.fitg.tui4jviewer.view.MainView;

import org.springframework.boot.CommandLineRunner;

/**
 * Example Spring Boot command line runner for TUI4J.
 */
public class Tui4jCommandLineRunner implements CommandLineRunner {

    private final MainView tui4jApplication;

    public Tui4jCommandLineRunner(MainView tui4jApplication) {
        this.tui4jApplication = tui4jApplication;
    }

    @Override
    public void run(String... args) throws Exception {
        new Program(tui4jApplication)
                .withAltScreen()
                .run();
    }
}
