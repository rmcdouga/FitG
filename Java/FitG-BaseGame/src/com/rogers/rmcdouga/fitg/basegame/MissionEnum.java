package com.rogers.rmcdouga.fitg.basegame;

public enum MissionEnum implements Mission {
	
	SABOTAGE(53, 'S', "Sabotage", "TBD"),
	DIPLOMACY(54, 'D', "Diplomacy", "TBD"),
	START_STOP_REBELLION(55, 'R', "Start Rebellion/Stop Rebellion", "TBD"),
	SCAVENGE(56, 'P', "Scavenge For Possesions", "TBD"),
	GAIN_CHARACTERS(57, 'G', "Gain Characters", "TBD"),
	GATHER_INFORMATION(58, 'I', "Gather Information", "TBD"),
	FREE_PRISONERS(59, 'F', "Free Prisoners", "TBD"),
	ASSASSINATION(60, 'A', "Assassination", "TBD"),
	START_REBEL_CAMP(61, 'B', "Start Rebel Camp", "TBD"),
	SUBVERT_TROOPS(62, 'T', "Subvert Troops", "TBD"),
	COUP(63, 'C', "Coup", "TBD"),
	SUMMON_SOVEREIGN(64, 'E', "Summon Sovereign", "TBD"),
	SPACESHIP_QUEST(65, 'J', "Spaceship Quest", "TBD"),
	STEAL_ENEMY_RESOURCES(66, 'H', "Steal Enemy Resources", "TBD"),
	QUESTION_PRISONER(67, 'Q', "Question Prisoner", "TBD"),
	;
	
	private final MissionCard card;
	private final char mnemonic;
	private final String name;
	private final String description;
	
	private MissionEnum(int cardNumber, char mnemonic, String name, String description) {
		this.card = new MissionCard(cardNumber);
		this.mnemonic = mnemonic;
		this.name = name;
		this.description = description;
	}


	@Override
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Card#getCardNumber()
	 */
	public int getCardNumber() {
		return card.getCardNumber();
	}
	
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getMnemonic()
	 */
	@Override
	public char getMnemonic() {
		return mnemonic;
	}

	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}


	static private class MissionCard extends CardAbstractClass {
		protected MissionCard(int cardNumber) {
			super(cardNumber);
		}
	}

}
