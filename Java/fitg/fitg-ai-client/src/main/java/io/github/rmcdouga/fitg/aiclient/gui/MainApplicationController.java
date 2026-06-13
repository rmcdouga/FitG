package io.github.rmcdouga.fitg.aiclient.gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.MimeTypeUtils;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;

public class MainApplicationController {

    private static final Logger log = LoggerFactory.getLogger(MainApplicationController.class);

    @FXML
    public TextArea textAreaAiResponse;
    @FXML
    public TextArea textAreaInput;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public ImageView imageView;

    private final ChatClient chatClient;
    
    public MainApplicationController(ChatClient chatClient) {
		this.chatClient = chatClient;
		super();
	}

	public void initialize() {
        setupGui();
    }

    private void setupGui() {
        log.atInfo().log("FitG Desktop AI Assistant Loaded!");
    }

    @FXML
    public void handleButtonSendAction(ActionEvent actionEvent) {
        var textPrompt = textAreaInput.getText();
        var image = imageView.getImage();
        if (textPrompt == null || textPrompt.isBlank()) {
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
        var byteArrayOutputStream = convertJavaFxImageIntoPng(image);

        chatClient.prompt()
                .user(promptUserSpec -> promptUserSpec
                        .text(textPrompt)
                        .media(MimeTypeUtils.IMAGE_PNG, new InputStreamResource(
                                new ByteArrayInputStream(byteArrayOutputStream.toByteArray())
                        )))
                .stream()
                .content()
                .doOnComplete(this::markPromptComplete)
                .doOnError(this::handlePromptError)
                .subscribe(this::appendTokenToResponse);
    }

    private ByteArrayOutputStream convertJavaFxImageIntoPng(Image image) {
        try {
            var bufferedImage = SwingFXUtils.fromFXImage(image, null);
            var bao = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bao);
            return bao;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to convert JavaFX image to PNG", e);
        }
    }

    private void processTextOnlyPrompt(String textPrompt) {
        log.atInfo().log("Processing LLM input with text only");
        chatClient.prompt()
                .user(textPrompt)
                .stream()
                .content()
                .doOnComplete(this::markPromptComplete)
                .doOnError(this::handlePromptError)
                .subscribe(this::appendTokenToResponse);
    }

    private void appendTokenToResponse(String token) {
        Platform.runLater(() -> textAreaAiResponse.appendText(token));
    }

    private void markPromptComplete() {
        Platform.runLater(() -> progressBar.setProgress(0));
    }

    private void handlePromptError(Throwable error) {
        log.atError().setCause(error).log("Error while streaming AI response");
        Platform.runLater(this::showPromptError);
    }

    private void showPromptError() {
        progressBar.setProgress(0);
        textAreaAiResponse.appendText(System.lineSeparator() + "[Error receiving response]");
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