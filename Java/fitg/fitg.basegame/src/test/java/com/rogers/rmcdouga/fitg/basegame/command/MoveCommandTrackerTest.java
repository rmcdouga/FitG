package com.rogers.rmcdouga.fitg.basegame.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

class MoveCommandTrackerTest {

	private final MoveCommandTracker underTest = new MoveCommandTracker();
	private final Unit mockUnit = mock(Unit.class);
	
	@Test
	void testTrack_Move() {
		// Arrange
		Command moveCommand = new Command.MoveCommand.UnitMove(mockUnit, mock(Location.class));
		// Act
		underTest.track(moveCommand);
		// Assert
		assertTrue(underTest.hasMoved(mockUnit));
	}

	@Test
	void testHasMoved_NotMoved() {
		assertFalse(underTest.hasMoved(mockUnit));
	}

	@Disabled("This test cannot be implemented yet because the only Command implementations at this time are MoveCommands, so we cannot create a non-MoveCommand to test with.")
	@Test
	void testTrack_NonMove() {
		// Arrange
		Command nonMoveCommand = new Command.MoveCommand.UnitMove(mockUnit, mock(Location.class));
		// Act
		underTest.track(nonMoveCommand);
		// Assert
		assertFalse(underTest.hasMoved(mockUnit));
	}

}
