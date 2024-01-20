package com.rogers.rmcdouga.fitg.basegame.map;

import com.rogers.rmcdouga.fitg.basegame.GameMap;
import com.rogers.rmcdouga.fitg.basegame.GameMap.MapConsumer;

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
