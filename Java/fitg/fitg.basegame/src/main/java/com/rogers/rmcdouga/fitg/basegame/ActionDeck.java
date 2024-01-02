package com.rogers.rmcdouga.fitg.basegame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rogers.rmcdouga.fitg.basegame.Action.ActionFactory;
import com.rogers.rmcdouga.fitg.basegame.ActionDeck.ActionDeckState;

public class ActionDeck implements GameState<ActionDeckState> {
	
	private final ActionFactory actionFactory = Action.defaultFactory();
	private final Deque<Action> drawPile = new ArrayDeque<>(actionFactory.allActions());
	private final Deque<Action> discardPile = new ArrayDeque<>(actionFactory.numberOfActions());
	
	protected ActionDeck() {
		this.reset();
	}

	public Optional<Action> draw() {
		// Draw card from the top, place it on the discard pile and return it.
		// return Empty() if there are no cards left in the draw pile.
		Optional<Action> card = Optional.ofNullable(drawPile.poll());
		if (card.isPresent()) {
			discardPile.push(card.get());
		}
		return card;
	}
	
	public Optional<Action> peekDiscard() {
		// Peek at the top card in the discard pile.
		// return Empty() if there are no cards in the draw pile.
		return Optional.ofNullable(discardPile.peek());
	}
	
	public Optional<Action> peekDiscard(Action action) {
		// Peek at the card after this card in the discard pile.
		// return Empty() if there are no cards after this card in the draw pile.
		boolean foundAction = false;
		Iterator<Action> iterator = discardPile.iterator();
		while (iterator.hasNext() && !foundAction) {
			Action nextAction = iterator.next();
			if (action.equals(nextAction)) {
				foundAction = true;
			}
		}
		
		if (iterator.hasNext()) {
			// Since there is a next card, we return it.
			return Optional.of(iterator.next());
		} else if (foundAction) {
			// There's no next card, but we found the Action, so it must be the last card.
			return Optional.empty();
		} else {
			// We didn't find the Action provided, so we throw an exception
			throw new IllegalStateException("Unable to find Action Card #" + action.cardNumber() + " in discard deck.");
		}
	}
	
	public void reset() {
		// Transfer the discards to temporary list, shuffle them and then put them into the  draw pile.
		List<Action> tmpList = new ArrayList<>(actionFactory.numberOfActions());
		tmpList.addAll(drawPile);
		drawPile.clear();
		tmpList.addAll(discardPile);
		discardPile.clear();
		Collections.shuffle(tmpList);
		drawPile.addAll(tmpList);
	}
	
	public int numberOfCardsInDrawPile() {
		return drawPile.size();
	}
	
	public int numberOfCardsInDiscard() {
		return discardPile.size();
	}

	
	public record ActionDeckState(List<Integer> drawPileCards, List<Integer> discardPileCards) {};
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.GameState#getState()
	 */
	@Override
	public ActionDeckState getState() {
		List<Integer> drawPileCards = drawPile.stream().map(Card::cardNumber).collect(Collectors.toList());
		List<Integer> discardPileCards = discardPile.stream().map(Card::cardNumber).collect(Collectors.toList());
		return new ActionDeckState(drawPileCards, discardPileCards);
	}

	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.GameState#setState(java.util.Map)
	 */
	@Override
	public void setState(ActionDeckState state) {
		drawPile.clear();
		discardPile.clear();
		state.drawPileCards.stream().map(actionFactory::getAction).map(Optional::get).forEach(drawPile::add);
		state.discardPileCards.stream().map(actionFactory::getAction).map(Optional::get).forEach(discardPile::add);
	}
}
