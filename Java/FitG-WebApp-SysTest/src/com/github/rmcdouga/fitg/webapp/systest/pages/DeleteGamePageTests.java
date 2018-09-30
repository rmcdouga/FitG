package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class DeleteGamePageTests extends AbstractPageTests {
	
	static final String PAGE_TITLE = "Delete Game";
	
	public DeleteGamePageTests(WebDriver driver) {
		super(driver);
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be a Delete Game page.");
	}
	
	public static DeleteGamePageTests create(WebDriver driver) {
		return new DeleteGamePageTests(driver);
	}

	public static boolean isDeleteGamePage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}

    public void deleteGame(String gameName) {
    	List<WebElement> foundNames = this.driver.findElements(By.className("gameName"));
    	assertEquals(1, foundNames.size(), "Expected only one game name.");
    	assertEquals(gameName, foundNames.get(0).getText());
    	deleteGame();
    }

    public void deleteGame() {
		List<WebElement> inputElements = this.driver.findElements(By.xpath("//input[@value='Delete Game']"));
		assertEquals(1, inputElements.size(), "Expected to find only one Delete Game button.");
		inputElements.get(0).click();
    }
}
