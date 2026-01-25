package io.github.rmcdouga.fitg.tui4jviewer.view;

import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.Color;

public class ColorsAndStyles {
	// Colors chosen from ANSI 256 color chart: https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
	// Better examples here: https://hexdocs.pm/color_palette/ansi_color_codes.html

	// Use some colors to represent different environ types.
	private static final Color COLOR_RED = Color.color("#ff0000"); // ANSI Red #9
	private static final Color COLOR_TRUE_BLUE = Color.color("#005fd7"); // ANSI True Blue #26
	private static final Color COLOR_SKY_BLUE = Color.color("#00ff00"); // ANSI Sky Blue #45
	private static final Color COLOR_SEA_GREEN = Color.color("#87af87"); // ANSI Sea Green #108
	private static final Color COLOR_PASTEL_GREEN = Color.color("#87d787"); // ANSI Pastel Green #114
	private static final Color COLOR_VIVID_ORANGE = Color.color("#ff5f00"); // ANSI Vivid Orange #202
	
	// Use some colors to represent different faction types.
	private static final Color COLOR_STRONG_YELLOW = Color.color("#d7d700");	// ANSI Yellow #184
	private static final Color COLOR_STRONG_RED = Color.color("#d70000"); 		// ANSI Red #160

	// Environ Styles
	final static Style STYLE_AIR = Style.newStyle().foreground(COLOR_SKY_BLUE);
	final static Style STYLE_LIQUID = Style.newStyle().foreground(COLOR_SEA_GREEN);
	final static Style STYLE_FIRE = Style.newStyle().foreground(COLOR_VIVID_ORANGE);
	final static Style STYLE_SUBTERRANEAN = Style.newStyle().foreground(COLOR_RED);
	final static Style STYLE_WILD = Style.newStyle().foreground(COLOR_PASTEL_GREEN);
	final static Style STYLE_URBAN = Style.newStyle().foreground(COLOR_TRUE_BLUE);

	// Faction Styles
	final static Style STYLE_IMPERIAL = Style.newStyle().foreground(COLOR_STRONG_YELLOW);
	final static Style STYLE_REBEL = Style.newStyle().foreground(COLOR_STRONG_RED);

}
