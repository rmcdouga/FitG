package com.rogers.rmcdouga.fitg.basegame.tables;

import static com.rogers.rmcdouga.fitg.basegame.tables.MilitaryCombatResultsTable.Results.of;

public enum MilitaryCombatResultsTable {
	;
	
	public static record Results(int attackerStrLoss, int defenderStrLoss) {
		static Results of(int attackerLoss, int defenderLoss) {
				return new Results(attackerLoss, defenderLoss);
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

	public static Results roll(int attackerStrength, int defenderStrength) {
		return result(Dice.D6.roll(), attackerStrength, defenderStrength);
	}
	
	static Results result(int dieRoll, int attackerStrength, int defenderStrength) {
		if (!(attackerStrength > 0 && defenderStrength > 0)) {
			throw new IllegalArgumentException("AttackerStrength (" + attackerStrength + ") and DefenderStrength (" + defenderStrength + ") must both be > 0.");
		}
		return resultsTable[dieRoll - 1][determineRawColumn(attackerStrength, defenderStrength)];
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
