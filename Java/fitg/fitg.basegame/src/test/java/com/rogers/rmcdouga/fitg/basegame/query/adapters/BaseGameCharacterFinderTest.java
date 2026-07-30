package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

class BaseGameCharacterFinderTest {

	private final Game game = GameTest.createFlightToEgrixGame();
	private final CharacterFinder underTest = new BaseGameCharacterFinder(game);

	// --- findCharacter ---

	@ParameterizedTest(name = "findCharacter(\"{0}\") finds Jon_Kidu")
	@ValueSource(strings = {"Jon Kidu", "jon kidu", "JonKidu", "JON_KIDU"})
	void testFindCharacter_foundWithVariousFormats(String input) {
		Character result = underTest.findCharacter(input).orElseThrow();
		assertEquals(BaseGameCharacter.Jon_Kidu, result);
	}

	@Test
	void testFindCharacter_foundCharacterNotInPlay() {
		// Zina Adora is a valid enum value but not placed in the FlightToEgrix scenario
		assertTrue(underTest.findCharacter("Zina Adora").isPresent());
		assertEquals(BaseGameCharacter.Zina_Adora, underTest.findCharacter("Zina Adora").orElseThrow());
	}

	@ParameterizedTest(name = "findCharacter(\"{0}\") returns empty Optional")
	@ValueSource(strings = {"unknown", "foo", ""})
	void testFindCharacter_notFound(String input) {
		assertTrue(underTest.findCharacter(input).isEmpty());
	}

	// --- findCharacterInPlay ---

	@Test
	void testFindCharacterInPlay_foundOnMap() {
		// Jon Kidu is placed in the FlightToEgrix scenario
		Character result = underTest.findCharacterInPlay("Jon Kidu").orElseThrow();
		assertEquals(BaseGameCharacter.Jon_Kidu, result);
	}

	@Test
	void testFindCharacterInPlay_knownButNotPlaced() {
		// Zina Adora exists in the enum but is not placed in the FlightToEgrix scenario
		assertTrue(underTest.findCharacterInPlay("Zina Adora").isEmpty());
	}

	@ParameterizedTest(name = "findCharacterInPlay(\"{0}\") returns empty Optional")
	@ValueSource(strings = {"unknown", "foo", ""})
	void testFindCharacterInPlay_unknownId(String input) {
		assertTrue(underTest.findCharacterInPlay(input).isEmpty());
	}

	@Test
	void testFindAllCharactersInPlay_returnsPlacedCharactersOnly() {
		List<Character> result = underTest.findAllCharactersInPlay().toList();

		assertAll(
				() -> assertFalse(result.isEmpty()),
				() -> assertTrue(result.contains(BaseGameCharacter.Jon_Kidu)),
				() -> assertFalse(result.contains(BaseGameCharacter.Zina_Adora)),
				() -> assertTrue(result.stream().allMatch(character -> game.locationOf((Counter) character).isPresent()))
				);
	}
}
