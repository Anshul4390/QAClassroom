/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import static com.qait.automation.utils.DataReadWrite.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.qait.automation.utils.CustomFunctions;
import com.qait.demo.keywords.GoogleSearchPageActions;
import com.qait.demo.keywords.GoogleSearchResultPageActions;
import com.qait.demo.keywords.LandingPage;
import com.qait.demo.keywords.TopSearchedPageActions;

public class TestSessionInitiator {

	protected WebDriver driver;
	private WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	public CustomFunctions customFunctions;

	/**
	 * Initiating the page objects
	 */
	public GoogleSearchPageActions googleSearch;
	public GoogleSearchResultPageActions googleSearchResult;
    public TopSearchedPageActions searchedPage;
    
    public LandingPage landingPage;



	public WebDriver getDriver(){
		return this.driver;
	}

	private void _initPage() {
		googleSearch = new GoogleSearchPageActions(driver);
		googleSearchResult = new GoogleSearchResultPageActions(driver);
		searchedPage = new TopSearchedPageActions(driver);
		landingPage = new LandingPage(driver);

		// custom function init
		customFunctions = new CustomFunctions(driver);
	}
	/**
	 * Page object Initiation done
	 */

	public TestSessionInitiator() {
		wdfactory = new WebDriverFactory();
		testInitiator();
	}

	private void testInitiator() {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(_getSessionConfig().get("timeout")), TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = {"tier", "browser", "seleniumserver", "seleniumserverhost", "timeout" , "driverpath"};
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String applicationpath) {
		Reporter.log("The application url is :- " + applicationpath, true);
		Reporter.log("The test browser is :- " + _getSessionConfig().get("browser"), true);
		driver.get(applicationpath);
	}

	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public void closeBrowserSession() {
		driver.quit();
	}
	
	public void waitForElementToLoad(By seleniumFindExpression, int waitInMilliSeconds) {

		long now = new Date().getTime();
		long endTime = now + waitInMilliSeconds;

		while (now < endTime) {

			try {
				driver.findElement(seleniumFindExpression);
			} catch (NoSuchElementException e) {
				now = new Date().getTime();

				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
				}

				continue;
			}
			break;
		}
		if (now > endTime) {
			throw new IllegalStateException("could not find element " + seleniumFindExpression.toString() + " within " + waitInMilliSeconds + "ms.");
		}
	}


	
	public void setActivitySettings(String settings){
		Select select=new Select(driver.findElement(By.id("settingPresets")));
		select.selectByVisibleText(settings);
	}

	public void launchActivity(String activity) {
		Select select=new Select(driver.findElement(By.cssSelector(".examples.presets")));
		select.selectByVisibleText(activity);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		driver.findElement(By.cssSelector(".section>input:nth-child(1)")).click();
	}
	
	public void launchItem(String item) {
		driver.findElement(By.cssSelector(".below>div>input")).sendKeys(item);;
		driver.findElements(By.cssSelector(".below>input")).get(0).click();
	}
}