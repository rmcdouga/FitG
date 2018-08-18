package com.rogers.rmcdouga.fitg.basegame;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public enum ActionEnum implements Action {
	CARD_68(68, 
			"RH",	"Creature attacks one Mission Group. If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If  no creature is named, ignore Event.", 
			"B",	"Locals raid Enemy forces.  Non-Phasing Player must eliminate one of his military units in the Environ, or, if he controls a PDB that is Up on the planet, place it Down (non-Phasing Player's choice).", 
			"P",	"Locals shelter character from Enemy.  No Enemy searches may be conducted in the Environ for the remainder of the Mission Phase."),
	CARD_69(69, 
			"D ",	"Creature attacks one Mission Group. If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If  no creature is named, the Mission Group is attacked by a sentry robot (see Case 13.46).", 
			"C S ",	"Weather disturbances hamper Enemy operations.  The non-Phasing Player may conduct no searches in theis Environ this Mission Phase.", 
			"P I ",	"Coup Mission aborted; Characters detected. Roll the die. 1-3, no effect; 4, shift Loyalty marker one space  in non-Phasing Player's favor; 5 or 6, shift Loyalty and entire Mission Group captured."),
	CARD_70(70, 
			"P I ",	"Creature attacks one Mission Group. If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If  no creature is named, the Mission Group is attacked by two sentry robots (see Case 13.46).", 
			"S C ",	"Characters detected.", 
			"P ",	"Locals raid Enemy forces.  Non-Phasing Player must eliminate one of his military units in the Environ, or, if he controls a PDB that is Up on the planet, place it Down (non-Phasing Player's choice)."),
	CARD_71(71, 
			"C J ",	"Irate locals attack one Mission Group.  Refer to the Irate Locals Chart to determine the mob's attributes.", 
			"B T ",	"Would-be rebels chicken out.  Start Rebellion mission aborted. Characters detected.", 
			"P G ",	"Weather disturbances hamper Enemy oprtations.  The non-Phasing Player may conduct no searches in this Environ this Mission Phase."),
	CARD_72(72, 
			"P G ",	"Irate locals attack one Mission Group.  Refer to the Irate Locals Chart to determine the mob's attributes.", 
			"C ",	"Controversial political matter argued.  If a Diplomacy mission mission is being prefoemd, and no character in the Mission Group has a Diplomacy Rating of two or more, the mission is aborted.", 
			"D H ",	"The Imperial Player may commit an Atrocity on this planet (see Section 35.0).  Galactic Game only, otherwise ignore event."),
	CARD_73(73, 
			"D ",	"Would-be rebels chicken out.  Start Rebellion mission aborted.  Characters detected..", 
			"G I ",	"Weather disturbances hamper Enemy operations.  The non-Phasing Player may conduct no searches in this Environ this Mission Phase.", 
			"R S ",	"Wrong soldier contacted.  If a Subvert Troops mission is being performed, it is aborted.  Mission Group found by Enemy squand.  If an Enemy leader is stacked with the Enemy military units, the entire Mission Group is captured."),
	CARD_74(74, 
			"P ",	"Enemy Agent reveals mission.  The non-Phasing Player randomly chooses one mission that the Phasing Player is currently performing in the Environ; that mission is aborted.  Characters detected.", 
			"B H ",	"Irate locals attack one Mission Group.  Refer to the Irate Locals Chart to determine the mob's attributes.", 
			"C ",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature us named, ignore event."),
	CARD_75(75, 
			"F G ",	"Characters detected.", 
			"D ",	"Populace goes wild.  If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46).", 
			"A H ",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by a sentry robot (see Case 13.46)."),
	CARD_76(76, 
			"A ",	"Locals shelter character from Enemy.  No Enemy searches may be conducted in the Environ for the remainder of the Mission Phase.", 
			"B E ",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by a sentry robot (see Case 13.46).", 
			"F B ",	"Coup Mission aborted; Characters detected. Roll the die. 1-4, no effect; 5, shift Loyalty marker one space  in non-Phasing Player's favor; 6, shift Loyalty and entire Mission Group captured."),
	CARD_77(77, 
			"C ",	"Weather disturbances hamper Enemy operations.  The non-Phasing Player may conduct no searches in this Environ this Mission Phase.", 
			"S E ",	"Coup Mission aborted; Characters detected. Roll the die. 1, 2, no effect; 3-5, shift Loyalty marker one space  in non-Phasing Player's favor; 6, shift Loyalty and entire Mission Group captured, one character killed (Phasing Player's choice).", 
			"F I ",	"Local connections expedite missions.  All misssions receive one extra bonus draw in the Environ this Mission Phase."),
	CARD_78(78, 
			"C S ",	"Local connections expedite missions.  All missions recieve one extra bonus draw in this Environ this Mission Phase.", 
			"D G ",	"Coup mission aborted; characters detected. Roll the die. 1-3, no effect; 4,5, shift Loyalty marker one space in theNon-Phasing Player's favor; 6, shift Loyalty marker and entire Mission Group killed.", 
			"F P ",	"Characters are delayed by rumors.  No bonus draws may be taken in this Environ this Mission Phase."),
	CARD_79(79, 
			"S H ",	"Local connections expedite missions.  All missions recieve one extra bonus draw in this Environ this Mission Phase.", 
			"G P ",	"Creature attacks one Mission Group.  If a creatureis named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by a sentry robot (see Case 13.46).", 
			"B ",	"Civil war breaks out.  The populace blames the Phasing Player.  If the Loyalty marker is currently at 1 or 2 in the Player's favor, move the marker to Neutral.  If the Planet is in rebellion or not in the Player's favor, ignore event."),
	CARD_80(80, 
			"R E ",	"Local connections expedite missions.  All missions recieve one extra bonus draw in this Environ this Mission Phase.", 
			"F P ",	"Confusing local protocol aborts a Diplomacy mission. If one is being performed in the Environ, shift the Loyalty marker one space in the Non-Phasing Player's favor.", 
			"C S ",	"One Mission Group stumbles on Enemy squad.  If the Enemy Player controls the planet and has military units in the environ, character combat is in-itiated using the Squad Table to determine the Enemy's strength."),
	CARD_81(81, 
			"F I ",	"Wrong soldier contacted.  If a Subvert Troops mission is being performed, it is aborted.  Mission Group found by Enemy squad.  If an Enemy leader is stacked with the Enemy military units, the entire Mission Group is captured.", 
			"C T ",	"Accidents will happen, especially in an unfamiliar Environ. Any one character performing a mission in the Environ must receive a wound.", 
			"E ",	"Controversial political matters argued.  If a Diplomacy mission is being performed, and no character in the Mission Group has a Diplomacy rating of two or more, the mission is aborted."),
	CARD_82(82, 
			"G ",	"Civil war breaks out. The populace blames the Phasing Player.  If the Loyalty marker is currently at 1 or 2 in the Player's favor, move the marker to Neutral.  If the planet is in rebellion or not in the Player's favor, ignore event.", 
			"R S ",	"Weather disturbances hamper Enemy operations.  The non-Phasing Player may conduct no searches in this Environ this Mission Phase.", 
			"D ",	"Populace goes wild! If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46)."),
	CARD_83(83, 
			"D S ",	"Locals raid Enemy forces.  Non-Phasing Player must eliminate one of his military units in the Environ, or if he controls a PDB that is Up on the planet, place it Down (Non-Phasing Players choice).", 
			"R Q ",	"Characters detected.  Enemy player may conduct search for one Mission Group in the En-viron.", 
			"F ",	"Would-be rebels chicken out.  Start Rebellion mission aborted.  Characters detected."),
	CARD_84(84, 
			"F P ",	"Locals raid Enemy forces.  Non-Phasing Player must eliminate one of his military units in the Environ, or, if he controls a PDB that is Up on the planet, place it down (non-Phasing Players choice).", 
			"R ",	"Creature attacks one Mission Group. If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by two sentry robots (see Case 13.46).", 
			"S H ",	"Characters detected."),
	CARD_85(85, 
			"G I ",	"Populace goes wild! If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46).", 
			"R ",	"Characters detected.  Enemy player may conduct search for one Mission Group in the Environ.", 
			"S E ",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by two sentry robots."),
	CARD_86(86, 
			"F I ",	"Characters detected.  Enemy player may conduct search for one Mission Group in the Environ.", 
			"A J ",	"Characters are delayed by rumors.  No bonus draws may be taken in this Environ this Mission Phase.", 
			"C T ",	"Characters detected.  Enemy player may conduct search for one Mission Group in the Environ."),
	CARD_87(87, 
			"S C ",	"Disagreeable food substance hampers characters. No bonus draws may be taken in this En-viron this Mission Phase.", 
			"R ",	"Coup mission aborted; Characters detected. Roll the die. 1,2, no effect; 3,4, shift Loyalty marker one space in Non-Phasing Player's favor; 5,6, shift Loyalty and Mission Group found by Enemy squad or characters (Non-Phasing Player's choice).", 
			"F G ",	"Characters detected.  Enemy Player may conduct search for one Mission Group in the Environ."),
	CARD_88(88, 
			"T ",	"Characters are delayed by rumors.  No bonus draws may be taken in this Environ this Mission Phase.", 
			"F ",	"One Mission Group stumbles on Enemy squad.  If the Enemy Player controls the planet and has military units in the Environ, character combat is initiated using the Squad Table to determine the Enemy's strength.", 
			"B J ",	"Weather disturbances hamper Enemy operations.  The Non-Phasing Player may conduct no searches in this Environ this Mission Phase."),
	CARD_89(89, 
			"C E ",	"The Imperial Player may commit an Atrocity on this planet. (see Section 35.0). (non-Galactic Game, ignore event.).", 
			"P ",	"Civil war breaks out.  The populace blames the Phasing Player.  If the Loyalty marker is currently at 1 or 2 in the Player's favor, move the marker to Neutral. If the Planet is in Rebellion or not in the Player's favor, ignore event.", 
			"R ",	"Local connections expedite missions. All missions recieve one extra bonus draw in this Environ this Mission Phase."),
	CARD_90(90, 
			"B ",	"Confusing local protocol aborts a Diplomacy mission.  If one is being performed in the Environ, shift the Loyalty marker one space in the Non-Phasing Player's favor.", 
			"C H ",	"Enemy agent reveals mission.  The Non-Phasing Player randomly chooses one mission that the Phasing Player is currently performing in the Environ; that mission is aborted. Characters detected.", 
			"G I ",	"Confusing local protocol aborts Diplomacy mission. If one is being performed in the Environ, shift the Loyalty marker one space in the Non-Phasing Player's favor."),
	CARD_91(91, 
			"B E ",	"Controversial political matters argued.  If a Diplomacy mission is being performed, and no character in the Mission Group has a Diplomacy Rating of two or more, the mission is aborted.", 
			"P ",	"Local connections expedite missions. All missions recieve one extra bonus draw in this Environ this Mission Phase.", 
			"R Q ",	"Irate locals attack one Mission Group.  Refer to the Irate Locals Chart to determine the mob's attributes."),
	CARD_92(92, 
			"S G ",	"Coup mission aborted; characters detected. Roll the die.  1-3, no effect; 4,5, shift Loyalty marker one space in the Non-Phasing Player's favor; 6, shift Loyalty marker and entire Mission Group killed.", 
			"F I ",	"Local connections expedite missions. All missions recieve one extra bonus draw in this Environ this Mission Phase.", 
			"R T ",	"It's the off-season for the local creatures.  Ignore all \"Creature Attacks\" events in this environ this Mission Phase."),
	CARD_93(93, 
			"R B ",	"Coup mission aborted; Characters detected.  Roll the die. 1,2, no effect; 3,4, shift Loyalty marker one space in Non-Phasing Player's favor; 5,6, shift Loyalty and entire Mission Group captured.", 
			"F ",	"The Imperial Player may commit an Atrocity on this planet.  (non-Galactic Game, ignore event.).", 
			"S C ",	"Confusing local protocol aborts a Diplomacy mission. If one is being performed in the Environ, shift the Loyalty marker one space in the Non-Phasing Player's favor."),
	CARD_94(94, 
			"D Q ",	"Coup mission aborted; Characters detected.  Roll the die. 1-3, shift Loyalty marker one space in Non-Phasing Player's favor; 4-6, shift Loyalty marker and Mission Group found by Enemy squad or characters (Non-Phasing Player's choice).", 
			"F G ",	"Disagreeable food substance hampers characters.  No bonus draws may be taken in this Environ this Mission Phase.", 
			"B E ",	"Populace goes wild! If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46)."),
	CARD_95(95, 
			"R H ",	"Characters detected. Enemy player may conduct search for one Mission Group in Environ.", 
			"F I ",	"Populace goes wild! If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46).", 
			"B ",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, the Mission Group is attacked by two sentry robots (see Case 13.46)."),
	CARD_96(96, 
			"A T",	"Characters detected. Enemy Player may conduct search for one Mission Group in Environ.", 
			"D E",	"Wrong soldier contacted.  If a Subvert Troops mission is being performed, it is aborted.  Mission Group found by Enemy squad.  If an Enemy leader is stacked with the Enemy military units, entire Mission Group is captured.", 
			"P",	"Disagreeable food substance hampers characters.  No bonus draws may be taken in this Environ this Mission Phase."),
	CARD_97(97, 
			"F",	"Populace goes wild! If the planet is placed into rebellion during this Mission Phase, the Rebel Player receives double the resource value in this Environ (see Case 15.46).", 
			"S H",	"Creature attacks one Mission Group.  If a creature is named in the Environ, look it up in the Galactic Guide to determine its attributes.  If no creature is named, ignore event.", 
			"D",	"Enemy agent reveals mission.  The Non-Phasing Player randomly chooses one mission that the Phasing Player is currently performing in the Environ; that mission is aborted.  Characters detected."),
	
	;

	private final ActionCard actionCard;
	private final Map<EnvironType, Result> results;
	protected static final Set<ActionEnum> ALL_ACTIONS = EnumSet.allOf(ActionEnum.class);
	
	private ActionEnum(int cardNumber, String urbanMissions, String urbanResult, String specialMissions, String specialResult, String wildMissions, String wildResult) {
		this.actionCard = new ActionCard(cardNumber);
		this.results = new EnumMap<>(EnvironType.class);
		this.results.put(EnvironType.URBAN, new Result(urbanMissions, urbanResult));
		this.results.put(EnvironType.SPECIAL, new Result(specialMissions, specialResult));
		this.results.put(EnvironType.WILD, new Result(wildMissions, wildResult));
		if (this.results.size() != EnvironType.values().length) {
			throw new IllegalStateException("Number of EnvironTypes (" + EnvironType.values().length + ") is different than the size of the results (" + this.results.size() + ")!");
		}
	}


	@Override
	public int cardNumber() {
		return this.actionCard.cardNumber();
	}

	@Override
	public boolean isSuccessful(Mission mission, EnvironType environType) {
		return this.results.get(environType).missions().contains(mission);
	}


	@Override
	public MarkdownString getResultDescription(EnvironType environType) {
		return this.results.get(environType).resultDescription();
	}
	
	private static class ActionCard extends CardAbstractClass {
		protected ActionCard(int cardNumber) {
			super(cardNumber);
		}
	}

	public static class Result {
		private final Set<Mission> missions;
		private final MarkdownString resultDescription;
		protected Result(String missions, String result) {
			super();
			this.missions = Mission.getDefaultFactory().getMissionsFromMnemonics(missions);
			this.resultDescription = new MarkdownString(result);
		}
		/**
		 * @return the missions
		 */
		public Set<Mission> missions() {
			return missions;
		}
		/**
		 * @return the result
		 */
		public MarkdownString resultDescription() {
			return resultDescription;
		}
	}

}

