package com.rogers.rmcdouga.fitg.basegame.map;

public enum SpaceRoute {
	foo(Planet.Mimulus, Planet.Mimulus, 0);
	
	private final Planet end1;
	private final Planet end2;
	private final int navigationStars;
	
	private SpaceRoute(Planet end1, Planet end2, int navigationStars) {
		this.end1 = end1;
		this.end2 = end2;
		this.navigationStars = navigationStars;
	}
}
