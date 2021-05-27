package com.rogers.rmcdouga.fitg.basegame.units;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.CardTestUtils;

class BaseGamePossessionTest {

	@Test
	void testNumberOfPossessions() {
		Set<Integer> expectedCardNumbers = IntStream.range(33, 53)
													.mapToObj(Integer::valueOf)
													.collect(Collectors.toSet());
		Set<Integer> actualCardNumbers = BaseGamePossession.stream()
												.map(c->c.cardNumber())
												.collect(Collectors.toSet());
		assertIterableEquals(expectedCardNumbers, actualCardNumbers, ()->CardTestUtils.reportDifferences(expectedCardNumbers, actualCardNumbers));
	}


}
