package com.rogers.rmcdouga.fitg.basegame;

public abstract class CardAbstractClass implements Card {

	private final int cardNumber;

	protected CardAbstractClass(int cardNumber) {
		super();
		this.cardNumber = cardNumber;
	}

	@Override
	public int getCardNumber() {
		return cardNumber;
	}
	
}
