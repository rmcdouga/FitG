package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.tables.HyperjumpTable.Result;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;

class HyperjumpTableTest {

	@ParameterizedTest(name = "hyperjumpDistance={0}, navigationRating={1}, roll={2}")
	@MethodSource("generateArguments")
	void testResult(int hyperjumpDistance, int navigationRating, int roll) {
		Result result = HyperjumpTable.result(roll, hyperjumpDistance, navigationRating);
		int modifiedRoll = navigationRating > hyperjumpDistance ? 0 :hyperjumpDistance - navigationRating + roll;
		assertEquals(expectedDrift(modifiedRoll), result.drift());
		assertEquals(expectedDamage(modifiedRoll), result.isDamaged());
		assertEquals(expectedElimination(modifiedRoll), result.isEliminated());
	}

	private static int expectedDrift(int modifiedRoll) {
		if (modifiedRoll < 1) {
			return 0;
		}
		return switch(modifiedRoll) {
		case 2, 3, 4, 6 ->0;
		case 1, 5, 8, 10 -> 1;
		default -> 2;
		};
	}

	private static boolean expectedDamage(int modifiedRoll) {
		return switch(modifiedRoll) {
		case 10, 11 -> true;
		default -> false;
		};
	}
	
	private static boolean expectedElimination(int modifiedRoll) {
		return modifiedRoll > 11 ? true : false;
	}
	
	static Stream<Arguments> generateArguments() {
		return IntStream.range(1, 8)					// hyperjump distance (1-7)
			    .boxed()
			    .flatMap(hj->IntStream.range(1, 6)		// navigation rating (1-6)
			    					  .boxed()
			    					  .flatMap(nr->IntStream.range(1, 7)
			    							  				.mapToObj(r->Arguments.arguments(hj, nr, r))
			    							  )
			    		)
		;
		
	}

	@Disabled("This is not really a test, it just calculates the largest navigation rating among all characters.")
	@Test
	void testNavigation() {
		System.out.println("MaxNavRating = " + BaseGameCharacter.stream().mapToInt(BaseGameCharacter::navigation).max().getAsInt());
	}
	
}
