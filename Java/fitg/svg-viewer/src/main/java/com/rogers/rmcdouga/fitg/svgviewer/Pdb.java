package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

public final class Pdb extends Marker {
	private static final Path PDB_UP_IMAGE_FILENAME = Paths.get("Marker_PDB-Up.png");
	private static final Path PDB_DOWN_IMAGE_FILENAME = Paths.get("Marker_PDB-Down.png");

	public void draw(Graphics2D gc) throws IOException {
		Image image = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(imagePath(PDB_UP_IMAGE_FILENAME).toString())));
		gc.drawImage(image, 100, 100, null);
	}

}
