package com.rogers.rmcdouga.fitg.basegame;

import java.util.Optional;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface Mission extends Card {

	public char mnemonic();
	public String missionName();
	public MarkdownString description();
	public MarkdownString result();

	public static interface MissionFactory {
		public Optional<Mission> getMissionFromMnemonic(char mnemonic);
		public Optional<Mission> getMissionFromMnemonic(String mnemonic);
		public Set<Mission> getMissionsFromMnemonics(Set<Character> mnemonics);
		public Set<Mission> getMissionsFromMnemonics(String mnemonics);
	}
	
	public static MissionFactory defaultFactory() {
		return MissionEnum.missionFactory();
	}
}