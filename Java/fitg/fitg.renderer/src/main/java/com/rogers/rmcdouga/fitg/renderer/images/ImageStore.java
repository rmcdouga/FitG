package com.rogers.rmcdouga.fitg.renderer.images;

import java.awt.Image;
import java.nio.file.Path;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;

public interface ImageStore {

	Image getImage(Pdb pdb);

	Path getImagePath(Pdb pdb);

	Image getImage(Character character);

	Path getImagePath(Character character);

	Image getImage(ImperialSpaceship spaceship);

	Path getImagePath(ImperialSpaceship spaceship);

	Image getImage(RebelSpaceship spaceship);

	Path getImagePath(RebelSpaceship spaceship);

	Image getImage(RebelMilitaryUnit unit);

	Path getImagePath(RebelMilitaryUnit unit);

	Image getImage(ImperialMilitaryUnit unit);

	Path getImagePath(ImperialMilitaryUnit unit);

	Image getLoyaltyImage();

	Path getLoyaltyImagePath();

	Image getMapImage();

	Path getMapImagePath();
}