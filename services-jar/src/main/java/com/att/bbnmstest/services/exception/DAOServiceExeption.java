package com.att.bbnmstest.services.exception;

public class DAOServiceExeption extends AutomationBDDServiceException
{

   public DAOServiceExeption(String message)
   {
      super(message);
   }

   public DAOServiceExeption(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getDescription()
   {
      return "Exception while performing DB operations";
   }

}
