package com.att.bbnmstest.client.exception;

public class HttpClientException extends ClientException
{

   private static final String CLIENT_TYPE = "Http Client";

   public HttpClientException(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getClientType()
   {
      return CLIENT_TYPE;
   }

}
