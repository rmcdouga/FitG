package com.rogers.rmcdouga.fitg.basegame.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BaseGameProvinceTest {

	@Test
	void testGetName() {
		assertAll(
			()->assertEquals("One", BaseGameProvince.One.getName()),
			()->assertEquals("Two", BaseGameProvince.Two.getName()),
			()->assertEquals("Three", BaseGameProvince.Three.getName()),
			()->assertEquals("Four", BaseGameProvince.Four.getName()),
			()->assertEquals("Five", BaseGameProvince.Five.getName())
			);
	}

	@Test
	void testGetId() {
		assertAll(
			()->assertEquals(1, BaseGameProvince.One.getId()),
			()->assertEquals(2, BaseGameProvince.Two.getId()),
			()->assertEquals(3, BaseGameProvince.Three.getId()),
			()->assertEquals(4, BaseGameProvince.Four.getId()),
			()->assertEquals(5, BaseGameProvince.Five.getId())
		);
	}

	@Test
	void testGetStarSystems() {
		assertAll(
			()->assertEquals(6, BaseGameProvince.One.listStarSystems().size()),
			()->assertEquals(4, BaseGameProvince.Two.listStarSystems().size()),
			()->assertEquals(5, BaseGameProvince.Three.listStarSystems().size()),
			()->assertEquals(5, BaseGameProvince.Four.listStarSystems().size()),
			()->assertEquals(5, BaseGameProvince.Five.listStarSystems().size())
			);
	}

	@Test
	void testNumberOfProvinces() {
		assertEquals(5, BaseGameProvince.values().length);
	}
}
