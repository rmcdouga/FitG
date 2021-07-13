package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.units.Possession;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;

public interface PossessionPool {
	Possession getPossession(Possession possesion);
	Optional<Spaceship> getSpaceship(RebelSpaceship possesion);
	PossessionPool lost(Possession possesion); // Note: lost spaceships are destroyed (see 14.55)
	PossessionPool captured(Possession possesion);	// Captured by enemy
	PossessionPool destroyed(Possession possesion);	// Not sure if this is required, maybe just use lost().
	
	Optional<Possession> randomPossession();
}
