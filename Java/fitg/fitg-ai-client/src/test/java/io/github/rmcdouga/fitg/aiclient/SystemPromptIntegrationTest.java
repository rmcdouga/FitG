package io.github.rmcdouga.fitg.aiclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid unit? Respond with Yes or No.".formatted(unit);
		// Arrange
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
//		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("yes");
	}

	@ParameterizedTest
	@ValueSource(strings = {"liquid00", "Liquid 0 0 ", "Liquid_2_0", "Liquid 2-0"})
	void testSystemPrompt_InValidUnits(String unit, FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid unit? Respond with Yes or No.".formatted(unit);
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
//		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("no");
	}

	@ParameterizedTest
	@ValueSource(strings = {"zinaadora", "Zina Adora ", "Adam_Starlight"})
	void testSystemPrompt_ValidCharacters(String character, FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid character? Respond with Yes or No.".formatted(character);
		// Arrange
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
//		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("yes");
	}

	@ParameterizedTest
	@ValueSource(strings = {"Mickey Mouse", "Liquid10", "Liquid_2_0", "Liquid 2-0"})
	void testSystemPrompt_InValidCharacters(String character, FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		var testQuery = "Is %s is a valid character? Respond with Yes or No.".formatted(character);
		var aiResponse = sendQuery(robot, mainApplicationController, testQuery);
//		IO.println("AI Response: " + aiResponse);

		assertThat(aiResponse).containsIgnoringCase("no");
	}
}
