package com.rogers.rmcdouga.fitg.basegame.units;

import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit.ImperialMilitaryUnitData;
import com.rogers.rmcdouga.fitg.basegame.utils.Model;

public enum ImperialMilitaryUnit implements Unit, Model<ImperialMilitaryUnitData> {
	Militia(1, 0, false, 35),
	Patrol(1, 2, true, 24),
	Line(3, 2, true, 17),
	Veteran(3, 4, true, 13),
	Elite_Army(1, 2, true, 8),
	Elite_Navy(1, 2, true, 8),
	SuicideSquad(1, 2, true, 2),
	;
	
	public record ImperialMilitaryUnitData(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {};
	
	private final ImperialMilitaryUnitData imperialMilitaryUnitData;
	
	private ImperialMilitaryUnit(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {
		this(new ImperialMilitaryUnitData(environCombatStrength, spaceCombatStrength, isMobile, numUnits));
	}

	private ImperialMilitaryUnit(ImperialMilitaryUnitData imperialMilitaryUnitData) {
		this.imperialMilitaryUnitData = imperialMilitaryUnitData;
	}

	@Override
	public int environCombatStrength() {
		return imperialMilitaryUnitData.environCombatStrength;
	}

	@Override
	public int spaceCombatStrength() {
		return imperialMilitaryUnitData.spaceCombatStrength;
	}

	@Override
	public boolean isMobile() {
		return imperialMilitaryUnitData.isMobile;
	}

	public int numUnits() {
		return imperialMilitaryUnitData.numUnits;
	}

	@Override
	public ImperialMilitaryUnitData model() {
		return imperialMilitaryUnitData;
	}
}
