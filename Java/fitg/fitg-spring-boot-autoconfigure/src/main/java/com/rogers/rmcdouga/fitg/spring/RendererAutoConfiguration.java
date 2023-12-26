package com.rogers.rmcdouga.fitg.spring;

import java.awt.Graphics2D;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

@AutoConfiguration
public class RendererAutoConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public Map mapRenderer(Graphics2D g2d, ImageStore imageStore, GameBoard gameBoard, CounterLocator counterLocator) {
		return new Map(g2d, imageStore, gameBoard, counterLocator);
	}

}
