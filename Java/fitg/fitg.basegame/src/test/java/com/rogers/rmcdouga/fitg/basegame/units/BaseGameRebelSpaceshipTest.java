package com.rogers.rmcdouga.fitg.basegame.units;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class BaseGameRebelSpaceshipTest {

	@ParameterizedTest
	@EnumSource
	void testOf_BaseGameRebelSpaceship(BaseGameRebelSpaceship ss) {
		assertSame(ss, BaseGameRebelSpaceship.of((RebelSpaceship)ss));
	}

	@Test
	void testOf_NonBaseGameRebelSpaceship() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->BaseGameRebelSpaceship.of(new RebelSpaceship() {
			
			@Override
			public int cardNumber() {
				return 0;
			}
			
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
		}));
		String msg = ex.getMessage();
		assertNotNull(msg);
//		System.out.println(msg);
		assertThat(msg, containsString("not a BaseGameRebelSpaceship"));
	}

}
