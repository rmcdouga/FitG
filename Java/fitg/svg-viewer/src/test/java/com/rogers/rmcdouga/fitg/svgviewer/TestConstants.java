package com.rogers.rmcdouga.fitg.svgviewer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestConstants {
	public static final Path RESOURCES_DIR = Paths.get("src", "test", "resources");
	public static final Path ACTUAL_RESULTS_DIR = RESOURCES_DIR.resolve("actualResults");
	public static final Path SAMPLE_FILES_DIR = RESOURCES_DIR.resolve("sampleFiles");
	public static final Path EXPECTED_RESULTS_DIR = RESOURCES_DIR.resolve("expectedResults");

	public static <T> Set<T> compare(Set<? extends T> set1, Set<? extends T> set2) {
		return set2.stream()
			    .filter(val -> !set1.contains(val))
			    .collect(Collectors.toUnmodifiableSet());
	}
	
	private static <T> Optional<String> getCompareMessage(Set<? extends T> reference, String targetName, Set<? extends T> target) {
		var missingFromTarget = compare(reference, target);
		return !missingFromTarget.isEmpty() ? Optional.of("Missing (" + missingFromTarget.toString() + ") from " + targetName + ".") : Optional.empty();
	}
	
	public static <T> String getCompareMessage(String name1, Set<? extends T> set1, String name2, Set<? extends T> set2) {
		return Stream.concat(getCompareMessage(set1, name2, set2).stream(), getCompareMessage(set2, name1, set1).stream()).collect(Collectors.joining(" "));
	}
}
