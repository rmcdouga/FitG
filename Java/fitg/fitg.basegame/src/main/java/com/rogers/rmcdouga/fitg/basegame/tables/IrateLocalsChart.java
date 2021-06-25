package com.rogers.rmcdouga.fitg.basegame.tables;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;

public enum IrateLocalsChart {
	;
	
	public static Squad result(BaseGameRaceType race, BaseGameEnvironType environType) {
		return race.getSquad(environType).orElseThrow(()->new IllegalArgumentException(race.getName() + " do not exist in " + environType + " environs."));
	}
}
