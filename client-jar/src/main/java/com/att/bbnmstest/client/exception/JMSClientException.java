package com.att.bbnmstest.client.exception;

public class JMSClientException extends ClientException
{

   private static final String CLIENT_TYPE = "JMS Client";

   public JMSClientException(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getClientType()
   {
      return CLIENT_TYPE;
   }

}
