
## Implement Map Text Renderer
This will be used in the system prompt and sent as context.  The map description will only need to be generated once since it is always the same.
* Start with just a single star system (Egrix - for Flight to Egrix)
* Expand later as we implement more of the game (province, then whole map)
* Write unit tests that query the models for information on the map and then use an evaluator to determine if it is true.

## Implement State Renderer
This will be used in the prompt as well to tell the model where things are.
* Indicates where all units are, PDB state and currently loyalty.
* Write unit tests that query the models for information on the state and then use an evaluator to determine if it is true.

## Implement Movement
* Implement Move command -  *Done*
* Implement Detection routine
	* Per 9.3 Detection Routine
* 

## Implement Missions
* Designate an environ
	* add characters from the environ into the mission group
	* select the mission card for this group
	* repeat the last two steps as needed,
		* a character can only be used in one mission group
		* a mission card can only be used once per environ
* Resolve the mission groups, in order decided by player
	* Draw Action cards, one at a time, resolve Action Events
		* They may draw up to the environ size
	* Draw Bonus Actions cards, one at a time without resolving Actions Events
		* They may draw up to the number of bonus actions

