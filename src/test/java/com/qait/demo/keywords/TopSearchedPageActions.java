package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class TopSearchedPageActions extends GetPage{

	public TopSearchedPageActions(WebDriver driver) {
		super(driver, "TestPage");
	}

	public void verifyUserisOnHomePageOfSearchedLink(){
		verifyPageTitleExact();
		isElementDisplayed("logo_QA");
	}
}