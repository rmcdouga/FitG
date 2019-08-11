package com.rogers.rmcdouga.fitg.basegame;

public enum BaseGameSovereign {
	Ascaill(3, 3, 2, 1, Allegiance.Imperial, "Ascaill, a Rhone dictator, exerts tyrannical control over the inhabitants of the industrial megalopolis on the planet Pronox. Backed by powerful Imperial support, with which he squeezes profitable production rates out of his Rhone subjects, Ascaill pro¬vides the Empire with numerous weapons and communication devices."),
	Balgar(4, 4, 2, 1, Allegiance.Neutral, "Balgar dove into politics on the planet Mrane at a young age and came up a winner, pushing a program of scientific development that saved the Suvans' home world from natural disaster. His department prevented the freezing of much of the planet's habitable water when their sun, Luine, cooled unex¬pectedly and — luckily — only temporarily. Leading a fleet of Mrane's militia, Balgar directed the artificial heating of critical hydro-areas on the planet and re-ignited the requisite combustion rate on Luine."),
	Treb_Eyro(3, 3, 2, 0, Allegiance.Imperial, "Treb Eyro is a charismatic politician, decorated extensively for his services as an Imperial star soldier. After returning to his home planet, Etreg, he quickly captured the loyalty of the Ultracks, a highly advanced race living in the labyrinthine cities there. Commissioner Eyro handles the Ultracks' major industry, synthetic drugs; and controls all traffic, legal and illegal, of these sub-stances. He receives considerable financial reward from the Emperor when the traffic flows toward Orlog."),
	Xela_Grebb(3, 3, 2, 0, Allegiance.Neutral, "Xela_Grebb is the beloved king of the Calmas, a toad-like race that dwells in the dank, underground caverns of the planet Suti. He, and the Calmas in general, stay out of Imperial politics, content to pay occasional tribute in exchange for subterranean land rights. King Xfela's son, however, Drakir Grebb, is not so easily appeased, and has run off to join a roving band of Rebels; an action that his father disapproves of strongly, more out of concern for the prince's safety than out of political beliefs."),
	Odel_Hobar(3, 3, 2, 0, Allegiance.Imperial,"Odel Hobar, an Imperial loyalist of long standing, is the king of the Leonids, a feline-like race with a strong military society in the jungles of the planet Heliax. The pride of King Odel's army have taken part in the Empire's greatest campaigns, but his son, Odene Hobar, who looked to be developing into a fine starsoldier, could not take any more of the Empire's methods of conquest. When his father was unmoved by his pleas for the Rebel cause, Odene fled Heliax in disgust."),
	Inzenzia_III(3, 3, 1, 0, Allegiance.Rebel, "Inzenzia_III is the head of a theocratic council that governs the deeply ritualistic Jopers on the planet Barak. Inzenzia and his followers secretly oppose the Empire, who at first discouraged, and now openly ban, many Joperian religious customs."),
	Nam_Nhuk(4, 4, 2, 2, Allegiance.Rebel, "Nam Nhuk once commanded the most feared fleet of Piorad raiders in the galaxy. Finally hunted down and destroyed by the Empire at great expense, Nam Nhuk retreated with a few survivors to the subterranean cities of Ownex, where, under a new guise, he wrested control of the planet from a corrupt Piorad-Imperial puppet and returned the rulership to the resident Piorads. So grateful were the newly freed citizens that they declared Nam Nhuk their leader; an arrangement of the Empire reluctantly agreed to in exchange for a promise from the now-revealed leader that he would never again raid the spaceways."),
	Tensok_Phi(3, 3, 1, 0, Allegiance.Neutral, "Tensok Phi singlehandedly founded the newest Segunden colony on Aras when he was but a young explorer and oversaw its development into a prosperous, urbanized society within his lifetime, a feat of technology that holds him forever dear to his fellow colonists. Unconcerned with interstellar affairs, Phi has not openly opposed the Empire, with which he carries on healthy trade, nor has he discredited the Rebels."),
	Darb_Selesh(5, 4, 2, 0, Allegiance.Neutral, "Darb Selesh, the warrior king of the Cavalkus, is nonetheless a populist, constantly touring his planet of Anell on gilded wing. The Selesh line has ruled the Cavalkus since the dawn of their recorded history, differentiated from their airborne subjects by the sheen of their golden feathers. Although sympathetic to the rumored plight of those on other planets, the king and his people are much more concerned with battling the local races of semi-intelligent creatures."),
	Megda_Sheels(2, 2, 1, 0, Allegiance.Imperial, "Megda Sheels, Queen of Charkhan, rules its native race with an iron will. A strikingly beautiful regent, the amorous legends of her court make up the favorite tales of the Egrix System. For a brief while in her youth, she had but one partner, a Charkhanese no¬ble named Saytar. He could not contain her wandering affection, however. Heart¬broken, he joined the Imperial military service as a junior officer."),
	Shirofune(5, 5, 2, 0, Allegiance.Rebel,"Shirofune, the Grand Prince of the planet Tamset, leads the native race of Kirts. An excellent warrior, Shirofune achieved his local prominence through his prowess in ceremonial combat. Appalled at the Empire's lack of decorum in matters of warfare, Shirofune and his followers will eagerly embrace any honest opposition to the barbaric methods of the Emperor."),
	Yaldor(4, 4, 2,  0, Allegiance.Rebel, "Yaldor was recently named by the Theshians on the planet Rhexia as their leader, despite Imperial disapproval. A highly sophisticated and advanced society, the Theshians have provided the Empire with many of its most skilled technicians and scientists. However, the Theshian work force has been reduced to a trickle recently, as their trained professionals (including Yarro Latac) have declined, in increasing numbers, to accept assignments at Imperial research and development centers. Yaldor is attempting to lead the Theshians to a totally self-sustained state, within the Imperial sphere of influence.•");

	private final int combat;
	private final int endurance;
	private final int leadership;
	private final int spaceLeadership;
//	private final Allegiance startingAllegiance;	// I realize I don't really need this. but I feel more comfortable having it.
	private final String description;
	
	private Allegiance currentAllegiance;
	// 4 Imperials, 4 Rebels, 4 Neutral
	
	private BaseGameSovereign(int combat, int endurance, int leadership, int spaceLeadership, Allegiance startingAllegiance, String description) {
		this.combat = combat;
		this.endurance = endurance;
		this.leadership = leadership;
		this.spaceLeadership = spaceLeadership;
		this.description = description;
//		this.startingAllegiance = startingAllegiance;
		this.currentAllegiance = startingAllegiance;
	}


	public String getName() {
		return this.toString().replace('_', ' ');
	}


	public int getCombat() {
		return combat;
	}


	public int getEndurance() {
		return endurance;
	}


	public int getLeadership() {
		return leadership;
	}


	public String getDescription() {
		return description;
	}
	
	public int getSpaceLeadership() {
		return spaceLeadership;
	}
	
	public Allegiance getCurrentAllegiance() {
		return currentAllegiance;
	}

	public void setCurrentAllegiance(Allegiance currentAllegiance) {
		if (this.currentAllegiance != Allegiance.Neutral) {
			// Can only influence neutral sovereigns.
			throw new IllegalArgumentException("Cannot change the allegiance of " + this.getName() + ", they are already " + this.getCurrentAllegiance() + ".");
		}
		this.currentAllegiance = currentAllegiance;
	}

	public static enum Allegiance {
		Imperial, Rebel, Neutral;
	}
}
