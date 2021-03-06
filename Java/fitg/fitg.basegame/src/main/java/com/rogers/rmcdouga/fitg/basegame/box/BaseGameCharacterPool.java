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

	private final Map<BaseGameCharacter, CharacterState> pool;
	private final IntFunction<Integer> randomFn;
	
	BaseGameCharacterPool() {
		this(numEntries->(int)(Math.random()*numEntries));
	}

	BaseGameCharacterPool(IntFunction<Integer> randomFn) {
		this.pool = new EnumMap<>(BaseGameCharacter.class);
		BaseGameCharacter.stream().forEach(c->this.pool.put(c, CharacterState.AVAILABLE));
		this.randomFn = randomFn;
	}

	@Override
	public Character getCharacter(Character character) {
		if (checkAvailability(character) != CharacterState.AVAILABLE) {
			throw new IllegalArgumentException("Character (" + character + ") is not available (" + checkAvailability(character) + ").");
		}
		changeAvailability(character, CharacterState.IN_PLAY);
		return character;
	}

	@Override
	public CharacterPool kill(Character character) {
		if (checkAvailability(character) != CharacterState.IN_PLAY) {
			throw new IllegalArgumentException("Character (" + character + ") is not in play (" + checkAvailability(character) + ").");
		}
		changeAvailability(character, CharacterState.DEAD);
		return this;
	}

	@Override
	public Optional<Character> cloneCharacter(Faction faction) {
		Optional<BaseGameCharacter> randomDeadChar = selectRandomly(e->e.getKey().allegience() == faction && e.getValue() == CharacterState.DEAD);

		return randomDeadChar.isPresent() ? randomDeadChar.map(c->changeAvailability(c, CharacterState.IN_PLAY)).map(Character.class::cast) 
										  : randomCharacter(faction);
	}

	@Override
	public Optional<Character> randomCharacter(Faction faction) {
		// select available rebal character at random and call get();
		return selectRandomly(e->e.getKey().allegience() == faction && e.getValue() == CharacterState.AVAILABLE) 	// Select randomly from AVAILABLE character of the correct faction.
					.map(this::getCharacter);
	}

	private Optional<BaseGameCharacter> selectRandomly(Predicate<Map.Entry<BaseGameCharacter, CharacterState>> predicate) {
		var list = this.pool.entrySet()
						    .stream()
						    .filter(predicate)
						    .map(Map.Entry::getKey)
						    .toList();
		return selectRandomly(list);
	}

	private Optional<BaseGameCharacter> selectRandomly(List<BaseGameCharacter> candidates) {
		return candidates.isEmpty() ? Optional.empty() 
									: Optional.of(candidates.get(this.randomFn.apply(candidates.size())));
	}
	
	private BaseGameCharacter changeAvailability(Character character, CharacterState newStatus) {
		if (character instanceof BaseGameCharacter bgc) {
			this.pool.replace(bgc, newStatus);
			return bgc;
		} else {
			throw new IllegalArgumentException("Character was not a BaseGameCharacter! (" + character + ").");
		}
	}

	private CharacterState checkAvailability(Character character) {
		if (character instanceof BaseGameCharacter bgc) {
			return this.pool.get(bgc);
		} else {
			throw new IllegalArgumentException("Character was not a BaseGameCharacter! (" + character + ").");
		}
	}

	private enum CharacterState {
		AVAILABLE, IN_PLAY, DEAD;
	};
	
//	// This record is used because I anticipate tracking other stats besides CharacterState.  Other stats would then also
//	// be added to this record structure. If this doesn't end up happening, then this record could be removed and the Pool
//	// could use CharacterState directly.
//	private static record CharacterState(CharacterState available) {
//		private static final CharacterState AVAILABLE = new CharacterState(CharacterState.AVAILABLE);
//		private static final CharacterState IN_PLAY = new CharacterState(CharacterState.IN_PLAY);
//		private static final CharacterState DEAD = new CharacterState(CharacterState.DEAD);
//		
//		public CharacterState state(CharacterState newState) {
//			return from(newState);
//		}
//
//		public static CharacterState from(CharacterState available) {
//			return switch(available) {
//			case AVAILABLE -> AVAILABLE;
//			case IN_PLAY -> IN_PLAY;
//			case DEAD -> DEAD;
//			};
//		}
//	}
}
