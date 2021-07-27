package com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded;

import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Jon_Kidu;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.Vans_Ka_Tia_A;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.AbstractStrategy;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
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
		Counter jonKidu = findCounter(counters, Jon_Kidu).orElseThrow(()->new IllegalArgumentException("Jon Kidu should have been provided."));
		Counter vansKaTiaA = findCounter(counters, Vans_Ka_Tia_A).orElseThrow(()->new IllegalArgumentException("Vans_Ka_Tia_A should have been provided."));
		
		return List.of(new PlaceCountersInstruction(stackMgr.of(imperialSpaceship, jonKidu, vansKaTiaA), BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).get()));
	}

	private static Optional<ImperialSpaceship> findImperialSpaceship(Collection<Counter> counters) {
		for (Counter counter : counters) {
			if (counter instanceof ImperialSpaceship ss) {
				return Optional.of(ss);
			}
		}
		return Optional.empty();
	}

	private static Optional<Counter> findCounter(Collection<Counter> counters, Counter targetCounter) {
		for (Counter counter : counters) {
			if (counter == targetCounter) {
				return Optional.of(counter);
			}
		}
		return Optional.empty();
	}

}
