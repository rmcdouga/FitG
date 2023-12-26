package com.rogers.rmcdouga.fitg.spring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;

@SpringBootTest(classes = {com.rogers.rmcdouga.fitg.spring.GameAutoConfigurationTest.TestApplication.class, GameAutoConfiguration.class})
class GameAutoConfigurationTest {

	@Test
	void testGame(@Autowired Game game) {
		assertNotNull(game);
	}

	@SpringBootApplication
	public static class TestApplication {
		public static void main(String[] args) {
			SpringApplication.run(TestApplication.class, args);
		}
	}
}
