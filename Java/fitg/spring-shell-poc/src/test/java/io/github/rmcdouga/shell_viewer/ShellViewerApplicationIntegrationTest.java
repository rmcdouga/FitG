package io.github.rmcdouga.shell_viewer;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat; 
//import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
				useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
				classes = { ShellViewerApplication.class },
				properties = { "logging.console.enabled=true" },
				args = "displayGame")
@ExtendWith(OutputCaptureExtension.class)
class ShellViewerApplicationIntegrationTest {

	@Test
	void testCommandOutput(CapturedOutput output) {
		String outputString = output.toString();
		assertThat(outputString).contains("MainView Game Display");
	}
}
