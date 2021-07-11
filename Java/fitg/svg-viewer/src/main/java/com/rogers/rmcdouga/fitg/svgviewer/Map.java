package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.GameBoard;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;

public final class Map {
	
	public static final int MAP_WIDTH = 4986;	// in pixels
	public static final int MAP_HEIGHT = 3216;	
	private static final Path MAP_IMAGE_FILENAME = Paths.get("FITGMAP.jpg");
	private final Graphics2D g2d;
	private final GameBoard gameBoard;
	
	public Map(Graphics2D g2d, GameBoard game) {
		this.g2d = g2d;
		this.gameBoard = game;
	}

	public void draw() throws IOException {	// TODO:  Only draw the star systems on this scenario's map.
		draw(gameBoard.getStarSystems());
	}
	
	public void draw(Collection<com.rogers.rmcdouga.fitg.basegame.map.StarSystem> starSystems) throws IOException {
		Image image = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(MAP_IMAGE_FILENAME.toString())));
		g2d.drawImage(image, 0, 0, null);

		LoyaltyTrackMarkerRenderer ltmr = LoyaltyTrackMarkerRenderer.create(g2d, gameBoard, gameBoard);
		starSystems.stream()
				   .map(BaseGameStarSystem::requireBgss)
				   .map(StarSystem::from)
				   .forEach(ss->{
					   ss.draw(g2d);
					   ltmr.draw(ss);
				   });

		// Draw a test Pdb
//		(new Pdb()).draw(g2d, );
	}
	
}
