package com.rogers.rmcdouga.fitg.basegame.command.api.external;

public interface Mover {
	/**
	 * Move a non-unique counter (i.e. a military unit).
	 * 
	 * The code assumes that all parameters have been validated against the map but not the
	 * game state (i.e. unitType is a valid unit type, starOrPlanetId and destinationStarOrPlanetId 
	 * are valid star system or planet ids, and location and destinationLocation are valid locations 
	 * within the star system or planet).  If any of these parameters are invalid, the behavior is 
	 * to thrown an exception.
	 * 
	 * @param unitType - unit type
	 * @param starOrPlanetId	Id of the star system or planet where the counter is located
	 * @param location			Location within the star system or planet where the counter is located
	 * @param destinationStarOrPlanetId	Destination of the star system or planet where the counter is located
	 * @param destinationLocation			Destination location within the star system or planet where the counter is located
	 */
	Mover moveUnitCounter(String unitType, String currentStarOrPlanetId, String currentLocation, 
										   String destinationStarOrPlanetId, String destinationLocation);

	/**
	 * Move a unique counter (e.g. a character)
	 * 
	 * The code assumes that all parameters have been validated against the map but not the
	 * game state (i.e. unitType is a valid unit type, starOrPlanetId and destinationStarOrPlanetId 
	 * are valid star system or planet ids, and location and destinationLocation are valid locations 
	 * within the star system or planet).  If any of these parameters are invalid, the behavior is 
	 * to thrown an exception.
	 * 
	 * @param unitId -
	 * @param destination
	 */
	Mover moveCounter(String counterId, String destinationStarOrPlanetId,
			String destinationLocation);
	
}
