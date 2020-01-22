package com.att.bbnmstest.client.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.att.bbnmstest.client.exception.ClientException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerTemplateParser
{

   private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

   public static final String ENCODING_UTF = "UTF-8";

   public FreemarkerTemplateParser()
   {

      cfg.setDefaultEncoding(ENCODING_UTF);
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
      cfg.setWrapUncheckedExceptions(true);
   }


   public static Template getTemplate(String templateName, String templateDir) throws IOException
   {
      cfg.setClassForTemplateLoading(FreemarkerTemplateParser.class, templateDir);
      return (cfg.getTemplate(templateName));
   }

   public static String createRequest(Map orderDetailsAsMap, String templateName, String templateDir)
   {
      try
      {
         Template template = getTemplate(templateName, templateDir);
         StringWriter sw = new StringWriter();
         if (template != null)
         {
            template.process(orderDetailsAsMap, sw);
         }
         return sw.toString();
      }
      catch (TemplateException | IOException e)
      {
         throw new IllegalStateException("Exception occured while parsing template", e);
      }


   }

}
