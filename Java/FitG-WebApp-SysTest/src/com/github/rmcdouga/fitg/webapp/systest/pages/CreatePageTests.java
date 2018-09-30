package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreatePageTests extends AbstractPageTests {
	
	static final String PAGE_TITLE = "Create Game";
	
	public CreatePageTests(WebDriver driver) {
		super(driver);
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be a Create Game page.");
	}
	
	public static CreatePageTests create(WebDriver driver) {
		return new CreatePageTests(driver);
	}
	
	public static boolean isCreatePage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}
	
	public CreatePageTests fillGameName(String gameName) {
		WebElement nameField = this.driver.findElement(By.name("name"));
		nameField.sendKeys(gameName);
		return this;
	}
	
	public void clickCreate() {
		List<WebElement> inputElements = this.driver.findElements(By.xpath("//input[@value='Create Game']"));
		assertEquals(1, inputElements.size(), "Only expectded to find one input element.");
		inputElements.get(0).click();
	}

	public void createGame(String gameName) {
		fillGameName(gameName).clickCreate();
	}
	
}
