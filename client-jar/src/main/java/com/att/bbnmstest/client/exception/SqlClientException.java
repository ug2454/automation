package com.att.bbnmstest.client.exception;

public class SqlClientException extends ClientException
{

   private static final String CLIENT_TYPE = "SQL Client";

   public SqlClientException(String message)
   {
      super(message, null);
   }

   public SqlClientException(String message, Exception e)
   {
      super(message, e);
   }

   @Override
   public String getClientType()
   {
      return CLIENT_TYPE;
   }

}
