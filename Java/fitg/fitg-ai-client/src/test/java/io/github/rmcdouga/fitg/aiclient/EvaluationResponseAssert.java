package io.github.rmcdouga.fitg.aiclient;
import org.assertj.core.api.AbstractAssert;
import org.springframework.ai.evaluation.EvaluationResponse;

public class EvaluationResponseAssert extends AbstractAssert<EvaluationResponseAssert, EvaluationResponse> {

    private EvaluationResponseAssert(EvaluationResponse actual) {
        super(actual, EvaluationResponseAssert.class);
    }

    public static EvaluationResponseAssert assertThat(EvaluationResponse actual) {
        return new EvaluationResponseAssert(actual);
    }

    public EvaluationResponseAssert hasScoreGreaterThan(float expectedMinScore) {
        isNotNull();
        if (actual.getScore() < expectedMinScore) {
            failWithMessage("Expected evaluation score to be greater than <%s> but was <%s>", 
                expectedMinScore, actual.getScore());
        }
        return this;
    }

    public EvaluationResponseAssert containsFeedbackContaining(String expectedPhrase) {
        isNotNull();
        if (actual.getFeedback() == null || !actual.getFeedback().contains(expectedPhrase)) {
            failWithMessage("Expected feedback to contain <%s> but was <%s>", 
                expectedPhrase, actual.getFeedback());
        }
        return this;
    }
    
    public EvaluationResponseAssert passes() {
        isNotNull();
		if (!actual.isPass()) {
			failWithMessage("Expected evaluation to be a pass but was a fail\nFeedback:`%s`\nScore: %f".formatted(actual.getFeedback(), actual.getScore()));
		}
		return this;    	
    }
    
}
