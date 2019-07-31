package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

import java.util.Arrays;

public enum BaseGamePlanet {
	Mimulus();
	
	private final String name;
	private final int id;
	private final BaseGameCapitalType capitalType;
	private final List<Environ> environs;
	private final RaceType homeworld;
	private final BaseGamePlanetaryControlType control;
	private final LoyaltyType startingLoyaltyS;
	private final LoyaltyType startingLoyaltyA;
	private final List<BaseGateSpaceRoute> spaceRoutes;

	private LoyaltyType currentLoyalty;

	// This is just a placeholder to get things to compile
	private BaseGamePlanet() {
		this.name = null;
		this.id = 0;
		this.capitalType = null;
		this.environs = null;
		this.homeworld = null;
		this.control = null;
		this.startingLoyaltyS = null;
		this.startingLoyaltyA = null;
		this.spaceRoutes = null;
		this.currentLoyalty = null;
	}

	private BaseGamePlanet(String name, int id, BaseGameCapitalType capitalType, List<BaseGameEnviron> environs, BaseGameRaceType homeworld,
			BaseGamePlanetaryControlType control, BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA,
			List<BaseGateSpaceRoute> spaceRoutes, BaseGameLoyaltyType currentLoyalty) {
		this.name = name;
		this.id = id;
		this.capitalType = capitalType;
		this.environs = List.copyOf(environs);
		this.homeworld = homeworld;
		this.control = control;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.spaceRoutes = spaceRoutes;
		this.currentLoyalty = currentLoyalty;
	}
	
	
	
}
