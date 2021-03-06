package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static com.rogers.rmcdouga.fitg.basegame.tables.Squad.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.tables.Squad;

public enum BaseGameRaceType implements RaceType {
	// Star-faring Races
	Kayn("Kayn", ()->Optional.of(Mimulus), 
			"""
			The Kayns are a humanoid race with dog-like facial features. Steeped in tradition, \
			a Kayn will remain totally loyal to whomever or whatever he has been brought up to serve. \
			Their society is very military in structure and has provided the Empire with many of its best soldiers. \
			Not all Kayns have been raised under the wing of the Empire, however, and many become \
			freelance mercenaries or bodyguards for any person or cause that in-spires their devotion.\
			""",
			Map.of(Wild, SQUAD_7_6, Subterranian, SQUAD_6_4)
			),
	Piorad("Piorad", ()->Optional.of(Ayod), 
			"""
			Organized in a hierarchy of tribes, the Piorads have two seemingly opposing racial traits; \
			they originated and still live for the most part in huge, artificial, underground halls, \
			and large segments of the population roam the spaceways, raiding cargo transports and \
			wandering from opportunity to opportunity. They are a wealthy race, loathe to share their riches \
			with other races, yielding just enough to the Empire to avoid retaliation. \
			Piorad star-wanderers are among the galaxy's best spacecraft handlers and fighters.\
			""",
			Map.of(Urban, SQUAD_4_4, Subterranian, SQUAD_6_6)
			),
	Rhone("Rhone", ()->Optional.empty(), 
			"""
			The race from which the founders of the Empire hailed, the Rhones are still the ruling race in the galaxy \
			and the most numerous by far (it was not always thus). With records of mass colonization dating back far \
			before the founding of the current Imperial system, the Rhones' place of origin is not known; although \
			many suggest that they came from a distant corner of the galaxy or another one altogether, \
			long before the Interstellar Concordance was formed.\
			""",
			Map.of(Urban, SQUAD_5_4, Wild, SQUAD_4_4, Subterranian, SQUAD_4_4)
			),
	Saurian("Saurian", ()->Optional.of(Unarpha), 
			"""
			A reptilian race with humanoid bodies, the Saurians adapt easily to most planet conditions. \
			Before the Empire increased the oppressive nature of its rule, the Saurians governed themselves \
			with a strong parliamentary system that kept good order among their many colonized planets. \
			Strong, stealthy, and intelligent, a Saurian soldier is a welcome addition to any army.\
			""",
			Map.of(Urban, SQUAD_5_6, Wild, SQUAD_5_4, Subterranian, SQUAD_5_2, Liquid, SQUAD_5_2)
			),
	Segunden("Segunden", ()->Optional.of(Bajukai), 
			"""
			The Segundens are a dark-skinned humanoid race possessing great intelligence. \
			Never very interested in quick expansion and conquests, the Segundens have limited their influence \
			to a few planets which they have developed to a technological level far beyond that of most other \
			planets in the galaxy. Although they pay all necessary tributes to the Empire, they have great \
			personal integrity and pride, having made the decision to meet the Empire's demands only after \
			extensive calculations showed that, although they could defeat the Empire in open war, the cost \
			in resources and lives would be even greater than that of peaceful submission.\
			""",
			Map.of(Urban, SQUAD_6_4, Liquid, SQUAD_5_2)
			),
	Suvan("Suvan", ()->Optional.of(Mrane), 
			"""
			The Suvans are an amphibious race that thrive in mineral-rich water. \
			They have developed sophisticated techniques for extracting everything imaginable from their life-giving liquid, \
			and, when not wandering through the oceans of the planets they inhabit, they live on wide pavilions \
			constructed just beneath the water's surface. Somewhat adaptable to breathing outside of their natural habitat, \
			many Suvans live in dry cities and towns near the water's edge, although they are much weaker physically out of water.\
			""",
			Map.of(Urban, SQUAD_6_2, Liquid, SQUAD_7_4)
			),
	Xanthon("Xanthon", ()->Optional.of(Xan), 
			"""
			The latest addition to the interstellar community are the Xanthons, a race possessing incredible \
			strength and viciousness in hot environments. In temperate areas, Xanthons are weak and docile; \
			in cold areas, they cannot survive. Technologically behind the other star-faring races, the Xanthons \
			have little of worth to surrender to the Empire and thus are little involved in galactic politics.\
			""",
			Map.of(Fire, SQUAD_8_6)
			),
	Yester("Yester", ()->Optional.of(Cieson), 
			"""
			This bird-like race thrives in the clouds and wind currents of any hydrogen-rich atmosphere. \
			With their high intuitive intelligence and curious nature, they learned the secrets of spaceflight \
			long ago from other races and have colonized the skies of many planets in their beautiful \
			stellar-sail spacecraft.\
			""",
			Map.of(Urban, SQUAD_6_2, Air, SQUAD_6_4)
			),
	
	// Non-starfaring races
	Anon("Anon", ()->Optional.of(Jura), Air, SQUAD_4_2_HTH),
	Ardorats("Ardorats", ()->Optional.of(Liatris), Wild, SQUAD_4_4),
	Bork("Borks", ()->Optional.of(Bajukai), Wild, SQUAD_8_2),
	Calma("Calma", ()->Optional.of(Suti), Subterranian, SQUAD_5_4), 
	Charkhans("Charkhan", ()->Optional.of(BaseGamePlanet.Charkhan), Wild, SQUAD_5_4),
	Cavalkus("Cavalkus", ()->Optional.of(Annell), Air, SQUAD_4_3_HTH),
	Deaxins("Deaxins", ()->Optional.of(Midest), Wild, SQUAD_5_5_HTH),
	Illias("Illias", ()->Optional.of(BaseGamePlanet.Cercis), Wild, SQUAD_4_4), 
	Henone("Henone", ()->Optional.of(BaseGamePlanet.Horon), Liquid, SQUAD_6_6), 
	Kirts("Kirts", ()->Optional.of(Tamset), Wild, SQUAD_5_4),
	Jopers("Jopers", ()->Optional.of(Barak), Urban, SQUAD_6_2),
	Leonid("Leonid", ()->Optional.of(BaseGamePlanet.Heliax), Wild, SQUAD_6_4),
	Moghas("Moghas", ()->Optional.of(Suti), Wild, SQUAD_7_3_HTH),
	Mowev("Mowev", ()->Optional.of(BaseGamePlanet.Chim), Wild, SQUAD_4_4_HTH), 
	Ornotins("Ornotins", ()->Optional.of(Xan), Urban, SQUAD_6_4),
	Phans("Phans", ()->Optional.of(Heliax), Liquid, SQUAD_4_4),
	Rylians("Rylians", ()->Optional.of(Akubera), Subterranian, SQUAD_8_2_HTH),
	Susperans("Susperans", ()->Optional.of(Solvia), Urban, SQUAD_4_4),
	Theshian("Theshian", ()->Optional.of(BaseGamePlanet.Rhexia), Wild, SQUAD_4_2), 
	Thoks("Thoks", ()->Optional.of(Solvia), Wild, SQUAD_6_5_HTH),
	Ultraks("Ultraks", ()->Optional.of(Etreg), Urban, SQUAD_4_4),
	Urgaks("Urgaks", ()->Optional.of(Fliad), Wild, SQUAD_6_4),
	Ursi("Ursi", ()->Optional.of(BaseGamePlanet.Lysenda), Wild, SQUAD_7_6_HTH), 
	;
	
	
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
//	This bird-like race thrives in the clouds and wind currents of any hydrogen-rich at-mosphere. With their high intuitive intelli¬gence and curious nature, they learned the secrets of spaceflight long ago from other races and have colonized the skies of many planets in their beautiful stellar-sail spacecraft.

	
	
	private final String name;
	private final boolean isStarFaring;
	private final Supplier<Optional<BaseGamePlanet>> homePlanet;
	private final Optional<String> description;
	private final Optional<BaseGameEnvironType> environType;
	private final Map<BaseGameEnvironType, Squad> squads;

	// Constructor for non-starfaring races
	private BaseGameRaceType(String name, Supplier<Optional<BaseGamePlanet>> homePlanet, BaseGameEnvironType environType, Squad squad) {
		this.name = name;
		this.isStarFaring = false;
		this.homePlanet = homePlanet;
		this.description = Optional.empty();
		this.environType = Optional.of(environType);
		this.squads = Map.of(environType, squad);	// Build in Map is fine because it has special implementation.
	}

	// Constructor for starfaring races
	private BaseGameRaceType(String name, Supplier<Optional<BaseGamePlanet>> homePlanet, String description, Map<BaseGameEnvironType, Squad> squads) {
		this.name = name;
		this.isStarFaring = true;
		this.homePlanet = homePlanet;
		this.description = Optional.of(description);
		this.environType = Optional.empty();
		this.squads = new EnumMap<>(squads);	// Use an enumMap because it is efficient.
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return the isStarFaring
	 */
	@Override
	public boolean isStarFaring() {
		return isStarFaring;
	}

	/**
	 * @return the homePlanet
	 */
	@Override
	public Optional<BaseGamePlanet> getHomePlanet() {
		return homePlanet.get();
	}

	/**
	 * @return the description
	 */
	@Override
	public Optional<String> getDescription() {
		return description;
	}

	/**
	 * @return the environ type
	 */
	@Override
	public Optional<BaseGameEnvironType> getEnvironType() {
		return environType;
	}
	
	/**
	 * @param environ
	 * @return the Squad type associated with this race and environ
	 */
	@Override
	public Optional<Squad> getSquad(BaseGameEnvironType environ) {
		return Optional.ofNullable(squads.get(environ));
	}
}
