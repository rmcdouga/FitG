package com.rogers.rmcdouga.fitg.basegame;

public enum RaceType {
	foo;
	
	private final String name;
	private final boolean isStarFaring;
	private final Planet homePlanet;
	private final String description;
	
	private RaceType(String name, boolean isStarFaring, Planet homePlanet, String description) {
		this.name = name;
		this.isStarFaring = isStarFaring;
		this.homePlanet = homePlanet;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the isStarFaring
	 */
	public boolean isStarFaring() {
		return isStarFaring;
	}

	/**
	 * @return the homePlanet
	 */
	public Planet getHomePlanet() {
		return homePlanet;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
