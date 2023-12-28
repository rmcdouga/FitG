package com.rogers.rmcdouga.fitg.renderer.graphics2d;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations;
import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStore;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;

public final class Map {
	
	public static final int MAP_WIDTH = 4986;	// in pixels
	public static final int MAP_HEIGHT = 3216;	
	private final Graphics2D g2d;
	private final GameBoard gameBoard;
	private final CounterLocator counterLocator;
	private final ImageStore imageStore;
	
	public Map(Graphics2D g2d, ImageStore imageStore, GameBoard gameBoard, CounterLocator counterLocator) {
		this.g2d = g2d;
		this.gameBoard = gameBoard;
		this.counterLocator = counterLocator;
		this.imageStore = imageStore;
	}

	public void draw() throws IOException {
		draw(gameBoard.getStarSystems());
	}
	
	public void draw(Collection<com.rogers.rmcdouga.fitg.basegame.map.StarSystem> starSystems) throws IOException {
		Image image = imageStore.getMapImage();
		g2d.drawImage(image, 0, 0, null);

		
		LoyaltyTrackMarkerRenderer ltmr = LoyaltyTrackMarkerRenderer.create(g2d, gameBoard, gameBoard, imageStore);
		EnvironRenderer er = EnvironRenderer.create(g2d, counterLocator, new CounterRenderer(imageStore));
		List<BiConsumer<Graphics2D, Planet>> consumers = List.of(
				ltmr::draw,
				er::draw
				);
		starSystems.stream()
				   .map(BaseGameStarSystem::requireBgss)
				   .map(StarSystem::from)
				   .forEach(ss->{
					   ss.draw(g2d);
					   ss.drawPerPlanet(g2d, (gc, p)->consumers.forEach(c->c.accept(gc, p)));	// For each planet, apply each of the consumers in turn.
				   });
	}
}
