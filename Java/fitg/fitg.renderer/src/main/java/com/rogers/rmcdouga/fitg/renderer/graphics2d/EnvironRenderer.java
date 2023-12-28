package com.rogers.rmcdouga.fitg.renderer.graphics2d;

import java.awt.Graphics2D;
import java.util.Collection;

import com.rogers.rmcdouga.fitg.basegame.CounterLocations;
import com.rogers.rmcdouga.fitg.basegame.CounterLocator;
import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;

public class EnvironRenderer {

	private final Graphics2D gc;
	private final CounterLocator counterLocator;
	private final CounterRenderer counterRenderer;

	private EnvironRenderer(Graphics2D gc, CounterLocator counterLocator, CounterRenderer counterRenderer) {
		this.gc = gc;
		this.counterLocator = counterLocator;
		this.counterRenderer = counterRenderer;
	}

	public void draw(Graphics2D gc, Planet p) {
		p.drawPerEnviron(gc, (g2d, e)->drawEnviron(g2d, e));
	}
	
	private void drawEnviron(Graphics2D gc, Environ environ) {
		Collection<Counter> countersAt = this.counterLocator.countersAt(environ);
		System.out.println("Rendering " + countersAt.size() + " counters at " + environ.getType().getName());
		if (!countersAt.isEmpty()) {
			counterRenderer.renderCounters(gc, countersAt);
		}
	}
	
	public static EnvironRenderer create(Graphics2D gc, CounterLocator counterLocator, CounterRenderer counterRenderer) {
		return new EnvironRenderer(gc, counterLocator, counterRenderer);
	}
}
