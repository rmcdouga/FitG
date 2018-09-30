package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.WebDriver;

public class ActionDeckPageTests  extends AbstractPageTests {
	
	static final String PAGE_TITLE = "Action Deck";
	
	public ActionDeckPageTests(WebDriver driver) {
		super(driver);
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be a Delete Game page.");
	}
	
	public static ActionDeckPageTests create(WebDriver driver) {
		return new ActionDeckPageTests(driver);
	}

	public static boolean isActionDeckPage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}



}
