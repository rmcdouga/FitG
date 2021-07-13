package com.rogers.rmcdouga.fitg.basegame.units;

public interface Spaceship {

	public int cannons();
	public int shields();
	public int maneuver();
	public int maxPassengers();
	
	public default boolean overLimit(int numChars) {
		return numChars > maxPassengers();
	}
}
