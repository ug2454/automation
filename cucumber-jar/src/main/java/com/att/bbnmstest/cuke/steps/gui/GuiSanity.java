package com.att.bbnmstest.cuke.steps.gui;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.steps.BaseStep;

import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class GuiSanity extends BaseStep
{
   @Autowired
   @Qualifier("BBNMSLsGuiServices")
   private BBNMSLsGuiServices guiService;

   public BBNMSLsGuiServices getBBNMSLsGuiServices()
   {
      return this.guiService;
   }

   @Given("the user has access to BBNMS GUI")
   public void checkAccess() throws AutomationBDDServiceException
   {
      // Do something later
   }

   @When("the user navigates to BBNMS GUI")
   public void navigateGui() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().loginGui("Demo");
   }

   @Then("the BBNMS GUI is successfully loaded")
   public void validateGuiFirstPage() throws AutomationBDDServiceException
   {
      assertTrue(getBBNMSLsGuiServices().guiLoadValidate());
   }

   @Then("search should be successful")
   public void validateSearchSuccess() throws AutomationBDDServiceException
   {
      assertTrue(getBBNMSLsGuiServices().validateTopMsg());
   }

   @Then("result should be displayed")
   public void validateResultDisp() throws AutomationBDDServiceException
   {
      assertTrue(getBBNMSLsGuiServices().validateResultDisp());
   }

   @When("User clicks on orderlink")
   public void clickOrder() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().clickOrderLink();
   }

   @Then("(.*) page should load successfully")
   public void validateOrderSummary(String page) throws AutomationBDDServiceException, InterruptedException
   {
      assertTrue(getBBNMSLsGuiServices().validatePageDetails(page));
      assertTrue(getBBNMSLsGuiServices().validateTopMsg());
   }

   @When("user clicks on BAN link")
   public void clickBan() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().clickBanLink();
   }

   @When("searches an order for (.+) product in (.*) page")
   public void searchOrder(String productType, String page) throws AutomationBDDServiceException, InterruptedException
   {
      if (page.equals("order search"))
      {
         getBBNMSLsGuiServices().orderSearchOptions(productType);
      }
      else if (page.equals("batch resubmit"))
         getBBNMSLsGuiServices().batchSearchOptions(productType);
   }

   @Given("a (.+) customer with pending order was existing")
   public void getSanityBan(String productType) throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().loginGui("Demo");
      getBBNMSLsGuiServices().getSubsBan(productType);
   }

   @When("user searches the BAN in Gui")
   public void navigateToBan() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().subsSearchNavigate();
      getBBNMSLsGuiServices().banEnterAndSearch();
   }

   @When("user clicks on pending order link")
   public void pendingOrder() throws AutomationBDDServiceException
   {
      getBBNMSLsGuiServices().clickPendingOrder();
   }
   
   @And("^user clicks on Raw data tab$")
   public void nadInfoTab() throws InterruptedException, AutomationBDDServiceException {
      getBBNMSLsGuiServices().clickDetailsTab();
      getBBNMSLsGuiServices().clickRawDataTab();;
      
   }
   
}
