package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public interface CounterLocator {

	Collection<Counter> countersAt(Location location);
	CounterLocator move(Counter counter, Location destination);

}