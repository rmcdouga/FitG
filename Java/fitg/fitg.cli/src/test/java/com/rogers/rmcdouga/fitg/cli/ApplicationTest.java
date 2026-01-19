package com.rogers.rmcdouga.fitg.cli;

import static org.awaitility.Awaitility.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellScreen;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@ShellTest
@ContextConfiguration(classes = Application.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationTest {

	@Autowired
	ShellTestClient client;

	@Test
	void test(@Autowired ShellTestClient client) throws Exception {
		ShellScreen shellScreen = client.sendCommand("help");
		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(shellScreen).containsText("AVAILABLE COMMANDS");
		});
	}
}
