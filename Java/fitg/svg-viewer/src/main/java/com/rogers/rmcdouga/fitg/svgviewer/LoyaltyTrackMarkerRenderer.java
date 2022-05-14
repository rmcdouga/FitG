package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;
import com.rogers.rmcdouga.fitg.basegame.map.PdbManager;
import com.rogers.rmcdouga.fitg.svgviewer.images.ImageStore;

public class LoyaltyTrackMarkerRenderer {
	private static int MARKER_OFFSET = 10;  	// This is the offset from centre for Loyalty markers (typically up and to the left)

	private final Graphics2D gc;
	private final PdbRenderer pdbr;
	private final LoyaltyRenderer loyaltyr;

	private LoyaltyTrackMarkerRenderer(Graphics2D gc, PdbManager pdbm, LoyaltyManager lm, ImageStore is) {
		super();
		this.gc = gc;
		this.pdbr = PdbRenderer.create(pdbm, is);
		this.loyaltyr = LoyaltyRenderer.create(lm, is);
	}

//	public void draw(StarSystem ss) {
//		ss.drawPerPlanet(gc, this::draw);
//	}
//
	public void draw(Graphics2D gc, Planet p) {
		BaseGamePlanet bgPlanet = p.bgPlanet();
		BaseGameLoyaltyType loyaltyPosition = loyaltyr.position(bgPlanet);
		BaseGameLoyaltyType pdbPosition = pdbr.position(bgPlanet);
		if (loyaltyPosition == pdbPosition) {
			Graphics2D translatedGc = p.translateToLoyalty(gc, loyaltyPosition);
			pdbr.draw(offset(translatedGc, MARKER_OFFSET), bgPlanet);
			loyaltyr.draw(offset(translatedGc, -MARKER_OFFSET));
		} else {
			pdbr.draw(p.translateToLoyalty(gc, pdbPosition), bgPlanet);
			loyaltyr.draw(p.translateToLoyalty(gc, loyaltyPosition));
		}
	}

	private Graphics2D offset(Graphics2D src, int amount) {
		var result = (Graphics2D)src.create();
		result.translate(amount, amount);
		return result;
	}
	
	public static LoyaltyTrackMarkerRenderer create(Graphics2D gc, PdbManager pdbm, LoyaltyManager lm, ImageStore is) {
		return new LoyaltyTrackMarkerRenderer(gc, pdbm, lm, is);
	}
}
