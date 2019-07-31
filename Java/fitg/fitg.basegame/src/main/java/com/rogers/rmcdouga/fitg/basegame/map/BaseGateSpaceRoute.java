package com.rogers.rmcdouga.fitg.basegame.map;

public enum BaseGateSpaceRoute {
	foo(BaseGamePlanet.Mimulus, BaseGamePlanet.Mimulus, 0);
	
	private final BaseGamePlanet end1;
	private final BaseGamePlanet end2;
	private final int navigationStars;
	
	private BaseGateSpaceRoute(BaseGamePlanet end1, BaseGamePlanet end2, int navigationStars) {
		this.end1 = end1;
		this.end2 = end2;
		this.navigationStars = navigationStars;
	}
}
