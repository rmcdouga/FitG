package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

/**
 * This class represents the pool of available Counters.
 *
 */
public interface CounterPool {
	
	Optional<Unit> getCounter(Unit unitType);
	CounterPool returnCounter(Unit unit);
	Optional<Spaceship> getSpaceship(ImperialSpaceship spaceshipType);
	CounterPool returnSpaceship(ImperialSpaceship spaceship);
}
