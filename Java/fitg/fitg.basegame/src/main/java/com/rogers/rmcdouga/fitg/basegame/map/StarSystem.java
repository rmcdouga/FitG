package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;

public interface StarSystem {
	public BaseGameProvince getProvince();
	public int getId();
	public String getName();
	public List<? extends Planet> getPlanets();
	public List<? extends SpaceRoute> getSpaceRoutes();
}
