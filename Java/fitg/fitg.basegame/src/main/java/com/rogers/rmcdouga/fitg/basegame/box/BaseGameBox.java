package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.Possession;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public class BaseGameBox implements GameBox {
	private CounterPool counterPool = BaseGameCounterPool.create();
	
	public static GameBox create() {
		return new BaseGameBox();
	}

	@Override
	public Character getCharacter(Character character) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CharacterPool kill(Character character) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Character> cloneCharacter(Faction faction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Character> randomCharacter(Faction faction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Unit> getCounter(Unit unitType) {
		return counterPool.getCounter(unitType);
	}

	@Override
	public CounterPool returnCounter(Unit unit) {
		counterPool.returnCounter(unit);
		return this;
	}

	@Override
	public Possession getPossession(Possession possesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PossessionPool lost(Possession possesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PossessionPool captured(Possession possesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PossessionPool destroyed(Possession possesion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Possession random() {
		// TODO Auto-generated method stub
		return null;
	}

}
