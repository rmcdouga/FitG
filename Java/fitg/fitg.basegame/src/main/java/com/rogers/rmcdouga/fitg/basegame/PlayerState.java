package com.rogers.rmcdouga.fitg.basegame;

public class PlayerState implements Player {
	enum Faction { REBEL, IMPERIAL, NEUTRAL, ADMIN };
	
	public final String name;
	public final Faction faction;
	
	protected PlayerState(String name, Faction faction) {
		super();
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
