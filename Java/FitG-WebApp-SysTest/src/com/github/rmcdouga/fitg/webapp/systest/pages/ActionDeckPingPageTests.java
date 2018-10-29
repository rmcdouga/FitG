package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.openqa.selenium.WebDriver;

public class ActionDeckPingPageTests {

	static final String PAGE_TITLE = "Action Deck";
	
	public ActionDeckPingPageTests(WebDriver driver) {
		assertThatThisIsActionDeckPingPage(driver);
	}

	private void assertThatThisIsActionDeckPingPage(WebDriver driver) {
		assertNull(driver.getTitle(), "Expected Ping Page title to be null.");
	}
	
	public static ActionDeckPingPageTests create(WebDriver driver) {
		return new ActionDeckPingPageTests(driver);
	}

	public static boolean isActionDeckPingPage(WebDriver driver) {
		return driver.getTitle() == null && driver.getPageSource().contains("Ping!");
	}

}
