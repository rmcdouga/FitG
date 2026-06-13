package io.github.rmcdouga.fitg.aiclient;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

@Component
public class FxmlLoader {

    private final ApplicationContext context;
    private final ResourceLoader resourceLoader;

    public FxmlLoader(ApplicationContext context, ResourceLoader resourceLoader) {
        this.context = context;
		this.resourceLoader = resourceLoader;
    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(resourceLoader.getResource("classpath:" + fxmlPath).getURL());
        loader.setClassLoader(context.getClassLoader());
        return loader.load();
    }
}
