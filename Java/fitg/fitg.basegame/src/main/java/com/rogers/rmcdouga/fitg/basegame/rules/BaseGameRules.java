package com.rogers.rmcdouga.fitg.basegame.rules;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class BaseGameRules implements Rules {
	
	private final Game game;

	public BaseGameRules(Game game) {
		this.game = game;
	}

	@Override
	public Set<Location> validMoves(Counter counter) {
		return switch(counter) {
			case Spaceship spaceship -> validMoves(spaceship);
			case Stack stack -> validMoves(stack);
			case Character character -> validMoves(character);
			case Unit unit -> validMoves(unit);
			default -> throw new IllegalArgumentException("No movement rules defined for " + counter + " of class " + counter.getClass().getName());
		};
	}
	
	private Set<Location> validMoves(Spaceship spaceship) {
		// Spaceships can move to any other environ on the same planet, or to any other planet in the same star system.
		return Set.of();
	}

	private Set<Location> validMoves(Stack stack) {
		return Set.of();
	}
	
	private Set<Location> validMoves(Character character) {
		return Set.of();
	}
	
	private Set<Location> validMoves(Unit unit, Location currentLocation) {
		Set<Location> locations = new HashSet<>();
		
		if (currentLocation instanceof Environ environ) {
			locations.addAll(otherEnvironsOnPlanet(environ));
		}
		if (unit.isMobile()) {
			
		}
		return locations;
	}
	
	// Get the other environs on the same planet as the given environ
	private Set<Environ> otherEnvironsOnPlanet(Environ environ) {
		Planet planet = game.getPlanetContaining(environ);
		return planet.streamEnvirons().filter(e->!e.equals(environ)).collect(Collectors.toUnmodifiableSet());
	}
	
}
