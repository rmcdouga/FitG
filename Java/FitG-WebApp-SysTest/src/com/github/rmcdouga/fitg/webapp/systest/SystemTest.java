package com.github.rmcdouga.fitg.webapp.systest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.rmcdouga.fitg.webapp.systest.pages.ActionDeckPageTests;
import com.github.rmcdouga.fitg.webapp.systest.pages.CreatePageTests;
import com.github.rmcdouga.fitg.webapp.systest.pages.ListGamesPageTests;

class SystemTest {

	private static final String GAME_NAME_PREFIX = "SeleniumTestGame";
	WebDriver driver;
	WebDriverWait wait;
	final String BASE_URL;
	final String PAGE_LOCATION = "HRForms/hrforms";
	final String USERNAME = "administrator";
	final String PASSWORD = "password";
	
	private enum DriverType { CHROME, HTMLUNIT };
	private final DriverType driverType = DriverType.HTMLUNIT;
	
	private enum TargetEnv { LOCAL, DEV, PROD };
	private final TargetEnv targetEnv = TargetEnv.LOCAL;
	
	public SystemTest() {
		switch(targetEnv) {
		case LOCAL:
			BASE_URL = "http://localhost:8080/FitG-WebApp/";
			break;
		case DEV:
			BASE_URL = "https://webapp-180401084243.azurewebsites.net/";
			break;
		case PROD:
			BASE_URL = "http://localhost:8080/";
			break;
		default:
			throw new IllegalStateException("Unknown target environment " + targetEnv.toString() + ".");
		}
	}

	@BeforeEach
	public void prepare() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		driver = driverType.equals(DriverType.HTMLUNIT) ? new HtmlUnitDriver(true) : new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
	}

	@AfterEach
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

	@Test
	public void testDefaultPage() {
		driver.get(BASE_URL);
	
		// If there are pre-exisitng games, then we get rid of them.
		if (ListGamesPageTests.isGamesListPage(driver)) {
			ListGamesPageTests.create(driver).deleteAllGames();
			driver.get(BASE_URL);
		}
		
		// OK, now we know that there should be no pre-existing games.
		assertTrue(CreatePageTests.isCreatePage(driver), ()->"There should be no pre-existing games, so should default to Create Game page but was '" + driver.getTitle() + "' page instead.");
		CreatePageTests.create(driver)
			.createGame(gameName(1));
		// Now that we've created a game, then the default page should take us to that game's Action Deck page.
		assertTrue(ActionDeckPageTests.isActionDeckPage(driver), ()->"After creating a game it should take us to the Action Deck page but was '" + driver.getTitle() + "' page instead.");
		
		// Now if we go back to the main page, it should take us to the ListGames Page
		driver.get(BASE_URL);
		assertTrue(ListGamesPageTests.isGamesListPage(driver), ()->"Now that there is an existing game, the default should direct to the Games List page but was '" + driver.getTitle() + "' page instead.");
		
		// Clean up after ourselves
		ListGamesPageTests.create(driver).deleteAllGames();
	}

	@Test
	public void testActionDeckPage() {
		driver.get(BASE_URL);
		final String gameName = "SeleniumActionDeckTestGame";
		
		if (ListGamesPageTests.isGamesListPage(driver)) {
			// There are some games already, make sure we don't have the one we're going to use.
			ListGamesPageTests listGamesPage = ListGamesPageTests.create(driver);
			if (listGamesPage.hasGame(gameName)) {
				listGamesPage.deleteGame(gameName);
			}
			// go to the Create Game Page explicitly
			driver.get(BASE_URL + "Games/Create");
		}
		if (CreatePageTests.isCreatePage(driver)) {
			CreatePageTests.create(driver)
						   .createGame(gameName);
		} else {
			fail("Landed on unexpected initial page.");
		}
		String url = BASE_URL + gameName + "/ActionDeck/Discard/0";
		driver.get(url);
		System.out.println("url=" + url);
		ActionDeckPageTests actionDeckPage = ActionDeckPageTests.create(driver)
																.isEmpty((b)->assertTrue(b, "Expected Deck to be empty initially."));
		
		// Draw a couple of cards
		int firstCardDrawn = actionDeckPage.drawCard()
				.numDiscardCards((i)->assertEquals(1, i.intValue(), "Expected one card in discard after first draw"))
				.cardNo();
		
		int secondCardDrawn = actionDeckPage.drawCard()
				.numDiscardCards((i)->assertEquals(2, i.intValue(), "Expected two cards in discard after second draw"))
				.cardNo();

		// Test previous card/next card links
		actionDeckPage.nextCard().cardNo((i)->assertEquals(firstCardDrawn, i.intValue(), "Expected next card to be the first card drawn."))
					  .prevCard().cardNo((i)->assertEquals(secondCardDrawn, i.intValue(), "Expected previous card to be the second card drawn."));

		// Test additional draw and reset.
		actionDeckPage.drawCard()
				.numDiscardCards((i)->assertEquals(3, i.intValue(), "Expected three cards in discard after third draw"))
				.resetDeck().isEmpty((b)->assertTrue(b, "Expected Deck to be empty after reset."))
				.drawCard().numDiscardCards((i)->assertEquals(1, i.intValue(), "Expected one card in discard after first draw after reset."));
		
	}
	
	private static String gameName(int gameNo) {
		return GAME_NAME_PREFIX + Integer.toString(gameNo);
	}
	
	@Disabled
	public void test_coe() {
		driver.get(BASE_URL + "/" + PAGE_LOCATION);
		driver.findElement(By.linkText("001_Absence_Information_Form.xdp")).click();

		// Should prompt for username and password
		wait.until(ExpectedConditions.titleIs("Adobe Experience Manager forms"));
		driver.findElement(By.name("j_username")).sendKeys(USERNAME);
		WebElement password_element = driver.findElement(By.name("j_password"));
		password_element.sendKeys(PASSWORD);
//	        assertEquals("Adobe Experience Manager forms", driver.findElement(By.tagName("title")).getText());
		password_element.submit();

		wait.until(ExpectedConditions.titleIs("Absence Information Form"));
		// Find all the fields
		List<WebElement> inputFields = driver.findElements(By.xpath("//input"));
		System.out.println("Found " + inputFields.size() + " fields.");

		// Fill in all the fields
		for (WebElement myField : inputFields) {
			String name = myField.getAttribute("name");
			String type = myField.getAttribute("type");
			if (myField.isDisplayed() && myField.isEnabled()) {
				if (type.equalsIgnoreCase("text")) {
					String fieldClass = myField.getAttribute("class");
					if (fieldClass != null && fieldClass.equalsIgnoreCase("datePickerTarget")) {
						myField.sendKeys("2016-03-16");
					} else {
						myField.sendKeys("---" + name + "---");
					}
				} else if (type.equalsIgnoreCase("checkbox")) {
					myField.click();
				} else if (type.equalsIgnoreCase("radio")) {
					String value = myField.getAttribute("value");
					if (value != null) {
						// Don't know what to do here yet.
						// clickRadioOption(name, value);
					} else {
						System.out.println("Found radio button " + name + " has value of null.");
					}
				}
			}
		}

		driver.findElement(By.xpath("//input[@aria-label='Submit']")).click();

		wait.until(ExpectedConditions.titleIs("Submission Complete"));

	}

	@Disabled
	public void test_google() {
		// And now use this to visit Google
		driver.get("http://www.google.com");
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());

	}

}
