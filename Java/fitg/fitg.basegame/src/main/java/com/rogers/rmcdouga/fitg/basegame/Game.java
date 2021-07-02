package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.CounterPool;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class Game implements GameState {
	private static final String ACTION_DECK_LABEL = "actionDeck";
	private final ActionDeck actionDeck = new ActionDeck();
	private final StackManager stackMgr = new StackManager();
	private final CounterLocations counterLocations = new CounterLocations(stackMgr);
	private final CounterPool unitPool = BaseGameBox.create();
	
	private final Scenario scenario;
	private final Collection<StarSystem> map;
	
	private Game(Scenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		this.scenario = scenario;
		this.map = scenario.createMap();
		scenario.setupCounters(counterLocations, unitPool, stackMgr, rebelDecisions, imperialDecisions);
	}

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

	public static Game createGame(Scenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		Game game = new Game(scenario, rebelDecisions, imperialDecisions);
		
		return game;
	}
	
}
