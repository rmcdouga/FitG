package com.rogers.rmcdouga.fitg.basegame.map;

public enum BaseGameSpaceRoute implements SpaceRoute {
	// Province 1 internal
	R_11_12(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Uracus, 1),
	R_11_13(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Zamorax, 0),
	R_11_14(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Atriard, 0),
	R_12_15(BaseGameStarSystem.Uracus, BaseGameStarSystem.Bex, 0),
	R_13_14(BaseGameStarSystem.Zamorax, BaseGameStarSystem.Atriard, 0),
	R_13_15(BaseGameStarSystem.Zamorax, BaseGameStarSystem.Bex, 0),
	R_13_16(BaseGameStarSystem.Zamorax, BaseGameStarSystem.Osirius, 0),
	R_14_16(BaseGameStarSystem.Atriard, BaseGameStarSystem.Osirius, 0),
	R_15_16(BaseGameStarSystem.Bex, BaseGameStarSystem.Osirius, 0),

	// Province 2 internal
	R_21_23(BaseGameStarSystem.Phisaria, BaseGameStarSystem.Ancore, 0),
	R_22_23(BaseGameStarSystem.Egrix, BaseGameStarSystem.Ancore, 0),
	R_22_24(BaseGameStarSystem.Egrix, BaseGameStarSystem.Gellas, 1),
	R_23_24(BaseGameStarSystem.Ancore, BaseGameStarSystem.Gellas, 1),
	
	// Province 3 internal
	R_31_32(BaseGameStarSystem.Pycius, BaseGameStarSystem.Ribex, 0),
	R_31_34(BaseGameStarSystem.Pycius, BaseGameStarSystem.Aziza, 0),
	R_32_35(BaseGameStarSystem.Ribex, BaseGameStarSystem.Luine, 1),
	R_33_34(BaseGameStarSystem.Rorth, BaseGameStarSystem.Aziza, 0),
	R_34_35(BaseGameStarSystem.Aziza, BaseGameStarSystem.Luine, 1),
	
	// Province 4 internal
	R_41_42(BaseGameStarSystem.Erwind, BaseGameStarSystem.Wex, 0),
	R_41_43(BaseGameStarSystem.Erwind, BaseGameStarSystem.Varu, 0),
	R_42_44(BaseGameStarSystem.Wex, BaseGameStarSystem.Deblon, 0),
	R_43_44(BaseGameStarSystem.Varu, BaseGameStarSystem.Deblon, 1),
	R_44_45(BaseGameStarSystem.Deblon, BaseGameStarSystem.Martigna, 0),
	
	// Province 5 internal
	R_51_52(BaseGameStarSystem.Zakir, BaseGameStarSystem.Eudox, 1),
	R_51_54(BaseGameStarSystem.Zakir, BaseGameStarSystem.Irajeba, 1),
	R_52_53(BaseGameStarSystem.Eudox, BaseGameStarSystem.Corusa, 0),
	R_52_55(BaseGameStarSystem.Eudox, BaseGameStarSystem.Moda, 0),
	R_54_55(BaseGameStarSystem.Irajeba, BaseGameStarSystem.Moda, 0),
	
	// Province 1/2
	R_11_21(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Phisaria, 1),
	R_14_23(BaseGameStarSystem.Atriard, BaseGameStarSystem.Ancore, 1),
	
	// Province 1/3
	R_14_31(BaseGameStarSystem.Atriard, BaseGameStarSystem.Pycius, 0),
	R_16_33(BaseGameStarSystem.Osirius, BaseGameStarSystem.Rorth, 1),
	
	// Province 1/4
	R_12_42(BaseGameStarSystem.Uracus, BaseGameStarSystem.Wex, 1),
	R_15_44(BaseGameStarSystem.Bex, BaseGameStarSystem.Deblon, 1),
	R_15_45(BaseGameStarSystem.Bex, BaseGameStarSystem.Martigna, 1),
	
	// Province 1/5
	R_11_52(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Eudox, 1),
	R_11_53(BaseGameStarSystem.Tardyn, BaseGameStarSystem.Corusa, 1),
	R_12_55(BaseGameStarSystem.Uracus, BaseGameStarSystem.Moda, 0),
	
	// Province 2/3
	R_24_32(BaseGameStarSystem.Gellas, BaseGameStarSystem.Ribex, 1),
	
	// Province 2/5
	R_21_53(BaseGameStarSystem.Phisaria, BaseGameStarSystem.Corusa, 1),
	
	// Province 3/4
	R_33_45(BaseGameStarSystem.Rorth, BaseGameStarSystem.Martigna, 1),
	
	// Province 4/5
	R_41_54(BaseGameStarSystem.Erwind, BaseGameStarSystem.Irajeba, 1),
	;
	
	private final BaseGameStarSystem terminus1;
	private final BaseGameStarSystem terminus2;
	private final int navigationStars;
	
	private BaseGameSpaceRoute(BaseGameStarSystem end1, BaseGameStarSystem end2, int navigationStars) {
		this.terminus1 = end1;
		this.terminus2 = end2;
		this.navigationStars = navigationStars;
	}

	@Override
	public BaseGameStarSystem getTerminus1() {
		return terminus1;
	}

	@Override
	public BaseGameStarSystem getTerminus2() {
		return terminus2;
	}

	@Override
	public int getNavigationStars() {
		return navigationStars;
	}
}
