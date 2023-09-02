package com.rogers.rmcdouga.fitg.basegame.utils;

/**
 * Each object in the game has a set of immutable values (their model) and mutable values (their state).
 * Objects that implement this interface have state.
 * 
 * @param <T>
 * 	A record that represents all the mutable values of this object.
 */
public interface State<T> {
	/**
	 * Returns the state of the object.
	 * 
	 * @return
	 * 	the object's state
	 */
	T state();
	/**
	 * Sets the state of the object
	 * 
	 * @param state
	 * 	the new state of the object
	 */
	void state(T state);
}
