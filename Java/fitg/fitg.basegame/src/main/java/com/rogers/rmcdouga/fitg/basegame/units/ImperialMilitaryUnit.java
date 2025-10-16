package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;

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
	private final String id;


	private ImperialMilitaryUnit(int environCombatStrength, int spaceCombatStrength, boolean isMobile, int numUnits) {
		this.environCombatStrength = environCombatStrength;
		this.spaceCombatStrength = spaceCombatStrength;
		this.isMobile = isMobile;
		this.numUnits = numUnits;
		this.id = CounterFinder.normalizeId(this.toString());
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

	@Override
	public String id( ) {
		return this.id;
	}

	public int numUnits() {
		return numUnits;
	}
	
	public static Stream<ImperialMilitaryUnit> stream() {
		return Stream.of(ImperialMilitaryUnit.values());
	}

	public static Stream<ImperialMilitaryUnit> stream(Predicate<ImperialMilitaryUnit> predicate) {
		return ImperialMilitaryUnit.stream().filter(predicate);
	}
}
