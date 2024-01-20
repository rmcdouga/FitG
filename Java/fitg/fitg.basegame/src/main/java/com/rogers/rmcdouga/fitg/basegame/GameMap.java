package com.rogers.rmcdouga.fitg.basegame;

import java.util.Collection;
import java.util.function.Consumer;

import com.rogers.rmcdouga.fitg.basegame.map.Environ;
import com.rogers.rmcdouga.fitg.basegame.map.Planet;
import com.rogers.rmcdouga.fitg.basegame.map.StarSystem;

public interface GameMap {

	/**
	 * Gets all the StarSystems within the GameMap
	 * 
	 * @return a Collection of StarSystem objects
	 */
	public Collection<StarSystem> getStarSystems();

	/**
	 * Walks the map tree from the top (StarSystems) through the Planets and Environs.
	 * 
	 * The walk method is called to traverse the map.  It calls the appropriate method in the 
	 * MapConsumer for each entity.
	 * 
	 *  It invokes the consumers in a fixed order.  It calls the starSystemConsumer first, then 
	 *  it calls the planetConsumer, then is calls the environConsumer for all environs.  It then
	 *  calls the next planetConsumer and processes its environs.  Once all the planets have been 
	 *  processed, it moves to the next starSystem.
	 *  
	 * @param mapConsumer Consumer whose methods are called for each Map entity.
	 */
	public void walk(MapConsumer mapConsumer);
	

	/**
	 * Interface implemented by classes wishing to walk the Map's contents
	 */
	public interface MapConsumer {
		default void starSystemConsumer(StarSystem starSystem) {};
		default void planetConsumer(Planet planet) {};
		default void environConsumer(Environ environ) {};

		/**
		 * The consumer which will be called for each entity in the map.
		 * 
		 * @param starSystemConsumer
		 * 		consumer called for each StarSystm
		 * @param planetConsumer
		 * 		consumer called for each Planet
		 * @param environConsumer
		 * 		consumer called for each Environ
		 * @return the constructed MapConsumer
		 */
		public static MapConsumer consumer(Consumer<StarSystem> starSystemConsumer, Consumer<Planet> planetConsumer, Consumer<Environ> environConsumer) {
			return new MapConsumer() {
				
				@Override
				public void starSystemConsumer(StarSystem starSystem) {
					starSystemConsumer.accept(starSystem);
				}
				
				@Override
				public void planetConsumer(Planet planet) {
					planetConsumer.accept(planet);
				}
				
				@Override
				public void environConsumer(Environ environ) {
					environConsumer.accept(environ);
				}
			};
		}

		/**
		 * Create a MapConsumer that only processes Environs
		 * 
		 * @param environConsumer
		 * @return
		 */
		public static MapConsumer environConsumer(Consumer<Environ> environConsumer) {
			return consumer(s->{}, p->{}, environConsumer);	
		}
		/**
		 * Create a MapConsumer that only processes Planets
		 * 
		 * @param planetConsumer
		 * @return
		 */
		public static MapConsumer planetConsumer(Consumer<Planet> planetConsumer) {
			return consumer(s->{}, planetConsumer, e->{});	
		}
		/**
		 * Create a MapConsumer that only processes StarSystems
		 * 
		 * @param starsystemConsumer
		 * @return
		 */
		public static MapConsumer starSystemConsumer(Consumer<StarSystem> starsystemConsumer) {
			return consumer(starsystemConsumer, p->{}, e->{});	
		}
	}
}
