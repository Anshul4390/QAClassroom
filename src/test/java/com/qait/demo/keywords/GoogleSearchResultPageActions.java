package com.qait.demo.keywords;


import org.openqa.selenium.WebDriver;

import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class GoogleSearchResultPageActions extends GetPage{

	public GoogleSearchResultPageActions(WebDriver driver) {
		super(driver, "GoogleSearchResultPage");
	}

	public void performSearch(){
		element("btn_search").click();
	}
	
	public void validateSearchOnResultPage(String input){
		wait.waitForElementToBeVisible(element("resultsList"));
		Assert.assertTrue(element("lnk_firstSearchElement").getText().trim().equalsIgnoreCase(input), "Google Search not working as expected!!!");
	}
	
	public void enterIntoTopSearchedLink(){
		element("lnk_firstSearchElement").click();
	}
}