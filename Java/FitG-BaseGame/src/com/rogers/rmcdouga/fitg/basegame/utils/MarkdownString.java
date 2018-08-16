package com.rogers.rmcdouga.fitg.basegame.utils;

import java.text.AttributedString;

public class MarkdownString {
	private final String rawString;
	private final AttributedString attributedString;

	public MarkdownString(String string) {
		super();
		this.rawString = string;
		// TODO: Change this to recognize markdown and generate attributes.
		this.attributedString = new AttributedString(string);
	}

	public String getAsPlainString() {
		// TODO: Change this to return the string without the markdown in it.
		return rawString;
	}
	
	public String getAsRawString() {
		return rawString;
	}

	/**
	 * Returns as 
	 * 
	 * @return the attributed string
	 */
	public AttributedString getAsAttributedString() {
		return attributedString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getAsPlainString();
	}
	
	// TODO: Add nested classes that extends AttributedCharacterIterator.Attribute.
	//       Need Bold and Italic
}
