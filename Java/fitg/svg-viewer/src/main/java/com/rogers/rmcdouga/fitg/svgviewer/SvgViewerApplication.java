package com.rogers.rmcdouga.fitg.svgviewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SvgViewerApplication implements CommandLineRunner {
	 private static Logger log = LoggerFactory.getLogger(SvgViewerApplication.class);
	 
	public static void main(String[] args) {
		SpringApplication.run(SvgViewerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Fill in this method with mainline
		log.info("Inside mainline.");
	}

}
