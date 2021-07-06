package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.Scenario;

class BaseGameLoyaltyManagerTest {

	@Test
	void testCreate_StartRebellion() {
		BaseGameLoyaltyManager underTest = BaseGameLoyaltyManager.create(List.of(Egrix), Scenario.Type.StartRebellion);
		assertAll(
				()->assertEquals(BaseGameLoyaltyType.Loyal, underTest.getLoyalty(Quibron)),
				()->assertEquals(BaseGameLoyaltyType.Neutral, underTest.getLoyalty(Angoff)),
				()->assertEquals(BaseGameLoyaltyType.Patriotic, underTest.getLoyalty(Charkhan))
				);
	}

	@Test
	void testCreate_Armagheddon() {
		BaseGameLoyaltyManager underTest = BaseGameLoyaltyManager.create(List.of(Egrix, Gellas), Scenario.Type.Armegeddon);
		assertAll(
				()->assertEquals(BaseGameLoyaltyType.Unrest, underTest.getLoyalty(Quibron)),
				()->assertEquals(BaseGameLoyaltyType.Unrest, underTest.getLoyalty(Angoff)),
				()->assertEquals(BaseGameLoyaltyType.Dissent, underTest.getLoyalty(Charkhan)),
				()->assertEquals(BaseGameLoyaltyType.Loyal, underTest.getLoyalty(Orning))
				);
	}

}
