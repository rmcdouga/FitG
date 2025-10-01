package com.rogers.rmcdouga.fitg.basegame.query.api;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Environ.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

public interface EnvironFinder {
	Environ findEnviron(Planet planet, EnvironType environType);
	Environ findEnviron(String planetName, String environType);
	Environ findEnviron(int planetId, String environType);
}
