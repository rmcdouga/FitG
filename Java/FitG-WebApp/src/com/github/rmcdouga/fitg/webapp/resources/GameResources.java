package com.github.rmcdouga.fitg.webapp.resources;

import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.map.SingletonMap;
import org.glassfish.jersey.server.mvc.Template;

import com.github.rmcdouga.fitg.webapp.util.JsonUtil;
import com.rogers.rmcdouga.fitg.basegame.Game;

@Path(GameResources.GAMES_PATH)
public class GameResources {
	private static final String FITG_GAMES_LABEL = "FitG_Games";
	public static final int MAX_GAME_NAME_SIZE = 30;
	public static final String GAME_NAME_PARAM = "name";
	public static final String GAME_NAME_LABEL = "gameName";
	public static final String GAMES_LIST_LABEL = "gamesList";
	public static final String GAMES_CREATED_LABEL = "gamesCreated";
	public static final String CREATE_GAME_LABEL = "createGame";
	public static final String DEFAULT_GAME_NAME = "default";
	public static final String REL_GAMES_PATH = "Games";
	public static final String GAMES_PATH = "/" + REL_GAMES_PATH;
	public static final String CREATE_PATH = "/Create";
	public static final String DELETE_PATH = "/Delete";

	private static final Map<String, WebAppGame> games = createGamesMap();
	private static Map<String, WebAppGame> createGamesMap() {
		LinkedHashMap<String, WebAppGame> map = new LinkedHashMap<>();
		return map;
	}
	
	public static Optional<Game> game(String name) {
		// Whenever we get a game we update the last access time.
		return Optional.ofNullable(games.get(name).updateLastAccessTime());
	}

	public static int numGames() {
		return games.size();
	}

	private static Map<String, Object> getState() {
		Map<String, Object> gameStates = new HashMap<>();
		games.forEach((name, game)->gameStates.put(name, game.getState()));
		return gameStates;
	}

	private static void setState(Map<String, Object> state) {
		games.clear();
		state.forEach((name, gameState)->{
			WebAppGame newGame = new WebAppGame();
			newGame.setState((Map<String, Object>)gameState);
			WebAppGame putGame = games.put(name, newGame);
			});
	}
	
	
	public static void saveGames(Writer w) {
		Json.createWriter(w).writeObject(JsonUtil.MapToJson(getState(), FITG_GAMES_LABEL));
	}
	
	public static void loadGames(Reader r) {
		setState((Map<String, Object>)(JsonUtil.JsonToMap(Json.createReader(r).readObject()).get(FITG_GAMES_LABEL)));
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
		String gameName = determineGameName(queryName, formName);
		if (games.putIfAbsent(gameName, new WebAppGame()) != null) {
			// Seems we already have a game there with this name.
			throw new ClientErrorException("Game '" + gameName + "' already exists.", Status.CONFLICT.getStatusCode());
		}
		return Response.seeOther(new URI(gameName + ActionDeckResources.ACTION_DECK_PATH + ActionDeckResources.DISCARD_PATH + "/0")).build();
	}

	private String determineGameName(String queryName, String formName) {
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
		return gameName;
	}

	@GET
	@Path(CREATE_PATH)
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/CreateGame.mustache")
	public Map<String, Object> createGameGetHtml() {
		return Collections.emptyMap();
	}

	/*
	 * JSON Methods
	 */
	
	// Lists Games
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject listGamesJson() {
		List<Object> gamesList = new ArrayList<>(games.size());
		games.forEach((name,game)->gamesList.add(new SingletonMap<>(GAME_NAME_LABEL, name)));
		return JsonUtil.MapToJson(gamesList, GAMES_LIST_LABEL);
	}
	
	// Creates games
	@POST
	@Path(CREATE_PATH)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject createGameJson(@QueryParam(GAME_NAME_PARAM) String queryName, JsonObject createGameJson) throws URISyntaxException, NotFoundException {
		String bodyName = createGameJson.isEmpty() ? null : createGameJson.getString(CREATE_GAME_LABEL);
		// Accept the game name from either the query parameter or from the JSON body.
		String gameName = determineGameName(queryName, bodyName);
		if (games.putIfAbsent(gameName, new WebAppGame()) != null) {
			// Seems we already have a game there with this name.
			throw new ClientErrorException("Game '" + gameName + "' already exists.", Status.CONFLICT.getStatusCode());
		}
		List<Object> gamesList = new ArrayList<>(1);
		gamesList.add(new SingletonMap<>(GAME_NAME_LABEL, gameName));
		return JsonUtil.MapToJson(gamesList, GAMES_CREATED_LABEL);
	}

	private static class WebAppGame extends Game {
		private static final String LAST_ACCESS_DATE_TIME_LABEL = "lastAccessDateTime";
		private static final String CREATION_DATE_TIME_LABEL = "creationDateTime";
		private Instant creationDateTime = Instant.now();
		private Instant lastAccessDateTime = Instant.now();

		public WebAppGame updateLastAccessTime() {
			lastAccessDateTime = Instant.now();
			return this;
		}
		
		/* (non-Javadoc)
		 * @see com.rogers.rmcdouga.fitg.basegame.Game#getState()
		 */
		@Override
		public Map<String, Object> getState() {
			Map<String, Object> state = super.getState();
			
			state.put(CREATION_DATE_TIME_LABEL, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault()).format(creationDateTime));
			state.put(LAST_ACCESS_DATE_TIME_LABEL, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault()).format(lastAccessDateTime));
			return state;
		}
		/* (non-Javadoc)
		 * @see com.rogers.rmcdouga.fitg.basegame.Game#setState(java.util.Map)
		 */
		@Override
		public void setState(Map<String, Object> state) {
			super.setState(state);
			creationDateTime = DateTimeFormatter.ISO_DATE_TIME.parse((String)state.get(CREATION_DATE_TIME_LABEL), Instant::from);
			lastAccessDateTime = DateTimeFormatter.ISO_DATE_TIME.parse((String)state.get(LAST_ACCESS_DATE_TIME_LABEL), Instant::from);
		}
		
	}
}
