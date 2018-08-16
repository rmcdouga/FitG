package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Mission extends Card {

	char mnemonic();

	String missionName();

	MarkdownString description();

	MarkdownString result();

}