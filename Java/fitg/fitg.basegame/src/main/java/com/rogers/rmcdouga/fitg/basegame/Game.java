package com.rogers.rmcdouga.fitg.basegame;

import java.util.HashMap;
import java.util.Map;

public class Game implements GameState {
	private static final String ACTION_DECK_LABEL = "actionDeck";
	private final ActionDeck actionDeck = new ActionDeck();

	/**
	 * @return the actionDeck
	 */
	public ActionDeck actionDeck() {
		return actionDeck;
	}

	@Override
	public Map<String, Object> getState() {
		Map<String, Object> state = new HashMap<>();
		Map<String, Object> actionDeckState = actionDeck.getState();
		state.put(ACTION_DECK_LABEL, actionDeckState);
		return state;
	}

	@Override
	public void setState(Map<String, Object> state) {
		@SuppressWarnings("unchecked")
		Map<String, Object> actionDeckState = (Map<String, Object>) state.get(ACTION_DECK_LABEL);
		actionDeck.setState(actionDeckState);
	}

	
}
