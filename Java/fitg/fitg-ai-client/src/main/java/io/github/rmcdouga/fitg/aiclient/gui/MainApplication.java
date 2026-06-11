package io.github.rmcdouga.fitg.aiclient.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.jfree.fx.FXGraphics2D;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.rmcdouga.fitg.aiclient.ContextUtil;

import java.awt.Graphics2D;
import java.util.Objects;

public class MainApplication extends Application {
	private ConfigurableApplicationContext applicationContext;
	private Stage mainStage;

//	ApplicationContextInitializer<ConfigurableApplicationContext> initializer = context -> {
//		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
//		GenericBeanDefinition def = new GenericBeanDefinition();
//		def.setBeanClass(Graphics2D.class);
//		def.setInstanceSupplier(()->new FXGraphics2D(mapCanvas.getGraphicsContext2D()));
//		registry.registerBeanDefinition("graphics2D", def);
//	};
	
//	@Override
//	public void init() {
////	    applicationContext = new SpringApplicationBuilder(SpringApplication.class)
////	    		.initializers(initializer)
////	    		.run();
//	    applicationContext = SpringApplication.run(SpringApplication.class);
//	}
//
//	@Override
//	public void stop() {
//	    applicationContext.close();
//	    mainStage.close();
//	}
//	
    @Override
    public void start(Stage stage) throws Exception {
    	this.mainStage = stage;
    	
        Parent parent = FXMLLoader.load(
                Objects.requireNonNull(MainApplication.class.getResource("/fxml/mainwindow.fxml"))
        		);
        Scene scene = new Scene(parent);
        stage.setTitle("FitG AI Client");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon/icon.png"));
        stage.setOnCloseRequest(_ -> {
            SpringApplication.exit(ContextUtil.getApplicationContext());
        });
       stage.show();
    }

    public static void launchApplication() {
        MainApplication.launch(MainApplication.class, "");
    }

}
