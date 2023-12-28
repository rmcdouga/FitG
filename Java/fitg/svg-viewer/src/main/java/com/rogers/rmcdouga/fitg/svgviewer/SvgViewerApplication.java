package com.rogers.rmcdouga.fitg.svgviewer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jfree.svg.SVGGraphics2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;
import com.rogers.rmcdouga.fitg.svgviewer.images.ClassPathImageStore;

@SpringBootApplication
public class SvgViewerApplication implements CommandLineRunner, ApplicationContextAware {
	private static Logger log = LoggerFactory.getLogger(SvgViewerApplication.class);

	private static final Path MAIN_RESOURCES_DIR = Paths.get("src", "main", "resources");
	private static final Path TEST_RESOURCES_DIR = Paths.get("src", "test", "resources");
	
	public static void main(String[] args) {
		SpringApplication.run(SvgViewerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Fill in this method with mainline
		log.info("Inside mainline.");
		String svgDocument = drawMap();
		Files.writeString(TEST_RESOURCES_DIR.resolve("actualResults").resolve("FitgMap.svg"), svgDocument);
		log.info("Mainline complete.");
	}

	@Bean
	public static SVGGraphics2D graphics2d() {
		return new SVGGraphics2D(Map.MAP_WIDTH, Map.MAP_HEIGHT);
	}

	@Bean
	public static ImageStore classPathImageStore() {
		return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getContext() {
		return applicationContext;
	}

	public String drawMap() throws IOException {
		return drawMap(applicationContext.getBean(Map.class), applicationContext.getBean(SVGGraphics2D.class));
	}

	public String drawMap(Map map, SVGGraphics2D svgGraphics2D) throws IOException {
		map.draw();
		return svgGraphics2D.getSVGDocument();
	}
	
//	// TODO:  Change this to read a game from a GameState.
//	private static Game createGame() {
//		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
//		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
//		return Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
//	}
}
