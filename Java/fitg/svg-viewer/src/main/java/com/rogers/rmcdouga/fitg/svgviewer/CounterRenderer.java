package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.List;

import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.svgviewer.images.ImageStore;

public class CounterRenderer {
	private final ImageStore imageStore;

	CounterRenderer(ImageStore imageStore) {
		this.imageStore = imageStore;
	}

	public void renderCounters(Graphics2D gc, Collection<Counter> counters) {
		List<CounterOrStack> list = counters.stream().map(c->CounterOrStack.from(c, imageStore)).toList();
		// TODO: Figure out the layout (which involves getting all the image sizes)
		// Each of the counters should be placed side by side within the environ.
		// Stacks take up more room.
		
	}
}
