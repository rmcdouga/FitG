package com.rogers.rmcdouga.fitg.basegame.box;

public interface CharacterPool {
	Character getCharacter(Character character);
	CharacterPool kill(Character character);
	CharacterPool cloneCharacter();	// used by Cloning Complex, randomly selects dead character
	
	Character randomRebelCounter();
	Character randomImperialCounter();
}
