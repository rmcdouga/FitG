package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

public interface Environ extends Location {

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
	OptionalInt getResources();

	/**
	 * @return the starResources
	 */
	boolean isStarResources();

	/**
	 * @return the coupRating
	 */
	OptionalInt getCoupRating();

	/**
	 * @return the races
	 */
	List<RaceType> getRaces();

	/**
	 * @return creature
	 */
	Optional<BaseGameCreature> getCreature();

	/**
	 * @return sovereign
	 */
	Optional<BaseGameSovereign> getSovereign();

	/**
	 * Information that is common across environs.
	 *
	 */
	public interface EnvironType {

		/**
		 * Name of the EnvironType
		 */
		String getName();

		/**
		 * @return the isSpecial
		 */
		boolean isSpecial();

	}
}