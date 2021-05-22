package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.stream.Stream;

public interface StarSystem {
	public BaseGameProvince getProvince();
	public int getId();
	public String getName();
	public List<? extends Planet> listPlanets();
	public List<? extends SpaceRoute> listSpaceRoutes();
	public Stream<? extends Planet> streamPlanets();
	public Stream<? extends SpaceRoute> streamSpaceRoutes();
}
