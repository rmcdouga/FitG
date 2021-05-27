package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGameSpaceship implements Spaceship {
	Explorer(33, 2, 2, 4, 8),
	Galactic_Freighter(34, 0, 1, 0, 16),
	Interstellar_Sloop(35, 2, 1, 2, 4),
	Planetary_Privateer(36, 3, 2, 3, 6),
	Solar_Merchant(37, 0, 1, 1, 14),
	Star_Cruiser(38, 1, 2, 2, 10),
	Stellar_Courier(39, 2, 3, 4, 4),
	S_XIII(40, 0, 4, 6, 5),	// See Case 14.58. Province and Galactic Games: Add one to Pilot's Navigation rating for Hyperjump
	;

	private final int cardNumber;
	private final int cannons;
	private final int shields;
	private final int maneuver;
	private final int maxPassengers;
	
	private BaseGameSpaceship(int cardNumber, int cannons, int shields, int maneuver, int maxPassengers) {
		this.cardNumber = cardNumber;
		this.cannons = cannons;
		this.shields = shields;
		this.maneuver = maneuver;
		this.maxPassengers = maxPassengers;
	}

	@Override
	public int cardNumber() {
		return this.cardNumber;
	}

	@Override
	public int cannons() {
		return this.cannons;
	}

	@Override
	public int shields() {
		return this.shields;
	}

	@Override
	public int maneuver() {
		return this.maneuver;
	}

	@Override
	public int maxPassengers() {
		return this.maxPassengers;
	}

	public static Stream<BaseGameSpaceship> stream() {
		return Stream.of(values());
	}
	
	public static Stream<BaseGameSpaceship> stream(Predicate<BaseGameSpaceship> predicate) {
		return stream().filter(predicate);
	}
}
