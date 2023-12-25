package com.rogers.rmcdouga.fitg.basegame.units;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.ImperialSpaceshipData;
import com.rogers.rmcdouga.fitg.basegame.utils.Model;

public enum BaseGameImperialSpaceship implements ImperialSpaceship, Model<ImperialSpaceshipData> {
	Redjacs_Spaceship(4, 2, 3, 4),
	Imperial_Spaceship(2, 1, 1, 4),
	;

	public static final int NUM_IMPERIAL_SPACESHIPS = 3;
	
	public record ImperialSpaceshipData(int maneuver, int cannons, int shields, int maxPassengers) {};

	private final ImperialSpaceshipData imperialSpaceshipData;

	private BaseGameImperialSpaceship(int maneuver, int cannons, int shields, int maxPassengers) {
		this(new ImperialSpaceshipData(maneuver, cannons, shields, maxPassengers));
	}
	
	private BaseGameImperialSpaceship(ImperialSpaceshipData imperialSpaceshipData) {
		this.imperialSpaceshipData = imperialSpaceshipData;
	}

	@Override
	public int maneuver() {
		return imperialSpaceshipData.maneuver;
	}

	@Override
	public int cannons() {
		return imperialSpaceshipData.cannons;
	}

	@Override
	public int shields() {
		return imperialSpaceshipData.shields;
	}

	@Override
	public int maxPassengers() {
		return imperialSpaceshipData.maxPassengers;
	}
	
	public static BaseGameImperialSpaceship of(ImperialSpaceship spaceship) {
		if (spaceship instanceof BaseGameImperialSpaceship bgss) {
			return bgss;
		}
		throw new IllegalArgumentException("ImperialSpaceship (" + spaceship.toString() + ") is not a BaseGameImperialSpaceship.");
	}

	@Override
	public ImperialSpaceshipData model() {
		return imperialSpaceshipData;
	}
}
