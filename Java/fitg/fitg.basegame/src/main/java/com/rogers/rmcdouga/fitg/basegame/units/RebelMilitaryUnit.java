package com.rogers.rmcdouga.fitg.basegame.units;

import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit.RebelMilitaryUnitData;
import com.rogers.rmcdouga.fitg.basegame.utils.Model;

public enum RebelMilitaryUnit implements Unit, Model<RebelMilitaryUnitData> {
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
	
	public record RebelMilitaryUnitData(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {};
	
	private final RebelMilitaryUnitData rebelMilitaryUnitData;

	private RebelMilitaryUnit(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {
		this(new RebelMilitaryUnitData(environCombatStrength, spaceCombatStrength, isMobile, numUnits));
	}

	private RebelMilitaryUnit(RebelMilitaryUnitData rebelMilitaryUnitData) {
		this.rebelMilitaryUnitData = rebelMilitaryUnitData;
	}

	@Override
	public int environCombatStrength() {
		return rebelMilitaryUnitData.environCombatStrength;
	}

	@Override
	public int spaceCombatStrength() {
		return rebelMilitaryUnitData.spaceCombatStrength;
	}

	@Override
	public boolean isMobile() {
		return rebelMilitaryUnitData.isMobile;
	}

	public int numUnits() {
		return rebelMilitaryUnitData.numUnits;
	}

	@Override
	public RebelMilitaryUnitData model() {
		return rebelMilitaryUnitData;
	}
}
