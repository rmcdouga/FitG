package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.requireBgp;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import com.rogers.rmcdouga.fitg.basegame.Scenario;

public class BaseGameLoyaltyManager implements LoyaltyManager {

	Map<BaseGamePlanet, LoyaltyType> loyalties = new EnumMap<>(BaseGamePlanet.class);

	// Prevent instantiation by anybody else.
	private BaseGameLoyaltyManager(Collection<StarSystem> map, Scenario.Type scenarioType) {
		// Set up the loyalties for the planets in this scenario.
		Function<Planet, LoyaltyType> loyalyFn = switch(scenarioType) {
			case Armegeddon -> Planet::getStartingLoyaltyA;
			case StartRebellion -> Planet::getStartingLoyaltyS;
			};
		map	.stream()
			.flatMap(ss->ss.listPlanets().stream())			// Convert stream of StarSystem to stream of Planets
			.forEach(p->loyalties.put(requireBgp(p), loyalyFn.apply(p)));	// for each Planet, set up the Loyalty.
	}

	@Override
	public LoyaltyType getLoyalty(Planet planet) {
		return loyalties.get(requireBgp(planet));
	}

	@Override
	public LoyaltyManager shiftLeft(Planet planet) {
		loyalties.compute(requireBgp(planet), (k,v)->v.shiftLeft());
		// TODO:  Handle any domino effects
		return this;
	}

	@Override
	public LoyaltyManager shiftRight(Planet planet) {
		loyalties.compute(requireBgp(planet), (k,v)->v.shiftRight());
		// TODO:  Handle any domino effects
		return this;
	}

	public static BaseGameLoyaltyManager create(Collection<StarSystem> map, Scenario.Type scenarioType) {
		return new BaseGameLoyaltyManager(map, scenarioType);
	}
}
