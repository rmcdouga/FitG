package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGameWeapon implements Weapon {
	// TODO: Create implementation for the weapon abilities.
	Snipers_Rifle(41),
		// Adds one to the owner's Combat Rating
		// Owner receives one bonus draw on Assassination mission (A).
	Assassins_Blade(42),
		// Owner receives three bonus draws on an Assassination mission (A).
		// Adds three to owner's Combat rating for one round of combat
		// When blade is used once, it is removed from play.
	;

	private final int cardNumber;
	
	private BaseGameWeapon(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public int cardNumber() {
		return this.cardNumber;
	}

	public static Stream<BaseGameWeapon> stream() {
		return Stream.of(values());
	}
	
	public static Stream<BaseGameWeapon> stream(Predicate<BaseGameWeapon> predicate) {
		return stream().filter(predicate);
	}	
}
