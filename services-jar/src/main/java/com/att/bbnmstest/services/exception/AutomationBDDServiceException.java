package com.att.bbnmstest.services.exception;


public abstract class AutomationBDDServiceException extends Exception
{


   /**
    * 
    */
   private static final long serialVersionUID = 6613090085814922315L;

   public AutomationBDDServiceException(String message)
   {
      this(message, null);
   }

   public AutomationBDDServiceException(String message, Exception e)
   {
      super(message, e);
   }

   abstract public String getDescription();

   @Override
   public String getMessage()
   {
      return getDescription() + " Exception [" + super.getMessage() + "]";
   }

}
