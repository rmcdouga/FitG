package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

class BaseGameCharacterTest {

	@Test
	void testNumberOfCharacters() {
		Set<Integer> expectedCardNumbers = IntStream.range(1, 32)
													.mapToObj(Integer::valueOf)
													.collect(Collectors.toSet());
		Set<Integer> actualCardNumbers = Stream.of(BaseGameCharacter.values())
												.map(c->c.cardNumber())
												.collect(Collectors.toSet());
		expectedCardNumbers.removeAll(actualCardNumbers);
		assertEquals(0, expectedCardNumbers.size(), ()->("Missing card numbers (" + expectedCardNumbers.stream().map(i->Integer.toString(i)).collect(Collectors.joining(",")) + ")."));
	}
	
	@Test
	void testRandomAttributes() {
		assertFalse(Zina_Adora.spaceLeadership().isPresent());
		assertEquals(1, Adam_Starlight.spaceLeadership().getAsInt());
		assertEquals(2, Agan_Rafa.bonusDraws(BaseGameMission.ASSASINATION));
		assertTrue(Jon_Kidu.isHomePlanet(BaseGamePlanet.Squamot));
		assertFalse(Jon_Kidu.isHomePlanet(BaseGamePlanet.Orlog));
	}
	
	@ParameterizedTest
	@EnumSource
	void testDescriptions(BaseGameCharacter character) {
		// Test for characters that might accidently be introduced in a raw string.
		// None of these characters appear in the descriptions, so they are likely artifacts that were introduced
		// by accident.
		String description = character.description();
		assertFalse(description.isBlank());
		assertFalse(description.contains("\n"));
		assertFalse(description.contains("\r"));
//		assertFalse(description.contains("\""));
		assertFalse(description.contains("+"));
	}

}
