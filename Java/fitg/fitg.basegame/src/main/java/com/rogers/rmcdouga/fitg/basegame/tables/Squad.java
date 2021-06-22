package com.rogers.rmcdouga.fitg.basegame.tables;

public record Squad(int combat, int endurance, boolean fireFight) {
	
	public static Squad of(int combat, int endurance, boolean fireFight) {
		return new Squad(combat, endurance, fireFight);
	}
	public static Squad of(int combat, int endurance) {
		return new Squad(combat, endurance, true);
	}
}
