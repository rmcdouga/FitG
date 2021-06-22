package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SquadChartTest {
	private static record Results(int combat, int endurance) {};
	
	private static final Results[] expectedResults = {
			new Results(4, 4),		// 1
			new Results(6, 4),		// 2
			new Results(8, 6),		// 3,4
			new Results(10, 6),		// 5, 6, 7
			new Results(12, 8),		// 8, 9, 10, 11
			new Results(14, 8),		// 12+
	};

	@ParameterizedTest
	@CsvSource({"1, 0", "2, 1", "3, 2", "4, 2", "5, 3", "6, 3", "7, 3", "8, 4", "9, 4", "10, 4", "11, 4", "12, 5", "13, 5"})
	void testResult(int militaryStrength, int resultIndex) {
		Results expectedResult = expectedResults[resultIndex];
		Squad result = SquadChart.result(militaryStrength);
		assertAll(
				()->assertEquals(expectedResult.combat, result.combat()),
				()->assertEquals(expectedResult.endurance, result.endurance())
				);
	}

	@ParameterizedTest
	@CsvSource({"1, 3", "2, 3", "3, 3", "4, 4", "5, 4", "6, 4", "7, 4", "8, 5", "9, 5", "10, 5", "11, 5", "12, 5", "13, 5"})
	void testResultWithModifiers(int militaryStrength, int resultIndex) {
		Results expectedResult = expectedResults[resultIndex];
		Squad result = SquadChart.result(militaryStrength, SquadChart.Modifier.LEADER_PRESENT, SquadChart.Modifier.ELITE_PRESENT);
		assertAll(
				()->assertEquals(expectedResult.combat, result.combat()),
				()->assertEquals(expectedResult.endurance, result.endurance())
				);
	}

	
}
