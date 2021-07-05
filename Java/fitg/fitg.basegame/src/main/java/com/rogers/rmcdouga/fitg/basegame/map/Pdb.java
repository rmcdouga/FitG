package com.rogers.rmcdouga.fitg.basegame.map;

public enum Pdb {
	DOWN_0(State.DOWN, Level.ZERO),
	DOWN_1(State.DOWN, Level.ONE),
	DOWN_2(State.DOWN, Level.TWO),
	UP_0(State.UP, Level.ZERO),
	UP_1(State.UP, Level.ONE),
	UP_2(State.UP, Level.TWO),
	;
	
	private final State state;
	private final Level level;
	
	private Pdb(State state, Level level) {
		this.state = state;
		this.level = level;
	}

	public enum State {
		UP, DOWN;
	}
	
	public enum Level {
		ZERO, ONE, TWO;
		
		public Level increase() {
			return switch(this) {
			case ONE, TWO -> Level.TWO;
			case ZERO -> Level.ONE;
			};
		}
		
		public Level decrease() {
			return switch(this) {
			case ZERO, ONE -> Level.ZERO;
			case TWO -> Level.ONE;
			};
		}
	}
	
	public Pdb increaseLevel() {
		return of(this.state, this.level.increase());
	}

	public Pdb decreaseLevel() {
		return of(this.state, this.level.decrease());
	}
	
	public Pdb up() {
		return of(State.UP, this.level);
	}

	public Pdb down() {
		return of(State.DOWN, this.level);
	}
	
	public boolean isUp() {
		return state == State.UP;
	}

	public boolean isDown() {
		return state == State.DOWN;
	}

	public Level level() {
		return level;
	}
	
	public static Pdb of(State state, Level level) {
		return switch(state) {
		case UP -> switch(level) {
				   case ZERO -> UP_0;
				   case ONE -> UP_1;
				   case TWO -> UP_2;
				   };
		case DOWN -> switch(level) {
		   			 case ZERO -> DOWN_0;
		   			 case ONE -> DOWN_1;
		   			 case TWO -> DOWN_2;
				   };
		};
		
	}
}
