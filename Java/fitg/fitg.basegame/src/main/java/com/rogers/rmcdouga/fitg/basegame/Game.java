package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.ActionDeck.ActionDeckState;
import com.rogers.rmcdouga.fitg.basegame.CounterLocations.CounterLocationsState;
import com.rogers.rmcdouga.fitg.basegame.Game.InternalGameState;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.PlaceCountersInstruction;
import com.rogers.rmcdouga.fitg.basegame.Scenario.PlayerDecisions.SetPdbInstructions;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.MapWalker;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class Game implements GameState<InternalGameState>, GameBoard, CounterLocator {
	
	private final ActionDeck actionDeck = new ActionDeck();
	private final GameBoard gameBoard;
	
	private final StackManager stackMgr = new StackManager();
	private final GameBox gameBox = BaseGameBox.create();
	private final CounterLocations counterLocations = new CounterLocations(gameBox);
	
	private final Scenario scenario;
	
	private Game(Scenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		this.scenario = scenario;
		this.gameBoard = BaseGameGameBoard.create(scenario.createMap(), scenario.type());
		scenario.setupPdbs().forEach(this::setupPdb);
		scenario.setupCounters(gameBox, stackMgr, rebelDecisions, imperialDecisions).forEach(this::placeCounter);
	}

	@Override
	public Collection<Counter> countersAt(Location location) {
		return counterLocations.countersAt(location);
	}

	/**
	 * @return the actionDeck
	 */
	public ActionDeck actionDeck() {
		return actionDeck;
	}

	public record InternalGameState(ActionDeckState actionDeckState, CounterLocationsState counterLocationsState) {};
	
	@Override
	public InternalGameState getState() {
		return new InternalGameState(actionDeck.getState(), counterLocations.getState());
	}

	@Override
	public void setState(InternalGameState state) {
		actionDeck.setState(state.actionDeckState);
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
	

	private void setupPdb(SetPdbInstructions instruction) {
		switch(instruction.pdb().level()) {		// intentional fall through on the cases...
		case TWO:
			increasePdb(instruction.p());
		case ONE:
			increasePdb(instruction.p());
		case ZERO:
		}
		if (instruction.pdb().isUp()) {
			upPdb(instruction.p());
		}
	}

	private void placeCounter(PlaceCountersInstruction instruction) {
		counterLocations.placeCounter(instruction.location(), instruction.counter());
	}

	@Override
	public void walk(MapConsumer mapConsumer) {
		gameBoard.walk(mapConsumer);
	}
}
