package io.github.rmcdouga.fitg.tui4jviewer.view;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.jline.utils.AttributedStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Renderer;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.color.TerminalColor;
import com.williamcallahan.tui4j.term.TerminalInfo;
import com.williamcallahan.tui4j.term.TerminalInfoProvider;

class BaseGameEnvironRendererTest {

	private static final String AIR_SYMBOL = "◯";
	private static final String LIQUID_SYMBOL = "💧";
	private static final String FIRE_SYMBOL = "◇";
	private static final String SUBTERRANEAN_SYMBOL = "☗";
	private static final String WILD_SYMBOL = "▲";
	private static final String URBAN_SYMBOL = "■";
	
	@SuppressWarnings("unused")
	private static final List<Environ> allEnvirons = List.of(
			BaseGameEnviron.air(1).build(),
			BaseGameEnviron.fire(2).build(),
			BaseGameEnviron.liquid(3).build(),
			BaseGameEnviron.subterranian(4).build(),
			BaseGameEnviron.wild(5).build(),
			BaseGameEnviron.urban(6).build()
			);

	private static final List<String> allSymbols = List.of(
			AIR_SYMBOL,
			FIRE_SYMBOL,
			LIQUID_SYMBOL,
			SUBTERRANEAN_SYMBOL,
			WILD_SYMBOL,
			URBAN_SYMBOL
			);

	@BeforeAll
	static void setupOnce() {
		setupTUI4JTerminal();
	}

	@ParameterizedTest
	@FieldSource("allEnvirons")
	void testRenderEnvironEnviron(Environ environ) {
		String result = BaseGameEnvironRenderer.renderEnviron(ZoomLevel.PLANETARY, environ);
		assertAll(
				()->assertThat(result, is(not(emptyString()))),
				()->assertThat(result, containsString(Integer.toString(environ.getSize()))),
				()->assertThat(result, containsString(allSymbols.get(environ.getSize() - 1)))
				);
	}

	private static void setupTUI4JTerminal() {
		TerminalInfo.provide(new TerminalInfoProvider() {
			
			@Override
			public TerminalInfo provide() {
				return new TerminalInfo(false, new TerminalColor() {
					
					@Override
					public AttributedStyle applyAsForeground(AttributedStyle style, Renderer renderer) {
						throw new UnsupportedOperationException();
					}
					
					@Override
					public AttributedStyle applyAsBackground(AttributedStyle style, Renderer renderer) {
						throw new UnsupportedOperationException();
					}
				});
			};
		});
	}
}
