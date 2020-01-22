package com.att.bbnmstest.cuke.steps.wll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.StateContext;
import com.att.bbnmstest.cuke.StepConstants;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException.Reason;
import com.att.bbnmstest.cuke.steps.ActivityValidationSteps;
import com.att.bbnmstest.cuke.steps.BaseStep;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.Utils;
import com.att.bbnmstest.services.util.XpathConstants;
import com.att.bbnmstest.services.wll.FixedWirelessService;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@Ignore
public class FixedWireLessStep extends BaseStep
{
   private final static Logger logger = Logger.getLogger(FixedWireLessStep.class);

   @Autowired
   @Qualifier("FixedWirelessService")
   private FixedWirelessService fixedWlService;

   @Autowired
   private ActivityValidationSteps bbnmsActivityValidationSteps;


   @Given("^OMS fixed wireless (.+) (.+) data order$")
   public void setUpFwOrder(String orderActionType, String orderActionSubType)
   {
      try
      {
         logger.info("OMS fixed wireless " + orderActionType + "-" + orderActionSubType + " data order");
         getContext().setOrderType(orderActionType + "-" + orderActionSubType);
         getContext().setOrderXmlReq(fixedWlService.setUpOrder(orderActionType, orderActionSubType, (getContext()
            .getOrderXmlReq() != null) ? getContext().getOrderXmlReq() : getContext()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("OMS Order creation failed for " + orderActionType + "-"
            + orderActionSubType, Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Given("^subTransportType attribute on the order was (.+)$")
   public void setTransportType(String subTransportTypeValue)
   {
	   if (StringUtils.equals(subTransportTypeValue, StepConstants.WLL_SUB_TRANSPORT_TYPE_BLANK))
      {
         subTransportTypeValue = "";
      }

      getContext().setSubTransportType(subTransportTypeValue);
      getContext().setSubTransportTypeCm(subTransportTypeValue);

      /*
       * Fetch ban and serviceId/circuitId for Change, Move and Cease orders
       */
      if (isCeaseOrder() || isChangeOrder() || isMoveOrder())
      {
         fetchProvisionedBanOfNgfwType(subTransportTypeValue);
         logger.info("Ban and Service Id from Context" + getContext().getBan() + "  " + getContext().getServiceId());
         fixedWlService.updateOrderBanAndServiceId(getContext().getOrderActionType(), getContext().getBan(),
            getContext().getServiceId(), getContext().getOrderXmlReq());
      }
   }

   @Given("^appointment Date was in past date$")
   public void setAppointmentDate()
   {
	   //add the code for appointment date in past
	   getContext().getOrderXmlReq().put("dueDate", Utils.getExtendedDateByDays(-1));
	   getContext().getOrderXmlReq().put("apt_start", Utils.getExtendedDateByDays(-1));
	   getContext().getOrderXmlReq().put("apt_end", Utils.getExtendedDateByDays(0));

   }
   @When("^BBNMS receives a wll (.+) request$")
   public void sendOrderRequest(String requestType)
   {
      try
      {
         fixedWlService.sendOmsRequest(requestType, getContext().getOrderXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to send OMS " + requestType + " request",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^BBNMS-OMS interface accepts the request$")
   public void validateOmsTaskAtOl()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      bbnmsActivityValidationSteps.validateOmsTaskSucceeds(
         StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
   }

   @Then("^BBNMS-OMS interface rejects the request$")
   public void validateOmsRejectsRequest()
   {

      if (getContext().getOrderXmlReq().containsKey("orderNumberPvData"))
      {
         bbnmsActivityValidationSteps.validateOmsTaskFailed((String) getContext().getOrderXmlReq().get(
            EnvConstants.TAG_ORIG_ORDER_ACT_ID_PV), StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
      }
      else
      {
         bbnmsActivityValidationSteps.validateOmsTaskFailed(
            StepConstants.TASK_TRANSPORT_STATUS_PROVISION_TRANSPORT_RESPONSE);
      }
   }

   @Then("^a service order workflow gets initiated in BBNMS$")
   public void validateWorkflowTask()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
   }

   @Given("^OMS (.+) wireless provide data order exits in BBNMS$")
   public void createFixedWirelessDataSubscription(String orderSubTransporttype)
   {
      try
      {

         if (getContext().isPurgedOrderContext())
         {
            logger.info("Context has purged order details . New PR-NA order will not be placed");
            return;
         }

         logger.info("Run workFlow for PR-NA Fixed wireless order");

         setUpFwOrder(StepConstants.ORDER_TYPE_PROVIDE, StepConstants.ORDER_SUB_TYPE_NOT_APPLICABLE);

         if (StringUtils.equals("ngfw", orderSubTransporttype))
         {
            setTransportType(StepConstants.WLL_SUB_TRANSPORT_TYPE_NEXTGEN);
         }
         else
         {
            setTransportType(StepConstants.WLL_SUB_TRANSPORT_TYPE_BLANK);
         }


         flowOmsOrder();

         validateSuccessfulOwlInstallation();
         validateServiceActivation();
         sendValidPurRgRequest();
         validateSuccessCloseoutRequest();


         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_ORDER_LEVEL_PONR);
         sendOrderRequest(StepConstants.OMS_REQ_PTC);
         fixedWlService.validateTaskInProgressInOL(getContext().getOrigOrderId(), StepConstants.TASK_HOLD_FOR_DELAY);
         fixedWlService.updateEligibleToExecuteTimeForHoldForDelayTimerTask(getContext().getOrigOrderId(),
            StepConstants.TASK_HOLD_FOR_DELAY_DEFINATION_ID);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);

         logger.info("Marking Context as Purged Order");

         getContext().setAsPurgedOrderContext();

      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Order Flow Execution failed [" + e.getMessage() + "]",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^Hold for Delay Timer task is in_progress in OL$")
   public void validateHoldTimerTask() throws AutomationBDDServiceException
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_ORDER_LEVEL_PONR);
      sendOrderRequest(StepConstants.OMS_REQ_PTC);
      fixedWlService.validateTaskInProgressInOL(getContext().getOrigOrderId(), StepConstants.TASK_HOLD_FOR_DELAY);
   }


   @And("^Update Hold for Delay Timer$")
   public void updateHoldforDealyTime() throws AutomationBDDServiceException
   {
      fixedWlService.updateEligibleToExecuteTimeForHoldForDelayTimerTask(getContext().getOrigOrderId(),
         StepConstants.TASK_HOLD_FOR_DELAY_DEFINATION_ID);

   }

   @Then("^PONR task succeeds in ol$")
   public void validatePonrTask()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_ORDER_LEVEL_PONR);
      sendOrderRequest(StepConstants.OMS_REQ_PTC);

   }

   @And("^OWA closeout succeeds in ol$")
   public void validateSuccessCloseoutRequest()
   {
      try
      {
         fixedWlService.sendWllIssacRequest(fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
            getContext().getBan(), StepConstants.ISSACT_REQ_CLOSEOUT, getContext().getSubTransportType(),
            (java.util.Map<String, String>) getContext().getWllSimOrderMap()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Issac Closeout Request Failed [" + e.getMessage() + "]",
            Reason.SERVICE_ERROR, e, getContext());
      }
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_WORK_ORDER_COMLETTION);
   }

   @Given("^a provisioned ngfw subscriber in BBNMS$")
   public void fetchProvisionedBanOfNgfwType()
   {
      logger.info("Step:a provisioned ngfw subscriber in BBNMS");
      fetchProvisionedBanOfNgfwType(StepConstants.WLL_SUB_TRANSPORT_TYPE_NEXTGEN);

   }

   private void fetchProvisionedBanOfNgfwType(String subTransportType)
   {
      if (getContext().isPurgedOrderContext())
      {
         logger.info("Context has purged order details . New PR-NA order will not be placed");
         return;
      }
      try
      {
         List<String> banAndServiceId = fixedWlService.getWllBanAndServiceFromWllSubscriptions(subTransportType);
         if (banAndServiceId != null && banAndServiceId.size() == 2)
         {
            getContext().setBan(banAndServiceId.get(0));
            getContext().setServiceId(banAndServiceId.get(1));
         }
         else
         {
            throw new AutomationBDDCucumberException("Ban and ServiceId Not found in OL",
               AutomationBDDCucumberException.Reason.RECORD_NOT_FOUND, getContext());
         }
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to fetch Ban for subTransportType  [NextGen]",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   @When("^BBNMS accepts wll oms order$")
   public void flowOmsOrder()
   {
      try
      {
         if (isMoveOrder())
         {
            java.util.Map<String, String> valueMap = fixedWlService.updateIccidAndImsiForCeaseMoveOrder(getContext()
               .getBan());
            fixedWlService.removeDetailsFromCsiscForTn(valueMap.get(StepConstants.ATTRIBUTE_TN));
            fixedWlService.updateCsiscforMoveOrder(valueMap);
         }
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to perform pre-setup for PV order ", Reason.SERVICE_ERROR, e,
            getContext());
      }
      sendOrderRequest(StepConstants.OMS_REQ_PT);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      sendOrderRequest(StepConstants.OMS_REQ_CPE);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_DISPATCH_TICKET);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_EXECUTE_AHL);
   }

   @And("^Get Imei Iccid for the account$")
   public void getImeiIccidforCease() throws AutomationBDDServiceException
   {
      java.util.Map<String, String> valueMap = fixedWlService.updateIccidAndImsiForCeaseMoveOrder(getContext()
         .getBan());

   }

   @When("^BBNMS accepts the (suspend|resume) order$")
   public void flowSuspendResumeOrder()
   {

      sendOrderRequest(StepConstants.OMS_REQ_BAN_TREATMENT);

      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_WLL_BAN_TREATEMENT_REQ_FROM_OMS);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_CROSS_PRODUCT_VALIDATION);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SUSPEND_RESUME_SERVICE);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_INITITATE_SUSPEND_RESUME_REQ);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_BAN_TREATEMENT_COMPLETION_TO_OMS);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_PURGE_ORDER);
   }


   @Then("^resume order successfully gets completed in BBNMS$")
   public void validateResumeOrderIsSuccess()
   {
      try
      {
         assertTrue(fixedWlService.validateWllSubscriberResumedStatus(getContext().getBan()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate resume order status", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @Given("^a suspend subscriber exists in BBNMS$")
   public void fetchSuspendSubscriberFromOlDb()
   {
      try
      {
         List<String> banServiceId = fixedWlService.getWllBanServiceIdForSuspendedOrder();
         assertNotNull(banServiceId);
         assertEquals(2, banServiceId.size());
         getContext().setBan(banServiceId.get(0));
         getContext().setServiceId(banServiceId.get(1));

         logger.info("Suspended Ban :" + getContext().getBan() + " , Service Id :" + getContext().getServiceId());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to get Ban amd ServiceId for Supspend order",
            Reason.SERVICE_ERROR, e, getContext());
      }
      catch (java.lang.AssertionError e)
      {
         throw new AutomationBDDCucumberException("Failed to get Ban amd ServiceId for Supspend order",
            Reason.ASSERT_EXCEPTION, e, getContext());
      }
   }


   @Then("^suspend order successfully gets completed in BBNMS$")
   public void validateSuspendOrderIsSuccess()
   {
      try
      {
         assertTrue(fixedWlService.validateWllSubscriberSuspendedStatus(getContext().getBan()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate Supspend order status", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }


   @When("^AHL runs and generates the appropriate WOLI for the NGFW service$")
   public void validateOrderSucceedsAtExecuteAhl()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_DISPATCH_TICKET);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_EXECUTE_AHL);
   }


   @Then("^WOLI (.+) shall be sent on the message to CSI-WF$")
   public void validateWoliInCsiWfRequest(String productLine)
   {
      try
      {
         assertTrue(fixedWlService.validateWoliInCsiWfRequest(getContext().getOrigOrderId(), productLine));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate Woli Request status for[" + productLine + "]",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^NTI (.+) shall be sent on the message to CSI-WF$")
   public void validateSubTransportTypeInCsiRequest(String subTransportType)
   {
      if (subTransportType.equals(StepConstants.WLL_SUB_TRANSPORT_TYPE_BLANK))
      {
         subTransportType = "";
      }
      try
      {
         assertTrue(fixedWlService.validateSubTransportTypeFromEih(getContext().getOrigOrderId(), subTransportType));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate subTransportType[" + subTransportType
            + "] in EIH ", Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Given("^dispatch is specified on the order$")
   public void setDispatchOrder()
   {
      assertTrue(true);
   }

   @When("^CSI-WF interface creates the dispatch ticket$")
   public void validateWfSucceedsCdtActivity()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_DISPATCH_TICKET);
   }

   @When("^order fails in BBNMS$")
   public void validateOrderFailsInBbnms()
   {
      /* send just PT and CPE the order will fail at owa installation. */
      sendOrderRequest(StepConstants.OMS_REQ_PT);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);

      sendOrderRequest(StepConstants.OMS_REQ_CPE);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);

      bbnmsActivityValidationSteps.validateTaskFailedInOL(StepConstants.TASK_RECEIVE_OWA_INSTALLATION);

   }

   @Then("^BBNMS sends (.+) on notifyFIRSTfalloutProvision exception request to first$")
   public void validateSubTransportTypeOnFirstException(String subTransportType)
   {
      if (subTransportType.equals(StepConstants.WLL_SUB_TRANSPORT_TYPE_BLANK))
      {
         subTransportType = "";
      }
      try
      {
         assertTrue(fixedWlService.validateSubTransportTypeInFirstException(getContext().getOrigOrderId(),
            subTransportType));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException(
            "Failed to validate  notifyFIRSTfalloutProvision exception for subTransportType[" + subTransportType + "] ",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @And("^BBNMS accepts a valid wll publish rg notification$")
   public void sendValidPurRgRequest()
   {
      getContext().setOrderPubRGXmlReq(fixedWlService.createWllPubRgNotification(getContext().getBan(), getContext()
         .getOrigOrderId(), getContext().getOrderActionType(), getContext().getServiceId()));
      try
      {
         fixedWlService.sendWllOmsPubRgRequest(getContext().getOrderPubRGXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to send PubRG Request", Reason.SERVICE_ERROR, e,
            getContext());
      }
      if (isMoveOrder())
      {
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_QUERY_CMS_FOR_OWA_INFO);
      }
      else
      {
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_NAD_INFO);
      }
   }

   @And("^BBNMS accepts an invalid wll publish rg notification$")
   public void sendInValidPurRgRequest()
   {
      getContext().setOrderPubRGXmlReq(fixedWlService.createInvalidWllPubRgNotification(getContext().getBan(),
         getContext().getOrigOrderId(), getContext().getOrderActionType(), getContext().getServiceId()));
      try
      {
         fixedWlService.sendWllOmsPubRgRequest(getContext().getOrderPubRGXmlReq());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to send PubRG Request", Reason.SERVICE_ERROR, e,
            getContext());
      }
      bbnmsActivityValidationSteps.validateTaskFailedInOL(StepConstants.TASK_RECEIVE_NAD_INFO);
   }

   @And("^OWA installation succeeds in ol$")
   public void validateSuccessfulOwlInstallation()
   {
      try
      {
         generateImeiIccidImsiAndUpdateSimResponse();

         /*
          * Check if Cease Move Order completed before sending Process request for Provide Move orders
          */
         if (isMoveOrder())
         {
            logger.info("Check Order Status for Cease Move Order :" + getContext().getCeaseMoveOrigOrderId());
            bbnmsActivityValidationSteps.validateTaskSuccedsInOL(getContext().getCeaseMoveOrigOrderId(),
               StepConstants.TASK_COMPLETE_MOVE_CEASE_ORDER);
         }

         bbnmsActivityValidationSteps.validateTaskInProgressInOL(StepConstants.TASK_WAIT_FOR_OWA_INSTALLATION);

         fixedWlService.sendWllIssacRequest(fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
            getContext().getBan(), StepConstants.ISSACT_REQ_PROCESS, getContext().getSubTransportType(), getContext()
               .getWllSimOrderMap()));
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_OWA_INSTALLATION);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to perform OWA installation", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @And("^OWA installation fails in ol$")
   public void validateFailedOwlInstallation()
   {
      try
      {
         getContext().setWllSimOrderMap(fixedWlService.generateInvalidIccidImeiImsi());
         java.util.Map<String, String> simValueMap = fixedWlService.createAndUpdateSimResponse(getContext()
            .getOrderActionType(), getContext().getOrderXmlReq(), getContext().getWllSimOrderMap());

         getContext().setTn(simValueMap.get(StepConstants.ATTRIBUTE_TN));
         /*
          * Check if Cease Move Order completed before sending Process request for Provide Move orders
          */
         if (isMoveOrder())
         {
            logger.info("Check Order Status for Cease Move Order :" + getContext().getCeaseMoveOrigOrderId());
            bbnmsActivityValidationSteps.validateTaskSuccedsInOL(getContext().getCeaseMoveOrigOrderId(),
               StepConstants.TASK_COMPLETE_MOVE_CEASE_ORDER);
         }

         fixedWlService.sendWllIssacRequest(fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
            getContext().getBan(), StepConstants.ISSACT_REQ_PROCESS, getContext().getSubTransportType(), getContext()
               .getWllSimOrderMap()));
         bbnmsActivityValidationSteps.validateTaskFailedInOL(StepConstants.TASK_WAIT_FOR_OWA_INSTALLATION);
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate  OWA installation", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @And("^Service Activation succeeds in ol$")
   public void validateServiceActivation()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WLL_SERVICE_ACTIVAION);
   }

   @And("^Service Activation fails in ol$")
   public void validateServiceActivationFail()
   {
      try
      {
         fixedWlService.removeDetailsFromCsiscForTn(getContext().getTn());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to remove details from Csisc for TN [" + getContext().getTn()
            + "]", Reason.SERVICE_ERROR, e, getContext());
      }
      bbnmsActivityValidationSteps.validateTaskFailedInOL(StepConstants.TASK_WLL_SERVICE_ACTIVAION);
   }

   @When("^WF invokes the switch control interface$")
   public void wfMovesTillScActivation() throws AutomationBDDServiceException
   {
      if (isChangeOrder())
      {
         getContext().getOrderXmlReq().put("tnPrefix", "3");
         fixedWlService.createAndUpdateSimScRespOnly(getContext().getOrderActionType(), getContext().getOrderXmlReq(),
            getContext().getWllSimOrderMap());
      }
      sendOrderRequest("PT");
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
      sendOrderRequest("CPE");

      if (isMoveOrder())
      {
         java.util.Map<String, String> valueMap = fixedWlService.updateIccidAndImsiForCeaseMoveOrder(getContext()
            .getBan());
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(getContext().getCeaseMoveOrigOrderId(),
            StepConstants.TASK_COMPLETE_MOVE_CEASE_ORDER);
      }

      if (isMoveOrder() || isProvideOrder())
      {
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
         getContext().setWllSimOrderMap(fixedWlService.generateIccidImeiImsi());
         fixedWlService.createAndUpdateSimResponse(getContext().getOrderActionType(), getContext().getOrderXmlReq(),
            getContext().getWllSimOrderMap());
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_DISPATCH_TICKET);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_EXECUTE_AHL);
         getContext().setOrderPubRGXmlReq(fixedWlService.createWllPubRgNotification(getContext().getBan(), getContext()
            .getOrigOrderId(), getContext().getOrderActionType(), getContext().getServiceId()));
         fixedWlService.sendWllOmsPubRgRequest(getContext().getOrderPubRGXmlReq());
         fixedWlService.sendWllIssacRequest(fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
            getContext().getBan(), StepConstants.ISSACT_REQ_PROCESS, getContext().getSubTransportType(), getContext()
               .getWllSimOrderMap()));
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_OWA_INSTALLATION);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_NAD_INFO);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WLL_SERVICE_ACTIVAION);

      }
      else if (isCeaseOrder())
      {
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_PONR);
         sendOrderRequest("PTC");
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQUEST_2ND_CALL);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WLL_SERVICE_DEACTIVAION);
      }
      else
      {

         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_EXCHANGE_CPE_DATA_1ST_CALL);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_SEND_DISPATCH_TICKET);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_EXECUTE_AHL);
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WLL_SERVICE_ACTIVAION);

      }
   }

   @Then("^switch control ManageWLLProvisioningRequest sends the following attributes and values:$")
   public void validateManageProvRequestAttributes(DataTable table)
   {
      List<List<String>> attributeList = table.asLists(String.class);
      try
      {
         assertTrue(fixedWlService.validateAPNValuesInSwitchControlRequestForNgFw(attributeList.get(1), getContext()
            .getOrigOrderId()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate ManageWLLProvisioningRequest attributes",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^serviceTransactionType is (.+) in ManageWLLProvisioningRequest$")
   public void validateServiceTransactionType(String serviceTransactionType)
   {
      String xpathKey = XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_SERVICETRANSACTIONTYPE;
      try
      {
         assertTrue(fixedWlService.validateManageWLLProvisioningRequestAttr(serviceTransactionType, getContext()
            .getOrigOrderId(), xpathKey));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate ManageWLLProvisioningRequest request attributes",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^ntiType is (.+) ntiValue is (.+) in ManageWLLProvisioningRequest$")
   public void validateNtiTypeAndNtiValueInScRequest(String ntiType, String ntiValue)
   {
      try
      {
         assertTrue(fixedWlService.validateManageWLLProvisioningRequestAttr(ntiType, getContext().getOrigOrderId(),
            XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_NTITYPE));
         assertTrue(fixedWlService.validateManageWLLProvisioningRequestAttr(ntiValue, getContext().getOrigOrderId(),
            XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_NTIVALUE));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate ManageWLLProvisioningRequest request attributes",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^gNetworkType value is (.+) in provisionWLLRequest$")
   public void validateNetworkTypeInG2ProvRequest(String gNetworkType)
   {
      try
      {
         assertTrue(fixedWlService.validateProvisionWLLRequestAttr(gNetworkType, getContext().getOrigOrderId(),
            XpathConstants.XPATH_EIH_G2_PROVWLL_REQ_NETWORKTYPE));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate provision wll request", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @When("^BBNMS receives a closeout from ISAAC$")
   public void sendCloseoutReqToBbnms()
   {
      try
      {
         fixedWlService.sendWllIssacRequest(fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
            getContext().getBan(), StepConstants.ISSACT_REQ_CLOSEOUT, getContext().getSubTransportType(), getContext()
               .getWllSimOrderMap()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to send Closeout request", Reason.SERVICE_ERROR, e,
            getContext());
      }
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_WORK_ORDER_COMLETTION);
   }

   @When("^EDW batch file is searched for subTransportType$")
   public void fetchOrderIdFromBatchFile()
   {
      getContext().setOrigOrderId(fixedWlService.fetchNextGenBanFromBatchFile());
      assertNotNull(getContext().getOrigOrderId());
   }


   @Then("^(.+) is found in EDW batch file$")
   public void validateIfOrerIsNextGen(String subTransportType)
   {
      try
      {
         assertTrue(fixedWlService.validateSubTransPortTypeFromSubscriptions(getContext().getOrigOrderId(),
            subTransportType));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to validate subtrasport type in batch file",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   private void generateImeiIccidImsiAndUpdateSimResponse()
   {

      try
      {
         getContext().setWllSimOrderMap(fixedWlService.generateIccidImeiImsi());
         java.util.Map<String, String> simValueMap = fixedWlService.createAndUpdateSimResponse(getContext()
            .getOrderActionType(), getContext().getOrderXmlReq(), getContext().getWllSimOrderMap());
         getContext().setTn(simValueMap.get(StepConstants.ATTRIBUTE_TN));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to Generate Imei/Imsi/Iccid and update Sim Response",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }


   @And("^ManageWLLInstallationDetails request does not contains the following fields:$")
   public void updateProcessRequest(DataTable dataTable)
   {

      generateImeiIccidImsiAndUpdateSimResponse();

      /*
       * Check if Cease Move Order completed before sending Process request for Provide Move orders
       */
      if (isMoveOrder())
      {
         logger.info("Check Order Status for Cease Move Order :" + getContext().getCeaseMoveOrigOrderId());
         bbnmsActivityValidationSteps.validateTaskSuccedsInOL(getContext().getCeaseMoveOrigOrderId(),
            StepConstants.TASK_COMPLETE_MOVE_CEASE_ORDER);
      }

      Map<String, String> processReqOrderMap = fixedWlService.createWllIssacRequest(getContext().getOrigOrderId(),
         getContext().getBan(), StepConstants.ISSACT_REQ_PROCESS, getContext().getSubTransportType(), getContext()
            .getWllSimOrderMap());

      List<String> processReqFields = dataTable.asList(String.class);

      if (processReqOrderMap != null && processReqFields != null)
      {

         for (String entry : processReqFields)
         {
            processReqOrderMap.put(entry, "");
         }
      }

      getContext().setProcessReqOrderReq(processReqOrderMap);
   }

   @And("^BBNMS receives ManageWLLInstallationDetails Process request$")
   public void sendAndValidateManageWLLInstallationDetailsProcessReq()
   {
      try
      {
         getContext().setWllProcessResponse(fixedWlService.sendWllIssacRequest(getContext().getProcessReqOrderMap()));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to send ManageWLLInstallationDetails Process request",
            Reason.SERVICE_ERROR, e, getContext());
      }
   }

   @Then("^CSIIN interface rejects the request with reason:$")
   public void validateProcessReqFailed(DataTable dTable)
   {
      List<String> reasons = dTable.asList(String.class);

      if (reasons != null && !reasons.isEmpty())
      {
         for (int i = 1; i < reasons.size(); i++)
         {
            assertTrue(getContext().getWllProcessResponse().contains(reasons.get(i)));
         }
      }
   }

   @Then("^CSIIN interface accepts the request$")
   public void validateProcessReqAccepted()
   {
      assertTrue(getContext().getWllProcessResponse().contains(StepConstants.WLL_PROCESS_SUCCESS_RESPOSNE_MSG));
   }

   @And("^Process request has attribute (.+) as (.+)$")
   public void updateProcessRequestAttribute(String attribute, String value)
   {
      if (getContext().getProcessReqOrderMap() == null)
      {
         assertTrue(false);
      }
      getContext().getProcessReqOrderMap().put(attribute, value);
   }

   @When("^wllproductvalidationinfo table is queried for make and model with nti as (.+) and subtransporttype is (.+)$")
   public List<String> validateMakeModelFromWllproductvalidationinfo(String nti, String subtransporttype)
   {


      if ("null".equals(subtransporttype))
      {
         subtransporttype = null;
      }

      List<String> queryResult;
      try
      {
         queryResult = fixedWlService.validatemakeModelByWllproductvalidationinfo(nti, subtransporttype);
         assertNotNull(queryResult);
         assertEquals(2, queryResult.size());
         StateContext context = getContext();
         context.put("make", queryResult.get(0));
         context.put("model", queryResult.get(1));

         return queryResult;
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to Validate Make Model for Product", Reason.SERVICE_ERROR, e,
            getContext());
      }

   }

   @Then("^make is (.+)$")
   public void validateMake(String make)
   {

      assertTrue(getContext().get("make").equals(make));

   }

   @And("^model is (.+)$")
   public void validateModel(String model)
   {

      assertTrue(getContext().get("model").equals(model));

   }

   @When("^Process request has a new imei value$")
   public void updateImeiInProcessReq()
   {
      if (getContext().getProcessReqOrderMap() == null)
      {
         assertTrue(false);
      }
      try
      {
         getContext().getProcessReqOrderMap().put("imei", fixedWlService.generateImei());
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Failed to generate IMEI number", Reason.SERVICE_ERROR, e,
            getContext());
      }
   }

   @Given("^a ngfw (.+)-(.+) order succeeded at wll service activation$")
   public void createAndFlowOrderTillScActivation(String orderType, String orderSubType)
      throws AutomationBDDServiceException
   {
      setUpFwOrder(orderType, orderSubType);
      setTransportType("NextGen");
      flowOmsOrder();
      validateSuccessfulOwlInstallation();
      validateServiceActivation();
      sendValidPurRgRequest();
   }

   @When("^BBNMS accepts a cancel on the (.+) order$")
   public void createAndSendCancelOrder(String orderType) throws AutomationBDDServiceException
   {
      getContext().setOrderType(orderType + "CA");
      getContext().getOrderXmlReq().put("orderActionSubType", "CA");
      getContext().getOrderXmlReq().put("orderActionReferenceNumber", "C");
      setTransportType("NextGen");
      sendOrderRequest(StepConstants.OMS_REQ_PT);

   }

   @Then("^cancel flow gets initiated$")
   public void validateCancelFlowInitiated()
   {
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_RECEIVE_TRANSPORT_REQ_FROM_OMS);
   }

   @When("^Deactivation service activation task succeeds in ol$")
   public void flowCancelOrderTillDeactivation() throws AutomationBDDServiceException
   {
      sendOrderRequest(StepConstants.OMS_REQ_PTC);
      bbnmsActivityValidationSteps.validateTaskSuccedsInOL(StepConstants.TASK_WLL_SERVICE_DEACTIVAION);
   }

   @When("^BBNMS accepts a retrieveProvisioningStatusInformation request from FIRST-RT with (.+)$")
   public void validateFirstRtRetrieveProvisioningStatusInformationRequest(String input)
      throws AutomationBDDServiceException
   {

      if ("omsOrderNumber".equals(input))
      {
         getContext().setWllFirstRtResponse(fixedWlService.sendWllRetrieveProvisioningStatusInformationRequest(
            fixedWlService.createWllRetrieveProvisioningStatusInformationRequestMap("", getContext()
               .getOrderNumber())));

      }
      else if ("Ban".equals(input))
      {
         logger.info("inside ban case");
         logger.info("ban is" + getContext().getBan());
         getContext().setWllFirstRtResponse(fixedWlService.sendWllRetrieveProvisioningStatusInformationRequest(
            fixedWlService.createWllRetrieveProvisioningStatusInformationRequestMap(getContext().getBan(), "")));
      }

   }

   @Then("^BBNMS sends (.+) on retrieveProvisioningStatusInformation response$")
   public void sendSubtransporttypeOnFirstRtResponse(String subtransporttype)
   {

      logger.info("Subtransport type is sent to FirstRt Response " + subtransporttype);

      if ("Blank".equals(subtransporttype))
      {
         subtransporttype = "";
      }

      assertTrue(getContext().getWllFirstRtRepsonse().contains(StepConstants.WLL_FIRST_RT_SUCCESS_RESPONSE_TAGNAME));
      assertTrue(getContext().getWllFirstRtRepsonse().contains(StepConstants.WLL_FIRST_RT_SUCCESS_RESPONSE_TAGVALUE));
   }
@When("^BBNMS accepts (.+) request$")
//BBNMS accepts PTC request
public void sendOmsReq(String msgType)
{
   logger.info("Sending "+msgType+" for the order");
   sendOrderRequest("PTC");
}
}
