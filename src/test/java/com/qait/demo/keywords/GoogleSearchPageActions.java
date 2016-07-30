package com.qait.demo.keywords;


import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class GoogleSearchPageActions extends GetPage{

	public GoogleSearchPageActions(WebDriver driver) {
		super(driver, "GoogleSearchPage");
	}

	public void VerifyUserIsOnGoogleSearchPage(){

		verifyPageTitleExact();
		isElementDisplayed("inp_searchBox");
	}

	public void enterSearchterm(String searchTerm){
		element("inp_searchBox").clear();
		element("inp_searchBox").sendKeys(searchTerm);
	}
	
}