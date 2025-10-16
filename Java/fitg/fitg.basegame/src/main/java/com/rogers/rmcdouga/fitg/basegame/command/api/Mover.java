package com.rogers.rmcdouga.fitg.basegame.command.api;

import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public interface Mover {
	/**
	 * Move a non-unique unit (i.e. a military unit).
	 * 
	 * @param unitType - unit type
	 * @param location - Can be a planet/environ combo or a planet/orbit combo
	 * @param destination - Can be a planet/environ combo or a planet/orbit combo (are intermediate stars eligible destinations?) 
	 */
	default Mover moveUnit(String unitType, String location, String destination) { return this; };

	/**
	 * Move a unique unit (e.g. a character)
	 * 
	 * @param unitId -
	 * @param destination
	 */
	default Mover moveUnit(String unitId, String destination) { return this; };
	
	void move(BaseGameCharacter character, Location destination);
	void move(Stack stack, Location destination);
	void move(Unit unit, Location destination);
	
}
