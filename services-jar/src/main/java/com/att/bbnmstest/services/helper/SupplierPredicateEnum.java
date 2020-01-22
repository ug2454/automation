package com.att.bbnmstest.services.helper;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.att.bbnmstest.services.dao.OrchestrationLayerDAO;
import com.att.bbnmstest.services.util.EnvConstants;


public enum SupplierPredicateEnum
{

   OmsValidationStatus
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {

         return () -> {
            try
            {

               String status = olDao.getTransactionStatusByOrdernumberAndMsgTag(valuesMap.get(
                  EnvConstants.TAG_ORIG_ORDER_ACTION_ID), valuesMap.get(EnvConstants.TAG_MESSAGE_TAG));
               logger.info("OMS trans status = " + status);
               return status;
            }
            catch (Exception e)
            {
               return null;
            }
         };
      }

      @Override
      public Predicate<String> getPredicate(String expectedStatus)
      {
         return (status) -> {
            return (StringUtils.equalsIgnoreCase(status, expectedStatus));
         };
      }

   },

   OrderStatus
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {
         return () -> {
            try
            {

               Map<String, String> statusPerTask = olDao.getOrderTaskStatusByOrderNumerAndTask(valuesMap.get(
                  EnvConstants.TAG_ORIG_ORDER_ACTION_ID), "'" + valuesMap.get(EnvConstants.TAG_TASK_DESC) + "'");
               logger.info("Order status = " + statusPerTask);

               if (MapUtils.isNotEmpty(statusPerTask))
               {
                  Map.Entry<String, String> status = statusPerTask.entrySet().iterator().next();
                  return status.getValue();
               }
            }
            catch (Exception e)
            {
               return null;
            }
            return null;
         };
      }

      @Override
      public Predicate<String> getPredicate(String expectedStatus)
      {
         return (status) -> {
            return (StringUtils.equalsIgnoreCase(status, expectedStatus));
         };
      }
   },

   CsiWfCreateFiberServiceWorkOrderRequestValidation
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {
         return () -> {
            try
            {

               String attributeVal = olDao.getCsiWfReqValidation(valuesMap.get(EnvConstants.TAG_XPATH), valuesMap.get(
                  EnvConstants.TAG_ORIG_ORDER_ACTION_ID));
               logger.info("Order status = " + attributeVal);
               return attributeVal;
            }
            catch (Exception e)
            {
               return null;
            }
         };
      }

      @Override
      public Predicate<String> getPredicate(String status)
      {
         return (attributeVal) -> {
            return (StringUtils.equalsIgnoreCase(attributeVal, status));
         };
      }

   },
   L1Acknowlegement
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {
         return () -> {
            try
            {
               String correlationId = olDao.getL1Acknowlegment(valuesMap.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID),
                  valuesMap.get(EnvConstants.TAG_ORDERVERSION));
               logger.info("JMS CorrelationId = " + correlationId);
               return correlationId;
            }
            catch (Exception e)
            {
               return null;
            }
         };
      }

      @Override
      public Predicate<String> getPredicate()
      {
         return (correlationId) -> {
            return (StringUtils.isNotEmpty(correlationId));
         };
      }
   },
   MPCFLowableIdByBan
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {
         return () -> {
            try
            {
               String orderId = olDao.getMPCFlowableIDByBan(valuesMap.get(EnvConstants.TAG_BAN));
               logger.info("MPC Order Id = " + orderId);
               valuesMap.put("origOrdId", orderId);
               return orderId;
            }
            catch (Exception e)
            {
               return null;
            }

         };
      }

      @Override
      public Predicate<String> getPredicate()
      {
         return (orderId) -> {
            return (StringUtils.isNotEmpty(orderId));
         };
      }
   };
   private final static Logger logger = Logger.getLogger(SupplierPredicateEnum.class);


   public Predicate<String> getPredicate(String status)
   {
      throw new UnsupportedOperationException("getPredicate(String S) method not defined");
   }

   public Predicate<String> getPredicate()
   {
      throw new UnsupportedOperationException("getPredicate() method not defined");
   }

   public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, Map<String, String> valuesMap)
   {
      throw new UnsupportedOperationException("getSupplier() method not defined");
   }
}
