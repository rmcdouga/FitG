//usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//RUNTIME_OPTIONS -Dspring.ai.ollama.chat.model=gemma4:latest
//DEPS org.springframework.ai:spring-ai-bom:2.0.0@pom
//DEPS org.springframework.boot:spring-boot-starter:4.1.0
//DEPS org.springframework.ai:spring-ai-starter-model-ollama:2.0.0

package io.github.rmcdouga.fitg.aiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ModelEvaluationTest {
	private static final Logger log = LoggerFactory.getLogger(ModelEvaluationTest.class);

	private static final String SYSTEM_PROMPT = """
			The following section is a description of a star system in the universe of "Freedom In The Galaxy".
			The map contains a single star system with multiple planets.  Each planet has multiple environments.
			Use this information to answer questions about the star system, its planets, and their characteristics.
			Be concise and accurate in your responses.
			--------
			# FitG Map
			
			## Star System: 22 Egrix
			
			### Planet: 221 Quibron
			
			- **Loyalty:** Loyal
			- **Control:** ImperialControlled
			- **PDB:** UP_1
			
			#### Environment: Wild
			
			- **Size:** 4
			- **Resources:** 6*
			- **Coup:** -
			- **Races:** [Saurian*]
			- **Creature:** Snow Giants
			- **Sovereign:** -
			
			### Planet: 222 Angoff
			
			- **Loyalty:** Neutral
			- **Control:** RebelControlled
			- **PDB:** UP_2
			
			#### Environment: Urban
			
			- **Size:** 6
			- **Resources:** 9*
			- **Coup:** 3
			- **Races:** [Yester*]
			- **Creature:** Laboroid
			- **Sovereign:** -
			
			### Planet: 223 Charkhan
			
			- **Loyalty:** Patriotic
			- **Control:** ImperialControlled
			- **PDB:** UP_0
			
			#### Environment: Air
			
			- **Size:** 5
			- **Resources:** 7*
			- **Coup:** -
			- **Races:** [Yester*]
			- **Creature:** Drants
			- **Sovereign:** -
			
			#### Environment: Wild
			
			- **Size:** 3
			- **Resources:** 5*
			- **Coup:** -
			- **Races:** [Charkhan]
			- **Creature:** -
			- **Sovereign:** Megda Sheels
			""";
	private static final String USER_PROMPT = "What is the loyalty of Charkhan?";

    public static void main(String[] args) {
    	log.info("Starting application...");
        SpringApplication.run(ModelEvaluationTest.class, args);
        log.info("Application completed.");
    }

    // Create a CommandLineRunner bean to execute the evaluation logic after the application starts
    @Bean
    CommandLineRunner modelTest(ChatClient.Builder chatClientBuilder) {
    	log.info("Starting modelTest...");
    	// Build the ChatClient
    	ChatClient chatClient = chatClientBuilder.build();
    	return args -> {
    	   	log.info("Sending prompt...");
    	    
    	   	// Send the prompt to the model and get the response
    	   	var result = chatClient.prompt()
    	   			   .system(SYSTEM_PROMPT)
    	   			  .user(ps->ps.text(USER_PROMPT))
    	   			  .call()
    	   			  .content();
//   				  .doOnComplete(onComplete)
//   				  .doOnError(onError)
//    	   			  .subscribe(IO::print);
    	   	IO.println(result);
       	   	log.info("Prompt sent...");
       	 			// Print the response to the console
//			System.out.println("Model Response: " + response);
		};
    }
}