package com.rogers.rmcdouga.fitg.basegame;

public enum TurnOrder {
	GAME_TURN;
	
	public enum OperationsPhaseSegment {
		Interplanetary_Military_Movement, Military_Space_Combat, Character_Movement, Surface_Military_Movement,
		Enemy_Reaction, Environ_Military_Combat, Orbit_Organization;
	}
	
	public enum SearchPhaseSegments {
		Search;

	}
	public enum MissionPhaseSegments {
		Mission_Assignment, Mission_Action, Bonus_Draw;

	}
	
	public enum Phase {
		Operations, Search, Mission;
	}
	
	
}
