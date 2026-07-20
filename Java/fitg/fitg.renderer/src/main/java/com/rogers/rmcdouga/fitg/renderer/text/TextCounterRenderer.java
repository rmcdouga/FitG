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
	
	private record CharacterRow(String id, String name, String alias, String faction) {
		private static final String CHARACTER_TABLE_ROW_FORMAT = "| %s | %s | %s | %s |";

		private CharacterRow(Character character) {
			this(character.id(), 
				 character.name().replace('_', ' '), 
				 character.name(), 
				 character.allegience().toString()
				 );
		}
		
		private String toCharacterMarkdownRow() {
			return String.format(CHARACTER_TABLE_ROW_FORMAT, id, name, alias, faction);
		}
		
		private static String toCharacterMarkdownHeader() {
			return String.format(CHARACTER_TABLE_ROW_FORMAT, "id", "name", "alias", "faction");
		}
	}

	private record UnitRow(String id, String name, String aliases, String faction) {
		private static final String UNIT_TABLE_ROW_FORMAT = "| %s | %s | %s | %s |";

		private UnitRow(Unit unit) {
			this(unit.id(), 
				 unit.name().replace('_', ' '), 
				 unit.name() + ", " + unit.name().replaceFirst("^([^_]*_[^_]*)_", "$1-").replace('_', ' '), 
				 unit.faction().toString()
				 );
		}
		
		private String toUnitMarkdownRow() {
			return String.format(UNIT_TABLE_ROW_FORMAT, id, name, aliases, faction);
		}
		
		private static String toUnitMarkdownHeader() {
			return String.format(UNIT_TABLE_ROW_FORMAT, "id", "name", "aliases", "faction");
		}
	}
	
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
						Stream.of(CharacterRow.toCharacterMarkdownHeader()), 
						characters.map(this::renderCharacterMarkdownRow)
						)
					.collect(Collectors.joining("\n", "Valid characters in Freedom In The Galaxy:\n", ""));
	}
	
	private String renderCharacterMarkdownRow(Character character) {
		return new CharacterRow(character).toCharacterMarkdownRow();
	}

	
	private String renderUnitsMarkdown(Stream<Unit> units) {
		return Stream.concat(
				Stream.of(UnitRow.toUnitMarkdownHeader()), 
				units.map(this::renderUnitMarkdownRow)
				)
			.collect(Collectors.joining("\n", "Valid units in Freedom In The Galaxy:\n", ""));
		
	}
	
	private String renderUnitMarkdownRow(Unit unit) {
		return new UnitRow(unit).toUnitMarkdownRow();
	}
}
