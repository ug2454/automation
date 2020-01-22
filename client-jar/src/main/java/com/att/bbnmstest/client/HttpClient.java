package com.att.bbnmstest.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.exception.HttpClientException;

public class HttpClient
{
   public enum ContentType
   {

      XML("application/xml"), XML_TEXT("text/xml"), XML_TEXT_CHARSET_UTF_8("text/xml; charset=utf-8"), JSON(
         "application/json");

      private static final String CONTENT_TYPE_HEADER = "Content-Type";
      private String type;

      ContentType(String type)
      {
         this.type = type;
      }

      public void setHeader(HttpHeaders headers)
      {
         if (headers != null)
         {
            headers.set(CONTENT_TYPE_HEADER, type);
         }
      }

   }

   private final RestTemplate restTemplate;
   private final String url;
   private final HttpHeaders headers;


   public HttpClient(String url, ContentType contentType)
   {
      this.url = url;
      restTemplate = new RestTemplate();
      headers = new HttpHeaders();
      contentType.setHeader(headers);
   }

   public HttpClient(String url, String username, String password, ContentType contentType)
   {
      this(url, contentType);
      headers.setBasicAuth(username, password);
   }


   public <K, T> T post(K request, Class<T> clazz) throws ClientException
   {
      try
      {
         HttpEntity<Object> entity = new HttpEntity<Object>(request, headers);
         return restTemplate.exchange(url, HttpMethod.POST, entity, clazz).getBody();
      }
      catch (RestClientException rce)
      {
         throw new HttpClientException("Exception occured wile making post call to :" + url, rce);
      }
   }

   public String post(String request) throws ClientException
   {
      return post(request, String.class);
   }

   public <T> T get(Class<T> clazz) throws ClientException
   {
      return get(clazz, null);
   }

   public <T> T get(Class<T> clazz, Object param) throws ClientException
   {
      try
      {
         HttpEntity<Object> entity = new HttpEntity<Object>(headers);
         return restTemplate.exchange(url, HttpMethod.GET, entity, clazz, param).getBody();
      }
      catch (RestClientException rce)
      {
         throw new HttpClientException("Exception occured wile making get call to :" + url, rce);
      }
   }

   public String get() throws ClientException
   {
      return get(String.class);
   }

}
