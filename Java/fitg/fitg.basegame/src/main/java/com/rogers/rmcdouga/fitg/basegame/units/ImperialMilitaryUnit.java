package com.rogers.rmcdouga.fitg.basegame.units;

public enum ImperialMilitaryUnit implements Unit {
	Militia(1, 0, false, 35),
	Patrol(1, 2, true, 24),
	Line(3, 2, true, 17),
	Veteran(3, 4, true, 13),
	Elite_Army(1, 2, true, 8),
	Elite_Navy(1, 2, true, 8),
	SuicideSquad(1, 2, true, 2),
	;
	
	private final int environCombatStrength;
	private final int spaceCombatStrength;
	private final boolean isMobile;
	private final int numUnits;

	private ImperialMilitaryUnit(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {
		this.environCombatStrength = environCombatStrength;
		this.spaceCombatStrength = spaceCombatStrength;
		this.isMobile = isMobile;
		this.numUnits = numUnits;
	}

	@Override
	public int environCombatStrength() {
		return this.environCombatStrength;
	}

	@Override
	public int spaceCombatStrength() {
		return this.spaceCombatStrength;
	}

	@Override
	public boolean isMobile() {
		return this.isMobile;
	}

	public int numUnits() {
		return numUnits;
	}
}
