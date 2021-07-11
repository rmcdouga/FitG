package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.Optional;
import java.util.stream.Stream;

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
	
	public static Optional<BaseGameLoyaltyType> from(String name) {
		return Stream.of(BaseGameLoyaltyType.values())
					 .filter(t->t.getName().equalsIgnoreCase(name))
					 .findFirst();
	}
	
	public static BaseGameLoyaltyType requireBglt(LoyaltyType loyaltyType) {
		if (loyaltyType instanceof BaseGameLoyaltyType bglt) {
			return bglt;
		}
		throw new IllegalArgumentException("LoyaltyType (" + loyaltyType.getName() + ") is not a BaseGameLoyaltyType.");
	}

}
