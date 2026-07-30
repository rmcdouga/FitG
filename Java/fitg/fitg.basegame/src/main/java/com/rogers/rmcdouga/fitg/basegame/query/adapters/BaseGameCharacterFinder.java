package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
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

	@Override
	public Stream<CharacterEligibleForMission> findAllCharactersEligibleForMissions(Faction faction) {
		return BaseGameCharacter.stream()
								.filter(c -> c.allegience() == faction)	
								.mapMulti(this::createIfCharacterEligibleForMissionConsumer);
	}
	
	private void createIfCharacterEligibleForMissionConsumer(Character character, Consumer<CharacterEligibleForMission> consumer) {
		game.locationOf((Counter) character)		// Find location
			.filter(Environ.class::isInstance)		// Keep if it's an Environ
			.ifPresent(location -> consumer.accept(new CharacterEligibleForMission(character, (Environ) location)));
	}
}
