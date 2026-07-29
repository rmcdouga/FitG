package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.units.Character;

@ExtendWith(MockitoExtension.class)
class MissionGroupTest {

	@Mock private Game mockGame;
	@Mock private Mission mockMission;
	@Mock private Character mockCharacterOne;
	@Mock private Character mockCharacterTwo;
	@Mock private Environ mockEnviron;
	@Mock private Environ otherEnviron;

	@Test
	void exposesCharactersMissionAndEnviron() {
		when(mockGame.locationOf(mockCharacterOne)).thenReturn(Optional.of(mockEnviron));
		when(mockGame.locationOf(mockCharacterTwo)).thenReturn(Optional.of(mockEnviron));

		var underTest = new MissionGroup(mockGame, List.of(mockCharacterOne, mockCharacterTwo), mockMission);

		assertEquals(List.of(mockCharacterOne, mockCharacterTwo), underTest.characters());
		assertSame(mockMission, underTest.mission());
		assertSame(mockEnviron, underTest.environ());
		assertThrows(UnsupportedOperationException.class, () -> underTest.characters().add(mockCharacterOne));
	}

	@Test
	void rejectsCharacterNotInAnEnviron() {
		when(mockCharacterOne.name()).thenReturn("Alpha");
		when(mockGame.locationOf(mockCharacterOne)).thenReturn(Optional.empty());

		var exception = assertThrows(IllegalArgumentException.class,
				() -> new MissionGroup(mockGame, List.of(mockCharacterOne), mockMission));

		assertEquals("Character 'Alpha' is not in an environ.", exception.getMessage());
	}

	@Test
	void rejectsCharactersFromDifferentEnvirons() {
		when(mockGame.locationOf(mockCharacterOne)).thenReturn(Optional.of(mockEnviron));
		when(mockGame.locationOf(mockCharacterTwo)).thenReturn(Optional.of(otherEnviron));

		var exception = assertThrows(IllegalArgumentException.class,
				() -> new MissionGroup(mockGame, List.of(mockCharacterOne, mockCharacterTwo), mockMission));

		assertEquals("All characters must be in the same environ.", exception.getMessage());
	}
}
