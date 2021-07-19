package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public interface GameBox extends CharacterPool, CounterPool, PossessionPool {

//	default <T extends Counter> Optional<? extends Counter> getCounter(T counter) {
//		if (counter instanceof Character character) {
//			return Optional.of(getCharacter(character));
//		} else if (counter instanceof ImperialSpaceship spaceship) {
//			return getSpaceship(spaceship).map(ImperialSpaceship.class::cast);
//		} else if (counter instanceof Unit unit) {
//			return getCounter(unit).map(Unit.class::cast);
//		} else if (counter instanceof Stack stack) {
//			return getStack(stack).map(Stack.class::cast);
//		}
//		throw new IllegalArgumentException("Unable to get " + counter + " from box.  Unexpected type (" + counter.getClass().getName() + ").");				
//	}
	@SuppressWarnings("unchecked")
	default <T extends Counter> Optional<T> get(T counter) {
		if (counter instanceof Character character) {
			return Optional.of((T)getCharacter(character));
		} else if (counter instanceof ImperialSpaceship spaceship) {
			return getSpaceship(spaceship).map(o->(T)o);
		} else if (counter instanceof Unit unit) {
			return getCounter(unit).map(o->(T)o);
		} else if (counter instanceof Stack stack) {
			return getStack(stack).map(o->(T)o);
		}
		throw new IllegalArgumentException("Unable to get " + counter + " from box.  Unexpected type (" + counter.getClass().getName() + ").");				
	}

	default <T extends Counter> Optional<Stack> get(StackManager stackMgr, @SuppressWarnings("unchecked") T... counters) {
		return getCounters(Arrays.asList(counters)).map(stackMgr::of);
	}
	
	default <T extends Counter> Optional<SpaceshipStack> get(StackManager stackMgr, Spaceship impSs, @SuppressWarnings("unchecked") T... counters) {
		Optional<Spaceship> spaceship = getSpaceship(impSs);
		if (spaceship.isEmpty()) {
			return Optional.empty();
		}
		return getCounters(Arrays.asList(counters)).map(c->stackMgr.of(spaceship.get(), c));
	}
	
	private <T extends Stack> Optional<? extends Stack> getStack(T stack) {
		Optional<Collection<Counter>> stackContents = getCounters(stack);
		if (stackContents.isEmpty()) {
			return Optional.empty();
		}
		if (stack instanceof SpaceshipStack ssStack) {
			Spaceship spaceship = ssStack.spaceship();
			Optional<Spaceship> spaceshipCounter = getSpaceship(spaceship);
			if (spaceshipCounter.isEmpty()) {
				return Optional.empty();
			}
			return Optional.of(stack.replace(spaceshipCounter.get(), stackContents.get()));
		} else {	// regular stack
			return Optional.of(stack.replace(stackContents.get()));
		}

	}
	
	// It's unusual to return an Optional of a Collection (you usually return an empty result) however in this case, I want to flag
	// the case where a counter was unavailable while still allow for processing of an empty collection.
	private Optional<Collection<Counter>> getCounters(Collection<Counter> counterCol) {
		List<Counter> result = new ArrayList<>(counterCol.size());
		for (Counter counter : counterCol) {
			Optional<? extends Counter> optCounter = get(counter);
			if (optCounter.isEmpty()) {	// If any of the counters in the stack are unavailable, return empty() 
				return Optional.empty();
			}
			result.add(optCounter.get());
		}
		return Optional.of(result);
	}
	
	private Optional<Spaceship> getSpaceship(Spaceship spaceship) {
		if (spaceship instanceof RebelSpaceship rebSs) {
			return Optional.of(getPossession(rebSs)).map(Spaceship.class::cast);
		} else if (spaceship instanceof ImperialSpaceship impSs) {
			return getSpaceship(impSs);
		} else {
			throw new IllegalArgumentException("Unable to get " + spaceship + " from box.  Unexpected type (" + spaceship.getClass().getName() + ").");				
		}
	}
}
