package com.rogers.rmcdouga.fitg.basegame.map;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.Adare;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb.Level;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb.State;

class BaseGamePdbManagerTest {

	private final BaseGamePdbManager underTest = BaseGamePdbManager.create();
	
	@Test
	void testGetPdb() {
		Pdb result = underTest.getPdb(Adare);
		testPdb(result, State.UP, Level.ZERO);
	}

	@Test
	void testIncreaseDecreasePdb() {
		testPdb(underTest.getPdb(Adare), State.UP, Level.ZERO);						// establish baseline
		testPdb(underTest.increasePdb(Adare).getPdb(Adare), State.UP, Level.ONE);
		testPdb(underTest.increasePdb(Adare).getPdb(Adare), State.UP, Level.TWO);
		testPdb(underTest.increasePdb(Adare).getPdb(Adare), State.UP, Level.TWO);		// Can't increase past 2
		testPdb(underTest.decreasePdb(Adare).getPdb(Adare), State.UP, Level.ONE);
		testPdb(underTest.decreasePdb(Adare).getPdb(Adare), State.UP, Level.ZERO);
		testPdb(underTest.decreasePdb(Adare).getPdb(Adare), State.UP, Level.ZERO);	// Can't decrease past 0		
	}

	@Test
	void testUpDownPdb() {
		testPdb(underTest.getPdb(Adare), State.UP, Level.ZERO);						// establish baseline
		testPdb(underTest.downPdb(Adare).getPdb(Adare), State.DOWN, Level.ZERO);
		testPdb(underTest.downPdb(Adare).getPdb(Adare), State.DOWN, Level.ZERO);
		testPdb(underTest.upPdb(Adare).getPdb(Adare), State.UP, Level.ZERO);
		testPdb(underTest.upPdb(Adare).getPdb(Adare), State.UP, Level.ZERO);
	}

	private void testPdb(Pdb pdb, State state, Level level) {
		assertAll(
				()->assertEquals(state == State.UP, pdb.isUp()),
				()->assertEquals(level, pdb.level())
				);
	}
	
	@Test
	void testGetPdb_InvalidPlanet() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->underTest.getPdb(FAKE_PLANET));
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, allOf(containsString("FakePlanet"), containsString("is not a BaseGamePlanet")));
	}

	private static final Planet FAKE_PLANET = new Planet() {

		@Override
		public LoyaltyType getCurrentLoyalty() {
			return null;
		}

		@Override
		public void setCurrentLoyalty(LoyaltyType currentLoyalty) {
		}

		@Override
		public String getName() {
			return "FakePlanet";
		}

		@Override
		public int getId() {
			return 0;
		}

		@Override
		public BaseGameCapitalType getCapitalType() {
			return null;
		}

		@Override
		public List<? extends Environ> listEnvirons() {
			return null;
		}

		@Override
		public Optional<? extends RaceType> getHomeworld() {
			return null;
		}

		@Override
		public BaseGamePlanetaryControlType getControlA() {
			return null;
		}

		@Override
		public LoyaltyType getStartingLoyaltyS() {
			return null;
		}

		@Override
		public LoyaltyType getStartingLoyaltyA() {
			return null;
		}

		@Override
		public BaseGamePlanetaryControlType getCurrentControl() {
			return null;
		}

		@Override
		public void setCurrentControl(BaseGamePlanetaryControlType currentControl) {
		}

		@Override
		public BaseGameStarSystem getStarSystem() {
			return null;
		}

		@Override
		public boolean hasSecret() {
			return false;
		}
	};

}
