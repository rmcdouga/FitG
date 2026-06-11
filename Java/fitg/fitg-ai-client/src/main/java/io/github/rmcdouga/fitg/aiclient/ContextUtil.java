package io.github.rmcdouga.fitg.aiclient;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.github.rmcdouga.fitg.aiclient.gui.MainApplicationController;
import jakarta.annotation.PreDestroy;

public class ContextUtil implements ApplicationContextAware {
    private final Logger log = Logger.getLogger(MainApplicationController.class.getName());

    
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.log(Level.INFO, "Application context set in ContextUtil" + applicationContext);
        ContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return Objects.requireNonNull(applicationContext);
    }
    
    @PreDestroy
    public void destroy() {
    	log.log(Level.INFO, "Callback triggered - @PreDestroy.");
    }

    public void close() {
    	log.log(Level.INFO, "Callback triggered - close().");
    }
}
