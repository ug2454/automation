package com.att.bbnmstest.client.exception;

public class ShellClientException extends ClientException
{

   private static final String CLIENT_TYPE = "Shell Client";

   public ShellClientException(String message)
   {
      super(message, null);
   }

   public ShellClientException(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getClientType()
   {
      return CLIENT_TYPE;
   }

}
