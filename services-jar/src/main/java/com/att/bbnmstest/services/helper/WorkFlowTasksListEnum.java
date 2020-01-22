package com.att.bbnmstest.services.helper;

import java.util.Arrays;
import java.util.List;

public enum WorkFlowTasksListEnum
{

   XGSPONEMSCMSSAMACTIVATION
   {
      @Override
      public List<String> getWorkFlowTasksList()
      {
         return Arrays.asList("EMS Activation", "SAM Activation", "EMS/CMS/SAM Activation", "CMS Activation");
      }
   },
   QUERY_NAD_INFO
   {
      @Override
      public List<String> getWorkFlowTasksList()
      {
         return Arrays.asList("Query for NAD Info");
      }
   },

   NAD_ACTIVATION
   {
      @Override
      public List<String> getWorkFlowTasksList()
      {
         return Arrays.asList("Receive NAD Info");
      }
   };

   public List<String> getWorkFlowTasksList()
   {
      throw new UnsupportedOperationException();
   }

}
