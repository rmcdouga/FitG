package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import com.rogers.rmcdouga.fitg.basegame.BaseGameCreature;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;
import com.rogers.rmcdouga.fitg.basegame.BaseGameSovereign;
import com.rogers.rmcdouga.fitg.basegame.RaceType;

import java.util.Arrays;
import java.util.Collections;

public enum BaseGamePlanet {
	Mimulus(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, BaseGameRaceType.Kayn, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			List.of(BaseGameEnviron.of(BaseGameEnvironType.Wild, 4, 4, true, 0, List.of(BaseGameRaceType.Kayn), null, null))),
	Magro(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, null, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			Collections.emptyList()),
	Fliad(BaseGameStarSystem.Tardyn, BaseGameCapitalType.None, null, 
			BaseGameLoyaltyType.Patriotic, BaseGameLoyaltyType.Loyal, BaseGamePlanetaryControlType.ImperialControlled,
			Collections.emptyList());
	
/*
﻿Name,Id,Home Race,S,A,Capital,Secret,Environ1,Size,Resources,StarResources,Coup Rating,Races,Creature,Sovereign,Environ2,Size,Resources,StarResources,Coup Rating,Race,Creature,Sovereign,Environ3,Size,Resources,StarResources,Coup Rating,Race,Creature
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
	private final List<Environ> environs;
	private final RaceType homeworld;
	private final LoyaltyType startingLoyaltyS;
	private final LoyaltyType startingLoyaltyA;
	private final BaseGamePlanetaryControlType controlA;

	private LoyaltyType currentLoyalty;
	private BaseGamePlanetaryControlType currentControl;

	/*
	﻿Home Race,S,A,Capital,Secret,Environ1,Size,Resources,StarResources,Coup Rating,Races,Creature,Sovereign,Environ2,Size,Resources,StarResources,Coup Rating,Race,Creature,Sovereign,Environ3,Size,Resources,StarResources,Coup Rating,Race,Creature
	 */
	private BaseGamePlanet(BaseGameStarSystem starSystem, BaseGameCapitalType capitalType, BaseGameRaceType homeworld,
			BaseGameLoyaltyType startingLoyaltyS, BaseGameLoyaltyType startingLoyaltyA, BaseGamePlanetaryControlType controlA,
			List<Environ> environs) {
		this.starSystem = starSystem;
		this.capitalType = capitalType;
		this.homeworld = homeworld;
		this.controlA = controlA;
		this.startingLoyaltyS = startingLoyaltyS;
		this.startingLoyaltyA = startingLoyaltyA;
		this.environs = List.copyOf(environs);
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

	public List<Environ> getEnvirons() {
		return environs;
	}

	public RaceType getHomeworld() {
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
	
}
