package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

import java.util.Arrays;
import java.util.Collections;

public enum BaseGamePlanet {
	Mimulus(111, BaseGameCapitalType.None, Collections.emptyList(),
			null, BaseGamePlanetaryControlType.ImperialControlled, BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal,
			Collections.emptyList(), null);
	
	private final int id;
	private final BaseGameCapitalType capitalType;
	private final List<Environ> environs;
	private final RaceType homeworld;
	private final BaseGamePlanetaryControlType control;
	private final LoyaltyType startingLoyaltyS;
	private final LoyaltyType startingLoyaltyA;
	private final List<BaseGateSpaceRoute> spaceRoutes;

	private LoyaltyType currentLoyalty;

	private BaseGamePlanet(int id, BaseGameCapitalType capitalType, List<BaseGameEnviron> environs, BaseGameRaceType homeworld,
			BaseGamePlanetaryControlType control, BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA,
			List<BaseGateSpaceRoute> spaceRoutes, BaseGameLoyaltyType currentLoyalty) {
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

	public LoyaltyType getCurrentLoyalty() {
		return currentLoyalty;
	}

	public void setCurrentLoyalty(LoyaltyType currentLoyalty) {
		this.currentLoyalty = currentLoyalty;
	}

	public String getName() {
		return this.toString().replace('_', ' ');
	}

	public int getId() {
		return id;
	}

	public BaseGameCapitalType getCapitalType() {
		return capitalType;
	}

	public List<Environ> getEnvirons() {
		return environs;
	}

	public RaceType getHomeworld() {
		return homeworld;
	}

	public BaseGamePlanetaryControlType getControl() {
		return control;
	}

	public LoyaltyType getStartingLoyaltyS() {
		return startingLoyaltyS;
	}

	public LoyaltyType getStartingLoyaltyA() {
		return startingLoyaltyA;
	}

	public List<BaseGateSpaceRoute> getSpaceRoutes() {
		return spaceRoutes;
	}	
}
