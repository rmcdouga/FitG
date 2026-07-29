package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.Action.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.ActionDeck;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

public class ActionDeckAdapter implements ActionDeck {
	
	private final Game game;
	
	public ActionDeckAdapter(Game game) {
		this.game = game;
	}

	@Override
	public ShuffledActionDeck shuffle(Environ environ) {
		return new ShuffledActionDeckAdapter(game.actionDeck(), EnvironType.from(environ));
	}

	private static class ShuffledActionDeckAdapter implements ShuffledActionDeck {

		private final com.rogers.rmcdouga.fitg.basegame.ActionDeck shuffledDeck;
		private final EnvironType environType;
		
		public ShuffledActionDeckAdapter(com.rogers.rmcdouga.fitg.basegame.ActionDeck shuffledDeck, EnvironType environType) {
			this.shuffledDeck = shuffledDeck;
			this.environType = environType;
			this.shuffledDeck.reset();
		}

		@Override
		public MissionDrawDeck startMission(Mission mission) {
			return new MissionDrawDeck() {
				private int successes = 0;
				
				@Override
				public Optional<ActionResult> draw() {
					Optional<ActionResult> result = shuffledDeck.draw().map(a->new ActionResultAdapter(a.getResultDescription(environType), a.isSuccessful(mission, environType)));
					if (result.isPresent() && result.get().isSuccess()) {
						successes++;
					}
					return result;
				}

				@Override
				public int numberOfSuccesses() {
					return successes;
				}
			};
		}
	}	
	
	private record ActionResultAdapter(MarkdownString resultDescription, boolean isSuccess) implements ActionResult {}
}
