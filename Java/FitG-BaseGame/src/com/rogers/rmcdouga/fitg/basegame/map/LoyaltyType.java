package com.rogers.rmcdouga.fitg.basegame.map;

public enum LoyaltyType {
	Patriotic, Loyal, Neutral, Dissent, Unrest;
	
	public LoyaltyType shiftLeft() {
		int ordinal = this.ordinal();
		return ordinal > 0 ? values()[ordinal - 1] : this;
	}

	public LoyaltyType shiftRight() {
		int ordinal = this.ordinal();
		return ordinal < Unrest.ordinal() ? values()[ordinal + 1] : this;
	}
}
