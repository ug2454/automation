package com.att.bbnmstest.services.dispatcher;

import static com.att.bbnmstest.services.util.EnvConstants.ISSAC_MESSAGE_SENDER_URL;
import static com.att.bbnmstest.services.util.EnvConstants.TEMPLATE_DIR;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.HttpClient;
import com.att.bbnmstest.client.HttpClient.ContentType;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.FreemarkerTemplateParser;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import com.att.bbnmstest.services.util.PropUtil;

@Component
public class IssacDispatcher implements RequestDispatcher
{

   private HttpClient client;

   @Autowired
   private PropUtil propUtil;

   public IssacDispatcher(@Value("${" + ISSAC_MESSAGE_SENDER_URL + "}") String url)
   {
      client = new HttpClient(url, ContentType.XML_TEXT_CHARSET_UTF_8);
   }

   private String createOmsRequest(Map orderDetailsAsMap, String template) throws RequestDispatcherServiceException
   {
      try
      {
         return FreemarkerTemplateParser.createRequest(orderDetailsAsMap, template, propUtil.getProperty(TEMPLATE_DIR));
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
