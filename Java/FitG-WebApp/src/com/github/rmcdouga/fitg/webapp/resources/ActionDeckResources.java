package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
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
import com.github.rmcdouga.fitg.webapp.util.JsonUtil;
import com.rogers.rmcdouga.fitg.basegame.Action;
import com.rogers.rmcdouga.fitg.basegame.Action.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.ActionDeck;
import com.rogers.rmcdouga.fitg.basegame.Game;

@Path(ActionDeckResources.ACTION_DECK_RESOURCE_PATH)
public class ActionDeckResources {
	public static final String HAS_CARD_LABEL = "has_card";
	public static final String HAS_NEXT_CARD_LABEL = "has_next_card";
	public static final String HAS_PREV_CARD_LABEL = "has_prev_card";
	public static final String NEXT_CARDNO_LABEL = "next_cardno";
	public static final String PREV_CARDNO_LABEL = "prev_cardno";
	public static final String CARD_NUMBER_LABEL = "card_number";
	public static final String ACTION_CARD_DISCARD_LABEL = "ActionCardDiscard";
	public static final String REL_ACTION_DECK_PATH = "ActionDeck";
	public static final String ACTION_DECK_PATH = "/" + REL_ACTION_DECK_PATH;
	public static final String ACTION_DECK_RESOURCE_PATH = "{gameStr}" + ACTION_DECK_PATH;
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
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Response actionDeckHtml(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Specifies that the method processes HTTP GET requests
	@GET
	@Path(DISCARD_PATH)
	@Produces(MediaType.TEXT_HTML)
	public Response actionDeckDiscardHtml(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}


	
	// Specifies that the method processes HTTP GET requests
	@GET
	@Path(DISCARD_PATH_CARD_NO)
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/ActionCard.mustache")
	public Map<String, Object> actionCardDiscardHtml(@PathParam("gameStr") String gameStr, @PathParam("cardNo") int cardNo) {
		return actionCardDiscard(getActionDeck(gameStr), cardNo, true);
	}

	// Specifies that the method processes HTTP GET requests
	@GET
	@Path(DISCARD_PATH_CARD_NO)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject actionCardDiscardJson(@PathParam("gameStr") String gameStr, @PathParam("cardNo") int cardNo) {
		return JsonUtil.MapToJson(actionCardDiscard(getActionDeck(gameStr), cardNo, false), ACTION_CARD_DISCARD_LABEL);
	}

	private Map<String, Object> actionCardDiscard(ActionDeck actionDeck, int cardNo, boolean includeIds) {
		Map<String, Object> parms = new HashMap<>();
		
		System.out.println("Retreiving discard #" + cardNo);
		boolean hasCard = false;

		// TBD - Clean this up, surely this can be more elegant.
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
			
			parms.put(CARD_NUMBER_LABEL, action.cardNumber());
			for (EnvironType environType : EnumSet.allOf(EnvironType.class)) {
				String descKey = environType.name().toLowerCase() + "_desc";
				System.out.println("Writing desc key '" + descKey + "'.");
				parms.put(descKey, action.getResultDescription(environType).getAsHtmlString());
				if (includeIds) {
					String descKeyId = descKey  + "_id";
					parms.put(descKeyId, descKeyId);
				}
				String missionLettersKey = environType.name().toLowerCase() + "_missionLetters";
				parms.put(missionLettersKey, action.getMissionLetters(environType));
				if (includeIds) {
					String missionLettersKeyId = missionLettersKey  + "_id";
					parms.put(missionLettersKeyId, missionLettersKeyId);
				}
			}

			boolean hasPrevCard = cardNo > 0;	// Is there a previous card
			parms.put(HAS_PREV_CARD_LABEL, hasPrevCard);
			if (hasPrevCard) {
				parms.put(PREV_CARDNO_LABEL, cardNo - 1);
			}
			boolean hasNextCard = cardNo + 1 < numberOfCardsInDiscard;
			parms.put(HAS_NEXT_CARD_LABEL, hasNextCard);
			if (hasNextCard) {
				parms.put(NEXT_CARDNO_LABEL, cardNo + 1);
			}
			if (includeIds) {
				parms.put("card_number_id", CARD_NO_ID);
				parms.put(PREV_CARD_NO_ID, PREV_CARD_NO_ID);
				parms.put(NEXT_CARD_NO_ID, NEXT_CARD_NO_ID);
			}
			parms.put("discard_card_number", cardNo + 1);
			parms.put("discard_num_cards", actionDeck.numberOfCardsInDiscard());
			
			System.out.println("parms='" + parms.toString() + "'");
			System.out.println("Returning card #" + action.cardNumber() + " hasPrevCard=" + Boolean.toString(hasPrevCard) + " hasNextCard=" + Boolean.toString(hasNextCard));
		} else {
			if (includeIds) {
				parms.put("empty_discard_id", EMPTY_DISCARD_ID);
			}
			System.out.println("Discard is empty.");
		}
		parms.put(HAS_CARD_LABEL, hasCard);
		
		return parms;
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(DRAW_PATH)
	@Produces(MediaType.TEXT_HTML)
	public Response drawActionCardHtml(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		getActionDeck(gameStr).draw();
		// After drawing, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(RESET_PATH)
	@Produces(MediaType.TEXT_HTML)
	public Response resetActionCardsHtml(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		getActionDeck(gameStr).reset();
		// After reseting, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(DRAW_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	public Response drawActionCardJson(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		getActionDeck(gameStr).draw();
		// After drawing, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	// Specifies that the method processes HTTP POST requests
	@POST
	@Path(RESET_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetActionCardsJson(@PathParam("gameStr") String gameStr) throws URISyntaxException {
		getActionDeck(gameStr).reset();
		// After reseting, redirect the user to GET the top card on the discard (i.e. the card drawn)
		return Response.seeOther(new URI(gameStr + ACTION_DECK_PATH + DISCARD_PATH + "/0")).build();
	}

	private ActionDeck getActionDeck(String gameStr) {
		Optional<Game> game = GamesResources.game(gameStr);
		System.out.println("Getting Game '" + gameStr + "', exists=" + game.isPresent() + "'.");
		return game.orElseThrow(()->new NotFoundException("Unable to find game '" + gameStr + "'.")).actionDeck();
	}

	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping(@PathParam("gameStr") String gameStr) {
		System.out.println("ActionDeck Ping");
		return FitGWebApplication.PING_RESPONSE;
	}
}
