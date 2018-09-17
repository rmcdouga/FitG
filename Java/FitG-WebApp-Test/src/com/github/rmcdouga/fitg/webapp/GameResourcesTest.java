package com.github.rmcdouga.fitg.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import com.github.rmcdouga.fitg.webapp.resources.ActionDeckResources;
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


}
