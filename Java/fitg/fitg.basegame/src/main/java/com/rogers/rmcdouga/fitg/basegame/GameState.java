package com.rogers.rmcdouga.fitg.basegame;

/**
 * Represents some abstract game state.  It is implemented by classes that contain state that needs to be saved/loaded.
 * 
 * A class that needs to save/load state will define a record that contains the data to be saved, and then use that
 * record as this classes's parameterized class.
 * 
 * @param <S>
 */
public interface GameState<S extends Record> {
	
	public S getState();
	public void setState(S state);

}
