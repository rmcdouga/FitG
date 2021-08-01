package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameCreature.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameCapitalType.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanetaryControlType.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType.*;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;


public enum BaseGamePlanet implements Planet {
	// Tardyn,11
	Mimulus(Tardyn, None, Kayn, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.wild(4)
						   .resources(4)
						   .races(Kayn)
						   .creature(Prox)
						   .build()),
	//	Mimulus,111,Kayans,2,0,,,
	//		Wild,4,4,TRUE,,Kayns,Prox,,,,,,,,,,,,,,,,
	Magro(Tardyn, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .starResources()
			   .races(Rhone)
			   .build(),
		   BaseGameEnviron.air(5)
			   .resources(7)
			   .starResources()
			   .races(Yester)
			   .creature(Glane)
			   .build()),
	//	Magro,112,,2,1,,,
	// 		Urban,3,,,6,Rhones,,,
	//		Air,5,,,7,,Glane,,,,,,,,
	Fliad(Tardyn, None, Patriotic, Neutral, ImperialControlled, true,
			BaseGameEnviron.liquid(4)
			   .resources(4)
			   .starResources()
			   .races(Suvan)
			   .creature(Dindin)
			   .coupRating(3)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .races(Urgaks) // Not a star-faring race.
			   .resources(4)
			   .build()),
	//	Fliad,113,,2,1,,,
	//		Water,4,,,4,,,,
	//		Wild,4,,,4,,,,,,,,,,

	// Environ of(EnvironType type, int size, int resources, boolean starResources, int coupRating, List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign)

	// Uracas,12
	Kalgar(Uracus, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.subterranian(6)
			   .resources(7)
			   .starResources()
			   .races(Kayn, Saurian)
			   .creature(Crunge)
			   .build()),
	Bajukai(Uracus, None, Segunden, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(8)
			   .starResources()
			   .races(Segunden)
			   .build(),
		   BaseGameEnviron.liquid(3)
			   .resources(7)
			   .starResources()
			   .races(Segunden)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .resources(6)
			   .races(Bork)	// Not a star faring race
			   .creature(Ym$Barror)
			   .build()),
	
	// Kalgar,121,,2,1,,,
	//		Subterranian,6,7,TRUE,,"Kayns, Saurians",Crunge,,,,,,,,,,,,,,,,
	// Bajukai,122,,1,0,,,
	//   Urban,3,8,TRUE,,Segundians ,,,
	//   Water,3,7,TRUE,,Segundians,,,
	//   Wild,4,6,,,Borks,Ymbarrors

	//	Name,Id,Home Race,S,A,Capital,Secret,
	//     Environ1,Size,Resources,StarResources,Coup Rating,Races,Creature,Sovereign,
	//     Environ2,Size,Resources,StarResources,Coup Rating,Race,Creature,Sovereign,
	//     Environ3,Size,Resources,StarResources,Coup Rating,Race,Creature
	

	// Zamorax,13
	Tiglyf(Zamorax, None, Patriotic, Dissent, ImperialControlled, true,
			BaseGameEnviron.urban(5)
			   .resources(10)
			   .starResources()
			   .races(Segunden)
			   .creature(Vorozion)
			   .build()),
	// Tiglyf,131,,2,-1,,TRUE,Urban,5,10,TRUE,,Segundians,Vorozion,,,,,,,,,,,,,,,,

	// Atriard,14
	Ownex(Atriard, None, Neutral, Unrest, RebelControlled,
			BaseGameEnviron.subterranian(5)
			   .resources(5)
			   .starResources()
			   .races(Piorad)
			   .creature(Siromuse)
			   .sovereign(Nam_Nhuk)
			   .build()),
	Adare(Atriard, None, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(Rhone)
			   .creature(Zernipak)
			   .build(),
		   BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(Rhone)
			   .build()),
	Mitrith(Atriard, None, Patriotic, Dissent, ImperialControlled, true,
			BaseGameEnviron.fire(2)
			   .resources(2)
			   .races(Xanthon)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .resources(6)
			   .starResources()
			   .races(Saurian)
			   .creature(Zop)
			   .build()),
	// Ownex,141,,0,-3,,,
	// 		Subteranian,5,5,TRUE,,Piorads,Stromuse,Nam Nhuk (R),,,,,,,,,,,,,,,
	// Adare,142,,1,0,,,
	// 		Urban,4,7,TRUE,,Rhones,Zernipaks,,
	//		Wild,5,7,TRUE,,Rhones,,,,,,,,,
	// Mitrith,143,,,,,,
	//		Fire,2,2,,,Xanthons,,,
	//		Wild,4,6,TRUE,,Saurians,Zop,,,,,,,,

	// Bex,15
	Jura(Bex, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(Saurian, Rhone)
			   .build(),
		   BaseGameEnviron.air(3)
			   .resources(1)
			   .races(Anon)	// Not a star-faring race
			   .build()),
	Diomas(Bex, Provincial, Patriotic, Patriotic, ImperialControlled,
			BaseGameEnviron.urban(5)
			   .resources(7)
			   .starResources()
			   .races(Rhone)
			   .creature(Muggers)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(4)
			   .races(Rhone)
			   .creature(Chlorofix)
			   .build()),
	// Jura,151,,2,1,,,Urban,4,7,TRUE,,"Saurians, Rhones",,,Air,3,1,,,Anons,,,,,,,,,
	// Diomas,152,,2,2,Province,,Urban,5,7,TRUE,,Rhones,Muggers,,Wild,3,4,,,Rhones,Chlorofix,,,,,,,,

	// Osirius,16
	Liomax(Osirius, None, Patriotic, Neutral, ImperialControlled,
			BaseGameEnviron.wild(6)
			   .resources(8)
			   .starResources()
			   .races(Rhone)
			   .creature(Lomrels)
			   .build()),
	Orlog(Osirius, Throne, Patriotic, Patriotic, ImperialControlled,
			BaseGameEnviron.urban(6)
			   .resources(7)
			   .starResources()
			   .races(Rhone)
			   .build(),
		   BaseGameEnviron.subterranian(2)
			   .resources(2)
			   .races(Rhone)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(3)
			   .races(Rhone)
			   .build()),
	Icid(Osirius, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(Saurian)
			   .creature(Rotron)
			   .build()),
	// Liomax,161,,2,0,,,Wild,6,8,TRUE,,Rhones,Lomrels,,,,,,,,,,,,,,,,
	// Orlog,162,,2,2,Throne,,Urban,6,7,TRUE,,Rhones,,,Subterannian,2,2,TRUE,,Rhones,,,Wild,3,3,,,Rhones,
	// Icid,163,,2,1,,,Wild,5,7,TRUE,,Saurians,Rotrons,,,,,,,,,,,,,,,,

	// Phisaria,21
	Cieson(Phisaria, None, Yester, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.air(3)
			   .resources(5)
			   .starResources()
			   .races(Yester)
			   .creature(Derigion)
			   .build()),
	Etreg(Phisaria, None, Patriotic, Loyal, ImperialControlled, true,
			BaseGameEnviron.urban(3)
			   .resources(8)
			   .races(Ultraks)	// Non-star faring
			   .sovereign(Treb_Eyro)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .resources(4)
			   .races(Kayn)
			   .creature(Magron)
			   .build()),
	// Cieson,211,Yesters,1,0,,,Air,3,5,TRUE,,Yesters,Derigions,,,,,,,,,,,,,,,,
	// Etreg,212,,2,0,,TRUE,Urban,3,8,,,Ultraks,,Treb Eyro (i),Wild,4,4,,,Kayns,Magrons,,,,,,,,

	// Egrix,22
	Quibron(Egrix, None, Loyal, Unrest, ImperialControlled, true,
			BaseGameEnviron.wild(4)
			   .resources(6)
			   .starResources()
			   .races(Saurian)
			   .creature(Snow_Giants)
			   .build()),
	Angoff(Egrix, None, Neutral, Unrest, RebelControlled, true,
			BaseGameEnviron.urban(6)
			   .resources(9)
			   .starResources()
			   .races(Yester)
			   .creature(Laboroid)
			   .coupRating(3)
			   .build()),
	Charkhan(Egrix, None, Patriotic, Dissent, ImperialControlled,
			BaseGameEnviron.air(5)
			   .resources(7)
			   .starResources()
			   .races(Yester)
			   .creature(Drants)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
			   .races(Charkhans)	// Non-starfaring race
			   .sovereign(Megda_Sheels)
			   .build()),
	// Quibron,221,,1,-2,,TRUE,Wild,4,6,TRUE,,Saurians,Snow Giants,,,,,,,,,,,,,,,,
	// Angoff,222,,0,-3,,TRUE,Urban,6,9,TRUE,3,Yesters,Laboroids,,,,,,,,,,,,,,,,
	// Charkhan,223,,2,-1,,,Air,5,7,TRUE,,Yesters,Drants,,Wild,3,5,TRUE,,Charkhans,,Magda Sheels (i),,,,,,,

	// Ancore,23
	Pronox(Ancore, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .starResources()
			   .races(Rhone)
			   .creature(Propang)
			   .sovereign(Ascaill)
			   .build(),
		   BaseGameEnviron.air(3)
			   .resources(5)
			   .starResources()
			   .races(Yester)
			   .build()),
	Lysenda(Ancore, None, Loyal, Dissent, ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(Segunden, Piorad)
			   .creature(Drusers)
			   .build(),
			BaseGameEnviron.subterranian(3)
			   .resources(2)
			   .races(Piorad)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(3)
			   .races(Ursi)	// Non-starfaring race
			   .creature(Batranoban)
			   .build()),
	// Pronox,231,,2,1,,,Urban,3,6,TRUE,,Rhones,Propangs,Ascaill (i),Air,3,5,TRUE,,,,,,,,,,,
	//Lysenda,232,,1,-1,,,Urban,4,7,TRUE,,"Segundians, Piorads",Drusers,,Subterranian,2,2,,,Piorads,,,Wild,3,3,,,Ursi,Batranobans

	//	Gellas,24
	Orning(Gellas, Provincial, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(Suvan)
			   .creature(Gilekite)
			   .coupRating(1)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(Rhone)
			   .creature(Sandiabs)
			   .build()),
	//	Orning,241,,2,1,Province,,Water,3,3,,1,Suvans,Gilekites,,Wild,5,7,TRUE,,Rhones,Sandiabs,,,,,,,,

	//	Pycius,31
	Chim(Pycius, Provincial, Patriotic, Patriotic, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(5)
			   .starResources()
			   .races(Rhone)
			   .creature(Namdasn)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(1)
			   .races(Mowev)	// Non-starfaring race
			   .build()),
	Tamset(Pycius, None, Patriotic, Loyal, ImperialControlled, true,
			BaseGameEnviron.air(4)
			   .resources(6)
			   .starResources()
			   .races(Yester)
			   .creature(Verfusier)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(5)
			   .races(Kirts)	// Non-starfaring race
			   .creature(Gregg)
			   .sovereign(Shirofune)
			   .build()),
	//	Chim,311,,2,2,Province,,Urban,3,5,TRUE,,Rhones,Namdasns,,Wild,4,1,,,Mowevs,,,,,,,,,
	//	Tamset,312,,-1,-3,,TRUE,Air,4,6,TRUE,,Yesters,Verfusiers,,Wild,5,5,,,Krits,Graggs,Shirofune (r),,,,,,,

	//	Ribex,32
	Unarpha(Ribex, None, Saurian, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .starResources()
			   .races(Saurian)
			   .creature(Alweg)
			   .build()),
	Suti(Ribex, None, Neutral, Unrest, ImperialControlled,
			BaseGameEnviron.subterranian(3)
			   .resources(5)
			   .starResources()
			   .races(Calma)	// Non-stardaring rage
			   .creature(Arags)
			   .sovereign(Xela_Grebb)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(2)
			   .races(Moghas)	// Non-starfaring race
			   .creature(Chantenes)
			   .build()),
	Tsipa(Ribex, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.urban(5)
			   .resources(8)
			   .starResources()
			   .races(Rhone)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(6)
			   .races(Rhone)
			   .creature(Queemer)
			   .build()),
	//	Unarpha,321,Saurians,1,0,,,Urban,3,6,TRUE,,Saurians,Alweg,,,,,,,,,,,,,,,,
	//	Suti,322,,0,-2,,,Subterranian,3,5,TRUE,,Calmas,Arags,Deal Grebb (n),Wild,4,2,,,Moghas,Chantenes,,,,,,,,
	//	Tsipa,323,,2,1,,,Urban,5,8,TRUE,,Rhones,,,Wild,4,6,TRUE,,Rhones,Queemers,,,,,,,,

	//	Rorth,33
	Squamot(Rorth, None, Neutral, Unrest, RebelControlled, true,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(Saurian)
			   .coupRating(3)
			   .build()),
	//	Squanot,331,,0,-3,,TRUE,Urban,4,7,TRUE,3,Saurians,,,,,,,,,,,,,,,,,

	//	Aziza,34
	Midest(Aziza, None, Patriotic, Dissent, ImperialControlled, true,
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(Suvan)
			   .creature(Morna)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(2)
			   .races(Deaxins) // Non-starfaring race
			   .creature(Vrialta)
			   .build()),
	Akubera(Aziza, None, Patriotic, Neutral, ImperialControlled,
			BaseGameEnviron.urban(2)
			   .resources(4)
			   .races(Suvan,Rhone)
			   .creature(Synestins)
			   .build(),
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(Suvan)
			   .build(),
			BaseGameEnviron.subterranian(4)
			   .resources(6)
			   .races(Rylians) // Non-starfaring race
			   .creature(Elilad)
			   .build()),
	//	Midest,341,,2,1,,TRUE,Water,3,3,,2,Suvans,Morna,,Wild,4,2,,,Deaxins,Vrialta,,,,,,,,
	//	Akubera,342,,2,0,,,Urban,2,4,,,"Suvans, Rhones",Synestins,,Water,3,3,,,Suvans,,,Subterranian,4,6,,,Rylians,Elilad

	//	Luine,35
	Mrane(Luine, None, Suvan, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(Suvan)
			   .creature(Gyrogos)
			   .sovereign(Balgar)
			   .build()),
	Kelta(Luine, None, Loyal, Dissent, ImperialControlled, true,
			BaseGameEnviron.wild(2)
			   .resources(4)
			   .races(Saurian)
			   .creature(Leonus)
			   .build()),
	//	Mrane,351,Suvans,1,0,,,Water,3,3,,,Suvans,Gyrogos,Balgar (n),,,,,,,,,,,,,,,
	//	Kelta,352,,1,-1,,TRUE,Wild,2,4,,,Suarians,Leonus,,,,,,,,,,,,,,,,

	//	Erwind,41
	Troliso(Erwind, Provincial, Patriotic, Patriotic, ImperialControlled,
			BaseGameEnviron.urban(6)
			   .resources(8)
			   .starResources()
			   .races(Rhone)
			   .creature(Sekekers)
			   .build()),
	Heliax(Erwind, None, Patriotic, Neutral, ImperialControlled,
			BaseGameEnviron.liquid(5)
			   .resources(7)
			   .starResources()
			   .races(Phans)	// Non-starfaring race
			   .creature(Virus)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .races(Leonid)	// Non-starfaring race
			   .sovereign(Odel_Hobar)
			   .build()),
	//	Troliso,411,,2,2,Province,,Urban,6,8,TRUE,,Rhones,Sekekers,,,,,,,,,,,,,,,,
	//	Heliax,412,,2,0,,,Water,5,7,TRUE,,Phans,Virus,,Wild,5,7,,,Leonus,,Oden Hobar (i),,,,,,,

	//	Wex,42
	Lonica(Wex, None, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(8)
			   .starResources()
			   .races(Segunden, Rhone)
			   .coupRating(1)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(6)
			   .starResources()
			   .races(Rhone)
			   .creature(Frost_Mist)
			   .build()),
	//	Lonica,421,,1,0,,,Urban,3,8,TRUE,1,"Segundians, Rhones",,,Wild,4,6,TRUE,,Rhones,Frost Mist,,,,,,,,

	//	Varu,43
	Horon(Varu, None, Patriotic, Neutral, ImperialControlled,
			BaseGameEnviron.liquid(6)
			   .resources(10)
			   .starResources()
			   .races(Henone)	// Non-starfaring race
			   .creature(Snorkas)
			   .build()),
	Solvia(Varu, None, Loyal, Dissent, ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .races(Susperans) // Non-starfaring race
			   .creature(Telebots)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.subterranian(3)
			   .resources(3)
			   .races(Piorad)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(3)
			   .races(Thoks)	// Non-starfaring race
			   .creature(Gadhars)
			   .build()),
	Cercis(Varu, None, Neutral, Unrest, RebelControlled,
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(Piorad)
			   .creature(Kinsog)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
			   .races(Illias)	// Non-starfaring race
			   .build()),
	//	Horon,431,,2,0,,,Water,6,10,TRUE,,Henones,Snorkas ,,,,,,,,,,,,,,,,
	//	Solvia,432,,1,1,,,Urban,4,7,,2,Susperans ,Telebots,,Subterranian ,3,3,,,Piorads,,,Wild,5,3,,,Thoks,Gadhars
	//	Cercis,433,,0,-3,,,Subterranian ,4,4,,,Piorads,Kinsogs,,Wild,3,5,TRUE,,Illeas,,,,,,,,,

	//	Deblon,44
	Rhexia(Deblon, None, Dissent, Unrest, RebelControlled,
			BaseGameEnviron.wild(3)
			   .resources(7)
			   .starResources()
			   .races(Theshian)	// Non-starfaring race
			   .creature(Thunk)
			   .sovereign(Yaldor)
			   .build()),
	Tartio(Deblon, None, 
			Loyal, Dissent, ImperialControlled, true,
			BaseGameEnviron.urban(3)
			   .resources(4)
			   .races(Piorad)
			   .build(),
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(Piorad)
			   .creature(Gamels)
			   .build()),
	//	Rhexia,441,,-1,-3,,,Wild,3,7,TRUE,,Thesnians,Thunks ,Zaldor (r),,,,,,,,,,,,,,,
	//	Tartio,442,,1,-1,,TRUE,Urban,3,4,,,Piorads,,,Subterranian ,4,4,,,Piorads,Gamels ,,,,,,,,

	//	Martigna,45
	Ayod(Martigna, None, Piorad, Loyal, Neutral, ImperialControlled,
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(Piorad)
			   .creature(Spithid)
			   .build()),
	//	Ayod,451,Piorads,1,0,,,Subterranian ,4,4,,,Piorads,Spithids,,,,,,,,,,,,,,,,

	//	Zakir,51
	Barak(Zakir, None, Neutral, Unrest, RebelControlled,
			BaseGameEnviron.urban(5)
			   .resources(10)
			   .races(Jopers)	// Non-starfaring race
			   .creature(Hysnatons)
			   .sovereign(Inzenzia_III)
			   .build()),
	Liatris(Zakir, None, Patriotic, Neutral, ImperialControlled, true,
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .races(Ardorats)	// Non-starfaring race
			   .creature(Garb)
			   .coupRating(1)
			   .build()),
	Xan(Zakir, None, Xanthon, Loyal, Dissent, ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .races(Ornotins)	// Non-starfaring race
			   .build(),
			BaseGameEnviron.fire(5)
			   .resources(4)
			   .races(Xanthon)
			   .creature(Mish)
			   .build()),
	//	Barak,511,,0,-3,,,Urban,5,10,,,Jopers,Hysnatons,Inzenzia III (r),,,,,,,,,,,,,,,
	//	Liatris,512,,2,0,,TRUE,Wild,5,7,,1,Ardorats ,Gachs,,,,,,,,,,,,,,,,
	//	Xan,513,Xanathons,1,-1,,,Urban,3,6,,,Orthontins,,,Fire,5,4,,,Xanthons,Mish,,,,,,,,

	//	Eudox,52
	Aras(Eudox, None, Loyal, Neutral, ImperialControlled, true,
			BaseGameEnviron.urban(4)
			   .resources(9)
			   .starResources()
			   .races(Segunden)
			   .creature(Chardireeds)
			   .sovereign(Tensok_Phi)
			   .build()),
	Capilax(Eudox, None, Loyal, Dissent, ImperialControlled, true,
			BaseGameEnviron.fire(4)
			   .resources(3)
			   .races(Xanthon)
			   .creature(Onflam)
			   .build()),
	Adrax(Eudox, None, Patriotic, Loyal, ImperialControlled,
			BaseGameEnviron.subterranian(2)
			   .resources(4)
			   .races(Rhone)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
			   .races(Rhone)
			   .creature(Thinagig)
			   .build()),
	//	Aras,521,,1,0,,TRUE,Urban,4,9,TRUE,,Segundians ,Chardireeds,Tensok-Phi (n),,,,,,,,,,,,,,,
	//	Capilax,522,,1,-1,,TRUE,Fire,4,3,,,Xanthons,Onflams,,,,,,,,,,,,,,,,
	//	Adrax,523,,2,1,,,Subterranian ,2,4,,2,Rhones,,,Wild,3,5,TRUE,,Rhones,Thinagigs,,,,,,,,

	//	Corusa,53
	Scythia(Corusa, None, Loyal, Unrest, RebelControlled, true,
			BaseGameEnviron.fire(4)
			   .resources(3)
			   .races(Xanthon)
			   .build()),
	//	Scythia,531,,1,-3,,TRUE,Fire,4,3,,,Xanthons,,,,,,,,,,,,,,,,,

	//	Irajeba,54
	Annell(Irajeba, None, Neutral, Unrest, ImperialControlled, true,
			BaseGameEnviron.air(4)
			   .resources(8)
			   .starResources()
			   .races(Cavalkus)	// Non-starfaring race
			   .creature(Fog)
			   .sovereign(Darb_Selesh)
			   .build()),
	Trov(Irajeba, Provincial, Patriotic, Patriotic, ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(6)
			   .starResources()
			   .races(Rhone)
			   .creature(Valaterix)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(4)
			   .races(Rhone)
			   .build()),
	//	Annell,541,,0,-2,,TRUE,Air,4,8,,,Cavalkus,Fog,Darn Salesh (n),,,,,,,,,,,,,,,
	//	Trov,542,,2,2,Provincial,,Urban,4,6,TRUE,,Rhones,Valaterix,,Wild,3,4,,,Rhones,,,,,,,,,

	//	Moda,55
	Niconi(Moda, None, Patriotic, Neutral, ImperialControlled,
			BaseGameEnviron.wild(4)
			   .resources(5)
			   .races(Kayn)
			   .creature(Wyths)
			   .coupRating(2)
			   .build()),
	//	Niconi,551,,2,0,,,Wild,4,5,TRUE,2,Kayns,Wyths,,,,,,,,,,,,,,,,

	;
	
/*
Name,Id,Home Race,S,A,Capital,Secret,Environ1,Size,Resources,StarResources,Coup Rating,Races,Creature,Sovereign,Environ2,Size,Resources,StarResources,Coup Rating,Race,Creature,Sovereign,Environ3,Size,Resources,StarResources,Coup Rating,Race,Creature
Tardyn,11,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Mimulus,111,Kayans,2,0,,,Wild,4,4,TRUE,,Kayns,Prox,,,,,,,,,,,,,,,,
Magro,112,,2,1,,,Urban,3,,,6,Rhones,,,Air,5,,,7,,Glane,,,,,,,,
Fliad,113,,2,1,,,Water,4,,,4,,,,Wild,4,,,4,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Uracas,12,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Kalgar,121,,2,1,,,Subterranian,6,7,TRUE,,"Kayns, Saurians",Crunge,,,,,,,,,,,,,,,,
Bajukai,122,,1,0,,,Urban,3,8,TRUE,,Segundians ,,,Water,3,7,TRUE,,Segundians,,,Wild,4,6,,,Borks,Ymbarrors
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Zamorax,13,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Tiglyf,131,,2,-1,,TRUE,Urban,5,10,TRUE,,Segundians,Vorozion,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Atriard,14,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Ownex,141,,0,-3,,,Subteranian,5,5,TRUE,,Piorads,Stromuse,Nam Nhuk (R),,,,,,,,,,,,,,,
Adare,142,,1,0,,,Urban,4,7,TRUE,,Rhones,Zernipaks,,Wild,5,7,TRUE,,Rhones,,,,,,,,,
Mitrith,143,,,,,,Fire,2,2,,,Xanthons,,,Wild,4,6,TRUE,,Saurians,Zop,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Bex,15,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Jura,151,,2,1,,,Urban,4,7,TRUE,,"Saurians, Rhones",,,Air,3,1,,,Anons,,,,,,,,,
Diomas,152,,2,2,Province,,Urban,5,7,TRUE,,Rhones,Muggers,,Wild,3,4,,,Rhones,Chlorofix,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Osirius,16,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Liomax,161,,2,0,,,Wild,6,8,TRUE,,Rhones,Lomrels,,,,,,,,,,,,,,,,
Orlog,162,,2,2,Throne,,Urban,6,7,TRUE,,Rhones,,,Subterannian,2,2,TRUE,,Rhones,,,Wild,3,3,,,Rhones,
Icid,163,,2,1,,,Wild,5,7,TRUE,,Saurians,Rotrons,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Phisaria,21,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Cieson,211,Yesters,1,0,,,Air,3,5,TRUE,,Yesters,Derigions,,,,,,,,,,,,,,,,
Etreg,212,,2,0,,TRUE,Urban,3,8,,,Ultraks,,Treb Eyro (i),Wild,4,4,,,Kayns,Magrons,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Egrix,22,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Quibron,221,,1,-2,,TRUE,Wild,4,6,TRUE,,Saurians,Snow Giants,,,,,,,,,,,,,,,,
Angoff,222,,0,-3,,TRUE,Urban,6,9,TRUE,3,Yesters,Laboroids,,,,,,,,,,,,,,,,
Charkhan,223,,2,-1,,,Air,5,7,TRUE,,Yesters,Drants,,Wild,3,5,TRUE,,Charkhans,,Magda Sheels (i),,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Ancore,23,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Pronox,231,,2,1,,,Urban,3,6,TRUE,,Rhones,Propangs,Ascaill (i),Air,3,5,TRUE,,,,,,,,,,,
Lysenda,232,,1,-1,,,Urban,4,7,TRUE,,"Segundians, Piorads",Drusers,,Subterranian,2,2,,,Piorads,,,Wild,3,3,,,Ursi,Batranobans
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Gellas,24,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Orning,241,,2,1,Province,,Water,3,3,,1,Suvans,Gilekites,,Wild,5,7,TRUE,,Rhones,Sandiabs,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Pycius,31,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Chim,311,,2,2,Province,,Urban,3,5,TRUE,,Rhones,Namdasns,,Wild,4,1,,,Mowevs,,,,,,,,,
Tamset,312,,-1,-3,,TRUE,Air,4,6,TRUE,,Yesters,Verfusiers,,Wild,5,5,,,Krits,Graggs,Shirofune (r),,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Ribex,32,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Unarpha,321,Saurians,1,0,,,Urban,3,6,TRUE,,Saurians,Alweg,,,,,,,,,,,,,,,,
Suti,322,,0,-2,,,Subterranian,3,5,TRUE,,Calmas,Arags,Deal Grebb (n),Wild,4,2,,,Moghas,Chantenes,,,,,,,,
Tsipa,323,,2,1,,,Urban,5,8,TRUE,,Rhones,,,Wild,4,6,TRUE,,Rhones,Queemers,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Rorth,33,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Squanot,331,,0,-3,,TRUE,Urban,4,7,TRUE,3,Saurians,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Aziza,34,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Midest,341,,2,1,,TRUE,Water,3,3,,2,Suvans,Morna,,Wild,4,2,,,Deaxins,Vrialta,,,,,,,,
Akubera,342,,2,0,,,Urban,2,4,,,"Suvans, Rhones",Synestins,,Water,3,3,,,Suvans,,,Subterranian,4,6,,,Rylians,Elilad
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Luine,35,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Mrane,351,Suvans,1,0,,,Water,3,3,,,Suvans,Gyrogs,Bulgar (n),,,,,,,,,,,,,,,
Kelta,352,,1,-1,,TRUE,Wild,2,4,,,Suarians,Leonus,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Erwind,41,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Troliso,411,,2,2,Province,,Urban,6,8,TRUE,,Rhones,Sekekers,,,,,,,,,,,,,,,,
Heliax,412,,2,0,,,Water,5,7,TRUE,,Phans,Virus,,Wild,5,7,,,Leonids,,Oden Hobar (i),,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Wex,42,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Lonica,421,,1,0,,,Urban,3,8,TRUE,1,"Segundians, Rhones",,,Wild,4,6,TRUE,,Rhones,Frost Mist,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Varu,43,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Horon,431,,2,0,,,Water,6,10,TRUE,,Henones,Snorkas ,,,,,,,,,,,,,,,,
Solvia,432,,1,1,,,Urban,4,7,,2,Susperans ,Telebots,,Subterranian ,3,3,,,Piorads,,,Wild,5,3,,,Thoks,Gadhars
Cercis,433,,0,-3,,,Subterranian ,4,4,,,Piorads,Kinsols,,Wild,3,5,TRUE,,Illeas,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Deblon,44,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Rhexia,441,,-1,-3,,,Wild,3,7,TRUE,,Thesnians,Thunks ,Zaldor (r),,,,,,,,,,,,,,,
Tartio,442,,1,-1,,TRUE,Urban,3,4,,,Piorads,,,Subterranian ,4,4,,,Piorads,Gamels ,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Martigna,45,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Ayod,451,Piorads,1,0,,,Subterranian ,4,4,,,Piorads,Spithids,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Zakir,51,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Barak,511,,0,-3,,,Urban,5,10,,,Jopers,Hysnatons,Inzenzia III (r),,,,,,,,,,,,,,,
Liatris,512,,2,0,,TRUE,Wild,5,7,,1,Ardorats ,Gachs,,,,,,,,,,,,,,,,
Xan,513,Xanathons,1,-1,,,Urban,3,6,,,Orthontins,,,Fire,5,4,,,Xanthons,Mish,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Eudox,52,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Aras,521,,1,0,,TRUE,Urban,4,9,TRUE,,Segundians ,Chardireeds,Tensok-Phi (n),,,,,,,,,,,,,,,
Capilax,522,,1,-1,,TRUE,Fire,4,3,,,Xanthons,Onflams,,,,,,,,,,,,,,,,
Adrax,523,,2,1,,,Subterranian ,2,4,,2,Rhones,,,Wild,3,5,TRUE,,Rhones,Thinagigs,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Corusa,53,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Scythia,531,,1,-3,,TRUE,Fire,4,3,,,Xanthons,,,,,,,,,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Irajeba,54,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Annell,541,,0,-2,,TRUE,Air,4,8,,,Cavalkus,Fog,Darn Salesh (n),,,,,,,,,,,,,,,
Trov,542,,2,2,Provincial,,Urban,4,6,TRUE,,Rhones,Valaterix,,Wild,3,4,,,Rhones,,,,,,,,,
,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Moda,55,,,,,,,,,,,,,,,,,,,,,,,,,,,,
Niconi,551,,2,0,,,Wild,4,5,TRUE,2,Kayns,Wyths,,,,,,,,,,,,,,,,


*/
	
	private final BaseGameStarSystem starSystem;
	private final BaseGameCapitalType capitalType;
	private final List<BaseGameEnviron> environs;
	private final Optional<BaseGameRaceType> homeworld;
	private final LoyaltyType startingLoyaltyS;
	private final LoyaltyType startingLoyaltyA;
	private final BaseGamePlanetaryControlType controlA;
	private final boolean hasSecret;

	private LoyaltyType currentLoyalty;
	private BaseGamePlanetaryControlType currentControl;
	

	/*
	Home Race,S,A,Capital,Secret,Environ1,Size,Resources,StarResources,Coup Rating,Races,Creature,Sovereign,Environ2,Size,Resources,StarResources,Coup Rating,Race,Creature,Sovereign,Environ3,Size,Resources,StarResources,Coup Rating,Race,Creature
	 */
	private BaseGamePlanet(BaseGameStarSystem starSystem, BaseGameCapitalType capitalType, BaseGameRaceType homeworld,
			BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA, BaseGamePlanetaryControlType controlA,
			BaseGameEnviron... environs) {
		this.starSystem = starSystem;
		this.capitalType = capitalType;
		this.homeworld = Optional.of(homeworld);
		this.controlA = controlA;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.environs = List.of(environs);
		this.hasSecret = false;
	}

	private BaseGamePlanet(BaseGameStarSystem starSystem, BaseGameCapitalType capitalType,
			BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA, BaseGamePlanetaryControlType controlA,
			BaseGameEnviron... environs) {
		this.starSystem = starSystem;
		this.capitalType = capitalType;
		this.homeworld = Optional.empty();
		this.controlA = controlA;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.environs = List.of(environs);
		this.hasSecret = false;
	}

	private BaseGamePlanet(BaseGameStarSystem starSystem, BaseGameCapitalType capitalType, BaseGameRaceType homeworld,
			BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA, BaseGamePlanetaryControlType controlA,
			boolean hasSecret, BaseGameEnviron... environs) {
		this.starSystem = starSystem;
		this.capitalType = capitalType;
		this.homeworld = Optional.of(homeworld);
		this.controlA = controlA;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.environs = List.of(environs);
		this.hasSecret = hasSecret;
	}

	private BaseGamePlanet(BaseGameStarSystem starSystem, BaseGameCapitalType capitalType,
			BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA, BaseGamePlanetaryControlType controlA,
			boolean hasSecret, BaseGameEnviron... environs) {
		this.starSystem = starSystem;
		this.capitalType = capitalType;
		this.homeworld = Optional.empty();
		this.controlA = controlA;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.environs = List.of(environs);
		this.hasSecret = hasSecret;
	}

	@Override
	public LoyaltyType getCurrentLoyalty() {
		return currentLoyalty;
	}

	@Override
	public void setCurrentLoyalty(LoyaltyType currentLoyalty) {
		this.currentLoyalty = currentLoyalty;
	}

	@Override
	public String getName() {
		return this.toString().replace('_', ' ');
	}

	@Override
	public int getId() {
		int myIndex = this.ordinal();
		BaseGamePlanet[] allPlanets = BaseGamePlanet.values();

		// The last digit if our id is the offset from the last star system that is in a different province.
		IntPredicate testOp = (i)->(i >= 0 && allPlanets[i].starSystem == this.starSystem);	// Stop if index < 0 or the star system pointed to has a different province.
		IntUnaryOperator indexOp = (oi)->myIndex - oi - 1;	// Current star system's index minus the offset index (plus 1)

		int offset = 0;
		while(testOp.test(indexOp.applyAsInt(offset))) {
			offset++;
		}
		
		return this.starSystem.getId() * 10 + (offset + 1);
	}

	@Override
	public BaseGameCapitalType getCapitalType() {
		return capitalType;
	}

	@Override
	public List<BaseGameEnviron> listEnvirons() {
		return environs;
	}

	@Override
	public Stream<BaseGameEnviron> streamEnvirons() {
		return environs.stream();
	}

	@Override
	public Optional<BaseGameRaceType> getHomeworld() {
		return homeworld;
	}

	@Override
	public BaseGamePlanetaryControlType getControlA() {
		return controlA;
	}

	@Override
	public LoyaltyType getStartingLoyaltyS() {
		return startingLoyaltyS;
	}

	@Override
	public LoyaltyType getStartingLoyaltyA() {
		return startingLoyaltyA;
	}

	@Override
	public BaseGamePlanetaryControlType getCurrentControl() {
		return currentControl;
	}

	@Override
	public void setCurrentControl(BaseGamePlanetaryControlType currentControl) {
		this.currentControl = currentControl;
	}

	@Override
	public BaseGameStarSystem getStarSystem() {
		return starSystem;
	}

	@Override
	public boolean hasSecret() {
		return hasSecret;
	}
	
	private static final List<BaseGamePlanet> planetList = List.of(BaseGamePlanet.values());
	
	public static List<BaseGamePlanet> planets() {
		return planetList;
	}
	
	public static List<BaseGamePlanet> planets(Predicate<BaseGamePlanet> predicate) {
		return BaseGamePlanet.stream()
							 .filter(predicate)
							 .collect(Collectors.toUnmodifiableList());
	}
	
	public static Stream<BaseGamePlanet> stream() {
		return Stream.of(BaseGamePlanet.values());
	}

	public static Stream<BaseGamePlanet> stream(Predicate<BaseGamePlanet> predicate) {
		return BaseGamePlanet.stream().filter(predicate);
	}

	public static BaseGamePlanet requireBgp(Planet planet) {
		if (planet instanceof BaseGamePlanet bgp) {
			return bgp;
		}
		throw new IllegalArgumentException("Planet (" + planet.getName() + ") is not a BaseGamePlanet.");
	}
}
