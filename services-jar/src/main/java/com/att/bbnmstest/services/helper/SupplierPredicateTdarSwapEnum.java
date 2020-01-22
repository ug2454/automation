package com.att.bbnmstest.services.helper;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

import com.att.bbnmstest.services.dao.OrchestrationLayerDAO;
import com.att.bbnmstest.services.util.EnvConstants;


public enum SupplierPredicateTdarSwapEnum
{

   LineItemActivationStatus1
   {

      @Override
      public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, final Map<String, String> valuesMap)
      {
         return () -> {
            try
            {
               return olDao.getLineItemIDActivationStatusByOrderId(valuesMap.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID),
                  valuesMap.get("lineItemId"));
            }
            catch (Exception e)
            {
               return null;
            }
         };
      }

      @Override
      public Predicate<String> getPredicate(String expectedStatus)
      {
         return (status) -> {
            return (StringUtils.equalsIgnoreCase(status, expectedStatus));
         };
      }
   };

   public Supplier<String> getSupplier(OrchestrationLayerDAO olDao, Map<String, String> valuesMap)
   {
      throw new UnsupportedOperationException("getSupplier() method not defined");
   }

   public Predicate<String> getPredicate(String expectedStatus)
   {
      throw new UnsupportedOperationException("getPredicate(String) method not defined");
   }

}
