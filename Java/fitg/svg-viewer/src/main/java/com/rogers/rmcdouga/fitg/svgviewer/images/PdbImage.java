package com.rogers.rmcdouga.fitg.svgviewer.images;

import java.nio.file.Path;

import com.rogers.rmcdouga.fitg.basegame.map.Pdb;

enum PdbImage {
	PDB_UP(Path.of("Marker_PDB-Up.png")),
	PDB_DOWN(Path.of("Marker_PDB-Down.png")),
	;
	
	private final Path imageLocation;

	private PdbImage(Path imagePath) {
		this.imageLocation = imagePath;
	}

	Path imageLocation() {
		return imageLocation;
	}
	
	static PdbImage of(Pdb pdb) {
		if (pdb.isUp()) {
			return PDB_UP;
		} else if (pdb.isDown()) {
			return PDB_DOWN;
		} else {
			throw new IllegalStateException("Unexpected Pdb Value. '" + pdb + "' is neither up nor down.");
		}
	}
}

