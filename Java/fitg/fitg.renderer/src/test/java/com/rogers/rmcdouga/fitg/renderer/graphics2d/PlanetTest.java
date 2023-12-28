package com.rogers.rmcdouga.fitg.renderer.graphics2d;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

class PlanetTest {

	@Test
	void testPlanetsAndBasePlanetsMatch() {
		var bgpSet = BaseGamePlanet.stream().map(BaseGamePlanet::toString).collect(Collectors.toUnmodifiableSet());
		var pSet = Planet.stream().map(Planet::toString).collect(Collectors.toUnmodifiableSet());
		assertEquals(bgpSet, pSet, 
				()->"Planets does not match BaseGamePlanets. " + TestConstants.getCompareMessage("BaseGamePlanets", bgpSet, "Planets", pSet));
	}

}
