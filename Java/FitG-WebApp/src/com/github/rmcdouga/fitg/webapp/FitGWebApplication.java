package com.github.rmcdouga.fitg.webapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import com.github.rmcdouga.fitg.webapp.resources.ActionDeckResources;
import com.github.rmcdouga.fitg.webapp.resources.GameResources;

@ApplicationPath(FitGWebApplication.APPLICATION_TOP_LEVEL)
@Path("")
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

        Reloader reloader = new Reloader();
        Optional.ofNullable(getSingletons()).ifPresent(s->s.add(reloader));
	}

	// Specifies that the method processes HTTP POST requests
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response resetActionCards() throws URISyntaxException {
		System.out.println("Main Page redirect");
		URI redirectUri;
		if (GameResources.games().isEmpty()) {
			redirectUri = new URI(GameResources.REL_GAMES_PATH + GameResources.CREATE_PATH);
		} else {
			redirectUri = new URI(GameResources.GAMES_PATH);
		}
		return Response.seeOther(redirectUri).build();
	}

	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return FitGWebApplication.PING_RESPONSE;
	}
	
	// Handle Application startup and shutdown
	// https://stackoverflow.com/questions/39821157/init-method-in-jersey-jax-rs-web-service
	@WebListener
	public class StartupListener implements ServletContextListener {

	    @Override
	    public void contextInitialized(ServletContextEvent event) {
	        // Perform action during application's startup
	    	// TODO: Load Games from file in local storage
	    	System.out.println("StartupListener - Servlet Startup");
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent event) {
	        // Perform action during application's shutdown
	    	// TODO: Save Games from file in local storage
	    	System.out.println("StartupListener - Servlet Shutdown");
	    }
	}
	
	public class Reloader implements ContainerLifecycleListener {

		Container container;

		public void reload(ResourceConfig newConfig) {
			container.reload(newConfig);
		}

		public void reload() {
			container.reload();
		}

		@Override
		public void onStartup(Container container) {
			this.container = container;
	    	System.out.println("Reloader - Server Startup");
		}

		@Override
		public void onReload(Container container) {
			// ignore or do whatever you want after reload has been done
	    	System.out.println("Reloader - Server Reload");
		}

		@Override
		public void onShutdown(Container container) {
			// ignore or do something after the container has been shutdown
	    	System.out.println("Reloader - Server Shutdown");
		}
	}
	
}
