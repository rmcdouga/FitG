package com.github.rmcdouga.fitg.webapp.authentication;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

import com.github.rmcdouga.fitg.webapp.resources.AuthenticationResources;

import javax.ws.rs.Priorities;

@RequiresSignIn
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	private static final String REALM = "example";
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	   
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	    // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        Cookie cookie = requestContext.getCookies().get(AuthenticationResources.COOKIE_NAME);
        if (cookie == null) {
            System.out.println("Not Authorized - No cookie found. '" + requestContext.getUriInfo().getAbsolutePath().toString() + "'");
            UriBuilder uriBuilder =  requestContext.getUriInfo().getBaseUriBuilder()
            								  .path(AuthenticationResources.AUTHENTICATION_PATH)
            								  .queryParam(AuthenticationResources.REDIRECT_URL_PARAM, requestContext.getUriInfo().getPath())
            								  .queryParam(AuthenticationResources.REASON_PARAM, "Not logged in.");
            requestContext.abortWith(
                    Response.seeOther(uriBuilder.build())
                            .build());
        } else if (AuthenticationStatus.LOGGED_IN.toString().equals(cookie.getValue())) {
            System.out.println("Authorized");
        } else {
            System.out.println("Not Authorized - cookie has wrong value.");
            UriBuilder uriBuilder =  requestContext.getUriInfo().getBaseUriBuilder()
					  .path(AuthenticationResources.AUTHENTICATION_PATH)
					  .queryParam(AuthenticationResources.REDIRECT_URL_PARAM, requestContext.getUriInfo().getPath())
					  .queryParam(AuthenticationResources.REASON_PARAM, "Invalid session.");
            requestContext.abortWith(
                    Response.seeOther(uriBuilder.build())
                            .build());
        }
        // Validate the Authorization header
//        if (!isTokenBasedAuthentication(authorizationHeader)) {
//            abortWithUnauthorized(requestContext);
//            return;
//        }
//
//        // Extract the token from the Authorization header
//        String token = authorizationHeader
//                            .substring(AUTHENTICATION_SCHEME.length()).trim();
//
//        try {
//
//            // Validate the token
//            validateToken(token);
//
//        } catch (Exception e) {
//            abortWithUnauthorized(requestContext);
//        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
	}

}
