package com.att.bbnmstest.services.dispatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.HttpClient;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import com.att.bbnmstest.services.model.Order;
import static com.att.bbnmstest.services.util.EnvConstants.OMS_MILE_STONE_INFT_URL;
import static com.att.bbnmstest.services.util.EnvConstants.OMS_MILE_STONE_INFT_USER;
import static com.att.bbnmstest.services.util.EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY;
import static com.att.bbnmstest.services.util.EnvConstants.OMS_MILE_STONE_INFT_PASS;

@Component
public class OmsMilestoneDispatcher
{

   private final HttpClient client;

   public OmsMilestoneDispatcher(@Value("${" + OMS_MILE_STONE_INFT_URL + "}") String url, @Value("${"
      + OMS_MILE_STONE_INFT_USER + "}") String user, @Value("${" + OMS_MILE_STONE_INFT_PASS + "}") String password)
   {
      this.client = new HttpClient(url, user, EncryptDecryptUtil.decryptBySysProp(password,
         BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY), HttpClient.ContentType.JSON);
   }


   public Order getOrder(String workFlowId) throws RequestDispatcherServiceException
   {
      try
      {
         return client.get(Order.class, workFlowId);

      }
      catch (ClientException e)
      {
         throw new RequestDispatcherServiceException("Exception thrown from rest client", e);
      }
   }
}
