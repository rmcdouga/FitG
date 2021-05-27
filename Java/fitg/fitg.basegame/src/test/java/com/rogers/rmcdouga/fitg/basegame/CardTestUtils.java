package com.rogers.rmcdouga.fitg.basegame;

import java.util.Set;
import java.util.stream.Collectors;

public class CardTestUtils {

	/**
	 * Reports any differences between the expected set and the actual set.
	 * 
	 * @param expected
	 * @param actual
	 * @return
	 */
	public static String reportDifferences(Set<Integer> expected, Set<Integer> actual) {
		Set<Integer> originalExpected = Set.copyOf(expected);
		Set<Integer> originalActual = Set.copyOf(actual);
		expected.removeAll(originalActual);
		actual.removeAll(originalExpected);
		String missing = expected.size() != 0 ? "Missing card numbers (" + expected.stream().map(i->Integer.toString(i)).collect(Collectors.joining(",")) + ")." : "";
		String extra = actual.size() != 0 ? "Extra card numbers (" + actual.stream().map(i->Integer.toString(i)).collect(Collectors.joining(",")) + ")." : "";
		return (missing + " " + extra).trim();
	}
	
}
