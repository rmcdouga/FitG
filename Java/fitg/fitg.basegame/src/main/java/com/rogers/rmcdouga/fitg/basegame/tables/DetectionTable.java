package com.rogers.rmcdouga.fitg.basegame.tables;

import static com.rogers.rmcdouga.fitg.basegame.tables.DetectionTable.Result.*;

public enum DetectionTable {
	Column0(Column.create(Dd, Dd, Dd, E_, E_, E_)),
	Column1(Column.create(D_, D_, Dd, Dd, Dd, E_)),
	Column2(Column.create(U, D_, D_, Dd, Dd, E_)),
	Column3(Column.create(U, D, D_, D_, Dd, Dd)),
	Column4(Column.create(U, U, D, D_, D_, Dd)),
	Column56(Column.create(U, U, U, D, D_, D_)),
	Column78(Column.create(U, U, U, U, D, D_)),
	Column9_(Column.create(U, U, U, U, U, D)),
	;
	
	private Column results;
	
	private DetectionTable(Column results) {
		this.results = results;
	}

	private Result result(int roll) {
		return results.result(roll);
	}
	
	public enum Result {
		E_(true),	// E  - Eliminated 
		Dd(false),	// Dd - Detected and Damaged
		D_(true), 	// D* - Detected and fleet detachment may attack Spaceships
		D(false), 	// D  - Detected
		U(false);	// U  - Undetected
		
		private final boolean fleetMayAttackSpaceships;

		private Result(boolean fleetMayAttackSpaceships) {
			this.fleetMayAttackSpaceships = fleetMayAttackSpaceships;
		}

		public boolean fleetMayAttackSpaceships() {
			return this.fleetMayAttackSpaceships;
		}
	}
	
	private static class Column {
		private Result[] results;

		private Column(Result[] results) {
			this.results = results;
		}
		
		public Result result(int roll) {
			return results[roll - 1];
		}
		
		public static Column create(Result... results) {
			if (results.length != 6) {
				throw new IllegalArgumentException("Column needs exactly 6 results!");
			}
			return new Column(results);
		}
	}
	
	public static Result roll(int evasionValue) {
		return result(Dice.D6.roll(), evasionValue);
	}
	
	static Result result(int roll, int evasionValue) {
		if (evasionValue < 0) {
			throw new IllegalArgumentException("Evasion Value out of range (" + evasionValue + ").");
		} else if (roll < 1 || roll > 6) {
			throw new IllegalArgumentException("Die roll out of range (" + roll + ").");
		}
		DetectionTable column = switch(evasionValue) {
		case 0  -> Column0;
		case 1  -> Column1;
		case 2  -> Column2;
		case 3  -> Column3;
		case 4  -> Column4;
		case 5,6  -> Column56;
		case 7,8  -> Column78;
		default -> Column9_;
		};
				
		return column.result(roll);
	}
}
