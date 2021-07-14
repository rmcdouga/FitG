package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

public class MockPlayerStrategies {
	public static class FlightToEgrixStrategies {
		public static class ScenarioRebelDecisions implements Scenario.PlayerDecisions {

			@Override
			public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
				throw new UnsupportedOperationException("Set Pdbs should not be called for Rebel Player in Flight To Egrix"); 
			}

			@Override
			public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
				throw new UnsupportedOperationException("Place Counters should not be called for Rebel Player in Flight To Egrix"); 
			}
			
		}
		public static class ScenarioImperialDecisions implements Scenario.PlayerDecisions {

			@Override
			public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
				throw new UnsupportedOperationException("Set Pdbs should not be called for Imperial Player in Flight To Egrix"); 
			}

			@Override
			public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
//				if (counters.size() != 1) {
//					throw new IllegalArgumentException("Expected one stack of counters to be placed but was passed " + counters.size() +  " counters.");
//				}
				Counter spaceship = counters.stream()
											.flatMap(c->c instanceof Stack s ? s.stream() : Stream.of(c))
											.filter(c->c == BaseGameImperialSpaceship.Imperial_Spaceship)
											.findAny()
											.get();
				List<Counter> characters = counters.stream().filter(c->c != BaseGameImperialSpaceship.Imperial_Spaceship).collect(Collectors.toList());
				SpaceshipStack stack = stackMgr.of((Spaceship)spaceship, characters.toArray(new Counter[0]));
				
				return List.of(new PlaceCountersInstruction(stack, BaseGamePlanet.Quibron.environ(0)));
			}
			
		}
	}
}
