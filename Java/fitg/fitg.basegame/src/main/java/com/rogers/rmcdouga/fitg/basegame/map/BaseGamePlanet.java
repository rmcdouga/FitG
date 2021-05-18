package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;


public enum BaseGamePlanet {
	// Tardyn,11
	Mimulus(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, BaseGameRaceType.Kayn, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.wild(4)
						   .resources(4)
						   .starResources()
						   .races(BaseGameRaceType.Kayn)
						   .creature(BaseGameCreature.Prox)
						   .build()),
	//	Mimulus,111,Kayans,2,0,,,
	//		Wild,4,4,TRUE,,Kayns,Prox,,,,,,,,,,,,,,,,
	Magro(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .build(),
		   BaseGameEnviron.air(5)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .creature(BaseGameCreature.Glane)
			   .build()),
	//	Magro,112,,2,1,,,
	// 		Urban,3,,,6,Rhones,,,
	//		Air,5,,,7,,Glane,,,,,,,,
	Fliad(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.liquid(4)
			   .resources(4)
			   .starResources()
			   .races(BaseGameRaceType.Suvan)
			   .creature(BaseGameCreature.Dindin)
			   .coupRating(3)
			   .build(),
		   BaseGameEnviron.wild(4)
			   // .races(BaseGameRaceType.Urgaks) // Not a star-faring race.
			   .resources(4)
			   .build()),
	//	Fliad,113,,2,1,,,
	//		Water,4,,,4,,,,
	//		Wild,4,,,4,,,,,,,,,,

	// Environ of(EnvironType type, int size, int resources, boolean starResources, int coupRating, List<BaseGameRaceType> races, BaseGameCreature creature, BaseGameSovereign sovereign)

	// Uracas,12
	Kalgar(BaseGameStarSystem.Uracus, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.subterranian(6)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Kayn, BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Crunge)
			   .build()),
	Bajukai(BaseGameStarSystem.Uracus, BaseGameCapitalType.None, BaseGameRaceType.Segunden, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(8)
			   .starResources()
			   .races(BaseGameRaceType.Segunden)
			   .build(),
		   BaseGameEnviron.liquid(3)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Segunden)
			   .build(),
		   BaseGameEnviron.subterranian(4)
			   .resources(6)
//			   .races(BaseGameRaceType.Bork)	// Not a star faring race
			   .creature(BaseGameCreature.Ym$Barror)
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
	Tiglyf(BaseGameStarSystem.Zamorax, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.urban(5)
			   .resources(10)
			   .starResources()
			   .races(BaseGameRaceType.Segunden)
			   .creature(BaseGameCreature.Vorozion)
			   .build()),
	// Tiglyf,131,,2,-1,,TRUE,Urban,5,10,TRUE,,Segundians,Vorozion,,,,,,,,,,,,,,,,

	// Atriard,14
	Ownex(BaseGameStarSystem.Atriard, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled,
			BaseGameEnviron.subterranian(5)
			   .resources(5)
			   .starResources()
			   .races(BaseGameRaceType.Piorad)
			   .creature(BaseGameCreature.Siromuse)
			   .sovereign(BaseGameSovereign.Nam_Nhuk)
			   .build()),
	Adare(BaseGameStarSystem.Atriard, BaseGameCapitalType.None,  
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Zernipak)
			   .build(),
		   BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .build()),
	Mitrith(BaseGameStarSystem.Atriard, BaseGameCapitalType.None,
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.fire(2)
			   .resources(2)
			   .races(BaseGameRaceType.Xanthon)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Zop)
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
	Jura(BaseGameStarSystem.Bex, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Saurian, BaseGameRaceType.Rhone)
			   .build(),
		   BaseGameEnviron.air(3)
			   .resources(1)
//			   .races(BaseGameRaceType.Anon)	// Not a star-faring race
			   .build()),
	Diomas(BaseGameStarSystem.Bex, BaseGameCapitalType.Provincial, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Patriotic, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(5)
			   .resources(7)
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Muggers)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(4)
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Chlorofix)
			   .build()),
	// Jura,151,,2,1,,,Urban,4,7,TRUE,,"Saurians, Rhones",,,Air,3,1,,,Anons,,,,,,,,,
	// Diomas,152,,2,2,Province,,Urban,5,7,TRUE,,Rhones,Muggers,,Wild,3,4,,,Rhones,Chlorofix,,,,,,,,

	// Osirius,16
	Liomax(BaseGameStarSystem.Osirius, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.wild(6)
			   .resources(8)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Lomrels)
			   .build()),
	Orlog(BaseGameStarSystem.Osirius, BaseGameCapitalType.Throne, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Patriotic, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(6)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .build(),
		   BaseGameEnviron.subterranian(2)
			   .resources(2)
			   .races(BaseGameRaceType.Rhone)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(3)
			   .races(BaseGameRaceType.Rhone)
			   .build()),
	Icid(BaseGameStarSystem.Osirius, BaseGameCapitalType.None,
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Rotron)
			   .build()),
	// Liomax,161,,2,0,,,Wild,6,8,TRUE,,Rhones,Lomrels,,,,,,,,,,,,,,,,
	// Orlog,162,,2,2,Throne,,Urban,6,7,TRUE,,Rhones,,,Subterannian,2,2,TRUE,,Rhones,,,Wild,3,3,,,Rhones,
	// Icid,163,,2,1,,,Wild,5,7,TRUE,,Saurians,Rotrons,,,,,,,,,,,,,,,,

	// Phisaria,21
	Cieson(BaseGameStarSystem.Phisaria, BaseGameCapitalType.None, BaseGameRaceType.Yester, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.air(3)
			   .resources(5)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .creature(BaseGameCreature.Derigion)
			   .build()),
	Etreg(BaseGameStarSystem.Phisaria, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.urban(3)
			   .resources(8)
//			   .races(BaseGameRaceType.Utrak)	// Non-star faring
			   .sovereign(BaseGameSovereign.Treb_Eyro)
			   .build(),
		   BaseGameEnviron.wild(4)
			   .resources(4)
			   .races(BaseGameRaceType.Kayn)
			   .creature(BaseGameCreature.Magron)
			   .build()),
	// Cieson,211,Yesters,1,0,,,Air,3,5,TRUE,,Yesters,Derigions,,,,,,,,,,,,,,,,
	// Etreg,212,,2,0,,TRUE,Urban,3,8,,,Ultraks,,Treb Eyro (i),Wild,4,4,,,Kayns,Magrons,,,,,,,,

	// Egrix,22
	Quibron(BaseGameStarSystem.Egrix, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.liquid(4)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Snow_Giants)
			   .build()),
	Angoff(BaseGameStarSystem.Egrix, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled, true,
			BaseGameEnviron.urban(6)
			   .resources(9)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .creature(BaseGameCreature.Laboroid)
			   .coupRating(3)
			   .build()),
	Charkhan(BaseGameStarSystem.Egrix, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.air(3)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .creature(BaseGameCreature.Drants)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
//			   .races(BaseGameRaceType.Charkhans)	// Non-start-faring race
			   .sovereign(BaseGameSovereign.Megda_Sheels)
			   .build()),
	// Quibron,221,,1,-2,,TRUE,Wild,4,6,TRUE,,Saurians,Snow Giants,,,,,,,,,,,,,,,,
	// Angoff,222,,0,-3,,TRUE,Urban,6,9,TRUE,3,Yesters,Laboroids,,,,,,,,,,,,,,,,
	// Charkhan,223,,2,-1,,,Air,5,7,TRUE,,Yesters,Drants,,Wild,3,5,TRUE,,Charkhans,,Magda Sheels (i),,,,,,,

	// Ancore,23
	Pronox(BaseGameStarSystem.Ancore, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Propang)
			   .sovereign(BaseGameSovereign.Ascaill)
			   .build(),
		   BaseGameEnviron.air(3)
			   .resources(5)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .build()),
	Lysenda(BaseGameStarSystem.Ancore, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Segunden, BaseGameRaceType.Piorad)
			   .creature(BaseGameCreature.Drusers)
			   .build(),
			BaseGameEnviron.subterranian(3)
			   .resources(2)
			   .races(BaseGameRaceType.Piorad)
			   .build(),
		   BaseGameEnviron.wild(3)
			   .resources(3)
//			   .races(BaseGameRaceType.Ursi)	// Non-start-faring race
			   .creature(BaseGameCreature.Batranoban)
			   .build()),
	// Pronox,231,,2,1,,,Urban,3,6,TRUE,,Rhones,Propangs,Ascaill (i),Air,3,5,TRUE,,,,,,,,,,,
	//Lysenda,232,,1,-1,,,Urban,4,7,TRUE,,"Segundians, Piorads",Drusers,,Subterranian,2,2,,,Piorads,,,Wild,3,3,,,Ursi,Batranobans

	//	Gellas,24
	Orning(BaseGameStarSystem.Gellas, BaseGameCapitalType.Provincial, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.liquid(4)
			   .resources(3)
			   .races(BaseGameRaceType.Suvan)
			   .creature(BaseGameCreature.Gilekite)
			   .coupRating(1)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Sandiabs)
			   .build()),
	//	Orning,241,,2,1,Province,,Water,3,3,,1,Suvans,Gilekites,,Wild,5,7,TRUE,,Rhones,Sandiabs,,,,,,,,

	//	Pycius,31
	Chim(BaseGameStarSystem.Pycius, BaseGameCapitalType.Provincial, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Patriotic, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(5)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Namdasn)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(1)
//			   .races(BaseGameRaceType.Mowevs)	// Non-starfaring race
			   .build()),
	Tamset(BaseGameStarSystem.Pycius, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.air(4)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Yester)
			   .creature(BaseGameCreature.Verfusier)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(5)
//			   .races(BaseGameRaceType.Kirts)	// Non-starfaring race
			   .creature(BaseGameCreature.Gregg)
			   .sovereign(BaseGameSovereign.Shirofune)
			   .build()),
	//	Chim,311,,2,2,Province,,Urban,3,5,TRUE,,Rhones,Namdasns,,Wild,4,1,,,Mowevs,,,,,,,,,
	//	Tamset,312,,-1,-3,,TRUE,Air,4,6,TRUE,,Yesters,Verfusiers,,Wild,5,5,,,Krits,Graggs,Shirofune (r),,,,,,,

	//	Ribex,32
	Unarpha(BaseGameStarSystem.Ribex, BaseGameCapitalType.None, BaseGameRaceType.Saurian, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.air(3)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Alweg)
			   .build()),
	Suti(BaseGameStarSystem.Ribex, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.subterranian(3)
			   .resources(5)
			   .starResources()
//			   .races(BaseGameRaceType.Calmas)	// Non-stardaring rage
			   .creature(BaseGameCreature.Arags)
			   .sovereign(BaseGameSovereign.Xela_Grebb)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(3)
//			   .races(BaseGameRaceType.Moghas)	// Non-starfaring race
			   .creature(BaseGameCreature.Chantenes)
			   .build()),
	Tsipa(BaseGameStarSystem.Ribex, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(5)
			   .resources(8)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(6)
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Queemer)
			   .build()),
	//	Unarpha,321,Saurians,1,0,,,Urban,3,6,TRUE,,Saurians,Alweg,,,,,,,,,,,,,,,,
	//	Suti,322,,0,-2,,,Subterranian,3,5,TRUE,,Calmas,Arags,Deal Grebb (n),Wild,4,2,,,Moghas,Chantenes,,,,,,,,
	//	Tsipa,323,,2,1,,,Urban,5,8,TRUE,,Rhones,,,Wild,4,6,TRUE,,Rhones,Queemers,,,,,,,,

	//	Rorth,33
	Squanot(BaseGameStarSystem.Rorth, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled, true,
			BaseGameEnviron.urban(4)
			   .resources(7)
			   .starResources()
			   .races(BaseGameRaceType.Saurian)
			   .coupRating(3)
			   .build()),
	//	Squanot,331,,0,-3,,TRUE,Urban,4,7,TRUE,3,Saurians,,,,,,,,,,,,,,,,,

	//	Aziza,34
	Midest(BaseGameStarSystem.Aziza, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(BaseGameRaceType.Suvan)
			   .creature(BaseGameCreature.Morna)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(2)
//			   .races(BaseGameRaceType.Deaxins) // Non-starfaring race
			   .creature(BaseGameCreature.Vrialta)
			   .build()),
	Akubera(BaseGameStarSystem.Aziza, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(2)
			   .resources(4)
			   .races(BaseGameRaceType.Suvan,BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Synestins)
			   .build(),
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(BaseGameRaceType.Suvan)
			   .build(),
			BaseGameEnviron.subterranian(4)
			   .resources(6)
//			   .races(BaseGameRaceType.Rylians) // Non-starfaring race
			   .creature(BaseGameCreature.Elilad)
			   .build()),
	//	Midest,341,,2,1,,TRUE,Water,3,3,,2,Suvans,Morna,,Wild,4,2,,,Deaxins,Vrialta,,,,,,,,
	//	Akubera,342,,2,0,,,Urban,2,4,,,"Suvans, Rhones",Synestins,,Water,3,3,,,Suvans,,,Subterranian,4,6,,,Rylians,Elilad

	//	Luine,35
	Mrane(BaseGameStarSystem.Luine, BaseGameCapitalType.None, BaseGameRaceType.Suvan, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.liquid(3)
			   .resources(3)
			   .races(BaseGameRaceType.Suvan)
			   .creature(BaseGameCreature.Gyrogos)
			   .sovereign(BaseGameSovereign.Balgar)
			   .build()),
	Kelta(BaseGameStarSystem.Luine, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.wild(2)
			   .resources(4)
			   .races(BaseGameRaceType.Saurian)
			   .creature(BaseGameCreature.Leonus)
			   .build()),
	//	Mrane,351,Suvans,1,0,,,Water,3,3,,,Suvans,Gyrogos,Balgar (n),,,,,,,,,,,,,,,
	//	Kelta,352,,1,-1,,TRUE,Wild,2,4,,,Suarians,Leonus,,,,,,,,,,,,,,,,

	//	Erwind,41
	Troliso(BaseGameStarSystem.Erwind, BaseGameCapitalType.Provincial, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Patriotic, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(6)
			   .resources(8)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Sekekers)
			   .build()),
	Heliax(BaseGameStarSystem.Erwind, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.liquid(5)
			   .resources(7)
			   .starResources()
//			   .races(BaseGameRaceType.Phans)	// Non-starfaring race
			   .creature(BaseGameCreature.Virus)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(7)
//			   .races(BaseGameRaceType.Leonids)	// Non-starfaring race
			   .sovereign(BaseGameSovereign.Odel_Hobar)
			   .build()),
	//	Troliso,411,,2,2,Province,,Urban,6,8,TRUE,,Rhones,Sekekers,,,,,,,,,,,,,,,,
	//	Heliax,412,,2,0,,,Water,5,7,TRUE,,Phans,Virus,,Wild,5,7,,,Leonus,,Oden Hobar (i),,,,,,,

	//	Wex,42
	Lonica(BaseGameStarSystem.Wex, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(8)
			   .starResources()
			   .races(BaseGameRaceType.Segunden, BaseGameRaceType.Rhone)
			   .coupRating(1)
			   .build(),
			BaseGameEnviron.wild(4)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Frost_Mist)
			   .build()),
	//	Lonica,421,,1,0,,,Urban,3,8,TRUE,1,"Segundians, Rhones",,,Wild,4,6,TRUE,,Rhones,Frost Mist,,,,,,,,

	//	Varu,43
	Horon(BaseGameStarSystem.Varu, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.liquid(6)
			   .resources(10)
			   .starResources()
//			   .races(BaseGameRaceType.Henones)	// Non-starfaring race
			   .creature(BaseGameCreature.Snorkas)
			   .build()),
	Solvia(BaseGameStarSystem.Varu, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(7)
//			   .races(BaseGameRaceType.Susperans) // Non-starfaring race
			   .creature(BaseGameCreature.Telebots)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.subterranian(3)
			   .resources(3)
			   .races(BaseGameRaceType.Piorad)
			   .build(),
			BaseGameEnviron.wild(5)
			   .resources(3)
//			   .races(BaseGameRaceType.Thoks)	// Non-starfaring race
			   .creature(BaseGameCreature.Gadhars)
			   .build()),
	Cercis(BaseGameStarSystem.Varu, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled,
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(BaseGameRaceType.Piorad)
			   .creature(BaseGameCreature.Kinsog)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
//			   .races(BaseGameRaceType.Illeas)	// Non-starfaring race
			   .build()),
	//	Horon,431,,2,0,,,Water,6,10,TRUE,,Henones,Snorkas ,,,,,,,,,,,,,,,,
	//	Solvia,432,,1,1,,,Urban,4,7,,2,Susperans ,Telebots,,Subterranian ,3,3,,,Piorads,,,Wild,5,3,,,Thoks,Gadhars
	//	Cercis,433,,0,-3,,,Subterranian ,4,4,,,Piorads,Kinsogs,,Wild,3,5,TRUE,,Illeas,,,,,,,,,

	//	Deblon,44
	Rhexia(BaseGameStarSystem.Deblon, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Dissent, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled,
			BaseGameEnviron.wild(3)
			   .resources(7)
			   .starResources()
//			   .races(BaseGameRaceType.Thesnians)	// Non-starfaring race
			   .creature(BaseGameCreature.Thunk)
			   .sovereign(BaseGameSovereign.Yaldor)
			   .build()),
	Tartio(BaseGameStarSystem.Deblon, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.urban(3)
			   .resources(4)
			   .races(BaseGameRaceType.Piorad)
			   .build(),
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(BaseGameRaceType.Piorad)
			   .creature(BaseGameCreature.Gamels)
			   .build()),
	//	Rhexia,441,,-1,-3,,,Wild,3,7,TRUE,,Thesnians,Thunks ,Zaldor (r),,,,,,,,,,,,,,,
	//	Tartio,442,,1,-1,,TRUE,Urban,3,4,,,Piorads,,,Subterranian ,4,4,,,Piorads,Gamels ,,,,,,,,

	//	Martigna,45
	Ayod(BaseGameStarSystem.Martigna, BaseGameCapitalType.None, BaseGameRaceType.Piorad, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.subterranian(4)
			   .resources(4)
			   .races(BaseGameRaceType.Piorad)
			   .creature(BaseGameCreature.Spithid)
			   .build()),
	//	Ayod,451,Piorads,1,0,,,Subterranian ,4,4,,,Piorads,Spithids,,,,,,,,,,,,,,,,

	//	Zakir,51
	Barak(BaseGameStarSystem.Zakir, BaseGameCapitalType.None,  
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled,
			BaseGameEnviron.urban(5)
			   .resources(10)
//			   .races(BaseGameRaceType.Jopers)	// Non-starfaring race
			   .creature(BaseGameCreature.Hysnatons)
			   .sovereign(BaseGameSovereign.Inzenzia_III)
			   .build()),
	Liatris(BaseGameStarSystem.Zakir, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.wild(5)
			   .resources(7)
//			   .races(BaseGameRaceType.Ardorats)	// Non-starfaring race
			   .creature(BaseGameCreature.Garb)
			   .coupRating(1)
			   .build()),
	Xan(BaseGameStarSystem.Zakir, BaseGameCapitalType.None, BaseGameRaceType.Xanthon,
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(3)
			   .resources(6)
//			   .races(BaseGameRaceType.Orthotins)	// Non-starfaring race
			   .build(),
			BaseGameEnviron.fire(5)
			   .resources(4)
			   .races(BaseGameRaceType.Xanthon)
			   .creature(BaseGameCreature.Mish)
			   .build()),
	//	Barak,511,,0,-3,,,Urban,5,10,,,Jopers,Hysnatons,Inzenzia III (r),,,,,,,,,,,,,,,
	//	Liatris,512,,2,0,,TRUE,Wild,5,7,,1,Ardorats ,Gachs,,,,,,,,,,,,,,,,
	//	Xan,513,Xanathons,1,-1,,,Urban,3,6,,,Orthontins,,,Fire,5,4,,,Xanthons,Mish,,,,,,,,

	//	Eudox,52
	Aras(BaseGameStarSystem.Eudox, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.urban(4)
			   .resources(9)
			   .starResources()
			   .races(BaseGameRaceType.Segunden)
			   .creature(BaseGameCreature.Chardireeds)
			   .sovereign(BaseGameSovereign.Tensok_Phi)
			   .build()),
	Capilax(BaseGameStarSystem.Eudox, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Dissent, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.fire(4)
			   .resources(3)
			   .races(BaseGameRaceType.Xanthon)
			   .creature(BaseGameCreature.Onflam)
			   .build()),
	Adrax(BaseGameStarSystem.Eudox, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.subterranian(2)
			   .resources(4)
			   .races(BaseGameRaceType.Rhone)
			   .coupRating(2)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(5)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Thinagig)
			   .build()),
	//	Aras,521,,1,0,,TRUE,Urban,4,9,TRUE,,Segundians ,Chardireeds,Tensok-Phi (n),,,,,,,,,,,,,,,
	//	Capilax,522,,1,-1,,TRUE,Fire,4,3,,,Xanthons,Onflams,,,,,,,,,,,,,,,,
	//	Adrax,523,,2,1,,,Subterranian ,2,4,,2,Rhones,,,Wild,3,5,TRUE,,Rhones,Thinagigs,,,,,,,,

	//	Corusa,53
	Scythia(BaseGameStarSystem.Corusa, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Loyal, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.RebelControlled, true,
			BaseGameEnviron.fire(4)
			   .resources(3)
			   .races(BaseGameRaceType.Xanthon)
			   .build()),
	//	Scythia,531,,1,-3,,TRUE,Fire,4,3,,,Xanthons,,,,,,,,,,,,,,,,,

	//	Irajeba,54
	Annell(BaseGameStarSystem.Irajeba, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Neutral, BaseGameLoyaltyType.Unrest, BaseGamePlanetaryControlType.ImperialControlled, true,
			BaseGameEnviron.air(4)
			   .resources(8)
			   .starResources()
//			   .races(BaseGameRaceType.Cavalkus)	// Non-starfaring race
			   .creature(BaseGameCreature.Fog)
			   .sovereign(BaseGameSovereign.Darb_Selesh)
			   .build()),
	Trov(BaseGameStarSystem.Irajeba, BaseGameCapitalType.Provincial, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Patriotic, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.urban(4)
			   .resources(6)
			   .starResources()
			   .races(BaseGameRaceType.Rhone)
			   .creature(BaseGameCreature.Valaterix)
			   .build(),
			BaseGameEnviron.wild(3)
			   .resources(4)
			   .races(BaseGameRaceType.Rhone)
			   .build()),
	//	Annell,541,,0,-2,,TRUE,Air,4,8,,,Cavalkus,Fog,Darn Salesh (n),,,,,,,,,,,,,,,
	//	Trov,542,,2,2,Provincial,,Urban,4,6,TRUE,,Rhones,Valaterix,,Wild,3,4,,,Rhones,,,,,,,,,

	//	Moda,55
	Niconi(BaseGameStarSystem.Moda, BaseGameCapitalType.None, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Neutral, BaseGamePlanetaryControlType.ImperialControlled,
			BaseGameEnviron.wild(4)
			   .resources(5)
			   .races(BaseGameRaceType.Kayn)
			   .creature(BaseGameCreature.Wyths)
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

	public LoyaltyType getCurrentLoyalty() {
		return currentLoyalty;
	}

	public void setCurrentLoyalty(LoyaltyType currentLoyalty) {
		this.currentLoyalty = currentLoyalty;
	}

	public String getName() {
		return this.toString().replace('_', ' ');
	}

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

	public BaseGameCapitalType getCapitalType() {
		return capitalType;
	}

	public List<BaseGameEnviron> getEnvirons() {
		return environs;
	}

	public Optional<BaseGameRaceType> getHomeworld() {
		return homeworld;
	}

	public BaseGamePlanetaryControlType getControlA() {
		return controlA;
	}

	public LoyaltyType getStartingLoyaltyS() {
		return startingLoyaltyS;
	}

	public LoyaltyType getStartingLoyaltyA() {
		return startingLoyaltyA;
	}

	public BaseGamePlanetaryControlType getCurrentControl() {
		return currentControl;
	}

	public void setCurrentControl(BaseGamePlanetaryControlType currentControl) {
		this.currentControl = currentControl;
	}

	public BaseGameStarSystem getStarSystem() {
		return starSystem;
	}

	public boolean hasSecret() {
		return hasSecret;
	}
}
