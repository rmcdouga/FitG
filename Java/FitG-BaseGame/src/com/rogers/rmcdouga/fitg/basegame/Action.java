package com.rogers.rmcdouga.fitg.basegame;

import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Action extends Card {

	Set<Mission> missions();
	MarkdownString urbanResult();
	MarkdownString specialResult();
	MarkdownString wildResult();
	
}
