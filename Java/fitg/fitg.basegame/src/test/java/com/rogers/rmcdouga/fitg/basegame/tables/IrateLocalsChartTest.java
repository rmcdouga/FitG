package com.rogers.rmcdouga.fitg.basegame.tables;

import static org.junit.jupiter.api.Assertions.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType.*;

import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;

class IrateLocalsChartTest {

	Map<BaseGameEnvironType, Squad> rhones = Map.of(Urban, Squad.of(5, 4), Wild, Squad.of(4, 4), Subterranian, Squad.of(4, 4));
	Map<BaseGameEnvironType, Squad> saurians = Map.of(Urban, Squad.of(5, 6), Wild, Squad.of(5, 4), Subterranian, Squad.of(5, 2), Liquid, Squad.of(5, 2));
	Map<BaseGameEnvironType, Squad> segundens = Map.of(Urban, Squad.of(6, 4), Liquid, Squad.of(5, 2));
	Map<BaseGameEnvironType, Squad> suvans = Map.of(Urban, Squad.of(6, 2), Liquid, Squad.of(7, 4));
	Map<BaseGameEnvironType, Squad> piorads = Map.of(Urban, Squad.of(4, 4), Subterranian, Squad.of(6, 6));
	Map<BaseGameEnvironType, Squad> xanthons = Map.of(Fire, Squad.of(8, 6));
	Map<BaseGameEnvironType, Squad> yesters = Map.of(Urban, Squad.of(6, 2), Air, Squad.of(6, 4));
	Map<BaseGameEnvironType, Squad> kayns = Map.of(Wild, Squad.of(7, 6), Subterranian, Squad.of(6, 4));
	
	Map<BaseGameRaceType, Map<BaseGameEnvironType, Squad>> sfExpectedResults = 
			Map.of(Rhone, rhones, Saurian, saurians, Segunden, segundens, Suvan, suvans, Piorad, piorads, Xanthon, xanthons, Yester, yesters, Kayn, kayns);

	static record NSFResult(BaseGameEnvironType environ, Squad squad) {
		static Map.Entry<BaseGameRaceType, NSFResult> entry(BaseGameRaceType race, BaseGameEnvironType environ, Squad squad) {
			return new SimpleImmutableEntry<>(race, new NSFResult(environ, squad));
		}
	};
	
	Map<BaseGameRaceType, NSFResult> nsfExpectedResults = Map.ofEntries(
			NSFResult.entry(Anon, Air, Squad.of(4, 2, false)),
			NSFResult.entry(Ardorats, Wild, Squad.of(4, 4)),
			NSFResult.entry(Bork, Wild, Squad.of(8, 2)),
			NSFResult.entry(Calma, Subterranian, Squad.of(5, 4)),
			NSFResult.entry(Charkhans, Wild, Squad.of(5, 4)),
			NSFResult.entry(Cavalkus, Air, Squad.of(4, 3, false)),
			NSFResult.entry(Deaxins, Wild, Squad.of(5, 5, false)),
			NSFResult.entry(Illias, Wild, Squad.of(4, 4)),
			NSFResult.entry(Henone, Liquid, Squad.of(6, 6)),
			NSFResult.entry(Kirts, Wild, Squad.of(5, 4)),
			NSFResult.entry(Jopers, Urban, Squad.of(6, 2)),
			NSFResult.entry(Leonid, Wild, Squad.of(6, 4)),
			NSFResult.entry(Moghas, Wild, Squad.of(7, 3, false)),
			NSFResult.entry(Mowev, Wild, Squad.of(4, 4, false)),
			NSFResult.entry(Ornotins, Urban, Squad.of(6, 4)),
			NSFResult.entry(Phans, Liquid, Squad.of(4, 4)),
			NSFResult.entry(Rylians, Subterranian, Squad.of(8, 2, false)),
			NSFResult.entry(Susperans, Urban, Squad.of(4, 4)),
			NSFResult.entry(Theshian, Wild, Squad.of(4, 2)),
			NSFResult.entry(Thoks, Wild, Squad.of(6, 5, false)),
			NSFResult.entry(Ultraks, Urban, Squad.of(4, 4)),
			NSFResult.entry(Urgaks, Wild, Squad.of(6, 4)),
			NSFResult.entry(Ursi, Wild, Squad.of(7, 6, false))
			);
	
	@ParameterizedTest
	@MethodSource("generateScenarios")
	void testResult(BaseGameRaceType race, BaseGameEnvironType environ) {
		if (race.isStarFaring()) {
			Squad expectedResult = sfExpectedResults.get(race).get(environ);
			if (expectedResult == null) {
				testForException(race, environ);
			} else {
				assertEquals(expectedResult, IrateLocalsChart.result(race, environ));
			}
		} else {
			NSFResult nsfResult = nsfExpectedResults.get(race);
			BaseGameEnvironType expectedEnviron = nsfResult.environ();
			Squad expectedResult = nsfResult.squad();
			if (expectedEnviron == environ) {
				assertEquals(expectedResult, IrateLocalsChart.result(race, environ));
			} else {
				testForException(race, environ);
			}
		}
	}

	private void testForException(BaseGameRaceType race, BaseGameEnvironType environ) {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->IrateLocalsChart.result(race, environ));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertTrue(msg.contains("do not exist in"));
		assertTrue(msg.contains(race.getName()));
		assertTrue(msg.contains(environ.getName()));
	}

	static Stream<Arguments> generateScenarios() {
		return Stream.of(BaseGameRaceType.values())
					 .flatMap(r->Stream.of(BaseGameEnvironType.values())
							 		   .map(e->Arguments.arguments(r, e))
							 )
			;
	}
	
}
