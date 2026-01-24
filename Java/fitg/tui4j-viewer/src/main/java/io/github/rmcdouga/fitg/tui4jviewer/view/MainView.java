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
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.NoColor;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.WindowSizeMessage;

public class MainView  implements Model {

	private static final Logger log = LoggerFactory.getLogger(MainView.class);
	
	
	private static final Color COLOR_RED = Color.color("#ff0000"); // ANSI Red #9
	private final static Style SELECTION = Style.newStyle().foreground(COLOR_RED);
    private final static Style NO_COLOR = Style.newStyle().foreground(new NoColor());
	
	private final Game game;
	private final StarSystemFinder starSystemFinder;
	private final PlanetFinder planetFinder;
	private ZoomLevel currentZoomLevel = ZoomLevel.PLANETARY;

	public MainView(Game game, StarSystemFinder starSystemFinder, PlanetFinder planetFinder) {
		this.game = game;
		this.starSystemFinder = starSystemFinder;
		this.planetFinder = planetFinder;
	}

	@Override
	public Command init() {
		// Don't need to do anything special on init.
		return null;
	}

	@Override
	public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            return switch (keyPressMessage.key()) {
                case "q", "Q" -> UpdateResult.from(this, QuitMessage::new);
                default -> UpdateResult.from(this);
            };
        }

        if (msg instanceof WindowSizeMessage windowSizeMessage) {
        				log.atInfo().log("Window resized to {} cols x {} rows",
					windowSizeMessage.width(),
					windowSizeMessage.height());
			return UpdateResult.from(this);
        }
        log.atInfo().log("Received message of type: {}", msg.getClass().getName());
        // do nothing for other messages
        return UpdateResult.from(this);
	}

	@Override
	public String view() {
		return formatStarSystem(22) + "\n" +
			   formatStarSystem(11) + "\n" +
			   formatStarSystem(14);
	}
	
	private String formatStarSystem(int Id) {
		return formatStarSystem(starSystemFinder.findById(Id).orElseThrow());
	}
	
	private String formatStarSystem(StarSystem starSystem) {
		StringBuilder sb = new StringBuilder();
		sb.append(SELECTION.render("StarSystem: "));
		sb.append(String.format("%s (ID: %d) - ", starSystem.getName(), starSystem.getId()));
		for (Planet planet : starSystem.listPlanets()) {
			sb.append("\n");
			sb.append(indent(formatPlanet(planet), 2));
		}
		return sb.toString();
	}

	private static String indent(String str, int spaces) {
		String indent = " ".repeat(spaces);
		return indent + str.replace("\n", "\n" + indent);
	}
	
	private String formatPlanet(int Id) {
		return formatPlanet(planetFinder.findById(Id).orElseThrow());
	}
	
	private String formatPlanet(Planet planet) {
		log.atInfo().addArgument(planet.getName()).log("Formatting planet: {}");
		log.atInfo().addArgument(game.getPdb(planet).toString()).log("  Pdb: {}");
		StringBuilder sb = new StringBuilder();
		sb.append(SELECTION.render("Planet: "));
		sb.append(String.format("%s (ID: %d) in %s - ", planet.getName(), planet.getId(), planet.getStarSystem().getName()));
		sb.append(String.format("%s", formatEnvirons(planet.listEnvirons())));
		return sb.toString();
	}

	private String formatEnvirons(List<? extends Environ> environs) {
		// TODO: How to show couunters, stacks, etc.?
		return environs.stream()
				.map(e->renderEnviron(currentZoomLevel, e) + " " + renderCounters(currentZoomLevel, game.countersAt(e)))
				.collect(Collectors.joining("|", "|", "|"));
	}
	
}
