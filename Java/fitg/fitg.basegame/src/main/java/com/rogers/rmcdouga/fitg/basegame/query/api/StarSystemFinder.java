package com.rogers.rmcdouga.fitg.basegame.query.api;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public interface StarSystemFinder {
	Optional<StarSystem> findByName(String name);
	Optional<StarSystem> findById(int id);
}
