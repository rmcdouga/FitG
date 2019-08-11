package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;

class BaseGamePlanetTest {

	@Test
	void testGetEnvirons() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHomeworld() {
		fail("Not yet implemented");
	}

	@Test
	void testHasAllSovereigns() {
		Set<BaseGameSovereign> allSovereigns = EnumSet.allOf(BaseGameSovereign.class);
		Set<BaseGameSovereign> planetSovereigns = EnumSet.noneOf(BaseGameSovereign.class);
		for(BaseGamePlanet planet : BaseGamePlanet.values()) {
			for(Environ environ : planet.getEnvirons()) {
				BaseGameSovereign sovereign = environ.getSovereign();
				if (sovereign != null) {
					assertFalse(planetSovereigns.contains(sovereign), "Should be no duplicates.");
					planetSovereigns.add(sovereign);
				}
			}
		}
		assertTrue(planetSovereigns.containsAll(allSovereigns), "Should contain all sovereigns.");
	}

	@Test
	void testHasAllCreatures() {
		Set<BaseGameCreature> allCreatures = EnumSet.allOf(BaseGameCreature.class);
		Set<BaseGameCreature> planetCreatures = EnumSet.noneOf(BaseGameCreature.class);
		for(BaseGamePlanet planet : BaseGamePlanet.values()) {
			for(Environ environ : planet.getEnvirons()) {
				BaseGameCreature creature = environ.getCreature();
				if (creature != null) {
					assertFalse(planetCreatures.contains(creature), "Should be no duplicates.");
					planetCreatures.add(creature);
				}
			}
		}
		assertTrue(planetCreatures.containsAll(allCreatures), "Should contain all creatures.");
	}

}
