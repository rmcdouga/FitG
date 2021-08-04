package com.rogers.rmcdouga.fitg.svgviewer.images;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;

public class ClassPathImageStore implements ImageStore {

	private static final Path IMAGE_PATH = Paths.get("images");
	
	private static final Path LOYALTY_IMAGE_FILENAME = Paths.get("Marker_Loyalty.png");
	private static final Path MAP_IMAGE_FILENAME = Paths.get("FITGMAP.jpg");

	private static Path imagePath(Path filename) {
		return IMAGE_PATH.resolve(filename);
	}

	private static Image readImage(Path imageFilePath) {
		try {
			return ImageIO.read(Objects.requireNonNull(ClassPathImageStore.class.getClassLoader().getResourceAsStream(imagePath(imageFilePath).toString())));
		} catch (IOException e) {
			throw new IllegalStateException("Error reading file (" + imageFilePath + ").", e);
		}
	}
	
	@Override
	public Image getImage(Pdb pdb) {
		return readImage(PdbImage.of(pdb).imageLocation());
	}

	@Override
	public Path getImagePath(Pdb pdb) {
		return imagePath(PdbImage.of(pdb).imageLocation());
	}

	@Override
	public Image getImage(BaseGameCharacter character) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Path getImagePath(BaseGameCharacter character) {
		throw new UnsupportedOperationException("Not implemented yet!");		
	}

	@Override
	public Image getImage(BaseGameImperialSpaceship spaceship) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Path getImagePath(BaseGameImperialSpaceship spaceship) {
		throw new UnsupportedOperationException("Not implemented yet!");		
	}

	@Override
	public Image getImage(BaseGameRebelSpaceship spaceship) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Path getImagePath(BaseGameRebelSpaceship spaceship) {
		throw new UnsupportedOperationException("Not implemented yet!");		
	}

	@Override
	public Image getImage(RebelMilitaryUnit unit) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Path getImagePath(RebelMilitaryUnit unit) {
		throw new UnsupportedOperationException("Not implemented yet!");		
	}

	@Override
	public Image getImage(ImperialMilitaryUnit unit) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Path getImagePath(ImperialMilitaryUnit unit) {
		throw new UnsupportedOperationException("Not implemented yet!");		
	}

	@Override
	public Image getLoyaltyImage() {
		return readImage(LOYALTY_IMAGE_FILENAME);
	}

	@Override
	public Path getLoyaltyImagePath() {
		return imagePath(LOYALTY_IMAGE_FILENAME);
	}

	@Override
	public Image getMapImage() {
		return readImage(MAP_IMAGE_FILENAME);
	}

	@Override
	public Path getMapImagePath() {
		return imagePath(MAP_IMAGE_FILENAME);
	}

}
