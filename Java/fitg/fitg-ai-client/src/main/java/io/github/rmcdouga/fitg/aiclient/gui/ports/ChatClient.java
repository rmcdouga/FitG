package io.github.rmcdouga.fitg.aiclient.gui.ports;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

public interface ChatClient {

	void sendPrompt(String textPrompt, 
					byte[] media, 
					Runnable onComplete, 
					Consumer<? super Throwable> onError,
					Consumer<? super String> consumer);

	void sendPrompt(String textPrompt, 
					Runnable onComplete, 
					Consumer<? super Throwable> onError,
					Consumer<? super String> consumer);

	default PromptBuilder prompt() {
		return new PromptBuilder(this);
	}
	
	static class PromptBuilder {
		private final ChatClient chatClient;
		private String textPrompt;
		private byte[] media;
		private Runnable onComplete;
		private Consumer<? super Throwable> onError; 
		private Consumer<? super String> consumer;
		
		private PromptBuilder(ChatClient chatClient) {
			this.chatClient = chatClient;
		}
		
		public PromptBuilder text(String textPrompt) {
			this.textPrompt = textPrompt;
			return this;
		}
		
		public PromptBuilder media(byte[] media) {
			this.media = media;
			return this;
		}
		
		public PromptBuilder onComplete(Runnable onComplete) {
			this.onComplete = onComplete;
			return this;
		}
		
		public PromptBuilder onError(Consumer<? super Throwable> onError) {
			this.onError = onError;
			return this;
		}
		
		public PromptBuilder onContent(Consumer<? super String> consumer) {
			this.consumer = consumer;
			return this;
		}
		
		public void send() {
			if (media != null) {
				chatClient.sendPrompt(requireNonNull(textPrompt), media, requireNonNull(onComplete), requireNonNull(onError), requireNonNull(consumer));
			} else {
				chatClient.sendPrompt(requireNonNull(textPrompt), requireNonNull(onComplete), requireNonNull(onError), requireNonNull(consumer));
			}
		}
	}

}
