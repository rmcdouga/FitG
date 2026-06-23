package com.rogers.rmcdouga.fitg.basegame.command;

import static org.mockito.Mockito.*;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

@ExtendWith(MockitoExtension.class)
class CommandDispatcherTest {

	@Mock private Game mockGame;
	@Mock private Location mockLocation;
	@Mock private Character mockCharacter;
	@Mock private Stack mockStack;
	@Mock private Unit mockUnit;
	@Mock private CounterLocator mockCounterLocation;
	
	private CommandDispatcher underTest;
	
	@BeforeEach
	void setUp() throws Exception {
		underTest = new CommandDispatcher(mockGame);
	}
	
	@Test
	void testDispatch_CharacterMove() {
		// Arrange
		var command = new Command.MoveCommand.CharacterMove(mockCharacter, mockLocation);
		when(mockGame.move(mockCharacter, mockLocation)).thenReturn(mockCounterLocation);
		// Act
		underTest.dispatch(command);
		// Assert
		verify(mockGame).move(same(mockCharacter), same(mockLocation));
	}

	@Test
	void testDispatch_StackMove() {
		// Arrange
		var command = new Command.MoveCommand.StackMove(mockStack, mockLocation);
		when(mockGame.move(mockStack, mockLocation)).thenReturn(mockCounterLocation);
		// Act
		underTest.dispatch(command);
		// Assert
		verify(mockGame).move(same(mockStack), same(mockLocation));
	}

	@Test
	void testDispatch_UnitMove() {
		// Arrange
		var command = new Command.MoveCommand.UnitMove(mockUnit, mockLocation);
		when(mockGame.move(mockUnit, mockLocation)).thenReturn(mockCounterLocation);
		// Act
		underTest.dispatch(command);
		// Assert
		verify(mockGame).move(same(mockUnit), same(mockLocation));
	}


}
