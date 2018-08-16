package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public enum MissionEnum implements Mission {
	
	SABOTAGE(53, 'S', "Sabotage", 
			"May attempt to damage Enemy PDB or military units by undercover activity.\r\n" + 
			"May only be attempted in Environ occupied by Enemy military units and/or if Enemy PDB is on planet.",
			"If the mission let-ter comes up once in the Action Deck, the player may remove one Enemy 1-0 military unit from the Environ (it is eliminated) or he may place the Enemy PDB Down.\r\n" + 
			"Or, if two mission letters come up, he may remove any one Enemy military unit from the Environ or reduce the Enemy PDB by one level and place it Down."),
	DIPLOMACY(54, 'D', "Diplomacy", 
			"May attempt to shift a planet Loyalty in Player's favor through negotiation with delegates of local populace.", 
			"Every mission let-ter comes up in the Action Deck, shifts the planet Loyalty marker one space in favor of the Phasing Player, to a maximum of two shifts.  May not place a planet in or out of rebellion.\r\n" + 
			"*Bonus Draws:* One character's Diplomacy rating plus one for each other character on the mission plus current Loyalty Rating (see Case 15.11)."),
	START_STOP_REBELLION(55, 'R', "Start Rebellion/Stop Rebellion", 
			"*Rebel Player:* May attempt to start full scale rebellion if planet Loyalty at Unrest.\r\n" + 
			"*Imperial Player:* May attempt to halt rebellion by non-military means on planet in Rebellion.", 
			"Rebel Player:  If the Mission Letter comes up in the Action Deck, flip the Loyalty marker to the Rebellion side.  See Case 15.4.\r\n" + 
			"Imperial Player:  If the mission letter comes up twice in the Action Deck, flip the Rebellion marker to the Loyalty side. No bonus draws."),
	SCAVENGE(56, 'P', "Scavenge For Possesions", 
			"*Rebel Player only:* May attempt to find object of personal use.", 
			"If the mission let-ter comes up in the Action Deck, draw a card from the Possesions Deck.  The new item may be assigned to any character in the mission group.  No more than one new possesion may be gained in one mission."),
	GAIN_CHARACTERS(57, 'G', "Gain Characters", 
			"May attempt to find new friend of worth to the Player's cause.\r\n" + 
			"In the Star System and Province Games, the Imperial Player may perform this mission only if his current number of characters is equal to or less than the number of characters he started the scenario with.", 
			"If the mission letter comes up in the Action Deck, draw a Character Card at random from the appropriate deck.  The new character's counter is placed in the Environ with the Mission Group.  No more than one character may be gained in one mission."),
	GATHER_INFORMATION(58, 'I', "Gather Information", 
			"May attempt to learn details of Enemy deployment or strategic plans.\r\n" + 
			"May not be performed on a planet that is controlled by the Phasing Player if there are no Enemy units in the Environ.", 
			"If one mission letter comes up in the Action Deck, all Enemy military units and caracters on the planet are revealed and detected.\r\n" + 
			"Star System and Province Games: If two mission letters come up the above occurs and the Phasin Player earns one Victory Point for learning an Enemy secret.\r\n" + 
			"Galactic Game: If two mission letters come up, the above happens and the Player learns an Enemy Secret (see Case 37.4)."),
	FREE_PRISONERS(59, 'F', "Free Prisoners", 
			"May attempt to free Friendly captured characters in the same Environ.", 
			"Every time that the mission letter comes up in the Action Deck, one captured character may be freed; place the captured character with the Mission Group.  If more that one character is currently captured, the Phasing Player takes the one of his choice from among the Enemy's prisoners (but only those in the same Environ)."),
	ASSASSINATION(60, 'A', "Assassination", 
			"May attempt to assassinate any one detected Enemy character in Environ.\r\n" + 
			"May only be performed in an Environ occupied by at least one detected Enemy character (who must be named before drawing Action Cards).", 
			"If the mission letter comes up in the Action Deck, the  named Enemy character is removed from play.  If it does not come up, roll the die.  On a roll of 4 or 5 all characters on the mission are captured; on a roll of 6, all characters on the mission are killed.\r\n" + 
			"Bonus Draws: One character's Intelligence rating."),
	START_REBEL_CAMP(61, 'B', "Start Rebel Camp", 
			"*Rebel Player only:* May attempt to establish a band of locals dedicated to the Rebel cause.\r\n" + 
			"May be performed only on a planet that is cur-rently Patriotic or Loyal.", 
			"If the mission let-ter comes up in the Action Deck, place a Rebel Camp marker in the Environ.  Beginning with the next Rebel Player-Turn, the camp may perform certain missions, within the restrictions of Case 13.2.  The camp is removed from the game im-mediately if the planet's loyalty becomes Dissent or Unrest, or if there are a number of Imperial military Strength Points in the Environ equal to or greater than the Environ Size plus three.\r\n" + 
			"Bonus Draws: One character's Leadership rating."),
	SUBVERT_TROOPS(62, 'T', "Subvert Troops", 
			"*Rebel Player only:*  May attempt to subvert Im-perial Militia, Patrol, or Line Military units to the Rebel cause..\r\n" + 
			"May be performed only in an Environ occupied by an Imperial military unit(s).", 
			"If the mission letter comes up in the Action Deck, the Imperial Player must remove one Imperial Militia military unit from the Environ.  It is replaced with a 1-0 Rebel military unit of the same Environ type.  If the mission letter comes up twice, two Militia, one Patrol or one Line unit is removed and replaces with a 2-1 military unit.\r\n" + 
			"Bonus Draws: One character's Leadership rating."),
	COUP(63, 'C', "Coup", 
			"May attempt to place a small group favorable to the Player's cause in control of an Environ.\r\n" + 
			"May be performed only in an Environ with a Coup rating and if at least one character in the Mission Group has an Intelligence rating greater than one.", 
			"Every mission letter that comes up in the Action Deck shifts the planet Loyalty marker one space in favor of the Phasing Player.  May not place a planet in or out of rebellion.  Some Action Events may abort a Coup mission with adverse effects to the Mission Group.\r\n" + 
			"Bonus Draws: One character's Leadership rating plus the planet's Coup rating."),
	SUMMON_SOVEREIGN(64, 'E', "Summon Sovereign", 
			"May attempt to summon a sovereign and his loyal followers to the Player's cause.\r\n" + 
			"May be attempted only in an Environ occupied by a Friendly or Neutral sovereign, within the restriction of Case 25.1", 
			"If one mission letter comes up in the Action Deck, a friendly sovereign has been  summoned, see Case 25.1.  If two mission letters come up, a neutral sovereign has been summoned, with the same effects as above.  If the mission fails, see Case 25.15.\r\n" + 
			"Bonus Draws: One character's Diplomacy rating plus one chracter's Leadership rating."),
	SPACESHIP_QUEST(65, 'J', "Spaceship Quest", 
			"May attempt to free a captured character in an Orbit  Box.  In the Galactic Game, the Rebel Player may attempt to destroy an Imperial Atrocuty  unit in an Orbit Box.\r\n" + 
			"May be performed only by a character with a Navigation rating of two or higher and a spaceship, in an Orbit Box that contains a cap-tured Friendly character or, if the Rebel performing mission, an Imperial Atrocity unit.", 
			"If one mission let-ter comes up in the Action Deck, one captured character has been rescued; place his counter with the Mission Group. If two mission letters come up , see Section 24.0.  The mission receives only Bonus Draws (see Case 24.0)\r\n" + 
			"Bonus Draws: Evasion value of the pilot and spaceship (see Case 14.52) plus the Cannon rating of the spaceship."),
	STEAL_ENEMY_RESOURCES(66, 'H', "Steal Enemy Resources", 
			"May attempt to take Force Points from the Enemy player's Resource Track and add them to yours. May only be attempted if there are at least two Force Points on the Enemy Resource Track. Rebel Player may only perform on an imperial controlled Capital or Throne Planet, ot planet in the province scheduled to be taxed this Game-Turn (see Case 33.1). The Imperial Player may only perform on the Rebel secret base, after it is revealed (see Case 34.2)", 
			"If one mission let-ter comes up in the Action Deck, two Force Points are subtracted from the Enemy Player's Resource Track and added to the Phasing  Player's  Track.  If two mission letters come up, eight Force Points are subtracted from the Enemy Track and added to the Phasing Player's.\r\n" + 
			"Bonus Draws: One character's Intelligence rating plus one for each other character in the Mission Group."),
	QUESTION_PRISONER(67, 'Q', "Question Prisoner", 
			"May attempt to learn Enemy secrets by question-ing a captured Enemy character. The Imperial Player may choose to torture a captured Rebel character.\r\n" + 
			"May be performed by only one character stacked with a captured Enemy character.", 
			"If one Mission Letter comes up in the Action Deck, the captured character has revealed an Enemy secret (see Case 37.4).\r\n" + 
			"The mission receives only Bonus Draws (see Case 37.11)\r\n" + 
			"Bonus Draws: Three plus the questioning character's Intelligence rating. If the Imperial Player  announces  torture, he receives six draws, plus and minus the characters' In-telligence ratings (however, see Case 37.12)."),
	;
	
	private final MissionCard card;
	private final char mnemonic;
	private final String missionName;
	private final MarkdownString description;
	private final MarkdownString result;
	
	private MissionEnum(int cardNumber, char mnemonic, String missionName, String description, String result) {
		this.card = new MissionCard(cardNumber);
		this.mnemonic = mnemonic;
		this.missionName = missionName;
		this.description = new MarkdownString(description);
		this.result = new MarkdownString(result);
	}


	@Override
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Card#getCardNumber()
	 */
	public int cardNumber() {
		return card.cardNumber();
	}
	
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getMnemonic()
	 */
	@Override
	public char mnemonic() {
		return mnemonic;
	}

	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getName()
	 */
	@Override
	public String missionName() {
		return missionName;
	}

	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getDescription()
	 */
	@Override
	public MarkdownString description() {
		return description;
	}


	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Mission#getResult()
	 */
	@Override
	public MarkdownString result() {
		return result;
	}


	 private static class MissionCard extends CardAbstractClass {
		protected MissionCard(int cardNumber) {
			super(cardNumber);
		}
	}

}
