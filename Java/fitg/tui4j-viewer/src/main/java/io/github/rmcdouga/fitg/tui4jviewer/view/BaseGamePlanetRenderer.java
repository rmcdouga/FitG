package io.github.rmcdouga.fitg.tui4jviewer.view;

import static io.github.rmcdouga.fitg.tui4jviewer.view.BaseGameCounterRenderer.renderCounters;
import static io.github.rmcdouga.fitg.tui4jviewer.view.BaseGameEnvironRenderer.renderEnviron;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

public class BaseGamePlanetRenderer {
	private static final Logger log = LoggerFactory.getLogger(BaseGamePlanetRenderer.class);

	private final Game game;
	
	BaseGamePlanetRenderer(Game game) {
		this.game = game;
	}

	String renderPlanet(ZoomLevel zoomLevel, Planet planet) {
		return formatPlanet_Planetary(zoomLevel, planet);
	}

	private String formatPlanet_Planetary(ZoomLevel zoomLevel, Planet planet) {
		StringBuilder sb = new StringBuilder();
		sb.append("Planet: ");
		sb.append(String.format("%s (ID: %d) PDB is %s ", planet.getName(), planet.getId(), game.getPdb(planet)));
		sb.append(String.format("%s", formatEnvirons(zoomLevel, planet.listEnvirons())));
		return sb.toString();
	}

	private String formatEnvirons(ZoomLevel zoomLevel, List<? extends Environ> environs) {
		// TODO: How to show couunters, stacks, etc.?
		return environs.stream()
				.map(e->renderEnviron(zoomLevel, e) + " " + renderCounters(zoomLevel, game.countersAt(e)))
				.collect(Collectors.joining("|", "|", "|"));
	}

}
