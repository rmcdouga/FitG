package io.github.rmcdouga.fitg.aiclient.gui;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class MainWindowFxmlWiringTest {

    @Test
    void mainWindowFxmlLoadsAndWiresController(FxRobot robot, SoftAssertions softly) {
        var rootRef = new AtomicReference<Parent>();
        var controllerRef = new AtomicReference<Object>();

        robot.interact(() -> {
            var loader = new FXMLLoader(getClass().getResource("/fxml/mainwindow.fxml"));
            loader.setControllerFactory(type -> {
                if (type == MainApplicationController.class) {
                    return new MainApplicationController(new StubChatClient());
                }
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new IllegalStateException("Unable to create controller: " + type.getName(), e);
                }
            });
            try {
                rootRef.set(loader.load());
                controllerRef.set(loader.getController());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

        softly.assertThat(controllerRef.get()).isInstanceOf(MainApplicationController.class);
        softly.assertThat(rootRef.get().lookup("#textAreaAiResponse")).isInstanceOf(TextArea.class);
        softly.assertThat(rootRef.get().lookup("#textAreaInput")).isInstanceOf(TextArea.class);
        softly.assertThat(rootRef.get().lookup("#progressBar")).isInstanceOf(ProgressBar.class);
        softly.assertThat(rootRef.get().lookup("#imageView")).isInstanceOf(ImageView.class);
    }

    private static class StubChatClient implements ChatClient {
        @Override
        public void sendPrompt(String textPrompt, byte[] media, Runnable onComplete,
                Consumer<? super Throwable> onError, Consumer<? super String> consumer) {
            consumer.accept("");
            onComplete.run();
        }

        @Override
        public void sendPrompt(String textPrompt, Runnable onComplete, Consumer<? super Throwable> onError,
                Consumer<? super String> consumer) {
            consumer.accept("");
            onComplete.run();
        }
    }
}
