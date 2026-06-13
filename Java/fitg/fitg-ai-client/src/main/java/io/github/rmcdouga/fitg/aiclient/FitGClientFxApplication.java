package io.github.rmcdouga.fitg.aiclient;

import java.util.Objects;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FitGClientFxApplication extends Application {
	private ConfigurableApplicationContext applicationContext;
	private Stage mainStage;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(FitGClientSpringApplication.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        mainStage.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
    	this.mainStage = stage;
    	
    	FxmlLoader fxmlLoader = applicationContext.getBean(FxmlLoader.class);
    	Parent parent = fxmlLoader.load("/fxml/mainwindow.fxml");
        Scene scene = new Scene(parent);
        stage.setTitle("FitG AI Client");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon/icon.png"));
       stage.show();
    }
}
