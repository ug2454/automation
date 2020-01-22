package com.att.bbnmstest.client;

import java.net.URI;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;

import com.att.bbnmstest.client.exception.JMSClientException;
import com.att.bbnmstest.client.utils.UserCredentials;

public class JMSClient
{

   private final String url;
   private final String username;
   private final String password;
   private final String connectionFactory;
   private final String namingFactory;
   private JmsTemplate jmsTemplate;

   public JMSClient(URI url, UserCredentials credentials, String connectionFactory, String namingFactory)
   {
      this.url = (url != null) ? url.getPath() : null;
      this.username = credentials.getUsername();
      this.password = credentials.getPassword();
      this.connectionFactory = connectionFactory;
      this.namingFactory = namingFactory;
   }

   private JmsTemplate getJmsTemplate()
   {
      if (jmsTemplate == null)
      {
         jmsTemplate = new JmsTemplate();
         jmsTemplate.setConnectionFactory(queueConnectionFactory());
         jmsTemplate.setDestinationResolver(destinationResolver(jndiTemplate()));
      }

      return this.jmsTemplate;
   }

   private Properties getJNDiProperties()
   {

      final Properties jndiProps = new Properties();
      jndiProps.setProperty(Context.INITIAL_CONTEXT_FACTORY, namingFactory);
      jndiProps.setProperty(Context.PROVIDER_URL, url);
      if (StringUtils.isNotEmpty(username))
      {
         jndiProps.setProperty(Context.SECURITY_PRINCIPAL, username);
      }
      if (StringUtils.isNotEmpty(password))
      {
         jndiProps.setProperty(Context.SECURITY_CREDENTIALS, password);
      }
      return jndiProps;

   }

   private JndiDestinationResolver destinationResolver(JndiTemplate jndiTemplate)
   {
      JndiDestinationResolver destinationResolver =
         new org.springframework.jms.support.destination.JndiDestinationResolver();
      destinationResolver.setJndiTemplate(jndiTemplate);
      return destinationResolver;
   }

   private ConnectionFactory queueConnectionFactory()
   {
      return lookupByJndiTemplate(connectionFactory, QueueConnectionFactory.class);
   }

   private JndiTemplate jndiTemplate()
   {
      JndiTemplate jndiTemplate = new JndiTemplate();
      jndiTemplate.setEnvironment(getJNDiProperties());
      return jndiTemplate;
   }

   private <T> T lookupByJndiTemplate(String jndiName, Class<T> requiredType)
   {

      try
      {
         Object located = jndiTemplate().lookup(jndiName);
         if (located == null)
         {
            throw new NameNotFoundException("JNDI object with [" + jndiName + "] not found");
         }
         return requiredType.cast(located);
      }
      catch (NamingException e)
      {
         throw new JMSClientException("Exception while jndi lookup", e);
      }
   }


   public void sendMesage(String queueName, String message)
   {
      MessageCreator messageCreator = (s) -> s.createTextMessage(message);
      getJmsTemplate().send(queueName, messageCreator);
   }


}
