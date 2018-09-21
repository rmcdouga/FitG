package com.rogers.rmcdouga.fitg.basegame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.Action.ActionFactory;

class ActionDeckTest {
	final static ActionFactory factory = Action.defaultFactory();
	final static int numberOfActions = factory.numberOfActions(); 

	@Test
	void testActionDeck() {
		ActionDeck actionDeck = new ActionDeck();
		checkForResetDeck(actionDeck);
	}

	@Test
	void testDraw() {
		final int numDraws = 2;

		ActionDeck actionDeck = new ActionDeck();
		checkDraw(numDraws, actionDeck);
		checkDraw(numDraws, actionDeck);
	}

	@Test
	void testDraw_emptyDeck() {
		// Check that after you draw all cars you get a empty result back.
		ActionDeck actionDeck = new ActionDeck();
		checkDraw(numberOfActions, actionDeck);	// Draw all cards.
		Optional<Action> drawWhenEmpty = actionDeck.draw();
		assertFalse(drawWhenEmpty.isPresent(), "Drawing from an empty deck should result in an empty result.");
	}

	@Test
	void testPeekDiscard() {
		final int numDraws = 2;

		ActionDeck actionDeck = new ActionDeck();
		checkDraw(numDraws, actionDeck);
		
		Optional<Action> drawnAction3 = actionDeck.draw();
		assertTrue(drawnAction3.isPresent(), "Expecting to draw a card, but none was found.");
		
		Optional<Action> peekDiscard = actionDeck.peekDiscard();
		assertTrue(peekDiscard.isPresent(), "Expecting to draw a card, but none was found.");
		
		Action discardAction = peekDiscard.get();
		Action lastDrawnAction = drawnAction3.get();
		assertEquals(lastDrawnAction, discardAction, "Expected the discard (" + lastDrawnAction.cardNumber() + ") to be the last card drawn (" + discardAction.cardNumber()+ ").");
	}

	@Test
	void testPeekDiscardAction() {
		ActionDeck actionDeck = new ActionDeck();
		Optional<Action> drawnActionOptional1 = actionDeck.draw();
		assertTrue(drawnActionOptional1.isPresent(), "Expecting to draw a card, but none was found.");
		Action drawnAction1 = drawnActionOptional1.get();
		Optional<Action> drawnActionOptional2 = actionDeck.draw();
		assertTrue(drawnActionOptional2.isPresent(), "Expecting to draw a card, but none was found.");
		Action drawnAction2 = drawnActionOptional2.get();
		Optional<Action> drawnActionOptional3 = actionDeck.draw();
		assertTrue(drawnActionOptional3.isPresent(), "Expecting to draw a card, but none was found.");
		Action drawnAction3 = drawnActionOptional3.get();

		
		Optional<Action> peekCard1 = actionDeck.peekDiscard(drawnAction3);
		assertTrue(peekCard1.isPresent(), "Should have found a discarded card.");
		assertEquals(drawnAction2, peekCard1.get(), "Expected to retrieve card (" + drawnAction2.cardNumber() + ") but found (" + peekCard1.get().cardNumber() + ") instead.");
		Optional<Action> peekCard2 = actionDeck.peekDiscard(drawnAction2);
		assertTrue(peekCard2.isPresent(), "Should have found a discarded card.");
		assertEquals(drawnAction1, peekCard2.get(), "Expected to retrieve card (" + drawnAction1.cardNumber() + ") but found (" + peekCard2.get().cardNumber() + ") instead.");
		Optional<Action> peekCard3 = actionDeck.peekDiscard(drawnAction1);
		assertFalse(peekCard3.isPresent(), "Should not have found a discarded card, should have found the end of the deck.");
	}

	@Test
	void testPeekDiscardAction_InvalidAction() {
		final int numDraws = 3;
		ActionDeck actionDeck = new ActionDeck();
		
		// Draw some cards and maintain a list of what was drawn.
		List<Action> actionsDrawn = new ArrayList<>(numDraws);
		for(int i = 0; i < numDraws; i++) {
			Optional<Action> drawnActionOptional = actionDeck.draw();
			assertTrue(drawnActionOptional.isPresent(), "Expecting to draw a card, but none was found.");
			actionsDrawn.add(drawnActionOptional.get());
		}
		
		// Construct a set of actions that were not drawn.
		Set<Action> actionsNotDrawn = new HashSet<>(factory.allActions());
		boolean changed = actionsNotDrawn.removeAll(actionsDrawn);
		assertTrue(changed, "Expected that removing drawn actions woulc change the actionsNotDrawn set.");
		
		// Get the first action that was not drawn.
		Iterator<Action> iterator = actionsNotDrawn.iterator();
		assertTrue(iterator.hasNext(), "Expected the there would be at least one action that had not been drawn.");
		Action actionNotDrawn = iterator.next();
		
		final IllegalStateException thrownException = assertThrows(
				IllegalStateException.class,
                () -> { Optional<Action> peekCard = actionDeck.peekDiscard(actionNotDrawn); }
        );
		assertTrue(thrownException.getMessage().contains("Unable to find Action Card"), "Expected exceptoin message to contain 'Unable to find Action Card' somwhere in it.");
		assertTrue(thrownException.getMessage().contains(Integer.toString(actionNotDrawn.cardNumber())), "Expected exception message to cintain the card number of the invalid card.");
	}
	
	@Test
	void testReset() {
		final int numDraws = 2;

		ActionDeck actionDeck = new ActionDeck();
		checkDraw(numDraws, actionDeck);
		
		actionDeck.reset();
		
		checkForResetDeck(actionDeck);
	}

	private void checkForResetDeck(ActionDeck actionDeck) {
		assertEquals(numberOfActions, actionDeck.numberOfCardsInDrawPile(), "Expect DrawPile to contain all actions." );
		assertEquals(0, actionDeck.numberOfCardsInDiscard() , "Expect DiscardPile to be empty.");
		
		List<Action> actionList = new ArrayList<>(factory.allActions());
		List<Action> drawList = new ArrayList<>(factory.numberOfActions());
		Optional<Action> cardDrawn = actionDeck.draw();
		while (cardDrawn.isPresent()) {
			drawList.add(cardDrawn.get());
			cardDrawn = actionDeck.draw();
		}
		assertNotEquals(actionList, drawList, "Expect the shuffled draw deck to be different than the list of available actions.");
	}

	private void checkDraw(final int numDraws, ActionDeck actionDeck) {
		final int startingDrawSize = actionDeck.numberOfCardsInDrawPile();
		final int startingDiscardSize = actionDeck.numberOfCardsInDiscard();
		
		for (int i = 0; i < numDraws; i++) {
			Optional<Action> drawnAction = actionDeck.draw();
			assertTrue(drawnAction.isPresent(), "Expecting to draw a card, but none was found.");
		}

		assertEquals(startingDrawSize - numDraws, actionDeck.numberOfCardsInDrawPile(), "Expected the number of draws in the drawpile to be total minus the number of cards drawn.");
		assertEquals(startingDiscardSize + numDraws, actionDeck.numberOfCardsInDiscard(), "Expected the number of draws in the discardpile to be the number of cards drawn.");
	}

	@Test
	void testGameState() {
		final int numDraws = 2;

		ActionDeck actionDeck = new ActionDeck();
		checkDraw(numDraws, actionDeck);
		assertEquals(2, actionDeck.numberOfCardsInDiscard(), "Expected 2 cards to be in actionDeck discard initially");

		Map<String, Object> state = actionDeck.getState();

		ActionDeck actionDeck2 = new ActionDeck();
		assertEquals(0, actionDeck2.numberOfCardsInDiscard(), "Expected no cards to be in actionDeck2 discard initially");
		actionDeck2.setState(state);
		
		// Compare actionDeck and actionDeck2 - they should be the same.
		assertEquals(actionDeck.numberOfCardsInDrawPile(), actionDeck2.numberOfCardsInDrawPile(), "Expected drawPiles to have the same number of cards."); 
		assertEquals(actionDeck.numberOfCardsInDiscard(), actionDeck2.numberOfCardsInDiscard(), "Expected discardPiles to have the same number of cards.");
		
		assertEquals(actionDeck.peekDiscard(), actionDeck2.peekDiscard(), "Expected the to decks should be equal after state save/load.");
		while (actionDeck.numberOfCardsInDrawPile() > 0) {
			assertEquals(actionDeck.draw(), actionDeck2.draw(), "Expected the to decks should be equal after state save/load.");
		}
	}
}
