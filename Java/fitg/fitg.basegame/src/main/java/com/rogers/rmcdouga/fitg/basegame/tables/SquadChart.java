package com.rogers.rmcdouga.fitg.basegame.tables;

public enum SquadChart {
	Row1(new Squad(4, 4)),
	Row2(new Squad(6, 4)),
	Row3(new Squad(8, 6)),
	Row4(new Squad(10, 6)),
	Row5(new Squad(12, 8)),
	Row6(new Squad(14, 8)),
	;
	
	public static record Squad(int combat, int endurance) {}
	
	private final Squad squad;
	
	private SquadChart(Squad squad) {
		this.squad = squad;
	}


	/**
	 * Lookup the squad characteristics based on military strength
	 * 
	 */
	public static Squad result(int militaryStrength) {
		return determineRow(militaryStrength).squad;
	}
	
	private static SquadChart determineRow(int militaryStrength) {
		if (militaryStrength < 1) {
			throw new IllegalStateException("militaryStrength must be >0.");
		}
		return switch(militaryStrength) {
		case 1 -> Row1;
		case 2 -> Row2;
		case 3,4 -> Row3;
		case 5,6,7 -> Row4;
		case 8, 9, 10, 11 -> Row5;
		default -> Row6;
		};
	}
}
