package com.rogers.rmcdouga.fitg.svgviewer;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Planet {
	Mimulus(BaseGamePlanet.Mimulus),

	Magro(BaseGamePlanet.Magro),

	Fliad(BaseGamePlanet.Fliad),

	Kalgar(BaseGamePlanet.Kalgar),

	Bajukai(BaseGamePlanet.Bajukai),

	Tiglyf(BaseGamePlanet.Tiglyf),

	Ownex(BaseGamePlanet.Ownex),

	Adare(BaseGamePlanet.Adare),

	Mitrith(BaseGamePlanet.Mitrith),

	Jura(BaseGamePlanet.Jura),

	Diomas(BaseGamePlanet.Diomas),

	Liomax(BaseGamePlanet.Liomax),

	Orlog(BaseGamePlanet.Orlog),

	Icid(BaseGamePlanet.Icid),

	Cieson(BaseGamePlanet.Cieson),

	Etreg(BaseGamePlanet.Etreg),

	Quibron(BaseGamePlanet.Quibron),

	Angoff(BaseGamePlanet.Angoff),

	Charkhan(BaseGamePlanet.Charkhan),

	Pronox(BaseGamePlanet.Pronox),

	Lysenda(BaseGamePlanet.Lysenda),

	Orning(BaseGamePlanet.Orning),

	Chim(BaseGamePlanet.Chim),

	Tamset(BaseGamePlanet.Tamset),

	Unarpha(BaseGamePlanet.Unarpha),

	Suti(BaseGamePlanet.Suti),

	Tsipa(BaseGamePlanet.Tsipa),

	Squamot(BaseGamePlanet.Squamot),

	Midest(BaseGamePlanet.Midest),

	Akubera(BaseGamePlanet.Akubera),

	Mrane(BaseGamePlanet.Mrane),

	Kelta(BaseGamePlanet.Kelta),

	Troliso(BaseGamePlanet.Troliso),

	Heliax(BaseGamePlanet.Heliax),

	Lonica(BaseGamePlanet.Lonica),

	Horon(BaseGamePlanet.Horon),

	Solvia(BaseGamePlanet.Solvia),

	Cercis(BaseGamePlanet.Cercis),

	Rhexia(BaseGamePlanet.Rhexia),

	Tartio(BaseGamePlanet.Tartio),

	Ayod(BaseGamePlanet.Ayod),

	Barak(BaseGamePlanet.Barak),

	Liatris(BaseGamePlanet.Liatris),

	Xan(BaseGamePlanet.Xan),

	Aras(BaseGamePlanet.Aras),

	Capilax(BaseGamePlanet.Capilax),

	Adrax(BaseGamePlanet.Adrax),

	Scythia(BaseGamePlanet.Scythia),

	Annell(BaseGamePlanet.Annell),

	Trov(BaseGamePlanet.Trov),

	Niconi(BaseGamePlanet.Niconi);

	private final BaseGamePlanet bgPlanet;
	private static final java.util.Map<BaseGamePlanet, Planet> planetMap; // initialized in static block
	private static final int[] planet_diameter = { 190 * 2, 295 * 2, 390 * 2 };

	static {
		planetMap = new EnumMap<>(BaseGamePlanet.class);
		Stream.of(values()).forEach(p -> planetMap.put(p.bgPlanet, p));
	}

	private Planet(BaseGamePlanet bgPlanet) {
		this.bgPlanet = bgPlanet;
	}

	public static Stream<Planet> stream() {
		return Stream.of(values());
	}

	public static Stream<Planet> stream(Predicate<Planet> predicate) {
		return stream().filter(predicate);
	}

	public static Planet from(BaseGamePlanet p) {
		return planetMap.get(p);
	}

	public void draw(Graphics2D gc, int starSystemX, int starSystemY) {
		int i = planetOrdinal() - 1;
		// draw one planet orbit for each planet
		gc.setColor(Color.WHITE);
		gc.drawOval(starSystemX - (planet_diameter[i]/2), starSystemY - (planet_diameter[i]/2), planet_diameter[i], planet_diameter[i]);
	}
	
	private int planetOrdinal() {
		return bgPlanet.getId() % 10;
	}
}
