package com.rogers.rmcdouga.fitg.basegame.units;

/**
 * This is a tagging interface that tags things that can have a location
 *
 */
public interface Counter {
	String id();

	default boolean isA(Counter counterType) {
		return this.isA(counterType.id());
	}

	default boolean isA(String counterTypeStr) {
		return this.id().equals(counterTypeStr);
	}
}
