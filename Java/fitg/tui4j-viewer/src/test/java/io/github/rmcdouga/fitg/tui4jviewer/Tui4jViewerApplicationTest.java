package io.github.rmcdouga.fitg.tui4jviewer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Disabled("Currently hangs waiting for keypress after context load")
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class Tui4jViewerApplicationTest {

	@Test
	void contextLoads() {
	}

}
