package com.att.bbnmstest.cuke.steps.gui;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sourceforge.htmlunit.corejs.javascript.ast.GeneratorExpressionLoop;

@Ignore
public class GuiSteps extends BaseStep
{
   private final static Logger logger = Logger.getLogger(GuiSteps.class);

   @Autowired
   @Qualifier("BBNMSLsGuiServices")
   private BBNMSLsGuiServices guiService;

   @Autowired
   private ActivityValidationSteps validationSteps;

   public BBNMSLsGuiServices getBBNMSLsGuiServices()
   {
      return this.guiService;
   }

   @Given("a service-provisioned (.+) subscriber exist in BBNMS")
   public void setUpOrder(String productType) throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().loginGui(productType);
   }

   @When("user navigates to BBNMS gui (.*) page")
   public void navigateGui(String page) throws AutomationBDDServiceException
   {
      if (page.equals("order search"))
         getBBNMSLsGuiServices().orderSearchNavigate();
      else if (page.equals("batch resubmit"))
         getBBNMSLsGuiServices().batchNavigate();
   }

   @When("^user navigates to Angular GUI page$")
   public void dtvGuiNavigate() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().orderSearchNavigate();
   }
   
   @When("^user navigates to Angular GUI Subscription Search page$")
   public void guiNavigateSubscriptionSearch() throws AutomationBDDServiceException {
      getBBNMSLsGuiServices().subscriptionSearchNavigate();
   }

   @When("user searches an order for (.+) product in (.*) page")
   public void searchOrder(String productType, String page) throws AutomationBDDServiceException, InterruptedException
   {
      if (page.equals("order search")) {
         getBBNMSLsGuiServices().orderSearchOptions(productType);
         getBBNMSLsGuiServices().getOrderScreenDetails();
      }
      else if (page.equals("batch resubmit"))
         getBBNMSLsGuiServices().batchSearchOptions(productType);
   }

   @Then("(.*) screen will display workflow status and workflow description same as from the workflow microservice")
   public void validateSearchResult(String page) throws AutomationBDDServiceException
   {
      assertTrue(getBBNMSLsGuiServices().getSearchResultValidate());
   }

   @When("user clicks on order number link and navigates to order summary page")
   public void clickOrderLink() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().clickOrderLink();
   }

   @Then("order summary screen will display workflow status and description same as from the workflow microservice")
   public void ValidateSummary() throws AutomationBDDServiceException
   {
      assertTrue(getBBNMSLsGuiServices().validateOrderSummary());
   }

   @And("^user searches an order for (.+) in Angular GUI$")
   public void dtvSearchOrder(String productType) throws AutomationBDDServiceException, InterruptedException
   {
      getBBNMSLsGuiServices().dtvOrderSearch(productType);

   }
   
   @And("^user searches (.+) order in Angular GUI$")
   public void uverseOrderSearch(String productType) throws InterruptedException, AutomationBDDServiceException{
      getBBNMSLsGuiServices().uverseOrderSearch(productType);
      
   }
   
   @And("^user searches for (.+) fttn-bp cvoip order$")
   public void uverseFttnBpOrderSearch(String productType) throws InterruptedException, AutomationBDDServiceException{
      getBBNMSLsGuiServices().uverseFttnBpOrderSearch(productType);
      
   }
   @And("^user click on Order link$")
   public void clickDtvLink() throws AutomationBDDServiceException, InterruptedException
   {
      getBBNMSLsGuiServices().clickDtvOrderLink();
   }

   @And("^user click on BAN$")
   public void clickBan() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().clickDtvBan();
   }
   @And("^search an order for (.+) by BAN in Angular GUI$")
	public void searchbyBAN(String productType) throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().searchDtvOrderByBan();
	}
   
   @And("^user searches ban in Subscription Search page$")
   public void banSearchInSubscriptionPage() throws DAOServiceExeption {
      getBBNMSLsGuiServices().enterBanInSubscriptionSearch();
      
   }
   
   @Then("^Subscription Summary page should be displayed$")
   public void validateSubscriptionSummaryPage() throws InterruptedException {
      getBBNMSLsGuiServices().validateSubscriptionSummaryPageByBanClick();
   }
   
   @When("^user clicks on Subscription Info tab$")
   public void subscriptionInfoTab() throws InterruptedException {
      getBBNMSLsGuiServices().clickOnSubscriptionInfoTab();
   }
   @Then("^Subscription details should be displayed$")
   public void validateSubscriptionSearchResult() throws AutomationBDDCucumberException, AutomationBDDServiceException {
      assertTrue(getBBNMSLsGuiServices().validateTopMsg());
      
   }
   @And("^Service Type should be populated with required value$")
   public void validateServiceType() throws DAOServiceExeption, InterruptedException {
      assertTrue(getBBNMSLsGuiServices().validateServiceTypeInSubscriptionSearch());
   }
	@Then("^user click on workflow$")
	public void clickWorkflow() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().userClickWorkflow();
	}
	@And("^verify value of MDU Connected Propert is No$")
	public void connectedProperty() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().verifyConnectedPropert();
	}
	
	@And("^quit driver$")
	public void quitDriver() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().quitGui();
	}
	
	@And("^user click on WOLI ID$")
	public void clickWoli() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().clickDtvWoli();
	}
	
	@When("^user navigates to Admin Page$")
	public void clickAdmin() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().clickAdminPortSwap();
		
	}
	@And("^user enable BBExpress PortSwap$")
	public void enablePortSwap() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().enableBbexpressPortSwap();
	}
	@Then("^user validate success message for enable$")
	public void validateMsgEnable() throws AutomationBDDServiceException{
		assertTrue(getBBNMSLsGuiServices().validateSuccessMessageEnable());
	}
	
	@And("^user disable BBExpress PortSwap$")
	public void disablePortSwap() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().disableBbexpressPortSwap();
	}
	@Then("^user validate success message for disable$")
	public void validateMsgDisable() throws AutomationBDDServiceException{
		assertTrue(getBBNMSLsGuiServices().validateSuccessMessageDisable());
	}
	
	@Then("^order details page should be displayed$")
	public void validateSearchResult() throws AutomationBDDCucumberException, AutomationBDDServiceException {
	   assertTrue(getBBNMSLsGuiServices().validateTopMsg());
	   
	}
	@And("^user navigates to Reassign Client Screen$")
	public void reassignClientScreen() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().reassignClientScreen();
	}
	@And("^user click dropdown receiver$")
	public void dropdownReceiver() throws AutomationBDDServiceException {
		
		getBBNMSLsGuiServices().dropdownReceiver();
	}
	@When("^Deactivate or Reassign based on status which is (.+)$")
	public void clickDeactivate(String status) throws AutomationBDDServiceException, InterruptedException {
		getBBNMSLsGuiServices().clickDeactivateorReassign(status);
	}
	@And("^user click Refresh Button$")
	public void clickRefresh() throws AutomationBDDServiceException, InterruptedException{
		Thread.sleep(5000);
		getBBNMSLsGuiServices().clickRefresh();
	}
	@Then("^Verify Client is Deactivated$")
	public void verifyInactive() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().verifyStatusDescription();
	}
	
	@When("^user selects the receiver to relocate$")
	public void dropDownReceiver() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().selectReceiver();
	}
	@Then("^user click on reassign button$")
	public void reassignClient() throws AutomationBDDServiceException, InterruptedException{
		getBBNMSLsGuiServices().clickReassignButton();		
	}
	@Then("Verify Client is Reassinged")
	public void clientReassignVerify() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().verifyStatusDescription();
	}
	
	@When("^cvoip failed activities was searched by user$")
	public void clickLatestOrder() throws AutomationBDDServiceException, InterruptedException{
		getBBNMSLsGuiServices().clickLatestOrder();		
	}
	@When("^user sort by failed activities$")
	public void sortByFailed() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().sortByFailed();
	}
	@Then("^result and activity name should be verified for failed order$")
	public void verifyFailedActivity() throws AutomationBDDServiceException{
		assertTrue(getBBNMSLsGuiServices().verifyFailedActivity());
	}
	@And("^user opens Angular GUI$")
	public void openGUI() throws AutomationBDDServiceException{
		 getBBNMSLsGuiServices().orderSearchNavigate();
	}
	@Then("^result and activity name should be verified for inprogress order$")
	public void verifyInprogressActivity() throws AutomationBDDServiceException{
		assertTrue(getBBNMSLsGuiServices().verifyInprogressActivity());
	}
	@When("^user searches for Inprogress Activity$")
	public void searchInprogress() throws AutomationBDDServiceException, InterruptedException{
		getBBNMSLsGuiServices().searchInprogress();
	}
	@Then("^verify reset button should be working$")
	public void verifyReset() throws AutomationBDDServiceException,InterruptedException{
		assertTrue(getBBNMSLsGuiServices().verifyReset());
	}
	@Then("^user verify history tab$")
	public void verifyHistoryTab() throws AutomationBDDServiceException, InterruptedException{
		getBBNMSLsGuiServices().verifyHistoryTab();
	}
	@Then("^user verify ban,orderno, bbnmsorder no, due date, creation date, order status$")
	public void verifyOrderSummary() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().verifyOrderSummary();
	}
	@When("^open subscription search window$")
	public void subscriptionSearch() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().subscriptionSearch();
	}	
	@And("^user searches for dtv ban$")
	public void subscriptionSearchDtvBan() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().subscriptionSearchDtvBan();
	}
	@Then("^user verify 4kcontent, swm, mrv, countyfips value$")
	public void verifySubscriptionInfo() throws AutomationBDDServiceException{
		getBBNMSLsGuiServices().verifySubscriptionInfo();
	}
	@Then("^user click on CloseoutWoli Activation$")
	public void closeoutWoliButton() throws AutomationBDDServiceException {
		getBBNMSLsGuiServices().closeoutWoliButton();
	}
	
	
	
	
	
}
