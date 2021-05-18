package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoyaltyTypeTest {

	// Patriotic, Loyal, Neutral, Dissent, Unrest
	@Test
	public void testNumberOfLoyaltyTypes() {
		assertEquals(5, BaseGameLoyaltyType.values().length);
	}
	
	
	@ParameterizedTest
	@CsvSource({"Unrest, Dissent", "Dissent, Neutral", "Neutral, Loyal", "Loyal, Patriotic", "Patriotic, Patriotic"})
	public void testShiftLeft(BaseGameLoyaltyType source, BaseGameLoyaltyType result) {
		assertEquals(source.shiftLeft(), result);
	}

	@ParameterizedTest
	@CsvSource({"Patriotic, Loyal", "Loyal, Neutral", "Neutral, Dissent", "Dissent, Unrest", "Unrest, Unrest"})
	public void testShiftRight(BaseGameLoyaltyType source, BaseGameLoyaltyType result) {
		assertEquals(source.shiftRight(), result);
	}

}
