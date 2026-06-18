package com.rogers.rmcdouga.fitg.basegame.units;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;

public interface Unit extends Counter {
	public int environCombatStrength();
	public int spaceCombatStrength();
	public boolean isMobile();
	public Faction faction();
}
