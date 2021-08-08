package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;
import com.rogers.rmcdouga.fitg.basegame.CardTestUtils;
import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

class BaseGameCharacterTest {

	@Test
	void testNumberOfCharacters() {
		Set<Integer> expectedCardNumbers = IntStream.range(1, 33)
													.mapToObj(Integer::valueOf)
													.collect(Collectors.toSet());
		Set<Integer> actualCardNumbers = Stream.of(BaseGameCharacter.values())
												.map(c->c.cardNumber())
												.collect(Collectors.toSet());
		
		assertIterableEquals(expectedCardNumbers, actualCardNumbers, ()->CardTestUtils.reportDifferences(expectedCardNumbers, actualCardNumbers));
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

	@ParameterizedTest
	@EnumSource
	void testOf_BaseGameCharacter(BaseGameCharacter character) {
		assertSame(character, BaseGameCharacter.of((Character)character));
	}
	
	@Test
	void testOf_NonBaseGameCharacter() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->BaseGameCharacter.of(new Character() {
			
			@Override
			public OptionalInt spaceLeadership() {
				return null;
			}
			
			@Override
			public RaceType race() {
				return null;
			}
			
			@Override
			public int navigation() {
				return 0;
			}
			
			@Override
			public int leadership() {
				return 0;
			}
			
			@Override
			public boolean isHomePlanet(Planet planet) {
				return false;
			}
			
			@Override
			public int intelligence() {
				return 0;
			}
			
			@Override
			public boolean hasSpecialAbility(SpecialAbility specialAbility) {
				return false;
			}
			
			@Override
			public int endurance() {
				return 0;
			}
			
			@Override
			public int diplomacy() {
				return 0;
			}
			
			@Override
			public String description() {
				return null;
			}
			
			@Override
			public int combat() {
				return 0;
			}
			
			@Override
			public int bonusDraws(Mission mission) {
				return 0;
			}
			
			@Override
			public Faction allegience() {
				return null;
			}
		}));
		
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, containsString("not a BaseGameCharacter"));
	}
}
