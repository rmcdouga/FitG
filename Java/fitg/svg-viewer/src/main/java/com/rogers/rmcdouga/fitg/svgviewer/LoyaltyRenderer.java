package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;
import com.rogers.rmcdouga.fitg.svgviewer.images.ImageStore;

public class LoyaltyRenderer {


	private final LoyaltyManager lm;
	private final Image loyaltyMarkerImage;

	private LoyaltyRenderer(LoyaltyManager lm, ImageStore imageStore) {
		super();
		this.lm = lm;
		this.loyaltyMarkerImage = imageStore.getLoyaltyImage();
	}
	
	public void draw(Graphics2D gc) {
		gc.drawImage(loyaltyMarkerImage, -loyaltyMarkerImage.getWidth(null)/2, -loyaltyMarkerImage.getHeight(null)/2, null);
	}
	
	public BaseGameLoyaltyType position(BaseGamePlanet p) {
		return BaseGameLoyaltyType.requireBglt(lm.getLoyalty(p));
	}

	public static LoyaltyRenderer create(LoyaltyManager lm, ImageStore imageStore) {
		return new LoyaltyRenderer(lm, imageStore);
	}
}
