package com.rogers.rmcdouga.fitg.basegame.units;

public interface Unit extends Counter {
	public int environCombatStrength();
	public int spaceCombatStrength();
	public boolean isMobile();
}
