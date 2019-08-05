package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaseGameStarSystemTest {

	@Test
	void testGetProvince() {
		assertAll(
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Tardyn.getProvince()),
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Uracas.getProvince()),
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Zamorax.getProvince()),
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Atriard.getProvince()),
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Bex.getProvince()),
			()->assertEquals(BaseGameProvince.One, BaseGameStarSystem.Osirius.getProvince()),
		
			()->assertEquals(BaseGameProvince.Two, BaseGameStarSystem.Phisaria.getProvince()),
			()->assertEquals(BaseGameProvince.Two, BaseGameStarSystem.Egrix.getProvince()),
			()->assertEquals(BaseGameProvince.Two, BaseGameStarSystem.Ancore.getProvince()),
			()->assertEquals(BaseGameProvince.Two, BaseGameStarSystem.Gellas.getProvince()),
		
			()->assertEquals(BaseGameProvince.Three, BaseGameStarSystem.Pycius.getProvince()),
			()->assertEquals(BaseGameProvince.Three, BaseGameStarSystem.Ribex.getProvince()),
			()->assertEquals(BaseGameProvince.Three, BaseGameStarSystem.Rorth.getProvince()),
			()->assertEquals(BaseGameProvince.Three, BaseGameStarSystem.Aziza.getProvince()),
			()->assertEquals(BaseGameProvince.Three, BaseGameStarSystem.Luine.getProvince()),
		
			()->assertEquals(BaseGameProvince.Four, BaseGameStarSystem.Erwind.getProvince()),
			()->assertEquals(BaseGameProvince.Four, BaseGameStarSystem.Wex.getProvince()),
			()->assertEquals(BaseGameProvince.Four, BaseGameStarSystem.Varu.getProvince()),
			()->assertEquals(BaseGameProvince.Four, BaseGameStarSystem.Deblon.getProvince()),
			()->assertEquals(BaseGameProvince.Four, BaseGameStarSystem.Martigna.getProvince()),
		
			()->assertEquals(BaseGameProvince.Five, BaseGameStarSystem.Zakir.getProvince()),
			()->assertEquals(BaseGameProvince.Five, BaseGameStarSystem.Eudox.getProvince()),
			()->assertEquals(BaseGameProvince.Five, BaseGameStarSystem.Corusa.getProvince()),
			()->assertEquals(BaseGameProvince.Five, BaseGameStarSystem.Irajeba.getProvince()),
			()->assertEquals(BaseGameProvince.Five, BaseGameStarSystem.Moda.getProvince())
			);
	}

	@Test
	void testGetId() {
		assertAll(
			()->assertEquals(11, BaseGameStarSystem.Tardyn.getId()),
			()->assertEquals(12, BaseGameStarSystem.Uracas.getId()),
			()->assertEquals(13, BaseGameStarSystem.Zamorax.getId()),
			()->assertEquals(14, BaseGameStarSystem.Atriard.getId()),
			()->assertEquals(15, BaseGameStarSystem.Bex.getId()),
			()->assertEquals(16, BaseGameStarSystem.Osirius.getId()),
	
			()->assertEquals(21, BaseGameStarSystem.Phisaria.getId()),
			()->assertEquals(22, BaseGameStarSystem.Egrix.getId()),
			()->assertEquals(33, BaseGameStarSystem.Ancore.getId()),
			()->assertEquals(44, BaseGameStarSystem.Gellas.getId()),
	
			()->assertEquals(31, BaseGameStarSystem.Pycius.getId()),
			()->assertEquals(32, BaseGameStarSystem.Ribex.getId()),
			()->assertEquals(33, BaseGameStarSystem.Rorth.getId()),
			()->assertEquals(34, BaseGameStarSystem.Aziza.getId()),
			()->assertEquals(35, BaseGameStarSystem.Luine.getId()),
	
			()->assertEquals(41, BaseGameStarSystem.Erwind.getId()),
			()->assertEquals(42, BaseGameStarSystem.Wex.getId()),
			()->assertEquals(43, BaseGameStarSystem.Varu.getId()),
			()->assertEquals(44, BaseGameStarSystem.Deblon.getId()),
			()->assertEquals(45, BaseGameStarSystem.Martigna.getId()),
	
			()->assertEquals(51, BaseGameStarSystem.Zakir.getId()),
			()->assertEquals(52, BaseGameStarSystem.Eudox.getId()),
			()->assertEquals(53, BaseGameStarSystem.Corusa.getId()),
			()->assertEquals(54, BaseGameStarSystem.Irajeba.getId()),
			()->assertEquals(55, BaseGameStarSystem.Moda.getId())
			);
	}

	@Test
	void testGetPlanets() {
		assertAll(
			()->assertEquals(3, BaseGameStarSystem.Tardyn.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Uracas.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Zamorax.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Atriard.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Bex.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Osirius.getPlanets().size()),
		
			()->assertEquals(2, BaseGameStarSystem.Phisaria.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Egrix.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Ancore.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Gellas.getPlanets().size()),
		
			()->assertEquals(2, BaseGameStarSystem.Pycius.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Ribex.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Rorth.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Aziza.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Luine.getPlanets().size()),
		
			()->assertEquals(2, BaseGameStarSystem.Erwind.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Wex.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Varu.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Deblon.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Martigna.getPlanets().size()),
		
			()->assertEquals(3, BaseGameStarSystem.Zakir.getPlanets().size()),
			()->assertEquals(3, BaseGameStarSystem.Eudox.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Corusa.getPlanets().size()),
			()->assertEquals(2, BaseGameStarSystem.Irajeba.getPlanets().size()),
			()->assertEquals(1, BaseGameStarSystem.Moda.getPlanets().size())
			);
	}
}
