package com.rogers.rmcdouga.fitg.basegame;

import java.util.List;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

public class MissionGroup {
	private final Game game;
	private final List<Character> characters;
	private final Mission mission;
	private final Environ environ;

	public MissionGroup(Game game, List<Character> characters, Mission mission) {
		this.game = game;
		this.characters = List.copyOf(characters);
		this.mission = mission;
		this.environ = ensureAllCharactersInSameEnviron();
	}
	
	private Environ ensureAllCharactersInSameEnviron() {
		Environ firstEnviron = getCharacterEnviron(characters.getFirst());
		for (Character character : characters) {
			if (!getCharacterEnviron(character).equals(firstEnviron)) {
				throw new IllegalArgumentException("All characters must be in the same environ.");
			}
		}			
		return firstEnviron;
	}

	private Environ getCharacterEnviron(Character character) {
		Optional<Location> location = game.locationOf(character);
		if (location.isPresent() && location.get() instanceof Environ environ) {
			return environ;
		} else {
			throw new IllegalArgumentException("Character '" + character.name() + "' is not in an environ.");
		}
	}

	public List<Character> characters() {
		return characters;
	}

	public Mission mission() {
		return mission;
	}

	public Environ environ() {
		return environ;
	}
}
