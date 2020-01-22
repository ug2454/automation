package com.att.bbnmstest.services.exception;

public class RequestDispatcherServiceException extends AutomationBDDServiceException
{

   public RequestDispatcherServiceException(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getDescription()
   {
      return "Exception while making external system/interface calls";
   }

}
