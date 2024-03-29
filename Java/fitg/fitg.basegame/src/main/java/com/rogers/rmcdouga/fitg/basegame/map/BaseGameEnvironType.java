package com.rogers.rmcdouga.fitg.basegame.map;

public enum BaseGameEnvironType implements Environ.EnvironType {
	Urban(false), Wild(false), Liquid(true), Subterranian(true), Air(true), Fire(true);
	
	private final boolean isSpecial;

	private BaseGameEnvironType(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * Name of the EnvironType
	 */
	@Override
	public String getName() {
		return this.toString();
	}
	
	/**
	 * @return the isSpecial
	 */
	@Override
	public boolean isSpecial() {
		return isSpecial;
	}
	
	public static BaseGameEnvironType requireBgEnvironType(Environ.EnvironType environType) {
		if (environType instanceof BaseGameEnvironType bget) {
			return bget;
		}
		throw new IllegalArgumentException("EnvironType (" + environType.getName() + ") is not a BaseGameEnvironType.");
	}
}
