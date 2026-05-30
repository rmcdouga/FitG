package io.github.rmcdouga.fitg.fxviewer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

import io.github.rmcdouga.fitg.fxviewer.images.ClassPathImageStore;
import javafx.application.Application;

@SpringBootApplication
public class SpringApplication {
	public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }
	
	@Bean
	public static ImageStore classPathImageStore() {
		return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
	}


}
