package io.github.rmcdouga.fitg.fxviewer;

import java.awt.Graphics2D;

import org.jfree.fx.FXGraphics2D;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class FxApplication extends Application{
	private ConfigurableApplicationContext applicationContext;
	private static Stage stage;
	private final Canvas mapCanvas = new Canvas(Map.MAP_WIDTH, Map.MAP_HEIGHT);
	
	ApplicationContextInitializer<ConfigurableApplicationContext> initializer = context -> {
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setBeanClass(Graphics2D.class);
		def.setInstanceSupplier(()->new FXGraphics2D(mapCanvas.getGraphicsContext2D()));
		registry.registerBeanDefinition("graphics2D", def);
	};
	
	@Override
	public void init() {
	    applicationContext = new SpringApplicationBuilder(SpringApplication.class)
	    		.initializers(initializer)
	    		.run();
	}

	@Override
	public void stop() {
	    applicationContext.close();
	    stage.close();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		
		 primaryStage.setTitle("Hello World!");
            // Draw the map on the canvas
            applicationContext.getBean(Map.class).draw();
            
	        ScrollPane root = new ScrollPane();
	        root.setContent(mapCanvas);
	        primaryStage.setScene(new Scene(root, Map.MAP_WIDTH, Map.MAP_HEIGHT));
	        primaryStage.show();
	}

}
