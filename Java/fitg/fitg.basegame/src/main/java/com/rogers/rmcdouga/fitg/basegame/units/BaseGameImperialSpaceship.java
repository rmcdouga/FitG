package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;

public enum BaseGameImperialSpaceship implements ImperialSpaceship {
	Redjacs_Spaceship(4, 2, 3, 4),
	Imperial_Spaceship(2, 1, 1, 4),
	;

	public static final int NUM_IMPERIAL_SPACESHIPS = 3;
	
	private final int maneuver;
	private final int cannons;
	private final int shields;
	private final int maxPassengers;
	private final String id;
	
	private BaseGameImperialSpaceship(int maneuver, int cannons, int shields, int maxPassengers) {
		this.maneuver = maneuver;
		this.cannons = cannons;
		this.shields = shields;
		this.maxPassengers = maxPassengers;
		this.id = CounterFinder.normalizeId(this.toString());
	}

	@Override
	public int maneuver() {
		return maneuver;
	}

	@Override
	public int cannons() {
		return cannons;
	}

	@Override
	public int shields() {
		return shields;
	}

	@Override
	public int maxPassengers() {
		return maxPassengers;
	}

	@Override
	public String id() {
		return id;
	}
	
	public static BaseGameImperialSpaceship of(ImperialSpaceship spaceship) {
		if (spaceship instanceof BaseGameImperialSpaceship bgss) {
			return bgss;
		}
		throw new IllegalArgumentException("ImperialSpaceship (" + spaceship.toString() + ") is not a BaseGameImperialSpaceship.");
	}
	
	public static Stream<BaseGameImperialSpaceship> stream() {
		return Stream.of(BaseGameImperialSpaceship.values());
	}

	public static Stream<BaseGameImperialSpaceship> stream(Predicate<BaseGameImperialSpaceship> predicate) {
		return BaseGameImperialSpaceship.stream().filter(predicate);
	}
}
