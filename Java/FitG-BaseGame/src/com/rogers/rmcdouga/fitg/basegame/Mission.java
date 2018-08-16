package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Mission extends Card {

	char getMnemonic();

	String getName();

	MarkdownString getDescription();

}