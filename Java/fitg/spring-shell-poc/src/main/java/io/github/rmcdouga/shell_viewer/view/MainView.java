package io.github.rmcdouga.shell_viewer.view;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;

public class MainView {

	private final Game game;
	private final StarSystemFinder starSystemFinder;
	private final PlanetFinder planetFinder;
	private final BaseGamePlanetRenderer planetRenderer;;
	private ZoomLevel currentZoomLevel = ZoomLevel.PLANETARY;

	public MainView(Game game, StarSystemFinder starSystemFinder, PlanetFinder planetFinder) {
		this.game = game;
		this.starSystemFinder = starSystemFinder;
		this.planetFinder = planetFinder;
		this.planetRenderer = new BaseGamePlanetRenderer(game);
	}

	public void displayGame(Terminal terminal) {
		view().println(terminal);
	}

	public AttributedString view() {
		final AttributedString nl = new AttributedString("\n");
		final AttributedStringBuilder sb = new AttributedStringBuilder();
		return sb.append(formatStarSystem(22))
				 .append(nl) 
				 .append(formatStarSystem(11))
				 .append(nl) 
				 .append(formatStarSystem(14))
				 .toAttributedString();
	}
	
	private AttributedString formatStarSystem(int id) {
		return formatStarSystem(starSystemFinder.findById(id).orElseThrow());
	}
	
	private AttributedString formatStarSystem(StarSystem starSystem) {
		AttributedStringBuilder sb = new AttributedStringBuilder();
		sb.append("StarSystem: ");
		sb.append(String.format("%s (ID: %d) - ", starSystem.getName(), starSystem.getId()));
		for (Planet planet : starSystem.listPlanets()) {
			sb.append("\n");
			sb.append(planetRenderer.renderPlanet(currentZoomLevel, planet));
		}
		return sb.toAttributedString();
	}

	private static String indent(String str, int spaces) {
		String indent = " ".repeat(spaces);
		return indent + str.replace("\n", "\n" + indent);
	}
}
