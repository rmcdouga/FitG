package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.Collections;
import java.util.List;

public enum BaseGameProvince {
	One(Collections.emptyList()), Two(Collections.emptyList()), Three(Collections.emptyList()), Four(Collections.emptyList()), Five(Collections.emptyList());
	
	private final List<BaseGameStarSystem> starSystems;
	
	
	private BaseGameProvince(List<BaseGameStarSystem> starSystems) {
		this.starSystems = starSystems;
	}
	
	public String getName() {
		return this.toString();
	}
	public int getId() {
		return this.ordinal() + 1;
	}

	public List<BaseGameStarSystem> getStarSystems() {
		return starSystems;
	}
}
