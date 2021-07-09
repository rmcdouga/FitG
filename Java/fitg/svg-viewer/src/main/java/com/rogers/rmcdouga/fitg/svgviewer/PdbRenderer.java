package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;

public final class PdbRenderer extends Marker {
	private static final Path PDB_UP_IMAGE_FILENAME = Paths.get("Marker_PDB-Up.png");
	private static final Path PDB_DOWN_IMAGE_FILENAME = Paths.get("Marker_PDB-Down.png");
	private static final Image pdbUpImage;
	private static final Image pdbDownImage;
	static {
		try {
			pdbUpImage = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(imagePath(PDB_UP_IMAGE_FILENAME).toString())));
		} catch (IOException e) {
			throw new IllegalStateException("Error reading file (" + PDB_UP_IMAGE_FILENAME + ").", e);
		}
		try {
			pdbDownImage = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(imagePath(PDB_DOWN_IMAGE_FILENAME).toString())));
		} catch (IOException e) {
			throw new IllegalStateException("Error reading file (" + PDB_DOWN_IMAGE_FILENAME + ").", e);
		}
	}
	
	private final Graphics2D gc;
	private final PdbManager pdbm;
	
	private PdbRenderer(Graphics2D gc, PdbManager pdbm) {
		super();
		this.gc = gc;
		this.pdbm = pdbm;
	}

	public void draw(StarSystem ss) {
		ss.drawPerPlanet(gc, this::draw);
	}

	private void draw(Graphics2D gc, Planet p) {
		Pdb pdb = this.pdbm.getPdb(p.bgPlanet());
		var img = pdb.isUp() ? pdbUpImage : pdbDownImage;
		BaseGameLoyaltyType location = switch(pdb.level()) {
			case ZERO -> BaseGameLoyaltyType.Neutral;
			case ONE -> BaseGameLoyaltyType.Loyal;
			case TWO -> BaseGameLoyaltyType.Patriotic;
			};
		p.translateToLoyalty(gc, location).drawImage(img, -img.getWidth(null)/2, -img.getHeight(null)/2, null);
	}
	
	public static PdbRenderer from(Graphics2D gc, PdbManager pdbm) {
		return new PdbRenderer(gc, pdbm);
	}
}
