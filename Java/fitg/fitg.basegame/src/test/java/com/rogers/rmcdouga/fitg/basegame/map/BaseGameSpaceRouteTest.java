package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class BaseGameSpaceRouteTest {
	
	@Test
	void testNumNavigationStars() {
		int numNavigationStars = Stream.of(BaseGameSpaceRoute.values())
									   .mapToInt(BaseGameSpaceRoute::getNavigationStars)
									   .sum();
		assertEquals(20, numNavigationStars);	// Should be 20 Navigation Stars
	}

	@Test
	void testNumRoutes() {
		assertEquals(42, BaseGameSpaceRoute.values().length);	// Should be 20 Navigation Stars
	}

}
