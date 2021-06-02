package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;

public final class Map {
	
	private static final int MAP_WIDTH = 4986;	// in pixels
	private static final int MAP_HEIGHT = 3216;	
	private static final Path MAP_IMAGE_FILENAME = Paths.get("FITGMAP.jpg");
	
	public String draw() throws IOException {
		SVGGraphics2D g2 = new SVGGraphics2D(MAP_WIDTH, MAP_HEIGHT);
		draw(g2);
		return g2.getSVGDocument();
	}

	public void draw(Graphics2D gc) throws IOException {
		Image image = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(MAP_IMAGE_FILENAME.toString())));
		gc.drawImage(image, 0, 0, null);
		StarSystem.drawAll(gc);
		
		// Draw a test Pdb
		(new Pdb()).draw(gc);
	}
}
