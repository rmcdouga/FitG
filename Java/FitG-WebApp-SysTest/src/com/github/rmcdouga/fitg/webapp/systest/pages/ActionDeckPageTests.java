package com.github.rmcdouga.fitg.webapp.systest.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.rmcdouga.fitg.webapp.resources.ActionDeckResources;

public class ActionDeckPageTests  extends AbstractPageTests {
	
	static final String PAGE_TITLE = "Action Deck";
	
	public ActionDeckPageTests(WebDriver driver) {
		super(driver);
		assertThatThisIsActionDeckPage(driver);
	}

	private void assertThatThisIsActionDeckPage(WebDriver driver) {
		assertEquals(PAGE_TITLE, driver.getTitle(), "Expected the page to be an Action Deck page.");
	}
	
	public static ActionDeckPageTests create(WebDriver driver) {
		return new ActionDeckPageTests(driver);
	}

	public static boolean isActionDeckPage(WebDriver driver) {
		return AbstractPageTests.isCorrectPage(driver, PAGE_TITLE);
	}

	public ActionDeckPageTests drawCard() {
		List<WebElement> inputElements = this.driver.findElements(By.name("draw"));
		assertEquals(1, inputElements.size(), "Only expected to find one draw button.");
		inputElements.get(0).click();
		return new ActionDeckPageTests(driver);	// New page, so create new driver object.
	}

	public ActionDeckPageTests resetDeck() {
		List<WebElement> inputElements = this.driver.findElements(By.name("reset"));
		assertEquals(1, inputElements.size(), "Only expected to find one reset button.");
		inputElements.get(0).click();
		assertThatThisIsActionDeckPage(driver);
		return this;
	}
	
	public ActionDeckPageTests nextCard() {
		this.driver.findElement(By.id(ActionDeckResources.NEXT_CARD_NO_ID)).click();
		assertThatThisIsActionDeckPage(driver);
		return this;
	}

	public ActionDeckPageTests prevCard() {
		this.driver.findElement(By.id(ActionDeckResources.PREV_CARD_NO_ID)).click();
		assertThatThisIsActionDeckPage(driver);
		return this;
	}
	
	public int cardNo() {
		String cardNumberText = this.driver.findElement(By.id(ActionDeckResources.CARD_NO_ID)).getText();
		return Integer.parseInt(cardNumberText.split(" ")[1]);
	}
	
	public int numDiscardCards() {
		String cardNumberText = this.driver.findElement(By.id("discard_num_cards_id")).getText();
		return Integer.parseInt(cardNumberText);
	}

	public int discardCardNo() {
		String cardNumberText = this.driver.findElement(By.id("discard_card_number_id")).getText();
		return Integer.parseInt(cardNumberText);
	}
	
	public boolean isEmpty() {
		List<WebElement> noCardsElements = this.driver.findElements(By.id("empty_discard_id"));
		return !noCardsElements.isEmpty();
	}
	
	public ActionDeckPageTests cardNo(Consumer<Integer> consumer) {
		consumer.accept(cardNo());
		return this;
	}
	
	public ActionDeckPageTests numDiscardCards(Consumer<Integer> consumer) {
		consumer.accept(numDiscardCards());
		return this;
	}

	public ActionDeckPageTests discardCardNo(Consumer<Integer> consumer) {
		consumer.accept(discardCardNo());
		return this;
	}

	public ActionDeckPageTests isEmpty(Consumer<Boolean> consumer) {
		consumer.accept(isEmpty());
		return this;
	}
	
}
