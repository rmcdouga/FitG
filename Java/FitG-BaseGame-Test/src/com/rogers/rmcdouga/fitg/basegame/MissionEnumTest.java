package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MissionEnumTest {

	private static final int EXPECTED_NUM_OF_MISSIONS = 15;
	private static final int EXPECTED_START_CARD_NO = 53;
	private static final int EXPECTED_END_CARD_NO = EXPECTED_START_CARD_NO + EXPECTED_NUM_OF_MISSIONS - 1;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMissionFromMnemonicChar_valid() {
		char mnemonic_upper = 'P';
		Optional<Mission> mission = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic_upper);
		
		assertTrue(mission.isPresent(), "Expected mission '" + mnemonic_upper + "' should be present.");

		char mnemonic_lower = 'p';
		Optional<Mission> mission2 = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic_lower);
		
		assertTrue(mission2.isPresent(), "Expected mission '" + mnemonic_lower + "' (in lower case) should also be present.");
	}

	@Test
	void testGetMissionFromMnemonicChar_invalid() {
		char mnemonic = 'Z';
		Optional<Mission> mission = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic);
		
		assertFalse(mission.isPresent(), "Expected mission '" + mnemonic + "' should be present.");
	}

	@Test
	void testGetMissionFromMnemonicString_valid() {
		String mnemonic_upper = "S";
		Optional<Mission> mission = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic_upper);
		
		assertTrue(mission.isPresent(), "Expected mission '" + mnemonic_upper + "' should be present.");
		assertTrue(mission.get() == MissionEnum.SABOTAGE, "Expected mission '" + mnemonic_upper + "' to be SABOTAGE, but was '" + mission.get().missionName() + "'.");

		String mnemonic_lower = "t";
		Optional<Mission> mission2 = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic_lower);
		
		assertTrue(mission2.isPresent(), "Expected mission '" + mnemonic_lower + "' (in lower case) should also be present.");
		assertTrue(mission2.get() == MissionEnum.SUBVERT_TROOPS, "Expected mission '" + mnemonic_lower + "' to be SABOTAGE, but was '" + mission2.get().missionName() + "'.");
	}

	@Test
	void testGetMissionFromMnemonicString_invalid() {
		String mnemonic = "Y";
		Optional<Mission> mission = MissionEnum.missionFactory().getMissionFromMnemonic(mnemonic);
		
		assertFalse(mission.isPresent(), "Expected mission '" + mnemonic + "' should be present.");
	}

	@Test
	void testGetMissionsFromMnemonicsSetOfCharacter_valid() {
		Set<Character> mnemonics = "STP".chars().mapToObj(c->Character.valueOf((char)c)).collect(Collectors.toSet());
		Set<Mission> expected_results = Collections.<Mission>unmodifiableSet(EnumSet.of(MissionEnum.SABOTAGE, MissionEnum.SUBVERT_TROOPS, MissionEnum.SCAVENGE));
		Set<Mission> missions = MissionEnum.missionFactory().getMissionsFromMnemonics(mnemonics);

		assertFalse(missions.isEmpty(), "Expected some missions to be returned, but instead it is empty.");
		assertEquals(expected_results, missions, "Actual results and expected results do not match.");
	}

	@Test
	void testGetMissionsFromMnemonicsSetOfCharacter_invalid() {
		Set<Character> mnemonics = "ZYW".chars().mapToObj(c->Character.valueOf((char)c)).collect(Collectors.toSet());
		Set<Mission> missions = MissionEnum.missionFactory().getMissionsFromMnemonics(mnemonics);

		assertTrue(missions.isEmpty(), "Expected missions to be empty, but had " + missions.size() + " entries.");
	}

	@Test
	void testGetMissionsFromMnemonicsString_valid() {
		String mnemonics = "STP";
		Set<Mission> expected_results = Collections.<Mission>unmodifiableSet(EnumSet.of(MissionEnum.SABOTAGE, MissionEnum.SUBVERT_TROOPS, MissionEnum.SCAVENGE));
		Set<Mission> missions = MissionEnum.missionFactory().getMissionsFromMnemonics(mnemonics);
		
		assertFalse(missions.isEmpty(), "Expected some missions to be returned, but instead it is empty.");
		assertEquals(expected_results, missions, "Actual results and expected results do not match.");
	}

	@Test
	void testGetMissionsFromMnemonicsString_validWithwhitespace() {
		String mnemonics2 = "S,t p";
		Set<Mission> expected_results2 = Collections.<Mission>unmodifiableSet(EnumSet.of(MissionEnum.SABOTAGE, MissionEnum.SUBVERT_TROOPS, MissionEnum.SCAVENGE));
		Set<Mission> missions2 = MissionEnum.missionFactory().getMissionsFromMnemonics(mnemonics2);
		
		assertFalse(missions2.isEmpty(), "Expected some missions to be returned, but instead it is empty.");
		assertEquals(expected_results2, missions2, "Actual results and expected results do not match.");
	}

	@Test
	void testNumberOfMissions() {
		assertEquals(EXPECTED_NUM_OF_MISSIONS, MissionEnum.ALL_MISSION_MNEMONICS.length(), "Expected there to be " + EXPECTED_NUM_OF_MISSIONS + " unique missions.");
	}
	
	@Test
	void testMissionCardNumbers() {
		Set<Integer> expectedCards = IntStream.rangeClosed(EXPECTED_START_CARD_NO, EXPECTED_END_CARD_NO).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		Set<Integer> foundCards = MissionEnum.ALL_MISSIONS.stream().mapToInt(MissionEnum::cardNumber).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		
		assertEquals(expectedCards, foundCards, "Expected card numbers and found card numbers should match.");
	}

}
