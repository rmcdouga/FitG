package com.rogers.rmcdouga.fitg.basegame;

import java.util.Set;
import java.util.EnumSet;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public enum ActionEnum implements Action {
	CARD_68(68, null, 
			"TBD", 
			"TBD", 
			"TBD"),
	;

	private final ActionCard actionCard;
	private final Set<Mission> missions;
	private final MarkdownString urbanResult;
	private final MarkdownString specialResult;
	private final MarkdownString wildResult;
	
	
	private ActionEnum(int cardNumber, Set<Mission> missions, String urbanResult, String specialResult, String wildResult) {
		this.actionCard = new ActionCard(cardNumber);
		this.missions = missions;
		this.urbanResult = new MarkdownString(urbanResult);
		this.specialResult = new MarkdownString(specialResult);
		this.wildResult = new MarkdownString(wildResult);
	}

	@Override
	public Set<Mission> missions() {
		return this.missions;
	}

	@Override
	public MarkdownString urbanResult() {
		return this.urbanResult;
	}

	@Override
	public MarkdownString specialResult() {
		return this.specialResult;
	}

	@Override
	public MarkdownString wildResult() {
		return this.wildResult;
	}

	@Override
	public int cardNumber() {
		return this.actionCard.cardNumber();
	}

	private static class ActionCard extends CardAbstractClass {
		protected ActionCard(int cardNumber) {
			super(cardNumber);
		}
	}

}
