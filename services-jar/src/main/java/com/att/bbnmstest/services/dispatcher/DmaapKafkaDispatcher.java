package com.att.bbnmstest.services.dispatcher;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.KafkaClient;
import com.att.bbnmstest.client.KafkaClient.OffsetResetPolicy;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.services.exception.RequestDispatcherServiceException;
import com.att.bbnmstest.services.model.Order;
import com.att.bbnmstest.services.util.EnvConstants;
import com.google.gson.Gson;

@Component
public class DmaapKafkaDispatcher
{
   private final static Logger logger = Logger.getLogger(DmaapKafkaDispatcher.class);

   private final KafkaClient client;
   private Gson parser;
   private int retryCount = 5;

   public DmaapKafkaDispatcher(@Value("${" + EnvConstants.DMAAP_KAFKA_CLIENT_BROKER + "}") String brokers, @Value("${"
      + EnvConstants.DMAAP_KAFKA_CLIENT_USER + "}") String username, @Value("${"
         + EnvConstants.DMAAP_KAFKA_CLIENT_PASSWORD + "}") String password, @Value("${"
            + EnvConstants.DMAAP_KAFKA_CLIENT_TOPIC + "}") String topic, @Value("${"
               + EnvConstants.DMAAP_KAFKA_CLIENT_SECURITY_MODULE + "}") String securityModule, @Value("${"
                  + EnvConstants.DMAAP_KAFKA_CLIENT_SECURITY_MECH + "}") String securityMechanism, @Value("${"
                     + EnvConstants.DMAAP_KAFKA_CLIENT_SECURITY_PROTO + "}") String securityPropotcol, @Value("${"
                        + EnvConstants.DMAAP_KAFKA_CLIENT_GROUP_ID + "}") String groupId, @Value("${"
                           + EnvConstants.DMAAP_KAFKA_CLIENT_CLIENT_ID + "}") String clientId, @Value("${"
                              + EnvConstants.DMAAP_KAFKA_CLIENT_RETRY_COUNT + "}") int retryCount, @Value("${"
                                 + EnvConstants.DMAAP_KAFKA_CLIENT_DESERIALIZER + "}") String deserializer, @Value("${"
                                    + EnvConstants.DMAAP_KAFKA_CLIENT_SERIALIZER + "}") String serializer)
   {
      Properties props = new KafkaClient.PropertiesBuilder().setBootstrapServers(brokers).setGroupId(
         getGroupIdFromSysProperties(groupId)).setClientId(clientId).setSecurityProtocol(securityPropotcol)
         .setSaslMechanisim(securityMechanism).setSaslJaasConfig(username, password, securityModule)
         .setAutoCommitInterval(10).setSessionTimeout(30000).setEnableAutoCommit(true).setKeyDeSerializer(deserializer)
         .setValueDeSerializer(deserializer).setKeySerializer(serializer).setValueSerializer(serializer)
         .setAutoOffsetReset(OffsetResetPolicy.latest).build();
      client = new KafkaClient(props, topic);
      this.retryCount = retryCount;
   }

   private String getGroupIdFromSysProperties(String groupId)
   {
      if (System.getProperty(EnvConstants.DMAAP_KAFKA_CLIENT_PIPELINE_GROUP_ID) != null)
      {
         logger.info("Group id from sys properties : " + System.getProperty(
            EnvConstants.DMAAP_KAFKA_CLIENT_PIPELINE_GROUP_ID));
         return System.getProperty(EnvConstants.DMAAP_KAFKA_CLIENT_PIPELINE_GROUP_ID);
      }

      return groupId;
   }

   private Gson getParser()
   {
      if (this.parser == null)
      {
         this.parser = new Gson();
      }

      return this.parser;
   }


   public void seekToEnd()
   {
      client.seekToEnd();
      client.consume();
   }

   public java.util.List<Order> getOrders() throws RequestDispatcherServiceException
   {
      java.util.List<Order> orders = new java.util.ArrayList<Order>();

      try
      {
         for (int i = 1; i <= retryCount; i++)
         {
            logger.info("Retry attempt to get messages:" + i);
            orders.addAll(getOrdersFromTopic());
         }
      }

      finally
      {
         client.cleanUp();
      }
      return orders;


   }

   private java.util.List<Order> getOrdersFromTopic() throws RequestDispatcherServiceException
   {
      java.util.List<Order> pojos = new java.util.ArrayList<Order>();

      try
      {
         java.util.List<String> kafkaMessges = client.consume();
         logger.debug("Response From Kafka :" + kafkaMessges);
         logger.info("Received [" + kafkaMessges.size() + "] messages from kafka");
         for (String response : kafkaMessges)
         {
            Order pojo = getParser().fromJson(response,Order.class);
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
