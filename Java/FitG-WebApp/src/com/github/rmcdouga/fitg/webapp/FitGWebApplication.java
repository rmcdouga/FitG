package com.github.rmcdouga.fitg.webapp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;

import com.rogers.rmcdouga.fitg.basegame.Game;

@ApplicationPath(FitGWebApplication.APPLICATION_TOP_LEVEL)
public class FitGWebApplication extends ResourceConfig {

	public static final String APPLICATION_TOP_LEVEL = "/";
	public static final String PING_PATH = "/ping";
	public static final String PING_RESPONSE = "Ping!";

	public FitGWebApplication() {
		super();
		packages(this.getClass().getPackage().getName());
		register(MvcFeature.class);
		register(MustacheMvcFeature.class);
        register(LoggingFeature.class);
        
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "ALL");
        property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, "PAYLOAD_ANY");
        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
        property("com.sun.jersey.config.feature.Debug", "true");

	}


	public static final Game game = new Game();
	
	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return FitGWebApplication.PING_RESPONSE;
	}
}
