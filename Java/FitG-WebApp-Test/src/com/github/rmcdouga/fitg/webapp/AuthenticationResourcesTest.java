package com.github.rmcdouga.fitg.webapp;

import static org.junit.jupiter.api.Assertions.*;

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
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.hanleyt.JerseyExtension;
import com.github.rmcdouga.fitg.webapp.resources.AuthenticationResources;
import com.github.rmcdouga.fitg.webapp.resources.GamesResources;

class AuthenticationResourcesTest {
	
	@RegisterExtension
	JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey, this::configureJerseyClient);

	private Application configureJersey(ExtensionContext extensionContext) {
		ResourceConfig resourceConfig = new ResourceConfig(FitGWebApplication.class);

		resourceConfig.packages(FitGWebApplication.class.getPackage().getName());
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

	private final static String AuthenticationPath = TestUtils.APPLICATION_PREFIX + AuthenticationResources.AUTHENTICATION_PATH;

	// Gathered into a single test because multiple tests does not seem to work under JUnit5 and JersetTestFramework
	@Test
	public void testAuthentication(WebTarget target) throws IOException {
		testAuthenticateGetHtml(target);
		testAuthenticatePostHtml(target);
	}

	private void testAuthenticateGetHtml(WebTarget target) throws IOException {
		Response result = TestUtils.trace(target.path(AuthenticationPath).request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildGet()
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + AuthenticationPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- HTML -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
		Document html = Jsoup.parse(new ByteArrayInputStream(entity), StandardCharsets.UTF_8.name(), "/");
		Elements inputElements = html.getElementsByTag("input");
		assertNotNull(inputElements, "Couldn't find any input elements.");
		List<String> namesList = inputElements.stream().map(AuthenticationResourcesTest::getName).collect(Collectors.toList());
		assertFalse(namesList.isEmpty(), "expected the input elements to have names." );
		assertTrue(namesList.contains(AuthenticationResources.EMAIL_PARAM), ()->"Expected form to have an input field named '" + AuthenticationResources.EMAIL_PARAM + "'.");
	}

	private static String getName(Element element) {
		return element.attributes().get("name");
	}
	
	private void testAuthenticatePostHtml(WebTarget target) throws IOException {
		final String testEmail = "rmcdouga@github.com";
		Form form = new Form();
		WebTarget target2 = target.path(AuthenticationPath).queryParam(AuthenticationResources.REDIRECT_URL_PARAM, "/");
		// Use a query parameter or a form parameter to provide the name. 
		form.param(AuthenticationResources.EMAIL_PARAM, testEmail );
		Response result = TestUtils.trace(target2.request())
				 .accept(MediaType.TEXT_HTML_TYPE)
				 .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))
				 .invoke();
	
		if (Response.Status.OK.getStatusCode() != result.getStatus()) {
			TestUtils.printTrace(result);
		}
		assertEquals(Response.Status.OK.getStatusCode(), result.getStatus(), ()->"Response from '" + AuthenticationPath + "' should be OK");
		assertTrue(result.hasEntity(), "Expected response to have body.");
		byte[] entity = IOUtils.toByteArray((InputStream)result.getEntity());
		System.out.println("----- HTML -----");
		IOUtils.write(entity, System.out);
		System.out.println("----------------");
	}

}
