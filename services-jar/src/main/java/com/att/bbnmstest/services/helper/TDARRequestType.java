package com.att.bbnmstest.services.helper;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.att.bbnmstest.services.dispatcher.RequestDispatcher;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;


public enum TDARRequestType
{

   Initiate
   {
      @Override
      public void sendIssacRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside Initiate Request");
         orderMap.put(ORDERACTIONMODE, name());
         adapter.sendRequest(orderMap, template);
      }
   },
   Process
   {

      @Override
      public void sendIssacRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside Process Request");
         orderMap.put(ORDERACTIONMODE, name());
         adapter.sendRequest(orderMap, template);
      }

   },
   Swap
   {

      @Override
      public void sendIssacRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside Swap Request");
         orderMap.put(ORDERACTIONMODE, name());
         adapter.sendRequest(orderMap, template);
      }

   },
   Closeout
   {
      @Override
      public void sendIssacRequest(RequestDispatcher adapter, Map orderMap, String template)
         throws AutomationBDDServiceException
      {
         logger.info("Inside Closeout Request");
         orderMap.put(ORDERACTIONMODE, name());
         adapter.sendRequest(orderMap, template);
      }
   };
   private final static Logger logger = Logger.getLogger(TDARRequestType.class);

   public static final String ORDERACTIONMODE = "orderActionMode";
   public static final String Req_Type_Initiate = "Initiate";
   public static final String Req_Type_Swap = "Swap";
   public static final String Req_Type_Process = "Process";
   public static final String Req_Type_Closeout = "Closeout";

   public void sendIssacRequest(RequestDispatcher adapter, Map orderMap, String template)
      throws AutomationBDDServiceException
   {
      throw new UnsupportedOperationException("TDAR RequestType is not supported.");
   }

   public static TDARRequestType getRequestType(String name)
   {
      try
      {
         return TDARRequestType.valueOf(StringUtils.upperCase(name));
      }
      catch (IllegalArgumentException e)
      {
         logger.info("UnSupported Request Type : " + name);
         throw e;
      }
   }
}
