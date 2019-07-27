package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoyaltyTypeTest {

	// Patriotic, Loyal, Neutral, Dissent, Unrest
	@Test
	public void testNumberOfLoyaltyTypes() {
		assertEquals(5, LoyaltyType.values().length);
	}
	
	
	@ParameterizedTest
	@CsvSource({"Unrest, Dissent", "Dissent, Neutral", "Neutral, Loyal", "Loyal, Patriotic", "Patriotic, Patriotic"})
	public void testShiftLeft(LoyaltyType source, LoyaltyType result) {
		assertEquals(source.shiftLeft(), result);
	}

	@ParameterizedTest
	@CsvSource({"Patriotic, Loyal", "Loyal, Neutral", "Neutral, Dissent", "Dissent, Unrest", "Unrest, Unrest"})
	public void testShiftRight(LoyaltyType source, LoyaltyType result) {
		assertEquals(source.shiftRight(), result);
	}

}
