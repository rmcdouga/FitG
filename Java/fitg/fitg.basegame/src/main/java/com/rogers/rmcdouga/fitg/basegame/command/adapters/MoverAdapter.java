package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;

public class MoverAdapter implements Mover {

	@Override
	public Mover moveUnit(String unitType, String location, String destination) {
		// TODO: perform queries to get units and locations, then create and execute a MoveCommand
		return null;
	}

	@Override
	public Mover moveUnit(String unitId, String destination) {
		// TODO: perform queries to get units and locations, then create and execute a MoveCommand
		return null;
	}

}
