package com.github.rmcdouga.fitg.webapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.collections4.map.SingletonMap;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import com.github.rmcdouga.fitg.webapp.resources.GamesResources;

@ApplicationPath(FitGWebApplication.APPLICATION_TOP_LEVEL)
@Path("")
public class FitGWebApplication extends ResourceConfig {

	private static final String SAVED_GAMES_FILENAME = "FitGSavedGames.json";
	private static final String SAVED_GAMES_DIR = "FitG";
	public static final String APPLICATION_TOP_LEVEL = "/";
	public static final String PING_PATH = "/ping";
	public static final String PING_RESPONSE = "Ping!";

	public FitGWebApplication() {
		super();
		packages(this.getClass().getPackage().getName());
		register(MvcFeature.class);
		register(MustacheMvcFeature.class);
        register(LoggingFeature.class);
        register(FitGDefaultExceptionMapper.class);
        
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "ALL");
        property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, "PAYLOAD_ANY");
        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
        property("com.sun.jersey.config.feature.Debug", "true");

        register(new Reloader());
	}
	
	private boolean isRunningOnAzure() {
		String username = System.getProperty("user.name").toLowerCase();
		return !(username.startsWith("rob") && username.endsWith("mcdougall"));
	}
	
	private java.nio.file.Path saveDirectory() throws IOException {
		java.nio.file.Path rootLocation = Paths.get("D:", "home", "data");
		if (!Files.exists(rootLocation)) {
			throw new FileNotFoundException("Unable to find the root data directory. (" + rootLocation.toString() + ").");
		}
		java.nio.file.Path saveDirectory = rootLocation.resolve(SAVED_GAMES_DIR);
		if (!Files.exists(saveDirectory)) {
			Files.createDirectory(saveDirectory);
		}
		return saveDirectory;
	}
	
	private Optional<java.nio.file.Path> saveFile() {
		java.nio.file.Path saveFile = null;
		try {
			saveFile = saveDirectory().resolve(SAVED_GAMES_FILENAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(saveFile);
	}
	

	// Specifies that the method processes HTTP POST requests
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response resetActionCards() throws URISyntaxException {
		System.out.println("Main Page redirect");
		URI redirectUri;
		if (GamesResources.numGames() == 0) {
			redirectUri = new URI(GamesResources.REL_GAMES_PATH + GamesResources.CREATE_PATH);
		} else {
			redirectUri = new URI(GamesResources.REL_GAMES_PATH);
		}
		return Response.seeOther(redirectUri).build();
	}

	// Ping Test
	@GET
	@Path(FitGWebApplication.PING_PATH)
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() throws IOException {
//		return FitGWebApplication.PING_RESPONSE + " User='" + System.getProperty("user.name") + "', System name='" + InetAddress.getLocalHost().getHostName() + "'.";
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
			pw.println(FitGWebApplication.PING_RESPONSE + " Running on Azure = " + Boolean.toString(isRunningOnAzure()));
			try (Stream<java.nio.file.Path> pathStream = Files.walk(saveDirectory())) {
				pathStream.forEach(p->pw.println(p.toString()));
			}
			return sw.toString();
		}		
	}
	
	// Handle Application startup and shutdown
	// https://stackoverflow.com/questions/39821157/init-method-in-jersey-jax-rs-web-service
	@WebListener
	public static class StartupListener implements ServletContextListener {

	    public StartupListener() {
			super();
		}

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

		private void loadSaveFile() {
			Optional<java.nio.file.Path> saveFileOptional = saveFile();
	    	if (saveFileOptional.isPresent() && Files.exists(saveFileOptional.get())) {
				java.nio.file.Path saveFile = saveFileOptional.get();
				try (Reader r = Files.newBufferedReader(saveFile)) {
					GamesResources.loadGames(r);
				} catch (IOException e) {
					// We can't throw exceptions, so just write out and carry on.
					e.printStackTrace();
				}
	    	}
		}

		private void writeSaveFile() {
			Optional<java.nio.file.Path> saveFileOptional = saveFile();
	    	if (saveFileOptional.isPresent()) {
	    		try {
					java.nio.file.Path saveFile = saveFileOptional.get();
					if (GamesResources.numGames() > 0) {
						try (Writer w = Files.newBufferedWriter(saveFile)) {
							GamesResources.saveGames(w);
						}
					} else if (Files.exists(saveFile)) {
						// No games are present, so we'll remove the file.
						Files.delete(saveFile);
					}
				} catch (IOException e) {
					// We can't throw exceptions, so just write out and carry on.
					e.printStackTrace();
				}
	    	}
		}

		@Override
		public void onStartup(Container container) {
			this.container = container;
	    	System.out.println("Reloader - Server Startup");
	    	loadSaveFile();
		}

		@Override
		public void onReload(Container container) {
			// ignore or do whatever you want after reload has been done
	    	System.out.println("Reloader - Server Reload");
	    	loadSaveFile();
		}

		@Override
		public void onShutdown(Container container) {
			// ignore or do something after the container has been shutdown
	    	System.out.println("Reloader - Server Shutdown");
	    	writeSaveFile();
		}
	}
	
	@Provider
	public static class FitGDefaultExceptionMapper implements ExceptionMapper<WebApplicationException> {
	  public Response toResponse(WebApplicationException ex) {
	    return Response.fromResponse(ex.getResponse()).
	      entity(new Viewable("/com/github/rmcdouga/fitg/webapp/Exception.mustache", new SingletonMap<String, Object>("message", ex.getResponse().getStatusInfo().getReasonPhrase() + ": " + ex.getMessage()))).
	      type(MediaType.TEXT_HTML_TYPE).
	      build();
	  }
	}
}
