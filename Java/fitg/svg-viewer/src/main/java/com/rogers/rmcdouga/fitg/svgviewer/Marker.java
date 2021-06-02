package com.rogers.rmcdouga.fitg.svgviewer;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Marker {
	private static final Path MARKER_PATH = Paths.get("markers");
	protected Path imagePath(Path filename) {
		return MARKER_PATH.resolve(filename);
	}
}
