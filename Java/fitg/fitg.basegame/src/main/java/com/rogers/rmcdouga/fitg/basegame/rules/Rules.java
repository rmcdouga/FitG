package com.rogers.rmcdouga.fitg.basegame.rules;

import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public interface Rules {

	// TODO: Get the current location of the character
	// TODO: Compare the current location to the destination and ensure that it is valid
	Set<Location> validMoves(Counter counter);
}
