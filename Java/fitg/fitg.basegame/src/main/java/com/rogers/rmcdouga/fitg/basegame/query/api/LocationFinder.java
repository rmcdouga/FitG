package com.rogers.rmcdouga.fitg.basegame.query.api;

import com.rogers.rmcdouga.fitg.basegame.map.Location;

public interface LocationFinder {
	/**
	 * Finds the location on the given star or planet.
	 * 
	 * If the starOrPlanetId is numeric, it is treated as an id, otherwise as a name.
	 * 
	 * @param starOrPlanetId Name or numeric id of a star system or planet
	 * @param location	environ type or in orbit for planets, drift/drift2 for star systems
	 * @return
	 */
	Location findLocation(String starOrPlanetId, String location);
}
