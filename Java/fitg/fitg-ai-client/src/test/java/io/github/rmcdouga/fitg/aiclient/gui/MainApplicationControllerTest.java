package io.github.rmcdouga.fitg.aiclient.gui;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class MainApplicationControllerTest {

    private final StubChatClient chatClient = new StubChatClient();;
    private MainApplicationController underTest;
    private TextArea responseArea;
    private TextArea inputArea;
    private ProgressBar progressBar;
    private ImageView imageView;

    @Start
    public void start(Stage stage) {
        underTest = new MainApplicationController(chatClient);
        responseArea = new TextArea();
        responseArea.setId("textAreaAiResponse");
        inputArea = new TextArea();
        inputArea.setId("textAreaInput");
        progressBar = new ProgressBar(0);
        progressBar.setId("progressBar");
        imageView = new ImageView();
        imageView.setId("imageView");
        var sendButton = new Button("Send");
        sendButton.setId("sendButton");
        sendButton.setOnAction(underTest::handleButtonSendAction);

        underTest.textAreaAiResponse = responseArea;
        underTest.textAreaInput = inputArea;
        underTest.progressBar = progressBar;
        underTest.imageView = imageView;
        underTest.initialize();

        var root = new VBox(responseArea, progressBar, inputArea, sendButton, imageView);
        stage.setScene(new Scene(root, 480, 320));
        stage.show();
    }

    @Test
    void sendButtonStreamsTextOnlyResponseOnHappyPath(FxRobot robot) {
    	
    	robot.interact(() -> inputArea.setText("Plan next move"));
        WaitForAsyncUtils.waitForFxEvents();
        robot.interact(() -> robot.lookup("#sendButton").queryButton().fire());
        WaitForAsyncUtils.waitForFxEvents();

        assertThat(chatClient.textPrompt).isEqualTo("Plan next move");
        assertThat(chatClient.media).isNull();
        assertThat(inputArea.getText()).isEmpty();
        assertThat(responseArea.getText()).isEqualTo("Ready.");
        assertThat(progressBar.getProgress()).isZero();
        assertThat(imageView.isVisible()).isFalse();
        assertThat(imageView.getImage()).isNull();
    }

    private static class StubChatClient implements ChatClient {
        private String textPrompt;
        private byte[] media;

        @Override
        public void sendPrompt(String textPrompt, byte[] media, Runnable onComplete,
                Consumer<? super Throwable> onError, Consumer<? super String> consumer) {
            this.textPrompt = textPrompt;
            this.media = media;
            consumer.accept("Ready.");
            onComplete.run();
        }

        @Override
        public void sendPrompt(String textPrompt, Runnable onComplete, Consumer<? super Throwable> onError,
                Consumer<? super String> consumer) {
            this.textPrompt = textPrompt;
            consumer.accept("Ready.");
            onComplete.run();
        }
    }
}