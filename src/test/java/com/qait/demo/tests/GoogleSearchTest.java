package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;

import com.qait.automation.TestSessionInitiator;
import org.testng.annotations.*;

public class GoogleSearchTest {

	TestSessionInitiator test;
	String appUrl = null;

	@BeforeClass
	public void start_test_session() {
		test = new TestSessionInitiator();
		appUrl = getData("app_url");
	}

	@Test
	public void Step01_Launch_GoogleSearchPage() {
		test.launchApplication(appUrl);
		test.googleSearch.VerifyUserIsOnGoogleSearchPage();
	}

	@Test(dependsOnMethods={"Step01_Launch_GoogleSearchPage"})
	public void Step02_Perform_GoogleSearch() {
		test.googleSearch.enterSearchterm(getData("searchTerm"));
		test.googleSearchResult.performSearch();
	}
	
	@Test(dependsOnMethods={"Step02_Perform_GoogleSearch"})
	public void Step03_Validate_Google_Search() {
		test.googleSearchResult.validateSearchOnResultPage(getData("searchTerm"));
	}

	@Test(dependsOnMethods={"Step03_Validate_Google_Search"})
	public void Step04_Validate_Searched_Link() {
		test.googleSearchResult.enterIntoTopSearchedLink();
		test.searchedPage.verifyUserisOnHomePageOfSearchedLink();
	}
	


	@AfterClass(alwaysRun = true)
	public void stop_test_session() {
		test.closeBrowserSession();
	}
}
