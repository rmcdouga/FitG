package com.rogers.rmcdouga.fitg.renderer.text;

import java.util.List;
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

	// Represents a field in a table, with a name and a function to extract the value from an object of type T.
	private static record TableField<T>(String name, java.util.function.Function<T, String> valueExtractor) {
		private String toMarkdownHeader() {
			return name;
		}
		
		private String toMarkdownRow(T object) {
			return valueExtractor.apply(object);
		}
		
		private static <T> TableField<T> of(String name, java.util.function.Function<T, String> valueExtractor) {
			return new TableField<>(name, valueExtractor);
		}
		
		private static <T> String toMarkdownHeader(List<TableField<T>> fields) {
			return fields.stream()
						 .map(TableField::toMarkdownHeader)
						 .collect(Collectors.joining(" | ", "| ", " |"));
		}

		private static <T> String toMarkdownRow(List<TableField<T>> fields, T object) {
			return fields.stream()
						 .map(field -> field.toMarkdownRow(object))
						 .collect(Collectors.joining(" | ", "| ", " |"));
		}
	}

	private static class CharacterRow {
		// Prevent instantiation of this class, since it is just a utility class for rendering characters as markdown rows.
		private CharacterRow() {
			throw new UnsupportedOperationException("CharacterRow is a utility class and cannot be instantiated.");
		}

		private static final List<TableField<Character>> CHARACTER_TABLE_FIELDS = List.of(
				TableField.of("id", Character::id),
				TableField.of("name", c->c.name().replace('_', ' ')),
				TableField.of("alias", Character::name),
				TableField.of("faction", c->c.allegience().toString())
				);
		private static final String CHARACTER_TABLE_HEADER_STR = TableField.toMarkdownHeader(CHARACTER_TABLE_FIELDS);

		private static String toCharacterMarkdownRow(Character character) {
			return TableField.toMarkdownRow(CHARACTER_TABLE_FIELDS, character);
		}
		
		private static String toCharacterMarkdownHeader() {
			return CHARACTER_TABLE_HEADER_STR;
		}
	}

	private static class UnitRow {
		// Prevent instantiation of this class, since it is just a utility class for rendering units as markdown rows.
		private UnitRow() {
			throw new UnsupportedOperationException("UnitRow is a utility class and cannot be instantiated.");
		}

		private static final List<TableField<Unit>> UNIT_TABLE_FIELDS = List.of(
				TableField.of("id", Unit::id),
				TableField.of("name", u->u.name().replace('_', ' ')),
				TableField.of("aliases", u->u.name() + ", " + u.name().replaceFirst("^([^_]*_[^_]*)_", "$1-").replace('_', ' ')),
				TableField.of("faction", u->u.faction().toString())
				);
		private static final String UNIT_TABLE_HEADER_STR = TableField.toMarkdownHeader(UNIT_TABLE_FIELDS);
		
		private static String toUnitMarkdownRow(Unit unit) {
			return TableField.toMarkdownRow(UNIT_TABLE_FIELDS, unit);
		}
		
		private static String toUnitMarkdownHeader() {
			return UNIT_TABLE_HEADER_STR;
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
						characters.map(CharacterRow::toCharacterMarkdownRow)
						)
					.collect(Collectors.joining("\n", "Valid characters in Freedom In The Galaxy:\n", ""));
	}
	
	private String renderUnitsMarkdown(Stream<Unit> units) {
		return Stream.concat(
				Stream.of(UnitRow.toUnitMarkdownHeader()), 
				units.map(UnitRow::toUnitMarkdownRow)
				)
			.collect(Collectors.joining("\n", "Valid units in Freedom In The Galaxy:\n", ""));
	}
}
