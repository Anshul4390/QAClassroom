package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class HRISTest {
	
	TestSessionInitiator test ;
	
	@BeforeClass
	public void start_test_session() {
		test = new TestSessionInitiator();
		String appUrl = getData("app_url");
		test.launchApplication(appUrl);
	}
	
	@Test
	public void defaultTabOnLandingPageIsUpdates(){
		Assert.assertEquals(test.landingPage.currentTabName(),"QAIT Updates");
	}
	
	@Test
	public void incorrectloginCredentialsRendersDefaultTab(){
		test.landingPage.switchToTab("Login");
		test.landingPage.loginWith(getData("credentials.invalid.username"), getData("credentials.invalid.password"));
		Assert.assertEquals(test.landingPage.currentTabName(),"QAIT Updates");
	}
	
	@AfterTest
	public void tearDown(){
		test.closeBrowserSession();
	}

}
