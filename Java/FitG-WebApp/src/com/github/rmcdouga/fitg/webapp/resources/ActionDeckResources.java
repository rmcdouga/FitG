package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.GET;
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

@Path(ActionDeckResources.ACTION_DECK_PATH)
public class ActionDeckResources {
	public static final String ACTION_DECK_PATH = "/ActionDeck";
	public static final String DRAW_PATH = "/Draw";
	public static final String DISCARD_PATH = "/Discard";
	private static final String DISCARD_PATH_CARD_NO = DISCARD_PATH + "/{cardNo}";

	public static final String CARD_NO_ID = "card_no_id";
	
	// Specifies that the method processes HTTP GET requests
	@GET
	@Path(DISCARD_PATH_CARD_NO)
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/ActionCard.mustache")
	public Map<String, Object> actionCardDiscard(@PathParam("cardNo") int cardNo) {
		Map<String, Object> parms = new HashMap<>();
		
		System.out.println("Retreiving discard #" + cardNo);
		// TBD - Clean this up, surely this can be more elegant.
		Optional<Action> topDiscard = FitGWebApplication.game.actionDeck().peekDiscard();
		if (!topDiscard.isPresent()) {
			topDiscard = FitGWebApplication.game.actionDeck().draw();
			if (!topDiscard.isPresent()) {
				// This should never happen
				throw new IllegalStateException("Action Deck is empty!! (No cards in discard and couldn't draw top card either.)");
			}
		}
		Optional<Action> nextDiscard = topDiscard;
		for(int i = 0; i < cardNo && nextDiscard.isPresent(); i++) {
			nextDiscard = FitGWebApplication.game.actionDeck().peekDiscard(nextDiscard.get());
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
		}
		System.out.println("Returning cad #" + action.cardNumber());
		
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

	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		System.out.println("ActionDeck Ping");
		return FitGWebApplication.PING_RESPONSE;
	}
}
