package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public interface GameMap {

	public Collection<StarSystem> getStarSystems();
	public Planet getPlanetContaining(Environ environ);
}
