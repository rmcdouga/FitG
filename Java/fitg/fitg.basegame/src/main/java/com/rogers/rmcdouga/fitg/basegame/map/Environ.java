package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

public interface Environ {

	/**
	 * @return the type
	 */
	EnvironType getType();

	/**
	 * @return the size
	 */
	int getSize();

	/**
	 * @return the resources
	 */
	int getResources();

	/**
	 * @return the starResources
	 */
	boolean isStarResources();

	/**
	 * @return the coupRating
	 */
	int getCoupRating();

	/**
	 * @return the races
	 */
	List<RaceType> getRaces();

	/**
	 * @return creature
	 */
	BaseGameCreature getCreature();

	/**
	 * @return sovereign
	 */
	BaseGameSovereign getSovereign();

}