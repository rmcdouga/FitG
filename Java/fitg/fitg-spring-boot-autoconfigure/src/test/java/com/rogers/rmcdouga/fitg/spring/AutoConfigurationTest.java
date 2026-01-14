package com.rogers.rmcdouga.fitg.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.PlanetFinder;
import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

@SpringBootTest(classes = {com.rogers.rmcdouga.fitg.spring.AutoConfigurationTest.TestApplication.class, 
						   com.rogers.rmcdouga.fitg.spring.AutoConfigurationTest.LocalTestConfiguration.class, 
						   GameAutoConfiguration.class, 
						   RendererAutoConfiguration.class,
						   QueryAutoConfiguration.class
						   })
class AutoConfigurationTest {

	@Test
	void testMap(@Autowired Map map) {
		assertNotNull(map);
	}

	@Test
	void testGame(@Autowired Game game) {
		assertNotNull(game);
	}

	@Test
	void testCounterFinder(@Autowired CounterFinder counterFinder) {
		assertNotNull(counterFinder);
	}

	@Test
	void testLocationCounterFinder(@Autowired LocationFinder locationFinder) {
		assertNotNull(locationFinder);
	}

	@Test
	void testCounterFinder(@Autowired PlanetFinder planetFinder) {
		assertNotNull(planetFinder);
	}


	@SpringBootApplication
	public static class TestApplication {
		public static void main(String[] args) {
			SpringApplication.run(TestApplication.class, args);
		}
	}

	@TestConfiguration
	public static class LocalTestConfiguration {
		@Bean
		public Graphics2D graphics2d() {
			BufferedImage off_Image = new BufferedImage(Map.MAP_WIDTH, Map.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			return off_Image.createGraphics();
		}

		@Bean
		public ImageStore imageStore() {
			return BaseGameImageStoreAdapter.wrap(null);	// Doesn't need to work for this test so null will do.
		}
	}
}
