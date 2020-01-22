package com.att.bbnmstest.services;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.utils.Wait;
import com.att.bbnmstest.services.dao.AssetDAO;
import com.att.bbnmstest.services.dao.CimDAO;
import com.att.bbnmstest.services.dao.CmsDAO;
import com.att.bbnmstest.services.dao.OrchestrationLayerDAO;
import com.att.bbnmstest.services.dispatcher.DmaapDispatcher;
import com.att.bbnmstest.services.dispatcher.DmaapKafkaDispatcher;
import com.att.bbnmstest.services.dispatcher.OMSDispatcher;
import com.att.bbnmstest.services.dispatcher.OMSJmsDispatcher;
import com.att.bbnmstest.services.dispatcher.OmsMilestoneDispatcher;
import com.att.bbnmstest.services.dispatcher.RequestDispatcher;

import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.helper.SupplierPredicateEnum;
import com.att.bbnmstest.services.model.Order;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.Utils;


@Component
@Qualifier("BBNMSService")
public class BBNMSService
{

   private final static Logger logger = Logger.getLogger(BBNMSService.class);


   @Autowired
   private DmaapKafkaDispatcher kafkaDmappAdapter;

   @Autowired
   private OrchestrationLayerDAO olDao;
   
   @Autowired
   private AssetDAO assetDao;

   @Autowired
   private CimDAO cimDao;

   @Autowired
   CmsDAO cmsDao;

   @Autowired
   private DmaapDispatcher httpDmaapAdapter;

   @Autowired
   private PropUtil propUtil;

   @Autowired
   private OMSDispatcher omsAdapter;

   @Autowired
   private OMSJmsDispatcher omsJmsAdapter;

   @Autowired
   private OmsMilestoneDispatcher omsMilestoneAdapter;


   public void seekToEndDmaapKafkaClient()
   {
      getKafkaDmappAdapter().seekToEnd();
   }

   public RequestDispatcher getOmsAdapter()
   {

      if (System.getProperty(EnvConstants.ENV_USE_OMS_JMS_ADAPTER) != null && System.getProperty(
         EnvConstants.ENV_USE_OMS_JMS_ADAPTER).equalsIgnoreCase("true"))
      {
         logger.info("Calling JMS addapter ");
         return omsJmsAdapter;
      }
      logger.info("Calling for Http Adapter");

      return omsAdapter;
   }

   public DmaapKafkaDispatcher getKafkaDmappAdapter()
   {
      return kafkaDmappAdapter;
   }

   public DmaapDispatcher getHttpDmaapAdapter()
   {

      return this.httpDmaapAdapter;
   }

   public OmsMilestoneDispatcher getOmsMilestoneAdapter()
   {

      return this.omsMilestoneAdapter;
   }

   public OrchestrationLayerDAO getOlDao()
   {
      return this.olDao;
   }

   public CimDAO getCimDao()
   {
      return this.cimDao;
   }

   public CmsDAO getCmsDao()
   {
      return this.cmsDao;
   }
   
   public AssetDAO getAssetDao() {
	   return this.assetDao;
   }


   public Map<String, String> generateUniqueOrdernumberAndOriginalOrderId() throws AutomationBDDServiceException
   {

      Map<String, String> valuesMap = new HashMap<String, String>();

      try
      {
         String maxFlowableId = getOlDao().getMaxFlowableIdFromAbstractFlow(propUtil.getProperty(
            EnvConstants.ORDER_SERIES));
         String orderNumber = getUniqueOrderNumber(maxFlowableId);
         String origOrdId = getUniqueOrderNumber(orderNumber);

         valuesMap.put(EnvConstants.TAG_ORDER_NUMBER, orderNumber);
         valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);

      }
      catch (AutomationBDDServiceException e)
      {
         throw e;
      }

      return valuesMap;

   }

   public String getUniqueOrderNumber(String maxFlowable) throws AutomationBDDServiceException
   {
      try
      {
         boolean notFound = true;
         int orederNum = Integer.parseInt(maxFlowable) + 1;
         String uniqueOrder = null;

         do
         {

            String servOrdFromHistMsg = getOlDao().getMaxServiceOrderFromHistoryMessage(String.valueOf(orederNum));
            String orderNumFromEihAll = getOlDao().getMaxOrderNumberFromEihAll(String.valueOf(orederNum));

            if (StringUtils.isNotEmpty(servOrdFromHistMsg) || StringUtils.isNotEmpty(orderNumFromEihAll))
            {
               notFound = true;
               orederNum = orederNum + 1;
            }
            else
            {
               notFound = false;
               uniqueOrder = String.valueOf(orederNum);
            }

         } while (notFound);

         return uniqueOrder;

      }
      catch (AutomationBDDServiceException e)
      {
         throw e;
      }
   }

   public Map<String, String> createUniqueOrdernumberAndOriginalOrderId(String identifier)
      throws AutomationBDDServiceException
   {

      try
      {
         Map<String, String> valuesMap = new HashMap<String, String>();
         String orderNumber = createUniqueOrderNumber(identifier, Integer.parseInt(propUtil.getProperty(
            EnvConstants.MAX_BAN_LENGTH)));
         /* sleep operation is needed to generated unique order id as prefix is generated based on current time */
         Thread.sleep(1);
         String origOrdId = createUniqueOrderNumber(identifier, Integer.parseInt(propUtil.getProperty(
            EnvConstants.MAX_BAN_LENGTH)));

         valuesMap.put(EnvConstants.TAG_ORDER_NUMBER, orderNumber);
         valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);

         return valuesMap;
      }
      catch (InterruptedException e)
      {
         throw new AutomationBDDServiceException("Erro while generating unique orderActionId/orderNumber")
         {
            @Override
            public String getDescription()
            {
               return "Unsupported NTI Exception";
            }

         };
      }


   }

   public String createUniqueOrderNumber(String identifier, int uniqueIdLength)
   {
      return Utils.generareIdWithPrefix(propUtil.getProperty(identifier), uniqueIdLength);
   }

   public Map<String, Boolean> getWorkflowActivitiesStatus(String origOrdActionId, List<String> workflowActivityList,
      String status) throws AutomationBDDServiceException
   {
      Map<String, Boolean> taskValMap = new HashMap<>();
      try
      {
         boolean isOrderFailed = isServiceOrderFailed(origOrdActionId);
         Iterator<String> itr = workflowActivityList.iterator();
         if (isOrderFailed)
         {
            StringBuilder buffer = new StringBuilder();
            for (String activity : workflowActivityList)
            {
               buffer.append("'").append(activity).append("'").append(",");
            }
            Map<String, String> values = getOrderStatus(origOrdActionId, StringUtils.substring(buffer.toString(), 0,
               StringUtils.lastIndexOf(buffer.toString(), ",")));

            if (MapUtils.isNotEmpty(values))
            {

               for (Map.Entry<String, String> s : values.entrySet())
               {

                  if (EnvConstants.STATUS_SUCCEEDED.equalsIgnoreCase(s.getValue()))
                  {
                     taskValMap.put(s.getKey(), true);
                  }
                  else
                  {
                     taskValMap.put(s.getKey(), false);
                  }
               }
            }
         }
         else
         {
            while (itr.hasNext())
            {
               String taskDescription = itr.next();
               boolean orderStatusAtDispatch = validateOrderStatusInOl(taskDescription, status, origOrdActionId);
               logger.info("Task[" + taskDescription + "] , Status [" + status + "] " + orderStatusAtDispatch);
               taskValMap.put(taskDescription, orderStatusAtDispatch);
            }

         }
      }
      catch (AutomationBDDServiceException e)
      {
         logger.error(e);
         throw e;
      }
      return taskValMap;
   }

   private boolean validateOrderStatusInOl(String taskDescription, String status, String origOrdActionId)
      throws AutomationBDDServiceException
   {

      boolean orderStatusAtDispatch = false;
      if (EnvConstants.STATUS_SUCCEEDED.equals(status))
      {
         orderStatusAtDispatch = isOrderSucceded(origOrdActionId, taskDescription);
      }
      else if (EnvConstants.STATUS_FAILED.equals(status))
      {
         orderStatusAtDispatch = isOrderFailed(origOrdActionId, taskDescription);
      }
      else
      {
         orderStatusAtDispatch = checkOrderStatus(origOrdActionId, taskDescription, status);
      }

      return orderStatusAtDispatch;
   }

   public boolean isServiceOrderFailed(String origOrdId) throws AutomationBDDServiceException
   {
      String status = getServiceOrderStatus(origOrdId);
      if (StringUtils.equalsIgnoreCase(status, EnvConstants.STATUS_FAILED))
      {
         return true;
      }
      return false;

   }

   public String getServiceOrderStatus(String ordernumber) throws AutomationBDDServiceException
   {
      logger.info("Service Order Status for Order : " + ordernumber);
      return getOlDao().getServiceOrderStatus(ordernumber);

   }

   public Map<String, String> getOrderStatus(String ordernumber, String taskDescription)
      throws AutomationBDDServiceException
   {
      logger.info("Get Order Status for ORder :" + ordernumber + ", For tasks:" + taskDescription);
      return getOlDao().getOrderTaskStatusByOrderNumerAndTask(ordernumber, taskDescription);
   }


   public boolean isOrderSucceded(String origOrdId, String taskDescription) throws AutomationBDDServiceException
   {
      return checkOrderStatus(origOrdId, taskDescription, EnvConstants.STATUS_SUCCEEDED);
   }

   public boolean isOrderFailed(String origOrdId, String taskDescription) throws AutomationBDDServiceException
   {
      return checkOrderStatus(origOrdId, taskDescription, EnvConstants.STATUS_FAILED);
   }

   public boolean checkOrderStatus(String origOrdId, String taskDescription, String status)
      throws AutomationBDDServiceException
   {
      Map<String, String> valuesMap = new HashMap<String, String>();
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);
      valuesMap.put(EnvConstants.TAG_TASK_DESC, taskDescription);
      SupplierPredicateEnum supplierPredicate = SupplierPredicateEnum.OrderStatus;
      return Wait.untilCondition(supplierPredicate.getSupplier(getOlDao(), valuesMap), supplierPredicate.getPredicate(
         status), 1, 5, MINUTES.toSeconds(3));

   }

   public boolean validateTaskSuccedsInOL(String origOrderId, String taskName) throws AutomationBDDServiceException
   {
      logger.info("Validate task [" + taskName + "] status  [" + EnvConstants.STATUS_SUCCEEDED + "]  in OL");
      return validateTaskStatusInOL(origOrderId, taskName, EnvConstants.STATUS_SUCCEEDED);
   }

   public boolean validateTaskFailedInOL(String origOrderId, String taskName) throws AutomationBDDServiceException
   {
      logger.info("Validate task [" + taskName + "] status  [" + EnvConstants.STATUS_FAILED + "]  in OL");
      return validateTaskStatusInOL(origOrderId, taskName, EnvConstants.STATUS_FAILED);
   }

   public boolean validateTaskInProgressInOL(String origOrderId, String taskName) throws AutomationBDDServiceException
   {
      logger.info("Validate task [" + taskName + "] status [" + EnvConstants.STATUS_IN_PROGRESS + "] in OL");
      return validateTaskStatusInOL(origOrderId, taskName, EnvConstants.STATUS_IN_PROGRESS);
   }

   public boolean validateTaskStatusInOL(String origOrderId, String taskName, String expectedStatus)
      throws AutomationBDDServiceException
   {
      logger.info("Validate task [" + taskName + "] task is " + expectedStatus + " in OL");
      boolean status = false;

      Map<String, Boolean> taskValidation = getWorkflowActivitiesStatus(origOrderId, Arrays.asList(taskName),
         expectedStatus);

      if (taskValidation.containsValue(false))
      {
         logger.info("Order is not " + expectedStatus + " at task : " + taskName);
         status = false;
      }
      else
      {
         logger.info("Order is " + expectedStatus + " at task : " + taskName);
         status = true;
      }

      logger.info("Step: " + taskName + " task " + expectedStatus + " in OL" + ", Status:" + status);
      return status;
   }

   public boolean validateOmsTaskSucceeds(String origOrdId, String messageTag)
   {
      logger.info("Validate OMS Task :" + messageTag + ", Orig Order Id :" + origOrdId);

      Map<String, String> valuesMap = new HashMap<String, String>();
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);
      valuesMap.put(EnvConstants.TAG_MESSAGE_TAG, messageTag);

      SupplierPredicateEnum supplierPredicate = SupplierPredicateEnum.OmsValidationStatus;

      return Wait.untilCondition(supplierPredicate.getSupplier(getOlDao(), valuesMap), supplierPredicate.getPredicate(
         EnvConstants.STATUS_SUCCESS), 1, 5, MINUTES.toSeconds(2));

   }


   public void updateEligibleToExecuteTimeForHoldForDelayTimerTask(String origOrderId, String taskId)
      throws AutomationBDDServiceException
   {
      logger.info("Update Eligible Time to execute for order  :" + origOrderId + ", Task Defination Id Id :" + taskId);
      int count = getOlDao().updateEligibleTimeForTask(origOrderId, taskId);
      if (count <= 0)
      {
         throw new AutomationBDDServiceException("Update for eligible time failed for orderActinId[" + origOrderId
            + "] for task[" + taskId + "]")
         {
            @Override
            public String getDescription()
            {
               return "BBNms Srvice Operation";
            }

         };
      }
   }

   public boolean validateOmsTaskFailed(String origOrdId, String messageTag)
   {
      logger.info("Validate OMS Task :" + messageTag + ", Orig Order Id :" + origOrdId);

      Map<String, String> valuesMap = new HashMap<String, String>();
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);
      valuesMap.put(EnvConstants.TAG_MESSAGE_TAG, messageTag);

      SupplierPredicateEnum supplierPredicate = SupplierPredicateEnum.OmsValidationStatus;

      return Wait.untilCondition(supplierPredicate.getSupplier(getOlDao(), valuesMap), supplierPredicate.getPredicate(
         EnvConstants.STATUS_FAIL), 1, 5, MINUTES.toSeconds(2));

   }


   public boolean validateEventStatusInDmapIntf(String origOrderId, List<List<String>> events, boolean hasHeaders)
      throws AutomationBDDServiceException
   {

      boolean result = true;

      List<Order> orders = getKafkaDmappAdapter().getOrders();

      logger.info("Orders returned:" + orders);

      for (int i = 0; i < events.size(); i++)
      {
         if (hasHeaders && i == 0)
         {
            continue;
         }
         boolean status = false;
         List<String> entry = events.get(i);

         String event_name = entry.get(0);
         String event_status = entry.get(1);

         for (Order order : orders)
         {
            if (origOrderId.equals(order.getBbnmsOrderNumber()))
            {

               if (order.getEventStatus().equals(event_status) && order.getEventName().equals(event_name)
                  && Order.MESSAGE_TYPE_EVENT.equals(order.getMessageType()))
               {
                  status = true;
                  break;
               }

            }

         }

         logger.info("Event Name:" + event_name + ", Event Status :" + event_status + ", Status: " + status);

         if (!status)
         {
            result = status;
         }

      }

      return result;
   }

   public List<String> validateStatusDescriptionMilestone(String wfId) throws AutomationBDDServiceException
   {
      logger.info("Validating for Workflow Id :" + wfId);
      Order order = getOmsMilestoneAdapter().getOrder(wfId);
      logger.info("Order returned:" + order);
      List<String> detailList = new ArrayList<>();
      detailList.add(order.getEventStatus());
      detailList.add(order.getEventStatusDescription());
      return detailList;
   }


   public boolean updateOlSettings(String activity, String value) throws AutomationBDDServiceException
   {
      return getOlDao().updateOlSettings(activity, value);
   }


   public boolean verifyOlDbIsUp() throws AutomationBDDServiceException
   {

      return getOlDao().verifyOlDb();

   }

}
