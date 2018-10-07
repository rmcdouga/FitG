package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ListGamesPageTests extends AbstractPageTests {
	
	private static final String DELETE_GAME_ANCHOR_XPATH = "//a[text()='Delete Game']";
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
		List<WebElement> deleteLinks = this.driver.findElements(By.xpath(DELETE_GAME_ANCHOR_XPATH));
		while(!deleteLinks.isEmpty()) {
			deleteLinks.get(0).click();
			DeleteGamePageTests.create(this.driver).deleteGame();
			deleteLinks = this.driver.findElements(By.xpath(DELETE_GAME_ANCHOR_XPATH));
		}
		this.driver.findElement(By.id("noGames")); // Should find the no games text when we're done.
		return this;
	}
	
	public void clickCreateLink() {
		this.driver.findElement(By.id("createGame")).click();
	}

	public ListGamesPageTests deleteGame(int index) {
		List<WebElement> deleteLinks = this.driver.findElements(By.xpath(DELETE_GAME_ANCHOR_XPATH));
		if (index >= 0 && index < deleteLinks.size()) {
			deleteLinks.get(index).click();
			DeleteGamePageTests.create(this.driver).deleteGame();
		} else {
			throw new IndexOutOfBoundsException("Index " + index + " is outside the number of delete links (" + deleteLinks.size() + ").");
		}
		return this;
	}

	public int gameIndex(String gameName) {
		List<WebElement> gameNames = this.driver.findElements(By.className("gameName"));
		for (int i = 0; i < gameNames.size(); i++) {
			if (gameName.equals(gameNames.get(i).getText())) {
				return i;
			}
		}
		return -1;
	}

	public boolean hasGame(String gameName) {
		return gameIndex(gameName) > -1;
	}
	
	public ListGamesPageTests deleteGame(String gameName) {
		return deleteGame(gameIndex(gameName));
	}

	public ListGamesPageTests  gameIndex(String gameName, Consumer<Integer> consumer) {
		consumer.accept(gameIndex(gameName));
		return this;
	}
	
	public ListGamesPageTests hasGame(String gameName, Consumer<Boolean> consumer) {
		consumer.accept(hasGame(gameName));
		return this;
	}
	
}
