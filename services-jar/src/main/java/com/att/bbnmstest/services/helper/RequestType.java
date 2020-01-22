package com.att.bbnmstest.services.helper;

import java.util.Map;

import org.apache.log4j.Logger;

import com.att.bbnmstest.services.dispatcher.RequestDispatcher;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;


public enum RequestType
{

   PT
   {
      @Override
      public void sendOmsRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside PT Switch Case");
         orderMap.put(EnvConstants.TAG_TASKNAME, "provisionTransport");
         adapter.sendRequest(orderMap, template);
      }
   },
   CPE
   {

      @Override
      public void sendOmsRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside CPE Switch Case");
         orderMap.put(EnvConstants.TAG_TASKNAME, "exchangeCpeDataCall1");
         adapter.sendRequest(orderMap, template);
      }

   },
   PTC
   {
      @Override
      public void sendOmsRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside PTC Switch Case");
         orderMap.put(EnvConstants.TAG_TASKNAME, "provisionTransportComplete");
         adapter.sendRequest(orderMap, template);
      }
   },
   MPC
   {
      @Override
      public void sendOmsRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside MPC Switch Case");

      }
   },
   BanTreatment
   {
      @Override
      public void sendOmsRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Send BanTreatment request");
         orderMap.put(EnvConstants.TAG_TASKNAME, "BANTreatment");
         adapter.sendRequest(orderMap, template);
      }
   };

   private final static Logger logger = Logger.getLogger(RequestType.class);


   public void sendOmsRequest(RequestDispatcher adapter, Map<String, String> orderMap, String template)
      throws AutomationBDDServiceException
   {
      throw new UnsupportedOperationException("RequestType is not supported.");
   }

}
