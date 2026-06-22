package com.rogers.rmcdouga.fitg.basegame.command.api.external;

public interface Mover {
	/**
	 * Move a non-unique unit (i.e. a military unit).
	 * 
	 * @param unitType - unit type
	 * @param location - Can be a planet/environ combo or a planet/orbit combo
	 * @param destination - Can be a planet/environ combo or a planet/orbit combo (are intermediate stars eligible destinations?) 
	 */
	Mover moveUnit(String unitType, String location, String destination);

	/**
	 * Move a unique unit (e.g. a character)
	 * 
	 * @param unitId -
	 * @param destination
	 */
	Mover moveUnit(String unitId, String destination);
	
}
