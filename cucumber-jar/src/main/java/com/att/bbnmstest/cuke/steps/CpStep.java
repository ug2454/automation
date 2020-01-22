package com.att.bbnmstest.cuke.steps;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.StateContext;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.cptest.services.CpGuiServices;
import com.att.cptest.services.config.CpConfigTabServices;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.htmlunit.corejs.javascript.ast.GeneratorExpressionLoop;

@Ignore
public class CpStep{
	/*
	 * @Autowired
	 * 
	 * @Qualifier("CpGuiServices") private CpGuiServices cpGuiServices;
	 */
	 @Autowired
	   private StateContext context;
	 
	@Autowired
	@Qualifier("CpConfigTabServices")
	private CpConfigTabServices cpConfigTabServices;
	
	private Scenario scenarioName;
	@Before
	public void beforeHook(Scenario scenario) throws AutomationBDDServiceException, IOException {
	     this.scenarioName = scenario;
	     //System......(scenario.getName())
	     //System......(scenario.getId())
	     //cpConfigTabServices.setLogname(scenarioName.getName());
		//cpConfigTabServices.setLogname("abc");
	}
	
	@Given("^User has logged into gmail$")
	public void loginGmail() throws AutomationBDDServiceException, IOException{
		cpConfigTabServices.createDriver();
		cpConfigTabServices.loginGui();
				
	}
	
	@Given("^planner was able to navigate to (.*) in (.*) tab$")
	public void navigatetoPage(String subTab, String mainTab) throws AutomationBDDServiceException, IOException {
		
		cpConfigTabServices.createDriver();
		cpConfigTabServices.loginGui();
		cpConfigTabServices.navigateCp();
		if (mainTab.equalsIgnoreCase("config")) {
			cpConfigTabServices.navigateConfig();
			if (subTab.equalsIgnoreCase("SET%"))
				cpConfigTabServices.navigateToSet();
			else if(subTab.equalsIgnoreCase("Automated Resolutions"))
				cpConfigTabServices.navigateToAutoRes();
		}else if (mainTab.equalsIgnoreCase("loan matrix")) {
			cpConfigTabServices.navigateLoanMatrix();
		}else if (mainTab.equalsIgnoreCase("daily plan")) {
			cpConfigTabServices.navigateSdndAg();
		}
		
	}

	@When("^planner clicks on add row button$")
	public void clickAddBtn() throws AutomationBDDServiceException {
		cpConfigTabServices.addRow();
	}

	@When("^fills the required (.*) data$")
	public void fillAddSetSanityForm(String option) throws AutomationBDDServiceException {
		if(option.equalsIgnoreCase("set"))
			cpConfigTabServices.addSanitySet();
		else if (option.equalsIgnoreCase("automated resolutions"))
			cpConfigTabServices.addSanityAutoRes();
	}

	@Then("^the record will be added successfully$")
	public void validateGuiMsg() throws AutomationBDDServiceException {
		assertTrue(cpConfigTabServices.checkGuiMsg("items have been added successfully"));
	}
	
	@When("^planner deletes the added (.*) configuration$")
	public void delSetConfig(String option) throws AutomationBDDServiceException {
		if(option.equalsIgnoreCase("set"))
			cpConfigTabServices.delSetRow();
		//else if (option.equalsIgnoreCase("automated resolutions"))
			//cpConfigTabServices.delAutoResRow();
	}

	@Then("^configuration is successfully deleted$")
	public void validateGuiDelMsg() throws AutomationBDDServiceException {
		assertTrue(cpConfigTabServices.checkGuiMsg("Item has been deleted successfully"));
	}
	
	@When("^planner adjusts width of columns$")
	public void column() throws AutomationBDDServiceException, IOException, InterruptedException {
		cpConfigTabServices.updateColumnWidth(context.getPage());
	}
	@When("^planner clicks on (.*) option$")
	public void loangraph(String option) throws AutomationBDDServiceException, IOException, InterruptedException {
		if (option.equals("loan graphs"))
				cpConfigTabServices.navigateLoanGraph();
		else if (option.equals("AG"))
				cpConfigTabServices.navigateHierarchy();
		context.setPage(option);
	}
	
	@Then("^the column width shall be successfully adjusted$")
	public void columnvalidate() throws AutomationBDDServiceException, IOException, InterruptedException {
		assertTrue(cpConfigTabServices.validateColumnWidth());
	}
	
	@Then("^the hours do not include lunch$")
	public void checkCalculations() throws AutomationBDDServiceException, IOException, InterruptedException {
		assertTrue(cpConfigTabServices.vlidateSdndMinutes());
	}
	
    @Then("^planned hours under unproductive time is displayed$")
	public void plannedHoursDisplayed() throws AutomationBDDServiceException, IOException, InterruptedException {
		assertTrue(cpConfigTabServices.unprodHoursDisplayed());
	}
    
    @Given("^the user is logged into CP2.0$")
    public void userLogged() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.createDriver();
		cpConfigTabServices.loginGui();
		
    }
    @And("^User has navigated to Daily plan$")
    public void navigateCP() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.navigateCp();
    	cpConfigTabServices.navigateSdndAg();
    	cpConfigTabServices.navigateHierarchy();
    }
    @When("^User clicks on the edit icon in Loan section of Daily Plan$")
    public void clickEditLoan() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.editLoanSdnd();
    }
    @When("^planner navigated to ltm ag$")
    public void clickAgLtm() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.clickAgLtm();
    }
    @Given("^planner navigated to ltm$")
    public void navigateLtm() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.createDriver();
		cpConfigTabServices.loginGui();
		cpConfigTabServices.navigateCp();
    	cpConfigTabServices.navigateLtm();
    }
    @And("^has navigated to Demand Duration under Demand Summary$")
    public void ltmDemandDuration() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.navigateLtmDemandDuration();
    }
    
    @Given("^user has opened make my trip$")
    public void loginMmt() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.createDriver();
		cpConfigTabServices.loginGui();
		
    }
    @When("^user clicked on departure date$")
    public void clickDepartDate() throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.clickDepartDate();
    }
    @Then("^user selects a random date of (.+) (.+)$")
    public void selectRandomDate(String month,String date) throws AutomationBDDServiceException, IOException, InterruptedException{
    	cpConfigTabServices.selectRandomDate(month,date);
    }
}
