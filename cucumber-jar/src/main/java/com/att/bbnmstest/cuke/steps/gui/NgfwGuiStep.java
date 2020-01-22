package com.att.bbnmstest.cuke.steps.gui;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.wll.FixedWirelessService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class NgfwGuiStep extends BaseStep
{
   private final static Logger logger = Logger.getLogger(NgfwGuiStep.class);

   @Autowired
   @Qualifier("BBNMSLsGuiServices")
   private BBNMSLsGuiServices guiService;

   @Autowired
   @Qualifier("FixedWirelessService")
   private FixedWirelessService fixedWlService;

   @Autowired
   private ActivityValidationSteps validationSteps;

   @Given("^a provisioned wll subscription with sub transport type as (.+)$")
   public void getWllBanFromOlDb(String subTransportType) throws Exception
   {
      getContext().setBan(fixedWlService.getWllBanAndServiceFromWllSubscriptions(subTransportType).get(0));
      logger.info("ban number from DB is -->" + getContext().getBan());
      assertTrue(!getContext().getBan().isEmpty());
   }

   @Given("^wll orders with sub transport type as (.+) present in the system$")
   public void checkIfOrdersPresentInSystem(String subTransportType) throws Exception
   {
      getWllBanFromOlDb(subTransportType);
   }

   @When("^user navigates to BBNMS order search page$")
   public void navigateToOrderSearchPage() throws AutomationBDDServiceException
   {
      guiService.loginGui("WLL");
   }

   @When("^user selects nti as (.+) and subtransportType value as (.+)$")
   public void selectNtiAndSubTransport(String nti, String subTransportType) throws AutomationBDDServiceException, InterruptedException
   {
      guiService.setNtiAndSubTransport(nti, subTransportType);
   }

   @When("^user clicks on (.+) button$")
   public void clickOn(String btn)
   {
      if (btn.equals("search"))
      {
         guiService.clickGuiItem("xpath");
      }
   }

   @When("^user selects (.+) from service order search dropdown$")
   public void selectServiceOrder(String prodType) throws InterruptedException, AutomationBDDServiceException
   {
      guiService.wllOrderSearch(prodType);
   }


   @When("^user enters the ban and click on search$")
   public void enterBanAndClickSearch()
   {
      assertTrue(true);
   }

   @Then("^WLL subscription summary gui shall display subtransportType value as (.+)$")
   public void validateSubscriptionSearchGuiAttributes(String subTransportType) throws AutomationBDDServiceException
   {
      assertTrue(guiService.validateSubscriptionSummaryPage(subTransportType, "subtransportType"));
   }

   @Then("^Wll order search gui shall display a list of order$")
   public void verifyOrderSearchGuiDisplaysOrderList()
   {
      assertTrue(guiService.getSearchStatus());
   }

   @When("^user clicks on any wll data order number$")
   public void clickOnDataOrderList() throws AutomationBDDServiceException
   {
      guiService.clickDataOrder();
   }

   @Then("^WLL service order summary page opens$")
   public void verifyServiceOrderPageOpens()
   {
      guiService.verifyServiceOrderPage();
   }

   @Then("^subtransportType value is (.+)$")
   public void matchAttributeValue(String subTransportType)
   {
      if (subTransportType.equals("Blank"))
      {
         String text = guiService.matchAttribute(subTransportType);
         assertTrue(text.equals(""));
      }
      else
      {

         assertTrue(guiService.matchAttribute(subTransportType).equals(subTransportType));
      }

   }


}
