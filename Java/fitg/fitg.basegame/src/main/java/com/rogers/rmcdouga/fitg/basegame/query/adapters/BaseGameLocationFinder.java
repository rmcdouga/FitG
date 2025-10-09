package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;

public class BaseGameLocationFinder implements LocationFinder {

	/**
	 * Finds the location on the given star or planet.
	 * 
	 * If the starOrPlanetId is numeric, it is treated as an id, otherwise as a name.
	 * 
	 * @param starOrPlanetId Name or numeric id of a star system or planet
	 * @param location	environ type or in orbit for planets, drift/drift2 for star systems
	 * @return
	 */
	@Override
	public Location findLocation(String starOrPlanetId, String location) {
		int numericId = parseInt(starOrPlanetId);
		if (numericId > 0) {
			if (numericId < 100) {
				return findStarSystemLocation(findStarSystem(numericId), location);
			} else {
				return findPlanetLocation(findPlanet(numericId), location);
			}
		}
		// Must be a name
		return findStarSystem(starOrPlanetId)
						.map(ss->findStarSystemLocation(ss, location))
						.orElseGet(()->findPlanet(starOrPlanetId)
											.map(p->findPlanetLocation(p, location))
											.orElseThrow(()->new IllegalArgumentException("No star system or planet found with name: " + starOrPlanetId))
									);
	}

	// Parse a string as an integer without throwing an exception (i.e. return 0 if not a number). 
	private static Integer parseInt(String s) {
		// create a regex pattern for integer
		Pattern pattern = Pattern.compile("-?\\d+");
		// attempt to match regex for integer
		return pattern.matcher(s).matches() ? Integer.valueOf(s) : 0;
	}
	
	// Find a star system by its ID.
	// Throws IllegalArgumentException if not found.
	private static StarSystem findStarSystem(Integer id) {
		return BaseGameStarSystem.stream()
				.filter(ss->ss.getId()==id.intValue())
				.findFirst()
				.orElseThrow(()->new IllegalArgumentException("No star system found with id: " + id));
	}

	// Find a star system by its name.
	// Returns Optional.empty() if not found.
	private static Optional<StarSystem> findStarSystem(String name) {
		return BaseGameStarSystem.stream()
				.filter(ss->ss.getName().equalsIgnoreCase(name))
				.map(ss->(StarSystem)ss)
				.findFirst();
	}

	// Find a planet by its ID.
	// Throws IllegalArgumentException if not found.
	private static Planet findPlanet(Integer id) {
		return BaseGamePlanet.stream()
				.filter(p->p.getId()==id.intValue())
				.findFirst()
				.orElseThrow(()->new IllegalArgumentException("No planet found with id: " + id));
	}

	// Find a planet by its name.
	// Returns Optional.empty() if not found.
	private static Optional<Planet> findPlanet(String name) {
		return BaseGamePlanet.stream()
				.filter(p->p.getName().equalsIgnoreCase(name))
				.map(p->(Planet)p)
				.findFirst();
	}
	
	// Find a location within a star system by its name.
	// Throws IllegalArgumentException if not found.
	private static Location findStarSystemLocation(StarSystem starSystem, String name) {
		return switch(name.toLowerCase()) {
			case "drift" -> starSystem.drift();
			case "drift2" -> starSystem.drift2();
			default -> throw new IllegalArgumentException("No location found with name: " + name + " in star system: " + starSystem);
		};
	}

	// Find a location on a planet by its name.
	// Throws IllegalArgumentException if not found.
	private static Location findPlanetLocation(Planet planet, String name) {
		Supplier<IllegalArgumentException> notFound = ()->new IllegalArgumentException("No location found with name: " + name + " on planet: " + planet);
		return switch(name.toLowerCase()) {
			case "in orbit", "orbit" -> planet.inOrbit();
			case "air" -> planet.environ(BaseGameEnvironType.Air).orElseThrow(notFound);
			case "fire" -> planet.environ(BaseGameEnvironType.Fire).orElseThrow(notFound);
			case "liquid" -> planet.environ(BaseGameEnvironType.Liquid).orElseThrow(notFound);
			case "subterranian", "sub" -> planet.environ(BaseGameEnvironType.Subterranian).orElseThrow(notFound);
			case "urban" -> planet.environ(BaseGameEnvironType.Urban).orElseThrow(notFound);
			case "wild" -> planet.environ(BaseGameEnvironType.Wild).orElseThrow(notFound);
			default -> throw notFound.get();
		};
	}
}

