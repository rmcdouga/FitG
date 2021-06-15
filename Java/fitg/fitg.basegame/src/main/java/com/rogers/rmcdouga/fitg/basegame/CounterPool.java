package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Unit;

/**
 * This class represents the pool of available Counters.
 *
 */
public interface CounterPool {
	
	Optional<Unit> getUnit(Unit unitType);
	CounterPool returnCounter(Unit unit);
}
