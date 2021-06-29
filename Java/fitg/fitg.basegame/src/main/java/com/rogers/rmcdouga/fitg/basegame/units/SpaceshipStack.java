package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Arrays;
import java.util.Collection;

public class SpaceshipStack extends Stack {
	private final Spaceship spaceship;

	private SpaceshipStack(Spaceship spaceship) {
		super();
		this.spaceship = spaceship;
	}

	private SpaceshipStack(Spaceship spaceship, Collection<Counter> stack) {
		super(stack);
		this.spaceship = spaceship;
	}
	
	public static SpaceshipStack of(Spaceship spaceship, Counter... counters) {
		if (spaceship.overLimit(counters.length)) {
			throw new IllegalArgumentException("Can't add " + counters.length + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters.");
		}
		return new SpaceshipStack(spaceship, Arrays.asList(counters));
	}

	@Override
	public boolean add(Counter e) {
		if (spaceship.overLimit(this.size() + 1)) {
			throw new IllegalArgumentException("Can't add additional characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and is at capacity.");
		}
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends Counter> c) {
		if (spaceship.overLimit(this.size() + c.size())) {
			throw new IllegalArgumentException("Can't add " + c.size() + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and it already has " + this.size() + " passengers.");
		}
		return super.addAll(c);
	}
	
	public boolean overLimit(int numChars) {
		return spaceship.overLimit(numChars);
	}
}
