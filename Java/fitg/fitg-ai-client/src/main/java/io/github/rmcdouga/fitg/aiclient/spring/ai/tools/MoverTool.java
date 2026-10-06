package io.github.rmcdouga.fitg.aiclient.spring.ai.tools;

import org.springframework.ai.tool.annotation.Tool;

import com.rogers.rmcdouga.fitg.basegame.command.api.external.Mover;

public class MoverTool implements Mover, SpringAiTool {
	private final Mover mover;

	public MoverTool(Mover mover) {
		this.mover = mover;
	}

	@Tool(description = "Move a non-unique unit from one location to another.  Use this for when the user wants to move a military unit.")
	@Override
	public Mover moveUnitCounter(String unitType, String currentStarOrPlanetId, String currentLocation,
			String destinationStarOrPlanetId, String destinationLocation) {
		mover.moveUnitCounter(unitType, currentStarOrPlanetId, currentLocation, destinationStarOrPlanetId,
				destinationLocation);
		return this;
	}

	@Tool(description = "Move a unique unit from one location to another.  Use this when the user wants to move a character or ship.")
	@Override
	public Mover moveCounter(String counterId, String destinationStarOrPlanetId, String destinationLocation) {
		mover.moveCounter(counterId, destinationStarOrPlanetId, destinationLocation);
		return this;
	}

	@Tool(description = "Move a stack containing a non-unique unit from one location to another.  Use this when the user wants to move a stack containing military units.")
	@Override
	public Mover moveStackContainingUnitCounter(String unitType, String currentStarOrPlanetId, String currentLocation,
			String destinationStarOrPlanetId, String destinationLocation) {
		mover.moveStackContainingUnitCounter(unitType, currentStarOrPlanetId, currentLocation,
				destinationStarOrPlanetId, destinationLocation);
		return this;
	}

	@Tool(description = "Move a stack containing a unique unit from one location to another.  Use this when the user wants to move a stack containing characters or ships.")
	@Override
	public Mover moveStackContainingCounter(String counterId, String destinationStarOrPlanetId,
			String destinationLocation) {
		mover.moveStackContainingCounter(counterId, destinationStarOrPlanetId, destinationLocation);
		return this;
	}
}
