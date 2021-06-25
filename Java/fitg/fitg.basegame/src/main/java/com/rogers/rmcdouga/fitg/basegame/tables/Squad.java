package com.rogers.rmcdouga.fitg.basegame.tables;

public record Squad(int combat, int endurance, boolean fireFight) {
	public static final Squad SQUAD_4_2 = Squad.of(4, 2);
	public static final Squad SQUAD_4_4 = Squad.of(4, 4);
	public static final Squad SQUAD_5_2 = Squad.of(5, 2);
	public static final Squad SQUAD_5_4 = Squad.of(5, 4);
	public static final Squad SQUAD_5_6 = Squad.of(5, 6);
	public static final Squad SQUAD_6_2 = Squad.of(6, 2);
	public static final Squad SQUAD_6_4 = Squad.of(6, 4);
	public static final Squad SQUAD_6_6 = Squad.of(6, 6);
	public static final Squad SQUAD_7_4 = Squad.of(7, 4);
	public static final Squad SQUAD_7_6 = Squad.of(7, 6);
	public static final Squad SQUAD_8_2 = Squad.of(8, 2);
	public static final Squad SQUAD_8_6 = Squad.of(8, 6);

	public static final Squad SQUAD_4_2_HTH = Squad.of(4, 2, false);
	public static final Squad SQUAD_4_3_HTH = Squad.of(4, 3, false);
	public static final Squad SQUAD_4_4_HTH = Squad.of(4, 4, false);
	public static final Squad SQUAD_5_5_HTH = Squad.of(5, 5, false);
	public static final Squad SQUAD_6_5_HTH = Squad.of(6, 5, false);
	public static final Squad SQUAD_7_3_HTH = Squad.of(7, 3, false);
	public static final Squad SQUAD_7_6_HTH = Squad.of(7, 6, false);
	public static final Squad SQUAD_8_2_HTH = Squad.of(8, 2, false);

	
	public static Squad of(int combat, int endurance, boolean fireFight) {
		return new Squad(combat, endurance, fireFight);
	}
	public static Squad of(int combat, int endurance) {
		return new Squad(combat, endurance, true);
	}
}
