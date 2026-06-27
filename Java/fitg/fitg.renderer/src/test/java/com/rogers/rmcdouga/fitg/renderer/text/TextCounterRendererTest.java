package com.rogers.rmcdouga.fitg.renderer.text;

import org.junit.jupiter.api.Test;

/**
 * This class under tests is never called in the application. 
 * This test is used to generate a markdown table of all the characters in the game for 
 * inclusion in the LLM prompt.
 */
class TextCounterRendererTest {

	TextCounterRenderer underTest = new TextCounterRenderer();
	
	@Test
	void testRenderMarkdown() {
		// Uncomment the following line to generate the markdown table of all characters and units in the game.
//		IO.println(underTest.renderMarkdown());
	}

}
