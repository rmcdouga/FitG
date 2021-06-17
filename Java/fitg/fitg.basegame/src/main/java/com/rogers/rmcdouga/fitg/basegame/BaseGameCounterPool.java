package com.rogers.rmcdouga.fitg.basegame;

import static java.util.function.Predicate.not;

import java.lang.reflect.Array;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public class BaseGameCounterPool implements CounterPool {

	private static Map<ImperialMilitaryUnit, Set<Unit>> imperialUnits = new EnumMap<>(ImperialMilitaryUnit.class);
	private static Map<RebelMilitaryUnit, Set<Unit>> rebelUnits = new EnumMap<>(RebelMilitaryUnit.class);
	
	private BaseGameCounterPool() {
		for (ImperialMilitaryUnit impUnit : ImperialMilitaryUnit.values()) {
			imperialUnits.put(impUnit, Set.of(generateImperialUnits(impUnit)));
		}
		for (RebelMilitaryUnit rebUnit : RebelMilitaryUnit.values()) {
			rebelUnits.put(rebUnit, Set.of(generateRebelUnits(rebUnit)));
		}
	}

	private GenericUnit<ImperialMilitaryUnit>[] generateImperialUnits(ImperialMilitaryUnit impUnit) {
		@SuppressWarnings("unchecked")
		GenericUnit<ImperialMilitaryUnit>[] units = (GenericUnit<ImperialMilitaryUnit>[])Array.newInstance(GenericUnit.class, impUnit.numUnits());
		for(int i = 0; i < impUnit.numUnits(); i++) {
			units[i] = new GenericUnit<ImperialMilitaryUnit>(impUnit, i);
		}
		return units;
	}

	private GenericUnit<RebelMilitaryUnit>[] generateRebelUnits(RebelMilitaryUnit rebUnit) {
		@SuppressWarnings("unchecked")
		GenericUnit<RebelMilitaryUnit>[] units = (GenericUnit<RebelMilitaryUnit>[])Array.newInstance(GenericUnit.class, rebUnit.numUnits());
		for(int i = 0; i < rebUnit.numUnits(); i++) {
			units[i] = new GenericUnit<RebelMilitaryUnit>(rebUnit, i);
		}
		return units;
	}

	@Override
	public Optional<Unit> getCounter(Unit unitType) {
		if (unitType instanceof ImperialMilitaryUnit impUnit) {
			return imperialUnits.get(impUnit)
								.stream()
								.map(GenericUnit.class::cast)
								.filter(not(GenericUnit::isUsed))	// Find first unused unit
								.findFirst()
								.map(GenericUnit::used);			// Mark it used.
		} else if (unitType instanceof RebelMilitaryUnit rebUnit) {
			return rebelUnits.get(rebUnit)
								.stream()
								.map(GenericUnit.class::cast)
								.filter(not(GenericUnit::isUsed))	// Find first unused unit
								.findFirst()
								.map(GenericUnit::used);			// Mark it used.
			
		}
		throw new IllegalArgumentException("Invalid unit type supplied (" + unitType.getClass().getName() + ").");
	}

	@Override
	public CounterPool returnCounter(Unit unit) {
		if (unit instanceof GenericUnit<?> genUnit) {
			genUnit.unUsed();			// Mark it as unused.
			return this;
		}
		throw new IllegalArgumentException("Invalid unit supplied (" + unit.getClass().getName() + ").");
	}

	public static CounterPool create() {
		return new BaseGameCounterPool();
	}

//	private static createImperialUnit(ImperialMilitaryUnit unit, int counter) {
//		return new Unit
//	}
	
	private static class GenericUnit<T extends Unit> implements Unit {

		private final T unitType;
		private final int unitNum;
		private boolean used;
		
		private GenericUnit(T unitType, int unitNum) {
			this.unitType = unitType;
			this.unitNum = unitNum;
		}
	
		@Override
		public int environCombatStrength() {
			return unitType.environCombatStrength();
		}
	
		@Override
		public int spaceCombatStrength() {
			return unitType.spaceCombatStrength();
		}
	
		@Override
		public boolean isMobile() {
			return unitType.isMobile();
		}

		private T unitType() {
			return unitType;
		}

		private int unitNum() {
			return unitNum;
		}

		public boolean isUsed() {
			return used;
		}

		public GenericUnit<T> used() {
			this.used = true;
			return this;
		}

		public GenericUnit<T> unUsed() {
			this.used = false;
			return this;
		}
	}

//	private static class GenericImperialUnit implements Unit {
//
//		private final ImperialMilitaryUnit unitType;
//		private final int unitNum;
//		private boolean used;
//		
//		private GenericImperialUnit(ImperialMilitaryUnit unitType, int unitNum) {
//			this.unitType = unitType;
//			this.unitNum = unitNum;
//			this.used = false;
//		}
//	
//		@Override
//		public int environCombatStrength() {
//			return unitType.environCombatStrength();
//		}
//	
//		@Override
//		public int spaceCombatStrength() {
//			return unitType.spaceCombatStrength();
//		}
//	
//		@Override
//		public boolean isMobile() {
//			return unitType.isMobile();
//		}
//
//		private ImperialMilitaryUnit unitType() {
//			return unitType;
//		}
//
//		private int unitNum() {
//			return unitNum;
//		}
//
//		public boolean isUsed() {
//			return used;
//		}
//
//		public GenericImperialUnit used() {
//			this.used = true;
//			return this;
//		}
//
//		public GenericImperialUnit unUsed() {
//			this.used = false;
//			return this;
//		}
//	}
//
//	private static class GenericRebelUnit implements Unit {
//
//		private final RebelMilitaryUnit unitType;
//		private final int unitNum;
//		private boolean used;
//		
//		private GenericRebelUnit(RebelMilitaryUnit unitType, int unitNum) {
//			this.unitType = unitType;
//			this.unitNum = unitNum;
//			this.used = false;
//		}
//	
//		@Override
//		public int environCombatStrength() {
//			return unitType.environCombatStrength();
//		}
//	
//		@Override
//		public int spaceCombatStrength() {
//			return unitType.spaceCombatStrength();
//		}
//	
//		@Override
//		public boolean isMobile() {
//			return unitType.isMobile();
//		}
//
//
//		private RebelMilitaryUnit unitType() {
//			return unitType;
//		}
//
//		private int unitNum() {
//			return unitNum;
//		}
//
//		public boolean isUsed() {
//			return used;
//		}
//
//		public GenericRebelUnit used() {
//			this.used = true;
//			return this;
//		}
//
//		public GenericRebelUnit unUsed() {
//			this.used = false;
//			return this;
//		}
//	}
}
