package com.rogers.rmcdouga.fitg.basegame.tables;

public enum CharacterCombatResultsTable {
	// negative values represent positive losses with a captured defender, -10 is a loss of zero with a captured defender.
	Column1(4, 6, CombatResult.of(4, 4, 3, 3, 2, 2), CombatResult.of(0, 0, 0, 0, 0, 1)),		// -7 or less
	Column2(4, 6, CombatResult.of(3, 3, -3, 2, 2, 1), CombatResult.of(0, 0, 0, 0, 0, 1)),		// -6 to -4
	Column3(3, 5, CombatResult.of(3, 2, 2, -2, 1, 0), CombatResult.of(0, 0, 0, 0, 1, 1)),		// -3 or -2
	Column4(3, 5, CombatResult.of(2, -2, 2, 1, 1, 0), CombatResult.of(0, 0, 0, -1, 1, 1)),		// -1
	Column5(3, 4, CombatResult.of(2, -2, 1, 1, 0, 0), CombatResult.of(0, 0, -10, 1, 1, 2)),		// 0
	Column6(2, 4, CombatResult.of(-2, 1, 1, 1, 0, 0), CombatResult.of(0, 0, 1, -1, 1, 2)),		// +1
	Column7(2, 4, CombatResult.of(2, -1, 1, 0, 0, 0), CombatResult.of(0, 1, 1, -1, -2, 2)),		// +2 or +3
	Column8(2, 3, CombatResult.of(1, -1, 1, 0, -10, 0), CombatResult.of(1, 1, -1, -2, 2, 3)),	// +4 to +6
	Column9(2, 3, CombatResult.of(1, -1, 0, 0, 0, 0), CombatResult.of(-1, 2, -2, 3, -3, 3)),	// +7 to +10
	Column10(1,3, CombatResult.of(1, 0, 0, 0, 0, 0), CombatResult.of(2, 2, 3, 3, 4, 4)),		// +11 or more
	;
	
	private final int activeBreakoffValue;
	private final int inactiveBreakoffValue;
	private final CombatResult[] attackerResult;
	private final CombatResult[] defenderResult;
	
	private CharacterCombatResultsTable(int activeBreakoffValue, int inactiveBreakoffValue,	
			CombatResult[] attackerResult, CombatResult[] defenderResult) {
		this.activeBreakoffValue = activeBreakoffValue;
		this.inactiveBreakoffValue = inactiveBreakoffValue;
		this.attackerResult = attackerResult;
		this.defenderResult = defenderResult;
	}

	/**
	 * This interface is the set of Attacker decisions that need to be made during the course of the combat,
	 * For instance, the attacker decides whether this is a kill or capture combat.
	 */
	public interface AttackerStrategy {
		public enum CombatTyoe {
			Kill, Capture;
		}
		// TODO:  Fill in the rest of this interface.
		
		public CombatTyoe combatType();
	}
	
	/**
	 * This interface is the set of Defender decisions the need to be made in the course of the combat.
	 * Examples include which defenders are active or inactive, and whether to attempt a break-off
	 */
	public interface DefenderStrategy {
		// TODO:  Fill in the rest of this interface.
		
	}

	public enum BreakOffResult {	
		None, Inactive, All;
		
		public static BreakOffResult of(boolean active, boolean inactive) {
			if (active && inactive) return All;
			if (!active && !inactive) return None;
			if (inactive && !active) return Inactive;
			throw new IllegalArgumentException("Cannot have a BreakOffResult where active characters escape and inactive ones do not.");
		}
	};
	
	public static BreakOffResult attemptBreakOff(int roll, int combatDifferential) {
		CharacterCombatResultsTable column = CharacterCombatResultsTable.column(combatDifferential);
		return BreakOffResult.of(roll <= column.activeBreakoffValue, roll <= column.inactiveBreakoffValue);
	}
	
	public static record CombatResult(int loss, boolean characterCaptured) {
		public static CombatResult of(int loss, boolean characterCaptured) {
			return new CombatResult(loss, characterCaptured);
		}
		public static CombatResult of(int loss) {
			return new CombatResult(loss, false);
		}
		
		public static CombatResult[] of(int... values) {
			if (values.length != 6) {
				throw new IllegalArgumentException("Result table length must be 6 but was " + values.length + ".");
			}
			CombatResult[] result = new CombatResult[6];
			for (int i = 0; i < 6; i++) {
				result[i] = values[i] < 0 ? new CombatResult(Math.abs(values[i]) % 10, true) : new CombatResult(values[i], false);
			}
			return result;
		}
	}
	
	public static CombatResult defenderResult(int roll, int combatDifferential) {
		return column(combatDifferential).defenderResult[roll - 1];
	}

	public static CombatResult attackerResult(int roll, int combatDifferential) {
		return column(combatDifferential).attackerResult[roll - 1];
	}
	
	private static CharacterCombatResultsTable column(int combatDifferentual) {
		if (combatDifferentual <= -7) {
			return Column1;
		}
		if (combatDifferentual >= 11) {
			return Column10;
		}
		return switch(combatDifferentual) {
			case -6, -5, -4 -> Column2;
			case -3, -2 -> Column3;
			case -1 -> Column4;
			case 0 -> Column5;
			case 1 -> Column6;
			case 2, 3 -> Column7;
			case 4, 5, 6 -> Column8;
			case 7, 8, 9, 10 -> Column9;
			default -> throw new IllegalStateException("Unexpected combatDifferentialValue (" + combatDifferentual + "). This should never happen!");	
		};
	}
}
