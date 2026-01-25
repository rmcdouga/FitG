package io.github.rmcdouga.fitg.tui4jviewer;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.GalacticGame;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.Angoff;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.Charkhan;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.Quibron;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.DoNothingStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

import io.github.rmcdouga.fitg.tui4jviewer.view.MainView;

@SpringBootApplication
public class Tui4jViewerApplication {
	private static final Logger log = LoggerFactory.getLogger(Tui4jViewerApplication.class);

	public Tui4jViewerApplication(GitConfig gitConfig) {
		gitConfig.logGitInformation();
	}

	public static void main(String[] args) {
		SpringApplication.run(Tui4jViewerApplication.class, args);
	}

	@Bean
	public BaseGameScenario scenario() {
		return GalacticGame;	// Set to galactic game to get a full map
	}	

	@Bean
	public Tui4jCommandLineRunner tui4jCommandLineRunner(MainView mainView) {
		return new Tui4jCommandLineRunner(mainView);
	}
	
	@Bean
	public MainView mainView(Game game, StarSystemFinder starSystemFinder, PlanetFinder planetFinder) {
		return new MainView(game, starSystemFinder, planetFinder);
	}
	
	@Bean(name = "rebelDecisions")
	public Scenario.PlayerDecisions rebelDecisions() {
		return new DoNothingStrategy();
	}
	
	@Bean(name = "imperialDecisions")
	public Scenario.PlayerDecisions imperialDecisions() {
		log.info("Providing Imperial Decisions with PDB settings for Flight To Egrix Game");
		return new Scenario.PlayerDecisions() {	// Anonymous class to delegate to FlightToEgrixImperialStrategy, adding the PDB setting that would be in FlightToEgrixScenario.
			private final Scenario.PlayerDecisions x = new FlightToEgrixImperialStrategy();
			
			@Override
			public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
				log.info("Setting Imperial PDB levels for Flight To Egrix Game");
				return List.of(
						new SetPdbInstructions(Quibron, Pdb.UP_1),	// Level 1 at Quibron
						new SetPdbInstructions(Angoff, Pdb.UP_2),	// Level 2 at Angoff
						new SetPdbInstructions(Charkhan, Pdb.UP_0)	// Level 0 at Charkhan
						);
			}
			
			@Override
			public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
				return x.placeCounters(counters, stackMgr);
			}
		};
	}	

}
