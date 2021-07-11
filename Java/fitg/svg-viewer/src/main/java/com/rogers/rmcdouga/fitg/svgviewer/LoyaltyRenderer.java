package com.rogers.rmcdouga.fitg.svgviewer;

import static com.rogers.rmcdouga.fitg.svgviewer.Marker.imagePath;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.LoyaltyManager;

public class LoyaltyRenderer {

	private static final Path LOYALTY_IMAGE_FILENAME = Paths.get("Marker_Loyalty.png");
	private static final Image loyaltyMarkerImage;
	static {
		try {
			loyaltyMarkerImage = ImageIO.read(Objects.requireNonNull(Map.class.getClassLoader().getResourceAsStream(imagePath(LOYALTY_IMAGE_FILENAME).toString())));
		} catch (IOException e) {
			throw new IllegalStateException("Error reading file (" + LOYALTY_IMAGE_FILENAME + ").", e);
		}
	}

	private final LoyaltyManager lm;

	private LoyaltyRenderer(LoyaltyManager lm) {
		super();
		this.lm = lm;
	}
	
	public void draw(Graphics2D gc) {
		gc.drawImage(loyaltyMarkerImage, -loyaltyMarkerImage.getWidth(null)/2, -loyaltyMarkerImage.getHeight(null)/2, null);
	}
	
	public BaseGameLoyaltyType position(BaseGamePlanet p) {
		return BaseGameLoyaltyType.requireBglt(lm.getLoyalty(p));
	}

	public static LoyaltyRenderer create(LoyaltyManager lm) {
		return new LoyaltyRenderer(lm);
	}
}
