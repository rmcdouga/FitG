package com.rogers.rmcdouga.fitg.basegame.box;

import com.rogers.rmcdouga.fitg.basegame.units.Possession;

public interface PossessionPool {
	Possession getPossession(Possession possesion);
	PossessionPool lost(Possession possesion); // Note: lost spaceships are destroyed (see 14.55)
	PossessionPool captured(Possession possesion);	// Captured by enemy
	PossessionPool destroyed(Possession possesion);	// Not sure if this is required, maybe just use lost().
	
	Possession random();
}
