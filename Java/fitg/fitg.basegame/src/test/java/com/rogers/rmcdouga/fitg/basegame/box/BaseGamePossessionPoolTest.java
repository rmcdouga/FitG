package com.rogers.rmcdouga.fitg.basegame.box;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCompanion.Norrocks;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameObject.Personal_Body_Shield;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship.Star_Cruiser;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameWeapon.Snipers_Rifle;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGamePossession;
import com.rogers.rmcdouga.fitg.basegame.units.Possession;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;

class BaseGamePossessionPoolTest {

	private final BaseGamePossessionPool underTest = new BaseGamePossessionPool(n->0);	// Random always picks the first.
	
	@Test
	void testGetPossession() {
		// Test getting one of each possession type: Spaceship, Weapon, Object, and Companion
		assertEquals(Star_Cruiser, underTest.getPossession(Star_Cruiser));
		assertEquals(Snipers_Rifle, underTest.getPossession(Snipers_Rifle));
		assertEquals(Personal_Body_Shield, underTest.getPossession(Personal_Body_Shield));
		assertEquals(Norrocks, underTest.getPossession(Norrocks));	
	}

	@Test
	void testGetPossession_NotAvailable() {
		Possession bodyShield = underTest.getPossession(Personal_Body_Shield);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getPossession(Personal_Body_Shield));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(bodyShield.toString()), containsString("is not available"), containsString("IN_PLAY")));
	}
	
	@Test
	void testGetSpaceship() {
		assertEquals(Star_Cruiser, underTest.getSpaceship(Star_Cruiser).get());
	}

	@Test
	void testGetSpaceship_NotAvailable() {
		Optional<Spaceship> starCruiser = underTest.getSpaceship(Star_Cruiser);
		assertTrue(starCruiser.isPresent());
		Optional<Spaceship> starCruiser2 = underTest.getSpaceship(Star_Cruiser);
		assertTrue(starCruiser2.isEmpty());
	}

	@Test
	void testLost() {
		Possession snipersRifle = underTest.getPossession(Snipers_Rifle);
		underTest.lost(snipersRifle);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getPossession(snipersRifle));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(snipersRifle.toString()), containsString("is not available"), containsString("LOST")));
	}

	@Test
	void testLost_NotInPlay() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.lost(Personal_Body_Shield));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(Personal_Body_Shield.toString()), containsString("is not in play"), containsString("AVAILABLE")));
	}

	@Test
	void testCaptured() {
		Possession snipersRifle = underTest.getPossession(Snipers_Rifle);
		underTest.captured(snipersRifle);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getPossession(snipersRifle));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(snipersRifle.toString()), containsString("is not available"), containsString("CAPTURED")));
	}

	@Test
	void testCaptured_NotInPlay() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.captured(Personal_Body_Shield));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(Personal_Body_Shield.toString()), containsString("is not in play"), containsString("AVAILABLE")));
	}

	@Test
	void testDestroyed() {
		Possession snipersRifle = underTest.getPossession(Snipers_Rifle);
		underTest.destroyed(snipersRifle);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getPossession(snipersRifle));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(snipersRifle.toString()), containsString("is not available"), containsString("DESTROYED")));
	}

	@Test
	void testDestroyed_NotInPlay() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.destroyed(Personal_Body_Shield));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, allOf(containsString(Personal_Body_Shield.toString()), containsString("is not in play"), containsString("AVAILABLE")));
	}

	@Disabled("This doesn't work because the HashMap does not produce a predictable order.  Item 0 may be different on different runs.")
	@Test
	void testRandomPossession() {
		assertEquals(Snipers_Rifle, underTest.randomPossession().get());
	}

	@Test
	void testRandomPossession_AllPossessions() {
		Set<Possession> allPossessions = BaseGamePossession.stream().collect(Collectors.toSet());
		Set<Possession> gottenPossessions = getAllPossessionsRandomly(new BaseGamePossessionPool());
		assertEquals(allPossessions, gottenPossessions);
	}

	private static Set<Possession> getAllPossessionsRandomly(BaseGamePossessionPool bgpp) {
		Set<Possession> foundPossessions = new HashSet<>();
		Optional<Possession> randomPossession = bgpp.randomPossession();
		while(randomPossession.isPresent()) {
			foundPossessions.add(randomPossession.get());
			randomPossession = bgpp.randomPossession();
		}
		return foundPossessions;
	}


}
