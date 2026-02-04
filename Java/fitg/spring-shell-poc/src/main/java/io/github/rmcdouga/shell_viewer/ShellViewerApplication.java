package io.github.rmcdouga.shell_viewer;

import java.io.PrintStream;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.core.command.Command;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
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
	
	public static void main(String[] args) throws Throwable {
//		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//			// Change System.out to use UTF-8
//			log.debug("Running on Windows OS - setting console code page to UTF-8");
//			setConsoleCodePage();
//			System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//		} else {
//			log.debug("Not running on Windows OS - no need to set console code page");
//		}

		SpringApplication.run(ShellViewerApplication.class, args);
	}
	
	static void setConsoleCodePage() throws Throwable {
		Linker linker = Linker.nativeLinker();
        SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32.dll", Arena.global());

        // Define function signatures: BOOL SetConsoleCP(UINT wCodePageID)
        FunctionDescriptor desc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);
        MethodHandle setConsoleCP = linker.downcallHandle(kernel32.find("SetConsoleCP").get(), desc);
        MethodHandle setConsoleOutputCP = linker.downcallHandle(kernel32.find("SetConsoleOutputCP").get(), desc);

        // Set Code Page to 65001 (UTF-8)
        int cpUtf8 = 65001;
        setConsoleCP.invoke(cpUtf8);
        setConsoleOutputCP.invoke(cpUtf8);
 	}
	
//	@Command
//	public void displayGame(MainView mainView) {
//		System.out.println(mainView.displayGame());
//	}
	
	@Bean
	Command displayGame(MainView mainView) {
	    return Command.builder()
	            .name("displayGame")
	            .execute(_ -> {
	            	log.info("Executing displayGame command");
	            	return mainView.displayGame().toAnsi();
	           });
	}

	@Bean
	public MainView mainView(Game game, StarSystemFinder starSystemFinder, PlanetFinder planetFinder) {
		return new MainView(game, starSystemFinder, planetFinder);
	}
	
	@Bean
	public BaseGameScenario scenario() {
		return BaseGameScenario.GalacticGame;	// Set to galactic game to get a full map
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