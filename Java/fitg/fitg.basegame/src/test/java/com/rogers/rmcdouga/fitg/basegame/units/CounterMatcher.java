package com.rogers.rmcdouga.fitg.basegame.units;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class CounterMatcher extends FeatureMatcher<Counter, String> {

	private CounterMatcher(Matcher<? super String> subMatcher) {
		super(subMatcher, "a Counter with id", "id");
	}

	@Override
	protected String featureValueOf(Counter actual) {
		return actual.id();
	}
	
	public static Matcher<Counter> hasId(String id) {
		return new CounterMatcher(equalTo(id));
	}
	
	public static Matcher<Counter> sameAs(Counter expected) {
		return new CounterMatcher(equalTo(expected.id()));
	}
}
