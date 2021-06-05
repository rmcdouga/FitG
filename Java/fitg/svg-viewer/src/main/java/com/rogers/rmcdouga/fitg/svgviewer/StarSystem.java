package com.rogers.rmcdouga.fitg.svgviewer;

import com.rogers.rmcdouga.fitg.basegame.map.BaseGamePlanet;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum StarSystem {
	Tardyn(BaseGameStarSystem.Tardyn, 2887, 576), 
	Uracus(BaseGameStarSystem.Uracus,  2386, 1155), 
	Zamorax(BaseGameStarSystem.Zamorax, 2576, 1856), 
	Atriard(BaseGameStarSystem.Atriard, 3139, 1421),
	Bex(BaseGameStarSystem.Bex, 1917, 1788), 
	Osirius(BaseGameStarSystem.Osirius, 2860, 2381),
	Phisaria(BaseGameStarSystem.Phisaria, 3842, 369), 
	Egrix(BaseGameStarSystem.Egrix, 4532, 464),
	Ancore(BaseGameStarSystem.Ancore, 3995, 1102), 
	Gellas(BaseGameStarSystem.Gellas, 4659, 1281),
	Pycius(BaseGameStarSystem.Pycius, 3687, 2076), 
	Ribex(BaseGameStarSystem.Ribex, 4532, 2069),
	Rorth(BaseGameStarSystem.Rorth, 3267, 2959), 
	Aziza(BaseGameStarSystem.Aziza, 3892, 2823),
	Luine(BaseGameStarSystem.Luine, 4635, 2853), 
	Erwind(BaseGameStarSystem.Erwind, 1049, 1967),
	Wex(BaseGameStarSystem.Wex, 1638, 2404), 
	Varu(BaseGameStarSystem.Varu, 976, 2747),
	Deblon(BaseGameStarSystem.Deblon, 1928, 2852), 
	Martigna(BaseGameStarSystem.Martigna, 2634, 2954),
	Zakir(BaseGameStarSystem.Zakir, 903, 457),
	Eudox(BaseGameStarSystem.Eudox, 1725, 458),
	Corusa(BaseGameStarSystem.Corusa, 2506, 258),
	Irajeba(BaseGameStarSystem.Irajeba, 995, 1247),
	Moda(BaseGameStarSystem.Moda, 1598, 1145);

	private final BaseGameStarSystem bgStarSystem;
	private final int x;
	private final int y;
	
	// Temp constants
	private static final int circle_diameter = 16;
	private static final boolean displayGuidelines = true;

	private StarSystem(BaseGameStarSystem bgStarSystem, int x, int y) {
		this.bgStarSystem = bgStarSystem;
		this.x = x;
		this.y = y;
	}

	public static Stream<StarSystem> stream() {
		return Stream.of(values());
	}

	public static Stream<StarSystem> stream(Predicate<StarSystem> predicate) {
		return stream().filter(predicate);
	}

	public void draw(Graphics2D gc) {
		if (displayGuidelines) {
			gc.setColor(Color.BLACK);
			gc.drawOval(this.x - (circle_diameter/2), this.y - (circle_diameter/2), circle_diameter, circle_diameter);
			gc.drawString(this.toString(), this.x, this.y + 20);
		}
		
		// Move the origin to the centre of the Star System.  The planets draw themselves relative to this.
		Graphics2D planetGc = ((Graphics2D)gc.create());
		planetGc.translate(this.x, this.y);
		bgStarSystem.listPlanets().forEach(p->Planet.from(p).draw(planetGc));
	}

	public static void drawAll(Graphics2D gc) {
		stream().forEach(ss->ss.draw(gc));
	}
}
