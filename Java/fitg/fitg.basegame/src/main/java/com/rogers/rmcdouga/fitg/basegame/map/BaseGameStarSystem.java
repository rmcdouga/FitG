package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.Collections;
import java.util.List;

public enum BaseGameStarSystem {
	Tardyn(BaseGameProvince.One, Collections.emptyList()),
	Uracas(BaseGameProvince.One, Collections.emptyList()),
	Zamorax(BaseGameProvince.One, Collections.emptyList()),
	Atriard(BaseGameProvince.One, Collections.emptyList()),
	Bex(BaseGameProvince.One, Collections.emptyList()),
	Osirius(BaseGameProvince.One, Collections.emptyList()),
	
	Phisaria(BaseGameProvince.Two, Collections.emptyList()),
	Egrix(BaseGameProvince.Two, Collections.emptyList()),
	Ancore(BaseGameProvince.Two, Collections.emptyList()),
	Gellas(BaseGameProvince.Two, Collections.emptyList()),

	Pycius(BaseGameProvince.Three, Collections.emptyList()),
	Ribex(BaseGameProvince.Three, Collections.emptyList()),
	Rorth(BaseGameProvince.Three, Collections.emptyList()),
	Aziza(BaseGameProvince.Three, Collections.emptyList()),
	Luine(BaseGameProvince.Three, Collections.emptyList()),

	Erwind(BaseGameProvince.Four, Collections.emptyList()),
	Wex(BaseGameProvince.Four, Collections.emptyList()),
	Varu(BaseGameProvince.Four, Collections.emptyList()),
	Deblon(BaseGameProvince.Four, Collections.emptyList()),
	Martigna(BaseGameProvince.Four, Collections.emptyList()),

	Zakir(BaseGameProvince.Five, Collections.emptyList()),
	Eudox(BaseGameProvince.Five, Collections.emptyList()),
	Corusa(BaseGameProvince.Five, Collections.emptyList()),
	Irajeba(BaseGameProvince.Five, Collections.emptyList()),
	Moda(BaseGameProvince.Five, Collections.emptyList()),
	;
	
	private final BaseGameProvince province;
	private final List<BaseGamePlanet> planets;

	private BaseGameStarSystem(BaseGameProvince province, List<BaseGamePlanet> planets) {
		this.province = province;
		this.planets = planets;
	}

	public BaseGameProvince getProvince() {
		return province;
	}

	public int getId() {
		return province.getId() * 10 + this.ordinal() + 1;
	}

	public String getName() {
		return this.toString();
	}

	public List<BaseGamePlanet> getPlanets() {
		return planets;
	}

}
