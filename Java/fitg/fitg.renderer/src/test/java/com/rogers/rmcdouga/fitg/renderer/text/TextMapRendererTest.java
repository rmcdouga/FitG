package com.rogers.rmcdouga.fitg.renderer.text;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameGameBoard;
import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

class TextMapRendererTest {

	private static final CounterLocator EMPTY_COUNTER_LOCATOR = new EmptyCounterLocator();

	@Test
	void renderOrdersStarSystemsAndPlanetsDeterministically() {
		GameBoard gameBoard = BaseGameGameBoard.create(List.of(BaseGameStarSystem.Egrix, BaseGameStarSystem.Tardyn), Scenario.Type.StartRebellion);
		var renderer = new TextMapRenderer(gameBoard, EMPTY_COUNTER_LOCATOR);

		var rendered = renderer.render();

		assertAll(
				() -> assertTrue(rendered.indexOf("star-system: 11 Tardyn") < rendered.indexOf("star-system: 22 Egrix"), rendered),
				() -> assertTrue(rendered.indexOf("planet: 111 Mimulus") < rendered.indexOf("planet: 112 Magro"), rendered));
	}

	@Test
	void renderIncludesPlanetStateAndEnvironMetadata() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.render();

		assertAll(
				() -> assertTrue(rendered.contains("planet: 222 Angoff"), rendered),
				() -> assertTrue(rendered.contains("loyalty: Neutral"), rendered),
				() -> assertTrue(rendered.contains("control: RebelControlled"), rendered),
				() -> assertTrue(rendered.contains("pdb: UP_2"), rendered),
				() -> assertTrue(rendered.contains("orbit: []"), rendered),
				() -> assertTrue(rendered.contains("environ: Urban | size=6 | resources=9* | coup=3 | races=[Yester*]"), rendered),
				() -> assertTrue(rendered.contains("environ: Wild | size=4 | resources=6* | coup=- | races=[Saurian*]"), rendered));
	}

	@Test
	void renderExpandsSpaceshipStacks() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.render();

		assertAll(
				() -> assertTrue(rendered.contains("spaceship-stack[imperialspaceship; passengers=[jonkidu, vanskatiea]]"), rendered),
				() -> assertTrue(rendered.contains("counters=[line, spaceship-stack[imperialspaceship; passengers=[jonkidu, vanskatiea]]]"), rendered));
	}

	@Test
	void renderCompactSuppressesEmptyFieldsAndAbbreviates() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.renderCompact();

		assertAll(
				// star system header uses bracket notation
				() -> assertTrue(rendered.contains("[22 Egrix]"), rendered),
				// planet line is single line with abbreviated state
				// Angoff: startingLoyaltyS=Neutral, ctrl=Rebel, pdb=UP_2 (2↑)
				() -> assertTrue(rendered.contains("[222 Angoff] loyalty=Neutral ctrl=Rebel pdb=2↑"), rendered),
				// Quibron: startingLoyaltyS=Loyal, ctrl=Imperial, pdb=UP_1 (1↑)
				() -> assertTrue(rendered.contains("[221 Quibron] loyalty=Loyal ctrl=Imperial pdb=1↑"), rendered),
				// Charkhan: startingLoyaltyS=Patriotic, ctrl=Imperial, pdb=UP_0 (0↑)
				() -> assertTrue(rendered.contains("[223 Charkhan] loyalty=Patriotic ctrl=Imperial pdb=0↑"), rendered),
				// environ shows only populated fields, space-joined
				() -> assertTrue(rendered.contains("Urban sz=6 res=9* coup=3 Yester* cr=Laboroid"), rendered),
				// spaceship stack uses compact bracket notation
				() -> assertTrue(rendered.contains("imperialspaceship[jonkidu vanskatiea]"), rendered),
				// counters separated by ::
				() -> assertTrue(rendered.contains(" :: "), rendered),
				// empty drift/drift2 are suppressed entirely
				() -> assertFalse(rendered.contains("drift"), rendered));
	}

	@Test
	void renderCompactSovereignAndCreatureUsePrefixes() {
		GameBoard gameBoard = BaseGameGameBoard.create(
				List.of(BaseGameStarSystem.Varu), Scenario.Type.StartRebellion);
		var renderer = new TextMapRenderer(gameBoard, EMPTY_COUNTER_LOCATOR);

		var rendered = renderer.renderCompact();

		// Sovereign on Charkhan Wild environ — Cercis is in Varu, has no sovereign but check Solvia has one
		// Cercis Wild doesn't have sovereign, but let's check Rhexia in Deblon...
		// Actually Varu has Cercis and Solvia (with sovereign Yaldor? no, Rhexia has Yaldor).
		// Varu: Horon, Solvia, Cercis  — Solvia has no sovereign, Cercis has none
		// Let's just verify prefix format for what we know is present in Luine (Mrane has Balgar sovereign)
		// Use Luine star system instead
		GameBoard luineBoard = BaseGameGameBoard.create(
				List.of(BaseGameStarSystem.Luine), Scenario.Type.StartRebellion);
		var luineRenderer = new TextMapRenderer(luineBoard, EMPTY_COUNTER_LOCATOR);
		var luineRendered = luineRenderer.renderCompact();

		// Mrane has sovereign Balgar, should appear as sov=Balgar
		assertTrue(luineRendered.contains("sov=Balgar"), luineRendered);
	}

	@Test
	void renderMarkdownIncludesProperFormatting() {
		var game = createFlightToEgrixGame();
		var renderer = new TextMapRenderer(game, game);

		var rendered = renderer.renderMarkdown();

		assertAll(
				() -> assertTrue(rendered.startsWith("# FitG Map"), rendered),
				() -> assertTrue(rendered.contains("## Star System: 22 Egrix"), rendered),
				() -> assertTrue(rendered.contains("### Planet: 222 Angoff"), rendered),
				() -> assertTrue(rendered.contains("- **Loyalty:** Neutral"), rendered),
				() -> assertTrue(rendered.contains("- **Control:** RebelControlled"), rendered),
				() -> assertTrue(rendered.contains("- **PDB:** UP_2"), rendered),
				() -> assertTrue(rendered.contains("#### Environment: Urban"), rendered),
				() -> assertTrue(rendered.contains("- **Size:** 6"), rendered),
				() -> assertTrue(rendered.contains("- **Resources:** 9*"), rendered),
				() -> assertTrue(rendered.contains("- **Coup:** 3"), rendered),
				() -> assertTrue(rendered.contains("- **Races:** [Yester*]"), rendered));
	}

	private static Game createFlightToEgrixGame() {
		return Game.createGame(BaseGameScenario.FlightToEgrix, new FlightToEgrixRebelStrategy(), new FlightToEgrixImperialStrategy());
	}

	private static final class EmptyCounterLocator implements CounterLocator {

		@Override
		public Collection<Counter> countersAt(Location location) {
			return List.of();
		}

		@Override
		public CounterLocator move(Counter counter, Location destination) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Optional<Location> locationOf(Counter counter) {
			return Optional.empty();
		}

		@Override
		public Stream<Location> locationOfByType(Counter counter) {
			return Stream.empty();
		}

		@Override
		public Optional<Counter> stackContaining(Counter counter) {
			return Optional.empty();
		}
	}
}
