package com.rogers.rmcdouga.fitg.basegame.box;

import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.IMPERIAL;
import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.REBEL;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Adam_Starlight;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Thysa_Kymbo;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Zina_Adora;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

class BaseGameCharacterPoolTest {

	private final BaseGameCharacterPool underTest = new BaseGameCharacterPool(n->0);	// Random always picks the first.

	@Test
	void testGetCharacter() {
		assertEquals(Adam_Starlight, underTest.getCharacter(Adam_Starlight));
	}

	@Test
	void testGetCharacter_NotAvailable() {
		Character adamStarlight = underTest.getCharacter(Adam_Starlight);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getCharacter(Adam_Starlight));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(adamStarlight.toString()), containsString("is not available"), containsString("IN_PLAY")));
	}

	@Test
	void testKill() {
		Character adamStarlight = underTest.getCharacter(Adam_Starlight);
		underTest.kill(adamStarlight);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getCharacter(Adam_Starlight));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(adamStarlight.toString()), containsString("is not available"), containsString("DEAD")));
	}

	@Test
	void testKill_NotInPlay() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.kill(Adam_Starlight));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(Adam_Starlight.toString()), containsString("is not in play"), containsString("AVAILABLE")));
	}

	@Test
	void testCloneCharacter() {
		Character adamStarlight = underTest.getCharacter(Adam_Starlight);
		underTest.kill(adamStarlight);
		assertEquals(Adam_Starlight, underTest.cloneCharacter(REBEL).get());
		assertEquals(Zina_Adora, underTest.cloneCharacter(REBEL).get());	// If no dead characters, then randomly get an available one.
	}

	@Test
	void testRandomCharacter() {
		assertEquals(Zina_Adora, underTest.randomCharacter(REBEL).get());	// Zina Adora is at location 0 for Rebels;
		IllegalArgumentException exRebel = assertThrows(IllegalArgumentException.class, ()->underTest.getCharacter(Zina_Adora));
		String msgRebel = exRebel.getMessage();
		assertNotNull(msgRebel);
//		System.out.println(msg);
		assertThat(msgRebel, allOf(containsString(Zina_Adora.toString()), containsString("is not available"), containsString("IN_PLAY")));
		assertEquals(Thysa_Kymbo, underTest.randomCharacter(IMPERIAL).get());	// Thysa_Kymbo is at location 0 for Imperials;
		IllegalArgumentException exImp = assertThrows(IllegalArgumentException.class, ()->underTest.getCharacter(Thysa_Kymbo));
		String msgImp = exImp.getMessage();
		assertNotNull(msgImp);
//		System.out.println(msg);
		assertThat(msgImp, allOf(containsString(Thysa_Kymbo.toString()), containsString("is not available"), containsString("IN_PLAY")));
	}

	@Test
	void testRandomCharacter_AllCharacters() {
		Set<BaseGameCharacter> allRebelChars = BaseGameCharacter.stream(c->c.allegience() == REBEL).collect(Collectors.toSet());
		Set<BaseGameCharacter> allImperialChars = BaseGameCharacter.stream(c->c.allegience() == IMPERIAL).collect(Collectors.toSet());
		
		BaseGameCharacterPool bgcp = new BaseGameCharacterPool();

		Set<BaseGameCharacter> foundRebelChars = getAllCharsRandomly(REBEL, bgcp);
		Set<BaseGameCharacter> foundImperialChars = getAllCharsRandomly(IMPERIAL, bgcp);

		assertEquals(allRebelChars, foundRebelChars);
		assertEquals(allImperialChars, foundImperialChars);
	}

	private static Set<BaseGameCharacter> getAllCharsRandomly(Faction faction, BaseGameCharacterPool bgcp) {
		Set<BaseGameCharacter> foundChars = EnumSet.noneOf(BaseGameCharacter.class);
		Optional<Character> randomChar = bgcp.randomCharacter(faction);
		while(randomChar.isPresent()) {
			foundChars.add((BaseGameCharacter)randomChar.get());
			randomChar = bgcp.randomCharacter(faction);
		}
		return foundChars;
	}

	@Test
	void testGetCharacter_NotBaseGameCharacter() {
		Character anonCharacter = new Character() {

			@Override
			public int combat() {
				return 0;
			}

			@Override
			public int endurance() {
				return 0;
			}

			@Override
			public int intelligence() {
				return 0;
			}

			@Override
			public int leadership() {
				return 0;
			}

			@Override
			public OptionalInt spaceLeadership() {
				return null;
			}

			@Override
			public int diplomacy() {
				return 0;
			}

			@Override
			public int navigation() {
				return 0;
			}

			@Override
			public RaceType race() {
				return null;
			}

			@Override
			public boolean isHomePlanet(Planet planet) {
				return false;
			}

			@Override
			public Faction allegience() {
				return null;
			}

			@Override
			public int bonusDraws(Mission mission) {
				return 0;
			}

			@Override
			public boolean hasSpecialAbility(SpecialAbility specialAbility) {
				return false;
			}

			@Override
			public String description() {
				return null;
			}

			@Override
			public String id() {
				return null;
			}
			
		};
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getCharacter(anonCharacter));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString("Character was not a BaseGameCharacter"), containsString(anonCharacter.getClass().getName())));
	}


}
