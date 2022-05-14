package com.rogers.rmcdouga.fitg.svgviewer;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

@SpringBootApplication
public class SvgViewerApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(SvgViewerApplication.class);

	private static final Path MAIN_RESOURCES_DIR = Paths.get("src", "main", "resources");
	private static final Path TEST_RESOURCES_DIR = Paths.get("src", "test", "resources");
	
	public static void main(String[] args) {
		SpringApplication.run(SvgViewerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Fill in this method with mainline
		log.info("Inside mainline.");
		String svgDocument = drawMap();
		Files.writeString(TEST_RESOURCES_DIR.resolve("actualResults").resolve("FitgMap.svg"), svgDocument);
		log.info("Mainline complete.");
	}

	public String drawMap() throws IOException {
		var g2 = new SVGGraphics2D(Map.MAP_WIDTH, Map.MAP_HEIGHT);
		Game game = createGame();
		new Map(g2, game, game).draw();
		return g2.getSVGDocument();
	}
	
	// TODO:  Change this to read a game from a GameState.
	private static Game createGame() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		return Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
	}
}
