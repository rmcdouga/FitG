package com.rogers.rmcdouga.fitg.basegame.command.adapters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rogers.rmcdouga.fitg.basegame.command.CommandDispatcher;
import com.rogers.rmcdouga.fitg.basegame.query.api.CounterFinder;
import com.rogers.rmcdouga.fitg.basegame.query.api.LocationFinder;

@Disabled
@ExtendWith(MockitoExtension.class)
class MoverAdapterTest {

	@Mock
	private CommandDispatcher mockCommandDispatcher;
	@Mock
	private CounterFinder mockCounterFinder;
	@Mock
	private LocationFinder mockLocationFinder;
	
	MoverAdapter underTest;
	
	@BeforeEach
	void setUp() throws Exception {
		underTest = new MoverAdapter(mockCommandDispatcher, mockCounterFinder, mockLocationFinder);
	}

	@Test
	void testMoveUnitStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	void testMoveUnitStringString() {
		fail("Not yet implemented");
	}

}
