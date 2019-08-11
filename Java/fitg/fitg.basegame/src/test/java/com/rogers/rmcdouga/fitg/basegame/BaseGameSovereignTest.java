package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign.Allegiance;

class BaseGameSovereignTest {

	@Test
	void testGetSpaceLeadership() {
		int count = 0;
		for(BaseGameSovereign sovereign : BaseGameSovereign.values()) {
			if (sovereign.getSpaceLeadership() > 0) {
				count++;
			}
		}
		assertEquals(3, count, "Expected 3 sovereigns to have space leadership (one imperial, one rebel and one neutral).");
	}

	@Test
	void testSetCurrentAllegiance_Rebel() {
		BaseGameSovereign underTest = Yaldor;
		assertEquals(Allegiance.Rebel, underTest.getCurrentAllegiance());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.setCurrentAllegiance(Allegiance.Imperial));
		String msg = ex.getMessage();
		assertTrue(msg.contains(underTest.getName()));
		assertTrue(msg.contains(Allegiance.Rebel.toString()));
	}

	@Test
	void testSetCurrentAllegiance_Imperial() {
		BaseGameSovereign underTest = Ascaill;
		assertEquals(Allegiance.Imperial, underTest.getCurrentAllegiance());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.setCurrentAllegiance(Allegiance.Rebel));
		String msg = ex.getMessage();
		assertTrue(msg.contains(underTest.getName()));
		assertTrue(msg.contains(Allegiance.Imperial.toString()));
	}

	@Test
	void testSetCurrentAllegiance_Neutral() {
		// Make sure he's Neutral
		BaseGameSovereign underTest = Balgar;
		assertEquals(Allegiance.Neutral, underTest.getCurrentAllegiance());
		
		// Set to Non-neutral
		underTest.setCurrentAllegiance(Allegiance.Rebel);
		assertEquals(Allegiance.Rebel, underTest.getCurrentAllegiance());

		// Make sure that setting it a second time generates an exception.
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.setCurrentAllegiance(Allegiance.Imperial));
		String msg = ex.getMessage();
		assertTrue(msg.contains(underTest.getName()));
		assertTrue(msg.contains(Allegiance.Rebel.toString()));
	}

	@Test
	void testAllegianceTotals() {
		Map<Allegiance, Integer> allegiances = new EnumMap<>(Allegiance.class);
		for(Allegiance allegiance : Allegiance.values()) {
			allegiances.put(allegiance, 0);
		}
		for(BaseGameSovereign sovereign : BaseGameSovereign.values()) {
			allegiances.compute(sovereign.getCurrentAllegiance(), (k,v)->Integer.valueOf((v.intValue() + 1)));
		}
		assertEquals(3, allegiances.size());	// Expect three types of allegiances
		for( Map.Entry<Allegiance, Integer> entry : allegiances.entrySet() ) {
			// All entries should be 4 since there are 4 of each type of sovereign
			assertEquals(4, entry.getValue().intValue(), ()->"Incorrect # of sovereigns with " + entry.getKey() + " allegiance.");
		}
	}

}
