package com.rogers.rmcdouga.fitg.basegame.box;

import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.PlayerState.Faction;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Possession;
import com.rogers.rmcdouga.fitg.basegame.units.RebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

public class BaseGameBox implements GameBox {
	private CounterPool counterPool;
	private CharacterPool characterPool;
	private PossessionPool possessionPool;
	
	private BaseGameBox() {
		this(BaseGameCounterPool.create(), new BaseGameCharacterPool(), new BaseGamePossessionPool());
		
	}
	
	BaseGameBox(CounterPool counterPool, CharacterPool characterPool, PossessionPool possessionPool) {
		super();
		this.counterPool = counterPool;
		this.characterPool = characterPool;
		this.possessionPool = possessionPool;
	}

	public static GameBox create() {
		return new BaseGameBox();
	}

	@Override
	public Character getCharacter(Character character) {
		return characterPool.getCharacter(character);
	}

	@Override
	public CharacterPool kill(Character character) {
		characterPool.kill(character);
		return this;
	}

	@Override
	public Optional<Character> cloneCharacter(Faction faction) {
		return characterPool.cloneCharacter(faction);
	}

	@Override
	public Optional<Character> randomCharacter(Faction faction) {
		return characterPool.randomCharacter(faction);
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
	public Optional<Spaceship> getSpaceship(ImperialSpaceship possesion) {
		return counterPool.getSpaceship(possesion);
	}

	@Override
	public CounterPool returnSpaceship(ImperialSpaceship spaceship) {
		return counterPool.returnSpaceship(spaceship);
	}

	@Override
	public Possession getPossession(Possession possesion) {
		return possessionPool.getPossession(possesion);
	}

	@Override
	public Optional<Spaceship> getSpaceship(RebelSpaceship possesion) {
		return possessionPool.getSpaceship(possesion);
	}

	@Override
	public PossessionPool lost(Possession possesion) {
		possessionPool.lost(possesion);
		return this;
	}

	@Override
	public PossessionPool captured(Possession possesion) {
		possessionPool.captured(possesion);
		return this;
	}

	@Override
	public PossessionPool destroyed(Possession possesion) {
		possessionPool.destroyed(possesion);
		return this;
	}

	@Override
	public Optional<Possession> randomPossession() {
		return possessionPool.randomPossession();
	}
}
