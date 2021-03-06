package com.rogers.rmcdouga.fitg.basegame.tables;

import static com.rogers.rmcdouga.fitg.basegame.tables.MilitaryCombatResultsTable.Results.of;

public enum MilitaryCombatResultsTable {
	;
	
	public static record Results(int attackerStrLoss, int defenderStrLoss) {
		static Results of(int attackerLoss, int defenderLoss) {
				return new Results(attackerLoss, defenderLoss);
			}
	}
	
	public static class Modifier {
		public static final Modifier NO_SHIFT = new Modifier(0);
		public static final Modifier SHIFT_LEFT = shiftLeftOf(1);
		public static final Modifier SHIFT_RIGHT = shiftRightOf(1);
			
		private final int amount;

		private Modifier(int amount) {
			this.amount = amount;
		}

		public Modifier shiftLeft(int amount) {
			return new Modifier(this.amount - amount);
		}
		public Modifier shiftRight(int amount) {
			return new Modifier(this.amount + amount);
		}
		public Modifier shiftLeft() {
			return this.shiftLeft(1);
		}
		public Modifier shiftRight() {
			return this.shiftRight(1);
		}
		public Modifier combine(Modifier modifier) {
			return new Modifier(this.amount + modifier.amount);
		}

		public static Modifier shiftLeftOf(int amount) {
			return new Modifier(-amount);
		}
		public static Modifier shiftRightOf(int amount) {
			return new Modifier(amount);
		}
	}
	
	private static Results[][] resultsTable = {
		// 1-6		1-5		 1-4	  1-3	   1-2		1-1		 2-1	   3-1	   	4-1		5-1		 6-1
		{of(8,0), of(7,0), of(6,0), of(5,0), of(5,0), of(4,0), of(3,1), of(3,2), of(2,2), of(2,3), of(1,4)},	// 1
		{of(7,0), of(6,0), of(5,0), of(5,0), of(4,0), of(3,1), of(2,2), of(2,3), of(1,3), of(1,4), of(1,5)},	// 2
		{of(7,0), of(5,0), of(5,0), of(4,0), of(3,1), of(2,1), of(2,2), of(2,3), of(1,4), of(1,4), of(1,5)},	// 3
		{of(6,0), of(5,0), of(4,1), of(3,1), of(3,1), of(2,2), of(1,3), of(1,3), of(1,4), of(0,5), of(0,6)},	// 4
		{of(5,1), of(4,1), of(4,1), of(3,1), of(2,2), of(1,2), of(1,3), of(0,4), of(0,5), of(0,6), of(0,7)},	// 5
		{of(4,1), of(4,1), of(3,1), of(3,2), of(1,2), of(0,3), of(0,4), of(0,5), of(0,6), of(0,7), of(0,8)},	// 6
	};
	private static int MIDDLE_COLUMN = 5;
	private static int MAX_COLUMN = MIDDLE_COLUMN + 4;
	private static int MIN_COLUMN = MIDDLE_COLUMN - 4;

	/**
	 * Makes a an appropriate roll for the Military Combat Results table
	 * 
	 * We separate roll and result so that they can both be logged.  The typical code would look like:
	 * 
	 * 		int roll = MilitaryCombatResultsTable.roll();
	 *      Results result = MilitaryCombatResultsTable.result(roll, attackerStrength, defenderStrength);
	 *		log("attacker strength was '" + attackerStrength + "', defender strength was '" + defenderStrength + "',  rolled '" + roll + "' on military combat results table and got '" + result "'.");
	 * 
	 * @return value between 1 and 6
	 */
	public static int roll() {
		return Dice.D6.roll();
	}

	/**
	 * Generate a result from the Military Combat Results Table.
	 * 
	 * @param attackerStrength
	 * @param defenderStrength
	 * @return
	 */
	public static Results roll(int attackerStrength, int defenderStrength) {
		return result(roll(), attackerStrength, defenderStrength);
	}
	
	/**
	 * Lookup the result of a die roll on the Military Combat Results table
	 * 
	 * @param dieRoll
	 * @param attackerStrength
	 * @param defenderStrength
	 * @return
	 */
	public static Results result(int dieRoll, int attackerStrength, int defenderStrength) {
		return result(dieRoll, attackerStrength, defenderStrength, Modifier.NO_SHIFT);
	}
	
	static Results result(int dieRoll, int attackerStrength, int defenderStrength, Modifier modifier) {
		if (!(attackerStrength > 0 && defenderStrength > 0)) {
			throw new IllegalArgumentException("AttackerStrength (" + attackerStrength + ") and DefenderStrength (" + defenderStrength + ") must both be > 0.");
		}
		return resultsTable[dieRoll - 1][determineColumn(attackerStrength, defenderStrength, modifier)];
	}
	
	// Determine the raw column (before column shifts are applied.
	static int determineColumn(int attackerStrength, int defenderStrength, Modifier modifier) {
		return Math.max(Math.min(resultsTable[0].length - 1, determineRawColumn(attackerStrength, defenderStrength) + modifier.amount), 0);
	}
	
	// Determine the raw column (before column shifts are applied.
	static int determineRawColumn(int attackerStrength, int defenderStrength) {
		return Math.max(Math.min(MAX_COLUMN, MIDDLE_COLUMN + calcColumnShift(attackerStrength, defenderStrength)), MIN_COLUMN);
	}
	
	static int calcColumnShift(int attackerStrength, int defenderStrength) {
		if (attackerStrength == defenderStrength) {
			return 0;
		} else if (attackerStrength > defenderStrength) {
			return divideRoundDown(attackerStrength, defenderStrength) - 1;
		} else { // defenderStrength < attackerStrength
			return -divideRoundUp(defenderStrength, attackerStrength) + 1;
		}
	}
	
	static int divideRoundUp(int larger, int smaller) {
		int interim = larger / smaller;
		return interim * smaller == larger ? interim : interim + 1;
	}

	static int divideRoundDown(int larger, int smaller) {
		return larger / smaller;
	}
}
