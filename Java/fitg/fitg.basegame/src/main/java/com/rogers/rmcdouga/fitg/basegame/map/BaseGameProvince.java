package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BaseGameProvince {
	One(), Two(), Three(), Four(), Five();
	
	private final Supplier<List<BaseGameStarSystem>> starSystems;
	
	private BaseGameProvince(Supplier<List<BaseGameStarSystem>> starSystems) {
		this.starSystems = starSystems;
	}
	
	private BaseGameProvince() {
		this.starSystems = this::listStarSystems;
	}
	
	public String getName() {
		return this.toString();
	}
	public int getId() {
		return this.ordinal() + 1;
	}

	public List<BaseGameStarSystem> getStarSystems() {
		return starSystems.get();
	}
	
	private List<BaseGameStarSystem> listStarSystems() {
		return Stream.of(BaseGameStarSystem.values())
					 .filter(s->this.equals(s.getProvince()))
					 .collect(Collectors.toUnmodifiableList());
	}
}
