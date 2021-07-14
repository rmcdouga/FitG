package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.SetPdbInstructions;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class Game implements GameState, GameBoard {
	private static final String ACTION_DECK_LABEL = "actionDeck";
	private final ActionDeck actionDeck = new ActionDeck();
	private final StackManager stackMgr = new StackManager();
	private final CounterLocations counterLocations = new CounterLocations(stackMgr);
	private final GameBoard gameBoard;
	private final GameBox gameBox = BaseGameBox.create();
	
	private final Scenario scenario;
	
	private Game(Scenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		this.scenario = scenario;
		this.gameBoard = BaseGameGameBoard.create(scenario.createMap(), scenario.type());
		Collection<SetPdbInstructions> pdbsSetup = scenario.setupPdbs();
		Collection<PlaceCountersInstruction> countersSetup = scenario.setupCounters(gameBox, stackMgr, rebelDecisions, imperialDecisions);
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

	@Override
	public Pdb getPdb(Planet planet) {
		return gameBoard.getPdb(planet);
	}

	@Override
	public LoyaltyType getLoyalty(Planet planet) {
		return gameBoard.getLoyalty(planet);
	}

	@Override
	public PdbManager increasePdb(Planet planet) {
		return gameBoard.increasePdb(planet);
	}

	@Override
	public LoyaltyManager shiftLeft(Planet planet) {
		return gameBoard.shiftLeft(planet);
	}

	@Override
	public PdbManager decreasePdb(Planet planet) {
		return gameBoard.decreasePdb(planet);
	}

	@Override
	public LoyaltyManager shiftRight(Planet planet) {
		return gameBoard.shiftRight(planet);
	}

	@Override
	public PdbManager upPdb(Planet planet) {
		return gameBoard.upPdb(planet);
	}

	@Override
	public PdbManager downPdb(Planet planet) {
		return gameBoard.downPdb(planet);
	}

	@Override
	public Collection<StarSystem> getStarSystems() {
		return gameBoard.getStarSystems();
	}
	
}
