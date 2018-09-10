package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

import com.github.rmcdouga.fitg.webapp.FitGWebApplication;
import com.rogers.rmcdouga.fitg.basegame.Action;
import com.rogers.rmcdouga.fitg.basegame.Action.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.ActionDeck;

@Path(ActionDeckResources.ACTION_DECK_PATH)
public class ActionDeckResources {
	public static final String ACTION_DECK_PATH = "/ActionDeck";
	public static final String DRAW_PATH = "/Draw";
	public static final String RESET_PATH = "/Reset";
	public static final String DISCARD_PATH = "/Discard";
	private static final String DISCARD_PATH_CARD_NO = DISCARD_PATH + "/{cardNo}";

	public static final String CARD_NO_ID = "card_no_id";
	public static final String EMPTY_DISCARD_ID = "empty_discard_id";
	public static final String PREV_CARD_NO_ID = "prev_cardno_id";
	public static final String NEXT_CARD_NO_ID = "next_cardno_id";
	
	// Specifies that the method processes HTTP GET requests
	@GET
	@Path(DISCARD_PATH_CARD_NO)
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/ActionCard.mustache")
	public Map<String, Object> actionCardDiscard(@PathParam("cardNo") int cardNo) {
		Map<String, Object> parms = new HashMap<>();
		
		System.out.println("Retreiving discard #" + cardNo);
		boolean hasCard = false;

		// TBD - Clean this up, surely this can be more elegant.
		ActionDeck actionDeck = FitGWebApplication.game.actionDeck();
		Optional<Action> topDiscard = actionDeck.peekDiscard();
		if (topDiscard.isPresent()) {
			hasCard = true;
			
			int numberOfCardsInDiscard = actionDeck.numberOfCardsInDiscard();
			if (cardNo > numberOfCardsInDiscard) {
				throw new NotFoundException("Invalid card number, there are only " + numberOfCardsInDiscard + " cards in the discard pile.");
			}
			// if cardNo is > 0, then we go through the discard pile to get the indicated card.
			Optional<Action> nextDiscard = topDiscard;
			for(int i = 0; i < cardNo && nextDiscard.isPresent(); i++) {
				nextDiscard = actionDeck.peekDiscard(nextDiscard.get());
				if (nextDiscard.isPresent()) {
					topDiscard = nextDiscard;
				}
			}
			Action action = topDiscard.get();
			
			parms.put("card_number", Integer.toString(action.cardNumber()));
			parms.put("card_number_id", CARD_NO_ID);
			for (EnvironType environType : EnumSet.allOf(EnvironType.class)) {
				String key = environType.name().toLowerCase() + "_desc";
				System.out.println("Writing desc key '" + key + "'.");
				parms.put(key, action.getResultDescription(environType).getAsHtmlString());
				String keyId = key  + "_id";
				parms.put(keyId, keyId);
				String missionLettersKey = environType.name().toLowerCase() + "_missionLetters";;
				parms.put(missionLettersKey, action.getMissionLetters(environType));
				String missionLettersKeyId = missionLettersKey  + "_id";
				parms.put(missionLettersKeyId, missionLettersKeyId);
			}

			boolean hasPrevCard = cardNo > 0;	// Is there a previous card
			parms.put("has_prev_card", hasPrevCard);
			parms.put(PREV_CARD_NO_ID, PREV_CARD_NO_ID);
			if (hasPrevCard) {
				parms.put("prev_cardno", cardNo - 1);
			}
			boolean hasNextCard = cardNo + 1 < numberOfCardsInDiscard;
			parms.put("has_next_card", hasNextCard);
			parms.put(NEXT_CARD_NO_ID, NEXT_CARD_NO_ID);
			if (hasNextCard) {
				parms.put("next_cardno", cardNo + 1);
			}
			parms.put("discard_card_number", Integer.toString(cardNo + 1));
			parms.put("discard_num_cards", Integer.toString(actionDeck.numberOfCardsInDiscard()));
			
			System.out.println("Returning card #" + action.cardNumber() + " hasPrevCard=" + Boolean.toString(hasPrevCard) + " hasNextCard=" + Boolean.toString(hasNextCard));
		} else {
			parms.put("empty_discard_id", EMPTY_DISCARD_ID);
			System.out.println("Discard is empty.");
		}
		parms.put("has_card", hasCard);
		
		return parms;
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(DRAW_PATH)
	@Produces(MediaType.TEXT_HTML)
	public Response drawActionCard() throws URISyntaxException {
		FitGWebApplication.game.actionDeck().draw();
		// After drawing, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(RESET_PATH)
	@Produces(MediaType.TEXT_HTML)
	public Response resetActionCards() throws URISyntaxException {
		FitGWebApplication.game.actionDeck().reset();
		// After reseting, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		System.out.println("ActionDeck Ping");
		return FitGWebApplication.PING_RESPONSE;
	}
}
