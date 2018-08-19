package com.rogers.rmcdouga.fitg.basegame;

import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Action extends Card {

	public enum EnvironType { URBAN, SPECIAL, WILD }

	public boolean isSuccessful(Mission mission, EnvironType environType);
	public MarkdownString getResultDescription(EnvironType environType);
	
	public static interface ActionFactory {
		public Set<Action> allActions();
		public int numberOfActions();
	}
	
	public static ActionFactory defaultFactory() {
		return ActionEnum.actionfactory();
	}
}
