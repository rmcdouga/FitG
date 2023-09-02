package com.rogers.rmcdouga.fitg.basegame.utils;

/**
 * Each object in the game has a set of immutable values (their model) and mutable values (their state).
 * Objects that implement this interface have a model.
 * 
 * @param <T>
 * 	A record that represents all the immutable values of this object.
 */
@FunctionalInterface
public interface Model<T> {
	/**
	 * returns the immutable values for this object
	 * 
	 * @return
	 * 	record that represents the immutable data for this object
	 */
	T model();
}
