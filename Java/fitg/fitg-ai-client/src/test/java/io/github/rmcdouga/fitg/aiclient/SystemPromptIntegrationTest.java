package io.github.rmcdouga.fitg.aiclient;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.rmcdouga.fitg.aiclient.EvaluationResponseAssert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Tag("requiresLLM")
@SpringBootTest
@ExtendWith(ApplicationExtension.class)
class SystemPromptIntegrationTest {

	@Autowired
	FxmlLoader fxmlLoader;

	@Start
	public void start(Stage stage) throws IOException {
		Parent parent = fxmlLoader.load("/fxml/mainwindow.fxml");
		Scene scene = new Scene(parent);
		stage.setTitle("FitG AI Client");
		stage.setScene(scene);
		stage.getIcons().add(new Image("/icon/icon.png"));
		stage.show();
	}

	private String sendQuery(FxRobot robot, MainApplicationController mainApplicationController, String testQuery)
			throws TimeoutException {
		robot.interact(() -> mainApplicationController.textAreaInput.setText(testQuery));
		WaitForAsyncUtils.waitForFxEvents();
		robot.interact(() -> robot.lookup("#sendButton").queryButton().fire());
		WaitForAsyncUtils.waitFor(5, java.util.concurrent.TimeUnit.SECONDS, ()->Double.valueOf(mainApplicationController.progressBar.getProgress()).intValue() == 0);

		var aiResponse = mainApplicationController.textAreaAiResponse.getText();
		return aiResponse;
	}

	@ParameterizedTest
	@ValueSource(strings = {"liquid10", "Liquid 1 0 ", "Liquid_1_0", "Liquid 1-0"})
	void testSystemPrompt_ValidUnits(String unit, FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController,
			  @Autowired FactCheckingEvaluator factCheckingEvaluator
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid unit? Respond with Yes or No.".formatted(unit);
		// Arrange
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("yes");
	}

	@ParameterizedTest
	@ValueSource(strings = {"liquid00", "Liquid 0 0 ", "Liquid_2_0", "Liquid 2-0"})
	void testSystemPrompt_InValidUnits(String unit, FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController,
			  @Autowired FactCheckingEvaluator factCheckingEvaluator
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid unit? Respond with Yes or No.".formatted(unit);
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("no");
	}
	
	@TestConfiguration
	static class LocalTestConfig {
		@Bean
		static FactCheckingEvaluator factCheckingEvaluator() {
			  // Set up the Ollama API
			  OllamaApi ollamaApi = OllamaApi.builder().baseUrl("http://localhost:11434").build();

			  OllamaChatOptions chatOptions = OllamaChatOptions.builder()
							 								   .model("bespoke-minicheck") // Use bespoke-minicheck model for fact-checking
							 								   .numPredict(2)
							 								   .temperature(0.0d)
							 								   .build();
			  
			  ChatModel chatModel = OllamaChatModel.builder()
					  							   .ollamaApi(ollamaApi)
					  							   .options(chatOptions)
					  							   .build();


			  // Create the FactCheckingEvaluator
			  var builder = ChatClient.builder(chatModel);
			  
			  return FactCheckingEvaluator.builder(builder).build();
		}
	}
}
