package com.rogers.rmcdouga.fitg.basegame.units;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType.*;
import static com.rogers.rmcdouga.fitg.basegame.BaseGameMission.*;
import static com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static com.rogers.rmcdouga.fitg.basegame.units.Character.SpecialAbility.*;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;
import com.rogers.rmcdouga.fitg.basegame.Card;
import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

public enum BaseGameCharacter implements Card, Character {
	Zina_Adora(Builder.character(1, REBEL, Rhone, "Princess of Adare")
			.combat(1)
			.endurance(2)
			.intelligence(3)
			.leadership(2)
			.diplomacy(2)
			.navigation(1)
			.homePlanet(Adare)
			.bonusDraw(GATHER_INFORMATION, 1)
			.description(
					"""
					""")
			.build()
			),
	Adam_Starlight(Builder.character(2, REBEL, Rhone)
			.combat(3)
			.endurance(4)
			.intelligence(2)
			.leadership(1)
			.spaceLeadership(1)
			.diplomacy(0)
			.navigation(4)
			.homePlanet(Liomax)
			.description(
					"""
					""")
			.build()
			),
	Agan_Rafa(Builder.character(3, REBEL, Rhone)
			.combat(5)
			.endurance(6)
			.intelligence(1)
			.leadership(2)
			.diplomacy(0)
			.navigation(3)
			.homePlanet(Pronox)
			.description(
					"""
					""")
			.bonusDraw(GATHER_INFORMATION, 1)
			.bonusDraw(ASSASINATION, 2)
			.build()
			),
	Frun_Sentel(Builder.character(4, REBEL, Xanthon, "Prince of Xan")
			.combat(5)
			.endurance(5)
			.intelligence(1)
			.leadership(1)
			.diplomacy(1)
			.navigation(3)
			.homePlanet(Xan)
			.description(
					"""
					""")
			.bonusDraw(START_REBEL_CAMP, 1)
			.bonusDraw(ASSASINATION, 2)
			.build()
			),
	Odene_Hobar(Builder.character(5, REBEL, Leonid, "Prince of Heliax")
			.combat(3)
			.endurance(4)
			.intelligence(3)
			.leadership(2)
			.diplomacy(2)
			.navigation(2)
			.homePlanet(Heliax)
			.description(
					"""
					""")
			.build()
			),
	Ly_Mantok(Builder.character(6, REBEL, Saurian)
			.combat(5)
			.endurance(4)
			.intelligence(4)
			.leadership(3)
			.diplomacy(1)
			.navigation(5)
			.homePlanet(Jura)
			.description(
					"""
					""")
			.bonusDraw(SUBVERT_TROOPS, 2)
			.specialAbility(OWNS_THE_EXPLORER)
			.build()
			),
	Vudot_Vodot(Builder.character(7, REBEL, Suvan)
			.combat(1)
			.endurance(2)
			.intelligence(4)
			.leadership(2)
			.diplomacy(4)
			.navigation(0)
			.homePlanet(Akubera)
			.description(
					"""
					""")
			.bonusDraw(COUP, 2)
			.bonusDraw(SUMMON_SOVEREIGN, 2)
			.build()
			),
	Ran_Jayma(Builder.character(8, REBEL, Piorad)
			.combat(4)
			.endurance(5)
			.intelligence(2)
			.leadership(1)
			.diplomacy(0)
			.navigation(5)
			.homePlanet(Ownex)
			.description(
					"""
					""")
			.bonusDraw(ASSASINATION, 1)
			.build()
			),

	;

	private record CharacterData (
			int cardNumber, 
			Faction allegience,
			RaceType race,
			Optional<String> title,
			int combat,
			int endurance,
			int intelligence,
			int leadership,
			OptionalInt spaceLeadership,
			int diplomacy,
			int navigation,
			BaseGamePlanet homePlanet,
			Map<? extends Mission, Integer> bonusDraws,
			String description,
			Set<? extends SpecialAbility> specialAbilities
			) {} ;
	
	private final CharacterData characterData;
	
	private BaseGameCharacter(CharacterData characterData) {
		this.characterData = characterData;
	}
	

	@Override
	public int combat() {
		return characterData.combat;
	}

	@Override
	public int endurance() {
		return characterData.endurance;
	}

	@Override
	public int intelligence() {
		return characterData.intelligence;
	}

	@Override
	public int leadership() {
		return characterData.leadership;
	}

	@Override
	public OptionalInt spaceLeadership() {
		return characterData.spaceLeadership;
	}

	@Override
	public int diplomacy() {
		return characterData.diplomacy;
	}

	@Override
	public int navigation() {
		return characterData.navigation;
	}

	@Override
	public RaceType race() {
		return characterData.race;
	}

	@Override
	public BaseGamePlanet homePlanet() {
		return characterData.homePlanet;
	}

	@Override
	public Faction allegience() {
		return characterData.allegience;
	}

	@Override
	public int bonusDraws(Mission mission) {
		return characterData.bonusDraws.get(mission);
	}

	@Override
	public boolean hasSpecialAbility(SpecialAbility specialAbility) {
		return characterData.specialAbilities().contains(specialAbility);
	}


	@Override
	public String descriptions() {
		return characterData.description();
	}

	@Override
	public int cardNumber() {
		return characterData.cardNumber;
	}

	private static class Builder {
		private final Integer cardNumber; 
		private final Faction allegience;
		private final RaceType race;
		private final Optional<String> title;
		private Integer combat;
		private Integer endurance;
		private Integer intelligence;
		private Integer leadership;
		private Integer spaceLeadership;
		private Integer diplomacy;
		private Integer navigation;
		private BaseGamePlanet homePlanet;
		private Map<BaseGameMission,Integer> bonusDraws = new EnumMap<BaseGameMission, Integer>(BaseGameMission.class);
		private String description;
		private Set<SpecialAbility> specialAbilities = EnumSet.noneOf(SpecialAbility.class);
		
		public Builder(Integer cardNumber, Faction allegience, RaceType race) {
			super();
			this.cardNumber = cardNumber;
			this.allegience = allegience;
			this.race = race;
			this.title = Optional.empty();
		}

		public Builder(Integer cardNumber, Faction allegience, RaceType race, String title) {
			super();
			this.cardNumber = cardNumber;
			this.allegience = allegience;
			this.race = race;
			this.title = Optional.of(title);
		}

		public Builder combat(int combat) {
			this.combat = combat;
			return this;
		}
		public Builder endurance(int endurance) {
			this.endurance = endurance;
			return this;
		}
		public Builder intelligence(int intelligence) {
			this.intelligence = intelligence;
			return this;
		}
		public Builder leadership(int leadership) {
			this.leadership = leadership;
			return this;
		}
		public Builder spaceLeadership(int spaceLeadership) {
			this.spaceLeadership = spaceLeadership;
			return this;
		}
		public Builder diplomacy(int diplomacy) {
			this.diplomacy = diplomacy;
			return this;
		}
		public Builder navigation(int navigation) {
			this.navigation = navigation;
			return this;
		}
		public Builder homePlanet(BaseGamePlanet homePlanet) {
			this.homePlanet = homePlanet;
			return this;
		}
		public Builder bonusDraw(BaseGameMission mission, Integer numDraws) {
			Integer existing = this.bonusDraws.put(mission, numDraws);
			if (existing != null) {
				throw new IllegalArgumentException("Can't add the same mission twice (" + existing + ").");
			}
			return this;
		}
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		public Builder specialAbility(SpecialAbility specialAbility) {
			if (!this.specialAbilities.add(specialAbility)) {
				throw new IllegalArgumentException("Can't add the same special ability twice (" + specialAbility + ").");
			}
			return this;
		}

		public CharacterData build() {
			return new CharacterData(
					Objects.requireNonNull(this.cardNumber, "CardNumber must be populated"),
					Objects.requireNonNull(this.allegience, "Allegience must be populated"),
					Objects.requireNonNull(this.race, "Race must be populated"),
					Objects.requireNonNull(this.title, "Title must be populated"),
					Objects.requireNonNull(this.combat, "Combat must be populated"),
					Objects.requireNonNull(this.endurance, "Endurance must be populated"),
					Objects.requireNonNull(this.intelligence, "Intelligence must be populated"),
					Objects.requireNonNull(this.leadership, "Leadership must be populated"),
					this.spaceLeadership != null ? OptionalInt.of(this.spaceLeadership) : OptionalInt.empty(),
					Objects.requireNonNull(this.diplomacy, "Diplomacy must be populated"),
					Objects.requireNonNull(this.navigation, "Navigation must be populated"),
					Objects.requireNonNull(this.homePlanet, "HomePlanet must be populated"),
					Objects.requireNonNull(Collections.unmodifiableMap(this.bonusDraws), "BonusDraws must not be null."),
					Objects.requireNonNull(this.description, "Description must be populated"),
					Objects.requireNonNull(Collections.unmodifiableSet(this.specialAbilities), "Special Abilities must not be null.")
					);
					
		}

		public static Builder character(int cardNumber, Faction faction, RaceType raceType) {
			return new Builder(cardNumber,faction, raceType);
		}

		public static Builder character(int cardNumber, Faction faction, RaceType raceType, String title) {
			return new Builder(cardNumber,faction, raceType, title);
		}
		
	}
}
