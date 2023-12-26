package com.rogers.rmcdouga.fitg.spring;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

@AutoConfiguration
public class GameAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public BaseGameScenario scenario() {
		return FlightToEgrix;
	}	

	@ConditionalOnMissingBean
	@Bean
	public Game game(BaseGameScenario scenario) {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		return Game.createGame(scenario, rebelDecisions, imperialDecisions);
	}
}
