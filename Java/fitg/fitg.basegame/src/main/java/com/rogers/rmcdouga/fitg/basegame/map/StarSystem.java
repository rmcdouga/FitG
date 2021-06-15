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
	public Drift drift();
	public Drift drift2();
	
	public interface Drift extends Location {
		public enum DriftType {
			Drift, Drift2;
		}
		public DriftType type();
		public StarSystem starSystem();
	}

}
