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
class CommandValidatorTest {

	@Mock MoveCommandTracker mockMoveCommandTracker;
	@Mock Unit mockUnit;
	
	private CommandValidator underTest;
	
	@BeforeEach
	void setUp() throws Exception {
		underTest = new CommandValidator(mockMoveCommandTracker);
	}
	
	@Test
	void validateMoveCommand_Pass() {
		// Arrange
		when(mockMoveCommandTracker.hasMoved(any())).thenReturn(false);
		Command moveCommand = new Command.MoveCommand.UnitMove(mockUnit, mock(Location.class));
		// Act
		underTest.validate(moveCommand);
		// Assert
		verify(mockMoveCommandTracker).hasMoved(same(mockUnit));
	}

	@Test
	void validateMoveCommand_Fail_AlreadyMoved() {
		// Arrange
		when(mockMoveCommandTracker.hasMoved(any())).thenReturn(true);
		Command moveCommand = new Command.MoveCommand.UnitMove(mockUnit, mock(Location.class));
		// Act
		MoveCommandValidationException ex = assertThrows(MoveCommandValidationException.class, ()->underTest.validate(moveCommand));
		// Assert
		assertEquals("Counter has already moved this turn: " + mockUnit, ex.getMessage());
		verify(mockMoveCommandTracker).hasMoved(same(mockUnit));
	}

}
