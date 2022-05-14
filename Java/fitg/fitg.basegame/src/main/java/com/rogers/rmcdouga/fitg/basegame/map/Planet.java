package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.RaceType;

public interface Planet {
	public LoyaltyType getCurrentLoyalty();
	public void setCurrentLoyalty(LoyaltyType currentLoyalty);
	public String getName();
	public int getId();
	public BaseGameCapitalType getCapitalType();
	public List<? extends Environ> listEnvirons();
	public Optional<? extends RaceType> getHomeworld();
	public BaseGamePlanetaryControlType getControlA();
	public LoyaltyType getStartingLoyaltyS();
	public LoyaltyType getStartingLoyaltyA();
	public BaseGamePlanetaryControlType getCurrentControl();
	public void setCurrentControl(BaseGamePlanetaryControlType currentControl);
	public BaseGameStarSystem getStarSystem();
	public boolean hasSecret();
	
	default public int numEnvirons() { return listEnvirons().size(); }
	default public Environ environ(int index) { return listEnvirons().get(index); }
	default public Stream<? extends Environ> streamEnvirons() { return listEnvirons().stream(); }
	default public Optional<? extends Environ> environ(Environ.EnvironType type) { return streamEnvirons().filter(e->e.getType().equals(type)).findAny(); }
	default public int indexOf(Environ environ) { return listEnvirons().indexOf(environ); }
}
