package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

public class BaseGameEnviron implements Environ {
	private final EnvironType type;
	private final int size;
	private final int resources;
	private final boolean starResources;
	private final int coupRating;
	private final List<RaceType> races;
	private final BaseGameCreature creature;
	private final BaseGameSovereign sovereign;
	
	private BaseGameEnviron(EnvironType type, int size, int resources, boolean starResources, int coupRating,
			List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign) {
		super();
		this.type = type;
		this.size = size;
		this.resources = resources;
		this.starResources = starResources;
		this.coupRating = coupRating;
		this.races = List.copyOf(races);
		this.creature = creature;
		this.sovereign = sovereign;
	}

	/**
	 * @return the type
	 */
	@Override
	public EnvironType getType() {
		return type;
	}

	/**
	 * @return the size
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * @return the resources
	 */
	@Override
	public int getResources() {
		return resources;
	}

	/**
	 * @return the starResources
	 */
	@Override
	public boolean isStarResources() {
		return starResources;
	}

	/**
	 * @return the coupRating
	 */
	@Override
	public int getCoupRating() {
		return coupRating;
	}

	/**
	 * @return the races
	 */
	@Override
	public List<RaceType> getRaces() {
		return races;
	}
	
	@Override
	public BaseGameCreature getCreature() {
		return creature;
	}

	@Override
	public BaseGameSovereign getSovereign() {
		return sovereign;
	}

	public static Environ of(EnvironType type, int size, int resources, boolean starResources, int coupRating, List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign) {
		return new BaseGameEnviron(type, size, resources, starResources, coupRating, races, creature, sovereign);
	}
}
