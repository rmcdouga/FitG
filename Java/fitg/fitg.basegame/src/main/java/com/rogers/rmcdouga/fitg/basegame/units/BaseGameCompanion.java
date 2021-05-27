package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGameCompanion implements Companion {
	// TODO: Create implementation for the companion abilities.
	Cervac_MK_V(49),
		// Adds one to the owner's Intelligence rating.
		// Disables detected Enemy character on same planet. Disabled character may not do anything (inactive defender if attacked) for any one Phase.
		// When Cervac initiates disabling, roll the die. On a roll of 5 or 6, Cervac is inoperative (disabling still takes effect).
	Norrocks(50),
		// Adds two to owner's Combat rating (only if part of defending force).
		// Ignore first "Creature Attacks" Action Event when performing a mission.
		// May take one wound in combat, but is then removed from play.
		// After each use, roll the die. On a roll of 6, Norrocks is inoperative.
	Charsot(51),
		// Adds one to the owner's Diplomacy rating.
		// No creature may receive a sur-prise column shift when attacking the owner.
		// Ignore all attacks from creatures that attack with an Intelligence rating.
	;

	private final int cardNumber;
	
	private BaseGameCompanion(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public int cardNumber() {
		return this.cardNumber;
	}

	public static Stream<BaseGameCompanion> stream() {
		return Stream.of(values());
	}
	
	public static Stream<BaseGameCompanion> stream(Predicate<BaseGameCompanion> predicate) {
		return stream().filter(predicate);
	}

}
