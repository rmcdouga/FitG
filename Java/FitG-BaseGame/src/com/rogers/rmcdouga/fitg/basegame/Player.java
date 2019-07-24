package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;

public interface Player {

	/**
	 * @return the name
	 */
	String name();

	/**
	 * @return the faction
	 */
	Faction faction();

}