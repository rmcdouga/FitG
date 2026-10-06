package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Action extends Card {

	public enum EnvironType { 
		URBAN, SPECIAL, WILD;
		
		public static EnvironType from(Environ environ) {
			return switch(environ.getType().getName()) {
				case "Urban" -> URBAN;
				case "Wild" -> WILD;
				case "Air", "Fire", "Liquid", "Subterranian" -> SPECIAL;
				default -> throw new IllegalArgumentException("Unknown EnvironType: " + environ.getType().getName());
			};
		}
	}

	public MarkdownString getResultDescription(EnvironType environType);
	public Set<Mission> getMissions(EnvironType environType);
	
	public static interface ActionFactory {
		public Set<Action> allActions();
		public int numberOfActions();
		public Optional<Action> getAction(int cardNo);
	}
	
	public static ActionFactory defaultFactory() {
		return BaseGameAction.actionfactory();
	}
	
	public default boolean isSuccessful(Mission mission, EnvironType environType) {
		return getMissions(environType).contains(mission);
	}

	public default String getMissionLetters(EnvironType environType, CharSequence delimiter) {
		return getMissions(environType).stream()
				.map(Mission::mnemonic)
				.map(c->Character.toString(c))
				.sorted()
				.collect(Collectors.joining(delimiter));
	}

	public default String getMissionLetters(EnvironType environType) {
		return getMissionLetters(environType, " ");
	}

}
