package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.rogers.rmcdouga.fitg.basegame.tables.DetectionTable.Result.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.tables.DetectionTable.Result;

class DetectionTableTest {
	
	private static final Result[][] expectedResults = {
			// 0   1    2    3   4   5   6   7   8   9   10
			{ Dd,  D_,  U,   U,  U,  U,  U,  U,  U,  U,  U }, // 1
			{ Dd,  D_,  D_,  D,  U,  U,  U,  U,  U,  U,  U }, // 2
			{ Dd,  Dd,  D_,  D_, D,  U,  U,  U,  U,  U,  U }, // 3
			{ E_,  Dd,  Dd,  D_, D_, D,  D,  U,  U,  U,  U }, // 4
			{ E_,  Dd,  Dd,  Dd, D_, D_, D_, D,  D,  U,  U }, // 5
			{ E_,  E_,  E_,  Dd, Dd, D_, D_, D_, D_, D,  D }, // 6
	};

	@ParameterizedTest
	@MethodSource("generatePairs") 
	void testRoll(Integer dieRoll, Integer evasionValue) {
		assertEquals(expectedResults[dieRoll - 1][evasionValue], DetectionTable.result(dieRoll, evasionValue));
	}

	static Stream<Arguments> generatePairs() {
		return IntStream.range(0, 11)
				 .boxed()
				 .flatMap(ev->IntStream.range(1, 7)
						 				.boxed()
						 				.map(die->Arguments.arguments(die, ev))
				  );
	}
	
	@Test
	void testRoll_InvalidEvasionValue() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->DetectionTable.result(1, -1));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertEquals("Evasion Value out of range (-1).", msg);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 7, 100000})
	void testRoll_InvalidRollValue(int value) {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->DetectionTable.result(value, 01));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertEquals("Die roll out of range (" + value + ").", msg);
	}
}
