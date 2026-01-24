package io.github.rmcdouga.fitg.tui4jviewer.view;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;

import net.fellbaum.jemoji.Emojis;

public class BaseGameEnvironRenderer {
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
		AIR(BaseGameEnvironType.Air, STYLE_AIR, LARGE_CIRCLE),  						// Emojis.CLOUD.getEmoji(); // "☁️";
		LIQUID(BaseGameEnvironType.Liquid, STYLE_LIQUID, Emojis.DROPLET.getEmoji()), 	// Emojis.DROPLET.getEmoji(); //  "💧";
		FIRE(BaseGameEnvironType.Fire, STYLE_FIRE, WHITE_DIAMOND),					// Emojis.FIRE.getEmoji(); // "🔥";
		SUBTERRANEAN(BaseGameEnvironType.Subterranian, STYLE_SUBTERRANEAN, BLACK_SHOGI_PIECE),// Emojis.HOLE.getEmoji(); // "🕳";
		WILD(BaseGameEnvironType.Wild, STYLE_WILD, BLACK_UP_POINTING_TRIANGLE),		// Emojis.MOUNTAIN.getEmoji(); // "🏔";
		URBAN(BaseGameEnvironType.Urban, STYLE_URBAN, BLACK_SQUARE);					// Emojis.OFFICE_BUILDING.getEmoji(); //  "🏢";
		
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
