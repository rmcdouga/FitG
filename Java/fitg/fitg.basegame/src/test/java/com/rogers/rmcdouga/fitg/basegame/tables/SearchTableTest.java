package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SearchTableTest {

	private static boolean[][] expectedDieResults = {
			{ false, false, false, false, false, false },
			{  true, false, false, false, false, false },	// 1
			{  true,  true, false, false, false, false },	// 2
			{  true,  true,  true, false, false, false },	// 3
			{  true,  true,  true,  true, false, false },	// 4
			{  true,  true,  true,  true,  true, false },	// 5
			{  true,  true,  true,  true,  true,  true },
	};
	
	private static int[] searchValueBoundaries = { 0, 1, 2, 3, 4, 6, 7, 9, 10, 13, 14, 17, 18, 22, 23, 24};
	private static int[][] expectedResults = {	// Index into ExpectedDieResults
			// 0, 1, 2, 3, 4, 6, 7, 9, 10, 13, 14, 17, 18, 22, 23, 24
			{ 0, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 6, 6, 6, 6},	// 1 or less
			{ 0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 6, 6},	// 2,3
			{ 0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5},	// 4,5
			{ 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4},	// 6,7
			{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3},	// 8 or more
	};
	
	@ParameterizedTest(name = "hidingValue={0}, searchValue={1}, roll={2}")
	@MethodSource("generateArguments")
	void testResult(int hidingValue, int searchValue, int roll, int searchValueIndex) {
		boolean result = SearchTable.result(roll, searchValue, hidingValue);
		int expectedDieResultIndex = expectedResults[Math.min(4, hidingValue / 2)][searchValueIndex];
		boolean expectedResult = expectedDieResults[expectedDieResultIndex][roll - 1];
		assertEquals(expectedResult, result);
	}

	static Stream<Arguments> generateArguments() {
		List<Arguments> result = new ArrayList<>(expectedResults[0].length * 5);
			for (int hidingValue = 0; hidingValue < 9; hidingValue++) {
				for (int searchValueIndex = 0; searchValueIndex < searchValueBoundaries.length; searchValueIndex++) {
					for (int die = 1; die < 7; die++) {
					result.add(Arguments.arguments(hidingValue, searchValueBoundaries[searchValueIndex], die, searchValueIndex));
				}
			}
		}
		return result.stream();
	}
}
