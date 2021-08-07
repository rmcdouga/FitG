package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;

public class EnvironRenderer {

	private final Graphics2D gc;

	private EnvironRenderer(Graphics2D gc) {
		super();
		this.gc = gc;
	}

	public void draw(StarSystem ss) {
		ss.drawPerPlanet(gc, this::draw);
	}

	private void draw(Graphics2D gc, Planet p) {
	}
}
