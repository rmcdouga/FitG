package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public enum BaseGameStarSystem {
	Tardyn(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	Uracas(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	Zamorax(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	Atriard(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	Bex(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	Osirius(BaseGameProvince.One, Collections.emptyList(), Collections.emptyList()),
	
	Phisaria(BaseGameProvince.Two, Collections.emptyList(), Collections.emptyList()),
	Egrix(BaseGameProvince.Two, Collections.emptyList(), Collections.emptyList()),
	Ancore(BaseGameProvince.Two, Collections.emptyList(), Collections.emptyList()),
	Gellas(BaseGameProvince.Two, Collections.emptyList(), Collections.emptyList()),

	Pycius(BaseGameProvince.Three, Collections.emptyList(), Collections.emptyList()),
	Ribex(BaseGameProvince.Three, Collections.emptyList(), Collections.emptyList()),
	Rorth(BaseGameProvince.Three, Collections.emptyList(), Collections.emptyList()),
	Aziza(BaseGameProvince.Three, Collections.emptyList(), Collections.emptyList()),
	Luine(BaseGameProvince.Three, Collections.emptyList(), Collections.emptyList()),

	Erwind(BaseGameProvince.Four, Collections.emptyList(), Collections.emptyList()),
	Wex(BaseGameProvince.Four, Collections.emptyList(), Collections.emptyList()),
	Varu(BaseGameProvince.Four, Collections.emptyList(), Collections.emptyList()),
	Deblon(BaseGameProvince.Four, Collections.emptyList(), Collections.emptyList()),
	Martigna(BaseGameProvince.Four, Collections.emptyList(), Collections.emptyList()),

	Zakir(BaseGameProvince.Five, Collections.emptyList(), Collections.emptyList()),
	Eudox(BaseGameProvince.Five, Collections.emptyList(), Collections.emptyList()),
	Corusa(BaseGameProvince.Five, Collections.emptyList(), Collections.emptyList()),
	Irajeba(BaseGameProvince.Five, Collections.emptyList(), Collections.emptyList()),
	Moda(BaseGameProvince.Five, Collections.emptyList(), Collections.emptyList()),
	;
	
	private final BaseGameProvince province;
	private final List<BaseGamePlanet> planets;
	private final List<BaseGateSpaceRoute> spaceRoutes;

	private BaseGameStarSystem(BaseGameProvince province, List<BaseGamePlanet> planets, List<BaseGateSpaceRoute> spaceRoutes) {
		this.province = province;
		this.planets = planets;
		this.spaceRoutes = spaceRoutes;
	}

	public BaseGameProvince getProvince() {
		return province;
	}

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

	public String getName() {
		return this.toString();
	}

	public List<BaseGamePlanet> getPlanets() {
		return planets;
	}

	public List<BaseGateSpaceRoute> getSpaceRoutes() {
		return spaceRoutes;
	}
}
