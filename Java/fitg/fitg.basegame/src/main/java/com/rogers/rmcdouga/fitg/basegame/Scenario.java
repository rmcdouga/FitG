package com.rogers.rmcdouga.fitg.basegame;

import java.util.function.Supplier;

public enum Scenario {
	FlightToEgrix(Type.StartRebellion, Rules.StarSystem, Scenario::createFlightToEgrix);
	
	public enum Type {
		StartRebellion, Armegeddon;
	}
	
	public enum Rules {
		StarSystem, Province, Galactic;
	}
	
	private final Type type;
	private final Rules rules;
	private final Supplier<Game> gameCreator; 

	private Scenario(Type type, Rules rules, Supplier<Game> gameCreator) {
		this.type = type;
		this.rules = rules;
		this.gameCreator = gameCreator;
	}

	public Type type() {
		return type;
	}
	public Rules rules() {
		return rules;
	}
	public Game createGame() {
		return this.gameCreator.get();
	}

	private static Game createFlightToEgrix() {
		return null;
	}
}
