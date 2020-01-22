package com.att.bbnmstest.services.model;


public class Order
{

   private String eventName;
   private String dateTime;
   private String ban;
   private String omsOrderNumber;
   private String bbnmsOrderNumber;
   private String omsReferenceNumber;
   private String eventStatus;
   private String eventStatusDescription;
   private boolean portChanged;
   private String messageType;

   public static final String MESSAGE_TYPE_EVENT = "EVENT";


   public String getMessageType()
   {
      return messageType;
   }

   public void setMessageType(String messageType)
   {
      this.messageType = messageType;
   }

   public String getEventName()
   {
      return eventName;
   }

   public void setEventName(String eventName)
   {
      this.eventName = eventName;
   }

   public String getDateTime()
   {
      return dateTime;
   }

   public void setDateTime(String dateTime)
   {
      this.dateTime = dateTime;
   }

   public String getBan()
   {
      return ban;
   }

   public void setBan(String ban)
   {
      this.ban = ban;
   }

   public String getOmsOrderNumber()
   {
      return omsOrderNumber;
   }

   public void setOmsOrderNumber(String omsOrderNumber)
   {
      this.omsOrderNumber = omsOrderNumber;
   }

   public String getBbnmsOrderNumber()
   {
      return bbnmsOrderNumber;
   }

   public void setBbnmsOrderNumber(String bbnmsOrderNumber)
   {
      this.bbnmsOrderNumber = bbnmsOrderNumber;
   }

   public String getOmsReferenceNumber()
   {
      return omsReferenceNumber;
   }

   public void setOmsReferenceNumber(String omsReferenceNumber)
   {
      this.omsReferenceNumber = omsReferenceNumber;
   }

   public String getEventStatus()
   {
      return eventStatus;
   }

   public void setEventStatus(String eventStatus)
   {
      this.eventStatus = eventStatus;
   }

   public String getEventStatusDescription()
   {
      return eventStatusDescription;
   }

   public void setEventStatusDescription(String eventStatusDescription)
   {
      this.eventStatusDescription = eventStatusDescription;
   }

   public boolean isPortChanged()
   {
      return portChanged;
   }

   public void setPortChanged(boolean portChanged)
   {
      this.portChanged = portChanged;
   }

   @Override
   public String toString()
   {
      return "Order [eventName=" + eventName + ", dateTime=" + dateTime + ", ban=" + ban + ", omsOrderNumber="
         + omsOrderNumber + ", bbnmsOrderNumber=" + bbnmsOrderNumber + ", omsReferenceNumber=" + omsReferenceNumber
         + ", eventStatus=" + eventStatus + ", eventStatusDescription=" + eventStatusDescription + ", portChanged="
         + portChanged + ", messageType=" + messageType + "]";
   }


}
