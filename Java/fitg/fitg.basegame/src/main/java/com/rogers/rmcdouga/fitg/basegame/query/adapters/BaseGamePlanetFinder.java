package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;

public class BaseGamePlanetFinder implements PlanetFinder {

	private final Game game;

	public BaseGamePlanetFinder(Game game) {
		this.game = game;
	}

	@Override
	public Optional<Planet> findByName(String name) {
		return game.getStarSystems().stream()
									.flatMap(s -> s.streamPlanets())
									.filter(p -> p.getName().equalsIgnoreCase(name))
									.map(Planet.class::cast)
									.findFirst();
	}

	@Override
	public Optional<Planet> findById(int id) {
		if (id < 100 || id > 551) {	// Shortcut if outside valid range
			return Optional.empty();
		}
		int starSystemId = id / 10;
		return game.getStarSystems().stream()
				.filter(s -> s.getId() == starSystemId)
				.flatMap(s -> s.streamPlanets())
				.filter(p -> p.getId() == id)
				.map(Planet.class::cast)
				.findFirst();
	}

}
