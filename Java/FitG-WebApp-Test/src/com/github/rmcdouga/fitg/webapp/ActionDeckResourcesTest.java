package com.github.rmcdouga.fitg.webapp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.hanleyt.JerseyExtension;
import com.github.rmcdouga.fitg.webapp.resources.ActionDeckResources;
import com.github.rmcdouga.fitg.webapp.resources.GameResources;

class ActionDeckResourcesTest {
	
	private static final String TEST_GAME_NAME = GameResources.DEFAULT_GAME_NAME;

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
	
	@Test
	void testDrawAndDiscard(WebTarget target) throws IOException {
		System.out.println("DrawAndDiscard WebTarget='" + target.toString() + "'.");
		
		GameResourcesTest.createGameHtml(target, TEST_GAME_NAME, false);

		{
		// Test that we start with an empty discard.
		int emptyDiscardCard = getDiscardCard(target, 0, false, false, false);
		assertEquals(0, emptyDiscardCard, "Expceted the discard to be empty.");

		// Draw a few cards and test that the discard pile contains the cards drawn in reverse order of drawing
		int firstCardDrawnNum = drawCard(target, false);
		int secondCardDrawnNum = drawCard(target, true);
		int thirdCardDrawnNum = drawCard(target, true);
		
		int firstDiscardCard = getDiscardCard(target, 0, true, false, true);
		assertEquals(thirdCardDrawnNum, firstDiscardCard, "Expceted top of the discard to be the last card drawn");
		int secondDiscardCard = getDiscardCard(target, 1, true, true, true);
		assertEquals(secondCardDrawnNum, secondDiscardCard, "Expceted second discard card to be the second card drawn");
		int thirdDiscardCard = getDiscardCard(target, 2, true, true, false);
		assertEquals(firstCardDrawnNum, thirdDiscardCard, "Expceted last discard card to be the first card drawn");
		
		// Reset the deck and check that the discard pile is empty.
		resetDeck(target);
		int emptyDiscardCardAgain = getDiscardCard(target, 0, false, false, false);
		assertEquals(0, emptyDiscardCardAgain, "Expceted the discard to be empty after reset.");

		// Expect 404 if we seek past the end of the discard when the discard is not empty.
		emptyDiscardCardAgain = getDiscardCard(target, 10, false, false, false);
		assertEquals(0, emptyDiscardCardAgain, "Expceted the discard to return empty no matter what discard number is requested.");
		firstCardDrawnNum = drawCard(target, false);
		getDiscardCardExpectNotFound(target, 10);
		}
		
		{
		// Start JSON Tests
		resetDeck(target);  // TODO: Change this to JSON
		int emptyDiscardCardJson = getDiscardCardJson(target, 0, false, false, false);
		assertEquals(0, emptyDiscardCardJson, "Expceted the discard to be empty.");
		// Draw a few cards and test that the discard pile contains the cards drawn in reverse order of drawing
		int firstCardDrawnNumJson = drawCardJson(target, false);
		int secondCardDrawnNumJson = drawCardJson(target, true);
		int thirdCardDrawnNumJson = drawCardJson(target, true);
		
		int firstDiscardCardJson = getDiscardCardJson(target, 0, true, false, true);
		assertEquals(thirdCardDrawnNumJson, firstDiscardCardJson, "Expceted top of the discard to be the last card drawn");
		int secondDiscardCardJson = getDiscardCardJson(target, 1, true, true, true);
		assertEquals(secondCardDrawnNumJson, secondDiscardCardJson, "Expceted second discard card to be the second card drawn");
		int thirdDiscardCardJson = getDiscardCardJson(target, 2, true, true, false);
		assertEquals(firstCardDrawnNumJson, thirdDiscardCardJson, "Expceted last discard card to be the first card drawn");
		
		// Reset the deck and check that the discard pile is empty.
		resetDeck(target);
		int emptyDiscardCardAgainJson = getDiscardCardJson(target, 0, false, false, false);
		assertEquals(0, emptyDiscardCardAgainJson, "Expceted the discard to be empty after reset.");

		// Expect 404 if we seek past the end of the discard when the discard is not empty.
		emptyDiscardCardAgainJson = getDiscardCardJson(target, 10, false, false, false);
		assertEquals(0, emptyDiscardCardAgainJson, "Expceted the discard to return empty no matter what discard number is requested.");
		firstCardDrawnNumJson = drawCardJson(target, false);
		getDiscardCardExpectNotFound(target, 10);
		}
	}

	private int getDiscardCard(WebTarget target, int discardCardLocation, boolean expectCard, boolean expectPrevCard, boolean expectNextCard) throws IOException {
		String targetPath = ActionDeckPath + ActionDeckResources.DISCARD_PATH + "/" + Integer.toString(discardCardLocation);
		Response result = TestUtils.trace(target.path(targetPath).request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildGet()
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		if (expectCard) {
			return getCardDrawnNum(entity, expectPrevCard, expectNextCard);
		} else {
			testForResetDeck(entity);
			return 0;
		}
	}

	private void getDiscardCardExpectNotFound(WebTarget target, int discardCardLocation) throws IOException {
		String targetPath = ActionDeckPath + ActionDeckResources.DISCARD_PATH + "/" + Integer.toString(discardCardLocation);
		Response result = TestUtils.trace(target.path(targetPath).request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildGet()
				 .invoke();
	
		if (Response.Status.NOT_FOUND.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
	}
	
	private int drawCard(WebTarget target, boolean expectNextCard) throws IOException {
		Form form = new Form();
	
		String targetPath = ActionDeckPath + ActionDeckResources.DRAW_PATH;
		System.out.println("TargetPath='" + targetPath + "'.");
		WebTarget path = target.path(targetPath);
		System.out.println("DrawCard WebTarget='" + path.toString() + "'.");
		Invocation buildPost = TestUtils.trace(path.request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		Response result = buildPost
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("HTML Result='" + new String(entity) + "'.");
		return getCardDrawnNum(entity, false, expectNextCard);
	}

	private int getCardDrawnNum(byte[] result, boolean expectPrevCard, boolean expectNextCard) throws IOException {
		Document html = Jsoup.parse(new ByteArrayInputStream(result), StandardCharsets.UTF_8.name(), "/");
		Element paragraph = html.getElementById(ActionDeckResources.CARD_NO_ID);
		assertNotNull(paragraph, "Couldn't find card number.");
		String paraText[] = paragraph.ownText().split(" ");	// Should be nr. ##
		assertEquals("nr.", paraText[0], "Expected Card Number text to start with 'nr.'");
		int cardDrawnNum = Integer.parseInt(paraText[1]);
		assertNotEquals(0, cardDrawnNum, "Card # Drawn should not be 0");
		Element prevCardAnchor = html.getElementById(ActionDeckResources.PREV_CARD_NO_ID);
		assertNotNull(prevCardAnchor, "Couldn't find previous card link.");
		String prevCardDisabled = prevCardAnchor.attr("disabled");
		if (expectPrevCard) {
			assertTrue(prevCardDisabled.isEmpty(), "Expected previous card link to be enabled.");
		} else {
			assertFalse(prevCardDisabled.isEmpty(), "Expected previous card link to be disabled.");
		}
		Element nextCardAnchor = html.getElementById(ActionDeckResources.NEXT_CARD_NO_ID);
		assertNotNull(nextCardAnchor, "Couldn't find next card link.");
		String nextCardDisabled = nextCardAnchor.attr("disabled");
		if (expectNextCard) {
			assertTrue(nextCardDisabled.isEmpty(), "Expected next card link to be enabled.");
		} else {
			assertFalse(nextCardDisabled.isEmpty(), "Expected next card link to be disabled.");
		}
		return cardDrawnNum;
	}

	private void testForResetDeck(byte[] result) throws IOException {
		Document html = Jsoup.parse(new ByteArrayInputStream(result), StandardCharsets.UTF_8.name(), "/");
		Element paragraph = html.getElementById(ActionDeckResources.EMPTY_DISCARD_ID);
		assertNotNull(paragraph, "Couldnm't find empty discard paragraph");
	}


	private void resetDeck(WebTarget target) throws IOException {
		Form form = new Form();
		
		String targetPath = ActionDeckPath + ActionDeckResources.RESET_PATH;
		System.out.println("TargetPath='" + targetPath + "'.");
		WebTarget path = target.path(targetPath);
		System.out.println("DrawCard WebTarget='" + path.toString() + "'.");
		Response result = TestUtils.trace(path.request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		testForResetDeck(entity);
	}
	
	// For the time being it appears that I can only run one test in a test run using the in-memory container.  I don't
	// know why that is, however I am living with it for now.
	@Disabled
	void testPing_ThisLevel(WebTarget target) throws IOException {
		System.out.println("Ping WebTarget='" + target.toString() + "'.");
		String testPath = ActionDeckPath + FitGWebApplication.PING_PATH;
		Response result = target.path(testPath).request().buildGet().invoke();
		
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), "Response should be OK.  Path='" + testPath + "'.");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		String string = IOUtils.toString((InputStream) result.getEntity(), StandardCharsets.UTF_8);
		assertThat(string, containsString(FitGWebApplication.PING_RESPONSE));
	}

	private int getDiscardCardJson(WebTarget target, int discardCardLocation, boolean expectCard, boolean expectPrevCard, boolean expectNextCard) throws IOException {
		String targetPath = ActionDeckPath + ActionDeckResources.DISCARD_PATH + "/" + Integer.toString(discardCardLocation);
		Response result = TestUtils.trace(target.path(targetPath).request())
				.accept(MediaType.APPLICATION_JSON)
				.buildGet().invoke();

		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("---- JSON Response ----");
		System.out.println(new String(entity));
		System.out.println("-----------------------");
		if (expectCard) {
			return getCardDrawnNumJson(entity, expectPrevCard, expectNextCard);
		} else {
			testForResetDeckJson(entity);
			return 0;
		}
	}

	private int drawCardJson(WebTarget target, boolean expectNextCard) throws IOException {
		Form form = new Form();
	
		String targetPath = ActionDeckPath + ActionDeckResources.DRAW_PATH;
		System.out.println("TargetPath='" + targetPath + "'.");
		WebTarget path = target.path(targetPath);
		System.out.println("DrawCard WebTarget='" + path.toString() + "'.");
		Invocation buildPost = TestUtils.trace(path.request())
				 .accept(MediaType.APPLICATION_JSON)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		Response result = buildPost
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("---- JSON Response ----");
		System.out.println(new String(entity));
		System.out.println("-----------------------");
		return getCardDrawnNumJson(entity, false, expectNextCard);
	}


	private int getCardDrawnNumJson(byte[] result, boolean expectPrevCard, boolean expectNextCard) throws IOException {
		JsonReader reader = Json.createReader(new ByteArrayInputStream(result));
		JsonStructure jsonStructure = reader.read();
		assertEquals(JsonValue.ValueType.OBJECT, jsonStructure.getValueType(), "Expected top level structure to be an object.");
		
		JsonObject jsonObject = jsonStructure.asJsonObject();
		JsonObject actioCardDiscardObject = jsonObject.getJsonObject(ActionDeckResources.ACTION_CARD_DISCARD_LABEL);
		boolean hasCard = actioCardDiscardObject.getBoolean(ActionDeckResources.HAS_CARD_LABEL);
		assertTrue(hasCard, "Expected hasCard to be true.");

		int cardDrawnNum = actioCardDiscardObject.getInt(ActionDeckResources.CARD_NUMBER_LABEL);
		assertNotEquals(0, cardDrawnNum, "Card # Drawn should not be 0");

		boolean hasPrevCard = actioCardDiscardObject.getBoolean(ActionDeckResources.HAS_PREV_CARD_LABEL);
		assertEquals(expectPrevCard, hasPrevCard, "Expected hasPrevCard to be '" + Boolean.toString(expectPrevCard) + "'.");
		if (hasPrevCard) {
			int prevCardNo = actioCardDiscardObject.getInt(ActionDeckResources.PREV_CARDNO_LABEL);
			assertTrue(prevCardNo >= 0 && prevCardNo < 30, "Expecte previous card number to be >= 0 & < 30, but was '" + Integer.toString(prevCardNo) + "'.");
		}
		
		boolean hasNextCard = actioCardDiscardObject.getBoolean(ActionDeckResources.HAS_NEXT_CARD_LABEL);
		assertEquals(expectNextCard, hasNextCard, "Expected hasNextCard to be '" + Boolean.toString(expectNextCard) + "'.");
		if (hasNextCard) {
			int nextCardNo = actioCardDiscardObject.getInt(ActionDeckResources.NEXT_CARDNO_LABEL);
			assertTrue(nextCardNo > 0 && nextCardNo < 30, "Expecte next card number to be > 0 & < 30, but was '" + Integer.toString(nextCardNo) + "'.");
		}
		return cardDrawnNum;
	}

	private void testForResetDeckJson(byte[] result) throws IOException {
		JsonReader reader = Json.createReader(new ByteArrayInputStream(result));
		JsonStructure jsonStructure = reader.read();
		assertEquals(JsonValue.ValueType.OBJECT, jsonStructure.getValueType(), "Expected top level structure to be an object.");
		
		JsonObject jsonObject = jsonStructure.asJsonObject();
		JsonObject actioCardDiscardObject = jsonObject.getJsonObject(ActionDeckResources.ACTION_CARD_DISCARD_LABEL);

		boolean hasCard = actioCardDiscardObject.getBoolean(ActionDeckResources.HAS_CARD_LABEL);
		assertFalse(hasCard, "Expected hasCard to be false.");
	}

	private void resetDeckJson(WebTarget target) throws IOException {
		Form form = new Form();
		
		String targetPath = ActionDeckPath + ActionDeckResources.RESET_PATH;
		System.out.println("TargetPath='" + targetPath + "'.");
		WebTarget path = target.path(targetPath);
		System.out.println("DrawCard WebTarget='" + path.toString() + "'.");
		Invocation buildPost = TestUtils.trace(path.request())
				 .accept(MediaType.APPLICATION_JSON_TYPE)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		Response result = buildPost
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + targetPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		testForResetDeckJson(entity);
	}
	

	private static final String ActionDeckPath = TestUtils.APPLICATION_PREFIX + TEST_GAME_NAME + ActionDeckResources.ACTION_DECK_PATH;


}
