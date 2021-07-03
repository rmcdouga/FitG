package com.rogers.rmcdouga.fitg.basegame.box;

import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.IMPERIAL;
import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.REBEL;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Adam_Starlight;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Thysa_Kymbo;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Zina_Adora;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

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
		assertEquals(Thysa_Kymbo, underTest.randomCharacter(IMPERIAL).get());	// Thysa_Kymbo is at location 0 for Imperials;
	}

}
