package io.github.rmcdouga.shell_viewer.view;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.jline.utils.AttributedString;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

class BaseGameEnvironRendererTest {

	@ParameterizedTest
	@CsvSource({
		"Adare, 0, ■, 4",
	})
	void testRenderEnvironZoomLevelEnviron(BaseGamePlanet planet, int environIndex, String expectedSymbol, int expectedSize) {
		AttributedString result = BaseGameEnvironRenderer.renderEnviron(null, planet.environ(0));
		// IO.println("%s(%d) ->'%s'".formatted(planet.toString(), environIndex, result.toString()));
		assertThat(result.toString(), allOf(containsString(expectedSymbol), containsString(Integer.toString(expectedSize))));
		
	}

}
