package com.qait.automation.utils;

import static com.qait.automation.utils.DataReadWrite.getProperty;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.galen.api.Galen;
import net.mindengine.galen.reports.GalenTestInfo;
import net.mindengine.galen.reports.HtmlReportBuilder;
import net.mindengine.galen.reports.model.LayoutReport;
import net.mindengine.galen.validation.ValidationError;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.qait.automation.getpageobjects.Tiers;

import freemarker.template.TemplateException;

public class LayoutValidation {

	WebDriver driver;
	String PageName;
	String tier;
	private String filepath = "src/test/resources/PageObjectRepository/";

	public LayoutValidation(WebDriver driver, String pageName) {
		this.driver = driver;
		this.PageName = pageName;
		setTier();
	}

	public void checklayout(List<String> tagsToBeTested) {
		try {
			// Executing layout check and obtaining the layout report
			LayoutReport layoutReport = Galen.checkLayout(this.driver,
					this.filepath + this.tier + this.PageName + ".spec",
					tagsToBeTested, null, null, null);

			// Creating a list of tests
			LinkedList<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

			// Creating an object that will contain the information about the
			// test
			GalenTestInfo test = GalenTestInfo.fromString(this.PageName
					+ " - layout test");

			// Adding layout report to the test report
			test.getReport().layout(layoutReport,
					this.PageName + " - layout test");
			tests.add(test);

			// Exporting all test reports to html
			new HtmlReportBuilder().build(tests, "target/galen-reports");

			if (layoutReport.errors() > 0) {
				Reporter.log("There are Layout Errors on the page:- "
						+ this.PageName + "!!! The Errors are for ", true);
				for (ValidationError error : layoutReport.getValidationErrors()) {
					for (String errorMsg : error.getMessages()) {
						Reporter.log(errorMsg, true);
					}
				}
				//Assert.fail();
			}

		} catch (IOException | TemplateException ex) {
			Reporter.log(ex.getLocalizedMessage(), true);
		}
	}

	private void setTier() {
		switch (Tiers.valueOf(getProperty("Config.properties", "tier"))) {
		case production:
		case PROD:
		case PRODUCTION:
		case Production:
		case prod:
			this.tier = "PROD/";
			break;
		case pristine:
		case PR:
		case PRISTINE:
		case Pristine:
		case pr:
			this.tier = "PR/";
			break;
		case qa:
		case QA:
		case Qa:
			this.tier = "QA/";
			break;
		case Dev:
		case DEV:
		case dev:
			this.tier = "DEV/";
			break;
		case mice:
		case MICE:
		case Mice:
			this.tier = "MICE/";
			break;
		case ItemService:
		case ITEMSERVICE:
		case itemservice:
			this.tier = "QA/";
			break;
		}
	}

}
