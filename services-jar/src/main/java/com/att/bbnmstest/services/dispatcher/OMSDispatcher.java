package com.att.bbnmstest.services.dispatcher;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.HttpClient;
import com.att.bbnmstest.client.HttpClient.ContentType;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.FreemarkerTemplateParser;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import static com.att.bbnmstest.services.util.EnvConstants.OMS_MESSAGE_SENDER_URL;
import static com.att.bbnmstest.services.util.EnvConstants.TEMPLATE_DIR;
import static com.att.bbnmstest.services.util.EnvConstants.QUEUE_OMS;
import static com.att.bbnmstest.services.util.EnvConstants.TEMPLATE_OMS_MSG_SENDER;
import com.att.bbnmstest.services.util.PropUtil;

@Component
public class OMSDispatcher implements RequestDispatcher
{

   private final static Logger logger = Logger.getLogger(OMSDispatcher.class);

   private HttpClient client;

   @Autowired
   private PropUtil propUtil;

   public OMSDispatcher(@Value("${" + OMS_MESSAGE_SENDER_URL + "}") final String url)
   {
      client = new HttpClient(url, ContentType.XML_TEXT_CHARSET_UTF_8);
   }

   private String createOmsRequest(Map orderDetailsAsMap, String template) throws RequestDispatcherServiceException
   {
      try
      {

         String message = FreemarkerTemplateParser.createRequest(orderDetailsAsMap, template, propUtil.getProperty(
            TEMPLATE_DIR));

         Map<String, String> messageMap = new java.util.HashMap<String, String>();
         messageMap.put("message", message);
         messageMap.put("ENV", propUtil.getEnv());
         messageMap.put("queue", QUEUE_OMS);

         return FreemarkerTemplateParser.createRequest(messageMap, propUtil.getProperty(TEMPLATE_OMS_MSG_SENDER),
            propUtil.getProperty(TEMPLATE_DIR));

      }
      catch (ClientException e)
      {
         throw new RequestDispatcherServiceException("Error while parsing template", e);
      }
   }

   @Override
   public String sendRequest(Map orderDetailsAsMap, String template) throws RequestDispatcherServiceException
   {
      try
      {
         logger.info("Sending message to Message Sender Service,template = ");
         return client.post(createOmsRequest(orderDetailsAsMap, template));
      }
      catch (ClientException e)
      {
         throw new RequestDispatcherServiceException("Client threw Exception while posting request", e);
      }
      catch (Exception e)
      {
         throw new RequestDispatcherServiceException("Unexpected Exception while posting request", e);
      }
   }

}
