package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;

public class BaseGameCounterFinder implements CounterFinder {
	
	private final Game game;
	private static final LocationFinder locationFinder = new BaseGameLocationFinder();

	public BaseGameCounterFinder(Game game) {
		this.game = game;
	}

	@Override
	public Optional<Counter> findCounter(String unitId, String starOrPlanetId, String location) {
		return internalFindCounter(unitId, Objects.requireNonNull(starOrPlanetId), Objects.requireNonNull(location));
	}

	@Override
	public Optional<Counter> findCounter(String unitId) {
		return internalFindCounter(unitId, null, null);
	}

	// Internal method allows null location
	private Optional<Counter> internalFindCounter(String unitId, /* Nullable */ String starOrPlanetId, /* Nullable */String locationStr) {
		Counter counterType = findCounterType(unitId).orElseThrow(() -> new IllegalArgumentException("Unknown unit type: " + unitId));

		if (isUniqueCounterType(counterType)) {
			// Unique counter types are not location dependent however we must ensure they have been placed on the map.
			// If they have no location, return an empty Optional.
			return game.locationOf(counterType).map(__->counterType);
		}
		
		// See if there's only one instance of this counter type in the game.  If so, return it without requiring a location.
		List<Location> locationsWithThisCounter = game.locationOfByType(counterType).toList();
		if (locationsWithThisCounter.size() == 1) {
			Location loc = locationsWithThisCounter.getFirst();
			List<Counter> countersAt = findCountersAt(counterType, loc);
			return Optional.of(countersAt.getFirst());
		}

		// Non-unique counter types require a location.
		if (starOrPlanetId == null || locationStr == null) {
			throw new IllegalArgumentException("Multiple instances of " + unitId + " possible. Please specify a star system or planet.");
		}
		
		List<Counter> counters =  findCountersAt(counterType, findLocation(starOrPlanetId, locationStr));
		
		return counters.isEmpty() ? Optional.empty() : Optional.of(counters.getFirst());	// Return the first one found (since all non-unique counters are the same).
	}

	private static Location findLocation(String starOrPlanetId, String locationStr) {
		return locationFinder.findLocation(starOrPlanetId, locationStr);
	}
	
	private List<Counter> findCountersAt(Counter counterType, Location location) {	

		return game.countersAt(location).stream()
										.filter(counterType::isA)
										.toList();
	}
	
	private static boolean isUniqueCounterType(Counter counterType) {
		// Characters and some spaceships are unique types.
		return switch (counterType) {
			case BaseGameCharacter bc -> true;
			case BaseGameImperialSpaceship bs -> bs == BaseGameImperialSpaceship.Redjacs_Spaceship;
			default -> false;
		};
	}
	
	private static Optional<Counter> findCounterType(String unitId) {
		String normalizedId = CounterFinder.normalizeId(unitId);
		// Search through character counters
		Stream<Counter> counterStream = Stream.of(
				characterStream(),
				imperialSpaceshipStream(),
				imperialMilitaryUnitStream(),
				rebelMilitaryUnitStream()
				)
				.reduce(Stream::concat)
				.orElse(Stream.empty());
		
		return counterStream
				.filter(c->c.isA(normalizedId))
				.findFirst();
	}

	private static Stream<Counter> characterStream() {
		return BaseGameCharacter.stream().map(Counter.class::cast);
	}

	private static Stream<Counter> imperialSpaceshipStream() {
		return BaseGameImperialSpaceship.stream().map(Counter.class::cast);
	}

	private static Stream<Counter> imperialMilitaryUnitStream() {
		return ImperialMilitaryUnit.stream().map(Counter.class::cast);
	}

	private static Stream<Counter> rebelMilitaryUnitStream() {
		return RebelMilitaryUnit.stream().map(Counter.class::cast);
	}

	@Override
	public Optional<Counter> findStackWithCounter(String unitId) {
		return findCounter(unitId).flatMap(game::stackContaining);
	}
}
