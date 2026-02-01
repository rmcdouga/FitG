package io.github.rmcdouga.shell_viewer.view;

import org.jline.utils.AttributedStyle;

public class ColorsAndStyles {
	// Colors chosen from ANSI 256 color chart: https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
	// Better examples here: https://hexdocs.pm/color_palette/ansi_color_codes.html

	// Use some colors to represent different environ types.
	private static final int COLOR_RED = 0xff0000; 			// ANSI Red #9
	private static final int COLOR_TRUE_BLUE = 0x005fd7;	// ANSI True Blue #26
	private static final int COLOR_SKY_BLUE = 0x00ff00; 	// ANSI Sky Blue #45
	private static final int COLOR_SEA_GREEN = 0x87af87; 	// ANSI Sea Green #108
	private static final int COLOR_PASTEL_GREEN = 0x87d787; // ANSI Pastel Green #114
	private static final int COLOR_VIVID_ORANGE = 0xff5f00; // ANSI Vivid Orange #202
	private static final int COLOR_WHITE = 0xffffff; 		// ANSI White #15
	
	// Use some colors to represent different faction types.
	private static final int COLOR_STRONG_YELLOW = 0xd7d700;	// ANSI Yellow #184
	private static final int COLOR_STRONG_RED = 0xd70000; 		// ANSI Red #160

	// Environ Styles
	final static AttributedStyle STYLE_AIR = AttributedStyle.DEFAULT.foregroundRgb(COLOR_SKY_BLUE);
	final static AttributedStyle STYLE_LIQUID = AttributedStyle.DEFAULT.foregroundRgb(COLOR_SEA_GREEN);
	final static AttributedStyle STYLE_FIRE = AttributedStyle.DEFAULT.foregroundRgb(COLOR_VIVID_ORANGE);
	final static AttributedStyle STYLE_SUBTERRANEAN = AttributedStyle.DEFAULT.foregroundRgb(COLOR_RED);
	final static AttributedStyle STYLE_WILD = AttributedStyle.DEFAULT.foregroundRgb(COLOR_PASTEL_GREEN);
	final static AttributedStyle STYLE_URBAN = AttributedStyle.DEFAULT.foregroundRgb(COLOR_TRUE_BLUE);

	// Faction Styles
	final static AttributedStyle STYLE_IMPERIAL = AttributedStyle.DEFAULT.foregroundRgb(COLOR_STRONG_YELLOW);
	final static AttributedStyle STYLE_REBEL = AttributedStyle.DEFAULT.foregroundRgb(COLOR_STRONG_RED);
	final static AttributedStyle STYLE_NEUTRAL = AttributedStyle.DEFAULT.foregroundRgb(COLOR_WHITE);

}