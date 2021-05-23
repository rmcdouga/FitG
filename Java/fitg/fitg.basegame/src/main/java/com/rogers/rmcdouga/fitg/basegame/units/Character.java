package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.OptionalInt;

import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

public interface Character {
	public int combat();
	public int endurance();
	public int intelligence();
	public int leadership();
	public OptionalInt spaceLeadership();
	public int diplomacy();
	public int navigation();
	public RaceType race();
	public BaseGamePlanet homePlanet();
	public Faction allegience();
	public int bonusDraws(Mission mission);
	public boolean hasSpecialAbility(SpecialAbility specialAbility);
	public String descriptions();
	
	public enum SpecialAbility {
		OWNS_THE_EXPLORER;
	}
}
