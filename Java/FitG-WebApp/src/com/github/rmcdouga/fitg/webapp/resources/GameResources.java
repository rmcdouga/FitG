package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.map.SingletonMap;
import org.glassfish.jersey.server.mvc.Template;

import com.rogers.rmcdouga.fitg.basegame.Game;

@Path(GameResources.GAMES_PATH)
public class GameResources {
	public static final int MAX_GAME_NAME_SIZE = 30;
	public static final String GAME_NAME_PARAM = "name";
	private static final String GAME_NAME_LABEL = "gameName";
	private static final String GAMES_LIST_LABEL = "gamesList";
	public static final String DEFAULT_GAME_NAME = "default";
	public static final String REL_GAMES_PATH = "Games";
	public static final String GAMES_PATH = "/" + REL_GAMES_PATH;
	public static final String CREATE_PATH = "/Create";
	public static final String DELETE_PATH = "/Delete";

	private static final Map<String, Game> games = createGamesMap();
	private static Map<String, Game> createGamesMap() {
		LinkedHashMap<String, Game> map = new LinkedHashMap<String, Game>();
//		map.put(DEFAULT_GAME_NAME, new Game());
		return map;
	}
	
	public static Optional<Game> game(String name) {
		return Optional.ofNullable(games.get(name));
	}

	// Lists Games
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/GamesList.mustache")
	public Map<String, Object> listGamesHtml() {
		List<Map<String, Object>> gamesList = new ArrayList<>(games.size());
		games.forEach((name,game)->gamesList.add(new SingletonMap<>(GAME_NAME_LABEL, name)));
		return new SingletonMap<>(GAMES_LIST_LABEL, gamesList);
	}
	
	// Creates games
	@POST
	@Path(CREATE_PATH)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response createGameHtml(@QueryParam(GAME_NAME_PARAM) String queryName, @FormParam(GAME_NAME_PARAM) String formName) throws URISyntaxException, NotFoundException {
		// Accept the game name from either the query parameter of the form name.
		String gameName; 
		if (queryName != null) {
			gameName = queryName;
		} else if (formName != null) {
			gameName = formName;
		} else {
			throw new NotFoundException("No name supplied for the game being created!");
		}
		if (gameName.length() > MAX_GAME_NAME_SIZE) {
			throw new BadRequestException("Game name supplied exceeds the maximum of " + Integer.toString(MAX_GAME_NAME_SIZE) + " characters.");
		}
		// Gamename must follow the same rules as a Java Identifier.
		if (!Character.isJavaIdentifierStart(gameName.codePointAt(0))) {
			throw new BadRequestException("Game names must follow Java Identifier rules. Invalid starting character.");
		}
		if (!gameName.codePoints().allMatch(Character::isJavaIdentifierPart)) {
			throw new BadRequestException("Game names must follow Java Identifier rules.");
		}
		if (games.putIfAbsent(gameName, new Game()) != null) {
			// Seems we already have a game there with this name.
			throw new ClientErrorException("Game '" + gameName + "' already exists.", Status.CONFLICT.getStatusCode());
		}
		return Response.seeOther(new URI(gameName + ActionDeckResources.ACTION_DECK_PATH + ActionDeckResources.DISCARD_PATH + "/0")).build();
	}

	
}
