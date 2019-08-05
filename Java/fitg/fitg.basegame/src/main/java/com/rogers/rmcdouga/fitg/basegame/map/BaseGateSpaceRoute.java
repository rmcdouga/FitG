package com.rogers.rmcdouga.fitg.basegame.map;

public enum BaseGateSpaceRoute {
	foo(BaseGameStarSystem.Bex, BaseGameStarSystem.Bex, 0);
	
	private final BaseGameStarSystem terminus1;
	private final BaseGameStarSystem terminus2;
	private final int navigationStars;
	
	private BaseGateSpaceRoute(BaseGameStarSystem end1, BaseGameStarSystem end2, int navigationStars) {
		this.terminus1 = end1;
		this.terminus2 = end2;
		this.navigationStars = navigationStars;
	}
}
