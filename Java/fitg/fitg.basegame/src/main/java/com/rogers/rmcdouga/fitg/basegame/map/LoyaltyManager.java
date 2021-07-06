package com.rogers.rmcdouga.fitg.basegame.map;

public interface LoyaltyManager {
	LoyaltyType getLoyalty(Planet planet);
	LoyaltyManager shiftLeft(Planet planet);
	LoyaltyManager shiftRight(Planet planet);
}
