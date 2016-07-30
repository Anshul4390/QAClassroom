package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.qait.automation.utils.DataReadWrite.getProperty;



import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.utils.LayoutValidation;

import static org.testng.Assert.fail;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class GetPage extends BaseUi {

	protected WebDriver driver;
	String pageName;
	LayoutValidation layouttest;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.driver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);
		
	}

	public void testPageLayout(List<String> tagsToBeTested){
		layouttest.checklayout(tagsToBeTested);
	}
	
	public void testPageLayout(String tagToBeTested){
		testPageLayout(Arrays.asList(tagToBeTested));
	}
	
	public void testPageLayout(){
		testPageLayout(Arrays.asList(getProperty("browser")));
	}
	
	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
					.trim()));
		}
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}
	
	protected WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBePresent(getLocator(elementToken, replacement1, replacement2));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(driver
					.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element "+ elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(driver.findElements(getLocator(
				elementToken, replacement)));
	}
	
	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return driver.findElements(getLocator(elementToken, replacement1, replacement2));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "with text " + elementTextReplace + "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName + " with text "
				+ elementTextReplace + " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText(), expectedText,
				"Assertion Failed: element '" + elementName
						+ "' Text is not as expected: ");
		logMessage("Assertion Passed: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName
				+ " is displayed.");
		return result;
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected String getLocatorString(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return locator[2].trim();
	}
	
	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = StringUtils.replace(locator[2], "$", replacement1);
		locator[2] = StringUtils.replace(locator[2], "%", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}
}