package com.rogers.rmcdouga.fitg.spring;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Scenario;
import com.rogers.rmcdouga.fitg.basegame.command.CommandDispatcher;
import com.rogers.rmcdouga.fitg.basegame.command.CommandValidator;
import com.rogers.rmcdouga.fitg.basegame.command.MoveCommandTracker;
import com.rogers.rmcdouga.fitg.basegame.command.UserCommandProcessing;
import com.rogers.rmcdouga.fitg.basegame.command.adapters.MoverAdapter;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGameCounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGameLocationFinder;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

@AutoConfiguration
public class GameAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	BaseGameScenario scenario() {
		return FlightToEgrix;
	}	

	@ConditionalOnMissingBean(name = "rebelDecisions")
	@Bean Scenario.PlayerDecisions rebelDecisions() {
		return new FlightToEgrixRebelStrategy();
	}	

	@ConditionalOnMissingBean(name = "imperialDecisions")
	@Bean Scenario.PlayerDecisions imperialDecisions() {
		return new FlightToEgrixImperialStrategy();
	}	

	@ConditionalOnMissingBean
	@Bean Game game(BaseGameScenario scenario, Scenario.PlayerDecisions rebelDecisions, Scenario.PlayerDecisions imperialDecisions) {
		return Game.createGame(scenario, rebelDecisions, imperialDecisions);
	}
	
	@Bean
	Mover mover(Game game) {
		MoveCommandTracker moveCommandTracker = new MoveCommandTracker();
		return new MoverAdapter(new UserCommandProcessing(new CommandValidator(moveCommandTracker), 
														  new CommandDispatcher(game), 
														  moveCommandTracker), 
								new BaseGameCounterFinder(game), 
								new BaseGameLocationFinder()
								);
	}
}
