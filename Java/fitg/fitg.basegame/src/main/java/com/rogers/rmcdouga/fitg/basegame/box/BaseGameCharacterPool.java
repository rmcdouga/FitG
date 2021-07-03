package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

class BaseGameCharacterPool implements CharacterPool {

	private final Map<BaseGameCharacter, CharacterStatus> pool;
	private final IntFunction<Integer> randomFn;
	
	BaseGameCharacterPool() {
		this(numEntries->(int)(Math.random()*numEntries));
	}

	BaseGameCharacterPool(IntFunction<Integer> randomFn) {
		this.pool = new EnumMap<>(BaseGameCharacter.class);
		BaseGameCharacter.stream().forEach(c->this.pool.put(c, CharacterStatus.AVAILABLE));
		this.randomFn = randomFn;
	}

	@Override
	public Character getCharacter(Character character) {
		if (checkAvailability(character) != CharacterState.AVAILABLE) {
			throw new IllegalArgumentException("Character (" + character + ") is not available (" + checkAvailability(character) + ").");
		}
		changeAvailability(character, CharacterStatus.IN_PLAY);
		return character;
	}

	@Override
	public CharacterPool kill(Character character) {
		if (checkAvailability(character) != CharacterState.IN_PLAY) {
			throw new IllegalArgumentException("Character (" + character + ") is not in play (" + checkAvailability(character) + ").");
		}
		changeAvailability(character, CharacterStatus.DEAD);
		return this;
	}

	@Override
	public Optional<Character> cloneCharacter(Faction faction) {
		Optional<BaseGameCharacter> randomDeadChar = selectRandomly(e->e.getKey().allegience() == faction && e.getValue() == CharacterStatus.DEAD);

		return randomDeadChar.isPresent() ? randomDeadChar.map(c->changeAvailability(c, CharacterStatus.IN_PLAY)).map(Character.class::cast) 
										  : randomCharacter(faction);
	}

	@Override
	public Optional<Character> randomCharacter(Faction faction) {
		// select available rebal character at random and call get();
		return selectRandomly(e->e.getKey().allegience() == faction && e.getValue() == CharacterStatus.AVAILABLE)
					.map(this::getCharacter);
	}

	private Optional<BaseGameCharacter> selectRandomly(Predicate<Map.Entry<BaseGameCharacter, CharacterStatus>> predicate) {
		var list = this.pool.entrySet()
						    .stream()
						    .filter(predicate)
						    .map(Map.Entry::getKey)
						    .toList();
		return selectRandomly(list);
	}

	private Optional<BaseGameCharacter> selectRandomlyByStatus(Predicate<CharacterStatus> predicate) {
		return selectRandomly(e->predicate.test(e.getValue()));
	}
	
	private Optional<BaseGameCharacter> selectRandomly(List<BaseGameCharacter> candidates) {
		if (candidates.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(candidates.get(this.randomFn.apply(candidates.size())));
	}
	
	private BaseGameCharacter changeAvailability(Character character, CharacterStatus newStatus) {
		if (character instanceof BaseGameCharacter bgc) {
			this.pool.replace(bgc, newStatus);
			return bgc;
		} else {
			throw new IllegalArgumentException("Character was not a BaseGameCharacter! (" + character + ").");
		}
	}

	private CharacterState checkAvailability(Character character) {
		if (character instanceof BaseGameCharacter bgc) {
			return this.pool.get(bgc).available();
		} else {
			throw new IllegalArgumentException("Character was not a BaseGameCharacter! (" + character + ").");
		}
	}

	private enum CharacterState {
		AVAILABLE, IN_PLAY, DEAD;
	};
	
	private static record CharacterStatus(CharacterState available) {
		private static final CharacterStatus AVAILABLE = new CharacterStatus(CharacterState.AVAILABLE);
		private static final CharacterStatus IN_PLAY = new CharacterStatus(CharacterState.IN_PLAY);
		private static final CharacterStatus DEAD = new CharacterStatus(CharacterState.DEAD);
	}
}
