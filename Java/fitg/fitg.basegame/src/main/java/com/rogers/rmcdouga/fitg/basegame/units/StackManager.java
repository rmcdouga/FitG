package com.rogers.rmcdouga.fitg.basegame.units;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.rogers.rmcdouga.fitg.basegame.GameState;


public class StackManager {

	private final Map<Counter, Stack> counterStackTracker = new HashMap<>();
	
	public Stack of(Counter... counters) {
		return of(Arrays.asList(counters));
	}

	public SpaceshipStack of(Spaceship spaceship, Counter... counters) {
		return of(spaceship, Arrays.asList(counters));
	}

	public Stack of(Collection<Counter> counterCol) {
		return trackStack(new Stack(ensureNoStacks(counterCol)));
	}

	public SpaceshipStack of(Spaceship spaceship, Collection<Counter> counterCol) {
		if (spaceship.overLimit(counterCol.size())) {
			throw new IllegalArgumentException("Can't add " + counterCol.size() + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters.");
		}
		return new SpaceshipStack(spaceship, counterCol);
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
	
	public static Collection<Counter> ensureNoStacks(Collection<Counter> counterCol) {
		for(Counter counter : counterCol) {
			if (counter instanceof Stack) {
				throw new IllegalArgumentException("Stacks are not allowed in this context (" + counter + ").");
			}
		}
		return counterCol;
	}

	/**
	 * Transfer a stack (potentially from another Stack Manager) into this stack manager.
	 * If the stack is already owned by this stack manager, then it is just returned as is.
	 * 
	 * @param stack stack to be transferred to this stack manager
	 * @return stack managed by this stack manager,
	 */
	public Stack transfer(Stack stack) {
		if (stack.getStackMgr().equals(this)) {
			return stack;
		}
		return stack instanceof SpaceshipStack ss ? of(ss.spaceship(), (Collection<Counter>) ss): of((Collection<Counter>) stack);
	}

	/**
	 * Find an equivalent stack from this Stack Manager.
	 * 
	 * @param stack
	 * @return
	 */
	public Optional<Stack> find(Stack stack) {
		if (stack.getStackMgr().equals(this)) {
			return Optional.of(stack);
		}
		// find the stack that the first counter is in and compare it to the stack provided.
		// since a counter can only be in one stack, if they don't match then the stacks don't match
		return stack.stream()
					.findFirst()						// grab the first counter
					.flatMap(this::stackContaining)		// find the stack within this stack manager that contains it
					.filter(s->s instanceof SpaceshipStack ss ? (stack instanceof SpaceshipStack sstack? s.isEquivalent(sstack) : false) : s.isEquivalent(stack));	// see if that stack is equivilent to the stack passed in
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

		public Stack of(Counter... counters) {
			return getStackMgr().of(counters);
		}

		public SpaceshipStack of(Spaceship spaceship, Counter... counters) {
			return getStackMgr().of(spaceship, counters);
		}

		public Stack of(Collection<Counter> counterCol) {
			return getStackMgr().of(counterCol);
		}

		public SpaceshipStack of(Spaceship spaceship, Collection<Counter> counterCol) {
			return getStackMgr().of(spaceship, counterCol);
		}

		public Stack replace(Counter... counters) {
			this.clear();
			return getStackMgr().of(counters);
		}

		public SpaceshipStack replace(Spaceship spaceship, Counter... counters) {
			this.clear();
			return getStackMgr().of(spaceship, counters);
		}

		public Stack replace(Collection<Counter> counterCol) {
			this.clear();
			return getStackMgr().of(counterCol);
		}

		public SpaceshipStack replace(Spaceship spaceship, Collection<Counter> counterCol) {
			this.clear();
			return getStackMgr().of(spaceship, counterCol);
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
		private StackManager getStackMgr() {
			return StackManager.this;
		}
		
		public boolean isEquivalent(Stack stack) {
			return Set.copyOf(this).equals(Set.copyOf(stack));
		}
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
		
		public Spaceship spaceship() {
			return spaceship;
		}

		@Override
		public boolean add(Counter e) {
			if (spaceship.overLimit(this.size() + 1)) {
				throw new IllegalArgumentException("Can't add additional characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and is at capacity.");
			}
			return super.add(requireCharacter(e));
		}

		@Override
		public boolean addAll(Collection<? extends Counter> c) {
			if (spaceship.overLimit(this.size() + c.size())) {
				throw new IllegalArgumentException("Can't add " + c.size() + " characters to " + spaceship + ".  It has a limit of " + spaceship.maxPassengers() + " characters and it already has " + this.size() + " passengers.");
			}
			c.forEach(SpaceshipStack::requireCharacter);
			return super.addAll(c);
		}

		private static Counter requireCharacter(Counter counter) {
			if (counter instanceof Character) {
				return counter;
			} else {
				throw new IllegalArgumentException("Can only add characters to a spaceship. Not allowed (" + counter.getClass().getName() + ").");
			}
		}
		
		public boolean overLimit(int numChars) {
			return spaceship.overLimit(numChars);
		}

		public boolean isEquivalent(SpaceshipStack stack) {
			return this.spaceship == stack.spaceship && Set.copyOf(this).equals(Set.copyOf(stack));
		}
	}
}
