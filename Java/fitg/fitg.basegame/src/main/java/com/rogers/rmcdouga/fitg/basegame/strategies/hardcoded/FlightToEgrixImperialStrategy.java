package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Jon_Kidu;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Vans_Ka_Tie_A;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;

public class FlightToEgrixImperialStrategy extends AbstractStrategy {

	@Override
	public Collection<SetPdbInstructions> setPdbs(Collection<StarSystem> map) {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public Collection<PlaceCountersInstruction> placeCounters(Collection<Counter> counters, StackManager stackMgr) {
		Spaceship imperialSpaceship = findImperialSpaceship(counters).orElseThrow(()->new IllegalArgumentException("An Imperial Spaceship should have been provided."));
		Counter jonKidu = findUniqueCounter(counters, c->c == Jon_Kidu).orElseThrow(()->new IllegalArgumentException("Jon Kidu should have been provided."));
		Counter vansKaTiaA = findUniqueCounter(counters, c->c == Vans_Ka_Tie_A).orElseThrow(()->new IllegalArgumentException("Vans_Ka_Tia_A should have been provided."));
		
		PlaceCountersInstruction placeCharacters = new PlaceCountersInstruction(stackMgr.of(imperialSpaceship, jonKidu, vansKaTiaA), BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).get());

		// Place one Line at Angoff Urban
		List<Counter> lineCounters = findCounter(counters, c->c.id().equalsIgnoreCase("Line"));
		if (lineCounters.size() != 1) {
			throw new IllegalArgumentException("A single Line counter should have been provided.");
		}
		PlaceCountersInstruction placeLineUnit = new PlaceCountersInstruction(lineCounters.getFirst(), BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).get());
		
		
		// Find three Patrol units, place them in each of the other environs
		List<Counter> patrolCounters = findCounter(counters, c->c.id().equalsIgnoreCase("Patrol"));
		if (patrolCounters.size() != 3) {
			throw new IllegalArgumentException("Three Patrol counters should have been provided.");
		}
		PlaceCountersInstruction placePatrolUnit1 = new PlaceCountersInstruction(patrolCounters.get(0), BaseGamePlanet.Charkhan.environ(BaseGameEnvironType.Air).get());
		PlaceCountersInstruction placePatrolUnit2 = new PlaceCountersInstruction(patrolCounters.get(1), BaseGamePlanet.Charkhan.environ(BaseGameEnvironType.Wild).get());
		PlaceCountersInstruction placePatrolUnit3 = new PlaceCountersInstruction(patrolCounters.get(2), BaseGamePlanet.Quibron.environ(BaseGameEnvironType.Wild).get());
		
		// Find three Militia units, place them in each of the other three environs
		List<Counter> militiaCounters = findCounter(counters, c->c.id().equalsIgnoreCase("Militia"));
		if (militiaCounters.size() != 3) {
			throw new IllegalArgumentException("Three Militia counters should have been provided.");
		}
		PlaceCountersInstruction placeMilitiaUnit1 = new PlaceCountersInstruction(militiaCounters.get(0), BaseGamePlanet.Charkhan.environ(BaseGameEnvironType.Air).get());
		PlaceCountersInstruction placeMilitiaUnit2 = new PlaceCountersInstruction(militiaCounters.get(1), BaseGamePlanet.Charkhan.environ(BaseGameEnvironType.Wild).get());
		PlaceCountersInstruction placeMilitiaUnit3 = new PlaceCountersInstruction(militiaCounters.get(2), BaseGamePlanet.Quibron.environ(BaseGameEnvironType.Wild).get());
		
			
		return List.of(placeCharacters, placeLineUnit, placePatrolUnit1, placePatrolUnit2, placePatrolUnit3, placeMilitiaUnit1, placeMilitiaUnit2, placeMilitiaUnit3);
	}

	private static Optional<ImperialSpaceship> findImperialSpaceship(Collection<Counter> counters) {
		for (Counter counter : counters) {
			if (counter instanceof ImperialSpaceship ss) {
				return Optional.of(ss);
			}
		}
		return Optional.empty();
	}

	private static List<Counter> findCounter(Collection<Counter> counters, Predicate<Counter> targetCounter) {
		return counters.stream().filter(targetCounter).toList();
	}

	private static Optional<Counter> findUniqueCounter(Collection<Counter> counters, Predicate<Counter> targetCounter) {
		return counters.stream().filter(targetCounter).findFirst();
	}
}
