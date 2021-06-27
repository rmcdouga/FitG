package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.tables.CharacterCombatResultsTable.BreakOffResult;
import com.rogers.rmcdouga.fitg.basegame.tables.CharacterCombatResultsTable.CombatResult;

class CharacterCombatResultsTableTest {

	private static boolean[][] expectedDieResults = {
			{ false, false, false, false, false, false },
			{  true, false, false, false, false, false },	// 1
			{  true,  true, false, false, false, false },	// 2
			{  true,  true,  true, false, false, false },	// 3
			{  true,  true,  true,  true, false, false },	// 4
			{  true,  true,  true,  true,  true, false },	// 5
			{  true,  true,  true,  true,  true,  true },
	};

	private static int[] combatDifferentialBoundaries = { -8, -7, -6, -4, -3, -2, -1, 0, 1, 2, 3, 4, 6, 7, 10, 11, 12};
	
	private static int[] expectedActiveBreakOffResults = { 4, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1 };
	private static int[] expectedInactiveBreakOffResults = { 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3 };
	
	@ParameterizedTest(name = "combatDifferential={0}, expectedActiveValue={1}, expectedInactiveValue={2}, roll={3}")
	@MethodSource("generateBreakOffArguments")
	void testAttemptBreakOff(int combatDifferential, int expectedActiveValue, int expectedInactiveValue, int roll) {
		boolean expectedActiveResult = expectedDieResults[expectedActiveValue][roll - 1];
		boolean expectedInactiveResult = expectedDieResults[expectedInactiveValue][roll - 1];
		BreakOffResult attemptBreakOff = CharacterCombatResultsTable.attemptBreakOff(roll, combatDifferential);
		assertEquals(expectedActiveResult, attemptBreakOff == BreakOffResult.All);
		assertEquals(expectedInactiveResult, attemptBreakOff == BreakOffResult.All || attemptBreakOff == BreakOffResult.Inactive);
	}

	static Stream<Arguments> generateBreakOffArguments() {
		List<Arguments> result = new ArrayList<>(expectedDieResults[0].length * 5);
		for (int combatDifferentialIndex = 0; combatDifferentialIndex < combatDifferentialBoundaries.length; combatDifferentialIndex++) {
			for (int die = 1; die < 7; die++) {
				result.add(Arguments.arguments(combatDifferentialBoundaries[combatDifferentialIndex], expectedActiveBreakOffResults[combatDifferentialIndex], expectedInactiveBreakOffResults[combatDifferentialIndex], die));
			}
		}
		return result.stream();
	}

	private static int[][] expectedAttackersResults = {
		   // -8, -7. -6, -5, -4, -3, -2, -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12	
			{  4,  4,  3,  3,  3,  3,  3,  2,  2, -2,  2,  2,  1,  1,  1,  1,  1,  1,  1,  1,  1},	// Roll = 1
			{  4,  4,  3,  3,  3,  2,  2, -2, -2,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0,  0},	// Roll = 2
			{  3,  3, -3, -3, -3,  2,  2,  2,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0,  0,  0,  0},	// Roll = 3
			{  3,  3,  2,  2,  2, -2, -2,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},	// Roll = 4
			{  2,  2,  2,  2,  2,  1,  1,  1,  0,  0,  0,  0,-10,-10,-10,  0,  0,  0,  0,  0,  0},	// Roll = 5
			{  2,  2,  1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},	// Roll = 6
	};
	
	@ParameterizedTest(name = "combatDifferential={0}, roll={1}")
	@MethodSource("generateCombatArguments")
	void testAttackerResult(int combatDifferential, int roll) {
		CombatResult result = CharacterCombatResultsTable.attackerResult(roll, combatDifferential);
		assertEquals(constructExpectedResult(expectedAttackersResults[roll -1][combatDifferential + 8]), result);
	}

	private static int[][] expectedDefendersResults = {
			   // -8, -7. -6, -5, -4, -3, -2, -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12	
				{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1, -1, -1, -1, -1,  2,  2},	// Roll = 1
				{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1,  1,  1,  2,  2,  2,  2,  2,  2},	// Roll = 2
				{  0,  0,  0,  0,  0,  0,  0,  0,-10,  1,  1,  1, -1, -1, -1, -2, -2, -2, -2,  3,  3},	// Roll = 3
				{  0,  0,  0,  0,  0,  0,  0, -1,  1, -1, -1, -1, -2, -2, -2,  3,  3,  3,  3,  3,  3},	// Roll = 4
				{  0,  0,  0,  0,  0,  1,  1,  1,  1,  1, -2, -2,  2,  2,  2, -3, -3, -3, -3,  4,  4},	// Roll = 5
				{  1,  1,  1,  1,  1,  1,  1,  1,  2,  2,  2,  2,  3,  3,  3,  3,  3,  3,  3,  4,  4},	// Roll = 6
		};
		
	@ParameterizedTest(name = "combatDifferential={0}, roll={1}")
	@MethodSource("generateCombatArguments")
	void testDefenderResult(int combatDifferential, int roll) {
		CombatResult result = CharacterCombatResultsTable.defenderResult(roll, combatDifferential);
		assertEquals(constructExpectedResult(expectedDefendersResults[roll -1][combatDifferential + 8]), result);
	}

	private static CombatResult constructExpectedResult(int expectedResult) {
		return expectedResult >= 0 ? CombatResult.of(expectedResult) : CombatResult.of(Math.abs(expectedResult) % 10, true);
	}
	
	static Stream<Arguments> generateCombatArguments() {
		return IntStream.range(-8, 13)						// combatDifferential values (-8 to 12) 
					    .boxed()
					    .flatMap(cd->IntStream.range(1, 7)	// die roll values (1 to 6)
					    					  .mapToObj(r->Arguments.arguments(cd, r))
					    		)
				;
	}

	@Test
	void validateTestData() {
		assertEquals(combatDifferentialBoundaries.length, expectedActiveBreakOffResults.length);
		assertEquals(combatDifferentialBoundaries.length, expectedInactiveBreakOffResults.length);
	}
}
