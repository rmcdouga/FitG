package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.GameTest;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

class BaseGameCharacterFinderTest {

	CharacterFinder underTest = new BaseGameCharacterFinder(GameTest.createFlightToEgrixGame());

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
}
