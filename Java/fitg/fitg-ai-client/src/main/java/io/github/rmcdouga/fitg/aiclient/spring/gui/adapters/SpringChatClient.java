package io.github.rmcdouga.fitg.aiclient.spring.gui.adapters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;

import io.github.rmcdouga.fitg.aiclient.spring.ai.tools.SpringAiTool;

public class SpringChatClient implements io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient {
	private static final Logger log = LoggerFactory.getLogger(SpringChatClient.class);

	// Code assumes there is only every one conversation with the AI.  
	// If we ever want to support multiple conversations, this will need to be changed.
	private static final String FITG_CLIENT_CONVERSATION_ID = "fitg-desktop-ai-assistant";

    private final ChatClient chatClient;
    private final Collection<SpringAiTool> springAiTools;
    private final AtomicBoolean isSystemPromptSent = new AtomicBoolean(false);
    private final String systemPrompt;
    
	private SpringChatClient(ChatClient chatClient, Collection<SpringAiTool> springAiTools, String systemPrompt) {
		this.chatClient = chatClient;
		this.springAiTools = springAiTools;
		this.systemPrompt = systemPrompt;
	}

	public static SpringChatClient from(ChatClient.Builder chatClientBuilder, Resource systemPromptResource, Collection<SpringAiTool> springAiTools) throws IOException {
		// Define a in-memory store with a message window of 100 messages.
		var messageWindowChatMemory = MessageWindowChatMemory.builder()
															 .chatMemoryRepository(new InMemoryChatMemoryRepository())
															 .maxMessages(100) // Keep the last 10 messages in memory
															 .build();

		// Attach it as a default advisor when building the ChatClient
		ChatClient chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build())
												 .build();
		// Read the system prompt from the resource
		String systemPrompt = systemPromptResource.getContentAsString(StandardCharsets.UTF_8);

		// create a chat client that wraps the Spring ChatClient and the Spring AI tools.
		return new SpringChatClient(chatClient, springAiTools, systemPrompt);
	}

	@Override
	public void sendPrompt(String textPrompt, 
						   byte[] media, 
						   Runnable onComplete,
						   Consumer<? super Throwable> onError, 
						   Consumer<? super String> consumer) {
		if (!isSystemPromptSent.getAndSet(true)) { sendSystemPrompt(); }
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
		if (!isSystemPromptSent.getAndSet(true)) { sendSystemPrompt(); }
		sendPrompt(onComplete, onError, consumer, textPromptCustomizer(textPrompt));
	}

	private Consumer<PromptUserSpec> textPromptCustomizer(String textPrompt) {
		return promptUserSpec -> promptUserSpec.text(textPrompt);
	}

	private void sendPrompt(Runnable onComplete, Consumer<? super Throwable> onError, Consumer<? super String> consumer,
			Consumer<PromptUserSpec> promptSpecCustomizer) {
		chatClient.prompt()
				.advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, FITG_CLIENT_CONVERSATION_ID))
                .user(promptSpecCustomizer)
                .tools(springAiTools)
                .stream()
                .content()
                .doOnComplete(onComplete)
                .doOnError(onError)
                .subscribe(consumer);
	}

	private void sendSystemPrompt() {
		// Send system propmt to the AI to inform it of it's role and limitations.
		log.atInfo().log("Sending system prompt");
		
		CallResponseSpec response = chatClient.prompt()
				  .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, FITG_CLIENT_CONVERSATION_ID))
        		  .user(systemPrompt)
        		  .call();
		
		log.atInfo().addArgument(()->response.content()).log("System prompt response: {}");
	}
}
