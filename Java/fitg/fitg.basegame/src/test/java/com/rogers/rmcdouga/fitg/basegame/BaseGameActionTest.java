package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class BaseGameActionTest {

	private static final int EXPECTED_NUM_OF_ACTIONS = 30;
	private static final int EXPECTED_START_CARD_NO = 68;
	private static final int EXPECTED_END_CARD_NO = EXPECTED_START_CARD_NO + EXPECTED_NUM_OF_ACTIONS - 1;

	@Test
	void testIsSuccessful_true() {
		assertTrue(BaseGameAction.CARD_68.isSuccessful(BaseGameMission.STEAL_ENEMY_RESOURCES, Action.EnvironType.URBAN), "Expected Mission to be successful.");
	}

	@Test
	void testIsSuccessful_false() {
		assertFalse(BaseGameAction.CARD_68.isSuccessful(BaseGameMission.ASSASSINATION, Action.EnvironType.URBAN), "Expected Mission to be successful.");
	}

	@Test
	void testActionCardNumbers() {
		assertEquals(EXPECTED_NUM_OF_ACTIONS, BaseGameAction.ALL_ACTIONS.size(), "Expected there to be " + EXPECTED_NUM_OF_ACTIONS + " unique actions.");
	}

	@Test
	void testNumberOfActions() {
		Set<Integer> expectedCards = IntStream.rangeClosed(EXPECTED_START_CARD_NO, EXPECTED_END_CARD_NO).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		Set<Integer> foundCards = BaseGameAction.ALL_ACTIONS.stream().mapToInt(BaseGameAction::cardNumber).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		
		assertEquals(expectedCards, foundCards, "Expected card numbers and found card numbers should match.");
	}
	
	@Test
	void testGetMissions() {
		Action action = BaseGameAction.CARD_68;
		Set<Mission> missions = action.getMissions(Action.EnvironType.URBAN);
		assertEquals(2, missions.size(), "Expected only two missions for card 68.");
		assertTrue(missions.contains(BaseGameMission.STEAL_ENEMY_RESOURCES), "Expected Card 68 missions to contain STEAL EMENY RESOURCES");
		assertTrue(missions.contains(BaseGameMission.START_STOP_REBELLION), "Expected Card 68 missions to contain START_STOP_REBELLION");
		assertFalse(missions.contains(BaseGameMission.ASSASSINATION), "Expected Card 68 missions to not contain ASSASSINATION");
	}
	
	@Test
	void testGetMissionLetters_NoDelim() {
		Action action = BaseGameAction.CARD_68;
		String missionLetters = action.getMissionLetters(Action.EnvironType.URBAN);
		assertEquals("H R", missionLetters, "Expected mission letters to be 'R H'.");
	
	}
	
	@Test
	void testGetMissionLetters_WithDelim() {
		Action action = BaseGameAction.CARD_68;
		String missionLetters = action.getMissionLetters(Action.EnvironType.URBAN, "|");
		assertEquals("H|R", missionLetters, "Expected mission letters to be 'R|H'.");
	}

	@Test
	void testActionFactory_GetAction() {
		Optional<Action> action = BaseGameAction.actionfactory().getAction(74);
		assertTrue(action.isPresent(), "Expected to find card 74.");
		assertEquals(BaseGameAction.CARD_74, action.get(), "Expected to retrieve CARD_74");
	}
	
	@Test
	void testActionFactory_GetAction_InvalidCard() {
		Optional<Action> action = BaseGameAction.actionfactory().getAction(1);
		assertFalse(action.isPresent(), "Expected not to find card 1.");
	}

}
