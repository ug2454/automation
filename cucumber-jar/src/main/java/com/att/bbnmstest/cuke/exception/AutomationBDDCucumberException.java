package com.att.bbnmstest.cuke.exception;

import com.att.bbnmstest.cuke.StateContext;

public class AutomationBDDCucumberException extends RuntimeException
{

   private Reason reason;

   public AutomationBDDCucumberException(Throwable e, StateContext context)
   {
      this.reason = Reason.UNEXPECTED_EXCEPTION;
      this.reason.setDescription(e.getMessage() + ", Context [" + context + "]");
   }

   public AutomationBDDCucumberException(String desc, Throwable e, StateContext context)
   {
      super(desc + "[" + context + "]", e);
      this.reason = Reason.UNEXPECTED_EXCEPTION;
      this.reason.setDescription(desc + ", Context[" + context + "]");
   }

   public AutomationBDDCucumberException(String desc, Reason reason, Throwable e, StateContext context)
   {
      super(desc + "[" + context + "]", e);
      this.reason = reason;
      this.reason.setDescription(desc + ", Context [" + context + "]");
   }

   public AutomationBDDCucumberException(String desc, Reason reason, StateContext context)
   {
      super(desc + "[" + context + "]");
      this.reason = reason;
      this.reason.setDescription(desc + ", Context [" + context + "]");
   }

   @Override
   public String getMessage()
   {
      return "[" + getReason().name() + "-" + getReason().getDescription() + "]" + super.getMessage();
   }

   public Reason getReason()
   {
      return reason;
   }


   public void setReason(Reason reason)
   {
      this.reason = reason;
   }


   public enum Reason
   {

      ASSERT_EXCEPTION("Exception while assert validation"), UNEXPECTED_EXCEPTION("Unexpected Exception Occred"),
      SERVICE_ERROR("Error occured while calling services"), RECORD_NOT_FOUND("Record Not found"), RESOURCE_NOT_FOUND(
         "Resouce Not found");


      private String description = "Unexpected error occurred";

      private Reason(String desc)
      {
         this.description = desc;
      }

      public String getDescription()
      {
         return this.description;
      }

      public void setDescription(String desc)
      {
         this.description = desc;
      }
   }


}
