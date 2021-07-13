package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGamePossession;
import com.rogers.rmcdouga.fitg.basegame.units.Possession;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;

public class BaseGamePossessionPool implements PossessionPool {

	private final Map<Possession, PossessionAvailability> pool = new HashMap<>();
	private final IntFunction<Integer> randomFn;

	BaseGamePossessionPool() {
		this(numEntries->(int)(Math.random()*numEntries));
	}
	
	BaseGamePossessionPool(IntFunction<Integer> randomFn) {
		this.randomFn = randomFn;
		BaseGamePossession.stream().forEach(p->pool.put(p, PossessionAvailability.AVAILABLE));
	}

	@Override
	public Possession getPossession(Possession possesion) {
		PossessionAvailability.AVAILABLE.throwExIfNot(checkAvailability(possesion), possesion);
		return changeAvailability(possesion, PossessionAvailability.IN_PLAY);
	}

	@Override
	public Optional<Spaceship> getSpaceship(RebelSpaceship spaceship) {
		if (checkAvailability(spaceship) != PossessionAvailability.AVAILABLE) {
			return Optional.empty();
		}
		return Optional.of((Spaceship)changeAvailability(spaceship, PossessionAvailability.IN_PLAY));
	}

	@Override
	public PossessionPool lost(Possession possesion) {
		PossessionAvailability.IN_PLAY.throwExIfNot(checkAvailability(possesion), possesion);
		changeAvailability(possesion, PossessionAvailability.LOST);
		return this;
	}

	@Override
	public PossessionPool captured(Possession possesion) {
		PossessionAvailability.IN_PLAY.throwExIfNot(checkAvailability(possesion), possesion);
		changeAvailability(possesion, PossessionAvailability.CAPTURED);
		return this;
	}

	@Override
	public PossessionPool destroyed(Possession possesion) {
		PossessionAvailability.IN_PLAY.throwExIfNot(checkAvailability(possesion), possesion);
		changeAvailability(possesion, PossessionAvailability.DESTROYED);
		return this;
	}

	@Override
	public Optional<Possession> randomPossession() {
		return selectRandomly(e->e.getValue() == PossessionAvailability.AVAILABLE)	// Select randomly from AVAILABLE possessions
					.map(this::getPossession);
	}

	private Optional<Possession> selectRandomly(Predicate<Map.Entry<Possession, PossessionAvailability>> predicate) {
		List<Possession> list = this.pool.entrySet()
										 .stream()
										 .filter(predicate)
										 .map(Map.Entry::getKey)
										 .toList();
		return selectRandomly(list);
	}

	private Optional<Possession> selectRandomly(List<Possession> list) {
		return list.isEmpty() ? Optional.empty() 
							  : Optional.of(list.get(randomFn.apply(list.size()))); 
	}
	
	private enum PossessionAvailability {
		AVAILABLE("available"), IN_PLAY("in play"), LOST("lost"), CAPTURED("captured"), DESTROYED("destroyed");
		
		private final String description;

		private PossessionAvailability(String description) {
			this.description = description;
		}
		
		public void throwExIfNot(PossessionAvailability availability, Possession possession) {
			if (this != availability) {
				throw new IllegalArgumentException("Possession (" + possession + ") is not " + this.description + " (" + availability + ").");
			}
		}
	}
	
	private Possession changeAvailability(Possession possession, PossessionAvailability availability) {
		this.pool.replace(possession, availability);
		return possession;
	}
	
	private PossessionAvailability checkAvailability(Possession possession) {
		return this.pool.get(possession);
	}
}
