package io.github.rmcdouga.fitg.tui4jviewer.view;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;

import net.fellbaum.jemoji.Emojis;

public class BaseGameEnvironRenderer {

	private static final String BLACK_UP_POINTING_TRIANGLE = "\u25B2";
	private static final String BLACK_SQUARE = "\u25A0";
	private static final String WHITE_DIAMOND = "\u25C7";
	private static final String LARGE_CIRCLE = "\u25EF";
	private static final String BLACK_PENTAGON = "\u2B1F";
	private static final String BLACK_SHOGI_PIECE = "\u2617";
	private static final String MOUNTAIN = "\u26F0";
	private static final String BLACK_DROPLET = "\u1F322";

	
	// Prevent instantiation
	private BaseGameEnvironRenderer() {
	}

	public enum BaseGameEnvironTypeRenderer {
		AIR(BaseGameEnvironType.Air, ColorsAndStyles.STYLE_AIR, LARGE_CIRCLE),  						// Emojis.CLOUD.getEmoji(); // "☁️";
		LIQUID(BaseGameEnvironType.Liquid, ColorsAndStyles.STYLE_LIQUID, Emojis.DROPLET.getEmoji()), 	// Emojis.DROPLET.getEmoji(); //  "💧";
		FIRE(BaseGameEnvironType.Fire, ColorsAndStyles.STYLE_FIRE, WHITE_DIAMOND),					// Emojis.FIRE.getEmoji(); // "🔥";
		SUBTERRANEAN(BaseGameEnvironType.Subterranian, ColorsAndStyles.STYLE_SUBTERRANEAN, BLACK_SHOGI_PIECE),// Emojis.HOLE.getEmoji(); // "🕳";
		WILD(BaseGameEnvironType.Wild, ColorsAndStyles.STYLE_WILD, BLACK_UP_POINTING_TRIANGLE),		// Emojis.MOUNTAIN.getEmoji(); // "🏔";
		URBAN(BaseGameEnvironType.Urban, ColorsAndStyles.STYLE_URBAN, BLACK_SQUARE);					// Emojis.OFFICE_BUILDING.getEmoji(); //  "🏢";
		
		private final BaseGameEnvironType environType;
		private final Style style;
		private final String symbol;

		private BaseGameEnvironTypeRenderer(BaseGameEnvironType environType, Style style, String symbol) {
			this.environType = environType;
			this.style = style;
			this.symbol = symbol;
		}

		static BaseGameEnvironTypeRenderer from(BaseGameEnvironType environType) {
			return switch (environType) {
			case BaseGameEnvironType.Air -> AIR;
			case BaseGameEnvironType.Liquid -> LIQUID;
			case BaseGameEnvironType.Fire -> FIRE;
			case BaseGameEnvironType.Subterranian -> SUBTERRANEAN;
			case BaseGameEnvironType.Wild -> WILD;
			case BaseGameEnvironType.Urban -> URBAN;
			};
		}
	};

	static String renderEnviron(ZoomLevel zoomLevel, Environ environ) {
		return renderEnviron(zoomLevel, BaseGameEnviron.requireBgEnviron(environ));
	}

	static String renderEnviron(ZoomLevel zoomLevel, BaseGameEnviron environ) {
		BaseGameEnvironType bgEnvironType = BaseGameEnvironType.requireBgEnvironType(environ.getType());
		BaseGameEnvironTypeRenderer renderer = BaseGameEnvironTypeRenderer.from(bgEnvironType);
		
		return renderer.style.render("%s %s".formatted(renderer.symbol, environ.getSize()));
	}
}
