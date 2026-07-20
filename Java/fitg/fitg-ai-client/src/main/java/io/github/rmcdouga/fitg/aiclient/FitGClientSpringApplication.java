package io.github.rmcdouga.fitg.aiclient;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import io.github.rmcdouga.fitg.aiclient.gui.ports.ChatClient;
import io.github.rmcdouga.fitg.aiclient.images.ClassPathImageStore;
import io.github.rmcdouga.fitg.aiclient.spring.ai.tools.MoverTool;
import io.github.rmcdouga.fitg.aiclient.spring.ai.tools.SpringAiTool;
import io.github.rmcdouga.fitg.aiclient.spring.gui.adapters.SpringChatClient;
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
    static ChatClient chatClient(org.springframework.ai.chat.client.ChatClient.Builder chatClientBuilder,
    							 @Value("classpath:prompts/SystemPrompt.md") Resource systemPromptResource,
					    		 Collection<SpringAiTool> springAiTools
					    		 ) throws IOException {
        return SpringChatClient.from(chatClientBuilder, systemPromptResource, springAiTools);
    }
    
    @Bean
    @Lazy
	static MainApplicationController mainApplicationController(ChatClient chatClient) {
		return new MainApplicationController(chatClient);
	}
    
    @Bean
    static Collection<SpringAiTool> springAiTools(Mover mover) {
		return List.of(new MoverTool(mover));
	}
    
    @Bean
	ChatClientBuilderCustomizer loggingCustomizer() {
		return builder -> builder.defaultAdvisors(SimpleLoggerAdvisor.builder().build());
	}
}
