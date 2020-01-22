package com.att.bbnmstest.cuke.steps;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException;
import com.att.bbnmstest.cuke.exception.AutomationBDDCucumberException.Reason;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;


@Ignore
public class ActivityValidationSteps extends BaseStep
{
   private final static Logger logger = Logger.getLogger(ActivityValidationSteps.class);

   @Autowired
   @Qualifier("BBNMSService")
   private BBNMSService bbnmsService;


   public BBNMSService getBBNMSService()
   {
      return bbnmsService;
   }

   @Then("^(.*) task should be successful in OL$")
   public void validateTaskSuccedsInOL(String taskName) throws AutomationBDDCucumberException
   {
      validateTaskSuccedsInOL(getContext().getOrigOrderId(), taskName);
   }

   @Then("^(.*) task in progress in OL$")
   public void validateTaskInProgressInOL(String taskName) throws AutomationBDDCucumberException
   {
      try
      {
         assertTrue(getBBNMSService().validateTaskInProgressInOL(getContext().getOrigOrderId(), taskName));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Task [" + taskName + "] for status In Progress", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Task [" + taskName + "] for status In Progress");
         }
         throw e;
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Service Exception", Reason.SERVICE_ERROR, getContext());
      }
   }

   @Then("^(.*) task should fail in ol$")
   public void validateTaskFailedInOL(String taskName) throws AutomationBDDCucumberException
   {
      try
      {
         assertTrue(getBBNMSService().validateTaskFailedInOL(getContext().getOrigOrderId(), taskName));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Task [" + taskName + "] for status Failed", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Task [" + taskName + "] for status Failed");
         }
         throw e;
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Service Exception", Reason.SERVICE_ERROR, getContext());
      }
   }


   @Then("^(.*) task successful in OMS$")
   public void validateOmsTaskSucceeds(String messageTag)
   {
      try
      {
         assertTrue(getBBNMSService().validateOmsTaskSucceeds(getContext().getOrigOrderId(), messageTag));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Task [" + messageTag + "] for status Success", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Task [" + messageTag + "] for status Success");
         }
         throw e;
      }

   }

   @Then("^(.*) task failed in OMS$")
   public void validateOmsTaskFailed(String messageTag)
   {
      validateOmsTaskFailed(getContext().getOrigOrderId(), messageTag);

   }

   public void validateOmsTaskFailed(String origOrderId, String messageTag) throws AutomationBDDCucumberException
   {
      try
      {
         assertTrue(getBBNMSService().validateOmsTaskFailed(origOrderId, messageTag));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Task [" + messageTag + "] for status Failed", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Task [" + messageTag + "] for status Failed");
         }
         throw e;
      }

   }


   @Then("^order listeners should be notified for the following events:$")
   public void validateDmaapKafkaEvents(DataTable dataTable) throws AutomationBDDCucumberException
   {
      List<List<String>> events = dataTable.raw();

      boolean hasHeaders = false;
      if (events != null && events.size() > 1 && "event_status".equals(events.get(0).get(1)) && "event_name".equals(
         events.get(0).get(0)))
      {
         logger.info("Removing headers....... ");
         hasHeaders = true;
      }
      try
      {
         assertTrue(getBBNMSService().validateEventStatusInDmapIntf(getContext().getOrigOrderId(), events, hasHeaders));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Events [" + events + "] ", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Events [" + events + "] ");
         }
         throw e;
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Service Exception", Reason.SERVICE_ERROR, getContext());
      }

   }


   public void validateTaskFailsInOL(List<String> tasks) throws AutomationBDDCucumberException
   {
      if (tasks == null || tasks.isEmpty())
      {
         assertTrue(false);
      }
      for (String taskName : tasks)
      {
         validateTaskFailedInOL(taskName);
      }
   }

   public void validateTaskSuccedsInOL(List<String> tasks) throws AutomationBDDCucumberException
   {
      validateTaskSuccedsInOL(getContext().getOrigOrderId(), tasks);
   }

   public void validateTaskSuccedsInOL(String origOrderId, List<String> tasks) throws AutomationBDDCucumberException
   {
      if (tasks == null || tasks.isEmpty())
      {
         assertTrue(false);
      }
      for (String taskName : tasks)
      {
         validateTaskSuccedsInOL(origOrderId, taskName);
      }
   }


   public void validateTaskSuccedsInOL(String origOrderId, String taskName) throws AutomationBDDCucumberException
   {
      logger.info("Validate Task [" + taskName + "] succeded in OL for Order :" + origOrderId);
      try
      {
         assertTrue(getBBNMSService().validateTaskSuccedsInOL(origOrderId, taskName));
      }
      catch (AutomationBDDCucumberException e)
      {
         logger.error("Validation Failed for Task [" + taskName + "] for status Success", e);
         if (e.getReason() == AutomationBDDCucumberException.Reason.ASSERT_EXCEPTION)
         {
            e.getReason().setDescription("Validation Failed for Task [" + taskName + "] for status Success");
         }
         throw e;
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Service Exception", Reason.SERVICE_ERROR, getContext());
      }
   }

   @And("(.+) setting is set to (.+) in ol")
   public void updateOlSettings(String activity, String value)
   {
      try
      {
         assertTrue(getBBNMSService().updateOlSettings(activity, value));
      }
      catch (AutomationBDDServiceException e)
      {
         throw new AutomationBDDCucumberException("Setting update failed Activity:" + activity + ", Value :" + value, e,
            getContext());
      }
   }

   @Given("^ol db is up and running$")
   public void verifyOlDbIsUpAndRunning() throws AutomationBDDServiceException
   {
      getBBNMSService().verifyOlDbIsUp();
      logger.info("Ol Db is Up!");
   }


}
