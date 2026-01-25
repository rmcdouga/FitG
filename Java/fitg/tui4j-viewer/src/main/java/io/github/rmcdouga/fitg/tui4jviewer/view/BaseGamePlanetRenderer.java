package io.github.rmcdouga.fitg.tui4jviewer.view;

import static io.github.rmcdouga.fitg.tui4jviewer.view.BaseGameCounterRenderer.renderCounters;
import static io.github.rmcdouga.fitg.tui4jviewer.view.BaseGameEnvironRenderer.renderEnviron;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

import net.fellbaum.jemoji.Emojis;

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
		sb.append(String.format("%s (ID: %d) %s %s ", planet.getName(), planet.getId(), formatLoyalty(game.getLoyalty(planet)), formatPdb(game.getPdb(planet))));
		sb.append(String.format("%s", formatEnvirons(zoomLevel, planet.listEnvirons())));
		return sb.toString();
	}

	private static String formatLoyalty(LoyaltyType loyalty) {
		return switch (BaseGameLoyaltyType.requireBglt(loyalty)) {
			case Patriotic -> Emojis.WHITE_FLAG.getEmoji(); // "\u2691"; // flag
			case Loyal -> Emojis.WHITE_HEART.getEmoji();	// "\u2665" black heart suit  (or "\u2764", heavy black heart)
			case Neutral -> Emojis.HANDSHAKE.getEmoji(); //  new String(Character.toChars(0x1F91D)); // hand shake
			case Dissent -> Emojis.BROKEN_HEART.getEmoji(); // new String(Character.toChars(0x1F494)); // broken heart
			case Unrest -> Emojis.RAISED_FIST.getEmoji(); // "\u270A"; // Fist
		};
	}

	private static String formatPdb(Pdb pdb) {
		return switch (pdb) {
			case DOWN_0 -> "↓0";
			case DOWN_1 -> "↓1";
			case DOWN_2 -> "↓2";
			case UP_0 -> "↑0";
			case UP_1 -> "↑1";
			case UP_2 -> "↑2";
		};
	}

	private String formatEnvirons(ZoomLevel zoomLevel, List<? extends Environ> environs) {
		// TODO: How to show couunters, stacks, etc.?
		return environs.stream()
				.map(e->renderEnviron(zoomLevel, e) + " " + renderCounters(zoomLevel, game.countersAt(e)))
				.collect(Collectors.joining("|", "|", "|"));
	}

}
