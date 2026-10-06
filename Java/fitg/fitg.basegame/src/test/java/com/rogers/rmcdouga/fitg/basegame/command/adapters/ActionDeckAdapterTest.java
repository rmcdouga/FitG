package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.BaseGameMission;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.Mission;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.ActionDeck.ActionResult;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.ActionDeck.MissionDrawDeck;
import com.rogers.rmcdouga.fitg.basegame.command.api.external.ActionDeck.ShuffledActionDeck;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.utils.MarkdownString;

@ExtendWith(MockitoExtension.class)
class ActionDeckAdapterTest {
	@Mock Game mockGame;
	@Mock com.rogers.rmcdouga.fitg.basegame.ActionDeck mockActionDeck;
	@Mock com.rogers.rmcdouga.fitg.basegame.Action mockAction;
	
	
	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void testShuffle(boolean expectedIsSuccess) {
		// Arrange
		Environ environ = BaseGamePlanet.Adare.environ(0);
		Mission mission = BaseGameMission.COUP;

		var expectedResultDescription = new MarkdownString("Test Description");
		var expectedEnvironType = com.rogers.rmcdouga.fitg.basegame.Action.EnvironType.from(environ);
		
		when(mockGame.actionDeck()).thenReturn(mockActionDeck);
		doNothing().when(mockActionDeck).reset();
		when(mockActionDeck.draw()).thenReturn(java.util.Optional.of(mockAction));
		when(mockAction.getResultDescription(any())).thenReturn(expectedResultDescription);
		when(mockAction.isSuccessful(eq(mission), any())).thenReturn(expectedIsSuccess);
		ActionDeckAdapter underTest = new ActionDeckAdapter(mockGame);

		// Act
		ShuffledActionDeck shuffledDeck = underTest.shuffle(environ);
		MissionDrawDeck missionDeck = shuffledDeck.startMission(mission);
		Optional<ActionResult> result = missionDeck.draw();
		
		// Assert
		ActionResult returnedAction = result.get();
		assertSame(expectedResultDescription, returnedAction.resultDescription());
		assertEquals(expectedIsSuccess, returnedAction.isSuccess());
		assertEquals(expectedIsSuccess ? 1 : 0, missionDeck.numberOfSuccesses());
		verify(mockAction).getResultDescription(same(expectedEnvironType));
		verify(mockAction).isSuccessful(same(mission), same(expectedEnvironType));
		verify(mockActionDeck).reset();
		verify(mockActionDeck).draw();
		verify(mockGame).actionDeck();
	}

	@Test
	void testShuffle_EmptyDraw() {
		// Arrange
		Environ environ = BaseGamePlanet.Adare.environ(0);
		Mission mission = BaseGameMission.COUP;

		when(mockGame.actionDeck()).thenReturn(mockActionDeck);
		doNothing().when(mockActionDeck).reset();
		when(mockActionDeck.draw()).thenReturn(java.util.Optional.empty());
		ActionDeckAdapter underTest = new ActionDeckAdapter(mockGame);

		// Act
		ShuffledActionDeck shuffledDeck = underTest.shuffle(environ);
		MissionDrawDeck missionDeck = shuffledDeck.startMission(mission);
		Optional<ActionResult> result = missionDeck.draw();
		
		// Assert
		assertTrue(result.isEmpty(), "Expected an empty result when drawing from an empty deck.");
		assertEquals(0, missionDeck.numberOfSuccesses());
		verify(mockActionDeck).reset();
		verify(mockActionDeck).draw();
		verify(mockGame).actionDeck();
	}
}
