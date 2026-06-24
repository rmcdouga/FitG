package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.command.UserCommandProcessing;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command;
import com.rogers.rmcdouga.fitg.basegame.command.api.internal.Command.MoveCommand;
import com.rogers.rmcdouga.fitg.basegame.map.Location;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

@ExtendWith(MockitoExtension.class)
class MoverAdapterTest {

    @Mock private UserCommandProcessing userCommandProcessing;
    @Mock private CounterFinder counterFinder;
    @Mock private LocationFinder locationFinder;
    @Mock private Unit unit;
    @Mock private Character character;
    @Mock private Location destination;
    @Captor private ArgumentCaptor<Command> commandCaptor;

    private Stack stack;
    private MoverAdapter underTest;

    @BeforeEach
    void setUp() {
        stack = new StackManager().of();
        underTest = new MoverAdapter(userCommandProcessing, counterFinder, locationFinder);
    }

    @Test
    void moveUnitCounterWhenUnitIsFoundSendsUnitMoveCommand() {
        when(counterFinder.findCounter("Militia", "Rigel", "Surface")).thenReturn(Optional.of(unit));
        when(locationFinder.findLocation("Sirius", "Drift")).thenReturn(destination);

        var returnedMover = underTest.moveUnitCounter("Militia", "Rigel", "Surface", "Sirius", "Drift");

        assertSame(underTest, returnedMover);
        var command = captureMoveCommand();
        var unitMove = assertInstanceOf(MoveCommand.UnitMove.class, command);
        assertSame(unit, unitMove.unit());
        assertSame(destination, unitMove.destination());
    }

    @Test
    void moveUnitCounterWhenUnitIsNotFoundDoesNotProcessCommand() {
        when(counterFinder.findCounter("Militia", "Rigel", "Surface")).thenReturn(Optional.empty());

        var returnedMover = underTest.moveUnitCounter("Militia", "Rigel", "Surface", "Sirius", "Drift");

        assertSame(underTest, returnedMover);
        verify(locationFinder, never()).findLocation("Sirius", "Drift");
        verify(userCommandProcessing, never()).processCommand(org.mockito.ArgumentMatchers.any(Command.class));
    }

    @Test
    void moveCounterWhenUnitIsFoundSendsUnitMoveCommand() {
        when(counterFinder.findCounter("unit-1")).thenReturn(Optional.of(unit));
        when(locationFinder.findLocation("Sirius", "Drift")).thenReturn(destination);

        var returnedMover = underTest.moveCounter("unit-1", "Sirius", "Drift");

        assertSame(underTest, returnedMover);
        var command = captureMoveCommand();
        var unitMove = assertInstanceOf(MoveCommand.UnitMove.class, command);
        assertSame(unit, unitMove.unit());
        assertSame(destination, unitMove.destination());
    }

     @Test
    void moveCounterWithNonUnitCharacterOrStackThrowsIllegalArgumentException() {
        Counter unknownCounter = mock(Counter.class);
        when(unknownCounter.id()).thenReturn("non-specific-counter");
        when(counterFinder.findCounter("non-specific-counter")).thenReturn(Optional.of(unknownCounter));

        assertThrows(IllegalArgumentException.class, () -> underTest.moveCounter("non-specific-counter", "Sirius", "Drift"));
    }

    @Test
    void moveCounterWhenCharacterIsFoundSendsCharacterMoveCommand() {
        when(counterFinder.findCounter("character-1")).thenReturn(Optional.of(character));
        when(locationFinder.findLocation("Sirius", "Drift")).thenReturn(destination);

        underTest.moveCounter("character-1", "Sirius", "Drift");

        var command = captureMoveCommand();
        var characterMove = assertInstanceOf(MoveCommand.CharacterMove.class, command);
        assertSame(character, characterMove.character());
        assertSame(destination, characterMove.destination());
    }

    @Test
    void moveCounterWhenStackIsFoundSendsStackMoveCommand() {
        when(counterFinder.findCounter("stack-1")).thenReturn(Optional.of(stack));
        when(locationFinder.findLocation("Sirius", "Drift")).thenReturn(destination);

        underTest.moveCounter("stack-1", "Sirius", "Drift");

        var command = captureMoveCommand();
        var stackMove = assertInstanceOf(MoveCommand.StackMove.class, command);
        assertSame(stack, stackMove.stack());
        assertSame(destination, stackMove.destination());
    }

    @Test
    void moveCounterWhenCounterIsNotFoundDoesNotProcessCommand() {
        when(counterFinder.findCounter("missing-counter")).thenReturn(Optional.empty());

        var returnedMover = underTest.moveCounter("missing-counter", "Sirius", "Drift");

        assertSame(underTest, returnedMover);
        verify(locationFinder, never()).findLocation("Sirius", "Drift");
        verify(userCommandProcessing, never()).processCommand(org.mockito.ArgumentMatchers.any(Command.class));
    }

    private MoveCommand captureMoveCommand() {
        verify(userCommandProcessing).processCommand(commandCaptor.capture());
        return assertInstanceOf(MoveCommand.class, commandCaptor.getValue());
    }
}