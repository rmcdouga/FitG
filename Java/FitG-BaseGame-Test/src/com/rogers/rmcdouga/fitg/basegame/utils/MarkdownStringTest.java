package com.rogers.rmcdouga.fitg.basegame.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MarkdownStringTest {

	final static String testString = "String with _Italics_ and **Bold**.";
	final static MarkdownString testMdString = new MarkdownString(testString);
	
	@Test
	void testGetAsPlainText() {
		String expectedResult = testString.replaceAll("_", "").replaceAll("\\*", "");
		assertEquals(expectedResult, testMdString.getAsPlainString());
	}

	@Test
	void testGetAsHtmlText() {
		String expectedResult = "<p>String with <em>Italics</em> and <strong>Bold</strong>.</p>\n";
		assertEquals(expectedResult, testMdString.getAsHtmlString());
	}

}
