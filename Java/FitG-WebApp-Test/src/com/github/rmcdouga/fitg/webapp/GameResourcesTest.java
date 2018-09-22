package com.github.rmcdouga.fitg.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.map.SingletonMap;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.hanleyt.JerseyExtension;
import com.github.rmcdouga.fitg.webapp.resources.GameResources;

public class GameResourcesTest {
	@RegisterExtension
	JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey, this::configureJerseyClient);

	private Application configureJersey(ExtensionContext extensionContext) {
		ResourceConfig resourceConfig = new ResourceConfig(FitGWebApplication.class);

		resourceConfig.packages(this.getClass().getPackage().getName());
		resourceConfig.register(MvcFeature.class);
		resourceConfig.register(MustacheMvcFeature.class);
		resourceConfig.property(ServerProperties.TRACING, "ALL");
		resourceConfig.property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
		resourceConfig.property("com.sun.jersey.config.feature.Debug", "true");

		return resourceConfig;
	}
	
	private ClientConfig configureJerseyClient(ExtensionContext extensionContext, ClientConfig clientConfig) {
		clientConfig.connectorProvider(new ApacheConnectorProvider());
		return clientConfig;
	}
	
	private final static String GamesPath = TestUtils.APPLICATION_PREFIX + GameResources.GAMES_PATH;
	
	@Test
	void testCreateAndDeleteGames(WebTarget target) throws IOException {
		// Check that there are no predefined games.
		List<String> gameNamesInitial = listGamesHtml(target);
		assertEquals(0, gameNamesInitial.size(), "Expected that there would be no games to start with, but found '" + gameNamesInitial.toString() + "'.");

		createGameHtml(target, "TestGame1", false);
		List<String> gameNamesAfterCreationPost = listGamesHtml(target);
		assertEquals(1, gameNamesAfterCreationPost.size(), "Expected that there would be one game after creation, but found '" + gameNamesAfterCreationPost.toString() + "'.");
		
		createGameHtml(target, "TestGame2", true);
		List<String> gameNamesAfterCreationGet = listGamesHtml(target);
		assertEquals(2, gameNamesAfterCreationGet.size(), "Expected that there would be two game after second creation, but found '" + gameNamesAfterCreationGet.toString() + "'.");
		
		System.out.println("Game Names:" + gameNamesInitial.toString());
		
		createGameJson(target, "TestGame3", false);
		List<String> gameNamesAfterCreationPostJson = listGamesJson(target);
		assertEquals(3, gameNamesAfterCreationPostJson.size(), "Expected that there would be three games after Json creation, but found '" + gameNamesAfterCreationPost.toString() + "'.");

		// Save the current state
		StringWriter sw = new StringWriter();
		GameResources.saveGames(sw);
		
		System.out.println("---- Save File JSON ----");
		System.out.println(sw.toString());
		System.out.println("------------------------");

		createGameJson(target, "TestGame4", true);
		List<String> gameNamesAfterCreationGetJson = listGamesJson(target);
		assertEquals(4, gameNamesAfterCreationGetJson.size(), "Expected that there would be four game after second Json creation, but found '" + gameNamesAfterCreationGet.toString() + "'.");

		// Load the state, make sure things are reset
		StringReader sr = new StringReader(sw.toString());
		GameResources.loadGames(sr);

		List<String> gameNamesAfterCreationPostSaveLoad = listGamesJson(target);
		assertEquals(3, gameNamesAfterCreationPostSaveLoad.size(), "Expected that there would be three games after games Reload, but found '" + gameNamesAfterCreationPost.toString() + "'.");
	}

	private static List<String> listGamesHtml(WebTarget target) throws IOException {
		Response result = TestUtils.trace(target.path(GamesPath).request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildGet()
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + GamesPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- HTML -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
		Document html = Jsoup.parse(new ByteArrayInputStream(entity), StandardCharsets.UTF_8.name(), "/");
		Elements gameNames = html.getElementsByClass("gameName");
		assertNotNull(gameNames, "Couldn't find any game names.");
		List<String> gamesList = gameNames.stream().map(Element::text).collect(Collectors.toList());
		if (gamesList.isEmpty()) {
			// Check to make sure that the No Games Found text was sent.
			Element noGamesFoundElement = html.getElementById("noGames");
			assertNotNull(noGamesFoundElement, "Expected the No Games Found text.");
		}
		return gamesList;
	}
	
	public static void createGameHtml(WebTarget target, String testGameName, boolean useQueryParm) throws IOException {
		Form form = new Form();
		WebTarget target2 = target.path(GamesPath + GameResources.CREATE_PATH);
		// Use a query parameter or a form parameter to provide the name. 
		if (useQueryParm) {
			target2 = target2.queryParam(GameResources.GAME_NAME_PARAM, testGameName);
		} else {
			form.param(GameResources.GAME_NAME_PARAM, testGameName);
		}
		Response result = TestUtils.trace(target2.request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + (GamesPath + GameResources.CREATE_PATH) + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- HTML -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
		
	}


	private static List<String> listGamesJson(WebTarget target) throws IOException {
		Response result = TestUtils.trace(target.path(GamesPath).request())
				 .accept(MediaType.APPLICATION_JSON_TYPE)
				 .buildGet()
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + GamesPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- JSON -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
		JsonReader reader = Json.createReader(new ByteArrayInputStream(entity));
		JsonStructure jsonStructure = reader.read();
		assertEquals(JsonValue.ValueType.OBJECT, jsonStructure.getValueType(), "Expected top level structure to be an object but was '" + jsonStructure.getValueType().toString() + "'.");
		JsonValue gameNames = jsonStructure.getValue("/" + GameResources.GAMES_LIST_LABEL);
		assertNotNull(gameNames, "Couldn't find any game names.");
		assertEquals(JsonValue.ValueType.ARRAY, gameNames.getValueType(), "Expected gameNames structure to be an array but was '" + jsonStructure.getValueType().toString() + "'.");
		JsonArray gameNamesArray = gameNames.asJsonArray();
		
		List<String> gamesList = gameNamesArray.stream().map(JsonValue::asJsonObject).map(o->o.getString(GameResources.GAME_NAME_LABEL)).collect(Collectors.toList());
		return gamesList;
	}
	
	public static void createGameJson(WebTarget target, String testGameName, boolean useQueryParm) throws IOException {
		JsonObject json;
		WebTarget target2 = target.path(GamesPath + GameResources.CREATE_PATH);
		// Use a query parameter or a form parameter to provide the name. 
		if (useQueryParm) {
			target2 = target2.queryParam(GameResources.GAME_NAME_PARAM, testGameName);
			json = Json.createObjectBuilder().build();
		} else {
			json = Json.createObjectBuilder(new SingletonMap<String, Object>(GameResources.CREATE_GAME_LABEL, testGameName)).build();
		}
		Response result = TestUtils.trace(target2.request())
				 .accept(MediaType.APPLICATION_JSON_TYPE)
				 .buildPost(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE))
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + (GamesPath + GameResources.CREATE_PATH) + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- JSON -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
	}

}
