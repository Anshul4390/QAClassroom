package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.GetPage;

public class LandingPage extends GetPage{

	public LandingPage(WebDriver driver) {
		super(driver, "LandingPage");
	}
	
	public String currentTabName(){
		
		String actual = (String)executeJavascript("return $(arguments[0]).text()","ul.nav-tabs>li.active span");
//		System.out.println(element("tab_current").isDisplayed());
//		return element("tab_current").getText().trim();
		return actual;
	}

	public void switchToTab(String tabName){
		String value ;
		for(WebElement tab : elements("tabs")){
			value = (String) executeJavascript("return $(arguments[0]).text()",tab);
			if(value.contains(tabName)){
				tab.click();
				break;
			}
		}
		
	}
	
	public void loginWith(String username, String password){
		element("inp_username").sendKeys(username);
		element("inp_password").sendKeys(password);
		element("btn_signIn").click();
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToBeVisible(element("updatesContainer"));
	}
}
