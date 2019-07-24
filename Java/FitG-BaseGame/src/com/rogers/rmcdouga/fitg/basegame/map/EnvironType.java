package com.rogers.rmcdouga.fitg.basegame.map;

public enum EnvironType {
	Urban(false), Wild(false), Liquid(true), Subterranian(true), Air(true), Fire(true);
	
	private final boolean isSpecial;

	private EnvironType(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * @return the isSpecial
	 */
	public boolean isSpecial() {
		return isSpecial;
	}
}
