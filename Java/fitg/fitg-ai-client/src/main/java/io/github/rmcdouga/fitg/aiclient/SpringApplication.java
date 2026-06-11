package io.github.rmcdouga.fitg.aiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplication;
import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import io.github.rmcdouga.fitg.aiclient.images.ClassPathImageStore;

@SpringBootApplication
public class SpringApplication {
    private final Logger log = LoggerFactory.getLogger(MainApplicationController.class);

	@EventListener(ApplicationReadyEvent.class)
	public void handleAppStart() {
		Thread.ofPlatform()
			  .name("JavaFX-App")
			  .start(() -> {
				  log.info("Starting JavaFX application...");
				  MainApplication.launch(MainApplication.class);
				  log.info("JavaFX application has exited");
			  });
	}

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
	
	@Bean
	public static ImageStore classPathImageStore() {
		return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
	}

    @Bean
    public static ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
    
    @Bean(destroyMethod = "close")
    public static ContextUtil contextUtil() {
		return new ContextUtil();
	}
}
