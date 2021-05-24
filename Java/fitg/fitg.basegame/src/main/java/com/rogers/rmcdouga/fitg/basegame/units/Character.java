package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.OptionalInt;

import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;

public interface Character {
	public int combat();
	public int endurance();
	public int intelligence();
	public int leadership();
	public OptionalInt spaceLeadership();
	public int diplomacy();
	public int navigation();
	public RaceType race();
	public boolean isHomePlanet(Planet planet);
	public Faction allegience();
	public int bonusDraws(Mission mission);
	public boolean hasSpecialAbility(SpecialAbility specialAbility);
	public String description();
	
	public enum SpecialAbility {
		OWNS_THE_EXPLORER,					// owns Explorer spaceship
		OWNS_THE_SOLAR_MERCHANT,			// owns Solar Merchant spaceship
		INCREASE_ABILTIIES_WITH_ZINA_ADORA,	// increase all ratinngs (except endurance0 if with Zina Adora
		HEAL_CHARACTERS,					// Heal all characters with him
		ADD_ONE_TO_HIDING_VALUE,			// Adds one to hiding value of the group he is with
		OWNS_PLANETARY_PRIVATEER,			// owns Planetary Privateer
		REPAIRS_SPACESHIPS_AND_POSSESSIONS,	// May repair damaged spaceships and inoperable possessions (see Case 27.3). 
		IGNORE_SENTRY_ROBOTS,				// Ignore "creature attacks" by Imperial sentry robots
		IGNORE_FIRST_CREATURE_ATTACKS_IN_NON_URBAN,	// Ignore first "creature attacks characters" event in Special or Wild Environ.
		REVEAL_PLANET_SECRET, 				// Reveal Planet Secret.
		IGNORE_IRATE_LOCALS, 				// Ignore all "irate locals attack" events.
		ADD_TWO_TO_HIDING_VALUE,			// Add two to hiding value of group she is hiding with.
		;
	}
}
