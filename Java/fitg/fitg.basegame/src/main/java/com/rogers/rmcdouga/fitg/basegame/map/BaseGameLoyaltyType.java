package com.rogers.rmcdouga.fitg.basegame.map;

public enum BaseGameLoyaltyType implements LoyaltyType {
	Patriotic, Loyal, Neutral, Dissent, Unrest;
	
	@Override
	public String getName() {
		return this.toString();
	}
	
	@Override
	public LoyaltyType shiftLeft() {
		int ordinal = this.ordinal();
		return ordinal > 0 ? values()[ordinal - 1] : this;
	}

	@Override
	public LoyaltyType shiftRight() {
		int ordinal = this.ordinal();
		return ordinal < Unrest.ordinal() ? values()[ordinal + 1] : this;
	}
}
