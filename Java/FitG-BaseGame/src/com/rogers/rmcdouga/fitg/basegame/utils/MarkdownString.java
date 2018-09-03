package com.rogers.rmcdouga.fitg.basegame.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class MarkdownString {
	private final String rawString;

	public MarkdownString(String string) {
		super();
		this.rawString = string;
	}

	public String getAsPlainString() {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(rawString);
		TextContentRenderer renderer = TextContentRenderer.builder().build();
		return renderer.render(document);
	}
	
	public String getAsRawString() {
		return rawString;
	}

	public String getAsHtmlString() {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(rawString);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getAsPlainString();
	}
	
}
