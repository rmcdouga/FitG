package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import com.rogers.rmcdouga.fitg.basegame.command.CommandDispatcher;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;

/**
 * This class implements the Mover interface and serves as an adapter between the external API and t
 * he internal game engine. It translates the external commands into the internal data structures 
 * and sends a MoveCommand to the game engine to perform the move operations.
 */
public class MoverAdapter implements Mover {
	
	private final CommandDispatcher commandDispatcher;
	private final CounterFinder counterFinder;
	private final LocationFinder locationFinder;
	

	public MoverAdapter(CommandDispatcher commandDispatcher, 
					    CounterFinder counterFinder,
					    LocationFinder locationFinder) {
		this.commandDispatcher = commandDispatcher;
		this.counterFinder = counterFinder;
		this.locationFinder = locationFinder;
		super();
	}

	@Override
	public Mover moveUnit(String unitType, String location, String destination) {
		// TODO: perform queries to get units and locations, then create and execute a MoveCommand
		// Locate a unit of this type at this location, determine the id and then move it to the destination
		return null;
	}

	@Override
	public Mover moveUnit(String unitId, String destination) {
		// TODO: perform queries to get units and locations, then create and execute a MoveCommand
		return null;
	}

}
