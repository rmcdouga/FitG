package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;

import org.junit.jupiter.api.Test;

class BaseGameCharacterTest {

	@Test
	void testNumberOfCharacters() {
		assertEquals(20, BaseGameCharacter.values().length);
	}
	
	@Test
	void testRandomAttributes() {
		assertFalse(Zina_Adora.spaceLeadership().isPresent());
		assertEquals(1, Adam_Starlight.spaceLeadership().getAsInt());
		assertEquals(2, Agan_Rafa.bonusDraws(BaseGameMission.ASSASINATION));
	}
	

}
