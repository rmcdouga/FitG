package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Action extends Card {

	public enum EnvironType { URBAN, SPECIAL, WILD }

	public MarkdownString getResultDescription(EnvironType environType);
	public Set<Mission> getMissions(EnvironType environType);
	
	public static interface ActionFactory {
		public Set<Action> allActions();
		public int numberOfActions();
		public Optional<Action> getAction(int cardNo);
	}
	
	public static ActionFactory defaultFactory() {
		return ActionEnum.actionfactory();
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
