package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

class BaseGameEnvironTest {

	@SuppressWarnings("unused")
	private static final List<Environ> allEnvirons = List.of(
			BaseGameEnviron.air(1).build(),
			BaseGameEnviron.fire(1).build(),
			BaseGameEnviron.liquid(1).build(),
			BaseGameEnviron.subterranian(1).build(),
			BaseGameEnviron.wild(1).build(),
			BaseGameEnviron.urban(1).build()
			);
	
	@ParameterizedTest
	@FieldSource("allEnvirons")
	void testRequireBgEnviron(Environ environ) {
		assertDoesNotThrow(() -> {
			BaseGameEnviron bgEnviron = BaseGameEnviron.requireBgEnviron(environ);
			assertNotNull(bgEnviron);
			assertEquals(environ,  bgEnviron);
		});
	}

	@Test
	void testRequireBgEnviron_failure() {
		assertThrows(IllegalArgumentException.class, () -> {
			Environ mockEnviron = new Environ() {

				@Override
				public EnvironType getType() {
					return new EnvironType() {

						@Override
						public String getName() {
							return "MockEnvironType";
						}

						@Override
						public boolean isSpecial() {
							return false;
						}
						
					};
				}

				@Override
				public int getSize() {
					return 0;
				}

				@Override
				public OptionalInt getResources() {
					return OptionalInt.empty();
				}

				@Override
				public boolean isStarResources() {
					return false;
				}

				@Override
				public OptionalInt getCoupRating() {
					return OptionalInt.empty();
				}

				@Override
				public List<RaceType> getRaces() {
					return List.of();
				}

				@Override
				public Optional<BaseGameCreature> getCreature() {
					return Optional.empty();
				}

				@Override
				public Optional<BaseGameSovereign> getSovereign() {
					return Optional.empty();
				}
				
			};
			BaseGameEnviron.requireBgEnviron(mockEnviron);
		});
	}

}
