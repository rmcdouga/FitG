package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

import org.glassfish.jersey.server.mvc.Template;

@Path(AuthenticationResources.AUTHENTICATION_PATH)
public class AuthenticationResources {

	public static final String AUTHENTICATION_PATH = "/Authenticate";

	public static final String EMAIL_PARAM = "emailAddress";
	public static final String REDIRECT_URL_PARAM = "redirectUrl";

	private static final String PASSWORD_PARAM = "password";
	private static final String MAX_EMAIL_SIZE_PARAM = "maxEmailSize";
	private static final int MAX_EMAIL_SIZE = 50;	// Maximum email string length

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/Authentication.mustache")
	public Map<String, Object> authenticateGetHtml(@QueryParam(REDIRECT_URL_PARAM) String redirectUrl) {
		Map<String, Object> map = new HashMap<>();
		map.put(REDIRECT_URL_PARAM, redirectUrl);
		map.put(MAX_EMAIL_SIZE_PARAM, MAX_EMAIL_SIZE);
		return map;
	}

	// Creates games
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response authenticatePostHtml(@FormParam(EMAIL_PARAM) String email, @FormParam(PASSWORD_PARAM) String password, @QueryParam(REDIRECT_URL_PARAM) String redirectUrl) throws URISyntaxException, NotFoundException {
		return Response.seeOther(new URI(redirectUrl)).build();
	}


}
