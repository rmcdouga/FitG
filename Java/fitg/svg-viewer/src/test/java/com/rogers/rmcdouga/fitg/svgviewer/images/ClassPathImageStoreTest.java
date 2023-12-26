package com.rogers.rmcdouga.fitg.svgviewer.images;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStore;

class ClassPathImageStoreTest {

	private final BaseGameImageStore underTest = new ClassPathImageStore();
	
	@ParameterizedTest
	@EnumSource
	void testGetImagePathPdb(Pdb pdb) throws Exception {
		testExists(underTest.getImagePath(pdb));
	}

	@ParameterizedTest
	@EnumSource
	void testGetImagePathBaseGameCharacter(BaseGameCharacter character) throws Exception {
		testExists(underTest.getImagePath(character));
	}

	@ParameterizedTest
	@EnumSource
	void testGetImagePathBaseGameImperialSpaceship(BaseGameImperialSpaceship spaceship) throws Exception {
		testExists(underTest.getImagePath(spaceship));
	}

	@ParameterizedTest
	@EnumSource
	void testGetImagePathBaseGameRebelSpaceship(BaseGameRebelSpaceship spaceship) throws Exception {
		testExists(underTest.getImagePath(spaceship));
	}

	@ParameterizedTest
	@EnumSource
	void testGetImagePathRebelMilitaryUnit(RebelMilitaryUnit unit) throws Exception {
		testExists(underTest.getImagePath(unit));
	}

	@ParameterizedTest
	@EnumSource
	void testGetImagePathImperialMilitaryUnit(ImperialMilitaryUnit unit) throws Exception {
		testExists(underTest.getImagePath(unit));
	}

	@Test
	void testGetLoyaltyImagePath() throws Exception {
		testExists(underTest.getLoyaltyImagePath());
	}

	@Test
	void testGetMapImagePath() throws Exception {
		testExists(underTest.getMapImagePath());
	}
	
	private void testExists(Path orig_path) throws URISyntaxException {
		URL url = this.getClass().getClassLoader().getResource(orig_path.toString());
		assertNotNull(url, "Image File '" + orig_path + "' does not exist.");
		Path path = Path.of(new URI(url.toString()));
		assertTrue(Files.exists(path), "Image File '" + orig_path + "' does not exist.");
	}
}
