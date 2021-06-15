package com.rogers.rmcdouga.fitg.basegame;

import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public class BaseGameCounterPool implements CounterPool {

	private BaseGameCounterPool() {
	}

	@Override
	public Unit getUnit(Unit unitType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CounterPool returnCounter(Unit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	public static CounterPool create() {
		return new BaseGameCounterPool();
	}

}
