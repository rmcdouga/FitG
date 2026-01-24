package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.IMPERIAL;
import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.REBEL;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Adam_Starlight;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Agan_Rafa;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Barca;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Boccanegra;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.Imperial_Spaceship;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship.Stellar_Courier;
import static com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit.Line;
import static com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit.Veteran;
import static com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit.Urban_1_0;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;

class BaseGameCounterTest {
	private static final StackManager stackMgr= new StackManager();

	private record TestCounter(Counter counter, Faction faction) {};
	@SuppressWarnings("unused")
	private static final List<TestCounter> TEST_COUNTERS = List.of(
			new TestCounter(Adam_Starlight, REBEL),			// Rebel Character
			new TestCounter(Barca, IMPERIAL),				// Imperial Character	
			new TestCounter(Urban_1_0, REBEL),				// Rebel Military Unit
			new TestCounter(Line, IMPERIAL),				// Imperial Military Unit
			new TestCounter(Imperial_Spaceship, IMPERIAL),	// Imperial Spaceship
			new TestCounter(stackMgr.of(Adam_Starlight, Agan_Rafa, Boccanegra), REBEL),						// Rebel Stack
			new TestCounter(stackMgr.of(Barca, Veteran), IMPERIAL),											// Imperial Stack
			new TestCounter(stackMgr.of(Stellar_Courier, Adam_Starlight, Agan_Rafa, Boccanegra), REBEL),	// Rebel Spaceship Stack
			new TestCounter(stackMgr.of((Spaceship)Imperial_Spaceship, Barca), IMPERIAL)					// Imperial Spaceship Stack
			);
	

	@ParameterizedTest
	@FieldSource("TEST_COUNTERS")
	void testFactionOfCounter(TestCounter testCounter) {
		assertEquals(testCounter.faction,  BaseGameCounter.factionOfCounter(testCounter.counter));
	}

	@ParameterizedTest
	@FieldSource("TEST_COUNTERS")
	void testIsFactionCounter(TestCounter testCounter) {
		switch(testCounter.faction) {
			case REBEL -> testIsFactionCounter(testCounter.counter, REBEL, IMPERIAL);
			case IMPERIAL -> testIsFactionCounter(testCounter.counter, IMPERIAL, REBEL);
			default -> fail("TestCounter faction must be REBEL or IMPERIAL");
		}
	}

	private static void testIsFactionCounter(Counter counter, Faction expectedfaction, Faction otherfaction) {
		assertAll(
				() -> assertTrue(BaseGameCounter.isFactionCounter(expectedfaction, counter)),
				() -> assertFalse(BaseGameCounter.isFactionCounter(otherfaction, counter))
				);
	}

	@Test
	void testContainsFactionCounters() {
		assertAll(
				()->testContainsFactionCounters(filterCountersFor(REBEL), REBEL, IMPERIAL),
				()->testContainsFactionCounters(filterCountersFor(IMPERIAL), IMPERIAL, REBEL)
				);
	}
	
	private static List<Counter> filterCountersFor(Faction faction) {
		return TEST_COUNTERS.stream().filter(tc -> tc.faction == faction).map(tc -> tc.counter).toList();
	}
	
	private static void testContainsFactionCounters(Collection<Counter> counters, Faction expectedfaction, Faction otherfaction) {
		assertAll(
				() -> assertTrue(BaseGameCounter.containsFactionCounters(expectedfaction, counters)),
				() -> assertFalse(BaseGameCounter.containsFactionCounters(otherfaction, counters))
				);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"ADMIN", "NEUTRAL"})
	void testIsFactionCounter_UnimplementedFactions(Faction faction) {
		UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,  ()->BaseGameCounter.isFactionCounter(faction, Adam_Starlight));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IsFactionCounter should not be called for this faction: " + faction));
	}
	
}
