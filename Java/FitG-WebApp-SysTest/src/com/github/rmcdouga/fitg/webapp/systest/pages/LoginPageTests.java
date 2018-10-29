package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageTests extends AbstractPageTests {

	static final String PAGE_TITLE = "Authentication - Not logged in.";
	
	public LoginPageTests(WebDriver driver) {
		super(driver);
		assertThatThisIsLoginPage(driver);
	}

	private void assertThatThisIsLoginPage(WebDriver driver) {
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be an Action Deck page.");
	}
	
	public static LoginPageTests create(WebDriver driver) {
		return new LoginPageTests(driver);
	}

	public static boolean isLoginPage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}

	public LoginPageTests setLoginParms(String username, String password) {
		WebElement nameField = this.driver.findElement(By.name("emailAddress"));
		nameField.sendKeys(username);

		// Ignore password for the time being.
		return this;
	}
	
	public void submit() {
		List<WebElement> inputElements = this.driver.findElements(By.xpath("//input[@value='Log In']"));
		assertEquals(1, inputElements.size(), "Only expectded to find one input element.");
		inputElements.get(0).click();
	}
}
