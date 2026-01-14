package io.github.rmcdouga.fitg.tui4jviewer.view;

import java.util.List;
import java.util.stream.Collectors;

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
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

public class MainView  implements Model {
	
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

        // do nothing for other messages
        return UpdateResult.from(this);
	}

	@Override
	public String view() {
		return formatPlanet(221) + "\n" +
			   formatPlanet(222) + "\n" +
			   formatPlanet(223);
	}
	private String formatPlanet(int Id) {
		return formatPlanet(planetFinder.findById(Id).orElseThrow());
	}
	
	private static String formatPlanet(Planet planet) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s (ID: %d) in %s - ", planet.getName(), planet.getId(), planet.getStarSystem().getName()));
		sb.append(String.format("%s", formatEnvirons(planet.listEnvirons())));
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
				case Air -> "☁️";
				case Fire -> "🔥";
				case Liquid -> "💧";
				case Subterranian -> "🕳";
				case Urban -> "🏢";
				case Wild -> "🏔";
			};
			
		} else {
			throw new IllegalArgumentException("Unknown EnvironType: " + type.getClass().getName());
		}
	}
}
