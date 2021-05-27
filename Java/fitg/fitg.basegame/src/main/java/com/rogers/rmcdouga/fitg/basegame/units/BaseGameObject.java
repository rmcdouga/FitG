package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGameObject implements Object {
	// TODO: Create implementation for the object abilities.
	Helian_Drug(43),
		// Adds two to any one characteristic of the owner for one Game-Turn (see Case 14.4)
		// When the drug is used once, it is removed from play.
	Scanner(44),
		// Reduces PDB effectiveness by one level during Emeny detection.
		// Adds two to hiding value of characters undergoing Enemy search (exception:has no effect on search by Enemy characters).
	Scrambler(45),
		// Adds four to hiding value of characters undergoing Enemy Search.
		// Halves combat strength of Irate Locals.
		// Places Enemy PDB on same planet Down.
		// After each use, roll the die. On a roll of 3 or higher, the scrambler is inoperative.
	MediKit_Of_Ptolus(46),
		// Immediately heals all wounds incurred by owner or characters with owner (see Case 13.72). May not be used between rounds of one combat.
		// After each use, roll the die. On a roll of 6, the Medi-kit is inoperative.
	Personal_Body_Shield(47),
		// Subtract one from any character combat result incurred by owner or group that the owner is with (in a firefight, the subtraction is made before doubling).
	Cache_Of_Rare_Gems(48),
		// May purchase any available spaceship (choose one from the Possession Deck), except the S-XIII.
		// Negates an Eneny Gather Information mission (I) in the same Environ.
		// Receives two bonus draws for missions A, C, D, E, S, F, I, or H.
		// Lost after one use (see Case 14.23).
	Advisor_Andriod(52),
		// Adds one to the owner's Diplomacy and Intelligence ratings.
		// If on a Diplomacy mission, ig-nore first "Abort Diplomacy" Action Event.
		// Galactic Game: Reveals Planet Secret (see Case 31.21).
	;

	private final int cardNumber;
	
	private BaseGameObject(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public int cardNumber() {
		return this.cardNumber;
	}

	public static Stream<BaseGameObject> stream() {
		return Stream.of(values());
	}
	
	public static Stream<BaseGameObject> stream(Predicate<BaseGameObject> predicate) {
		return stream().filter(predicate);
	}

}
