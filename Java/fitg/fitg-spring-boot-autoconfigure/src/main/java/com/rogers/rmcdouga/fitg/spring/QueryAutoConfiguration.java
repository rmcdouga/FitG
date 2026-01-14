package com.rogers.rmcdouga.fitg.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGameCounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGameLocationFinder;
import com.rogers.rmcdouga.fitg.basegame.query.adapters.BaseGamePlanetFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;

@AutoConfiguration
public class QueryAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public PlanetFinder planetFinder(Game game) {
		return new BaseGamePlanetFinder(game);
	}

	@ConditionalOnMissingBean
	@Bean
	public CounterFinder counterFinder(Game game) {
		return new BaseGameCounterFinder(game);
	}

	@ConditionalOnMissingBean
	@Bean
	public LocationFinder locationFinder() {
		return new BaseGameLocationFinder();
	}

}
