package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.RaceType;

public interface Planet {
	public LoyaltyType getCurrentLoyalty();
	public void setCurrentLoyalty(LoyaltyType currentLoyalty);
	public String getName();
	public int getId();
	public BaseGameCapitalType getCapitalType();
	public List<? extends Environ> getEnvirons();
	public Optional<? extends RaceType> getHomeworld();
	public BaseGamePlanetaryControlType getControlA();
	public LoyaltyType getStartingLoyaltyS();
	public LoyaltyType getStartingLoyaltyA();
	public BaseGamePlanetaryControlType getCurrentControl();
	public void setCurrentControl(BaseGamePlanetaryControlType currentControl);
	public BaseGameStarSystem getStarSystem();
	public boolean hasSecret();
}
