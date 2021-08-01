package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.RaceType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameLoyaltyType;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;

public enum Planet {
	Mimulus(BaseGamePlanet.Mimulus, 0, 58, 28.8),
	Magro(BaseGamePlanet.Magro, -8, 34, 17.2, List.of(80.0, 80.8)),
	Fliad(BaseGamePlanet.Fliad, 0, 24, 12.25, List.of(50.0, 48.8)),
	Kalgar(BaseGamePlanet.Kalgar, 0, 57.9, 28.85),
	Bajukai(BaseGamePlanet.Bajukai, -10, 34, 17.2),
	Tiglyf(BaseGamePlanet.Tiglyf, 3, 58, 29.2),
	Ownex(BaseGamePlanet.Ownex, 0, 58, 28.8),
	Adare(BaseGamePlanet.Adare, 0, 34.1, 17.1, List.of(80.6, 80.6)),
	Mitrith(BaseGamePlanet.Mitrith, 0, 24.5, 12.2, List.of(49.3, 49.3)),
	Jura(BaseGamePlanet.Jura, 70, 47, 23),
	Diomas(BaseGamePlanet.Diomas, 60, List.of(69.0, 69.0)),
	Liomax(BaseGamePlanet.Liomax, 0, 58, 28.8),
	Orlog(BaseGamePlanet.Orlog, 0, 34, 17.2),
	Icid(BaseGamePlanet.Icid, 0, 24.3, 12.2, List.of(50.0)),
	Cieson(BaseGamePlanet.Cieson, 0, 58, 28.9),
	Etreg(BaseGamePlanet.Etreg, -5, 33, 17.4, List.of(80.1, 80.1)),
	Quibron(BaseGamePlanet.Quibron, 0, 60, 28.5),
	Angoff(BaseGamePlanet.Angoff, -12, 35, 17.3, List.of(78.8)),
	Charkhan(BaseGamePlanet.Charkhan, -10, 25, 12.35, List.of(49.4, 48.5)),
	Pronox(BaseGamePlanet.Pronox, 70, 47, 23),
	Lysenda(BaseGamePlanet.Lysenda, 55, List.of(68.8, 68.8, 68.8)),
	Orning(BaseGamePlanet.Orning, 75, 46.6, 23),
	Chim(BaseGamePlanet.Chim, 70, 47, 23),
	Tamset(BaseGamePlanet.Tamset, 55, List.of(68.8, 68.8)),
	Unarpha(BaseGamePlanet.Unarpha, -6, 58.9, 28.7),
	Suti(BaseGamePlanet.Suti, -15, 34, 17.3, List.of(79.5, 80.5)),
	Tsipa(BaseGamePlanet.Tsipa, -8, 24, 12.3, List.of(49.7, 48.9)),
	Squamot(BaseGamePlanet.Squamot, -10, 58, 29.2),
	Midest(BaseGamePlanet.Midest, 70, 47, 23),
	Akubera(BaseGamePlanet.Akubera, 55, List.of(68.8, 68.8, 68.8)),
	Mrane(BaseGamePlanet.Mrane, 0, 60, 28.4),
	Kelta(BaseGamePlanet.Kelta, -15, 35, 17.3, List.of(78.8)),
	Troliso(BaseGamePlanet.Troliso, -3, 58, 28.75),
	Heliax(BaseGamePlanet.Heliax, -15, 33.8, 17.3, List.of(79.5, 80.7)),
	Lonica(BaseGamePlanet.Lonica, 75, 46.1, 23),
	Horon(BaseGamePlanet.Horon, 0, 59, 28.7),
	Solvia(BaseGamePlanet.Solvia, -15, 34.35, 17.2),
	Cercis(BaseGamePlanet.Cercis, -15, 24.6, 12.35, List.of(49.2, 48.3)), 
	Rhexia(BaseGamePlanet.Rhexia, -4, 58.5, 28.5),
	Tartio(BaseGamePlanet.Tartio, -15, 34.1, 17.2, List.of(80.1, 80.1)),
	Ayod(BaseGamePlanet.Ayod, -8, 59, 29),
	Barak(BaseGamePlanet.Barak, -10, 58, 29.2),
	Liatris(BaseGamePlanet.Liatris, -20, 34, 17.3, List.of(80.4)),
	Xan(BaseGamePlanet.Xan, -10, 24.2, 12.3, List.of(50.2, 49.0)),
	Aras(BaseGamePlanet.Aras, -2, 59.5, 28.4),
	Capilax(BaseGamePlanet.Capilax, -15, 34.5, 17.3, List.of(79.3)),
	Adrax(BaseGamePlanet.Adrax, -5, 24.6, 12.4, List.of(49.2, 48.8)),
	Scythia(BaseGamePlanet.Scythia, -12, 58, 29),
	Annell(BaseGamePlanet.Annell, -5, 58.5, 28.8),
	Trov(BaseGamePlanet.Trov, -18, 34, 17.3, List.of(80.0, 80.0)),
	Niconi(BaseGamePlanet.Niconi, -2, 58, 28.6),
	;
	
	private static final java.util.Map<BaseGamePlanet, Planet> planetMap; // initialized in static block
	private static final int[] planet_diameter = { 189 * 2, 295 * 2, 390 * 2 };
	private static final int marker_size = 65;
	private static final boolean displayGuidelines = true;
//	private static final double[] loyalty_size = {Math.PI / 12.001, Math.PI / 12, Math.PI / 11.999};	// Size of loyalty in radians.

	static {
		planetMap = new EnumMap<>(BaseGamePlanet.class);
		Stream.of(values()).forEach(p -> planetMap.put(p.bgPlanet, p));
	}

	private final BaseGamePlanet bgPlanet;
	private final int diameterCheat;
	private final double orbitSize;	// Size in degrees.
	private final double loyaltySize;	// Size in degrees.
	private final List<Double> environSizes;	// Size in degrees.

	private Planet(BaseGamePlanet bgPlanet) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = 0;
		this.orbitSize = 30;
		this.loyaltySize = 14.9;
		this.environSizes = List.of();
	}

	private Planet(BaseGamePlanet bgPlanet, int diameterCheat) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = diameterCheat;
		this.orbitSize = 30;
		this.loyaltySize = 14.9;
		this.environSizes = List.of();
	}

	private Planet(BaseGamePlanet bgPlanet, int diameterCheat, List<Double> environSizes) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = diameterCheat;
		this.orbitSize = 30;
		this.loyaltySize = 14.9;
		this.environSizes = environSizes;
	}

	private Planet(BaseGamePlanet bgPlanet, int diameterCheat, double orbitSize, double loyaltySize) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = diameterCheat;
		this.orbitSize = orbitSize;
		this.loyaltySize = loyaltySize;
		int numEnvirons = bgPlanet.numEnvirons();
		double envSize = (360-(orbitSize+(loyaltySize*5)))/numEnvirons;
		Double[] envSizes = new Double[numEnvirons];
		Arrays.fill(envSizes, envSize);
		this.environSizes = List.of(envSizes);	// Complete the circle with one environ.
	}

	private Planet(BaseGamePlanet bgPlanet, int diameterCheat, double orbitSize, double loyaltySize, List<Double> environSizes) {
		this.bgPlanet = bgPlanet;
		this.diameterCheat = diameterCheat;
		this.orbitSize = orbitSize;
		this.loyaltySize = loyaltySize;
		this.environSizes = environSizes;
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

	public BaseGamePlanet bgPlanet() {
		return this.bgPlanet;
	}

	public void draw(Graphics2D gc) {
		// draw one planet orbit for each planet
		int radius = radius();
		if (displayGuidelines) {
			// draw circle
			gc.setColor(Color.WHITE);
			gc.drawOval(-radius, -radius, radius*2, radius*2);
			
			// draw sector lines
			gc.drawLine(0, -radius - marker_size / 2, 0, -radius + marker_size / 2);
		}
		
		// draw markers
		if (displayGuidelines) {
			drawPlanetGuidlines((Graphics2D)gc.create(), radius);
		}
	}

	private void drawPlanetGuidlines(Graphics2D gc, int radius) {
		gc.rotate(Math.toRadians(this.orbitSize));	// rotate past the orbit arc
		
		// draw sector lines
		int halfMarkerSize = marker_size / 2;
		gc.drawLine(0, -radius - halfMarkerSize, 0, -radius + halfMarkerSize);
		double arc_size = this.loyaltySize;
		
		for (int i = 0; i < 5; i++) {
			gc.rotate(Math.toRadians(arc_size));
			gc.drawLine(0, -radius - halfMarkerSize, 0, -radius + halfMarkerSize);
			gc.drawString(Integer.toString(i), 0, -radius);
		}
		// Draw environs
		for (int i = 0; i < environSizes.size(); i++) {
			double environSize = Math.toRadians(environSizes.get(i));
			Environ environ = bgPlanet.environ(i);
			String environStats = environString(environ);
			writeTextStraightCentred((Graphics2D)gc.create(), -radius - (halfMarkerSize / 2), environSize / 2, environStats);
			gc.rotate(environSize);
			gc.drawLine(0, -radius - halfMarkerSize, 0, -radius + halfMarkerSize);
		}
		
		
	}

	private static void writeTextStraightCentred(Graphics2D gc, int radius, double offset, String string) {
		gc.rotate(offset);
		Font font = gc.getFont();
		FontRenderContext frc = gc.getFontRenderContext();
		
		GlyphVector gv = font.createGlyphVector(frc, string);
		Rectangle2D visualBounds = gv.getVisualBounds();
		gc.drawString(string, (int)(-visualBounds.getCenterX()), radius);
	}

	private String environString(Environ environ) {
		StringBuilder bldr = new StringBuilder();
		bldr.append(environ.getType().getName());
		bldr.append(" Size: ");
		bldr.append(environ.getSize());
		for (RaceType race : environ.getRaces()) {
			bldr.append(" ").append(race.getName());
			if (race.isStarFaring()) {
				bldr.append("*");
			}
		}
		environ.getResources().ifPresent(r->bldr.append(" Res: ").append(r).append(environ.isStarResources() ? "*" : ""));
		environ.getCreature().ifPresent(c->bldr.append(" ").append(c.name()));
		environ.getSovereign().ifPresent(s->bldr.append(" ").append(s.name()));
		environ.getCoupRating().ifPresent(c->bldr.append(" Coup:").append(c));
		return bldr.toString();
	}

	
	public Graphics2D translateToOrbit(Graphics2D g2d) {
		var planetG2d = (Graphics2D)g2d.create();
		// rotate to centre of the orbit box
		double arc_size = this.orbitSize  / 2;  
		planetG2d.rotate(Math.toRadians(arc_size));	// rotate past the orbit arc
		planetG2d.translate(0, -radius());
		return planetG2d;
	}
	
	public Graphics2D translateToLoyalty(Graphics2D g2d, BaseGameLoyaltyType loyaltyType) {
		var planetG2d = (Graphics2D)g2d.create();
		// rotate past orbit box + other loyalties and to centre of the current loyaltyType
		double arc_size = this.orbitSize + (this.loyaltySize * loyaltyType.ordinal()) + (loyaltySize / 2);  
		planetG2d.rotate(Math.toRadians(arc_size));	// rotate past the orbit arc
		planetG2d.translate(0, -radius());
		return planetG2d;
	}
	
	private int planetOrdinal() {
		return bgPlanet.getId() % 10;
	}
	
	public int radius() {
		int local_diameter = planet_diameter[planetOrdinal() - 1] + diameterCheat;
		return local_diameter/2;
	}
	
	/**
	 * start	- Start of arc in degrees
	 * end		- End of arc in degrees
	 *  0 degrees is straight up, movement is clockwise.
	 */
	public static record Arc(double start, double end) {};
	
	public Arc orbit() {
		return new Arc(0, orbitSize);
	}
	
	public Arc loyalty(BaseGameLoyaltyType type) {
		return loyalty(type.ordinal());
	}
	
	private Arc loyalty(int index) {
		return new Arc(loyaltySize*index, loyaltySize*(index+1));
	}
	
	public Arc environ(int index) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
