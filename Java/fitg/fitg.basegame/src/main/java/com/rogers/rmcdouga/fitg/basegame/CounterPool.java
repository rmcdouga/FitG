package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.units.Unit;

/**
 * This class represents the pool of available Counters.
 *
 */
public interface CounterPool {
	
	Unit getUnit(Unit unitType);
	CounterPool returnCounter(Unit unit);
}
