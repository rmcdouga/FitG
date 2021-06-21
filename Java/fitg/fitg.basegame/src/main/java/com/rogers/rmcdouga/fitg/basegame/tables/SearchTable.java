package com.rogers.rmcdouga.fitg.basegame.tables;

public enum SearchTable {
	row1(1, 2, 3, 4, 4, 5, 6, 6),
	row2(1, 1, 2, 3, 4, 4, 5, 6),
	row3(0, 1, 1, 2, 3, 4, 4, 5),
	row4(0, 0, 1, 1, 2, 2, 3, 4),
	row5(0, 0, 0, 1, 1, 1, 2, 3),
	;
	
	int[] rowValues;
	
	
	private SearchTable(int... rowValues) {
		this.rowValues = rowValues;
	}

	private int getRequiredNumber(int searchValue) {
		return rowValues[searchIndex(searchValue)];
	}

	private static SearchTable hidingIndex(int hidingValue) {
		if (hidingValue <= 1) return row1;
		if (hidingValue >= 8) return row5;
		return switch(hidingValue) {
		case 2,3 -> row2;
		case 4,5 -> row3;
		case 6,7 -> row4;
		default -> throw new IllegalStateException("This can't happen.");
		};
	}

	private static int searchIndex(int searchValue) {
		if (searchValue > 22) return 7;
		return switch(searchValue) {
		case 1 -> 0;
		case 2,3 -> 1;
		case 4,5,6 -> 2;
		case 7,8,9 -> 3;
		case 10,11,12,13 -> 4;
		case 14,15,16,17 -> 5;
		case 18,19,20,21,22 -> 6;
		default -> throw new IllegalStateException("This can't happen.");
		};
	}

	
	/**
	 * Makes a an appropriate roll for the Search table
	 * 
	 * We separate roll and result so that they can both be logged.  The typical code would look like:
	 * 
	 * 		int roll = SearchTable.roll();
	 *      boolean result = SearchTable.result(roll, searchVale, hidingValue);
	 *		log("search value was '" + searchVale + "', hiding value was '" + hidingValue + "',  rolled '" + roll + "' on search table and got found='" + result "'.");
	 * 
	 * @return value between 1 and 6
	 */
	public static int roll() {
		return Dice.D6.roll();
	}

	/**
	 * Rolls on the Search Table.
	 * 
	 * @param searchValue
	 * @param hidingValue
	 * @return result of search, true = found, false = not found 
	 */
	public static boolean roll(int searchValue, int hidingValue) {
		return result(roll(), searchValue, hidingValue);
	}
	
	/**
	 * Lookup the result of a die roll on the search table.
	 * 
	 * @param dieRoll
	 * @param searchValue
	 * @param hidingValue
	 * @return
	 */
	public static boolean result(int dieRoll, int searchValue, int hidingValue) {
		if (searchValue < 1) return false;
		int requiredNumber = hidingIndex(hidingValue).getRequiredNumber(searchValue);
		return dieRoll <= requiredNumber;
	}
}
