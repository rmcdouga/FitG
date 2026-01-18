package io.github.rmcdouga.fitg.tui4jviewer.view;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Environ.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.NoColor;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

import io.github.rmcdouga.fitg.tui4jviewer.GitConfig;
import net.fellbaum.jemoji.Emojis;

public class MainView  implements Model {
	private static final String BLACK_UP_POINTING_TRIANGLE = "\u25B2";

	private static final String BLACK_SQUARE = "\u25A0";

	private static final String WHITE_DIAMOND = "\u25C7";

	private static final String LARGE_CIRCLE = "\u25EF";

	private static final Logger log = LoggerFactory.getLogger(MainView.class);
	
	// Use some colors to represent different environ types.
	// Colors chosen from ANSI 256 color chart: https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
	// Better examples here: https://hexdocs.pm/color_palette/ansi_color_codes.html
	private static final Color COLOR_RED = Color.color("#ff0000"); // ANSI Red #9
	private static final Color COLOR_TRUE_BLUE = Color.color("#005fd7"); // ANSI True Blue #26
	private static final Color COLOR_SKY_BLUE = Color.color("#00ff00"); // ANSI Sky Blue #45
	private static final Color COLOR_SEA_GREEN = Color.color("#87af87"); // ANSI Sea Green #108
	private static final Color COLOR_PASTEL_GREEN = Color.color("#87d787"); // ANSI Pastel Green #114
	private static final Color COLOR_VIVID_ORANGE = Color.color("#ff5f00"); // ANSI Vivid Orange #202
	private final static Style STYLE_AIR = Style.newStyle().foreground(COLOR_SKY_BLUE);
	private final static Style STYLE_LIQUID = Style.newStyle().foreground(COLOR_SEA_GREEN);
	private final static Style STYLE_FIRE = Style.newStyle().foreground(COLOR_VIVID_ORANGE);
	private final static Style STYLE_SUBTERRANEAN = Style.newStyle().foreground(COLOR_RED);
	private final static Style STYLE_WILD = Style.newStyle().foreground(COLOR_PASTEL_GREEN);
	private final static Style STYLE_URBAN = Style.newStyle().foreground(COLOR_TRUE_BLUE);

	
	private final static Style SELECTION = Style.newStyle().foreground(COLOR_RED);
    private final static Style NO_COLOR = Style.newStyle().foreground(new NoColor());
	
	private final Game game;
	private final PlanetFinder planetFinder;

	public MainView(Game game, PlanetFinder planetFinder) {
		this.game = game;
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
        
        log.atInfo().log("Received message of type: {}", msg.getClass().getName());
        // do nothing for other messages
        return UpdateResult.from(this);
	}

	@Override
	public String view() {
		return formatPlanet(221) + "\n" +
			   formatPlanet(222) + "\n" +
			   formatPlanet(223);
	}
	
//	private String formatStarSystem(int Id) {
//		return formatStarSystem(game.getStarSystemFinder().findById(Id).orElseThrow());
//	}
	
	private String formatPlanet(int Id) {
		return formatPlanet(planetFinder.findById(Id).orElseThrow());
	}
	
	private static String formatPlanet(Planet planet) {
		StringBuilder sb = new StringBuilder();
		sb.append(SELECTION.render("Planet: "));
		sb.append(String.format("%s (ID: %d) in %s - ", planet.getName(), planet.getId(), planet.getStarSystem().getName()));
		sb.append(String.format("%s", STYLE_FIRE.render(formatEnvirons(planet.listEnvirons()))));
		return sb.toString();
	}

	private static String formatEnvirons(List<? extends Environ> environs) {
		// TODO: How to show couunters, stacks, etc.?
		return environs.stream()
				.map(environ -> formatEnvironType(environ.getType()))
				.collect(Collectors.joining("|", "|", "|"));
	}
		
	private static String formatEnvironType(EnvironType type) {
		if (type instanceof BaseGameEnvironType basegameType) {
			return switch (basegameType) {
				case Air -> STYLE_AIR.render(LARGE_CIRCLE); // Emojis.CLOUD.getEmoji(); // "☁️";
				case Fire -> STYLE_FIRE.render(WHITE_DIAMOND); // Emojis.FIRE.getEmoji(); // "🔥";
				case Liquid -> STYLE_LIQUID.render(LARGE_CIRCLE); // Emojis.DROPLET.getEmoji(); //  "💧";
				case Subterranian -> STYLE_SUBTERRANEAN.render(LARGE_CIRCLE); // Emojis.HOLE.getEmoji(); // "🕳";
				case Urban -> STYLE_URBAN.render(BLACK_SQUARE); // Emojis.OFFICE_BUILDING.getEmoji(); //  "🏢";
				case Wild -> STYLE_WILD.render(BLACK_UP_POINTING_TRIANGLE); // Emojis.MOUNTAIN.getEmoji(); // "🏔";
			};
			
		} else {
			throw new IllegalArgumentException("Unknown EnvironType: " + type.getClass().getName());
		}
	}
}
