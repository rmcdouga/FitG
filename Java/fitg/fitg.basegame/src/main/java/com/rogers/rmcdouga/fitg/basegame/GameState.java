package com.rogers.rmcdouga.fitg.basegame;

public interface GameState<S extends Record> {
	
	public S getState();
	public void setState(S state);

}
