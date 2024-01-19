package com.rogers.rmcdouga.fitg.basegame.map;

import java.util.function.Consumer;

import com.rogers.rmcdouga.fitg.basegame.GameMap;

/**
 * MapWalker class is used to perform operations on all the entities in a Map.
 * 
 * The walk method is called to traverse the map.  It calls the appropriate method in the 
 * MapConsumer for each entity.
 * 
 *  It invokes the consumers in a fixed order.  It calls the starSystemConsumer first, then 
 *  it calls the planetConsumer, then is calls the environConsumer for all environs.  It then
 *  calls the next planetConsumer and processes its environs.  Once all the planets have been 
 *  processed, it moves to the next starSystem.
 */
public class MapWalker {
	/**
	 * 
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

	private final GameMap map;
	
	public MapWalker(GameMap map) {
		this.map = map;
	}

	
	/**
	 * Walks the map tree from the top (StarSystems) through the Planets and Environs.
	 * 
	 * @param mapConsumer Consumer whose methods are called for each Map entity.
	 */
	public void walk(MapConsumer mapConsumer) {
		new Walker(mapConsumer).walk(map);
	}
	
	private final static class Walker{
		private final MapConsumer mapConsumer;
		
		private Walker(MapConsumer mapConsumer) {
			this.mapConsumer = mapConsumer;
		}

		private void walk(GameMap map) {
			map.getStarSystems().forEach(this::processStarSystem);
		}
		
		private void processStarSystem(StarSystem s) {
			mapConsumer.starSystemConsumer(s);
			s.listPlanets().forEach(this::processPlanet);
		}
		
		private void processPlanet(Planet p) {
			mapConsumer.planetConsumer(p);
			p.listEnvirons().forEach(this::processEnviron);
			
		}
		
		private void processEnviron(Environ e) {
			mapConsumer.environConsumer(e);
		}
	}
}
