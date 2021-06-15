package com.rogers.rmcdouga.fitg.basegame.map;

public record Pdb(State state, Level level) {

	public enum State {
		UP, DOWN;
	}
	
	public enum Level {
		ZERO, ONE, TWO;
		
		public Level increase() {
			return switch(this) {
			case ONE, TWO -> Level.TWO;
			case ZERO -> Level.ONE;
			default -> throw new IllegalStateException("Invalid Level '" + this + "'.");
			};
		}
		
		public Level decrease() {
			return switch(this) {
			case ZERO, ONE -> Level.ZERO;
			case TWO -> Level.ONE;
			default -> throw new IllegalStateException("Invalid Level '" + this + "'.");
			};
		}
	}
	
	public Pdb increaseLevel( ) {
		return new Pdb(this.state, this.level.increase());
	}

	public Pdb decreaseLevel( ) {
		return new Pdb(this.state, this.level.decrease());
	}
}
