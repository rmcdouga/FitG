package io.github.rmcdouga.fitg.aiclient.gui;

import java.io.IOException;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import io.github.rmcdouga.fitg.aiclient.FxmlLoader;
import io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SpringBootTest
@ExtendWith(ApplicationExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class MainWindowFxmlWiringTest {

	@MockitoBean
	ChatClient mockChatClient;
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

	@Test
	void mainWindowFxmlLoadsAndWiresController(SoftAssertions softly,
											   @Autowired MainApplicationController mainApplicationController
											  ) throws IOException {
		// Assert
		softly.assertThat(mainApplicationController).isNotNull();
		softly.assertThat(mainApplicationController.textAreaInput).isNotNull();
		softly.assertThat(mainApplicationController.textAreaAiResponse).isNotNull();
		softly.assertThat(mainApplicationController.progressBar).isNotNull();
		softly.assertThat(mainApplicationController.imageView).isNotNull();
	}
}
