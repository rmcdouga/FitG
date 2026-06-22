package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class FlightToEgrixRebelStrategy extends AbstractStrategy {

	@Override
	public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
		throw new UnsupportedOperationException("Rebels have no decisions to be made in FlightToEgrix scenario");
	}

	@Override
	public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
		throw new UnsupportedOperationException("Rebels have no decisions to be made in FlightToEgrix scenario");
	}
	
	@Override
	public Collection<MoveCommand> movementSegment() {
		return List.of();
	}

	@Override
	public Optional<MoveCommand> reactionMove(Environ environ) {
		return Optional.empty();
	}
}
