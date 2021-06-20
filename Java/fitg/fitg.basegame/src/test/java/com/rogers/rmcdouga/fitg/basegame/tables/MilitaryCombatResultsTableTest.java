package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.rogers.rmcdouga.fitg.basegame.tables.MilitaryCombatResultsTableTest.Result.of;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.tables.MilitaryCombatResultsTable.Results;

class MilitaryCombatResultsTableTest {
	
	public static record Result(int attackerLoss, int defenderLoss) {
		static Result of(int attackerLoss, int defenderLoss) {
			return new Result(attackerLoss, defenderLoss);
		}
		
		public boolean equals(Results target) {
			return Objects.requireNonNull(target, "target cannot be null.").attackerStrLoss() == attackerLoss && target.defenderStrLoss() == defenderLoss;
		}
	}
	
	private static Result[][] expectedResults = {
		// 1-6		1-5		 1-4	  1-3	   1-2		1-1		 2-1	   3-1	   	4-1		5-1		 6-1
		{of(8,0), of(7,0), of(6,0), of(5,0), of(5,0), of(4,0), of(3,1), of(3,2), of(2,2), of(2,3), of(1,4)},	// 1
		{of(7,0), of(6,0), of(5,0), of(5,0), of(4,0), of(3,1), of(2,2), of(2,3), of(1,3), of(1,4), of(1,5)},	// 2
		{of(7,0), of(5,0), of(5,0), of(4,0), of(3,1), of(2,1), of(2,2), of(2,3), of(1,4), of(1,4), of(1,5)},	// 3
		{of(6,0), of(5,0), of(4,1), of(3,1), of(3,1), of(2,2), of(1,3), of(1,3), of(1,4), of(0,5), of(0,6)},	// 4
		{of(5,1), of(4,1), of(4,1), of(3,1), of(2,2), of(1,2), of(1,3), of(0,4), of(0,5), of(0,6), of(0,7)},	// 5
		{of(4,1), of(4,1), of(3,1), of(3,2), of(1,2), of(0,3), of(0,4), of(0,5), of(0,6), of(0,7), of(0,8)},	// 6
	};
	
	@ParameterizedTest
	@MethodSource("generateArguments")
	void testResult(int dieRoll, int attStr, int defStr, int expectedColumn) {
		Results result = MilitaryCombatResultsTable.result(dieRoll, attStr, defStr);
		assertNotNull(result);
		Result expectedResult = expectedResults[dieRoll -1][expectedColumn < 1 ? 1 : (expectedColumn > 9 ? 9 : expectedColumn)];
		assertTrue(expectedResult.equals(result), ()->"expected (" + expectedResult + ") but was (" + result + ").");
	}

	static Stream<Arguments> generateArguments() {
		List<Arguments> result = new ArrayList<>(expectedResults.length * 6);
		for (int die = 1; die < 7; die++) {
			int expectedColumn = 0;
			for (int defStr = 6; defStr > 1; defStr--) {
				result.add(Arguments.arguments(die, 1, defStr, expectedColumn++));
			}
			for (int attStr = 1; attStr < 7; attStr++) {
				result.add(Arguments.arguments(die, attStr, 1, expectedColumn++));
			}
		}
		return result.stream();
	}
	
//	@Test
//	void testResult_InvalidRoll() {
//		MilitaryCombatResultsTable.result(0, 0, 0);
//	}
	@ParameterizedTest
	@CsvSource({
		"0, 0",
		"0, 1",
		"1, 0",
		"-1, -1",
		"-1, 1",
		"1, -1",
	})
	void testResult_InvalidStrength(int aStr, int dStr) {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->MilitaryCombatResultsTable.result(1, aStr, dStr));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString(Integer.toString(aStr)), containsString(Integer.toString(dStr)), containsString("must both be > 0")));
	}

	@ParameterizedTest
	@CsvSource({
		"1, 1, 1",
		"2, 1, 2",
		"3, 2, 2",
	})
	void testDivideRoundUp(int larger, int smaller, int expectedResult) {
		assertEquals(expectedResult, MilitaryCombatResultsTable.divideRoundUp(larger, smaller));
	}

	@ParameterizedTest
	@CsvSource({
		"1, 1, 1",
		"2, 1, 2",
		"3, 2, 1",
	})
	void testDivideRoundDown(int larger, int smaller, int expectedResult) {
		assertEquals(expectedResult, MilitaryCombatResultsTable.divideRoundDown(larger, smaller));
	}

	@ParameterizedTest
	@CsvSource({
		"1, 1, 0",
		"8, 7, 0",
		"2, 1, 1",
		"3, 2, 0",
		"4, 2, 1",
		"5, 2, 1",
		"6, 2, 2",
		"7, 2, 2",
		"8, 2, 3",
		"9, 2, 3",
		"10, 2, 4",
		"11, 2, 4",
		"12, 2, 5",
		"13, 2, 5",
		"14, 2, 6",
		"15, 2, 6",
		"16, 2, 7",
		"7, 8, -1",
		"1, 2, -1",
		"2, 3, -1",
		"2, 4, -1",
		"2, 5, -2",
		"2, 6, -2",
		"2, 7, -3",
		"2, 8, -3",
		"2, 9, -4",
		"2, 10,-4",
		"2, 11, -5",
		"2, 12, -5",
		"2, 13, -6",
		"2, 14, -6",
		"2, 15, -7",
		"2, 16, -7",
	})
	void testCalcColumnShift(int attacker, int defender, int expectedResult) {
		assertEquals(expectedResult, MilitaryCombatResultsTable.calcColumnShift(attacker, defender));
		
	}

	@ParameterizedTest
	@CsvSource({
		"1, 1, 5",
		"8, 7, 5",
		"2, 1, 6",
		"3, 2, 5",
		"10, 2, 9",
		"11, 2, 9",
		"12, 2, 9",
		"13, 2, 9",
		"14, 2, 9",
		"7, 8, 4",
		"1, 2, 4",
		"2, 9, 1",
		"2, 10,1",
		"2, 11, 1",
		"2, 12, 1",
		"2, 13, 1",
	})
	void testDetermineRawColumn(int attacker, int defender, int expectedResult) {
		assertEquals(expectedResult, MilitaryCombatResultsTable.determineRawColumn(attacker, defender));
		
	}

}
