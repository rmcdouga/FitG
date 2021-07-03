package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

public interface CharacterPool {
	Character getCharacter(Character character);
	CharacterPool kill(Character character);

	Optional<Character> cloneCharacter(Faction faction);	// used by Cloning Complex, randomly selects dead character
	Optional<Character> randomCharacter(Faction faction);	// Get a random character from a faction, if any are available.
}
