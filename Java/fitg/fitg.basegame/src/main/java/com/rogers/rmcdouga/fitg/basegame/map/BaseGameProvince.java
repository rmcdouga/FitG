package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BaseGameProvince {
	One(), Two(), Three(), Four(), Five();
	
	private final Predicate<BaseGameStarSystem> starSystemPredicate = s->this.equals(s.getProvince());
	
	private BaseGameProvince() {
	}
	
	public String getName() {
		return this.toString();
	}
	public int getId() {
		return this.ordinal() + 1;
	}

	public Stream<BaseGameStarSystem> streamStarSystems() {
		return BaseGameStarSystem.stream(starSystemPredicate);
	}

	public List<BaseGameStarSystem> listStarSystems() {
		// May memoize this list at some later point to save on objects, but at this point I'm not sure
		// how much Lists will be used (Streams may be more convenient).
		return streamStarSystems().collect(Collectors.toUnmodifiableList());
	}
	
}
