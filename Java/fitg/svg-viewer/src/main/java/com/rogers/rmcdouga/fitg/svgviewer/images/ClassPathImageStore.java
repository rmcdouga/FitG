package com.rogers.rmcdouga.fitg.svgviewer.images;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;

public class ClassPathImageStore implements BaseGameImageStore {


	private static final Path IMAGE_PATH = Path.of("images");
	private static final String IMAGE_EXTENSION = ".png";
	
	private static final Path LOYALTY_IMAGE_FILENAME = Path.of("Marker_Loyalty.png");
	private static final Path MAP_IMAGE_FILENAME = Path.of("FITGMAP.jpg");

	private static final Map<BaseGameCharacter, Path> characterMap = initImageMap(BaseGameCharacter.class, ClassPathImageStore::genCharacterName);
	private static final Map<ImperialMilitaryUnit, Path> imperialUnitMap = initImageMap(ImperialMilitaryUnit.class, u->"Imperial_" + u + IMAGE_EXTENSION);
	private static final Map<RebelMilitaryUnit, Path> rebelUnitMap = initImageMap(RebelMilitaryUnit.class, u->"Rebel_" + u + IMAGE_EXTENSION);
	private static final Map<BaseGameImperialSpaceship, Path> imperialSpaceshipMap = initImageMap(BaseGameImperialSpaceship.class, u->"ImperialShip_" + u + IMAGE_EXTENSION);
	private static final Map<BaseGameRebelSpaceship, Path> rebelSpaceshipMap = initImageMap(BaseGameRebelSpaceship.class, u->"RebelShip_" + u + IMAGE_EXTENSION);

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
		return readImage(characterMap.get(character));
	}

	@Override
	public Path getImagePath(BaseGameCharacter character) {
		return imagePath(characterMap.get(character));
	}

	@Override
	public Image getImage(BaseGameImperialSpaceship spaceship) {
		return readImage(imperialSpaceshipMap.get(spaceship));
	}

	@Override
	public Path getImagePath(BaseGameImperialSpaceship spaceship) {
		return imagePath(imperialSpaceshipMap.get(spaceship));
	}

	@Override
	public Image getImage(BaseGameRebelSpaceship spaceship) {
		return readImage(rebelSpaceshipMap.get(spaceship));
	}

	@Override
	public Path getImagePath(BaseGameRebelSpaceship spaceship) {
		return imagePath(rebelSpaceshipMap.get(spaceship));
	}

	@Override
	public Image getImage(RebelMilitaryUnit unit) {
		return readImage(rebelUnitMap.get(unit));
	}

	@Override
	public Path getImagePath(RebelMilitaryUnit unit) {
		return imagePath(rebelUnitMap.get(unit));
	}

	@Override
	public Image getImage(ImperialMilitaryUnit unit) {
		return readImage(imperialUnitMap.get(unit));
	}

	@Override
	public Path getImagePath(ImperialMilitaryUnit unit) {
		return imagePath(imperialUnitMap.get(unit));
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

	private static String genCharacterName(BaseGameCharacter character) {
		String prefix = switch(character.allegience()) {
			case REBEL -> "RebelChar_";
			case IMPERIAL -> "ImperialChar_";
			default -> throw new IllegalStateException("Unexpected allegience (" + character.allegience().toString() + ") for character '" + character.toString() + "'.");
		};
		return prefix + character.toString() + IMAGE_EXTENSION;
	}

	private static <T extends Enum<T>> Map<T, Path> initImageMap(Class<T> clazz, Function<T, String> nameMapper) {
		return Stream.of(clazz.getEnumConstants()).collect(Collectors.toMap(Function.identity(), 		// Loop through all the enums inserting the enum as the key
																	 v->Path.of(nameMapper.apply(v)), 	// Map the enum to a name and use that name as the value
																	 (l, r) -> {						// Throw an exceptiom if we hit a duplicate (shouldn't happen) 
																		 		throw new IllegalArgumentException("Duplicate keys " + l + "and " + r + ".");
																	 			}, 
																	 ()->new EnumMap<T, Path>(clazz)));	// Store in an EnumMap for efficiency
		
	}
}
