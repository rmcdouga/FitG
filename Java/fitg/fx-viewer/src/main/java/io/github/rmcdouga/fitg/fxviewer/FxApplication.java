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
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FxApplication extends Application{
	private static int MAP_WIDTH = 4986;
	private static int MAP_HEIGHT = 3216;
	private ConfigurableApplicationContext applicationContext;
	private static Stage stage;
	private final Canvas mapCanvas = new Canvas(Map.MAP_HEIGHT, Map.MAP_WIDTH);
	
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
	        Button btn = new Button();
	        btn.setText("Say 'Hello World'");
	        btn.setOnAction(_-> System.out.println("Hello World!"));
	        
	        
	        // Create a canvas and get its graphics context
//	        Canvas canvas = new Canvas(4986, 3216); // 4986×3216
//            GraphicsContext gc = canvas.getGraphicsContext2D();
//            var g2 = new FXGraphics2D(gc);
            
            // Setup the spring application context so that the map can be drawn.
//            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
//            GenericBeanDefinition def = new GenericBeanDefinition();
//            def.setBeanClass(Graphics2D.class);
//            def.setInstanceSupplier(()->g2);
//            registry.registerBeanDefinition("graphics2D", def);
	        
            // Draw the map on the canvas
            applicationContext.getBean(Map.class).draw();
            
	        StackPane root = new StackPane();
	        root.getChildren().add(mapCanvas);
	        root.getChildren().add(btn);
	        primaryStage.setScene(new Scene(root, MAP_WIDTH, MAP_HEIGHT));
	        primaryStage.show();
	}

}
