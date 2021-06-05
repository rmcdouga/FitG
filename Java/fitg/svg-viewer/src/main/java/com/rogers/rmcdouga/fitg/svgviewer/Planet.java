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
	Jura(BaseGamePlanet.Jura, 70),
	Diomas(BaseGamePlanet.Diomas, 60),
	Liomax(BaseGamePlanet.Liomax),
	Orlog(BaseGamePlanet.Orlog),
	Icid(BaseGamePlanet.Icid),
	Cieson(BaseGamePlanet.Cieson),
	Etreg(BaseGamePlanet.Etreg),
	Quibron(BaseGamePlanet.Quibron),
	Angoff(BaseGamePlanet.Angoff),
	Charkhan(BaseGamePlanet.Charkhan),
	Pronox(BaseGamePlanet.Pronox, 60),
	Lysenda(BaseGamePlanet.Lysenda, 55),
	Orning(BaseGamePlanet.Orning, 60),
	Chim(BaseGamePlanet.Chim, 60),
	Tamset(BaseGamePlanet.Tamset, 55),
	Unarpha(BaseGamePlanet.Unarpha),
	Suti(BaseGamePlanet.Suti),
	Tsipa(BaseGamePlanet.Tsipa),
	Squamot(BaseGamePlanet.Squamot),
	Midest(BaseGamePlanet.Midest, 60),
	Akubera(BaseGamePlanet.Akubera, 55),
	Mrane(BaseGamePlanet.Mrane, -5),
	Kelta(BaseGamePlanet.Kelta, -15),
	Troliso(BaseGamePlanet.Troliso),
	Heliax(BaseGamePlanet.Heliax),
	Lonica(BaseGamePlanet.Lonica, 65),
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

	private static final java.util.Map<BaseGamePlanet, Planet> planetMap; // initialized in static block
	private static final int[] planet_diameter = { 189 * 2, 295 * 2, 390 * 2 };
	private static final int marker_size = 65;
	private static final boolean displayGuidelines = true;

	static {
		planetMap = new EnumMap<>(BaseGamePlanet.class);
		Stream.of(values()).forEach(p -> planetMap.put(p.bgPlanet, p));
	}

	private final BaseGamePlanet bgPlanet;
	private final int diameterCheat;

	private Planet(BaseGamePlanet bgPlanet) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = 0;
	}

	private Planet(BaseGamePlanet bgPlanet, int diameterCheat) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = diameterCheat;
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

	public void draw(Graphics2D gc) {
		int i = planetOrdinal() - 1;
		// draw one planet orbit for each planet
		int local_diameter = planet_diameter[i] + diameterCheat;
		int radius = local_diameter/2;
		if (displayGuidelines) {
			// draw circle
			gc.setColor(Color.WHITE);
			gc.drawOval(-radius, -radius, local_diameter, local_diameter);
			
			// draw sector lines
			gc.drawLine(0, -radius - marker_size / 2, 0, -radius + marker_size / 2);
		}
		
		// draw markers
		Graphics2D gc2 = (Graphics2D)gc.create();
		gc2.rotate(Math.PI / 2);
		if (displayGuidelines) {
			// draw sector lines
			gc2.drawLine(0, -radius - marker_size / 2, 0, -radius + marker_size / 2);
			
		}
		gc2.rotate(Math.PI / -12);
		if (displayGuidelines) {
			// draw sector lines
			gc2.drawLine(0, -radius - marker_size / 2, 0, -radius + marker_size / 2);
			
		}
	}
	
	private int planetOrdinal() {
		return bgPlanet.getId() % 10;
	}
}
