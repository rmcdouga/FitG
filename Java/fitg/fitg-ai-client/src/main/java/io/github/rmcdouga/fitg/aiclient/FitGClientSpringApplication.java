package io.github.rmcdouga.fitg.aiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import io.github.rmcdouga.fitg.aiclient.images.ClassPathImageStore;
import javafx.application.Application;

@SpringBootApplication
public class FitGClientSpringApplication {
    private final Logger log = LoggerFactory.getLogger(MainApplicationController.class);

	public static void main(String[] args) {
	       Application.launch(FitGClientFxApplication.class, args);
	}
	
	// Application Beans
	
	@Bean
	static ImageStore classPathImageStore() {
		return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
	}

    @Bean
    static ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
    
    @Bean
    @Lazy
	static MainApplicationController mainApplicationController(ChatClient chatClient) {
		return new MainApplicationController(chatClient);
	}
}
