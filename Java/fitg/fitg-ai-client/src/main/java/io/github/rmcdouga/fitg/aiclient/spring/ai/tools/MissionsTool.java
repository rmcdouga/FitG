package io.github.rmcdouga.fitg.aiclient.spring.ai.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.tool.annotation.Tool;

import com.rogers.rmcdouga.fitg.basegame.MissionGroup;
import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.CharacterFinder.CharacterEligibleForMission;

public class MissionsTool implements SpringAiTool {
	private final Map<String, MissionGroup> missionGroups = new HashMap<>();
	
	private final CharacterFinder characterFinder;
	
	public MissionsTool(CharacterFinder characterFinder) {
		this.characterFinder = characterFinder;
	}

	// Display a list of environs that contain characters, that have not yet been part of a mission group
	// Ask the user to select one of the environs
	// Display a list of characters in that environ and available missions
	// Ask the user to select one or more of the characters to form a mission group
	// Display a list of available missions.
	// Assign a mission to a mission group
	// Once all mission groups have been designated, resolve each mission group in order decided by player
	//    Draw a mission card for the mission group, display the results
	//    Ask the user if they want to continue with the mission or abort
	
	void startMissionPhase() {
		missionGroups.clear();
	}
	
	void createMissionGroup(String environ, List<String> characterIds, String missionId) {
		
	};

	@Tool(description = "Supplies a list of characters available for missions for a particular faction.")
	Stream<CharacterEligibleForMission> listCharactersAvailableForMissions(String faction) {
		return characterFinder.findAllCharactersEligibleForMissions(Faction.fromString(faction)); 
	}
}
