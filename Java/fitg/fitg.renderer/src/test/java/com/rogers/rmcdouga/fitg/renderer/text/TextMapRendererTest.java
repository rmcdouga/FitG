package com.rogers.rmcdouga.fitg.renderer.text;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameGameBoard;
import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

class TextMapRendererTest {

	private static final CounterLocator EMPTY_COUNTER_LOCATOR = new EmptyCounterLocator();

	@Test
	void renderOrdersStarSystemsAndPlanetsDeterministically() {
		GameBoard gameBoard = BaseGameGameBoard.create(List.of(BaseGameStarSystem.Egrix, BaseGameStarSystem.Tardyn), Scenario.Type.StartRebellion);
		var renderer = new TextMapRenderer(gameBoard, EMPTY_COUNTER_LOCATOR);

		var rendered = renderer.render();

		assertThat(rendered)
					.contains("star-system: 11 Tardyn", "star-system: 22 Egrix");
		int tardinIndex = rendered.indexOf("star-system: 11 Tardyn");
		int egrixIndex = rendered.indexOf("star-system: 22 Egrix");
		assertThat(tardinIndex)
					.as("Tardyn should appear before Egrix")
					.isLessThan(egrixIndex);

		int mimulusIndex = rendered.indexOf("planet: 111 Mimulus");
		int magroIndex = rendered.indexOf("planet: 112 Magro");
		assertThat(mimulusIndex)
					.as("Mimulus should appear before Magro")
					.isLessThan(magroIndex);
	}

	@Test
	void renderIncludesPlanetStateAndEnvironMetadata() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.render();

		assertThat(rendered).contains("planet: 222 Angoff");
		assertThat(rendered).contains("loyalty: Neutral");
		assertThat(rendered).contains("control: RebelControlled");
		assertThat(rendered).contains("pdb: UP_2");
		assertThat(rendered).contains("orbit: []");
		assertThat(rendered).contains("environ: Urban | size=6 | resources=9* | coup=3 | races=[Yester*]");
		assertThat(rendered).contains("environ: Wild | size=4 | resources=6* | coup=- | races=[Saurian*]");
	}

	@Test
	void renderExpandsSpaceshipStacks() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.render();

		assertThat(rendered).contains("spaceship-stack[imperialspaceship; passengers=[jonkidu, vanskatiea]]");
		assertThat(rendered)
				.as("counters line should include spaceship stack")
				.contains("counters=[line, spaceship-stack[imperialspaceship; passengers=[jonkidu, vanskatiea]]]");
	}

	@Test
	void renderCompactSuppressesEmptyFieldsAndAbbreviates() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.renderCompact();

		assertThat(rendered).contains("[22 Egrix]");
		assertThat(rendered).contains("[222 Angoff] loyalty=Neutral ctrl=Rebel pdb=2↑");
		assertThat(rendered).contains("[221 Quibron] loyalty=Loyal ctrl=Imperial pdb=1↑");
		assertThat(rendered).contains("[223 Charkhan] loyalty=Patriotic ctrl=Imperial pdb=0↑");
		assertThat(rendered).contains("Urban sz=6 res=9* coup=3 Yester* cr=Laboroid");
		assertThat(rendered).contains("imperialspaceship[jonkidu vanskatiea]");
		assertThat(rendered).contains(" :: ");
		assertThat(rendered).doesNotContain("drift");
	}

	@Test
	void renderCompactSovereignAndCreatureUsePrefixes() {
		GameBoard gameBoard = BaseGameGameBoard.create(
				List.of(BaseGameStarSystem.Varu), Scenario.Type.StartRebellion);
		var renderer = new TextMapRenderer(gameBoard, EMPTY_COUNTER_LOCATOR);

		var rendered = renderer.renderCompact();

		GameBoard luineBoard = BaseGameGameBoard.create(
				List.of(BaseGameStarSystem.Luine), Scenario.Type.StartRebellion);
		var luineRenderer = new TextMapRenderer(luineBoard, EMPTY_COUNTER_LOCATOR);
		var luineRendered = luineRenderer.renderCompact();

		assertThat(luineRendered).contains("sov=Balgar");
	}

	@Test
	void renderMarkdownIncludesProperFormatting() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.renderMarkdown();

		assertThat(rendered).startsWith("# FitG Map");
		assertThat(rendered).contains("## Star System: 22 Egrix");
		assertThat(rendered).contains("### Planet: 222 Angoff");
		assertThat(rendered).contains("- **Loyalty:** Neutral");
		assertThat(rendered).contains("- **Control:** RebelControlled");
		assertThat(rendered).contains("- **PDB:** UP_2");
		assertThat(rendered).contains("#### Environment: Urban");
		assertThat(rendered).contains("- **Size:** 6");
		assertThat(rendered).contains("- **Resources:** 9*");
		assertThat(rendered).contains("- **Coup:** 3");
		assertThat(rendered).contains("- **Races:** [Yester*]");
	}

	private static Game createFlightToEgrixGame() {
		return Game.createGame(BaseGameScenario.FlightToEgrix, new FlightToEgrixRebelStrategy(), new FlightToEgrixImperialStrategy());
	}

	private static final class EmptyCounterLocator implements CounterLocator {

		@Override
		public List<Counter> countersAt(Location location) {
			return List.of();
		}

		@Override
		public CounterLocator move(Counter counter, Location destination) {
			throw new UnsupportedOperationException();
		}

		@Override
		public java.util.Optional<Location> locationOf(Counter counter) {
			return java.util.Optional.empty();
		}

		@Override
		public Stream<Location> locationOfByType(Counter counter) {
			return Stream.empty();
		}

		@Override
		public java.util.Optional<Counter> stackContaining(Counter counter) {
			return java.util.Optional.empty();
		}
	}
}
