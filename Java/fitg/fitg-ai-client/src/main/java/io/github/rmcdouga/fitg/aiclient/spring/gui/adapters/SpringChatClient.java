package io.github.rmcdouga.fitg.aiclient.spring.gui.adapters;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.MimeTypeUtils;

public class SpringChatClient implements io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient {
    private final ChatClient chatClient;
    
	public SpringChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@Override
	public void sendPrompt(String textPrompt, 
						   byte[] media, 
						   Runnable onComplete,
						   Consumer<? super Throwable> onError, 
						   Consumer<? super String> consumer) {
		sendPrompt(onComplete, onError, consumer, imagePromptCustomizer(textPrompt, media));
	}

	private Consumer<PromptUserSpec> imagePromptCustomizer(String textPrompt, byte[] media) {
		return promptUserSpec -> promptUserSpec.text(textPrompt)
                        					   .media(MimeTypeUtils.IMAGE_PNG, 
                        							  new InputStreamResource(new ByteArrayInputStream(media))
                        						);
	}

	@Override
	public void sendPrompt(String textPrompt, 
						   Runnable onComplete, 
						   Consumer<? super Throwable> onError,
						   Consumer<? super String> consumer) {
		sendPrompt(onComplete, onError, consumer, textPromptCustomizer(textPrompt));
	}

	private Consumer<PromptUserSpec> textPromptCustomizer(String textPrompt) {
		return promptUserSpec -> promptUserSpec.text(textPrompt);
	}

	private void sendPrompt(Runnable onComplete, Consumer<? super Throwable> onError, Consumer<? super String> consumer,
			Consumer<PromptUserSpec> promptSpecCustomizer) {
		chatClient.prompt()
                .user(promptSpecCustomizer)
                .stream()
                .content()
                .doOnComplete(onComplete)
                .doOnError(onError)
                .subscribe(consumer);
	}
}
