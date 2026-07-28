package com.rogers.rmcdouga.fitg.basegame.query.api;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Character;

public interface CharacterFinder {

	/**
	 * Find a character by their ID. The ID is matched case-insensitively and
	 * ignoring non-alphanumeric characters (e.g. spaces, hyphens).
	 *
	 * @param characterId the name or normalized ID of the character to find
	 * @return an {@link Optional} containing the character if found, or an empty
	 *         {@link Optional} if no character matches the given ID
	 */
	Optional<Character> findCharacter(String characterId);

	/**
	 * Find a character by their ID, but only if they are currently in play (i.e.
	 * placed on the map). The ID is matched case-insensitively and ignoring
	 * non-alphanumeric characters (e.g. spaces, hyphens).
	 *
	 * @param characterId the name or normalized ID of the character to find
	 * @return an {@link Optional} containing the character if found and in play, or
	 *         an empty {@link Optional} if no character matches the given ID or the
	 *         character is not currently on the map
	 */
	Optional<Character> findCharacterInPlay(String characterId);

	/**
	 * Normalize a string to be used as a character ID by removing all
	 * non-alphanumeric characters and converting to lower case.
	 *
	 * @param str the string to normalize
	 * @return the normalized string
	 */
	static String normalizeId(String str) {
		return CounterFinder.normalizeId(str);
	}
}