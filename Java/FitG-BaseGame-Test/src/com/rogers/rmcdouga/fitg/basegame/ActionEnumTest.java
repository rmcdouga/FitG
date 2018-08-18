package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class ActionEnumTest {

	private static final int EXPECTED_NUM_OF_ACTIONS = 30;
	private static final int EXPECTED_START_CARD_NO = 68;
	private static final int EXPECTED_END_CARD_NO = EXPECTED_START_CARD_NO + EXPECTED_NUM_OF_ACTIONS - 1;

	@Test
	void testIsSuccessful_true() {
		assertTrue(ActionEnum.CARD_68.isSuccessful(MissionEnum.STEAL_ENEMY_RESOURCES, Action.EnvironType.URBAN), "Expected Mission to be successful.");
	}

	@Test
	void testIsSuccessful_false() {
		assertFalse(ActionEnum.CARD_68.isSuccessful(MissionEnum.ASSASSINATION, Action.EnvironType.URBAN), "Expected Mission to be successful.");
	}

	@Test
	void testActoinCardNumbers() {
		assertEquals(EXPECTED_NUM_OF_ACTIONS, ActionEnum.ALL_ACTIONS.size(), "Expected there to be " + EXPECTED_NUM_OF_ACTIONS + " unique actions.");
	}

	@Test
	void testNumberOfActions() {
		Set<Integer> expectedCards = IntStream.rangeClosed(EXPECTED_START_CARD_NO, EXPECTED_END_CARD_NO).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		Set<Integer> foundCards = ActionEnum.ALL_ACTIONS.stream().mapToInt(ActionEnum::cardNumber).mapToObj(Integer::valueOf).collect(Collectors.toSet());
		
		assertEquals(expectedCards, foundCards, "Expected card numbers and found card numbers should match.");
	}

}
