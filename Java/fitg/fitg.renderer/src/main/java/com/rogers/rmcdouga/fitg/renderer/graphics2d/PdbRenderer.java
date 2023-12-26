package com.rogers.rmcdouga.fitg.renderer.graphics2d;

import java.awt.Graphics2D;
import java.awt.Image;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;

public final class PdbRenderer extends Marker {
	private final Image pdbUpImage;
	private final Image pdbDownImage;
	
	private final PdbManager pdbm;
	
	private PdbRenderer(PdbManager pdbm, ImageStore is) {
		super();
		this.pdbm = pdbm;
		this.pdbUpImage = is.getImage(Pdb.UP_0);
		this.pdbDownImage = is.getImage(Pdb.DOWN_0);
	}

	public void draw(Graphics2D gc, BaseGamePlanet p) {
		Pdb pdb = this.pdbm.getPdb(p);
		var img = pdb.isUp() ? pdbUpImage : pdbDownImage;
		gc.drawImage(img, -img.getWidth(null)/2, -img.getHeight(null)/2, null);
	}

	public BaseGameLoyaltyType position(BaseGamePlanet p) {
		Pdb pdb = this.pdbm.getPdb(p);
		return switch(pdb.level()) {	// TODO: Handle Rebel-Controlled
		case ZERO -> BaseGameLoyaltyType.Neutral;
		case ONE -> BaseGameLoyaltyType.Loyal;
		case TWO -> BaseGameLoyaltyType.Patriotic;
		};
	}

	public static PdbRenderer create(PdbManager pdbm, ImageStore is) {
		return new PdbRenderer(pdbm, is);
	}
}
