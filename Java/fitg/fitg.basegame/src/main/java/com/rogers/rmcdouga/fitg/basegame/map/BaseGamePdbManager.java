package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.requireBgp;

import java.util.EnumMap;
import java.util.Map;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb.Level;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb.State;

public class BaseGamePdbManager implements PdbManager {
	
	Map<BaseGamePlanet, Pdb> pdbs = new EnumMap<>(BaseGamePlanet.class);

	private BaseGamePdbManager() {
		BaseGamePlanet.stream().forEach(p->pdbs.put(p, Pdb.of(State.UP, Level.ZERO)));
	}

	@Override
	public Pdb getPdb(Planet planet) {
		return pdbs.get(requireBgp(planet));
	}

	@Override
	public PdbManager increasePdb(Planet planet) {
		pdbs.compute(requireBgp(planet), (k,v)->v.increaseLevel());
		return this;
	}

	@Override
	public PdbManager decreasePdb(Planet planet) {
		pdbs.compute(requireBgp(planet), (k,v)->v.decreaseLevel());
		return this;
	}

	@Override
	public PdbManager upPdb(Planet planet) {
		pdbs.compute(requireBgp(planet), (k,v)->v.up());
		return this;
	}

	@Override
	public PdbManager downPdb(Planet planet) {
		pdbs.compute(requireBgp(planet), (k,v)->v.down());
		return this;
	}

	public static BaseGamePdbManager create() {
		return new BaseGamePdbManager();
	}
	
}
