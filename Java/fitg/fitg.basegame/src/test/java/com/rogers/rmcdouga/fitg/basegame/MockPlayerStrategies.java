package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public class MockPlayerStrategies {
	public static class FlightToEgrixStrategies {
		public static class ScenarioRebelDecisions implements Scenario.PlayerDecisions {

			@Override
			public Collection<StarSystem> setPdbs(Collection<StarSystem> map) {
				throw new UnsupportedOperationException("Set Pdbs should not be called for Rebel Player in Flight To Egrix"); 
			}

			@Override
			public CounterLocations placeCounters(CounterLocations counterLocations, Collection<Counter> counters) {
				throw new UnsupportedOperationException("Place Counters should not be called for Rebel Player in Flight To Egrix"); 
			}
			
		}
		public static class ScenarioImperialDecisions implements Scenario.PlayerDecisions {

			@Override
			public Collection<StarSystem> setPdbs(Collection<StarSystem> map) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CounterLocations placeCounters(CounterLocations counterLocations, Collection<Counter> counters) {
				if (counters.size() != 1) {
					throw new IllegalArgumentException("Expected one stack of counters to be placed but was passed " + counters.size() +  " counters.");
				}
				counters.forEach(c->counterLocations.add(c, BaseGamePlanet.Quibron.environ(0)));

				return counterLocations;
			}
			
		}
	}
}
