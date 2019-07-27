package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Arrays;

import com.rogers.rmcdouga.fitg.basegame.RaceType;

public enum Planet {
	Mimulus();
	
	private final String name;
	private final int id;
	private final CapitalType capitalType;
	private final List<Environ> environs;
	private final RaceType homeworld;
	private final PlanetaryControlType control;
	private final LoyaltyType startingLoyaltyS;
	private final LoyaltyType startingLoyaltyA;
	private final List<SpaceRoute> spaceRoutes;

	private LoyaltyType currentLoyalty;

	// This is just a placeholder to get things to compile
	private Planet() {
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

	private Planet(String name, int id, CapitalType capitalType, List<Environ> environs, RaceType homeworld,
			PlanetaryControlType control, LoyaltyType startingLoyaltyS, LoyaltyType startingLoyaltyA,
			List<SpaceRoute> spaceRoutes, LoyaltyType currentLoyalty) {
		this.name = name;
		this.id = id;
		this.capitalType = capitalType;
		this.environs = environs;
		this.homeworld = homeworld;
		this.control = control;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.spaceRoutes = spaceRoutes;
		this.currentLoyalty = currentLoyalty;
	}
	
	
	
}
