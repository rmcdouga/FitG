package com.rogers.rmcdouga.fitg.basegame.box;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.*;

import java.lang.reflect.Array;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

class BaseGameCounterPool implements CounterPool {

	private static Map<ImperialMilitaryUnit, Set<Unit>> imperialUnits = new EnumMap<>(ImperialMilitaryUnit.class);
	private static Map<RebelMilitaryUnit, Set<Unit>> rebelUnits = new EnumMap<>(RebelMilitaryUnit.class);
	private static Map<BaseGameImperialSpaceship, Set<Spaceship>> imperialSpaceships = new EnumMap<>(BaseGameImperialSpaceship.class);
	
	private BaseGameCounterPool() {
		for (ImperialMilitaryUnit impUnit : ImperialMilitaryUnit.values()) {
			imperialUnits.put(impUnit, Set.of(generateImperialUnits(impUnit)));
		}
		for (RebelMilitaryUnit rebUnit : RebelMilitaryUnit.values()) {
			rebelUnits.put(rebUnit, Set.of(generateRebelUnits(rebUnit)));
		}
		for (BaseGameImperialSpaceship impSpaceship : BaseGameImperialSpaceship.values()) {
			imperialSpaceships.put(impSpaceship, Set.of(generateImperialSpaceships(impSpaceship)));
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

	private Spaceship[] generateImperialSpaceships(BaseGameImperialSpaceship impSpaceship) {
		return switch(impSpaceship) {
		case Redjacs_Spaceship -> new Spaceship[] { new GenericImperialSpaceship<ImperialSpaceship>(Redjacs_Spaceship, 0) };
		case Imperial_Spaceship -> {
				Spaceship[] result = new Spaceship[NUM_IMPERIAL_SPACESHIPS];
				for(int i = 0; i < NUM_IMPERIAL_SPACESHIPS; i++) {
					result[i] = new GenericImperialSpaceship<ImperialSpaceship>(Imperial_Spaceship, i);
				}
				yield result;
			}
		};
	}

	@Override
	public Optional<Unit> getCounter(Unit unitType) {
		if (unitType instanceof ImperialMilitaryUnit impUnit) {
			return imperialUnits.get(impUnit)
								.stream()
								.map(GenericUnit.class::cast)
								.filter(GenericUnit::isAvailable)	// Find first available unit
								.findFirst()
								.map(GenericUnit::used);			// Mark it used.
		} else if (unitType instanceof RebelMilitaryUnit rebUnit) {
			return rebelUnits.get(rebUnit)
								.stream()
								.map(GenericUnit.class::cast)
								.filter(GenericUnit::isAvailable)	// Find first available unit
								.findFirst()
								.map(GenericUnit::used);			// Mark it used.
			
		}
		throw new IllegalArgumentException("Invalid unit type supplied (" + unitType.getClass().getName() + ").");
	}

	@Override
	public Optional<Spaceship> getSpaceship(ImperialSpaceship spaceshipType) {
		if (spaceshipType instanceof BaseGameImperialSpaceship impSpaceship) {
			return imperialSpaceships.get(impSpaceship)
									 .stream()
									 .map(GenericImperialSpaceship.class::cast)
									 .filter(GenericImperialSpaceship::isAvailable)	// Find first available spaceship
									 .findFirst()
									 .map(GenericImperialSpaceship::used);			// Mark it used			
		}
		throw new IllegalArgumentException("Invalid spaceship type supplied (" + spaceshipType.getClass().getName() + ").");
	}

	@Override
	public CounterPool returnCounter(Unit unit) {
		GenericUnit.requireGu(unit).available();
		return this;
	}

	@Override
	public CounterPool returnSpaceship(ImperialSpaceship spaceship) {
		GenericImperialSpaceship.requireGis(spaceship).available();
		return this;
	}

	@Override
	public CounterPool removeFromPlay(Unit unit) {
		GenericUnit.requireGu(unit).removeFromPlay();
		return this;
	}

	@Override
	public CounterPool removeFromPlay(ImperialSpaceship spaceship) {
		GenericImperialSpaceship.requireGis(spaceship).removeFromPlay();
		return this;
	}

	public static CounterPool create() {
		return new BaseGameCounterPool();
	}

	private enum Status {
		AVAILABLE, USED, OUT_OF_PLAY;
	}

//	private static createImperialUnit(ImperialMilitaryUnit unit, int counter) {
//		return new Unit
//	}
	
	private static class GenericUnit<T extends Unit> implements Unit {

		private final T unitType;
		private final int unitNum;
		private Status status;

		private GenericUnit(T unitType, int unitNum) {
			this.unitType = unitType;
			this.unitNum = unitNum;
			this.status = Status.AVAILABLE;
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

		public boolean isAvailable() {
			return status == Status.AVAILABLE;
		}

		public GenericUnit<T> used() {
			this.status = Status.USED;
			return this;
		}

		public GenericUnit<T> available() {
			this.status = Status.AVAILABLE;
			return this;
		}

		public GenericUnit<T> removeFromPlay() {
			this.status = Status.OUT_OF_PLAY;
			return this;
		}

		@Override
		public String toString() {
			return "GenericUnit [unitType=" + unitType + ", unitNum=" + unitNum + ", status=" + status + "]";
		}
		
		public static GenericUnit<?> requireGu(Unit unit) {
			if (unit instanceof GenericUnit<?> genUnit) {
				return genUnit;
			}
			throw new IllegalArgumentException("Invalid unit supplied (" + unit.getClass().getName() + ").");
		}

		@Override
		public String id() {
			return unitType.id();
		}
	}

	private static class GenericImperialSpaceship<T extends ImperialSpaceship> implements ImperialSpaceship {

		private final T unitType;
		private final int unitNum;
		private Status status;

		private GenericImperialSpaceship(T unitType, int unitNum) {
			super();
			this.unitType = unitType;
			this.unitNum = unitNum;
			this.status = Status.AVAILABLE;
		}

		@Override
		public int cannons() {
			return unitType.cannons();
		}

		@Override
		public int shields() {
			return unitType.shields();
		}

		@Override
		public int maneuver() {
			return unitType.maneuver();
		}

		@Override
		public int maxPassengers() {
			return unitType.maxPassengers();
		}

		@Override
		public boolean overLimit(int numChars) {
			return unitType.overLimit(numChars);
		}
		
		@Override
		public String id() {
			return unitType.id();
		}

		public boolean isAvailable() {	// Is available in the Counter Pool
			return this.status == Status.AVAILABLE;
		}

		public GenericImperialSpaceship<T> used() {
			this.status = Status.USED;
			return this;
		}

		public GenericImperialSpaceship<T> available() {
			this.status = Status.AVAILABLE;
			return this;
		}

		public GenericImperialSpaceship<T> removeFromPlay() {
			this.status = Status.OUT_OF_PLAY;
			return this;
		}

		@Override
		public String toString() {
			return "GenericImperialSpaceship [unitType=" + unitType + ", unitNum=" + unitNum + ", status=" + status + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(unitNum, unitType);
		}
		
		public static GenericImperialSpaceship<?> requireGis(Spaceship spaceship) {
			if (spaceship instanceof GenericImperialSpaceship<?> genImpShip) {
				return genImpShip;
			}
			throw new IllegalArgumentException("Invalid unit supplied (" + spaceship.getClass().getName() + ").");

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
