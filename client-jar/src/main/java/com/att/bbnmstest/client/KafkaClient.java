package com.att.bbnmstest.client;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaClient implements java.lang.AutoCloseable
{

   public enum OffsetResetPolicy
   {
      earliest, latest
   }


   public static final String jaasTemplate = "%s required username=\"%s\" password=\"%s\";";

   private KafkaConsumer<String, String> consumer;
   private Producer<String, String> producer;
   private String topic;
   private final Properties props;

   public KafkaClient(Properties props, String topic)
   {
      this.props = props;
      this.topic = topic;
   }

   private KafkaConsumer<String, String> getConsumer()
   {
      if (consumer == null)
      {
         consumer = new KafkaConsumer<>(props);
         consumer.subscribe(Arrays.asList(topic));
      }

      return consumer;
   }

   public void setConsumer(KafkaConsumer<String, String> consumer)
   {
      this.consumer = consumer;
   }

   private Producer<String, String> getProducer()
   {
      if (producer == null)
      {
         producer = new KafkaProducer<>(props);
      }
      return producer;
   }

   public List<String> consume()
   {
      ConsumerRecords<String, String> records = consumeRecords(topic);

      List<String> messages = new ArrayList<String>();

      for (ConsumerRecord<String, String> record : records)
      {
         messages.add(record.value());
      }


      return messages;
   }

   public ConsumerRecords<String, String> consumeRecords(String topic)
   {
      ConsumerRecords<String, String> records = getConsumer().poll(Duration.ofSeconds(1));
      getConsumer().commitAsync();

      return records;
   }

   public void produce(String topic, String messageId, String message)
   {

      Future<RecordMetadata> response = getProducer().send(new ProducerRecord<>(topic, messageId, message));
      while (!response.isDone())
      {
         /* waiting for response from broker */
      }
   }

   public void cleanUp()
   {
      getProducer().close();
      getConsumer().close();
      setConsumer(null);
   }

   @Override
   public void close() throws Exception
   {
      cleanUp();
   }

   public void seekToEnd()
   {
      seekToEnd(1);
   }

   public void seekToEnd(int retryCount)
   {
      for (int i = 1; i <= retryCount; i++)
      {
         Set<TopicPartition> partitions = getConsumer().assignment();
         if (partitions != null && !partitions.isEmpty())
         {
            getConsumer().seekToEnd(partitions);
            break;
         }
         else
         {
            continue;
         }
      }

   }


   public static class PropertiesBuilder
   {
      private Properties props;

      public PropertiesBuilder()
      {
         this.props = new Properties();
      }

      public PropertiesBuilder setBootstrapServers(String value)
      {
         props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setGroupId(String value)
      {
         props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setClientId(String value)
      {
         props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setEnableAutoCommit(Boolean value)
      {
         props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.toString(value));
         return this;
      }

      public PropertiesBuilder setAutoCommitInterval(int value)
      {
         props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, Integer.toString(value));
         return this;
      }

      public PropertiesBuilder setAutoOffsetReset(OffsetResetPolicy value)
      {
         props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, value.name());
         return this;
      }

      public PropertiesBuilder setSessionTimeout(int value)
      {
         props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, Integer.toString(value));
         return this;
      }

      public PropertiesBuilder setKeySerializer(String value)
      {
         props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setValueSerializer(String value)
      {
         props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setKeyDeSerializer(String value)
      {
         props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setValueDeSerializer(String value)
      {
         props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setSecurityProtocol(String value)
      {
         props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, value);
         return this;
      }

      public PropertiesBuilder setSaslMechanisim(String value)
      {
         props.setProperty(SaslConfigs.SASL_MECHANISM, value);
         return this;
      }

      public PropertiesBuilder setSaslJaasConfig(String username, String password, String securityModule)
      {
         if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(securityModule))
         {
            throw new IllegalArgumentException(
               "Missing one ore more mandatory Parameter [username ,password,security module]");
         }
         props.setProperty(SaslConfigs.SASL_JAAS_CONFIG, String.format(jaasTemplate, securityModule, username,
            password));
         return this;
      }

      public Properties build()
      {
         return props;
      }
   }


}
