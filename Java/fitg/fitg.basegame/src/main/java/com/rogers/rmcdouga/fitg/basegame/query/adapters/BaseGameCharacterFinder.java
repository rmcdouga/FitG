package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Objects;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

/**
 * Base game implementation of {@link CharacterFinder} that searches the
 * {@link BaseGameCharacter} enum values by normalized ID.
 */
public class BaseGameCharacterFinder implements CharacterFinder {

	private final Game game;

	public BaseGameCharacterFinder(Game game) {
		this.game = Objects.requireNonNull(game);
	}

	@Override
	public Optional<Character> findCharacter(String characterId) {
		String normalizedId = CharacterFinder.normalizeId(characterId);
		return BaseGameCharacter.stream()
				.filter(c -> c.id().equals(normalizedId))
				.map(Character.class::cast)
				.findFirst();
	}

	@Override
	public Optional<Character> findCharacterInPlay(String characterId) {
		return findCharacter(characterId)
				.filter(c -> game.locationOf((Counter) c).isPresent());
	}
}
