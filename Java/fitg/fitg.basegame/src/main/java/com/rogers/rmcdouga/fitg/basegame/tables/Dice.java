package com.rogers.rmcdouga.fitg.basegame.tables;

import java.util.stream.IntStream;

public enum Dice {
	D6(6);
	
	private final int numSides;

	private Dice(int numSides) {
		this.numSides = numSides;
	}
	
	/**
	 * Create infinite stream of die rolls
	 * 
	 * @return Stream of die rolls
	 */
	public IntStream stream() {
		return Dice.stream(this.numSides);
	}
	
	/**
	 * Roll many dice and sum the result
	 * 
	 * @param numDice
	 * @return sum of rolling numDice times
	 */
	public int roll(int numDice) {
		return Dice.roll(this.numSides, numDice);
	}

	/**
	 * Roll one die
	 * 
	 * @return result of 1 die roll
	 */
	public int roll() {
		return this.roll(1);
	}

	/**
	 * Create infinite stream of die rolls
	 * 
	 * @param numSides size of dice to use
	 * @return Stream of die rolls
	 */
	public static IntStream stream(final int numSides) {
		return IntStream.generate(()->(int)(Math.random()*numSides+1));
	}
	
	/**
	 * Roll many dice and sum the result
	 * 
	 * @param numSides size of dice to use
	 * @param numDice number of dice to roll
	 * @return sum of rolling numDice times
	 */
	public static int roll(int numSides, int numDice) {
		return Dice.stream(numSides).limit(numDice).sum(); 
	}
}
