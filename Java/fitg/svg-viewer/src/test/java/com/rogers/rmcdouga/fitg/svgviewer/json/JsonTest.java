package com.rogers.rmcdouga.fitg.svgviewer.json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.svgviewer.TestConstants;


@SpringBootTest(webEnvironment = WebEnvironment.NONE, 
				classes = {
							com.rogers.rmcdouga.fitg.svgviewer.json.JsonTest.TestApplication.class,
							com.rogers.rmcdouga.fitg.spring.GameAutoConfiguration.class
						  }
				)
class JsonTest {

	@Autowired Game game;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

	@Test
	void test() throws Exception {
		assertNotNull(game);
		var resultFile = TestConstants.ACTUAL_RESULTS_DIR.resolve("game_state.json");
	    objectWriter.writeValue(resultFile.toFile(), game.getState());
	}

	@SpringBootApplication()
	public static class TestApplication {
		public static void main(String[] args) {
			SpringApplication.run(TestApplication.class, args);
		}
	}

}
