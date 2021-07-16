package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;

class BaseGamePlanetTest {

	private static <T> void addOnce(Set<T> set, T obj) {
		assertTrue(set.add(obj), "Should be no duplicates, but '" + obj + "' is  duplicated.");
	}
	
	private static <K, V> void addOnce(Map<K, V> map, K key, V value) {
		assertNull(map.put(key, value), "Should be no duplicates, but '" + key +  "/" + value + "' is  duplicated.");
	}
	
	private static <T> void shouldContainAll(Set<T> target, Set<T> completeSet) {
		if (!target.containsAll(completeSet)) {
			completeSet.removeAll(target);
			fail("Should contain all creatures but is missing " + completeSet.stream().map(T::toString).collect(Collectors.joining(",")) + ".");
		}
	}

	@Test
	void testAllStarFaringRacesHaveHomeworld() {
		Set<BaseGameRaceType> allStarFaringRaces = EnumSet.copyOf(
				Stream.of(BaseGameRaceType.values())
					  .filter(r->r.isStarFaring())
					  .collect(Collectors.toSet())
				);
		Set<BaseGameRaceType> planetHomeworlds = EnumSet.noneOf(BaseGameRaceType.class);
		Stream.of(BaseGamePlanet.values())						// Go through all planets
			  .map(BaseGamePlanet::getHomeworld)				// Get optional homeworld
			  .flatMap(Optional::stream)						// Drop those that aren't homeworlds
			  .forEach(race->addOnce(planetHomeworlds, race))	// Add then to our set,
			;
		planetHomeworlds.add(BaseGameRaceType.Rhone);	// Rhone's have no homeworld, so we add it manually.
		shouldContainAll(planetHomeworlds, allStarFaringRaces);
	}

	@Test
	void testAllHomeworldsMatchRace() {
		Map<BaseGameRaceType, BaseGamePlanet> homeworlds = new EnumMap<>(BaseGameRaceType.class);
//		Map<BaseGameRaceType, BaseGamePlanet> homeworlds = new HashMap<>();
		BaseGamePlanet.stream()
			  .filter(p->p.getHomeworld().isPresent())						// Only keep homeworlds
			  .forEach(p->addOnce(homeworlds, p.getHomeworld().get(), p));	// Add to the Map (just once)
		Stream.of(BaseGameRaceType.values())
		  	  .filter(r->r.isStarFaring())									// Only include starfaring races.
			  .filter(r->r.getHomePlanet().isPresent())						// Exclude Races that have no homeworld.
			  .forEach(r->assertEquals(r.getHomePlanet().get(), homeworlds.get(r), "Homeworld doesn't match for race '" + r + "'."));
		assertTrue(BaseGameRaceType.Rhone.getHomePlanet().isEmpty(), "Expected Rhone's to not have a homeworld.");
	}
	
	@Test
	void testHasAllSovereigns() {
		Set<BaseGameSovereign> allSovereigns = EnumSet.allOf(BaseGameSovereign.class);
		Set<BaseGameSovereign> planetSovereigns = EnumSet.noneOf(BaseGameSovereign.class);
		BaseGamePlanet.stream()
			  .flatMap(p->p.listEnvirons().stream())			// Get All Environs
			  .flatMap(e->e.getSovereign().stream())		// Get Sovereigns (if any)
			  .forEach(sov->addOnce(planetSovereigns, sov))	// Add them to planetSovereigns
			  ;
		shouldContainAll(planetSovereigns, allSovereigns);
	}

	@Test
	void testHasAllCreatures() {
		Set<BaseGameCreature> allCreatures = EnumSet.allOf(BaseGameCreature.class);
		Set<BaseGameCreature> planetCreatures = EnumSet.noneOf(BaseGameCreature.class);
		BaseGamePlanet.stream()
		  	  .flatMap(p->p.listEnvirons().stream())		// Get All Environs
		  	  .flatMap(e->e.getCreature().stream())		// Get Creatures (if any)
		  	  .forEach(c->addOnce(planetCreatures, c));	// Add them to planetCreatures
		  	  ; 
		planetCreatures.add(BaseGameCreature.Sentry_Robot);	// Add Sentry Robots (the default non-planetary creature encounter)
		shouldContainAll(planetCreatures, allCreatures);
	}

	@Test
	void testNoDuplicateEnvironsPerPlanet() {
		for (BaseGamePlanet planet : BaseGamePlanet.values()) {
			List<BaseGameEnviron> listOfEnvirons = planet.listEnvirons();
			Set<BaseGameEnvironType> environTypes = EnumSet.noneOf(BaseGameEnvironType.class);
			listOfEnvirons.forEach(e->environTypes.add(BaseGameEnvironType.requireBgEnvironType(e.getType())));
			assertEquals(listOfEnvirons.size(), environTypes.size());
		}
	}
}
