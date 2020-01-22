package com.att.bbnmstest.services.util;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.utils.EncryptDecryptUtil;


@Component
public class PropUtil
{
   @Autowired
   private Environment envProp;

   private static final Properties props = new Properties();


   public String getProperty(String key)
   {
      return (props.get(key) != null) ? props.getProperty(key) : envProp.getProperty(key);
   }


   public void setProperty(String key, String value)
   {
      props.setProperty(key, value);
   }

   public String getOMSMessageSenderUrl()
   {
      return getProperty(EnvConstants.OMS_MESSAGE_SENDER_URL);
   }

   public String getISSACReqMessageSenderUrl()
   {
      return getProperty(EnvConstants.ISSAC_MESSAGE_SENDER_URL);
   }

   public String getDbUrl(String system)
   {
      return getProperty("dbUrl." + system);

   }

   public String getDbUser(String system)
   {
      return getProperty("dbUser." + system);
   }


   public String getDbPass(String system) throws Exception
   {
      return getProperty("dbPass." + system);
   }

   public String getUnixHostName(String system)
   {
      return getProperty("unixhost.name." + system);
   }

   public String getUnixHostUser(String system)
   {
      return getProperty("unixhost.user." + system);
   }

   public String getUnixHostPrivateKey(String system)
   {
      return getProperty("unixhost.privatekey." + system);
   }

   public String getUnixHostKeyPhrase(String system)
   {
      return getProperty("unixhost.keyphrase." + system);
   }

   public String getEnv()
   {
      return getProperty("env");
   }

}
