package com.rogers.rmcdouga.fitg.spring;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

@AutoConfiguration
public class GameAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public BaseGameScenario scenario() {
		return FlightToEgrix;
	}	

	@ConditionalOnMissingBean(name = "rebelDecisions")
	@Bean
	public Scenario.PlayerDecisions rebelDecisions() {
		return new FlightToEgrixRebelStrategy();
	}	

	@ConditionalOnMissingBean(name = "imperialDecisions")
	@Bean
	public Scenario.PlayerDecisions imperialDecisions() {
		return new FlightToEgrixImperialStrategy();
	}	

	@ConditionalOnMissingBean
	@Bean
	public Game game(BaseGameScenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		return Game.createGame(scenario, rebelDecisions, imperialDecisions);
	}
}
