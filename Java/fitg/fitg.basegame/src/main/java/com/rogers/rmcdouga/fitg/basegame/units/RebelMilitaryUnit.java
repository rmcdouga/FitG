package com.rogers.rmcdouga.fitg.basegame.units;

public enum RebelMilitaryUnit implements Unit {
	Air_1_0(1, 0, false, 2),
	Air_2_1(2, 1, true, 2),
	Air_2_3(2, 3, true, 3),
	Air_4_3(4, 3, true, 3),
	Fire_1_0(1, 0, false, 2),
	Fire_2_1(2, 1, true, 3),
	Fire_2_3(2, 3, true, 1),
//	Fire_4_3(4, 3, true, 3),		// Doesn't exist
	Liquid_1_0(1, 0, false, 2),
	Liquid_2_1(2, 1, true, 4),
	Liquid_2_3(2, 3, true, 1),
	Liquid_4_3(4, 3, true, 4),
	Subterranean_1_0(1, 0, false, 5),
	Subterranean_2_1(2, 1, true, 3),
	Subterranean_2_3(2, 3, true, 3),
	Subterranean_4_3(4, 3, true, 2),
	Urban_1_0(1, 0, false, 6),
	Urban_2_1(2, 1, true, 5),
	Urban_2_3(2, 3, true, 6),
	Urban_4_3(4, 3, true, 6),
	Urban_4_4_Elite(4, 4, true, 5),
	Wild_1_0(1, 0, false, 8),
	Wild_2_1(2, 1, true, 7),
	Wild_2_3(2, 3, true, 7),
	Wild_4_3(4, 3, true, 6),
	Wild_4_4_Elite(4, 4, true, 4),
	;
	
	private final int environCombatStrength;
	private final int spaceCombatStrength;
	private final boolean isMobile;
	private final int numUnits;

	private RebelMilitaryUnit(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {
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

	public int getNumUnits() {
		return numUnits;
	}
}
