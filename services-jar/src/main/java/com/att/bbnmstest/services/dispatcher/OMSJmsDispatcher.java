package com.att.bbnmstest.services.dispatcher;

import java.net.URISyntaxException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.JMSClient;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.client.utils.FreemarkerTemplateParser;
import com.att.bbnmstest.client.utils.SimpleCredentials;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;

@Component
public class OMSJmsDispatcher implements RequestDispatcher
{
   private final static Logger logger = Logger.getLogger(OMSJmsDispatcher.class);

   @Autowired
   private PropUtil propUtil;

   private JMSClient jmsMessageSender;

   private String queueName;

   public OMSJmsDispatcher(@Value("${" + EnvConstants.OMS_MESSAGE_QUEUE_URL + "}") String url, @Value("${"
      + EnvConstants.OMS_MESSAGE_QUEUE_USER + "}") String username, @Value("${" + EnvConstants.OMS_MESSAGE_QUEUE_PASS
         + "}") String password, @Value("${" + EnvConstants.OMS_MESSAGE_QUEUE_CONNECTION_FACTORY
            + "}") String connectionFactory, @Value("${" + EnvConstants.OMS_MESSAGE_QUEUE_NAME_FACTORY
               + "}") String nameFactory, @Value("${" + EnvConstants.OMS_MESSAGE_QUEUE_NAME + "}") final String queue)
      throws RequestDispatcherServiceException
   {
      try
      {
         jmsMessageSender = new JMSClient(new java.net.URI(url), new SimpleCredentials(username, EncryptDecryptUtil
            .decryptBySysProp(password, EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY)), connectionFactory,
            nameFactory);
      }
      catch (URISyntaxException e)
      {
         throw new RequestDispatcherServiceException("Url [" + url + "] has syntax error", e);
      }
      this.queueName = queue;
   }


   @Override
   public String sendRequest(Map orderDetailsAsMap, String template) throws RequestDispatcherServiceException
   {
      logger.info("Sending message to JMS Queue " + queueName);
      jmsMessageSender.sendMesage(queueName, createOmsRequest(orderDetailsAsMap, template));
      return null;

   }

   private String createOmsRequest(Map orderDetailsAsMap, String template) throws RequestDispatcherServiceException
   {
      try
      {

         return FreemarkerTemplateParser.createRequest(orderDetailsAsMap, template, propUtil.getProperty(
            EnvConstants.TEMPLATE_DIR));
      }
      catch (ClientException e)
      {
         throw new RequestDispatcherServiceException("Error while parsing template", e);
      }
   }

}
