package io.github.rmcdouga.fitg.aiclient;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;

public class EvaluationRequestHelper {
	
	static EvaluationRequest evaluationRequestOf(String evalContext, String aiClaim) {
		return new EvaluationRequest(List.of(new Document(evalContext)), aiClaim);
	}
}
