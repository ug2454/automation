package com.att.bbnmstest.services.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Utils
{

   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

   public static String getExtendedDateByDays(int amount)
   {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, amount);
      Date dd = cal.getTime();
      return dateFormat.format(dd);
   }

   public static String generareIdWithPrefix(String identifier, int uniqueIdLength)
   {
      return StringUtils.join(identifier, StringUtils.right(String.valueOf(new Date().getTime()), uniqueIdLength
         - StringUtils.length(identifier)));
   }


   public static boolean isNumeric(String str)
   {
      try
      {
         Double.parseDouble(str);
         return true;
      }
      catch (NumberFormatException e)
      {
         return false;
      }
   }

}
