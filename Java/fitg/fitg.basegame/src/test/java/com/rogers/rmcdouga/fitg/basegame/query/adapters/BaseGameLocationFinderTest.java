package com.rogers.rmcdouga.fitg.basegame.query.adapters;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;

class BaseGameLocationFinderTest {

	LocationFinder underTest = new BaseGameLocationFinder();
	
	static final List<Arguments> testFindLocationArgs = List.of(
			Arguments.of("11", "drift", Tardyn.drift()),
			Arguments.of("12", "drift2", Uracus.drift2()),
			Arguments.of("Tardyn", "drift", Tardyn.drift()),
			Arguments.of("Uracus", "drift2", Uracus.drift2()),
			Arguments.of("Angoff", "urban", Angoff.environ(BaseGameEnvironType.Urban).orElseThrow()),
			Arguments.of("Angoff", "in orbit", Angoff.inOrbit()),
			Arguments.of("222", "urban", Angoff.environ(BaseGameEnvironType.Urban).orElseThrow()),
			Arguments.of("222", "in orbit", Angoff.inOrbit()),
			Arguments.of("223", "air", Charkhan.environ(BaseGameEnvironType.Air).orElseThrow()),
			Arguments.of("223", "wild", Charkhan.environ(BaseGameEnvironType.Wild).orElseThrow()),
			Arguments.of("232", "sub", Lysenda.environ(BaseGameEnvironType.Subterranian).orElseThrow()),
			Arguments.of("232", "Subterranian", Lysenda.environ(BaseGameEnvironType.Subterranian).orElseThrow()),
			Arguments.of("531", "fire", Scythia.environ(BaseGameEnvironType.Fire).orElseThrow()),
			Arguments.of("412", "liquid", Heliax.environ(BaseGameEnvironType.Liquid).orElseThrow())
			);
	
	@ParameterizedTest
	@FieldSource("testFindLocationArgs")
	void testFindLocation(String starOrPlanetId, String location, Location expected) {
		assertSame(expected, underTest.findLocation(starOrPlanetId, location));
	}

	static final List<Arguments> testFindLocation_BadInputArgs = List.of(
			Arguments.of("11", "foo", List.of("No location found", "in star system", "Tardyn", "foo")),						// Unknown location in known star system
			Arguments.of("Charkhan", "baz", List.of("No location found", "on planet", "Charkhan", "baz")),				// Unknown location in known planet
			Arguments.of("Lysenda", "fire", List.of("No location found", "on planet", "Lysenda", "fire")),				// Known planet but no such environ
			Arguments.of("99", "drift", List.of("No star system found with id", "99")),									// Unknown star system id
			Arguments.of("UnknownStar", "drift", List.of("No star system or planet found with name", "UnknownStar")),	// Unknown star system name
			Arguments.of("9999", "urban", List.of("No planet found with id", "9999")),									// Unknown planet id
			Arguments.of("UnknownPlanet", "urban", List.of("No star system or planet found with name", "UnknownPlanet"))// Unknown planet name
			);
	@ParameterizedTest
	@FieldSource("testFindLocation_BadInputArgs")
	void testFindLocation_BadInput(String starOrPlanetId, String location, List<String> expectedErrors) {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.findLocation(starOrPlanetId, location));
		assertNotNull(ex.getMessage());
		@SuppressWarnings("unchecked")
		Matcher<String>[] expectedStringMatchers = expectedErrors.stream().map(Matchers::containsString).toArray(i->new Matcher[i]);
		assertThat(ex.getMessage(), allOf(expectedStringMatchers));
	}
}