package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

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
	BaseGamePlanet getHomePlanet();

	/**
	 * @return the description
	 */
	String getDescription();

}