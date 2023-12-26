package com.rogers.rmcdouga.fitg.renderer.images;

import java.awt.Image;
import java.nio.file.Path;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;

public interface BaseGameImageStore {

	Image getImage(Pdb pdb);

	Path getImagePath(Pdb pdb);

	Image getImage(BaseGameCharacter character);

	Path getImagePath(BaseGameCharacter character);

	Image getImage(BaseGameImperialSpaceship spaceship);

	Path getImagePath(BaseGameImperialSpaceship spaceship);

	Image getImage(BaseGameRebelSpaceship spaceship);

	Path getImagePath(BaseGameRebelSpaceship spaceship);

	Image getImage(RebelMilitaryUnit unit);

	Path getImagePath(RebelMilitaryUnit unit);

	Image getImage(ImperialMilitaryUnit unit);

	Path getImagePath(ImperialMilitaryUnit unit);

	Image getLoyaltyImage();

	Path getLoyaltyImagePath();

	Image getMapImage();

	Path getMapImagePath();
}