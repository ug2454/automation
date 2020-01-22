package com.att.bbnmstest.cuke.steps;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.cuke.StateContext;
import com.att.bbnmstest.cuke.StepConstants;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException.Reason;

/**
 * 
 * @author aa9828 All step classes should extend BaseStep class Given When Then annotations should not be implemented in
 *         BaseStep class
 * 
 */
@SpringBootTest
@Ignore
@Component
public class BaseStep
{

   @Autowired
   private StateContext context;


   protected StateContext getContext()
   {
      return context;
   }

   protected boolean isMoveOrder(String orderType)
   {
      return StringUtils.equals(StepConstants.ORDER_TYPE_MOVE, orderType);
   }

   protected boolean isProvideOrder(String orderType)
   {
      return StringUtils.equals(StepConstants.ORDER_TYPE_PROVIDE, orderType);
   }

   protected boolean isChangeOrder(String orderType)
   {
      return StringUtils.equals(StepConstants.ORDER_TYPE_CHANGE, orderType);
   }

   protected boolean isMoveOrder()
   {
      return isMoveOrder(context.getOrderActionType());
   }

   protected boolean isProvideOrder()
   {
      return isProvideOrder(context.getOrderActionType());
   }

   protected boolean isChangeOrder()
   {
      return isChangeOrder(context.getOrderActionType());
   }

   protected boolean isCeaseOrder()
   {
      return StringUtils.equals(StepConstants.ORDER_TYPE_CEASE, context.getOrderActionType());
   }

   protected void assertTrue(boolean result) throws AutomationBDDCucumberException
   {
      try
      {
         Assert.assertTrue(result);
      }
      catch (java.lang.AssertionError e)
      {
         throw new AutomationBDDCucumberException("Assert True Exception", Reason.ASSERT_EXCEPTION, e, getContext());
      }
   }
}
