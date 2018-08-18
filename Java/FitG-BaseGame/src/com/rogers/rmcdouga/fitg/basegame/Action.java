package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Action extends Card {

	public enum EnvironType { URBAN, SPECIAL, WILD }

	public boolean isSuccessful(Mission mission, EnvironType environType);
	public MarkdownString getResultDescription(EnvironType environType);
}
