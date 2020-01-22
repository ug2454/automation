package com.att.bbnmstest.cuke;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
//import Reporter;
//import org.mockito.internal.exceptions.Reporter;

//import com.cucumber.listener.Reporter;

//import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/main/resources/features/google"},
plugin = { "pretty", "html:target/cucumber", "json:target/cucumber/cucumber.json" })
//plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber-report.json"})
public class RunCukesTest
{
	/*@AfterClass
	public static void writeExtentReport() {
	    Reporter.loadXMLConfig("src/main/resources/extent-config.xml");
	    Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Mac OSX");
        Reporter.setTestRunnerOutput("Sample test runner output message");
	}
	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig("src/main/resources/extent-config.xml");
	}*/
}
