package io.github.rmcdouga.shell_viewer.view;


import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

import net.fellbaum.jemoji.Emojis;

public class BaseGamePlanetRenderer {
	private static final Logger log = LoggerFactory.getLogger(BaseGamePlanetRenderer.class);

	private final Game game;
	private final BiFunction<ZoomLevel, Environ, AttributedString> environRenderer;
	private final BiFunction<ZoomLevel, Collection<Counter>, AttributedString> counterRenderer;
	
	BaseGamePlanetRenderer(Game game) {
		this(game, BaseGameEnvironRenderer::renderEnviron, BaseGameCounterRenderer::renderCounters);
	}

	// Used for unit testing
	BaseGamePlanetRenderer(Game game, 
			BiFunction<ZoomLevel, Environ, AttributedString> environRenderer,
			BiFunction<ZoomLevel, Collection<Counter>, AttributedString> counterRenderer) {
		this.game = game;
		this.environRenderer = environRenderer;
		this.counterRenderer = counterRenderer;
	}

	AttributedString renderPlanet(ZoomLevel zoomLevel, Planet planet) {
		return formatPlanet_Planetary(zoomLevel, planet);
	}

	private AttributedString formatPlanet_Planetary(ZoomLevel zoomLevel, Planet planet) {
		AttributedStringBuilder sb = new AttributedStringBuilder();
		sb.append("Planet: ");
		sb.append(String.format("%s (ID: %d) %s %s ", planet.getName(), planet.getId(), formatLoyalty(game.getLoyalty(planet)), formatPdb(game.getPdb(planet))));
		sb.append(formatEnvirons(zoomLevel, planet.listEnvirons()));
		return sb.toAttributedString();
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

	private AttributedString formatEnvirons(ZoomLevel zoomLevel, List<? extends Environ> environs) {
		final AttributedString bar = new AttributedString("|");
		// TODO: How to show couunters, stacks, etc.?
		var formattedEnvirons = AttributedString.join(bar, ()->environs.stream()
													  					.map(e->renderEnviron(zoomLevel, e))
													  					.iterator());
		return concat(bar, formattedEnvirons, bar);
	}

	private AttributedString renderEnviron(ZoomLevel zoomLevel, Environ environ) {
		return concat(environRenderer.apply(zoomLevel, environ),
					  new AttributedString(" "),
					  counterRenderer.apply(zoomLevel, game.countersAt(environ)));
	}
	
	private AttributedString concat(AttributedString... parts) {
		AttributedStringBuilder result = new AttributedStringBuilder();
		for (AttributedString part : parts) {
			result.append(part);
		}
		return result.toAttributedString();
	}
	
}