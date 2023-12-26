package com.rogers.rmcdouga.fitg.renderer.graphics2d;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Marker {
	private static final Path MARKER_PATH = Paths.get("markers");
	protected static Path imagePath(Path filename) {
		return MARKER_PATH.resolve(filename);
	}
}
