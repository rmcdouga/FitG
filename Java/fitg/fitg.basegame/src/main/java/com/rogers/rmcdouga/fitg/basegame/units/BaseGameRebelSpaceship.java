package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship.BaseGameRebelSpaceshipData;
import com.rogers.rmcdouga.fitg.basegame.utils.Model;

public enum BaseGameRebelSpaceship implements RebelSpaceship, Model<BaseGameRebelSpaceshipData> {
	Explorer(33, 2, 2, 4, 8),
	Galactic_Freighter(34, 0, 1, 0, 16),
	Interstellar_Sloop(35, 2, 1, 2, 4),
	Planetary_Privateer(36, 3, 2, 3, 6),
	Solar_Merchant(37, 0, 1, 1, 14),
	Star_Cruiser(38, 1, 2, 2, 10),
	Stellar_Courier(39, 2, 3, 4, 4),
	S_XIII(40, 0, 4, 6, 5),	// See Case 14.58. Province and Galactic Games: Add one to Pilot's Navigation rating for Hyperjump
	;

	public record BaseGameRebelSpaceshipData(int cardNumber, int cannons, int shields, int maneuver, int maxPassengers) {};
	
	private final BaseGameRebelSpaceshipData baseGameRebelSpaceshipData;
	
	private BaseGameRebelSpaceship(int cardNumber, int cannons, int shields, int maneuver, int maxPassengers) {
		this(new BaseGameRebelSpaceshipData(cardNumber, cannons, shields, maneuver, maxPassengers));
	}

	private BaseGameRebelSpaceship(BaseGameRebelSpaceshipData baseGameRebelSpaceshipData) {
		this.baseGameRebelSpaceshipData = baseGameRebelSpaceshipData;
	}

	@Override
	public int cardNumber() {
		return baseGameRebelSpaceshipData.cardNumber;
	}

	@Override
	public int cannons() {
		return baseGameRebelSpaceshipData.cannons;
	}

	@Override
	public int shields() {
		return baseGameRebelSpaceshipData.shields;
	}

	@Override
	public int maneuver() {
		return baseGameRebelSpaceshipData.maneuver;
	}

	@Override
	public int maxPassengers() {
		return baseGameRebelSpaceshipData.maxPassengers;
	}

	public static Stream<BaseGameRebelSpaceship> stream() {
		return Stream.of(values());
	}
	
	public static Stream<BaseGameRebelSpaceship> stream(Predicate<BaseGameRebelSpaceship> predicate) {
		return stream().filter(predicate);
	}
	
	public static BaseGameRebelSpaceship of(RebelSpaceship spaceship) {
		if (spaceship instanceof BaseGameRebelSpaceship bgss) {
			return bgss;
		}
		throw new IllegalArgumentException("RebelSpaceship (" + spaceship.toString() + ") is not a BaseGameRebelSpaceship.");
	}

	@Override
	public BaseGameRebelSpaceshipData model() {
		return baseGameRebelSpaceshipData;
	}
}
