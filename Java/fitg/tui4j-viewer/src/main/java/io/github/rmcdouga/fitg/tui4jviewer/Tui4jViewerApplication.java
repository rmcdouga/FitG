package io.github.rmcdouga.fitg.tui4jviewer;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.GalacticGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;

import io.github.rmcdouga.fitg.tui4jviewer.view.MainView;

@SpringBootApplication
public class Tui4jViewerApplication {
	
	public Tui4jViewerApplication(GitConfig gitConfig) {
		gitConfig.logGitInformation();
	}

	public static void main(String[] args) {
		SpringApplication.run(Tui4jViewerApplication.class, args);
	}

	@Bean
	public BaseGameScenario scenario() {
		return GalacticGame;
	}	

	@Bean
	public Tui4jCommandLineRunner tui4jCommandLineRunner(MainView mainView) {
		return new Tui4jCommandLineRunner(mainView);
	}
	
	@Bean
	public MainView mainView(Game game, StarSystemFinder starSystemFinder, PlanetFinder planetFinder) {
		return new MainView(game, starSystemFinder, planetFinder);
	}
}
