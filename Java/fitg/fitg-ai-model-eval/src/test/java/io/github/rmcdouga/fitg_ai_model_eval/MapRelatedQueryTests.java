package io.github.rmcdouga.fitg_ai_model_eval;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class MapRelatedQueryTests {
	private static final Logger log = LoggerFactory.getLogger(MapRelatedQueryTests.class);

	private static final String SYSTEM_PROMPT = """
			The following section is a description of a star system in the universe of "Freedom In The Galaxy".
			The map contains a single star system with multiple planets.  Each planet has multiple environments.
			Use this information to answer questions about the star system, its planets, and their characteristics.
			Be concise and accurate in your responses.
			Prefer single word answers when possible, and use the exact terminology from the map description.
			Prefer numerals over words for numbers.
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

	// Create the FactCheckingEvaluator
	FactCheckingEvaluator factCheckingEvaluator;

	@BeforeEach
	void setUp(@Autowired ChatClient.Builder chatClientBuilder) {
		factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder)
													 .build();	
	}
	
	@ParameterizedTest
	@CsvSource({
		"How many planets are in the Egrix star system?, 3",
		"What is the loyalty of planet 221?, Loyal",
		"What is the control status of planet 222?, RebelControlled",
		"What is the PDB of planet 223?, UP_0",
		"What is the size of the Wild environment on planet Quibron?, 4",
		"What resources are available on the Urban environment of planet Angoff?, 9*",
		"Is there a coup on Charkhan?, No",
	})
	void queryMapForInfo(String query, String expectedAnswer, @Autowired ChatClient.Builder chatClientBuilder) {
	   	// Send the prompt to the model and get the response
	   	var result = chatClientBuilder.build()
	   				.prompt()
	   				.system(SYSTEM_PROMPT)
	   				.user(query)
	   				.call()
	   				.content();
//				  .doOnComplete(onComplete)
//				  .doOnError(onError)
//	   			  .subscribe(IO::print);
	   	
   	   	assertThat(result).isEqualTo(expectedAnswer);
//  	  var factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
//  	  
//	  // Create an EvaluationRequest
//	  EvaluationRequest evaluationRequest = new EvaluationRequest(List.of(new Document(SYSTEM_PROMPT)), result);
//
//	  // Perform the evaluation
//	  EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
//	  assertThat(evaluationResponse.isPass()).as(()->
//	  		"Model Response: '%s'\nshould be supported by the context, but \nFeedback: '%s'\nScore: %f"
//	  			.formatted(result, evaluationResponse.getFeedback(), evaluationResponse.getScore()))
//	  										 .isTrue();
	}

	@Test
	void testFactChecking(@Autowired FactCheckingEvaluator factCheckingEvaluator) {
	  // Example context and claim
	  String context = "The Earth is the third planet from the Sun and the only astronomical object known to harbor life.";
	  String claim = "The Earth is the fourth planet from the Sun.";

	  // Create an EvaluationRequest
	  EvaluationRequest evaluationRequest = new EvaluationRequest(List.of(new Document(context)), claim);

	  // Perform the evaluation
	  EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

	  assertThat(evaluationResponse.isPass())
	  	.as(()->"The claim should not be supported by the context.\nFeedback:`%s`\nScore: %f".formatted(evaluationResponse.getFeedback(), evaluationResponse.getScore()))
	  	.isFalse();
	}

	@TestConfiguration
	static class LocalTestConfig {
		@Bean
		FactCheckingEvaluator factCheckingEvaluator() {
			  // Set up the Ollama API
			  OllamaApi ollamaApi = OllamaApi.builder().baseUrl("http://localhost:11434").build();

			  OllamaChatOptions chatOptions = OllamaChatOptions.builder()
							 								   .model("bespoke-minicheck") // Use bespoke-minicheck model for fact-checking
							 								   .numPredict(2)
							 								   .temperature(0.0d)
							 								   .build();
			  
			  ChatModel chatModel = OllamaChatModel.builder()
					  							   .ollamaApi(ollamaApi)
					  							   .options(chatOptions)
					  							   .build();


			  // Create the FactCheckingEvaluator
			  Builder builder = ChatClient.builder(chatModel);
			  
			  return FactCheckingEvaluator.builder(builder).build();
		}
	}
}
