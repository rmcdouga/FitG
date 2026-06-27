package com.rogers.rmcdouga.fitg.renderer.text;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.Character;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Unit;

/**
 * This class is never called in the application. It is used to generate a markdown table of all 
 * the characters in the game for inclusion in the LLM prompt.
 */
public class TextCounterRenderer {
	private static final String CHARACTER_TABLE_ROW_FORMAT = "| %s | %s | %s | %s |";
	private static final String UNIT_TABLE_ROW_FORMAT = "| %s | %s | %s | %s |";
	
	public String renderMarkdown() {
		return renderCharactersMarkdown(BaseGameCharacter.stream().map(Character.class::cast))
				+ "\n\n"
				+ renderUnitsMarkdown(Stream.concat(
						RebelMilitaryUnit.stream().map(Unit.class::cast),
						ImperialMilitaryUnit.stream().map(Unit.class::cast)
						));
	}

	private String renderCharactersMarkdown(Stream<Character> characters) {
		return Stream.concat(
						Stream.of(CHARACTER_TABLE_ROW_FORMAT.formatted("id", "name", "alias", "faction")), 
						characters.map(this::renderCharacterMarkdownRow)
						)
					.collect(Collectors.joining("\n", "Valid characters in Freedom In The Galaxy:\n", ""));
	}
	
	private String renderCharacterMarkdownRow(Character character) {
		return String.format(CHARACTER_TABLE_ROW_FORMAT, character.id(), character.name().replace('_', ' '), character.name(), character.allegience().toString());
	}

	
	private String renderUnitsMarkdown(Stream<Unit> units) {
		return Stream.concat(
				Stream.of(UNIT_TABLE_ROW_FORMAT.formatted("id", "name", "alias", "faction")), 
				units.map(this::renderUnitMarkdownRow)
				)
			.collect(Collectors.joining("\n", "Valid units in Freedom In The Galaxy:\n", ""));
		
	}
	
	private String renderUnitMarkdownRow(Unit unit) {
		return String.format(UNIT_TABLE_ROW_FORMAT, unit.id(), unit.name().replace('_', ' '), unit.name(), unit.faction().toString());
	}
}
