package com.rogers.rmcdouga.fitg.basegame.query.api;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.map.Planet;

public interface PlanetFinder {
	Optional<Planet> findByName(String name);
	Optional<Planet> findById(int id);
}
