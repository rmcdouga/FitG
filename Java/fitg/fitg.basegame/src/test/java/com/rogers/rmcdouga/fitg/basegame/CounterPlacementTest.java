package com.rogers.rmcdouga.fitg.basegame;

import static com.rogers.rmcdouga.fitg.basegame.map.BaseGameEnvironType.*;
import static com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship.*;
import static com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship.*;
import static com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit.Liquid_2_3;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations.PlacedCounter;
import com.rogers.rmcdouga.fitg.basegame.box.BaseGameBox;
import com.rogers.rmcdouga.fitg.basegame.box.GameBox;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.SpaceshipStack;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;

class CounterPlacementTest {

	private static final Environ LOCATION = Adare.environ(Urban).get();
	private static final Environ NEW_LOCATION = Adare.environ(Wild).get();
	
	// It's debatable whether I should mock the dependencies, but at the moment it's just easier to use the real classes.
	private final StackManager stackMgr = new StackManager();
	private final GameBox gameBox = BaseGameBox.create();
	private final CounterLocations counterLocations = new CounterLocations(gameBox);
	
	private final Stack testStack = stackMgr.of(Adam_Starlight, Els_Taroff, Liquid_2_3);
	private final Stack testSpaceshipStack = stackMgr.of((Spaceship)Imperial_Spaceship, Senator_Dermond);

	private final PlacedCounter<BaseGameCharacter> underTestCounter = counterLocations.placeCounter(LOCATION, Jon_Kidu).get();
	private final PlacedCounter<Stack> underTestStack = counterLocations.placeCounter(LOCATION, testStack).get();
	private final PlacedCounter<Stack> underTestSpaceshipStack = counterLocations.placeCounter(LOCATION, testSpaceshipStack).get();
	
	@Test
	void testPlaceCounter_Counter() {
		assertEquals(Jon_Kidu, underTestCounter.counter());
		assertEquals(LOCATION, underTestCounter.location());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Jon_Kidu), "Expected Jon Kidu to be IN_PLAY");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
	}
	
	@Test
	void testPlaceCounter_Stack() {
		assertTrue(underTestStack.counter().contains(Adam_Starlight));
		assertTrue(underTestStack.counter().contains(Els_Taroff));
		assertEquals(LOCATION, underTestStack.location());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Adam_Starlight), "Expected Adam Starlight to be IN_PLAY");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
		assertTrue(gameBox.get(Liquid_2_3).isEmpty());	// There's only one of these, so we should not be able to re-get it.
	}
	
	@Test
	void testPlaceCounter_SpaceshipStack() {
		assertTrue(((SpaceshipStack)underTestSpaceshipStack.counter()).spaceship() instanceof ImperialSpaceship);
		assertTrue(underTestSpaceshipStack.counter().contains(Senator_Dermond));
		assertEquals(LOCATION, underTestSpaceshipStack.location());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Senator_Dermond), "Expected Senator Dermond to be IN_PLAY");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
	}

	@Test
	void testPlaceCounter_StackList() {
		PlacedCounter<Stack> placedCounter = counterLocations.placeCounter(LOCATION, Zina_Adora, Ly_Mantok).get();
		assertEquals(LOCATION, placedCounter.location());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Zina_Adora), "Expected Zina Adora to be IN_PLAY");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
		assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Ly_Mantok), "Expected Zina Adora to be IN_PLAY");
	}

	@Test
	void testPlaceCounter_SpaceshipStackList() {
		PlacedCounter<SpaceshipStack> placedCounter = counterLocations.placeCounter(LOCATION, Explorer, Zina_Adora, Ly_Mantok).get();
		assertEquals(LOCATION, placedCounter.location());
		assertEquals(Explorer, placedCounter.counter().spaceship());
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Zina_Adora), "Expected Zina Adora to be IN_PLAY");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("IN_PLAY"));
		assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Ly_Mantok), "Expected Zina Adora to be IN_PLAY");
	}

	@Test
	void testMove_Counter() {
		assertEquals(LOCATION, underTestCounter.location());
		underTestCounter.move(NEW_LOCATION);
		assertEquals(NEW_LOCATION, underTestCounter.location());
	}

	@Test
	void testMove_Stack() {
		assertEquals(LOCATION, underTestStack.location());
		underTestStack.move(NEW_LOCATION);
		assertEquals(NEW_LOCATION, underTestStack.location());
	}

	@Test
	void testMove_StackSpaceship() {
		assertEquals(LOCATION, underTestSpaceshipStack.location());
		underTestSpaceshipStack.move(NEW_LOCATION);
		assertEquals(NEW_LOCATION, underTestSpaceshipStack.location());
	}

	@Test
	void testReturnToBox_Counter() {
		underTestCounter.returnToBox();
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Jon_Kidu), "Expected Jon Kidu to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
	}

	@Test
	void testReturnToBox_Stack() {
		underTestStack.returnToBox();
		assertTrue(gameBox.get(Liquid_2_3).isPresent());	// Should be able to re-get military units.
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Adam_Starlight), "Expected Adam Starlight to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
	}

	@Test
	void testReturnToBox_SpaceshipStack() {
		assertTrue(gameBox.get(Imperial_Spaceship).isPresent());	// Should be able to get both remaining imperial spaceships.
		assertTrue(gameBox.get(Imperial_Spaceship).isPresent());	// Should be able to get both remaining imperial spaceships.
		assertFalse(gameBox.get(Imperial_Spaceship).isPresent());	// Should be no imperial spaceships left.
		underTestSpaceshipStack.returnToBox();
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Senator_Dermond), "Expected Senator Dermond to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
		assertTrue(gameBox.get(Imperial_Spaceship).isPresent());	// Should now be able to re-get the imperial spaceships.
		assertFalse(gameBox.get(Imperial_Spaceship).isPresent());	// Should be no imperial spaceships left after that.
	}

	@Test
	void testRemoveFromPlay_Counter() {
		underTestCounter.removeFromPlay();
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Jon_Kidu), "Expected Jon Kidu to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
	}

	@Test
	void testRemoveFromPlay_Stack() {
		underTestStack.removeFromPlay();
		assertFalse(gameBox.get(Liquid_2_3).isEmpty());	// Should not be able to re-get military units.
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Adam_Starlight), "Expected Adam Starlight to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
	}

	@Test
	void testRemoveFromPlay_SpaceshipStack() {
		assertTrue(gameBox.get(Imperial_Spaceship).isPresent());	// Should be able to get both remaining imperial spaceships.
		assertTrue(gameBox.get(Imperial_Spaceship).isPresent());	// Should be able to get both remaining imperial spaceships.
		assertFalse(gameBox.get(Imperial_Spaceship).isPresent());	// Should be no imperial spaceships left.
		underTestSpaceshipStack.removeFromPlay();
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, ()->gameBox.getCharacter(Senator_Dermond), "Expected Senator Dermond to be DEAD");
		String msg = ex.getMessage();
		assertNotNull(msg);
		assertThat(msg, containsString("DEAD"));
		assertFalse(gameBox.get(Imperial_Spaceship).isPresent());	// Should be no imperial spaceships left after that.
	}
}
