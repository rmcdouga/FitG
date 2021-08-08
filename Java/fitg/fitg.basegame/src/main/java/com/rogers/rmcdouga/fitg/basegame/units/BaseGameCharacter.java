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
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;
import com.rogers.rmcdouga.fitg.basegame.Card;
import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.BaseGameRaceType;

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
					The once influential Queen of Adare fled her home planet when she supported her people's side in \
					several conflicts between the Empire and Adare, and the Empire responded by forcibly setting up an \
					Imperial puppet regime. Zina Adora joined the Rebel cause to try to regain her throne, but since then \
					she has expressed the desire to give up her chance at rulership to be with Rayner Derban.\
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
					Like most people, Adam never thought much about the Empire, he just lived his life the best \
					he could and put the occasional oppression down to the toughness of life. But he could not \
					ignore the slaying of his entire family, when Imperial troops who were going to interrogate \
					the Starlink family tortured the Starlight family by mistake. His desire for vengeance burned \
					so strongly that even the Rebels thought his hate for the Empire was unusually great.\
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
					While for centuries the Empire had hired the Pronoxian Mercenaries to do their dirty work for them \
					(mainly, establishing beachheads on unknown planets), the Empire decided that they were too great a \
					security risk and gave them their severance. Fighting was the only job Agan Rafa had known, \
					and if the Empire did not want him, he would find someone who would.\
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
					Sentel was the first Royal personage to formally come out in public and state his support for the Rebel cause. \
					He had counted on the support of his people to protect him personally from the Empire, but he underestimated \
					the power of the Imperial press coupled with skilled semanticists, and he was forced to flee the planet Xan, or be lynched.\
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
					Odel Hobar could not believe what had happened to his son, Odene. He sends his son off to get a good look at the Empire, \
					and he comes back and tells his father, Sovereign of Heliax, this nonsense about rebelling against the Empire! \
					Furious, Odel excommunicated Odene until he settled down and was willing to accept the responsibility of \
					being ruler of a planet and not believe such flighty nonsense.\
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
					An Imperial Sub-Commander is not supposed to have any out¬side business concerns, but this is a rarely enforced policy. \
					Ly Mantok would no doubt have gotten away with his corrupt dealings, had not ten thousand Mantok Laser Rifle; \
					refused to function in the middle of the Battle of Banjukai. When Mantok was formerly dismissed, \
					he swore that he would go to someone who would appreciate his abilities The Rebels, at the time, \
					were desperate enough to do just that.\
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
					The powerful ability for oration and the piercing, some say hypnotic, eyes of Vudot Vodot made him \
					very influential throughout the Empire, and on Akubera, where he was a planetary senator with a good \
					shot for Planetary Sovereign. But the Empire's views on many matters did not match those of Vodot's, \
					and a political scandal forced him out of office. Vudot Vodot did not particularly feel strongly about \
					the Empire, but if they thought he was an enemy, well, they'd get one!\
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
					It was quite a long time before the Rebel leaders would allow Ran Jayma, the notorious space pirate, \
					to take the Rebel oath. But gradually they came to realize that even a pirate could tell right from wrong \
					and see that the Empire had to be destroyed. Actually, Jayma was only being practical — if the Empire \
					kept taxing 90% of everything, there would be that much less for his take.\
					""")
			.bonusDraw(ASSASINATION, 1)
			.build()
			),
	Tourag(Builder.character(9, REBEL, Rhone)
			.combat(3)
			.endurance(3)
			.intelligence(2)
			.leadership(1)
			.diplomacy(2)
			.navigation(4)
			.homePlanet(Diomas)
			.description(
					"""
					The Traders had traditionally been a free people, and even the Empire did not tax them strongly. \
					But the Empire felt that the Traders were providing transportation for Rebel men and supplies, \
					and their spaceships were confiscated. Faced with the prospect of having to earn an honest living, \
					Tourag decided that joining the Rebels might be better for business in the long run instead.\
					""")
			.bonusDraw(SUBVERT_TROOPS, 1)
			.bonusDraw(SCAVENGE, 1)
			.specialAbility(OWNS_THE_SOLAR_MERCHANT)
			.build()
			),
	Rayner_Derban(Builder.character(10, REBEL, Rhone)
			.combat(5)
			.endurance(5)
			.intelligence(4)
			.leadership(4)
			.spaceLeadership(1)
			.diplomacy(3)
			.navigation(4)
			.homePlanet(Orlog)
			.description(
					"""
					Before Redjac became the Leader of the Imperial Knights, the Most Senior of the Knights was a mysterious, \
					ever-masked person who always championed the cause of the people. The "Masked Knight" was forced to flee \
					due to charges of treason by Redjac, and disappeared. When Rayner Derban joined the Rebels, some claimed \
					to see the stance of the Masked Knight in Derban's stance, to hear the voice of the Knight in Derban's voice. \
					But Derban will say nothing of his past. Rayner Derban is one of few who will dare to pass Kogus, \
					the Kayn bodyguard, to see Zina Adora.\
					""")
			.bonusDraw(SABOTAGE, 2)
			.build()
			),
	Kogus(Builder.character(11, REBEL, Kayn)
			.combat(5)
			.endurance(5)
			.intelligence(2)
			.leadership(0)
			.diplomacy(0)
			.navigation(3)
			.homePlanet(Niconi)
			.description(
					"""
					Since the time of the Kayn Mutiny, the family of Kogus had guarded the royalty of many planets faithfully, \
					never abandoning their post. And so Kogus had guarded the Queen of Adare, Zina Adora, despite her exile. \
					There are few who would dare the wrath of Kogus to visit Zina Adora — but Rayner Derban is one of those few.\
					""")
			.specialAbility(INCREASE_ABILTIIES_WITH_ZINA_ADORA)
			.build()
			),
	Doctor_Sontag(Builder.character(12, REBEL, Rhone)
			.combat(2)
			.endurance(2)
			.intelligence(4)
			.leadership(1)
			.diplomacy(3)
			.navigation(0)
			.homePlanet(Orlog)
			.description(
					"""
					The Planetary Stabilizer was a remarkable device — it could suddenly halt the rotation of a planet, \
					causing its outer crust to flake off totally and fly into space, utterly destroying the biosphere but \
					leaving the planet for Imperial terraforming. The inventor of the Stabilizer, Dr. Sontag, became so \
					disgusted with himself for inventing such a device of mass destruction that he joined the Rebels \
					to ease his tortured conscience.\
					""")
			.bonusDraw(GATHER_INFORMATION, 2)
			.specialAbility(HEAL_CHARACTERS)
			.build()
			),
	Scott_Rubel(Builder.character(13, REBEL, Rhone)
			.combat(3)
			.endurance(4)
			.intelligence(1)
			.leadership(1)
			.diplomacy(0)
			.navigation(5)
			.homePlanet(Adrax)
			.description(
					"""
					Action! Adventure! Danger! The imagination of the young Scott Rubel had traveled the length and breadth \
					of the universe, fighting and blasting his way to glory. As soon as he came of age, he joined the \
					Rebel cause, hoping to have the excitement that he could otherwise only dream about. \
					Fate works in mysterious ways, and shortly thereafter Rubel indeed became a Rebel hero by \
					single-handedly saving a small village of locals from being razed by Imperial patrols.\
					""")
			.build()
			),
	Boccanegra(Builder.character(14, REBEL, Piorad)
			.combat(5)
			.endurance(5)
			.intelligence(2)
			.leadership(2)
			.spaceLeadership(1)
			.diplomacy(0)
			.navigation(4)
			.homePlanet(Ayod)
			.description(
					"""
					The Piorad Space Vikings, as they called themselves, were a group of hardy explorers who were accustomed \
					to exploring the areas of the Galaxy outside the boundaries of the Empire, bringing back treasures and \
					riches from strange places. Unfortunately, the Space Vikings did not pay a high enough percentage of \
					their riches to the Imperial coffers, and the majority of them were arrested and executed as space pirates. \
					Boccanegra, the only one to slip through the Imperial fingers, swore revenge on the Empire for his friends.\
					""")
			.specialAbility(ADD_ONE_TO_HIDING_VALUE)
			.specialAbility(OWNS_PLANETARY_PRIVATEER)
			.build()
			),
	Drakir_Grebb(Builder.character(15, REBEL, Calma, "Prince of Suti")
			.combat(3)
			.endurance(4)
			.intelligence(3)
			.leadership(1)
			.diplomacy(1)
			.navigation(2)
			.homePlanet(Suti)
			.description(
					"""
					The Calma are a mild, passive race, but when one of them gets angry it can be years before \
					he will cool down and forgive. When the Empire looked the other way while poachers hunted \
					Calma for the precious metals within their gizzards, Drakir Grebb, Prince of Suti, himself \
					went to Orlog to protest. When he barely escaped with his life, he vowed the Empire would \
					never oppress his people again.\
					""")
			.build()
			),
	Yarro_Latac(Builder.character(16, REBEL, Theshian)
			.combat(2)
			.endurance(3)
			.intelligence(3)
			.leadership(1)
			.diplomacy(0)
			.navigation(4)
			.homePlanet(Rhexia)
			.description(
					"""
					An article in an obscure scientific journal noted that "despite many technological advances today, \
					the Empire still has not come close to the level of technology possessed by the Interstellar Concordance." \
					As a result of this simple sentence, the Master Technician Yarro Latac was so hounded by Imperial \
					persecution that he joined the Rebels just so he'd be able to finish his research.\
					""")
			.specialAbility(REPAIRS_SPACESHIPS_AND_POSSESSIONS)
			.specialAbility(IGNORE_SENTRY_ROBOTS)
			.build()
			),
	Professor_Mareg(Builder.character(17, REBEL, Illias)
			.combat(3)
			.endurance(4)
			.intelligence(4)
			.leadership(0)
			.diplomacy(0)
			.navigation(2)
			.homePlanet(Cercis)
			.description(
					"""
					There are few experts in the science of Galactography, and Mareg is one of them. There was little about \
					the Empire and its organization that he did not know. Unfortunately, he did not regard his knowledge as \
					secret, since most of it was a matter of public record anyway. But the Empire did, and forced the ruin \
					of his academic career. The Rebels, anxious to make use of his knowledge, convinced him to join, \
					promising him a professorship at any university he desired if they won.\
					""")
			.specialAbility(IGNORE_FIRST_CREATURE_ATTACKS_IN_NON_URBAN)
			.specialAbility(REVEAL_PLANET_SECRET)
			.build()
			),
	Oneste_Woada(Builder.character(18, REBEL, Henone)
			.combat(0)
			.endurance(3)
			.intelligence(3)
			.leadership(0)
			.diplomacy(3)
			.navigation(1)
			.homePlanet(Horon)
			.description(
					"""
					"Do not take it upon yourself to take away the most precious gift one has from another... \
					Killing is the resort of the foolish, or the cruel." Woada's words influenced a great many \
					in the Empire, including many Imperial soldiers. Woada was a troublemaker anyway, so he was \
					repressed as a matter of course. As Woada began to realize what the Empire was trying to do \
					to him, his sermons began to have a stronger anti-Empire sentiment, and he joined the Rebel \
					cause to end the suffering under the Imperial hand.\
					""")
			.specialAbility(IGNORE_IRATE_LOCALS)
			.build()
			),
	Sidir_Ganang(Builder.character(19, REBEL, Segunden)
			.combat(3)
			.endurance(3)
			.intelligence(3)
			.leadership(0)
			.diplomacy(1)
			.navigation(3)
			.homePlanet(Bajukai)
			.description(
					"""
					"Sidir Ganang and the Ganang Gang" was one of the most popular stereovision shows on Bajukai, \
					and Sidir Ganang posters, dolls, books, movies and grebble-gum cards made him a millionaire. \
					But his fortune tugged at the greed of some minor Imperial functionary, and Sidir Ganang was \
					blacklisted from the entertain ment business, and his fortune was confiscated. \
					Formerly, Ganang had merely protrayed galactic warriors on stereovision; now he actually became one, \
					fighting against the Empire.\
					""")
			.bonusDraw(COUP, 1)
			.bonusDraw(START_REBEL_CAMP, 1)
			.build()
			),
	Bridne_Murcada(Builder.character(20, REBEL, Ursi)
			.combat(3)
			.endurance(3)
			.intelligence(4)
			.leadership(0)
			.diplomacy(0)
			.navigation(3)
			.homePlanet(Lysenda)
			.description(
					"""
					For some, the art of unarmed combat is just a hobby, but for Bridne Murcada it was her whole life. \
					For years she was a chief drill instructor, teaching unarmed combat and stealth to green Imperial soldiers, \
					but she was dismissed when a jealous colleague revealed that she was not teaching them strictly by the book, \
					despite the effectiveness of her methods. Murcada joined the Rebels because it was the only way she could \
					put her abilities to the best use.\
					""")
			.bonusDraw(ASSASINATION, 3)
			.specialAbility(ADD_TWO_TO_HIDING_VALUE)
			.build()
			),
	Thysa_Kymbo(Builder.character(21, IMPERIAL, Rhone, "Princess of Orlog")
			.combat(1)
			.endurance(2)
			.intelligence(3)
			.leadership(1)
			.spaceLeadership(1)
			.diplomacy(1)
			.navigation(0)
			.homePlanet(Orlog)
			.description(
					"""
					Daughter of the current Emperor Coreguya, the princess has spent most of her adult life waiting for her father \
					to die, so that she may ascend to the throne. Because she has spent most of her life pampered in the \
					Imperial Court, she is unaware that Redjac may have other plans for the throne that do not involve succession. \
					The princess became the bitter enemy of Zina Adora when she learned that Rayner Derban was more attracted to \
					Zina than to herself.\
					""")
			.bonusDraw(GATHER_INFORMATION, 2)
			.bonusDraw(COUP, 1)
			.bonusDraw(SUMMON_SOVEREIGN, 2)
			.build()
			),
	Barca(Builder.character(22, IMPERIAL, Kayn)
			.combat(5)
			.endurance(4)
			.intelligence(4)
			.leadership(4)
			.spaceLeadership(3)
			.diplomacy(1)
			.navigation(4)
			.homePlanet(Kalgar)
			.description(
					"""
					Like all Kayns, Barca has a fierce loyalty for his friends and little mercy toward his enemies. \
					For 40 years, Barca has been the Grand Marshal of the Imperial Army, both on planet and in space. \
					His remarkable military prowess and ability to handle tactical and strategic combat situations is \
					at the disposal of the Empire, as Barca's loyalties remain fixed to the Imperial throne and whoever sits upon it.\
					""")
			.bonusDraw(SABOTAGE, 2)
			.build()
			),
	Saytar(Builder.character(23, IMPERIAL, BaseGameRaceType.Charkhans)
			.combat(4)
			.endurance(4)
			.intelligence(4)
			.leadership(2)
			.spaceLeadership(2)
			.diplomacy(1)
			.navigation(3)
			.homePlanet(BaseGamePlanet.Charkhan)
			.description(
					"""
					As a sub-commander of the Imperial armed forces, Saytar holds a rank just under Barca's in importance. \
					Dedicated to the causes of battle and the Imperium (in that order) with a fervor beyond that which is \
					normal for even a man in such a position, many believe Saytar will soon receive total control of the military, \
					if he does not have it de facto already. Others feel his blind ambition will be his downfall.\
					""")
			.build()
			),
	Telmen(Builder.character(24, IMPERIAL, Rhone)
			.combat(4)
			.endurance(4)
			.intelligence(3)
			.leadership(3)
			.spaceLeadership(2)
			.diplomacy(0)
			.navigation(4)
			.homePlanet(Tsipa)
			.description(
					"""
					The second Imperial Sub-Commander, Telman is little more than a hometown boy who made it good. \
					Coming from the backwaters of Tsipa and becoming popular and distinguished in several battles, \
					Telman was promoted to his current rank essentially to get him out of the way. In his current job, \
					he feels so inexperienced that it is rare for him to show any initiative without Saytar looking over his shoulder.\
					""")
			.build()
			),
	Jon_Kidu(Builder.character(25, IMPERIAL, Saurian, "Governor of Chim")
			.combat(3)
			.endurance(4)
			.intelligence(3)
			.leadership(3)
			.spaceLeadership(1)
			.diplomacy(1)
			.navigation(1)
			.homePlanet(Chim, Squamot)
			.description(
					"""
					Kidu has both the job of Im¬perial Lieutenant Governor and Head of the Imperial Intelligence Service. \
					In both positions, Jon Kidu is coolly efficient, following his orders to the letter, and letting no \
					creature, no matter what his allegiance, stand in the way of his actions. As a result, Jon Kidu has \
					become a name synonymous with fear throughout the entire Empire.\
					""")
			.bonusDraw(ASSASINATION, 1)
			.bonusDraw(COUP, 1)
			.build()
			),
	Gelba(Builder.character(26, IMPERIAL, Rhone, "Governor of Trov")
			.combat(3)
			.endurance(3)
			.intelligence(2)
			.leadership(3)
			.spaceLeadership(3)
			.diplomacy(2)
			.navigation(1)
			.homePlanet(Trov, Adrax)
			.description(
					"""
					One of the Lieutenant Governors of the Empire. While the post of Lieutenant Governor was once most influential \
					in the Imperial ear, since the mechanizations of Redjac the job has degenerated into speech-making and paper-filing. \
					Gelba has become quite bitter, since he spent 30 years of his life trying to claw his way up to the top, \
					only to find he had clawed his way to the bottom.\
					""")
			.bonusDraw(COUP, 1)
			.build()
			),
	Senator_Dermond(Builder.character(27, IMPERIAL, Rhone)
			.combat(2)
			.endurance(2)
			.intelligence(4)
			.leadership(0)
			.diplomacy(4)
			.navigation(0)
			.homePlanet(Diomas)
			.description(
					"""
					The Imperial Senate would be little more than a joke were it not for Dermond, the only Imperial politician \
					to have any influence on the Emperor at all. Dermond is the only person in the Imperial government who listens \
					to the voices of the peoples of the galaxy, and is very popular in consequence. But although he does not care \
					for the current Imperial policies, he is in favor of slow change and is as anti-Rebel as any hardened Imperial commander.\
					""")
			.bonusDraw(COUP, 2)
			.build()
			),
	Redjac(Builder.character(28, IMPERIAL, Rhone, "Knight of the Empire")
			.combat(6)
			.endurance(6)
			.intelligence(3)
			.leadership(2)
			.spaceLeadership(2)
			.diplomacy(1)
			.navigation(5)
			.homePlanet(Magro, Diomas, Orlog)
			.description(
					"""
					By making charges that the former Most Senior of the Imperial Knights was guilty of treason, \
					Redjac became the Leader of the Imperial Knights. From the time of his youth on the planet Magro, \
					and his rise through the Imperial ranks on Diomas, Redjac's life has been one of ambition, plotting \
					and taking. It is believed by many that Redjac's next conquest will be the Imperial throne itself.\
					""")
			.bonusDraw(ASSASINATION, 1)
			.bonusDraw(SABOTAGE, 1)
			.bonusDraw(SUMMON_SOVEREIGN, 1)
			.build()
			),
	Jin_Voles(Builder.character(29, IMPERIAL, Segunden, "Knight of the Empire")
			.combat(5)
			.endurance(5)
			.intelligence(2)
			.leadership(1)
			.spaceLeadership(1)
			.diplomacy(0)
			.navigation(5)
			.homePlanet(Tiglyf)
			.description(
					"""
					The most well known of the Imperial Knights, Jin Voles is the only Knight who matches the ideal of \
					what most people feel an Imperial Knight should be a dashing, heroic figure dedicated to the welfare \
					of the Empire. However, lin Voles will not stray one millimeter outside of his prerogative as an \
					Imperial Knight, and therefore does not seem to be aware of the corruption of the Empire that surrounds him.\
					""")
			.bonusDraw(ASSASINATION, 1)
			.build()
			),
	Vans_Ka_Tie_A(Builder.character(30, IMPERIAL, Rhone, "Knight of the Empire")
			.combat(4)
			.endurance(5)
			.intelligence(2)
			.leadership(1)
			.spaceLeadership(1)
			.diplomacy(1)
			.navigation(4)
			.homePlanet(Lonica)
			.description(
					"""
					The oldest member of the Imperial Knights, Vans Ka-Tie-A still remembers the days when \
					the Imperial Knights were respected by all, and were a force that not only kept the Emperor safe but \
					maintained peace and prosperity throughout the galaxy. But those days are gone, and with them the \
					respect that Ka-Tie-A had from the other Imperial Knights, most of whom regard him as a senile old fool.\
					""")
			.bonusDraw(GATHER_INFORMATION, 1)
			.build()
			),
	Els_Taroff(Builder.character(31, IMPERIAL, Rhone, "Knight of the Empire")
			.combat(4)
			.endurance(5)
			.intelligence(3)
			.leadership(1)
			.spaceLeadership(1)
			.diplomacy(0)
			.navigation(5)
			.homePlanet(Liomax)
			.description(
					"""
					Whenever Redjac has a secret meeting, or is going over his tangled plots and intrigues, it is rare \
					for Els Taroff not to be at his side. When Els Taroff first became an Imperial Knight, he was noted \
					for his independence and his courage. But as time passed, he gradually became little more than an \
					extension of Redjac, not daring to breathe unless Redjac so orders.\
					""")
			.bonusDraw(SABOTAGE, 1)
			.build()
			),
	Emperor_Coreguya(Builder.character(32, IMPERIAL, Rhone, "King of Orlog")
			.combat(2)
			.endurance(3)
			.intelligence(2)
			.leadership(2)
			.spaceLeadership(1)
			.diplomacy(2)
			.navigation(1)
			.homePlanet(Orlog)
			.description(
					"""
					Coreguya was chosen to be the next Emperor by Maxtross II, who is believed in turn to have been influenced by \
					Redjac, who wanted a weak Emper¬or on the Imperial throne. If so, Redjac could not have made a better choice. \
					Despite the immense power wielded by one who sits on the throne at Orlog, \
					Coreguya has remained content to indulge in the luxuries of the Imperial Palace while allowing all decisions \
					to fall on Redjac's shoulders.\
					""")
			.bonusDraw(COUP, 1)
			.bonusDraw(SUMMON_SOVEREIGN, 3)
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
			Set<? extends Planet> homePlanet,
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
	public boolean isHomePlanet(Planet planet) {
		return characterData.homePlanet.contains(planet);
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
		return characterData.specialAbilities.contains(specialAbility);
	}

	@Override
	public String description() {
		return characterData.description;
	}

	@Override
	public int cardNumber() {
		return characterData.cardNumber;
	}

	public static Stream<BaseGameCharacter> stream() {
		return Stream.of(BaseGameCharacter.values());
	}

	public static Stream<BaseGameCharacter> stream(Predicate<BaseGameCharacter> predicate) {
		return BaseGameCharacter.stream().filter(predicate);
	}
	
	public static BaseGameCharacter of(Character character) {
		if (character instanceof BaseGameCharacter bgc) {
			return bgc;
		}
		throw new IllegalArgumentException("Character (" + character.toString() + ") is not a BaseGameCharacter.");
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
		private Set<BaseGamePlanet> homePlanet;
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
		public Builder homePlanet(BaseGamePlanet... homePlanet) {
			this.homePlanet = Set.of(homePlanet);
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
