package com.rogers.rmcdouga.fitg.basegame.query.api;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public interface CounterFinder {
	/**
	 * Find a counter by its type and location.  If the counter type is unique, the location parameters will be ignored.
	 * This does not find characters that are in a stack (including spaceships).
	 * 
	 * @param unitId			Id of the counter type to find
	 * @param starOrPlanetId	Id of the star system or planet where the counter is located
	 * @param location			Location within the star system or planet where the counter is located
	 * @return					Optional containing the counter if found, or an empty Optional if not found
	 */
	Optional<Counter> findCounter(String unitId, String starOrPlanetId, String location);

	/**
	 * Find a counter by its unique ID.  If the counter type is not unique, an IllegalArgumentException will be thrown.
	 * This does not find characters that are in a stack (including spaceships).
	 * 
	 * @param unitId	Id of the counter type to find
	 * @return			Optional containing the counter if found, or an empty Optional if not found
	 */
	Optional<Counter> findCounter(String unitId);
	
	/**
	 * Find a stack containing a counter of the specified type.
	 * 
	 * @param unitId	Id of the counter type to find
	 * @return			Optional containing a counter of the specified type if found in any stack, or an empty Optional if not found	
	 */
	Optional<Counter> findStackWithCounter(String unitId);
	
	/**
	 * Normalize a string to be used as an ID by removing all non-alphanumeric characters and converting to lower case.
	 * 
	 * @param str
	 * @return
	 */
	public static String normalizeId(String str) {
		return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}
}
