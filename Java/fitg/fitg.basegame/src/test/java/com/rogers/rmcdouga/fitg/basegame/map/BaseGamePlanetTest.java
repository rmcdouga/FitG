package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;

class BaseGamePlanetTest {

	@Test
	void testGetEnvirons() {
		fail("Not yet implemented");
	}

	private static <T> void addOnce(Set<T> set, T obj) {
		assertTrue(set.add(obj), "Should be no duplicates, but '" + obj + "' is  duplicated.");
	}
	
	private static <T> void shouldContainAll(Set<T> target, Set<T> completeSet) {
		if (!target.containsAll(completeSet)) {
			completeSet.removeAll(target);
			fail("Should contain all creatures but is missing " + completeSet.stream().map(T::toString).collect(Collectors.joining(",")) + ".");
		}
	}

	@Test
	void testAllRacesHaveHomeworld() {
		Set<BaseGameRaceType> allRaces = EnumSet.allOf(BaseGameRaceType.class);
		Set<BaseGameRaceType> planetHomeworlds = EnumSet.noneOf(BaseGameRaceType.class);
		Stream.of(BaseGamePlanet.values())
			  .map(BaseGamePlanet::getHomeworld)
			  .flatMap(Optional::stream)
			  .forEach(race->addOnce(planetHomeworlds, race))
			;
		planetHomeworlds.add(BaseGameRaceType.Rhone);	// Rhone's have no homeworld, so we add it manually.
		shouldContainAll(planetHomeworlds, allRaces);
	}

	@Test
	void testHasAllSovereigns() {
		Set<BaseGameSovereign> allSovereigns = EnumSet.allOf(BaseGameSovereign.class);
		Set<BaseGameSovereign> planetSovereigns = EnumSet.noneOf(BaseGameSovereign.class);
		Stream.of(BaseGamePlanet.values())
			  .flatMap(p->p.getEnvirons().stream())			// Get All Environs
			  .flatMap(e->e.getSovereign().stream())		// Get Sovereigns (if any)
			  .forEach(sov->addOnce(planetSovereigns, sov))	// Add them to planetSovereigns
			  ;
		shouldContainAll(planetSovereigns, allSovereigns);
	}

	@Test
	void testHasAllCreatures() {
		Set<BaseGameCreature> allCreatures = EnumSet.allOf(BaseGameCreature.class);
		Set<BaseGameCreature> planetCreatures = EnumSet.noneOf(BaseGameCreature.class);
		Stream.of(BaseGamePlanet.values())
		  	  .flatMap(p->p.getEnvirons().stream())		// Get All Environs
		  	  .flatMap(e->e.getCreature().stream())		// Get Creatures (if any)
		  	  .forEach(c->addOnce(planetCreatures, c));	// Add them to planetCreatures
		  	  ; 
		planetCreatures.add(BaseGameCreature.Sentry_Robot);	// Add Sentry Robots (the default non-planetary creature encounter)
		shouldContainAll(planetCreatures, allCreatures);
	}

}
