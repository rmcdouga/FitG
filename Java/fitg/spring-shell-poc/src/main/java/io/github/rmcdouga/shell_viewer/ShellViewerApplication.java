package io.github.rmcdouga.shell_viewer;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.core.command.Command;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.DoNothingStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

import io.github.rmcdouga.shell_viewer.view.MainView;

@SpringBootApplication
public class ShellViewerApplication {
	private static final Logger log = LoggerFactory.getLogger(ShellViewerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ShellViewerApplication.class, args);
	}
	
//	@Command
//	public void displayGame(MainView mainView) {
//		System.out.println(mainView.displayGame());
//	}
	
	@Bean
	Command displayGame(MainView mainView) {
	    return Command.builder()
	            .name("displayGame")
	            .execute(context -> {
	            	log.info("Executing displayGame command");
//	            	System.out.println(mainView.displayGame());
	                context.outputWriter().println(mainView.displayGame());
	                context.outputWriter().flush();
	            });
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
						new SetPdbInstructions(BaseGamePlanet.Quibron, Pdb.UP_1),	// Level 1 at Quibron
						new SetPdbInstructions(BaseGamePlanet.Angoff, Pdb.UP_2),	// Level 2 at Angoff
						new SetPdbInstructions(BaseGamePlanet.Charkhan, Pdb.UP_0)	// Level 0 at Charkhan
						);
			}
			
			@Override
			public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
				return x.placeCounters(counters, stackMgr);
			}
		};
	}	
}