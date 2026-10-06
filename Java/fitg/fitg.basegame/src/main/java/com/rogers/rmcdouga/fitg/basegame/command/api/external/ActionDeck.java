package com.rogers.rmcdouga.fitg.basegame.command.api.external;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public interface ActionDeck {

	public interface ActionResult {

		MarkdownString resultDescription();
		boolean isSuccess();
	}
	
	public interface MissionDrawDeck {
		/**
		 * Draws the top card from the shuffled deck.  If the deck is empty, returns an empty Optional.
		 * 
		 * @return An Optional containing the drawn Action, or an empty Optional if the deck is empty.
		 */
		public Optional<ActionResult> draw();

		/**
		 * Number of successful draws for the mission.  
		 * 
		 * This is the number of draws for this mission that were successes.
		 * 
		 * @return Current number of successful draws for the mission.
		 */
		public int numberOfSuccesses();
	}
	
	public interface ShuffledActionDeck {
		/**
		 * Starts draws for the given mission.  
		 * 
		 * Returns a new MissionDrawDeck that can be used to draw cards for the mission.
		 * 
		 * @param mission Mission to start draws for.
		 * @return A new MissionDrawDeck for the given mission.
		 */
		MissionDrawDeck startMission(Mission mission);
	}

	ShuffledActionDeck shuffle(Environ environ);
}
