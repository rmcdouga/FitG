package io.github.rmcdouga.shell_viewer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellScreen;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.context.ContextConfiguration;

@ShellTest
@ContextConfiguration(classes = ShellViewerApplication.class)
class ShellViewerApplicationEndToEndTest {

	@Test
	void testCommandExecution(@Autowired ShellTestClient client) throws Exception {
		// when
		ShellScreen shellScreen = client.sendCommand("displayGame");

		// then
		ShellAssertions.assertThat(shellScreen).containsText("Command 'displayGame' executed successfully.");
	}
}
