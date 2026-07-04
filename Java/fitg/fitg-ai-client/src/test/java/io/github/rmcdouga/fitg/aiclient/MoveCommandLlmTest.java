package io.github.rmcdouga.fitg.aiclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Tag("requiresLLM")
@SpringBootTest(
		properties = {
				"spring.ai.chat.client.observations.log-prompt=true",
				"spring.ai.chat.client.observations.log-completion=true"
		}
)
@ExtendWith(ApplicationExtension.class)
class MoveCommandLlmTest {

	@Autowired
	FxmlLoader fxmlLoader;
	
	@MockitoBean
	Mover mockMover;

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
		WaitForAsyncUtils.waitFor(15, java.util.concurrent.TimeUnit.SECONDS, ()->Double.valueOf(mainApplicationController.progressBar.getProgress()).intValue() == 0);

		var aiResponse = mainApplicationController.textAreaAiResponse.getText();
		return aiResponse;
	}

	// Should be able to move a unit by saying ("Move X from Y to Z")
	// Should be able to move a unit by saying ("Move X at Y to Z")
	@RepeatedTest(3)
	void testMoveUnit(FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		when(mockMover.moveUnitCounter(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockMover);

		var testQuery = "Move Liquid 1-0 from 223 air to 223 wild";
		sendQuery(robot, mainApplicationController, testQuery);

		verify(mockMover).moveUnitCounter(AdditionalMatchers.or(argThat("liquid10"::equalsIgnoreCase),argThat("liquid 1-0"::equalsIgnoreCase)), 
										  eq("223"), 
										  argThat("Air"::equalsIgnoreCase), 
										  eq("223"), 
										  argThat("Wild"::equalsIgnoreCase));
	}

	// Should be able to move a unique unit by saying ("Move X to Y")
	@RepeatedTest(3)
	void testMoveById(FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		when(mockMover.moveCounter(anyString(), anyString(), anyString())).thenReturn(mockMover);

		var testQuery = "Move Zina Adora to 223 wild";
		sendQuery(robot, mainApplicationController, testQuery);

		verify(mockMover).moveCounter(argThat("ZinaAdora"::equalsIgnoreCase), eq("223"), argThat("Wild"::equalsIgnoreCase));
	}

	// Should be able to move a stack by saying ("Move the stack containing character to Y")
	@Disabled("Can't do this yet because the LLM doesn't know how to identify a stack containing a character")
	@Test
	void testStackContaining(FxRobot robot,
			  @Autowired MainApplicationController mainApplicationController
			  ) throws TimeoutException {
		when(mockMover.moveUnitCounter(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockMover);
		fail("Not yet implemented");
	}
}
