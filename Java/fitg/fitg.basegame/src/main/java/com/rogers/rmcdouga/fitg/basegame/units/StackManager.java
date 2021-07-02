package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.rogers.rmcdouga.fitg.basegame.GameState;


public class StackManager {

	private final Map<Counter, Stack> counterStackTracker = new HashMap<>();
	
	public Stack of(Counter... counters) {
		return trackStack(new Stack(Arrays.asList(counters)));
	}

	public SpaceshipStack of(Spaceship spaceship, Counter... counters) {
		if (spaceship.overLimit(counters.length)) {
			throw new IllegalArgumentException("Can't add " + counters.length + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters.");
		}
		return new SpaceshipStack(spaceship, Arrays.asList(counters));
	}

	public Optional<Stack> stackContaining(Counter counter) {
		return Optional.ofNullable(counterStackTracker.get(counter));
	}

	private Stack trackStack(Stack stack) {
		stack.forEach(c->counterStackTracker.put(c, stack));
		return stack;
	}
	
	private Stack addToStack(Stack stack, Counter counter) {
		counterStackTracker.put(counter, stack);
		return stack;
	}
	
	private Stack removeFromStack(Stack stack, Counter counter) {
		counterStackTracker.remove(counter, stack);
		return stack;
	}
	
	private Stack addAllToStack(Stack stack, Collection<? extends Counter> c2) {
		c2.forEach(c->counterStackTracker.put(c, stack));
		return stack;
	}
	
	private Stack removeAllFromStack(Stack stack, Collection<Counter> counters) {
		counters.forEach(c->counterStackTracker.remove(c, stack));
		return stack;
	}
	
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

//		public void forEach(Consumer<? super Counter> action) {
//			stack.forEach(action);
//		}
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

//		public <T> T[] toArray(IntFunction<T[]> generator) {
//			return stack.toArray(generator);
//		}

		@Override
		public boolean add(Counter e) {
			addToStack(this, e);
			return stack.add(e);
		}

		@Override
		public boolean remove(java.lang.Object o) {
			removeFromStack(this, (Counter)o);
			return stack.remove(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return stack.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Counter> c) {
			addAllToStack(this, c);
			return stack.addAll(c);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean removeAll(Collection<?> c) {
			removeAllFromStack(this, (Collection<Counter>)c);
			return stack.removeAll(c);
		}

//		public boolean removeIf(Predicate<? super Counter> filter) {
//			return stack.removeIf(filter);
//		}
	//
		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException("retainAll() has not been imoplemented for Stack.");
		}

		@Override
		public void clear() {
			removeAllFromStack(this, stack);
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

//		public Spliterator<Counter> spliterator() {
//			return stack.spliterator();
//		}
	//
//		public Stream<Counter> stream() {
//			return stack.stream();
//		}
	//
//		public Stream<Counter> parallelStream() {
//			return stack.parallelStream();
//		}
		
	}

	public class SpaceshipStack extends Stack {
		private final Spaceship spaceship;

		private SpaceshipStack(Spaceship spaceship) {
			super();
			this.spaceship = spaceship;
		}

		private SpaceshipStack(Spaceship spaceship, Collection<Counter> stack) {
			super(stack);
			this.spaceship = spaceship;
		}
		
		@Override
		public boolean add(Counter e) {
			if (spaceship.overLimit(this.size() + 1)) {
				throw new IllegalArgumentException("Can't add additional characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and is at capacity.");
			}
			return super.add(e);
		}

		@Override
		public boolean addAll(Collection<? extends Counter> c) {
			if (spaceship.overLimit(this.size() + c.size())) {
				throw new IllegalArgumentException("Can't add " + c.size() + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and it already has " + this.size() + " passengers.");
			}
			return super.addAll(c);
		}
		
		public boolean overLimit(int numChars) {
			return spaceship.overLimit(numChars);
		}
	}
}
