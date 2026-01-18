package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.query.api.StarSystemFinder;

public class BaseGameStarSystemFinder implements StarSystemFinder {

	private final Game game;

	public BaseGameStarSystemFinder(Game game) {
		this.game = game;
	}

	@Override
	public Optional<StarSystem> findByName(String name) {
		return game.getStarSystems().stream()
									.filter(s -> s.getName().equalsIgnoreCase(name))
									.findFirst();
	}

	@Override
	public Optional<StarSystem> findById(int id) {
		if (id < 11 || id > 55) {	// Shortcut if outside valid range
			return Optional.empty();
		}
		return game.getStarSystems().stream()
									.filter(s -> s.getId() == id)
									.findFirst();
	}

}
