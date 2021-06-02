package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SvgViewerApplication implements CommandLineRunner {
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

	public String drawMap() throws IOException {
		return new Map().draw();
	}
	
}
