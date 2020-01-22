package com.att.bbnmstest.services.dispatcher;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.HttpClient;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import com.att.bbnmstest.services.model.Order;
import static com.att.bbnmstest.services.util.EnvConstants.HTTP_DMAP_STATUS_INTF_URL;
import static com.att.bbnmstest.services.util.EnvConstants.HTTP_DMAP_STATUS_INTF_USER;
import static com.att.bbnmstest.services.util.EnvConstants.HTTP_DMAP_STATUS_INTF_PASS;
import static com.att.bbnmstest.services.util.EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class DmaapDispatcher
{
   private final static Logger logger = Logger.getLogger(DmaapDispatcher.class);

   private final HttpClient client;
   private Gson parser;

   public DmaapDispatcher(@Value("${" + HTTP_DMAP_STATUS_INTF_URL + "}") String url, @Value("${"
      + HTTP_DMAP_STATUS_INTF_USER + "}") String user, @Value("${" + HTTP_DMAP_STATUS_INTF_PASS + "}") String password)
   {
      this.client = new HttpClient(url, user, EncryptDecryptUtil.decryptBySysProp(password,
         BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY), HttpClient.ContentType.JSON);
   }


   private Gson getParser()
   {
      if (this.parser == null)
      {
         this.parser = new Gson();
      }

      return this.parser;
   }

   public List<Order> getOrders() throws AutomationBDDServiceException
   {
      List<Order> pojos = new ArrayList<Order>();
      String response;
      try
      {
         response = client.get();
         java.util.List<String> responseItems = getParser().fromJson(response, new TypeToken<java.util.List<String>>()
               {}.getType());
         logger.info("Response Items :" + responseItems);
         for (String item : responseItems)
         {
            if (item != null)
            {
               item = item.replaceAll("\\\\\"", "\"");
            }
            Order pojo = getParser().fromJson(item,Order.class);
            pojos.add(pojo);
         }

         return pojos;
      }
      catch (ClientException e)
      {
         throw new RequestDispatcherServiceException("Exception thrown from rest client", e);
      }

   }
}
