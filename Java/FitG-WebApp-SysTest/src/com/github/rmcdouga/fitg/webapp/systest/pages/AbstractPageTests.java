package com.github.rmcdouga.fitg.webapp.systest.pages;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPageTests {

	protected final WebDriver driver;

	protected static boolean isCorrectPage(WebDriver driver, String pageTitle) {
		Objects.nonNull(driver);
		Objects.nonNull(pageTitle);
		return pageTitle.equals(driver.getTitle());
	}

	protected AbstractPageTests(WebDriver driver) {
		this.driver = driver;
	}

}