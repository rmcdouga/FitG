package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGameStarSystem implements StarSystem {
	Tardyn(BaseGameProvince.One),
	Uracus(BaseGameProvince.One),
	Zamorax(BaseGameProvince.One),
	Atriard(BaseGameProvince.One),
	Bex(BaseGameProvince.One),
	Osirius(BaseGameProvince.One),
	
	Phisaria(BaseGameProvince.Two),
	Egrix(BaseGameProvince.Two),
	Ancore(BaseGameProvince.Two),
	Gellas(BaseGameProvince.Two),

	Pycius(BaseGameProvince.Three),
	Ribex(BaseGameProvince.Three),
	Rorth(BaseGameProvince.Three),
	Aziza(BaseGameProvince.Three),
	Luine(BaseGameProvince.Three),

	Erwind(BaseGameProvince.Four),
	Wex(BaseGameProvince.Four),
	Varu(BaseGameProvince.Four),
	Deblon(BaseGameProvince.Four),
	Martigna(BaseGameProvince.Four),

	Zakir(BaseGameProvince.Five),
	Eudox(BaseGameProvince.Five),
	Corusa(BaseGameProvince.Five),
	Irajeba(BaseGameProvince.Five),
	Moda(BaseGameProvince.Five),
	;
	
	private final BaseGameProvince province;
	private final Predicate<BaseGamePlanet> planetsPredicate = p->this.equals(p.getStarSystem());
	private final Predicate<BaseGameSpaceRoute> spaceRoutesPredicate = r->this.equals(r.getTerminus1()) || this.equals(r.getTerminus2());
	private final Drift drift = new DriftImpl(Drift.DriftType.Drift);
	private final Drift drift2 = new DriftImpl(Drift.DriftType.Drift2);
	

	private BaseGameStarSystem(BaseGameProvince province) {
		this.province = province;
	}

	@Override
	public BaseGameProvince getProvince() {
		return province;
	}

	@Override
	public int getId() {
		int myIndex = this.ordinal();
		BaseGameStarSystem[] allStarSystems = BaseGameStarSystem.values();

		// The last digit if our id is the offset from the last star system that is in a different province.
		IntPredicate testOp = (i)->(i >= 0 && allStarSystems[i].province == this.province);	// Stop if index < 0 or the star system pointed to has a different province.
		IntUnaryOperator indexOp = (oi)->myIndex - oi - 1;	// Current star system's index minus the offset index (plus 1)

		int offset = 0;
		while(testOp.test(indexOp.applyAsInt(offset))) {
			offset++;
		}
		
		return this.province.getId() * 10 + (offset + 1);
	}

	@Override
	public String getName() {
		return this.toString();
	}

	@Override
	public List<BaseGamePlanet> listPlanets() {
		return BaseGamePlanet.planets(planetsPredicate);
	}

	@Override
	public List<BaseGameSpaceRoute> listSpaceRoutes() {
		return BaseGameSpaceRoute.spaceRoutes(spaceRoutesPredicate);
	}

	@Override
	public Stream<BaseGamePlanet> streamPlanets() {
		return BaseGamePlanet.stream().filter(planetsPredicate);
	}

	@Override
	public Stream<BaseGameSpaceRoute> streamSpaceRoutes() {
		return BaseGameSpaceRoute.stream().filter(spaceRoutesPredicate);
	}
	
	@Override
	public Drift drift() {
		return drift;
	}

	@Override
	public Drift drift2() {
		return drift2;
	}

	public static Stream<BaseGameStarSystem> stream() {
		return Stream.of(BaseGameStarSystem.values());
	}

	public static Stream<BaseGameStarSystem> stream(Predicate<BaseGameStarSystem> predicate) {
		return BaseGameStarSystem.stream().filter(predicate);
	}
	
	private class DriftImpl implements Drift {
		private final DriftType type;

		private DriftImpl(DriftType type) {
			this.type = type;
		}

		public DriftType type() {
			return type;
		}

		@Override
		public StarSystem starSystem() {
			return BaseGameStarSystem.this;
		}
	}
}
