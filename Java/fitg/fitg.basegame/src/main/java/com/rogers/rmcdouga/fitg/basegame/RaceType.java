package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.tables.Squad;

public interface RaceType {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @return the isStarFaring
	 */
	boolean isStarFaring();

	/**
	 * @return the homePlanet
	 */
	Optional<BaseGamePlanet> getHomePlanet();

	/**
	 * @return the description
	 */
	Optional<String> getDescription();

	/**
	 * @return the environ type
	 */
	Optional<BaseGameEnvironType> getEnvironType();

	/**
	 * @param environ
	 * @return the Squad type associated with this race and environ (if any)
	 */
	Optional<Squad> getSquad(BaseGameEnvironType environ);
}