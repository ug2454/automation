package com.att.bbnmstest.cuke.steps.uverse;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.StepConstants;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException.Reason;
import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.uverse.UverseService;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class UverseStep extends BaseStep
{

   private final static Logger logger = Logger.getLogger(UverseStep.class);

   @Autowired
   @Qualifier("UverseService")
   private UverseService uverseService;

   @Autowired
   private ActivityValidationSteps validationSteps;


   @Given("^a non BVOIP Uverse (.+) (.+) (.+) order$")
   public void setUpOrder(String orderType, String orderSubType, String NTI)
   {
      logger.info("orderType: " + orderType);
      logger.info("orderSubType: " + orderSubType);
      logger.info("NTI: " + NTI);
      logger.info("UverseOrderDetailsAsMap : " + getContext().getOrderXmlReq());

      try
      {
         getContext().setOrderXmlReq(uverseService.createUverseOrder(orderType, orderSubType, NTI, getContext()
            .getOrderXmlReq()));
      }
      catch (Exception e)
      {
         throw new AutomationBDDCucumberException("Xml generation exception", e, getContext());
      }
      logger.info("UverseOrderDetailsAsMap : " + getContext().getOrderXmlReq());

   }

   @Given("^uverse subscriber with no pending order was provisioned in (.+)$")
   public void getPorivsionBanFromOl(String orderType)
   {
      try
      {
         if (logger.isDebugEnabled())
         {
            logger.debug("getPorivsionBanFromOl: orderType " + orderType);
            logger.debug("NTI " + getContext().getNti());
         }
         String ban = null;
         if (orderType.equals("CH"))
         {
            ban = uverseService.provisionedBan("UVERSE", getContext().getNti());

            if (logger.isDebugEnabled())
            {
               logger.debug("getPorivsionBanFromOl: ban " + ban);
            }

            getContext().setBan(ban);
         }
         else if (orderType.equals("PR"))
         {
            ban = getContext().getBan();
            if (logger.isDebugEnabled())
            {
               logger.debug("getPorivsionBanFromOl: ban " + ban);
            }

            getContext().setBan(ban);
            getContext().setServiceId(getContext().getServiceId());

         }
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("setup order failed for" + orderType, Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @Given("^a sanity order for (.+) (.+) (.+)$")
   public void setUpSanityOrder(String orderType, String orderSubType, String NTI)
   {
      try
      {
         getContext().setOrderXmlReq(uverseService.createUverseOrder(orderType, orderSubType, NTI, getContext()));
      }
      catch (Exception e)
      {
         throw new AutomationBDDCucumberException("Xml generation exception", e, getContext());
      }
      logger.info("UverseOrderDetailsAsMap : " + getContext().getOrderXmlReq());

   }


   @Given("^a FBS (.+) (.+) (.+) order$")
   public void setUpFBSOrder(String orderType, String orderSubType, String NTI)
   {
      try
      {
         getContext().setOrderXmlReq(uverseService.createFBSOrder(orderType, orderSubType, NTI));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Xml generation exception", e, getContext());
      }
      logger.info("FBSOrderDetailsAsMap : " + getContext().getOrderXmlReq());
   }

   @And("^oms Request was accepted$")
   public void sendOrder()
   {
      sendPtRequest();
      sendCpe();
   }

   @And("^the PT Request was accepted$")
   public void sendPtRequest()
   {
      try
      {
         uverseService.sendRequest(StepConstants.OMS_REQ_PT, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send pt Request Exception", e, getContext());
      }

      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      validationSteps.validateOmsTaskSucceeds(StepConstants.TASK_TRANSPORT_REQUEST_RESPONSE);
   }

   @When("^CPE request was accepted$")
   public void sendCpe()
   {
      try
      {
         uverseService.sendRequest(StepConstants.OMS_REQ_CPE, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send Cpe Request Exception", e, getContext());
      }


   }

   @Then("^Initiate SDP is succeeded$")
   public void sdpSuccessfull() throws AutomationBDDServiceException, InterruptedException
   {
      validationSteps.validateTaskSuccedsInOL("Initiate Handle SDP Provisioning Request");

   }


   @When("^BBNMS updates cim queries$")
   public void updates_ext_node_ciena_emux_with_authstatus()
   {
      try
      {
         uverseService.executeFbaseQueries(getContext().getEmtclli(), getContext().getServiceId());
      }
      catch (AutomationBDDServiceException e)
      {

         throw new AutomationBDDCucumberException("Update cim db exception", e, getContext());
      }
      logger.info("queries updated");

   }

   @When("^valid publish rg notification is accepted$")
   public void acceptsValidPubRgNotification()
   {
      try
      {
         uverseService.sendPubRgRequest(uverseService.createPubRgNotification(getContext().getOrderXmlReq()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send Valid PubRg Request Exception", e, getContext());
      }

   }

   @When("^valid publish rg notification for CH order is accepted$")
   public void acceptsValidChPubRgNotification()
   {
      try
      {
         uverseService.sendPubRgRequest(uverseService.createPubRgNotificationChange(getContext().getOrderXmlReq()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send Valid PubRg Request Exception", e, getContext());
      }

   }


   @And("^PONR is succeeded$")
   public void ponrSuccessfull()
   {
	   validationSteps.validateTaskSuccedsInOL("Apply Policies");
	   validationSteps.validateTaskSuccedsInOL("Initiate Handle SDP Provisioning Request");
	   validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_PONR);

   }

   @And("^PTC request was accepted$")
   public void sendPtc()
   {
      try
      {
         uverseService.sendRequest(StepConstants.OMS_REQ_PTC, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send PTC Request Exception", e, getContext());
      }
   }

   @When("^invalid publish rg notification is accepted$")
   public void acceptsInvalidPugRgNotification()
   {

      try
      {
         uverseService.sendPubRgRequest(uverseService.createInvalidPubRgNotification(getContext().getOrderXmlReq(),
            getContext().getCircuitId()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Send pubRG Request Exception", e, getContext());
      }

   }

   @Given("^a uverse subscriber with no pending order is provisioned in BBNMS$")
   public void getProvisionBanAndCircuitId()
   {
      try
      {
         getContext().setBan(uverseService.provisionedBan(EnvConstants.PRODUCTTYPE, EnvConstants.UVERSE_NTI));
         getContext().setServiceId(uverseService.provisionedCircuitId(EnvConstants.PRODUCTTYPE,
            EnvConstants.UVERSE_NTI));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Get Ban/circuitid Exception", e, getContext());
      }

   }

   @Given("^a FBS uverse subscriber with no pending order is provisioned in BBNMS$")
   public void fbsProvisonOrder()
   {
      try
      {
         getContext().setBan(uverseService.provisionedBanFbs(EnvConstants.PRODUCTTYPE, EnvConstants.FBS_NTI));
         getContext().setServiceId(uverseService.provisionedCircuitIdFbs(EnvConstants.PRODUCTTYPE,
            EnvConstants.FBS_NTI));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Get Ban/circuitid Exception", e, getContext());
      }

   }

   @And("^Wait for NAD Activation succeeds in ol$")
   public void nadActivationSucceedsInOl()
   {
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_NAD_INFO);
   }

   @Then("^Receive NAD Info succeeds in ol$")
   public void receiveNadInfoSuccessFully()
   {
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_NAD_INFO);
   }


   @When("^E/C/S Activation is successful$")
   public void flowHasProgressedEmsActivation()
   {
      validationSteps.validateTaskSuccedsInOL("EMS/CMS/SAM Activation");
   }

   @And("^Deactivate Policy in G2 is succeeded$")
   public void deactivateG2Successfull()
   {
      validationSteps.validateTaskSuccedsInOL(StepConstants.Deactivate_Policies_in_G2);
   }

   @And("^E/C/S DeActivation is successful$")
   public void deactivateECSSuccessfull()
   {
      validationSteps.validateTaskSuccedsInOL(StepConstants.Deactivate_EMS_CMS_SAM);
   }

   @And("^Order is Purged$")
   public void purgeOrderSuccessfull()
   {
      validationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);

   }

   @Then("^CMS DB with valid NAD Info is updated$")
   public void cmsDbUpdationSuccessful()
   {

      try
      {
         uverseService.updateCMS(getContext().getOrderXmlReq().get("modelNumberold").toString(), getContext()
            .getOrderXmlReq().get("manufacturerold").toString(), getContext().getBan());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("CMS Update Failed", e, getContext());
      }
   }

   @And("^Query CMS for NAD Info succeeds in ol$")
   public void queryForNadInfoSuccess()
   {
      validationSteps.validateTaskSuccedsInOL("Query for NAD Info");
   }

   @Then("^CMS DB with invalid NAD Info is updated$")
   public void wrongCmsDbUpdation()
   {
      try
      {
         uverseService.updatefailCMS(getContext().getOrderXmlReq().get("clli8").toString(), getContext()
            .getOrderXmlReq().get("vhoCode").toString(), getContext().getOrderXmlReq().get("ban").toString());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("CMS Update Failed", e, getContext());
      }
   }

   @Then("^Query CMS for NAD Info fails in ol$")
   public void queryForNadInfoFails()
   {
      validationSteps.validateTaskFailedInOL("Query for NAD Info");
   }

   @And("^work flow reaches nad activation$")
   public void workFlowReachesNadActivation()
   {
      try
      {
         getContext().setOrderPubRGXmlReq(uverseService.createPubRgNotificationChange(getContext().getOrderXmlReq()));
         uverseService.sendPubRgRequest(getContext().getOrderPubRGXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("send pubrg failed", e, getContext());
      }
   }


   @Given("^Purge a non BVOIP Uverse PR (.+) order$")
   public void purgeUverseProvideOrder(String NTI) throws AutomationBDDServiceException, InterruptedException
   {
      setUpOrder("PR", "NA", NTI);
      sendOrder();
      acceptsValidPubRgNotification();
      sdpSuccessfull();
      acceptsValidPubRgNotification();
      receiveNadInfoSuccessFully();
      ponrSuccessfull();
      sendPtc();
      purgeOrderSuccessfull();
   }

   @And("Order is set up as Nad Swap")
   public void setUpNadSwap()
   {
      getContext().getOrderXmlReq().put("rgActIndold", "D");
      getContext().getOrderXmlReq().put("technicianDispatchIndicator", "Install");
   }
   //installType is tech for the order
   @And("installType is (.*) for the order")
   public void setUpInstallType(String installType)
   {
      if(installType.equalsIgnoreCase("tech"))
    	  getContext().getOrderXmlReq().put("installationType", "Tech");
   }
}
