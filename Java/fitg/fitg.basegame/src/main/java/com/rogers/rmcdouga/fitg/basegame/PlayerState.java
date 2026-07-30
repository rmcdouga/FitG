package com.rogers.rmcdouga.fitg.basegame;

public class PlayerState implements Player {
	public enum Faction { 
		REBEL, IMPERIAL, NEUTRAL, ADMIN;
		
		public static Faction fromString(String str) {
			return switch (str.toLowerCase()) {
				case "rebel" -> REBEL;
				case "imperial" -> IMPERIAL;
				case "neutral" -> NEUTRAL;
				case "admin" -> ADMIN;
				case null -> throw new IllegalArgumentException("Faction string cannot be null");
				default -> throw new IllegalArgumentException("Unknown faction: " + str);
			};
		}
	};
	
	public final String name;
	public final Faction faction;
	
	protected PlayerState(String name, Faction faction) {
		this.name = name;
		this.faction = faction;
	}
	
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Player#name()
	 */
	@Override
	public String name() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.rogers.rmcdouga.fitg.basegame.Player#faction()
	 */
	@Override
	public Faction faction() {
		return faction;
	}
}
