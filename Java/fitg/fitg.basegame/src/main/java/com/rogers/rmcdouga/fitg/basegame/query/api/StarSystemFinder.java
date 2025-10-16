package com.rogers.rmcdouga.fitg.basegame.query.api;

import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public interface StarSystemFinder {
	StarSystem findByName(String name);
	StarSystem findById(int id);
}
