package com.rogers.rmcdouga.fitg.basegame.tables;

import static com.rogers.rmcdouga.fitg.basegame.tables.HyperjumpTable.Result.*;

public class HyperjumpTable {
	
	public enum Result {
		Drift_0(0, false, false),
		Drift_1(1, false, false),
		Drift_2(2, false, false),
		Drift_1D(1, true, false),
		Drift_2D(2, true, false),
		Drift_2E(2, false, true)
		;
		
		private final int driftValue;		// Which drift (0 = no drift)
		private final boolean damaged;		// Spaceship damaged or one unit in stack is lost.
		private final boolean eliminated;	// Spaceship eliminated or half the units in a stack are lost.

		private Result(int driftValue, boolean damaged, boolean eliminated) {
			this.driftValue = driftValue;
			this.damaged = damaged;
			this.eliminated = eliminated;
		}

		public int drift() {
			return driftValue;
		}

		public boolean isDamaged() {
			return damaged;
		}

		public boolean isEliminated() {
			return eliminated;
		}
	}
	
	private static final Result[] resultsTable = new Result[] { 
			Drift_0,	// 0
			Drift_1,	// 1
			Drift_0,	// 2
			Drift_0,	// 3
			Drift_0,	// 4
			Drift_1,	// 5
			Drift_0,	// 6
			Drift_2,	// 7
			Drift_1,	// 8
			Drift_2,	// 9
			Drift_1D,	// 10
			Drift_2D,	// 11
			Drift_2E,	// 12
			};
	
	public static Result result(int roll, int hyperjumpDistance, int navigationRating) {
		if (roll < 1 || hyperjumpDistance < 1 || navigationRating < 1) {
			throw new IllegalArgumentException("All arguments must be > 0.  Roll (" + roll + "), NavigationRating (" + navigationRating + "), Hyperjump Distance(" + hyperjumpDistance + ").");
		}
		if (navigationRating > hyperjumpDistance) {
			return resultsTable[0];
		}
		int resultIndex = hyperjumpDistance - navigationRating + roll;
		if (resultIndex > 11) {
			return resultsTable[12];			
		}
		return resultsTable[resultIndex];
	}
}
