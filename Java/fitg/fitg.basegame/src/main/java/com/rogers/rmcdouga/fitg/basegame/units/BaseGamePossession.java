package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum BaseGamePossession {
	;

	public static Stream<Possession> stream() {
		return Stream.of(BaseGameSpaceship.stream(), 
						 BaseGameWeapon.stream(), 
						 BaseGameObject.stream(), 
						 BaseGameCompanion.stream())
				      .flatMap(s->s);
	}
	
	public static Stream<Possession> stream(Predicate<Possession> predicate) {
		return stream().filter(predicate);
	}

}
