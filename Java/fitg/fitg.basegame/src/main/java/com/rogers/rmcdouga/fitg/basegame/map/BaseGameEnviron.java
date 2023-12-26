package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnviron.BaseGameEnvironData;
import com.rogers.rmcdouga.fitg.basegame.utils.Model;

public class BaseGameEnviron implements Environ, Model<BaseGameEnvironData> {

	public record BaseGameEnvironData(EnvironType type, int size, OptionalInt resources, boolean starResources, OptionalInt coupRating,
		List<RaceType> races, Optional<BaseGameCreature> creature, Optional<BaseGameSovereign> sovereign) {};
	
	private  final BaseGameEnvironData baseGameEnvironData;
	
	private BaseGameEnviron(BaseGameEnvironData gaseGameEnvironData) {
		this.baseGameEnvironData = gaseGameEnvironData;
	}

	private BaseGameEnviron(EnvironType type, int size, OptionalInt resources, boolean starResources, OptionalInt coupRating,
			List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign) {
		this(new BaseGameEnvironData(type, size, resources, starResources, coupRating,
				List.copyOf(races), Optional.ofNullable(creature), Optional.ofNullable(sovereign)));
	}

	/**
	 * @return the type
	 */
	@Override
	public EnvironType getType() {
		return baseGameEnvironData.type;
	}

	/**
	 * @return the size
	 */
	@Override
	public int getSize() {
		return baseGameEnvironData.size;
	}

	/**
	 * @return the resources
	 */
	@Override
	public OptionalInt getResources() {
		return baseGameEnvironData.resources;
	}

	/**
	 * @return the starResources
	 */
	@Override
	public boolean isStarResources() {
		return baseGameEnvironData.starResources;
	}

	/**
	 * @return the coupRating
	 */
	@Override
	public OptionalInt getCoupRating() {
		return baseGameEnvironData.coupRating;
	}

	/**
	 * @return the races
	 */
	@Override
	public List<RaceType> getRaces() {
		return baseGameEnvironData.races;
	}
	
	@Override
	public Optional<BaseGameCreature> getCreature() {
		return baseGameEnvironData.creature;
	}

	@Override
	public Optional<BaseGameSovereign> getSovereign() {
		return baseGameEnvironData.sovereign;
	}

	@Override
	public BaseGameEnvironData model() {
		return baseGameEnvironData;
	}

	private static BaseGameEnviron of(EnvironType type, int size, OptionalInt resources, boolean starResources, OptionalInt coupRating, List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign) {
		return new BaseGameEnviron(type, size, resources, starResources, coupRating, races, creature, sovereign);
	}
	
	public static Builder urban(int size) {
		return new Builder(BaseGameEnvironType.Urban, size);
	}
	
	public static Builder wild(int size) {
		return new Builder(BaseGameEnvironType.Wild, size);
	}
	
	public static Builder air(int size) {
		return new Builder(BaseGameEnvironType.Air, size);
	}
	
	public static Builder fire(int size) {
		return new Builder(BaseGameEnvironType.Fire, size);
	}
	
	public static Builder liquid(int size) {
		return new Builder(BaseGameEnvironType.Liquid, size);
	}
	
	public static Builder subterranian(int size) {
		return new Builder(BaseGameEnvironType.Subterranian, size);
	}
	
	public static class Builder {
		private EnvironType type;
		private int size;
		private OptionalInt resources = OptionalInt.empty();
		private boolean starResources = false;
		private OptionalInt coupRating = OptionalInt.empty();
		private List<BaseGameRaceType> races = List.of();
		private BaseGameCreature creature = null;
		private BaseGameSovereign sovereign = null;

		
		private Builder(EnvironType type, int size) {
			this.type = type;
			this.size = size;
		}
		
		public Builder resources(int resources) {
			if (resources < 1) {
				throw new IllegalArgumentException("Environ resources value must be > 0.");
			}
			this.resources = OptionalInt.of(resources);
			return this;
		}
		public Builder starResources() {
			this.starResources = true;
			return this;
		}
		public Builder coupRating(int coupRating) {
			this.coupRating = coupRating == 0 ? OptionalInt.empty() : OptionalInt.of(coupRating);
			return this;
		}
		public Builder races(BaseGameRaceType... races) {
			this.races = List.of(races);
			return this;
		}
		public Builder creature(BaseGameCreature creature) {
			this.creature = creature;
			return this;
		}
		public Builder sovereign(BaseGameSovereign sovereign) {
			this.sovereign = sovereign;
			return this;
		}
		public BaseGameEnviron build() {
			return BaseGameEnviron.of(type, size, resources, starResources, coupRating, races, creature, sovereign);
		}
	}
}
