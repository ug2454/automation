package com.att.bbnmstest.client.exception;


public abstract class ClientException extends RuntimeException
{

   public ClientException(String message)
   {
      this(message, null);
   }

   public ClientException(String message, Exception e)
   {
      super(message, e);
   }

   abstract public String getClientType();

   @Override
   public String getMessage()
   {
      return getClientType() + " Exception [" + super.getMessage() + "]";
   }
}
