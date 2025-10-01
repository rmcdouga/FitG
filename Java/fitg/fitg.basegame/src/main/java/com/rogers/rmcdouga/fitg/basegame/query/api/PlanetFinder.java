package com.rogers.rmcdouga.fitg.basegame.query.api;

import com.rogers.rmcdouga.fitg.basegame.map.Planet;

public interface PlanetFinder {
	Planet findByName(String name);
	Planet findById(int id);
}
