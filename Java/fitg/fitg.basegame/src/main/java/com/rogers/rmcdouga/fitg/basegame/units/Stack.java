package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.rogers.rmcdouga.fitg.basegame.GameState;

/**
 * Repreents a stack of counters (and is treated as a counter itself - i.e. it has a location).  
 * All counters in a stack share the same location.
 * 
 * (Note: This is not a "stack" in the computer science sense (i.e. you can do more than just add and remove from the top.)
 */
public class Stack implements Collection<Counter>, Counter, GameState {

	private final Collection<Counter> stack;

	public Stack() {
		this.stack = new HashSet<>();
	}

	protected Stack(Collection<Counter> stack) {
		this.stack = new HashSet<>(stack);
	}

	public static Stack of(Counter... counters) {
		return new Stack(Arrays.asList(counters));
	}
	
//	public void forEach(Consumer<? super Counter> action) {
//		stack.forEach(action);
//	}
//
	@Override
	public int size() {
		return stack.size();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public boolean contains(java.lang.Object o) {
		return stack.contains(o);
	}

	@Override
	public Iterator<Counter> iterator() {
		return stack.iterator();
	}

	@Override
	public java.lang.Object[] toArray() {
		return stack.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return stack.toArray(a);
	}

//	public <T> T[] toArray(IntFunction<T[]> generator) {
//		return stack.toArray(generator);
//	}

	@Override
	public boolean add(Counter e) {
		return stack.add(e);
	}

	@Override
	public boolean remove(java.lang.Object o) {
		return stack.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return stack.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Counter> c) {
		return stack.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return stack.removeAll(c);
	}

//	public boolean removeIf(Predicate<? super Counter> filter) {
//		return stack.removeIf(filter);
//	}
//
	@Override
	public boolean retainAll(Collection<?> c) {
		return stack.retainAll(c);
	}

	@Override
	public void clear() {
		stack.clear();
	}

	@Override
	public Map<String, java.lang.Object> getState() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	@Override
	public void setState(Map<String, java.lang.Object> state) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet.");
	}

//	public Spliterator<Counter> spliterator() {
//		return stack.spliterator();
//	}
//
//	public Stream<Counter> stream() {
//		return stack.stream();
//	}
//
//	public Stream<Counter> parallelStream() {
//		return stack.parallelStream();
//	}
	
}
