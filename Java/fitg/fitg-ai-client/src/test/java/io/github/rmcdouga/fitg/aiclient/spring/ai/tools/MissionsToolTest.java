package io.github.rmcdouga.fitg.aiclient.spring.ai.tools;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;

import io.github.rmcdouga.fitg.aiclient.FxmlLoader;
import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@Tag("requiresLLM")
@SpringBootTest(
		properties = {
				"spring.ai.chat.client.observations.log-prompt=true",
				"spring.ai.chat.client.observations.log-completion=true",
				"logging.level.io.github.rmcdouga.fitg.aiclient.spring.ai.tools.MissionsToolTest=debug"
		}
)
@ExtendWith(ApplicationExtension.class)
class MissionsToolTest {
    private final Logger log = LoggerFactory.getLogger(MissionsToolTest.class);

    @Autowired
	FxmlLoader fxmlLoader;
	
	@MockitoBean
	CharacterFinder mockCharacterFinder;

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


	@Test
	void testListCharactersAvailableForMissions(FxRobot robot,
												@Autowired MainApplicationController mainApplicationController
												) throws TimeoutException {
		var expectedEnviron_Adam = BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).orElseThrow();
		var expectedEnviron_Agan = BaseGamePlanet.Angoff.environ(BaseGameEnvironType.Urban).orElseThrow();

		when(mockCharacterFinder.findAllCharactersEligibleForMissions(Faction.REBEL))
			.thenReturn(Stream.of(
				new CharacterFinder.CharacterEligibleForMission(BaseGameCharacter.Adam_Starlight, expectedEnviron_Adam),
				new CharacterFinder.CharacterEligibleForMission(BaseGameCharacter.Agan_Rafa, expectedEnviron_Agan)
			));
		
		var testQuery = "What characters are available for missions for the Rebel faction?";
		sendQuery(robot, mainApplicationController, testQuery);
		
		verify(mockCharacterFinder).findAllCharactersEligibleForMissions(Faction.REBEL);

	}

}
