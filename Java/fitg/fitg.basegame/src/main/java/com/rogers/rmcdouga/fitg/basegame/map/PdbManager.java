package com.rogers.rmcdouga.fitg.basegame.map;

public interface PdbManager {
	public Pdb getPdb(Planet planet);
	public PdbManager increasePdb(Planet planet);
	public PdbManager decreasePdb(Planet planet);
	public PdbManager upPdb(Planet planet);
	public PdbManager downPdb(Planet planet);
}
