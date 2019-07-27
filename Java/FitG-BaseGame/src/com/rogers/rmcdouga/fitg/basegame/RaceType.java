package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.map.Planet;

// TODO: Planet.Mimulus is not every races homeworld.  That field needs to be updated when the planets enum is populated.
public enum RaceType {
	Kayn("Kayn", true, Planet.Mimulus, "The Kayns are a humanoid race with dog-like facial features. Steeped in tradition, a Kayn will remain totally loyal to whomever or whatever he has been brought up to serve. Their society is very military in structure and has provided the Empire with many of its best soldiers. Not all Kayns have been raised under the wing of the Empire, however, and many become free-lance mercenaries or bodyguards for any person or cause that in-spires their devotion."),
	Piorad("Piorad", true, Planet.Mimulus, "Organized in a hierarchy of tribes, the Piorads have two seemingly opposing racial traits; they originated and still live for the most part in huge, artificial, underground halls, and large segments of the population roam the spaceways, raiding cargo transports and wandering from opportunity to opportunity. They are a wealthy race, loathe to share their riches with other races, yielding just enough to the Empire to avoid retaliation. Piorad star-wanderers are among the galaxy's best spacecraft handlers and fighters."),
	Rhone("Rhone", true, Planet.Mimulus, "The race from which the founders of the Empire hailed, the Rhones are still the ruling race in the galaxy and the most numerous by far (it was not always thus). With records of mass colonization dating back far before the founding of the current Imperial system, the Rhones' place of origin is not known; although many suggest that they came from a distant corner of the galaxy or another one altogether, long before the Interstellar Concordance was formed."),
	Saurian("Saurian", true, Planet.Mimulus, "A reptilian race with humanoid bodies, the Saurians adapt easily to most planet con-ditions. Before the Empire increased the op-pressive nature of its rule, the Saurians gov-erned themselves with a strong parliamentary system that kept good order among their many colonized planets. Strong, stealthy, and intelligent, a Saurian soldier is a welcome addition to any army."),
	Segunden("Segunden", true, Planet.Mimulus, "The Segundens are a dark-skinned hu-manoid race possessing great intelligence. Never very interested in quick expansion and conquests, the Segundens have limited their influence to a few planets which they have developed to a technological level far beyond that of most other planets in the galaxy. Although they pay all necessary tributes to the Empire, they have great personal integrity and pride, having made the decision to meet the Empire's demands only after extensive calculations showed that, although they could defeat the Empire in open war, the cost in resources and lives would be even greater than that of peaceful submission."),
	Suvan("Suvan", true, Planet.Mimulus, "The Suvans are an amphibious race that thrive in mineral-rich water. They have developed sophisticated techniques for extracting everything imaginable from their life-giving liquid, and, when not wandering through the oceans of the planets they inhabit, they live on wide pavilions constructed just beneath the water's surface. Somewhat adaptable to breathing outside of their natural habitat, many Suvans live in dry cities and towns near the water's edge, although they are much weaker physically out of water."),
	Xanthon("Xanthon", true, Planet.Mimulus, "The latest addition to the interstellar community are the Xanthons, a race possessing incredible strength and viciousness in hot environments. In temperate areas, Xanthons are weak and docile; in cold areas, they cannot survive. Technologically behind the other star-faring races, the Xanthons have little of worth to surrender to the Empire and thus are little involved in galactic politics."),
	Yester("Yester", true, Planet.Mimulus, "This bird-like race thrives in the clouds and wind currents of any hydrogen-rich atmosphere. With their high intuitive intelli¬gence and curious nature, they learned the secrets of spaceflight long ago from other races and have colonized the skies of many planets in their beautiful stellar-sail space¬raft.");
	
//	Star-Faring Races
//	The eight important races in the galaxy are those that have developed the technology to travel through interplanetary and inter¬stellar space, enabling them to settle worlds other than that on which they evolved. The following entries, describing the general characteristics of each race and providing the players with a list of the planets occupied by each race, are especially useful when imple¬menting the Domino Effect on a planet occu¬pied by one of these races. In each list, the race's home planet appears first, followed by the other planets that the race has colonized.
	
//	Kayns. Home Planet: Mimulus (I 1 1); Kalgar (121), Etreg (212), and Niconi (551).
//	The Kayns are a humanoid race with dog-like facial features. Steeped in tradition, a Kayn will remain totally loyal to whomever or whatever he has been brought up to serve. Their society is very military in structure and has provided the Empire with many of its best soldiers. Not all Kayns have been raised under the wing of the Empire, however, and many become free-lance mercenaries or bodyguards for any person or cause that in-spires their devotion.
	
//	Piorads. Home Planet: Ayod (451); Ownex (141), Lysenda (232), Solvia (432), Cercis (433), and Tartio (442).
//	Organized in a hierarchy of tribes, the Piorads have two seemingly opposing racial traits; they originated and still live for the most part in huge, artificial, underground halls, and large segments of the population roam the spaceways, raiding cargo transports and wandering from opportunity to opportunity. They are a wealthy race, loathe to share their riches with other races, yielding just enough to the Empire to avoid retaliation. Piorad star-wanderers are among the galaxy's best spacecraft handlers and fighters.
	
//	Rhones. No Home Planet; Margro (112), Adare (142), Jura (151), Thomas (152), Lio-max (161), Orlog (162), Pronox (231), Or¬ning (241), Chim (311), Tsipa (323), A kubera (342), Troliso (411), Lonica (421), Adrax (523), and Trov (542).
//  The race from which the founders of the Empire hailed, the Rhones are still the ruling race in the galaxy and the most numerous by far (it was not always thus). With records of mass colonization dating back far before the founding of the current Imperial system, the Rhones' place of origin is not known; al¬though many suggest that they came from a distant corner of the galaxy or another one altogether, long before the Interstellar Con¬cordance was formed.
	
//	Saurians. Home Planet: Unarpha (321); Kal-gar (121), Mitrith (143), Jura (151), kid (163), Quibron (221), Squamot (331), and Kelta (352).
//	A reptilian race with humanoid bodies, the Saurians adapt easily to most planet con-ditions. Before the Empire increased the op-pressive nature of its rule, the Saurians gov-erned themselves with a strong parliamentary system that kept good order among their many colonized planets. Strong, stealthy, and intelligent, a Saurian soldier is a welcome addition to any army.
	
//	Segundens. Home Planet: Bajukai (122); Tiglyf (131), Lysenda (232), Lonica (421), and Aras (521).
//	The Segundens are a dark-skinned hu-manoid race possessing great intelligence. Never very interested in quick expansion and conquests, the Segundens have limited their influence to a few planets which they have developed to a technological level far beyond that of most other planets in the galaxy. Al-though they pay all necessary tributes to the Empire, they have great personal integrity and pride, having made the decision to meet the Empire's demands only after extensive calculations showed that, although they could defeat the Empire in open war, the cost in resources and lives would be even greater than that of peaceful submission.
	
//	Suvans. Home Planet: Mrane (351); Fliad (113), Orning (241), Midest (341), and Aku-bera (342).
//	The Suvans are an amphibious race that thrive in mineral-rich water. They have de-veloped sophisticated techniques for extract-ing everything imaginable from their life-giving liquid, and, when not wandering through the oceans of the planets they inhab¬it, they live on wide pavilions constructed just beneath the water's surface. Somewhat adaptable to breathing outside of their nat¬ural habitat, many Suvans live in dry cities and towns near the water's edge, although they are much weaker physically out of water.
	
//	Xanthons. Home Planet: Xan (513); Mitrith (143), Capilax (522), and Scythia (531).
//	The latest addition to the interstellar community are the Xanthons, a race possess-ing incredible strength and viciousness in hot environments. In temperate areas, Xanthons are weak and docile; in cold areas, they can-not survive. Technologically behind the other star-faring races, the Xanthons have little of worth to surrender to the Empire and thus are little involved in galactic politics.
	
//	Yesters. Home Planet: Cieson (211); Magro (112), Angoff (222), Charkhan (223), Pronox (231), and Tamset (312).
//	This bird-like race thrives in the clouds and wind currents of any hydrogen-rich at-mosphere. With their high intuitive intelli¬gence and curious nature, they learned the secrets of spaceflight long ago from other races and have colonized the skies of many planets in their beautiful stellar-sail space¬craft .

	
	
	private final String name;
	private final boolean isStarFaring;
	private final Planet homePlanet;
	private final String description;
	
	private RaceType(String name, boolean isStarFaring, Planet homePlanet, String description) {
		this.name = name;
		this.isStarFaring = isStarFaring;
		this.homePlanet = homePlanet;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the isStarFaring
	 */
	public boolean isStarFaring() {
		return isStarFaring;
	}

	/**
	 * @return the homePlanet
	 */
	public Planet getHomePlanet() {
		return homePlanet;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
