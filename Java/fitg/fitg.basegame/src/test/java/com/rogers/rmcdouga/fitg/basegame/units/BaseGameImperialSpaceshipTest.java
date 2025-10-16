package com.rogers.rmcdouga.fitg.basegame.units;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BaseGameImperialSpaceshipTest {

	@ParameterizedTest
	@EnumSource
	void testOf_BaseGameImperialSpaceship(BaseGameImperialSpaceship ss) {
		assertSame(ss, BaseGameImperialSpaceship.of((ImperialSpaceship)ss));
	}

	@Test
	void testOf_NonBaseGameImperialSpaceship() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->BaseGameImperialSpaceship.of(new ImperialSpaceship() {
			
			@Override
			public int shields() {
				return 0;
			}
			
			@Override
			public int maxPassengers() {
				return 0;
			}
			
			@Override
			public int maneuver() {
				return 0;
			}
			
			@Override
			public int cannons() {
				return 0;
			}

			@Override
			public String id() {
				return null;
			}
		}));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, containsString("not a BaseGameImperialSpaceship"));
	}

}
