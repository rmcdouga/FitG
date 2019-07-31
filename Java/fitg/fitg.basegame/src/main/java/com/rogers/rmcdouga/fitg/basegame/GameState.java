package com.rogers.rmcdouga.fitg.basegame;

import java.util.Map;

public interface GameState {
	
	public Map<String, Object> getState();
	public void setState(Map<String, Object> state);

}
