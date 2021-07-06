package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyManager;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePdbManager;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public class BaseGameGameBoard implements GameBoard {
	private final LoyaltyManager loyaltyManager;
	private final PdbManager pdbManager;

	// Prevent instantiation by anyone but me.
	private BaseGameGameBoard(Collection<StarSystem> map, Scenario.Type scenarioType) {
		this.loyaltyManager = BaseGameLoyaltyManager.create(map, scenarioType);
		this.pdbManager = BaseGamePdbManager.create();
	}

	@Override
	public LoyaltyType getLoyalty(Planet planet) {
		return loyaltyManager.getLoyalty(planet);
	}

	@Override
	public LoyaltyManager shiftLeft(Planet planet) {
		return loyaltyManager.shiftLeft(planet);
	}

	@Override
	public LoyaltyManager shiftRight(Planet planet) {
		return loyaltyManager.shiftRight(planet);
	}

	@Override
	public Pdb getPdb(Planet planet) {
		return pdbManager.getPdb(planet);
	}

	@Override
	public PdbManager increasePdb(Planet planet) {
		return pdbManager.increasePdb(planet);
	}

	@Override
	public PdbManager decreasePdb(Planet planet) {
		return pdbManager.decreasePdb(planet);
	}

	@Override
	public PdbManager upPdb(Planet planet) {
		return pdbManager.upPdb(planet);
	}

	@Override
	public PdbManager downPdb(Planet planet) {
		return pdbManager.downPdb(planet);
	}
	
	public static GameBoard create(Collection<StarSystem> map, Scenario.Type scenarioType) {
		return new BaseGameGameBoard(map, scenarioType);
	}
}
