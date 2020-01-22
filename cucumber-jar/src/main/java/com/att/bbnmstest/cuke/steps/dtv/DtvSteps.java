package com.att.bbnmstest.cuke.steps.dtv;


import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.StepConstants;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException.Reason;
import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.dtv.DTVService;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SuppressWarnings("unchecked")
@Ignore
public class DtvSteps extends BaseStep
{
   private final static Logger logger = Logger.getLogger(DtvSteps.class);

   @Autowired
   @Qualifier("DTVService")
   private DTVService dtvService;
   @Autowired
   private ActivityValidationSteps validationSteps;


   @Given("^a dtv (.+) order$")
   public void setUpOrder(String orderType)
   {
      getContext().setOrderType(orderType);

      try
      {
         getContext().setOrderXmlReq(dtvService.setUpOrder(getContext().getOrderActionType(), getContext()
            .getOrderSubActionType(), getContext()));

         logger.debug("Initialized Context : " + getContext());
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @And("^nffl Indicator and dealer code was passed$")
   public void addDealerCode()
   {
      // TODO
      assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
      dtvService.addDealerCode(getContext().getOrderXmlReq(), "Y", "1234");
   }


   @Given("^(.+) DTV Order$")
   public void setUpSuspendOrder(String orderType)
   {
      getContext().setOrderType(orderType);

      try
      {

         getContext().setOrderXmlReq(dtvService.setUpAmmendOrder(getContext().getOrderActionType(), getContext()
            .getOrderSubActionType(), getContext()));

         logger.debug("Initialized Context : " + getContext());
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @Given("^AM is received on the order$")
   public void setOrderno()
   {

      getContext().setOrderNumber(getContext().getOrderNumber());
      getContext().setOrigOrderId(getContext().getOrigOrderId());
   }


   @Given("^(.+) of dtv order$")
   public void setUpDTVOrder(String orderType)
   {
      getContext().setOrderType(orderType);

      try
      {
         getContext().setOrderXmlReq(dtvService.setUpDTVOrder(getContext().getOrderActionType(), getContext()
            .getOrderSubActionType(), getContext()));

         logger.debug("Initialized Context : " + getContext());
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @Given("^dtv subscriber with no pending order was provisioned in (.+)$")
   public void getPorivsionBanFromOl(String orderType)
   {
      try
      {
         if (orderType.equals("CH"))
         {
            String ban = dtvService.provisionedBan();

            if (logger.isDebugEnabled())
               logger.debug("getPorivsionBanFromOl: ban" + ban);
            getContext().setBan(ban);
         }
         else if (orderType.equals("PR"))
         {
            getContext().setBan(getContext().getBan());

         }
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }
   }


   @And("dispatch indicator was (.+) on the order")
   public void addTechDispatchIndicator(String dispatchInd)
   {
      assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
      dtvService.addTechAtributes(getContext().getOrderXmlReq(), "Install", dispatchInd);
   }

   @And("dispatch indicator was (.+) on the NFFL order")
   public void addTechDispatchIndicatorNFFL(String dispatchInd)
   {
      assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
      dtvService.addTechAtributes(getContext().getOrderXmlReq(), "None", dispatchInd);
   }

   @And("^stb swap action is Upgrade$")
   public void addSwapAction()
   {
      assertNotNull("Context does not contain order", getContext().getOrderXmlReq());
      dtvService.addSwapAction(getContext().getOrderXmlReq(), "D", "I", "Upgrade", "Upgrade");
   }

   @And("^previous apid is present$")
   public void addPreviousAPID()
   {
      assertNotNull("Context does not contain order", getContext().getOrderXmlReq());
      dtvService.updatePreviousAPID(getContext().getOrderXmlReq(), "1234567890");

   }

   @When("^Dtv (.+) Request is accepted$")
   public void sendOrderRequest(String requestType)
   {
      assertNotNull("Context does not contain order", getContext().getOrderXmlReq());
      try
      {
         dtvService.sendOmsRequest(requestType, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("PT Request Failed Exception" + requestType, Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @When("^DTV F&T (.+) request is accepted$")
   public void sendFTOrderRequest(String requestType)
   {
      assertNotNull("Context does not contain order", getContext().getOrderXmlReq());
      try
      {
         dtvService.sendOmsFTRequest(requestType, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("F&T Order PT Request Failed  Exception" + requestType,
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   public void createIssacRequest(String isaacReqType)
   {
      getContext().setTdarIssacRequest(dtvService.createIssacRequest(isaacReqType, getContext().getBan(), getContext()
         .getOrigOrderId()));
   }

   public void createIssacRequestNFFL(String isaacReqType)
   {
      getContext().setTdarIssacRequest(dtvService.createIssacRequestNFFL(isaacReqType, getContext().getBan(),
         getContext().getOrigOrderId()));
   }


   @When("^ISAAC Process Multiple activation request for NFFL Order is accepted$")
   public void sendProcessMultipleDevicesNFFL()
   {
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForStbsNFFL(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());


         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @When("^ISAAC Process request for Multiple activation is accepted$")
   public void sendProcessMultipleSTBs()
   {
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForMulStbs(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());


         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }


   @When("^ISAAC Process request for Multiple Genies activation is accepted$")
   public void sendProcessMultipleGenies()
   {
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForMulGenie(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());


         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   public void createRepairIssacRequest(String isaacReqType)
   {
      getContext().setTdarIssacRequest(dtvService.createReceiverIsaacRequest(isaacReqType, getContext().getBan(),
         getContext().getOrigOrderId()));
   }

   public void createReceiverIsaacRequest(String isaacReqType)
   {
      getContext().setTdarIssacRequest(dtvService.createReceiverIsaacRequest(isaacReqType, getContext().getBan(),
         getContext().getOrigOrderId()));
      assertTrue(true);

   }

   public void sendRepairIsaacRequest(String requestType)
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         dtvService.sendRepairIsaacRequest(requestType, getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("ISSAC Request Failed  Exception" + requestType, Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   public void sendIsaacRequest(String requestType)
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         dtvService.sendIssacRequest(requestType, getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("ISSAC Request Failed  Exception" + requestType, Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^ISAAC Initiate request is accepted$")
   public void sendInitiate() throws AutomationBDDServiceException
   {
      createIssacRequest(StepConstants.ISSACT_REQ_INITIATE);
      sendIsaacRequest(StepConstants.ISSACT_REQ_INITIATE);
   }

   @When("ISAAC Process activation request is accepted$")
   public void sendProcess()
   {
      createIssacRequest(StepConstants.ISSACT_REQ_PROCESS);
      sendProcessIssacRequestForLineItemsAndIdentifiers();

   }

   @When("ISAAC Process activation request for NFFL order is accepted$")
   public void sendProcessNFFL()
   {
      createIssacRequestNFFL(StepConstants.ISSACT_REQ_PROCESS);
      sendProcessIssacRequestForLineItemsAndIdentifiersNFFL();

   }

   @When("ISAAC Process swap request for NFFL order is accepted$")
   public void sendSwapProcessNFFL()
   {
      createIssacRequestNFFL(StepConstants.ISSACT_REQ_SWAP);
      sendSwapRequestForLineItemsAndIdentifiersNFFL();

   }

   @When("^ISAAC Initiate Repair request is accepted$")
   public void sendInitiateRepair() throws AutomationBDDServiceException
   {
      createRepairIssacRequest(StepConstants.ISSACT_REQ_INITIATE);
      sendRepairIsaacRequest(StepConstants.ISSACT_REQ_INITIATE);
   }


   @When("^ISAAC Closeout Repair request is accepted$")
   public void sendRepairCloseout()
   {
      createRepairIssacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      sendRepairIsaacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
   }

   @When("^ISAAC Process Hardware request is accepted$")
   public void sendProcessWvb()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createIssacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForWvb(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), (Map) getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Hardware Request Failed ", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @When("^ISAAC Parallel Process Activation requests are accepted$")
   public void sendProcessparallel()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForProcessParllel(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Parallel Process  Request Failed ", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @When("^ISAAC Process Receiver request is accepted$")
   public void sendProcessReceiver1()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForReceiver(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Receiver Request Failed ", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }


   @When("^ISAAC Process request for Activation and Notify are accepted$")
   public void sendProcessActivationAndNotify()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessActivationAndNotify(StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(),
            getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }


   @When("^ISAAC Process request for Multiple activations are accepted$")
   public void sendProcessMultipleDevices()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForStbs(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @When("^a Deactivation requests are sent on the SSCPET api for the client WOLIs$")
   public void sendProcessDeActivationOfClients()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForDeActivationOfClients(
            StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext()
               .getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process  Deactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^the Deactivation requests are sent on the SSCPET api for the client WOLIs$")
   public void sendProcessDeActivationOfMultipleClients()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleDeActivationOfClients(
            StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext()
               .getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple Deactivation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @When("^Deactivation requests for AI I are sent on the SSCPET api for the client WOLIs$")
   public void sendProcessDeActivationOfMultipleClientsForActionIndicatorAsI()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleDeActivationOfClientsOfI(
            StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext()
               .getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple Deactivation Requests For New Client Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @When("^Reactivation requests are sent on the SSCPET api for the client WOLIs$")
   public void sendProcessReActivationOfClients()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForReActivationOfClients(
            StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext()
               .getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Reactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^BBNMS accept ISAAC Process Multiple Dtstb activation request$")
   public void sendProcessMultipleStbDevices()
   {
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleStbs(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @When("^ISAAC Swap request of Client swap from one Genie to another Genie is accepted$")
   public void sendSwapClientFromG1ToG2()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_SWAP);
         dtvService.sendSwapIssacRequestForG1ToG2(StepConstants.ISSACT_REQ_SWAP, getContext().getOrigOrderId(),
            getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Reactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }


   @When("^ISAAC DOA Swap activation request on pending order is accepted$")
   public void sendDoaSwapClient()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_SWAP);
         dtvService.sendDoaSwapIssacRequest(StepConstants.ISSACT_REQ_SWAP, getContext().getOrigOrderId(), getContext()
            .getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Reactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^ISAAC Multiple Repair swap requests are accepted$")
   public void sendMultipleRepairSwap()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_SWAP);
         dtvService.sendMultipleRepairSwapIssacRequest(StepConstants.ISSACT_REQ_SWAP, getContext().getOrigOrderId(),
            getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Reactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^ISAAC Process Update notify request is accepted$")
   public void sendUpdateRequest()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendUpdateIssacRequest(StepConstants.ISSACT_REQ_PROCESS, getContext().getOrigOrderId(), getContext()
            .getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Reactivation Requests Failed ", Reason.SERVICE_ERROR,
            e, getContext());
      }

   }

   @When("^ISAAC Closeout request is accepted$")
   public void sendCloseout()
   {
      createIssacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      sendIsaacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiers()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiers(StepConstants.ISSACT_REQ_PROCESS, getContext()
            .getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("ISSAC Process Request Failed Exception", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersNFFL()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersNFFL(StepConstants.ISSACT_REQ_PROCESS, getContext()
            .getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("ISSAC Process Request Failed Exception", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   public void sendSwapRequestForLineItemsAndIdentifiersNFFL()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         dtvService.sendSwapRequestForLineItemsAndIdentifiersNFFL(StepConstants.ISSACT_REQ_SWAP, getContext()
            .getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("ISSAC Process Request Failed Exception", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @Then("^validate woli response contains Productname1 (.+)$")
   public void validateProductNameByOrderIdForMobileDvr(String productName)
   {
      try
      {
         assertTrue(dtvService.validateProductNameByOrderIdForMobileDvr(getContext().getOrigOrderId(), productName));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Validate Productname for Hardware Failed  Exception",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   @Then("^validate woli response contains Productname2 (.+)$")
   public void validateProductNameByOrderIdForInternalWvb(String productName)
   {
      try
      {
         assertTrue(dtvService.validateProductNameByOrderIdForInternalWvb(getContext().getOrigOrderId(), productName));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Validate Productname for Hardware Failed Exception",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^validate woli response contains Productname (.+)$")
   public void validateProductNameByOrderIdForRB5(String productName)
   {
      try
      {
         assertTrue(dtvService.validateProductNameByOrderIdForRB5(getContext().getOrigOrderId(), productName));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Validate Productname for Hardware Failed Exception",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   @Given("^dtv subscriber with no pending order is provisioned in BBNMS$")
   public void getPorivsionBanFromOl()
   {
      try
      {
         getContext().setBan(dtvService.provisionedBan());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Fetching Provisioned Ban Failed Exception", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @When("^the (.+)-(.+) order has purged completely in BBNMS$")
   public void completePurge(String orderType, String orderSubType)
   {

      if (isProvideOrder(orderType))
      {
         flowCompleteProvideOrder();
      }
      else if (isChangeOrder(orderType))
      {
         flowCompleteChangeOrder();

      }
      else if (isMoveOrder(orderType))
      {
         flowCompleteMoveOrder();
      }
      else
      {
         throw new AutomationBDDCucumberException("Order Type [" + orderType + "] not supported",
            Reason.UNEXPECTED_EXCEPTION, getContext());
      }

   }

   public void flowCompleteChangeOrder()
   {
      sendOrderRequest(StepConstants.OMS_REQ_PT);

      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
      sendOrderRequest(StepConstants.OMS_REQ_CPE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_ACTIVATE_SERVICES_IN_UDAS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_EXCHANGE_CPE_DATA_CALL_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RETREIVE_WOLI_INFO_FROM_AHL);
      try
      {
         assertTrue(dtvService.validateTaskInProgressInOL(getContext().getOrigOrderId(),
            StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION) || dtvService.validateTaskFailedInOL(getContext()
               .getOrigOrderId(), StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate task in ol", Reason.SERVICE_ERROR, e,
            getContext());
      }

      createIssacRequest(StepConstants.ISSACT_REQ_INITIATE);
      sendIsaacRequest(StepConstants.ISSACT_REQ_INITIATE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_INITIATE_WOLI_ACTIVATION);
      createIssacRequest(StepConstants.ISSACT_REQ_SWAP);
      sendProcessIssacRequestForLineItemsAndIdentifiers();
      createIssacRequest(StepConstants.ISSACT_REQ_PROCESS);
      sendProcessIssacRequestForLineItemsAndIdentifiers();
      createIssacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      sendIsaacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_COMPLETE_WOLI_ACTIVATION);
      sendOrderRequest(StepConstants.OMS_REQ_PTC);
      validationSteps.validateOmsTaskSucceeds(
         StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_COMPLETE_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);
   }


   public void flowCompleteMoveOrder()
   {
      sendFTOrderRequest(StepConstants.OMS_REQ_PT);

      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
      sendFTOrderRequest(StepConstants.OMS_REQ_CPE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_EXCHANGE_CPE_DATA_CALL_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RETREIVE_WOLI_INFO_FROM_AHL);
      try
      {
         Thread.sleep(250000);
         validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_CEASE_PONR);
         validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_NOTIFY_CEASE_PONR);
         validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_ACTIVATE_SERVICES_IN_UDAS);
         assertTrue(dtvService.validateTaskInProgressInOL(getContext().getOrigOrderId(),
            StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION) || dtvService.validateTaskFailedInOL(getContext()
               .getOrigOrderId(), StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION));
      }
      catch (AutomationBDDServiceException | InterruptedException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate task in ol", Reason.SERVICE_ERROR, e,
            getContext());
      }

      createIssacRequest(StepConstants.ISSACT_REQ_INITIATE);
      sendIsaacRequest(StepConstants.ISSACT_REQ_INITIATE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_INITIATE_WOLI_ACTIVATION);
      createIssacRequest(StepConstants.ISSACT_REQ_PROCESS);
      sendProcessIssacRequestForLineItemsAndIdentifiers();
      createIssacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      sendIsaacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_COMPLETE_WOLI_ACTIVATION);
      sendFTOrderRequest(StepConstants.OMS_REQ_PTC);
      validationSteps.validateOmsTaskSucceeds(
         StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_COMPLETE_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);
   }

   public void flowCompleteProvideOrder()
   {
      sendOrderRequest(StepConstants.OMS_REQ_PT);

      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
      sendOrderRequest(StepConstants.OMS_REQ_CPE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_ACTIVATE_SERVICES_IN_UDAS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_STATUS_EXCHANGE_CPE_DATA_CALL_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RETREIVE_WOLI_INFO_FROM_AHL);

      try
      {
         assertTrue(dtvService.validateTaskInProgressInOL(getContext().getOrigOrderId(),
            StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION) || dtvService.validateTaskFailedInOL(getContext()
               .getOrigOrderId(), StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate task in ol", Reason.SERVICE_ERROR, e,
            getContext());
      }

      createIssacRequest(StepConstants.ISSACT_REQ_INITIATE);
      sendIsaacRequest(StepConstants.ISSACT_REQ_INITIATE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WAIT_FOR_INTITIATE_WOLI_ACTIVATION);
      createIssacRequest(StepConstants.ISSACT_REQ_PROCESS);
      sendProcessIssacRequestForLineItemsAndIdentifiers();
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_INITIATE_WOLI_ACTIVATION);
      createIssacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      sendIsaacRequest(StepConstants.ISSACT_REQ_CLOSEOUT);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_COMPLETE_WOLI_ACTIVATION);
      sendOrderRequest(StepConstants.OMS_REQ_PTC);
      validationSteps.validateOmsTaskSucceeds(
         StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_COMPLETE_RESPONSE);
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);
   }

   @Then("^PONR should contain modelNumber for the WVB$")
   public void validateModelByOrderIdForPonr() throws AutomationBDDServiceException
   {
      dtvService.validateModelByOrderIdForPonr(getContext().getOrigOrderId());
   }

   @And("^PONR should contain manufacturer for the WVB$")
   public void validateManufacturerByOrderIdForPonr() throws AutomationBDDServiceException
   {
      dtvService.validateManufacturerByOrderIdForPonr(getContext().getOrigOrderId());
   }

   @When("^there are multiple DSWM30 and the power insertor on the order$")
   public List<String> MultipleDSWM30AndPowerInsertor() throws AutomationBDDServiceException
   {
      List<String> queryResult;
      queryResult = dtvService.validateMultipleDSWM30AndPowerInsertor(getContext().getOrigOrderId());
      ArrayList<String> result = new ArrayList<String>();
      // StateContext context = getContext();
      // context.get(queryResult.get(0));
      // context.get(queryResult.get(1));
      return queryResult;

   }

   @And("^the client should have the parent receiver ID in the PONR$")
   public void validateRecIdInPONR() throws AutomationBDDServiceException
   {
      dtvService.validateRecIdInPONR(getContext().getOrigOrderId());
   }

   @And("^the client should have the parent receiver ID in the PONR for CH order$")
   public void validateRecIdInPONRChorders() throws AutomationBDDServiceException
   {
      dtvService.validateRecIdInPONRCHorders(getContext().getOrigOrderId());
   }


   @Given("^the value for the Genie receiver under the column GenieReceiver was (.+)$")
   public void validateValueOfGenieReceiver(String GenieFlag)
   {
      try
      {
         assertTrue(dtvService.validateValueOfGenieReceiver(getContext().getOrigOrderId(), GenieFlag));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Validate Productname for Hardware Failed Exception",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @And("^ISAAC Process request for NIRDs was accepted$")
   public void sendProcessForMultipleAHLWolis()
   {
      assertNotNull("Context does not contain issace order", getContext().getTdarIssacRequest());
      try
      {
         createReceiverIsaacRequest(StepConstants.ISSACT_REQ_PROCESS);
         dtvService.sendProcessIssacRequestForLineItemsAndIdentifiersForAHLHardware(StepConstants.ISSACT_REQ_PROCESS,
            getContext().getOrigOrderId(), getContext().getBan(), (Map) getContext().getTdarIssacRequest());
         assertTrue(true);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Process Multiple AHL Wolis activation Requests Failed ",
            Reason.SERVICE_ERROR, e, getContext());
      }

   }

   @Then("^the Counts of multiple DSWM30 and the power insertor for (.+) (.+) order should be validated$")
   public void validateCountsOfDSWM30AndPowerinsertor(String orderType, String orderSubType)
   {
      if (orderType.equals("PR") && (orderSubType.equals("NA")))
      {
         logger.info("The Tuner Count=32.So there are two Multi switch on the order");
      }
      else if (orderType.equals("CH") && (orderSubType.equals("NA")))
      {
         logger.info("The Tuner Count=62.So there are two Multi switch on the order");
      }
      else if (orderType.equals("CH") && (orderSubType.equals("AM")))
      {
         logger.info("The Tuner Count=62.So there are two Multi switch on the order");
      }
      else if (orderType.equals("PV") && (orderSubType.equals("NA")))
      {
         logger.info("The Tuner Count=32.So there are two Multi switch on the order");
      }
   }

   @Then("Udas interface should populate SwapAction='DelayedRemove' from TdarReceiverProcessInfo table$")
   public void swapActionForGenie() throws AutomationBDDServiceException

   {
      dtvService.swapActionForGenie(getContext().getOrigOrderId());
   }

   @Then("Udas interface should populate SwapAction is null from TdarReceiverProcessInfo table$")
   public void swapActionForGenieisnull() throws AutomationBDDServiceException

   {
      dtvService.swapActionForGenie(getContext().getOrigOrderId());
   }

   @And("uDAS interface has sent HardRestore in action attribute under accountDetail structure$")
   public void hardRestore() throws AutomationBDDServiceException

   {
      dtvService.hardRestoreforSuspendOrders(getContext().getOrigOrderId());
   }

   @And("uDAS interface has sent HardSuspend in action attribute under accountDetail structure$")
   public void hardSuspend() throws AutomationBDDServiceException

   {
      dtvService.hardSuspendforSuspendOrders(getContext().getOrigOrderId());
   }

   @Then("the rollback activity should be no-op$")
   public void rollbackNoOp() throws AutomationBDDServiceException

   {
      dtvService.rollbackNoOpforSUandRSorders(getContext().getOrigOrderId());
   }

   @And("the IRD-REASSIGN Woli for the Clients to be assigned should be present$")
   public void reassignWoli() throws AutomationBDDServiceException

   {
      dtvService.reassignWoliForAhl(getContext().getOrigOrderId());
   }

   @Then("In the MVSR request udas i/f should activate the receiver$")
   public void MVSRrequest() throws AutomationBDDServiceException

   {
      dtvService.MVSRrequestforUDAS(getContext().getOrigOrderId());
   }

   @Given("^(.+), (.+) order was received by BBNMS$")
   public void setupOrder(String orderType, String prodType) throws AutomationBDDServiceException
   {
      getContext().setOrderType(orderType);
      logger.info("order type " + orderType);
      try
      {
         if (prodType.equalsIgnoreCase("RC1"))
         {
            if (orderType.equalsIgnoreCase("CH-NA") || orderType.equalsIgnoreCase("PV-NA"))
               getContext().setBan(getContext().getBan());
            if (!orderType.equalsIgnoreCase("PV-NA"))
               getContext().setOrderXmlReq(dtvService.setUpOrder(getContext().getOrderActionType(), getContext()
                  .getOrderSubActionType(), getContext()));
            else
            {
               logger.info("Inside PV method");
               getContext().setOrderXmlReq(dtvService.setUpDTVOrder(getContext().getOrderActionType(), getContext()
                  .getOrderSubActionType(), getContext()));
            }
         }
         else if (prodType.equalsIgnoreCase("NFFL"))
         {
            if (orderType.equalsIgnoreCase("CH-NA") || orderType.equalsIgnoreCase("PV-NA"))
            {
               getContext().setBan(getContext().getBan());
            }
            getContext().setOrderXmlReq(dtvService.setUpOrder(getContext().getOrderActionType(), getContext()
               .getOrderSubActionType(), getContext()));
            getContext().getOrderXmlReq().put("nfflInd", "Y");
            getContext().getOrderXmlReq().put("technicianDispatchIndicator", "None");
         }
         else if (prodType.equalsIgnoreCase("MDU"))
         {
            if (orderType.equalsIgnoreCase("CH-NA") || orderType.equalsIgnoreCase("PV-NA"))
            {
               getContext().setBan(getContext().getBan());
            }
            getContext().setOrderXmlReq(dtvService.setUpOrder(getContext().getOrderActionType(), getContext()
               .getOrderSubActionType(), getContext()));
            getContext().getOrderXmlReq().put("customerLine", "MDU_DTH");
            getContext().getOrderXmlReq().put("propertyContractType", "NA");
            getContext().getOrderXmlReq().put("technicianDispatchIndicator", "None");
         }
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @Given("^for a (.+)$")
   public void defaultMethod(String order) throws AutomationBDDServiceException
   {
      // do nothing
   }

   @When("^countyFips attribute (.*) present under DTT component on provisionTransport request$")
   public void checkFipsAttribute(String isPresent)
   {
      if (isPresent.equalsIgnoreCase("is"))
      {
         assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
         assertTrue(dtvService.updateFipsAtributes(getContext().getOrderXmlReq(), "13001"));
      }
      else
      {
         assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
         assertTrue(dtvService.updateFipsAtributes(getContext().getOrderXmlReq(), ""));
      }
   }

   @Then("^Dealer code for receiver should be reflected in tdarwolitable$")
   public void verifyDealerCodeReceiver() throws AutomationBDDServiceException
   {
      dtvService.verifyDealerCodeReceiver(getContext().getBan());
   }

   @Then("^Dealer code for swap should be reflected in tdarwolitable$")
   public void verifyDealerCodeSwap() throws AutomationBDDServiceException
   {
      dtvService.verifyDealerCodeSwap(getContext().getBan());
   }

   @Then("^Dealer code for hardware should be reflected in tdarwolitable$")
   public void verifyDealerCodeHardware() throws AutomationBDDServiceException
   {
      dtvService.verifyDealerCodeHardware(getContext().getBan());
   }

   @Then("^Dealer code should be reflected in asset db$")
   public void verifyDealerCodeAsset() throws AutomationBDDServiceException
   {
      dtvService.verifyDealerCodeAsset(getContext().getBan());
   }

   @Then("^Dealer code and NFFL indicator should be reflected in tdarwoli table$")
   public void verifyDealerandNFFL() throws AutomationBDDServiceException
   {
      dtvService.verifyDealerandNFFL(getContext().getBan(), getContext().getOrderNumber());
   }


   @When("^attribute value is (.+)$")
   public void updateFipsAttribute(String fipsCountyValue)
   {
      assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
      assertTrue(dtvService.updateFipsAtributes(getContext().getOrderXmlReq(), fipsCountyValue));
   }

   @Then("^BBNMS (.+) the (.+) for the order$")
   public void omsStatus(String omsAction, String requestType) throws AutomationBDDCucumberException,
      InterruptedException
   {
      assertNotNull("Context does not contain order", getContext().getOrderXmlReq());
      try
      {
         if (!getContext().getOrderActionType().equalsIgnoreCase("PV"))
            dtvService.sendOmsRequest(requestType, getContext().getOrderXmlReq());
         else
            dtvService.sendOmsFTRequest(requestType, getContext().getOrderXmlReq());
         if (omsAction.equalsIgnoreCase("Accepts"))
         {
            assertTrue(dtvService.checkOmsStatus(omsAction, getContext().getOrigOrderId()));
            logger.info("Order number - " + getContext().getOrigOrderId() + " is success");
         }
         else
         {
            assertTrue(dtvService.checkOmsStatus(omsAction, getContext().getOrigOrderId()));
            logger.info("Order number - " + getContext().getOrigOrderId() + " is reject");
         }

      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("OMS Request Failed Exception" + requestType, Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @When("^purchaseTerms attribute is present under DTSTB component on provisionTransport request$")
   public void purchasePresent()
   {
      logger.info("before purchase term");
      getContext().getOrderXmlReq().put("purchaseTerms1", "Rent");
      logger.info("Purchase term default value for DTSTB -> Rent");
   }

   @When("^actionIndicator of respective DTSTB is (.+)$")
   public void purchaseTermDtstbInd(String actionInd)
   {
      getContext().getOrderXmlReq().put("dtstb1Ind", actionInd);
      logger.info("Action indicator for DTSTB -> " + actionInd);
   }

   @When("^purchaseTerms attribute value is (.+)$")
   public void purchaseTermVal(String pTermsVal)
   {
      getContext().getOrderXmlReq().put("purchaseTerms1", pTermsVal);
      logger.info("Purchase term updated value for DTSTB -> " + pTermsVal);
   }


   @And("2 STBs have action Indicator (.+)")
   public void updateDTSTBWithActionIndicator(String actionIndicator)
   {
      assertNotNull("context does not contain an order", getContext().getOrderXmlReq());
      dtvService.updateStbActionIndicator(getContext().getOrderXmlReq(), actionIndicator);
   }

}
