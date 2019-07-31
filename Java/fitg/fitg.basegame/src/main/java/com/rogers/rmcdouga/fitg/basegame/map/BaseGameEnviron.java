package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

public class BaseGameEnviron implements Environ {
	private final EnvironType type;
	private final int size;
	private final int resources;
	private final boolean starResources;
	private final int coupRating;
	private final List<RaceType> races;
	
	private BaseGameEnviron(EnvironType type, int size, int resources, boolean starResources, int coupRating,
			List<BaseGameRaceType> races) {
		super();
		this.type = type;
		this.size = size;
		this.resources = resources;
		this.starResources = starResources;
		this.coupRating = coupRating;
		this.races = List.copyOf(races);
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
	
	public static Environ of(EnvironType type, int size, int resources, boolean starResources, int coupRating, List<BaseGameRaceType> races) {
		return new BaseGameEnviron(type, size, resources, starResources, coupRating, races);
	}
}
