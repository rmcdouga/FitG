package com.github.rmcdouga.fitg.webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

import com.github.rmcdouga.fitg.webapp.authentication.AuthenticationStatus;

@Path(AuthenticationResources.AUTHENTICATION_PATH)
public class AuthenticationResources {

	public static final String AUTHENTICATION_PATH = "/Authenticate";

	public static final String EMAIL_PARAM = "emailAddress";
	public static final String REDIRECT_URL_PARAM = "redirectUrl";
	public static final String REASON_PARAM = "reason";
 
	public static final String COOKIE_NAME = "com.github.rmcdouga.fitg.webapp.login_status";
	
	private static final String PASSWORD_PARAM = "password";
	private static final String MAX_EMAIL_SIZE_PARAM = "maxEmailSize";
	private static final int MAX_EMAIL_SIZE = 50;	// Maximum email string length

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "/com/github/rmcdouga/fitg/webapp/resources/Authentication.mustache")
	public Map<String, Object> authenticateGetHtml(@QueryParam(REDIRECT_URL_PARAM) String redirectUrl, @QueryParam(REASON_PARAM) String reason) {
		Map<String, Object> map = new HashMap<>();
		map.put(REDIRECT_URL_PARAM, validateRedirectUrl(redirectUrl));
		map.put(REASON_PARAM, validateReason(reason));
		map.put(MAX_EMAIL_SIZE_PARAM, MAX_EMAIL_SIZE);
		return map;
	}

	// Creates games
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response authenticatePostHtml(@FormParam(EMAIL_PARAM) String email, @FormParam(PASSWORD_PARAM) String password, @QueryParam(REDIRECT_URL_PARAM) String redirectUrl) throws URISyntaxException, NotFoundException {
		return Response.seeOther(new URI(validateRedirectUrl(redirectUrl))).cookie(new NewCookie(COOKIE_NAME, AuthenticationStatus.LOGGED_IN.toString(), null, null, null, NewCookie.DEFAULT_MAX_AGE, true)).build();
	}

	private String validateRedirectUrl(String redirectUrl) {
		Objects.requireNonNull(redirectUrl, "A redirectUrl must be supplied as a query parameter.");
		if (redirectUrl.trim().isEmpty()) {
			throw new InvalidParameterException("rediectUrl cannot be empty.");
		}
		return redirectUrl;
	}
	
	private String validateReason(String reason) {
		if (reason == null) {
			return "";
		}
		return reason;
	}

}
