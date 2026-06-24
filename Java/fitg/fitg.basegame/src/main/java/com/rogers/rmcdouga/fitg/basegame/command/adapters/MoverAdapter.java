package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import com.rogers.rmcdouga.fitg.basegame.command.UserCommandProcessing;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

/**
 * This class implements the Mover interface and serves as an adapter between the external API and t
 * he internal game engine. It translates the external commands into the internal data structures 
 * and sends a MoveCommand to the game engine to perform the move operations.
 */
public class MoverAdapter implements Mover {
	
	private final UserCommandProcessing userCommandProcessing;
	private final CounterFinder counterFinder;
	private final LocationFinder locationFinder;
	
	public MoverAdapter(UserCommandProcessing userCommandProcessing, 
						CounterFinder counterFinder,
						LocationFinder locationFinder
						) {
		this.userCommandProcessing = userCommandProcessing;
		this.counterFinder = counterFinder;
		this.locationFinder = locationFinder;
	}

	@Override
	public Mover moveUnitCounter(String unitType, String currentStarOrPlanetId, String currentLocation, 
												  String destinationStarOrPlanetId, String destinationLocation) {
		// Locate a unit of this type at this location, determine the id
		// then move it to the destination
		counterFinder.findCounter(unitType, currentStarOrPlanetId, currentLocation)
					 .map(Unit.class::cast)
					 .ifPresent(counter -> moveUnit(counter, locationFinder.findLocation(destinationStarOrPlanetId, destinationLocation)));
		return this;
	}

	private void moveUnit(Unit counter, Location destLocation) {
		// Create a MoveCommand and send it to the UserCommandProcessing
		userCommandProcessing.processCommand(new MoveCommand.UnitMove(counter, destLocation));
	}

	@Override
	public Mover moveCounter(String counterId, String destinationStarOrPlanetId, String destinationLocation) {
		// perform queries to get units and locations, then create a MoveCommand
		counterFinder.findCounter(counterId)
		 .ifPresent(counter -> moveCounter(counter, locationFinder.findLocation(destinationStarOrPlanetId, destinationLocation)));
		return this;
	}

	private void moveCounter(Counter counter, Location location) {
		switch (counter) {
			case Unit unit -> moveUnit(unit, location);
			case Character character -> moveCharacter(character, location);
			case Stack stack -> moveStack(stack, location);
			default -> throw new IllegalArgumentException("Counter: " + counter.id() + " cannot be moved.  Only Unit, Character, and Stack can be moved.");
		}
	}

	private void moveStack(Stack stack, Location location) {
		userCommandProcessing.processCommand(new MoveCommand.StackMove(stack, location));
	}

	private void moveCharacter(Character character, Location location) {
		userCommandProcessing.processCommand(new MoveCommand.CharacterMove(character , location));
	}

}
