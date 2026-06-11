package io.github.rmcdouga.fitg.aiclient.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.MimeTypeUtils;

import io.github.rmcdouga.fitg.aiclient.ContextUtil;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainApplicationController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(MainApplicationController.class);

    @FXML
    public TextArea textAreaAiResponse;
    @FXML
    public TextArea textAreaInput;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGui();
    }

    private void setupGui() {
        log.atInfo().log("FitG Desktop AI Assistant Loaded!");
    }

    @FXML
    public void handleButtonSendAction(ActionEvent actionEvent) {
        var textPrompt = textAreaInput.getText();
        var image = imageView.getImage();
        if (textPrompt == null || textPrompt.isEmpty()) {
            return;
        }
        imageView.setVisible(false);
        imageView.setImage(null);
        textAreaAiResponse.setText("");
        textAreaInput.clear();
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        if (image == null) {
            processTextOnlyPrompt(textPrompt);
        } else {
            processTextWithImagePrompt(textPrompt, image);
        }
    }

    private void processTextWithImagePrompt(String textPrompt, Image image) {
        log.atInfo().log("Processing LLM input with text and image");
        var chatClient = ContextUtil.getApplicationContext().getBean(ChatClient.class);
        var byteArrayOutputStream = convertJavaFxImageIntoPng(image);

        chatClient.prompt()
                .user(promptUserSpec -> {
                    promptUserSpec
                            .text(textPrompt)
                            .media(MimeTypeUtils.IMAGE_PNG, new InputStreamResource(
                                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray())
                            ));
                })
                .stream()
                .content()
                .doOnComplete(() -> Platform.runLater(() -> progressBar.setProgress(0)))
                .subscribe(token -> {
                    Platform.runLater(() -> textAreaAiResponse.appendText(token));
                });
    }

    private ByteArrayOutputStream convertJavaFxImageIntoPng(Image image) {
        try {
            var bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bao);
            return bao;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processTextOnlyPrompt(String textPrompt) {
        log.atInfo().log("Processing LLM input with text only");
        var chatClient = ContextUtil.getApplicationContext().getBean(ChatClient.class);
        chatClient.prompt()
                .user(textPrompt)
                .stream()
                .content()
                .doOnComplete(() -> Platform.runLater(() -> progressBar.setProgress(0)))
                .subscribe(token -> {
                    Platform.runLater(() -> textAreaAiResponse.appendText(token));
                });
    }

    @FXML
    public void handleButtonImagePickerAction(ActionEvent actionEvent) {
        var clipboard = Clipboard.getSystemClipboard();
        if (clipboard == null) {
            log.atInfo().log("Clipboard is null!");
            return;
        }
        if (!clipboard.hasImage()) {
            log.atInfo().log("Clipboard has no image!");
            return;
        }
        var image = clipboard.getImage();
        imageView.setImage(image);
        log.atInfo().log("Image loaded successfully!");
        imageView.setVisible(true);
    }
}
