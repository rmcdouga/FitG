package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ListGamesPageTests extends AbstractPageTests {
	
	static final String PAGE_TITLE = "Running Games";
	
	public ListGamesPageTests(WebDriver driver) {
		super(driver);
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be a List Games page.");
	}
	
	public static ListGamesPageTests create(WebDriver driver) {
		return new ListGamesPageTests(driver);
	}

	public static boolean isGamesListPage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}

	public ListGamesPageTests deleteAllGames() {
		List<WebElement> deleteLinks = this.driver.findElements(By.xpath("//a[text()='Delete Game']"));
		while(deleteLinks.size() > 0) {
			deleteLinks.get(0).click();
			DeleteGamePageTests.create(this.driver).deleteGame();
			deleteLinks = this.driver.findElements(By.xpath("//a[text()='Delete Game']"));
		}
		this.driver.findElement(By.id("noGames")); // Should find the no games text when we're done.
		return this;
	}
	
	public void clickCreateLink() {
		this.driver.findElement(By.id("createGame")).click();
	}
}
