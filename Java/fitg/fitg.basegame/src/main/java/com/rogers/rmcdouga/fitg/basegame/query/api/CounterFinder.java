package com.rogers.rmcdouga.fitg.basegame.query.api;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public interface CounterFinder {
	Optional<Counter> findCounter(String unitType, String location);
	Optional<Counter> findCounter(String unitId);
	
	// TODO: Find Stack containing a counter with the given id.
	// Optional<Stack> findStackWithCounter(String unitId);
	
	// Remove and non-alphanumeric characters and convert to lower case
	public static String normalizeId(String str) {
		return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}
}
