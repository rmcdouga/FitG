package com.rogers.rmcdouga.fitg.basegame.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.command.CommandValidator.MoveCommandValidationException;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

@ExtendWith(MockitoExtension.class)
class UserCommandProcessingTest {

	@Mock private CommandValidator commandValidator;
	@Mock private CommandDispatcher commandDispatcher; 
	@Mock private MoveCommandTracker moveCommandTracker;
	
	UserCommandProcessing underTest;
	
	@BeforeEach
	void setUp() throws Exception {
		underTest = new UserCommandProcessing(commandValidator, commandDispatcher, moveCommandTracker);
	}

	@Test
	void testProcessCommand_HappyPath() {
		// Arrange
		doNothing().when(commandValidator).validate(any());
		doNothing().when(commandDispatcher).dispatch(any());
		doNothing().when(moveCommandTracker).track(any());

		Command moveCommand = new Command.MoveCommand.UnitMove(mock(Unit.class), mock(Location.class));

		// Act
		underTest.processCommand(moveCommand);
		// Assert
		verify(commandValidator).validate(same(moveCommand));
		verify(commandDispatcher).dispatch(same(moveCommand));
		verify(moveCommandTracker).track(same(moveCommand));
	}

	@Test
	void testProcessCommand_ThrowsException() {
		// Arrange
		String exMessage = "Exception message";
		doThrow(new CommandValidator.MoveCommandValidationException(exMessage)).when(commandValidator).validate(any());

		Command moveCommand = new Command.MoveCommand.UnitMove(mock(Unit.class), mock(Location.class));

		// Act & Assert
		MoveCommandValidationException ex = assertThrows(CommandValidator.MoveCommandValidationException.class, () -> underTest.processCommand(moveCommand));
		assertEquals(exMessage, ex.getMessage());
		verify(commandValidator).validate(same(moveCommand));
		verifyNoInteractions(commandDispatcher, moveCommandTracker);
	}
}
