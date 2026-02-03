package io.github.rmcdouga.shell_viewer.view;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGamePlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

class BaseGamePlanetRendererTest {

	private static final Game GAME = Game.createGame(BaseGameScenario.FlightToEgrix, new FlightToEgrixRebelStrategy(), new FlightToEgrixImperialStrategy());
	private static final PlanetFinder PF = new BaseGamePlanetFinder(GAME);
	
	private static AttributedString mockEnvironRenderer(ZoomLevel zoomLevel, Environ environ) {
		return new AttributedString("MockEnviron");
	}

	private static AttributedString mockCounterRenderer(ZoomLevel zoomLevel, Collection<Counter> counters) {
		return new AttributedString("MockCounters");
	}
	
	private final BaseGamePlanetRenderer underTest = new BaseGamePlanetRenderer(GAME, 
			BaseGamePlanetRendererTest::mockEnvironRenderer,
			BaseGamePlanetRendererTest::mockCounterRenderer);
	
	@Test
	void testRenderPlanet() {
		AttributedString result = underTest.renderPlanet(ZoomLevel.PLANETARY, PF.findById(221).orElseThrow());
		// System.out.println(result.toAnsi());
		assertThat(result.toString(), allOf(containsString("Planet: Quibron (ID: 221)"), 
											containsString("🤍"), // white heart for Loyal
											containsString("↑1"),
											containsString("MockEnviron"),
											containsString("MockCounters")
										 ));
	}

}
