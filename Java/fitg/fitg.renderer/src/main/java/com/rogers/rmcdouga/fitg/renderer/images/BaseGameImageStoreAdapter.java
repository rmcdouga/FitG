package com.rogers.rmcdouga.fitg.renderer.images;

import java.awt.Image;
import java.nio.file.Path;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;

public class BaseGameImageStoreAdapter implements ImageStore {

	private final BaseGameImageStore baseGameImageStore;
	
	private BaseGameImageStoreAdapter(BaseGameImageStore baseGameImageStore) {
		this.baseGameImageStore = baseGameImageStore;
	}

	@Override
	public Image getImage(Pdb pdb) {
		return baseGameImageStore.getImage(pdb);
	}

	@Override
	public Path getImagePath(Pdb pdb) {
		return baseGameImageStore.getImagePath(pdb);
	}

	@Override
	public Image getImage(Character character) {
		return baseGameImageStore.getImage(BaseGameCharacter.of(character));
	}

	@Override
	public Path getImagePath(Character character) {
		return baseGameImageStore.getImagePath(BaseGameCharacter.of(character));
	}

	@Override
	public Image getImage(ImperialSpaceship spaceship) {
		return baseGameImageStore.getImage(BaseGameImperialSpaceship.of(spaceship));
	}

	@Override
	public Path getImagePath(ImperialSpaceship spaceship) {
		return baseGameImageStore.getImagePath(BaseGameImperialSpaceship.of(spaceship));
	}

	@Override
	public Image getImage(RebelSpaceship spaceship) {
		return baseGameImageStore.getImage(BaseGameRebelSpaceship.of(spaceship));
	}

	@Override
	public Path getImagePath(RebelSpaceship spaceship) {
		return baseGameImageStore.getImagePath(BaseGameRebelSpaceship.of(spaceship));
	}

	@Override
	public Image getImage(RebelMilitaryUnit unit) {
		return baseGameImageStore.getImage(unit);
	}

	@Override
	public Path getImagePath(RebelMilitaryUnit unit) {
		return baseGameImageStore.getImagePath(unit);
	}

	@Override
	public Image getImage(ImperialMilitaryUnit unit) {
		return baseGameImageStore.getImage(unit);
	}

	@Override
	public Path getImagePath(ImperialMilitaryUnit unit) {
		return baseGameImageStore.getImagePath(unit);
	}

	@Override
	public Image getLoyaltyImage() {
		return baseGameImageStore.getLoyaltyImage();
	}

	@Override
	public Path getLoyaltyImagePath() {
		return baseGameImageStore.getLoyaltyImagePath();
	}

	@Override
	public Image getMapImage() {
		return baseGameImageStore.getMapImage();
	}

	@Override
	public Path getMapImagePath() {
		return baseGameImageStore.getMapImagePath();
	}
	
	public static ImageStore wrap(BaseGameImageStore store) {
		return new BaseGameImageStoreAdapter(store);
	}
}
