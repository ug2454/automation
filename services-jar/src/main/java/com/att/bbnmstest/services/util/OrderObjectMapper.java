package com.att.bbnmstest.services.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderObjectMapper
{

   @Autowired
   private PropUtil propUtil;


   private final static Logger logger = Logger.getLogger(OrderObjectMapper.class);

   public Map<String, String> createUverseOrderObjectMap(String orderNumber, String origOrdId, String ban,
      String serviceID, String orderType, String orderSubType, String orderActionRefNumber, String NTI)
   {
      Map<String, String> orderDetailsAsMap = new HashMap<>();
      orderDetailsAsMap.put("ENV", propUtil.getEnv());
      orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);
      orderDetailsAsMap.put("origOrderActId", origOrdId);
      orderDetailsAsMap.put("orderNumber", orderNumber);
      orderDetailsAsMap.put("ban", ban);
      orderDetailsAsMap.put("orderActionType", orderType);
      orderDetailsAsMap.put("orderActionSubType", orderSubType);
      orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
      orderDetailsAsMap.put("serviceID", serviceID);
      orderDetailsAsMap.put("workOrderId", orderNumber);
      orderDetailsAsMap.put(EnvConstants.TAG_TASKNAME, "provisionTransport");
      orderDetailsAsMap.put("subTransportType", "");
      orderDetailsAsMap.put("dataSpeed", "fast");
      orderDetailsAsMap.put("deviceId", "ub327e1234");
      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(+1));
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("primaryNpanxx", "123456");
      orderDetailsAsMap.put("region", "southeast");
      orderDetailsAsMap.put("rateCenterCode", "BARKER");
      orderDetailsAsMap.put("vhoCode", "AUS2TX");
      orderDetailsAsMap.put("houseNumber", "123");
      orderDetailsAsMap.put("addressId", ban);
      orderDetailsAsMap.put("umcActInd", "I");
      orderDetailsAsMap.put("circuitId", serviceID);
      orderDetailsAsMap.put("uvOrderActionType", "UVPR");
      orderDetailsAsMap.put("installationType", "Tech");
      orderDetailsAsMap.put("amssInstallOption", "Tech");
      orderDetailsAsMap.put("completionIndicator", "NA");
      orderDetailsAsMap.put("installationType", "Tech");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("rgActIndnew", "I");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("telegenceId", "A6MCXX128450409");
      orderDetailsAsMap.put("totalBandwidth", "32.0");
      orderDetailsAsMap.put("plstActInd", "I");
      orderDetailsAsMap.put("clli8", "RENONVPP");
      orderDetailsAsMap.put("nadType", "UVRG-X");
      orderDetailsAsMap.put("transportType", NTI);
      orderDetailsAsMap.put("seventeenMhzFeat", "Disallow");
      orderDetailsAsMap.put("vectoringFeature", "Disallow");
      orderDetailsAsMap.put("rgActInd", "I");
      orderDetailsAsMap.put("modelNumber", "NVG589");
      orderDetailsAsMap.put("manufacturer", "Motorola");
      orderDetailsAsMap.put("flActInd", "I");
      orderDetailsAsMap.put("hsiaActInd", "I");
      orderDetailsAsMap.put("speed", "Hsia5x1");
      orderDetailsAsMap.put("firstName", "Alpha");
      orderDetailsAsMap.put("lastName", "Beta");
      orderDetailsAsMap.put("subType", "R");
      orderDetailsAsMap.put("timeZoneOffset", "-5");
      orderDetailsAsMap.put("transportTypeGroup", "Uverse");
      orderDetailsAsMap.put("modelNumberold", "NVG589");
      orderDetailsAsMap.put("manufacturerold", "Motorola");
      orderDetailsAsMap.put("rgActIndold", "D");
      orderDetailsAsMap.put("modelNumbernew", "5268AC");
      orderDetailsAsMap.put("manufacturernew", "2Wire");
      if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH))
      {
         orderDetailsAsMap.put("orderActionType", orderType);
         orderDetailsAsMap.put("umcActInd", "M");
         orderDetailsAsMap.put("uvOrderActionType", "UVCH");
         orderDetailsAsMap.put("amssInstallOption", "CSI");
         orderDetailsAsMap.put("installationType", "CSI");
         orderDetailsAsMap.put("technicianDispatchIndicator", "None");
         orderDetailsAsMap.put("plstActInd", "M");
         orderDetailsAsMap.put("rgActInd", "R");
         orderDetailsAsMap.put("completionIndicator", "CO");
         orderDetailsAsMap.put("hsiaActInd", "R");
         orderDetailsAsMap.put("subType", "R");
         orderDetailsAsMap.put("modelNumber", "5268AC");
         orderDetailsAsMap.put("manufacturer", "2Wire");
         orderDetailsAsMap.put("flActInd", "R");
         orderDetailsAsMap.put("addressId", ban);
         orderDetailsAsMap.put("rgActIndold", "R");

      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE))
      {

         orderDetailsAsMap.put("wdmInd", "D");
         orderDetailsAsMap.put("wlldInd", "D");
         orderDetailsAsMap.put("wsimInd", "D");
         orderDetailsAsMap.put("owaInd", "D");
         orderDetailsAsMap.put("wnadInd", "D");
         orderDetailsAsMap.put("umcActInd", "D");
         orderDetailsAsMap.put("uvOrderActionType", "UVCE");
         orderDetailsAsMap.put("plstActInd", "D");
         orderDetailsAsMap.put("rgActInd", "D");
         orderDetailsAsMap.put("flActInd", "D");
         orderDetailsAsMap.put("hsiaActInd", "D");
         orderDetailsAsMap.put("rgActIndnew", "D");
         orderDetailsAsMap.put("modelNumbernew", "NVG595");
         orderDetailsAsMap.put("manufacturernew", "Motorola");

      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_PR))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      if (StringUtils.equals(NTI, EnvConstants.NTI_IPCO))
      {
         orderDetailsAsMap.put("totalBandwidth", "18.0");
         orderDetailsAsMap.put("transportTypeGroup", "IPDSLAM");
      }
      orderDetailsAsMap = getUpdatedOrderDetails(orderDetailsAsMap, orderType, ban);
      return orderDetailsAsMap;
   }

   public Map<String, String> createFBSOrderObjectMap(String orderNumber, String origOrdId, String ban,
      String serviceID, String orderType, String orderSubType, String orderActionRefNumber, String NTI)
   {
      Map<String, String> orderDetailsAsMap = new HashMap<>();
      orderDetailsAsMap.put("ENV", propUtil.getEnv());
      orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);
      orderDetailsAsMap.put("origOrderActId", origOrdId);
      orderDetailsAsMap.put("orderNumber", orderNumber);
      orderDetailsAsMap.put("ban", ban);
      orderDetailsAsMap.put("orderActionType", orderType);
      orderDetailsAsMap.put("orderActionSubType", orderSubType);
      orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
      orderDetailsAsMap.put("serviceID", serviceID);
      orderDetailsAsMap.put("workOrderId", orderNumber);
      orderDetailsAsMap.put(EnvConstants.TAG_TASKNAME, "provisionTransport");
      orderDetailsAsMap.put("subTransportType", "");
      orderDetailsAsMap.put("dataSpeed", "fast");
      orderDetailsAsMap.put("deviceId", "ub327e1234");
      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-5));
      orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(+1));
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("primaryNpanxx", "123456");
      orderDetailsAsMap.put("region", "southeast");
      orderDetailsAsMap.put("rateCenterCode", "BARKER");
      orderDetailsAsMap.put("vhoCode", "AUS2TX");
      orderDetailsAsMap.put("houseNumber", "123");
      orderDetailsAsMap.put("addressId", ban);
      orderDetailsAsMap.put("umcActInd", "I");
      orderDetailsAsMap.put("circuitId", serviceID);
      orderDetailsAsMap.put("uvOrderActionType", "UVPR");
      orderDetailsAsMap.put("installationType", "Tech");
      orderDetailsAsMap.put("amssInstallOption", "Tech");
      orderDetailsAsMap.put("completionIndicator", "NA");
      orderDetailsAsMap.put("installationType", "Tech");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("nadType", "FBG");
      orderDetailsAsMap.put("fttbBuildingClli", "SNJSCAFO");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("telegenceId", "A6MCXX128450409");
      orderDetailsAsMap.put("totalBandwidth", "32.0");
      orderDetailsAsMap.put("plstActInd", "I");
      orderDetailsAsMap.put("clli8", "SNJSCAFO");
      orderDetailsAsMap.put("transportType", NTI);
      orderDetailsAsMap.put("seventeenMhzFeat", "Disallow");
      orderDetailsAsMap.put("vectoringFeature", "Disallow");
      orderDetailsAsMap.put("rgActIndnew", "I");
      orderDetailsAsMap.put("modelNumberold", "NVG595");
      orderDetailsAsMap.put("manufacturerold", "Motorola");
      orderDetailsAsMap.put("flActInd", "I");
      orderDetailsAsMap.put("hsiaActInd", "I");
      orderDetailsAsMap.put("speed", "12M3M");
      orderDetailsAsMap.put("firstName", "Alpha");
      orderDetailsAsMap.put("lastName", "Beta");
      orderDetailsAsMap.put("subType", "9");
      orderDetailsAsMap.put("timeZoneOffset", "-5");
      orderDetailsAsMap.put("transportTypeGroup", "Uverse");

      orderDetailsAsMap.put("EMTCLLIName", "SNJSCAFOASF");
      if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH))
      {

         orderDetailsAsMap.put("orderActionType", orderType);
         orderDetailsAsMap.put("umcActInd", "M");
         orderDetailsAsMap.put("uvOrderActionType", "UVCH");
         orderDetailsAsMap.put("amssInstallOption", "CSI");
         orderDetailsAsMap.put("installationType", "CSI");
         orderDetailsAsMap.put("technicianDispatchIndicator", "None");
         orderDetailsAsMap.put("plstActInd", "M");
         orderDetailsAsMap.put("rgActInd", "R");
         orderDetailsAsMap.put("completionIndicator", "CO");
         orderDetailsAsMap.put("hsiaActInd", "R");
         orderDetailsAsMap.put("modelNumbernew", "NVG595");
         orderDetailsAsMap.put("manufacturernew", "Motorola");
         orderDetailsAsMap.put("flActInd", "R");
         orderDetailsAsMap.put("rgActIndnew", "I");
         orderDetailsAsMap.put("rgActIndold", "D");
         orderDetailsAsMap.put("nadType1", "FBG");


      }
      orderDetailsAsMap = getUpdatedFBSOrderDetails(orderDetailsAsMap, orderType);
      return orderDetailsAsMap;
   }

   public Map<String, String> createUversePubRgNotificationChangeMap(Map<String, String> orderDetailsAsMap)
   {

      Map<String, String> pubRgMapCh = new HashMap<>();
      Date curDate = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy");
      String year = format.format(curDate);
      format = new SimpleDateFormat("MM");
      String month = format.format(curDate);
      format = new SimpleDateFormat("dd");
      String day = format.format(curDate);
      format = new SimpleDateFormat("HH");
      String hour = format.format(curDate);
      format = new SimpleDateFormat("mm");
      String minute = format.format(curDate);
      format = new SimpleDateFormat("ss");
      String sec = format.format(curDate);
      format = new SimpleDateFormat("SSS");
      String millisec = format.format(curDate);

      pubRgMapCh.put("orderNumber", orderDetailsAsMap.get("origOrderActId"));
      pubRgMapCh.put("deviceId", orderDetailsAsMap.get("origOrderActId"));
      pubRgMapCh.put("serialNumber", orderDetailsAsMap.get("origOrderActId"));
      pubRgMapCh.put("orderActionType", orderDetailsAsMap.get("orderActionType"));
      pubRgMapCh.put("year", year);
      pubRgMapCh.put("month", month);
      pubRgMapCh.put("day", day);
      pubRgMapCh.put("hour", hour);
      pubRgMapCh.put("minute", minute);
      pubRgMapCh.put("sec", sec);
      pubRgMapCh.put("millisec", millisec);
      pubRgMapCh.put("serviceId", orderDetailsAsMap.get("serviceID"));
      pubRgMapCh.put("ban", orderDetailsAsMap.get("ban"));
      pubRgMapCh.put("modelNumberRg", "5268AC");
      pubRgMapCh.put("vendorRg", "2Wire");
      return pubRgMapCh;

   }

   public Map<String, String> createPubRgNotificationMap(Map<String, String> orderDetailsAsMap)
   {

      Map<String, String> pubRgMap = new HashMap<>();
      Date curDate = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy");
      String year = format.format(curDate);
      format = new SimpleDateFormat("MM");
      String month = format.format(curDate);
      format = new SimpleDateFormat("dd");
      String day = format.format(curDate);
      format = new SimpleDateFormat("HH");
      String hour = format.format(curDate);
      format = new SimpleDateFormat("mm");
      String minute = format.format(curDate);
      format = new SimpleDateFormat("ss");
      String sec = format.format(curDate);
      format = new SimpleDateFormat("SSS");
      String millisec = format.format(curDate);

      pubRgMap.put("orderNumber", orderDetailsAsMap.get("origOrderActId"));
      pubRgMap.put("deviceId", orderDetailsAsMap.get("origOrderActId"));
      pubRgMap.put("serialNumber", orderDetailsAsMap.get("origOrderActId"));
      pubRgMap.put("orderActionType", orderDetailsAsMap.get("orderActionType"));
      pubRgMap.put("year", year);
      pubRgMap.put("month", month);
      pubRgMap.put("day", day);
      pubRgMap.put("hour", hour);
      pubRgMap.put("minute", minute);
      pubRgMap.put("sec", sec);
      pubRgMap.put("millisec", millisec);
      pubRgMap.put("serviceId", orderDetailsAsMap.get("serviceID"));
      pubRgMap.put("ban", orderDetailsAsMap.get("ban"));
      if (orderDetailsAsMap.get("transportType").equals("FTTB-C") || orderDetailsAsMap.get("transportType").equals(
         "FTTB-F"))
      {
         pubRgMap.put("modelNumberRg", "NVG595");

      }
      else
      {
         pubRgMap.put("modelNumberRg", "NVG589"); // NVG595 for FTTB-C

      }
      pubRgMap.put("vendorRg", "Motorola");
      return pubRgMap;

   }

   public Map<String, String> createInvalidPubRgNotificationMap(Map<String, String> orderDetailsAsMap, String serviceID)
   {
      Map<String, String> invalidPubRgMap = new HashMap<>();
      Date curDate = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy");
      String year = format.format(curDate);
      format = new SimpleDateFormat("MM");
      String month = format.format(curDate);
      format = new SimpleDateFormat("dd");
      String day = format.format(curDate);
      format = new SimpleDateFormat("HH");
      String hour = format.format(curDate);
      format = new SimpleDateFormat("mm");
      String minute = format.format(curDate);
      format = new SimpleDateFormat("ss");
      String sec = format.format(curDate);
      format = new SimpleDateFormat("SSS");
      String millisec = format.format(curDate);

      invalidPubRgMap.put("orderNumber", orderDetailsAsMap.get("origOrderActId"));
      invalidPubRgMap.put("deviceId", orderDetailsAsMap.get("origOrderActId"));
      invalidPubRgMap.put("serialNumber", orderDetailsAsMap.get("origOrderActId"));
      invalidPubRgMap.put("orderActionType", orderDetailsAsMap.get("orderActionType"));
      invalidPubRgMap.put("year", year);
      invalidPubRgMap.put("month", month);
      invalidPubRgMap.put("day", day);
      invalidPubRgMap.put("hour", hour);
      invalidPubRgMap.put("minute", minute);
      invalidPubRgMap.put("sec", sec);
      invalidPubRgMap.put("millisec", millisec);
      invalidPubRgMap.put("serviceId", serviceID);
      invalidPubRgMap.put("ban", orderDetailsAsMap.get("ban"));
      invalidPubRgMap.put("modelNumberRg", "ABC");
      invalidPubRgMap.put("vendorRg", "123");
      return invalidPubRgMap;
   }

   public Map<String, String> getUpdatedFBSOrderDetails(Map<String, String> orderDetailsAsMap, String orderType)
   {
      if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH))
      {

         orderDetailsAsMap.put("orderActionType", orderType);
         orderDetailsAsMap.put("umcActInd", "M");
         orderDetailsAsMap.put("uvOrderActionType", "UVCH");
         orderDetailsAsMap.put("amssInstallOption", "CSI");
         orderDetailsAsMap.put("installationType", "CSI");
         orderDetailsAsMap.put("technicianDispatchIndicator", "None");
         orderDetailsAsMap.put("plstActInd", "M");
         orderDetailsAsMap.put("rgActInd", "R");
         orderDetailsAsMap.put("completionIndicator", "CO");
         orderDetailsAsMap.put("hsiaActInd", "R");
         orderDetailsAsMap.put("modelNumbernew", "NVG595");
         orderDetailsAsMap.put("manufacturernew", "Motorola");
         orderDetailsAsMap.put("flActInd", "R");
         orderDetailsAsMap.put("rgActIndnew", "I");
         orderDetailsAsMap.put("rgActIndold", "D");
         orderDetailsAsMap.put("nadType1", "FBG");


      }
      return orderDetailsAsMap;
   }

   public Map<String, String> getUpdatedOrderDetails(Map<String, String> orderDetailsAsMap, String orderType,
      String ban)
   {
      if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH))
      {
         orderDetailsAsMap.put("orderActionType", orderType);
         orderDetailsAsMap.put("umcActInd", "M");
         orderDetailsAsMap.put("uvOrderActionType", "UVCH");
         orderDetailsAsMap.put("amssInstallOption", "CSI");
         orderDetailsAsMap.put("installationType", "CSI");
         orderDetailsAsMap.put("technicianDispatchIndicator", "None");
         orderDetailsAsMap.put("plstActInd", "M");
         orderDetailsAsMap.put("rgActInd", "R");
         orderDetailsAsMap.put("completionIndicator", "CO");
         orderDetailsAsMap.put("hsiaActInd", "R");
         orderDetailsAsMap.put("subType", "R");
         orderDetailsAsMap.put("modelNumber", "5268AC");
         orderDetailsAsMap.put("manufacturer", "2Wire");
         orderDetailsAsMap.put("flActInd", "R");
         orderDetailsAsMap.put("addressId", ban);
         orderDetailsAsMap.put("rgActIndold", "R");

      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE))
      {
         orderDetailsAsMap.put("wdmInd", "D");
         orderDetailsAsMap.put("wlldInd", "D");
         orderDetailsAsMap.put("wsimInd", "D");
         orderDetailsAsMap.put("owaInd", "D");
         orderDetailsAsMap.put("wnadInd", "D");

      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_PR))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      return orderDetailsAsMap;

   }

   public Map<String, String> createDTVOrderObjectMap(String orderNumber, String origOrdId, String ban,
      String orderActionType, String orderActionSubType)
   {
      java.util.Map<String, String> orderDetailsAsMap = new HashMap<>();

      String orderActionRefNumber = "A";

      orderDetailsAsMap.put("ENV", propUtil.getEnv());
      orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);
      orderDetailsAsMap.put("origOrderActId", origOrdId);
      orderDetailsAsMap.put("orderNumber", orderNumber);
      orderDetailsAsMap.put("ban", ban);
      orderDetailsAsMap.put("orderActionType", orderActionType);
      orderDetailsAsMap.put("orderActionSubType", orderActionSubType);
      orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
      orderDetailsAsMap.put("workOrderId", origOrdId);
      orderDetailsAsMap.put("largeCustomer", "Yes");

      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWorkOrderId", orderNumber);
      orderDetailsAsMap.put("rpiPrevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWllDataOrderNumber", orderNumber);
      orderDetailsAsMap.put("rpiWllDataOrigOrderNumber", origOrdId);
      orderDetailsAsMap.put("rpiWllDataOrdActionType", orderActionType);
      orderDetailsAsMap.put("rpiWllDataOrdActSubType", orderActionSubType);
      orderDetailsAsMap.put("rpiWllDataOrdActRef", orderActionRefNumber);
      orderDetailsAsMap.put("rpiWllDataOrdActStatus", "DE");
      orderDetailsAsMap.put("rpiWllDataDueDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("bypassReasonCode", "DO_PR");
      orderDetailsAsMap.put("postalCode", "30308");
      orderDetailsAsMap.put("city", "Los Angeles");
      orderDetailsAsMap.put("state", "CA");
      orderDetailsAsMap.put("addressId", "Hollywood Boulevard");
      orderDetailsAsMap.put("dtvmInd", "I");
      orderDetailsAsMap.put("installationType", "TECH");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("shippingIndicator", "Tech");
      orderDetailsAsMap.put("customerLine", "");
      orderDetailsAsMap.put("contractId", "");
      orderDetailsAsMap.put("propertyContractType", "");
      orderDetailsAsMap.put("supersedeIndicator", "");
      orderDetailsAsMap.put("swapaction1", "");
      orderDetailsAsMap.put("swapaction2", "");
      orderDetailsAsMap.put("swapaction3", "");
      orderDetailsAsMap.put("swapaction4", "");
      orderDetailsAsMap.put("swapaction5", "");
      orderDetailsAsMap.put("swapaction6", "");
      orderDetailsAsMap.put("prevapid1", "");
      orderDetailsAsMap.put("prevapid2", "");
      orderDetailsAsMap.put("prevapid3", "");
      orderDetailsAsMap.put("prevapid4", "");
      orderDetailsAsMap.put("prevapid5", "");
      orderDetailsAsMap.put("prevapid6", "");

      orderDetailsAsMap.put("dtcpInd", "I");
      orderDetailsAsMap.put("apid1", "1234567890");
      orderDetailsAsMap.put("dtstb1Ind", "I");
      orderDetailsAsMap.put("versionID", "1");
      orderDetailsAsMap.put("stb1Type", "DTVGenie");
      orderDetailsAsMap.put("productLine1", "IRD - GENIE 2");
      orderDetailsAsMap.put("modelNumber1", "HS17-500");
      orderDetailsAsMap.put("contractId1", "");
      orderDetailsAsMap.put("purchaseTerms1", "Rent");
      orderDetailsAsMap.put("isActive1Ind", "Y");
      orderDetailsAsMap.put("isActive1OldInd", "N");
      orderDetailsAsMap.put("macAddress1", "");
      orderDetailsAsMap.put("manufacturer1", "DIRECTV");
      orderDetailsAsMap.put("receiverID1", "REC" + ban);
      orderDetailsAsMap.put("serialNumber1", "SER" + ban);
      orderDetailsAsMap.put("accessCardNumber1", "ACC" + ban);
      orderDetailsAsMap.put("apid2", "2" + ban);
      orderDetailsAsMap.put("dtstb2Ind", "I");
      orderDetailsAsMap.put("stb2Type", "DTVGenieMini");
      orderDetailsAsMap.put("productLine2", "IRD - CLIENT");
      orderDetailsAsMap.put("modelNumber2", "C41-100");
      orderDetailsAsMap.put("contractId2", "");
      orderDetailsAsMap.put("purchaseTerms2", "Rent");
      orderDetailsAsMap.put("isActive2Ind", "Y");
      orderDetailsAsMap.put("isActive2OldInd", "N");
      orderDetailsAsMap.put("macAddress2", "ACE000ACE111");
      orderDetailsAsMap.put("manufacturer2", "DIRECTV");
      orderDetailsAsMap.put("receiverID2", "REC" + ban);
      orderDetailsAsMap.put("serialNumber2", "SER" + ban);
      orderDetailsAsMap.put("accessCardNumber2", "");
      orderDetailsAsMap.put("apid3", "3" + ban);
      orderDetailsAsMap.put("dtstb3Ind", "");
      orderDetailsAsMap.put("stb3Type", "");
      orderDetailsAsMap.put("productLine3", "");
      orderDetailsAsMap.put("modelNumber3", "");
      orderDetailsAsMap.put("contractId3", "");
      orderDetailsAsMap.put("purchaseTerms3", "");
      orderDetailsAsMap.put("isActive3Ind", "");
      orderDetailsAsMap.put("isActive3OldInd", "");
      orderDetailsAsMap.put("macAddress3", "");
      orderDetailsAsMap.put("manufacturer3", "");
      orderDetailsAsMap.put("receiverID3", "XXX" + ban);
      orderDetailsAsMap.put("serialNumber3", "");
      orderDetailsAsMap.put("accessCardNumber3", "");
      orderDetailsAsMap.put("apid4", "4" + ban);
      orderDetailsAsMap.put("dtstb4Ind", "");
      orderDetailsAsMap.put("stb4Type", "XX");
      orderDetailsAsMap.put("productLine4", "XX");
      orderDetailsAsMap.put("modelNumber4", "XX");
      orderDetailsAsMap.put("contractId4", "XX");
      orderDetailsAsMap.put("purchaseTerms4", "XX");
      orderDetailsAsMap.put("isActive4Ind", "XX");
      orderDetailsAsMap.put("isActive4OldInd", "XX");
      orderDetailsAsMap.put("macAddress4", "XX");
      orderDetailsAsMap.put("manufacturer4", "XX");
      orderDetailsAsMap.put("receiverID4", "XX");
      orderDetailsAsMap.put("serialNumber4", "XX");
      orderDetailsAsMap.put("accessCardNumber4", "XX");
      orderDetailsAsMap.put("apid5", "5" + ban);
      orderDetailsAsMap.put("dtstb5Ind", "");
      orderDetailsAsMap.put("stb5Type", "");
      orderDetailsAsMap.put("productLine5", "XX");
      orderDetailsAsMap.put("modelNumber5", "XX");
      orderDetailsAsMap.put("contractId5", "XX");
      orderDetailsAsMap.put("purchaseTerms5", "XX");
      orderDetailsAsMap.put("isActive5Ind", "XX");
      orderDetailsAsMap.put("isActive5OldInd", "XX");
      orderDetailsAsMap.put("macAddress5", "XX");
      orderDetailsAsMap.put("manufacturer5", "XX");
      orderDetailsAsMap.put("receiverID5", "XX");
      orderDetailsAsMap.put("serialNumber5", "XX");
      orderDetailsAsMap.put("accessCardNumber5", "XX");
      orderDetailsAsMap.put("apid6", "6" + ban);
      orderDetailsAsMap.put("dtstb6Ind", "");
      orderDetailsAsMap.put("stb6Type", "xx");
      orderDetailsAsMap.put("productLine6", "xx");
      orderDetailsAsMap.put("modelNumber6", "xx");
      orderDetailsAsMap.put("contractId6", "");
      orderDetailsAsMap.put("purchaseTerms6", "xx");
      orderDetailsAsMap.put("isActive6Ind", "xx");
      orderDetailsAsMap.put("isActive6OldInd", "xx");
      orderDetailsAsMap.put("macAddress6", "");
      orderDetailsAsMap.put("manufacturer6", "xx");
      orderDetailsAsMap.put("receiverID6", "xx");
      orderDetailsAsMap.put("serialNumber6", "");
      orderDetailsAsMap.put("dtaccInd", "I");
      orderDetailsAsMap.put("dtdhInd", "I");
      orderDetailsAsMap.put("dishType", "Standard");
      orderDetailsAsMap.put("apidWVB", "55" + ban);
      orderDetailsAsMap.put("dtwbInd", "I");
      orderDetailsAsMap.put("productLineWVB", "WIRELESS VIDEO BRIDGE");
      orderDetailsAsMap.put("modelNumberWVB", "WVBR0-25");
      orderDetailsAsMap.put("manufacturerWVB", "DIRECTV");
      orderDetailsAsMap.put("receiverIDWVB", "REC" + ban);
      orderDetailsAsMap.put("serialNumberWVB", "SWVB" + ban);
      orderDetailsAsMap.put("dtacrdInd", "");
      orderDetailsAsMap.put("productLinedtacrd", "XX");
      orderDetailsAsMap.put("addSrvInd", "I");
      orderDetailsAsMap.put("relocateReceiverCount", "0");
      orderDetailsAsMap.put("wholeHomeDvr", "Yes");
      orderDetailsAsMap.put("relocateDishCount", "0");
      orderDetailsAsMap.put("addInsInd", "I");
      orderDetailsAsMap.put("attDvInd", "I");
      orderDetailsAsMap.put("basePkg", "Entertainment");
      orderDetailsAsMap.put("hdAccessInd", "Y");
      orderDetailsAsMap.put("dtgp1Ind", "I");
      orderDetailsAsMap.put("dtGenrePackage1", "DIRECTVCINEMA");
      orderDetailsAsMap.put("dtgp1ContractId", "");
      orderDetailsAsMap.put("dttInd", "I");
      orderDetailsAsMap.put("countyFips", "13003");
      orderDetailsAsMap.put("purchaseTermsWvb", "Rent");
      orderDetailsAsMap.put("d2LiteEligibility", "Eligible");
      orderDetailsAsMap.put("mduConnectedProperty", "N");
      orderDetailsAsMap.put("dealerRestrictedProperty", "Y");
      orderDetailsAsMap.put("mrvIndicator", "Y");
      orderDetailsAsMap.put("swmIndicator", "MULTI-SWITCH SWM 8");
      orderDetailsAsMap.put("fourkService", "Y");
      orderDetailsAsMap.put("mpeg4RegionIndicator", "Y");
      orderDetailsAsMap.put("genieGen1Indicator", "Y");
      orderDetailsAsMap.put("nfflInd", "");
      orderDetailsAsMap.put("dealerCode", "");
      orderDetailsAsMap.put("firstName", "JON");
      orderDetailsAsMap.put("lastName", "SNOW");
      orderDetailsAsMap.put("newBAN", "");

      if (StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_CH))
      {

         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "R");
         orderDetailsAsMap.put("dtstb2Ind", "R");
         orderDetailsAsMap.put("dtwbInd", "R");
         orderDetailsAsMap.put("addSrvInd", "R");
         orderDetailsAsMap.put("addInsInd", "R");
         orderDetailsAsMap.put("attDvInd", "R");
         orderDetailsAsMap.put("dtgp1Ind", "R");
         orderDetailsAsMap.put("dttInd", "R");
         orderDetailsAsMap.put("dtstb3Ind", "");
         orderDetailsAsMap.put("stb3Type", "");
         orderDetailsAsMap.put("productLine3", "XX");
         orderDetailsAsMap.put("modelNumber3", "XX");
         orderDetailsAsMap.put("contractId3", "");
         orderDetailsAsMap.put("purchaseTerms3", "XX");
         orderDetailsAsMap.put("isActive3Ind", "XX");
         orderDetailsAsMap.put("isActive3OldInd", "XX");
         orderDetailsAsMap.put("macAddress3", "XX");
         orderDetailsAsMap.put("manufacturer3", "XX");
         orderDetailsAsMap.put("receiverID3", "XX" + ban);
         orderDetailsAsMap.put("serialNumber3", "XX" + ban);

      }
      else if (orderActionType.equals("CE"))
      {
         orderDetailsAsMap.put("dtvmInd", "D");
         orderDetailsAsMap.put("dtcpInd", "D");
         orderDetailsAsMap.put("dtstb1Ind", "D");
         orderDetailsAsMap.put("dtstb2Ind", "D");
         orderDetailsAsMap.put("dtwbInd", "D");
         orderDetailsAsMap.put("addSrvInd", "D");
         orderDetailsAsMap.put("addInsInd", "D");
         orderDetailsAsMap.put("attDvInd", "D");
         orderDetailsAsMap.put("dtgp1Ind", "D");
         orderDetailsAsMap.put("dttInd", "D");
         orderDetailsAsMap.put("dtdhInd", "D");
         orderDetailsAsMap.put("dtaccInd", "D");

      }
      else if (orderActionType.equals("PV"))
      {
         orderDetailsAsMap.put("dtvmIndCM", "D");
         orderDetailsAsMap.put("dtcpIndCM", "D");
         orderDetailsAsMap.put("dtstb1IndCM", "D");
         orderDetailsAsMap.put("dtstb2IndCM", "D");
         orderDetailsAsMap.put("dtstb3IndCM", "");
         orderDetailsAsMap.put("dtstb4IndCM", "");
         orderDetailsAsMap.put("dtstb5IndCM", "");
         orderDetailsAsMap.put("dtstb6IndCM", "");
         orderDetailsAsMap.put("dtaccIndCM", "D");
         orderDetailsAsMap.put("dtdhIndCM", "D");
         orderDetailsAsMap.put("dtwbIndCM", "D");
         orderDetailsAsMap.put("dtacrdIndCM", "D");
         orderDetailsAsMap.put("addSrvIndCM", "D");
         orderDetailsAsMap.put("addInsIndCM", "D");
         orderDetailsAsMap.put("attDvIndCM", "D");
         orderDetailsAsMap.put("dtgp1IndCM", "D");
         orderDetailsAsMap.put("dttIndCM", "D");
         orderDetailsAsMap.put("nfflInd", "N");
         orderDetailsAsMap.put("dtstb3Ind", "I");
         orderDetailsAsMap.put("stb3Type", "DTV4KGenie");
         orderDetailsAsMap.put("productLine3", "IRD - 4K CLIENT");
         orderDetailsAsMap.put("modelNumber3", "C61K-700");
         orderDetailsAsMap.put("contractId3", "");
         orderDetailsAsMap.put("purchaseTerms3", "Rent");
         orderDetailsAsMap.put("isActive3Ind", "Y");
         orderDetailsAsMap.put("isActive3OldInd", "N");
         orderDetailsAsMap.put("macAddress3", "ACE000ACEFFF");
         orderDetailsAsMap.put("manufacturer3", "DIRECTV");
         orderDetailsAsMap.put("receiverID3", "REC" + ban);
         orderDetailsAsMap.put("serialNumber3", "4K" + ban);

      }
      else if (orderActionType.equals("PR"))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      if (orderActionType.equals("CH"))
      {
         orderDetailsAsMap.put("wdmInd", "R");
         orderDetailsAsMap.put("wlldInd", "R");
         orderDetailsAsMap.put("wsimInd", "R");
         orderDetailsAsMap.put("owaInd", "R");
         orderDetailsAsMap.put("wnadInd", "R");

      }
      else if (orderActionType.equals("CE"))
      {
         orderDetailsAsMap.put("wdmInd", "D");
         orderDetailsAsMap.put("wlldInd", "D");
         orderDetailsAsMap.put("wsimInd", "D");
         orderDetailsAsMap.put("owaInd", "D");
         orderDetailsAsMap.put("wnadInd", "D");

      }
      else if (orderActionType.equals("PR"))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }
      else
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      return orderDetailsAsMap;
   }


   public Map<String, String> createDTVMultipleOrderObjectMap(String orderNumber, String origOrdId, String ban,
      String orderActionType, String orderActionSubType)
   {
      java.util.Map<String, String> orderDetailsAsMap = new HashMap<>();

      String orderActionRefNumber = "A";

      orderDetailsAsMap.put("ENV", propUtil.getEnv());
      orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);
      orderDetailsAsMap.put("origOrderActId", origOrdId);
      orderDetailsAsMap.put("orderNumber", orderNumber);
      orderDetailsAsMap.put("ban", ban);
      orderDetailsAsMap.put("orderActionType", orderActionType);
      orderDetailsAsMap.put("orderActionSubType", orderActionSubType);
      orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
      orderDetailsAsMap.put("workOrderId", origOrdId);


      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWorkOrderId", orderNumber);
      orderDetailsAsMap.put("rpiPrevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWllDataOrderNumber", orderNumber);
      orderDetailsAsMap.put("rpiWllDataOrigOrderNumber", origOrdId);
      orderDetailsAsMap.put("rpiWllDataOrdActionType", orderActionType);
      orderDetailsAsMap.put("rpiWllDataOrdActSubType", orderActionSubType);
      orderDetailsAsMap.put("rpiWllDataOrdActRef", orderActionRefNumber);
      orderDetailsAsMap.put("rpiWllDataOrdActStatus", "DE");
      orderDetailsAsMap.put("rpiWllDataDueDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("bypassReasonCode", "DO_PR");
      orderDetailsAsMap.put("postalCode", "30308");
      orderDetailsAsMap.put("city", "Los Angeles");
      orderDetailsAsMap.put("state", "CA");
      orderDetailsAsMap.put("addressId", "Hollywood Boulevard");
      orderDetailsAsMap.put("dtvmInd", "I");
      orderDetailsAsMap.put("installationType", "TECH");
      orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
      orderDetailsAsMap.put("shippingIndicator", "Tech");
      orderDetailsAsMap.put("largeCustomer", "Yes");
      orderDetailsAsMap.put("customerLine", "");
      orderDetailsAsMap.put("contractId", "");
      orderDetailsAsMap.put("propertyContractType", "NA");
      orderDetailsAsMap.put("supersedeIndicator", "");
      orderDetailsAsMap.put("swapaction1", "");
      orderDetailsAsMap.put("swapaction2", "");
      orderDetailsAsMap.put("swapaction3", "");
      orderDetailsAsMap.put("swapaction4", "");
      orderDetailsAsMap.put("swapaction5", "");
      orderDetailsAsMap.put("swapaction6", "");
      orderDetailsAsMap.put("prevapid1", "");
      orderDetailsAsMap.put("prevapid2", "");
      orderDetailsAsMap.put("prevapid3", "");
      orderDetailsAsMap.put("prevapid4", "");
      orderDetailsAsMap.put("prevapid5", "");
      orderDetailsAsMap.put("prevapid6", "");


      orderDetailsAsMap.put("dtcpInd", "I");
      orderDetailsAsMap.put("versionID", "1");

      orderDetailsAsMap.put("apid1", "1" + ban);
      orderDetailsAsMap.put("dtstb1Ind", "I");
      orderDetailsAsMap.put("stb1Type", "DTVGenieLite");
      orderDetailsAsMap.put("productLine1", "IRD - GENIE LITE");
      orderDetailsAsMap.put("modelNumber1", "H44-500");
      orderDetailsAsMap.put("contractId1", "");
      orderDetailsAsMap.put("purchaseTerms1", "Rent");
      orderDetailsAsMap.put("isActive1Ind", "Y");
      orderDetailsAsMap.put("isActive1OldInd", "N");
      orderDetailsAsMap.put("macAddress1", "");
      orderDetailsAsMap.put("manufacturer1", "DIRECTV");
      orderDetailsAsMap.put("receiverID1", "R01" + ban);
      orderDetailsAsMap.put("serialNumber1", "S01" + ban);
      orderDetailsAsMap.put("accessCardNumber1", "A01" + ban);

      orderDetailsAsMap.put("apid2", "2" + ban);
      orderDetailsAsMap.put("dtstb2Ind", "I");
      orderDetailsAsMap.put("stb2Type", "DTVGenieMini");
      orderDetailsAsMap.put("productLine2", "IRD - CLIENT");
      orderDetailsAsMap.put("modelNumber2", "C41-100");
      orderDetailsAsMap.put("contractId2", "");
      orderDetailsAsMap.put("purchaseTerms2", "Rent");
      orderDetailsAsMap.put("isActive2Ind", "Y");
      orderDetailsAsMap.put("isActive2OldInd", "N");
      orderDetailsAsMap.put("macAddress2", "M02" + ban);
      orderDetailsAsMap.put("manufacturer2", "DIRECTV");
      orderDetailsAsMap.put("receiverID2", "R01" + ban);
      orderDetailsAsMap.put("serialNumber2", "S02" + ban);
      orderDetailsAsMap.put("accessCardNumber2", "");

      orderDetailsAsMap.put("apid3", "3" + ban);
      orderDetailsAsMap.put("dtstb3Ind", "I");
      orderDetailsAsMap.put("stb3Type", "DTV4KGenie");
      orderDetailsAsMap.put("productLine3", "IRD - 4K CLIENT");
      orderDetailsAsMap.put("modelNumber3", "C61K-700");
      orderDetailsAsMap.put("contractId3", "");
      orderDetailsAsMap.put("purchaseTerms3", "Rent");
      orderDetailsAsMap.put("isActive3Ind", "Y");
      orderDetailsAsMap.put("isActive3OldInd", "N");
      orderDetailsAsMap.put("macAddress3", "M03" + ban);
      orderDetailsAsMap.put("manufacturer3", "DIRECTV");
      orderDetailsAsMap.put("receiverID3", "R01" + ban);
      orderDetailsAsMap.put("serialNumber3", "S03" + ban);
      orderDetailsAsMap.put("accessCardNumber3", "");

      orderDetailsAsMap.put("apid4", "4" + ban);
      orderDetailsAsMap.put("dtstb4Ind", "I");
      orderDetailsAsMap.put("stb4Type", "DTVSDDVR");
      orderDetailsAsMap.put("productLine4", "IRD - DVR");
      orderDetailsAsMap.put("modelNumber4", "R15-500");
      orderDetailsAsMap.put("contractId4", "");
      orderDetailsAsMap.put("purchaseTerms4", "Rent");
      orderDetailsAsMap.put("isActive4Ind", "Y");
      orderDetailsAsMap.put("isActive4OldInd", "N");
      orderDetailsAsMap.put("macAddress4", "");
      orderDetailsAsMap.put("manufacturer4", "DIRECTV");
      orderDetailsAsMap.put("receiverID4", "R04" + ban);
      orderDetailsAsMap.put("serialNumber4", "S04" + ban);
      orderDetailsAsMap.put("accessCardNumber4", "A04" + ban);

      orderDetailsAsMap.put("apid5", "5" + ban);
      orderDetailsAsMap.put("dtstb5Ind", "I");
      orderDetailsAsMap.put("stb5Type", "DTVWirelessGenie");
      orderDetailsAsMap.put("productLine5", "IRD - WIRELESS CLIENT");
      orderDetailsAsMap.put("modelNumber5", "C61W-700");
      orderDetailsAsMap.put("contractId5", "");
      orderDetailsAsMap.put("purchaseTerms5", "Rent");
      orderDetailsAsMap.put("isActive5Ind", "Y");
      orderDetailsAsMap.put("isActive5OldInd", "N");
      orderDetailsAsMap.put("macAddress5", "M05" + ban);
      orderDetailsAsMap.put("manufacturer5", "DIRECTV");
      orderDetailsAsMap.put("receiverID5", "R04" + ban);
      orderDetailsAsMap.put("serialNumber5", "S05" + ban);
      orderDetailsAsMap.put("accessCardNumber5", "");

      orderDetailsAsMap.put("apid6", "6" + ban);
      orderDetailsAsMap.put("dtstb6Ind", "I");
      orderDetailsAsMap.put("stb6Type", "DTVGenieMini");
      orderDetailsAsMap.put("productLine6", "IRD - CLIENT");
      orderDetailsAsMap.put("modelNumber6", "C51-500");
      orderDetailsAsMap.put("contractId6", "");
      orderDetailsAsMap.put("purchaseTerms6", "Rent");
      orderDetailsAsMap.put("isActive6Ind", "Y");
      orderDetailsAsMap.put("isActive6OldInd", "N");
      orderDetailsAsMap.put("macAddress6", "M06" + ban);
      orderDetailsAsMap.put("manufacturer6", "DIRECTV");
      orderDetailsAsMap.put("receiverID6", "R04" + ban);
      orderDetailsAsMap.put("serialNumber6", "S06" + ban);
      orderDetailsAsMap.put("accessCardNumber6", "");

      orderDetailsAsMap.put("apid7", "7" + ban);
      orderDetailsAsMap.put("dtstb7Ind", "I");
      orderDetailsAsMap.put("stb7Type", "DTVGenie");
      orderDetailsAsMap.put("productLine7", "IRD - ADVANCED WHOLE-HOME DVR");
      orderDetailsAsMap.put("modelNumber7", "HR54-200");
      orderDetailsAsMap.put("contractId7", "");
      orderDetailsAsMap.put("purchaseTerms7", "Rent");
      orderDetailsAsMap.put("isActive7Ind", "");
      orderDetailsAsMap.put("isActive7OldInd", "");
      orderDetailsAsMap.put("macAddress7", "");
      orderDetailsAsMap.put("manufacturer7", "DIRECTV");
      orderDetailsAsMap.put("receiverID7", "R07" + ban);
      orderDetailsAsMap.put("serialNumber7", "S07" + ban);
      orderDetailsAsMap.put("accessCardNumber7", "A07" + ban);

      orderDetailsAsMap.put("apid8", "8" + ban);
      orderDetailsAsMap.put("dtstb8Ind", "I");
      orderDetailsAsMap.put("stb8Type", "DTVWirelessGenie");
      orderDetailsAsMap.put("productLine8", "IRD - WIRELESS CLIENT");
      orderDetailsAsMap.put("modelNumber8", "C41W-500");
      orderDetailsAsMap.put("contractId8", "");
      orderDetailsAsMap.put("purchaseTerms8", "Rent");
      orderDetailsAsMap.put("isActive8Ind", "");
      orderDetailsAsMap.put("isActive8OldInd", "");
      orderDetailsAsMap.put("macAddress8", "M08" + ban);
      orderDetailsAsMap.put("manufacturer8", "DIRECTV");
      orderDetailsAsMap.put("receiverID8", "R07" + ban);
      orderDetailsAsMap.put("serialNumber8", "S08" + ban);
      orderDetailsAsMap.put("accessCardNumber8", "");

      orderDetailsAsMap.put("apid9", "9" + ban);
      orderDetailsAsMap.put("dtstb9Ind", "I");
      orderDetailsAsMap.put("stb9Type", "DTVGenieMini");
      orderDetailsAsMap.put("productLine9", "IRD - CLIENT");
      orderDetailsAsMap.put("modelNumber9", "C61-500");
      orderDetailsAsMap.put("contractId9", "");
      orderDetailsAsMap.put("purchaseTerms9", "Rent");
      orderDetailsAsMap.put("isActive9Ind", "");
      orderDetailsAsMap.put("isActive9OldInd", "");
      orderDetailsAsMap.put("macAddress9", "M09" + ban);
      orderDetailsAsMap.put("manufacturer9", "DIRECTV");
      orderDetailsAsMap.put("receiverID9", "R07" + ban);
      orderDetailsAsMap.put("serialNumber9", "S09" + ban);
      orderDetailsAsMap.put("accessCardNumber9", "");


      // orderDetailsAsMap.put("dtcpInd", "I");
      // orderDetailsAsMap.put("versionID", "1");
      // orderDetailsAsMap.put("apid1", "1" + ban);
      // orderDetailsAsMap.put("dtstb1Ind", "I");
      // orderDetailsAsMap.put("stb1Type", "DTVGenieLite");
      // orderDetailsAsMap.put("productLine1", "IRD - GENIE LITE");
      // orderDetailsAsMap.put("modelNumber1", "H44-500");
      // orderDetailsAsMap.put("contractId1", "");
      // orderDetailsAsMap.put("purchaseTerms1", "BYOE");
      // orderDetailsAsMap.put("isActive1Ind", "Y");
      // orderDetailsAsMap.put("isActive1OldInd", "N");
      // orderDetailsAsMap.put("macAddress1", "");
      // orderDetailsAsMap.put("manufacturer1", "DIRECTV");
      // orderDetailsAsMap.put("receiverID1", "R01" + ban);
      // orderDetailsAsMap.put("serialNumber1", "S01" + ban);
      // orderDetailsAsMap.put("accessCardNumber1", "A01" + ban);
      //
      // orderDetailsAsMap.put("apid2", "2" + ban);
      // orderDetailsAsMap.put("dtstb2Ind", "I");
      // orderDetailsAsMap.put("stb2Type", "DTVGenieMini");
      // orderDetailsAsMap.put("productLine2", "IRD - CLIENT");
      // orderDetailsAsMap.put("modelNumber2", "C41-100");
      // orderDetailsAsMap.put("contractId2", "");
      // orderDetailsAsMap.put("purchaseTerms2", "BYOE");
      // orderDetailsAsMap.put("isActive2Ind", "Y");
      // orderDetailsAsMap.put("isActive2OldInd", "N");
      // orderDetailsAsMap.put("macAddress2", "M02" + ban);
      // orderDetailsAsMap.put("manufacturer2", "DIRECTV");
      // orderDetailsAsMap.put("receiverID2", "R01" + ban);
      // orderDetailsAsMap.put("serialNumber2", "S02" + ban);
      // orderDetailsAsMap.put("accessCardNumber2", "");
      //
      // orderDetailsAsMap.put("apid3", "3" + ban);
      // orderDetailsAsMap.put("dtstb3Ind", "I");
      // orderDetailsAsMap.put("stb3Type", "DTV4KGenie");
      // orderDetailsAsMap.put("productLine3", "IRD - 4K CLIENT");
      // orderDetailsAsMap.put("modelNumber3", "C61K-700");
      // orderDetailsAsMap.put("contractId3", "");
      // orderDetailsAsMap.put("purchaseTerms3", "BYOE");
      // orderDetailsAsMap.put("isActive3Ind", "Y");
      // orderDetailsAsMap.put("isActive3OldInd", "N");
      // orderDetailsAsMap.put("macAddress3","M03" + ban);
      // orderDetailsAsMap.put("manufacturer3", "DIRECTV");
      // orderDetailsAsMap.put("receiverID3", "R01" + ban);
      // orderDetailsAsMap.put("serialNumber3", "S03" + ban);
      // orderDetailsAsMap.put("accessCardNumber3", "");
      //
      // orderDetailsAsMap.put("apid4", "4" + ban);
      // orderDetailsAsMap.put("dtstb4Ind", "I");
      // orderDetailsAsMap.put("stb4Type", "DTVSDDVR");
      // orderDetailsAsMap.put("productLine4", "IRD - DVR");
      // orderDetailsAsMap.put("modelNumber4", "R15-500");
      // orderDetailsAsMap.put("contractId4", "");
      // orderDetailsAsMap.put("purchaseTerms4", "BYOE");
      // orderDetailsAsMap.put("isActive4Ind", "Y");
      // orderDetailsAsMap.put("isActive4OldInd", "N");
      // orderDetailsAsMap.put("macAddress4", "");
      // orderDetailsAsMap.put("manufacturer4", "DIRECTV");
      // orderDetailsAsMap.put("receiverID4", "R04" + ban);
      // orderDetailsAsMap.put("serialNumber4", "S04" + ban);
      // orderDetailsAsMap.put("accessCardNumber4", "A04" + ban);
      //
      // orderDetailsAsMap.put("apid5", "5" + ban);
      // orderDetailsAsMap.put("dtstb5Ind", "I");
      // orderDetailsAsMap.put("stb5Type", "DTVWirelessGenie");
      // orderDetailsAsMap.put("productLine5", "IRD - WIRELESS CLIENT");
      // orderDetailsAsMap.put("modelNumber5", "C61W-700");
      // orderDetailsAsMap.put("contractId5", "");
      // orderDetailsAsMap.put("purchaseTerms5", "BYOE");
      // orderDetailsAsMap.put("isActive5Ind", "Y");
      // orderDetailsAsMap.put("isActive5OldInd", "N");
      // orderDetailsAsMap.put("macAddress5", "M05" + ban);
      // orderDetailsAsMap.put("manufacturer5", "DIRECTV");
      // orderDetailsAsMap.put("receiverID5", "R04" + ban);
      // orderDetailsAsMap.put("serialNumber5", "S05" + ban);
      // orderDetailsAsMap.put("accessCardNumber5", "");
      //
      // orderDetailsAsMap.put("apid6", "6" + ban);
      // orderDetailsAsMap.put("dtstb6Ind", "I");
      // orderDetailsAsMap.put("stb6Type", "DTVGenieMini");
      // orderDetailsAsMap.put("productLine6", "IRD - CLIENT");
      // orderDetailsAsMap.put("modelNumber6", "C51-500");
      // orderDetailsAsMap.put("contractId6", "");
      // orderDetailsAsMap.put("purchaseTerms6", "BYOE");
      // orderDetailsAsMap.put("isActive6Ind", "Y");
      // orderDetailsAsMap.put("isActive6OldInd", "N");
      // orderDetailsAsMap.put("macAddress6", "M06" + ban);
      // orderDetailsAsMap.put("manufacturer6", "DIRECTV");
      // orderDetailsAsMap.put("receiverID6", "R04" + ban);
      // orderDetailsAsMap.put("serialNumber6", "S06" + ban);
      // orderDetailsAsMap.put("accessCardNumber6", "");
      //
      // orderDetailsAsMap.put("apid7", "7" + ban);
      // orderDetailsAsMap.put("dtstb7Ind", "I");
      // orderDetailsAsMap.put("stb7Type", "DTVGenie");
      // orderDetailsAsMap.put("productLine7", "IRD - ADVANCED WHOLE-HOME DVR");
      // orderDetailsAsMap.put("modelNumber7", "HR54-200");
      // orderDetailsAsMap.put("contractId7", "");
      // orderDetailsAsMap.put("purchaseTerms7", "BYOE");
      // orderDetailsAsMap.put("isActive7Ind", "");
      // orderDetailsAsMap.put("isActive7OldInd", "");
      // orderDetailsAsMap.put("macAddress7", "");
      // orderDetailsAsMap.put("manufacturer7", "DIRECTV");
      // orderDetailsAsMap.put("receiverID7", "R07" + ban);
      // orderDetailsAsMap.put("serialNumber7", "S07" + ban);
      // orderDetailsAsMap.put("accessCardNumber7", "A07" + ban);
      //
      // orderDetailsAsMap.put("apid8", "8" + ban);
      // orderDetailsAsMap.put("dtstb8Ind", "I");
      // orderDetailsAsMap.put("stb8Type", "DTVWirelessGenie");
      // orderDetailsAsMap.put("productLine8", "IRD - WIRELESS CLIENT");
      // orderDetailsAsMap.put("modelNumber8", "C41W-500");
      // orderDetailsAsMap.put("contractId8", "");
      // orderDetailsAsMap.put("purchaseTerms8", "BYOE");
      // orderDetailsAsMap.put("isActive8Ind", "");
      // orderDetailsAsMap.put("isActive8OldInd", "");
      // orderDetailsAsMap.put("macAddress8", "M08" + ban);
      // orderDetailsAsMap.put("manufacturer8", "DIRECTV");
      // orderDetailsAsMap.put("receiverID8", "R07" + ban);
      // orderDetailsAsMap.put("serialNumber8", "S08" + ban);
      // orderDetailsAsMap.put("accessCardNumber8", "");
      //
      // orderDetailsAsMap.put("apid9", "9" + ban);
      // orderDetailsAsMap.put("dtstb9Ind", "I");
      // orderDetailsAsMap.put("stb9Type", "DTVGenieMini");
      // orderDetailsAsMap.put("productLine9", "IRD - CLIENT");
      // orderDetailsAsMap.put("modelNumber9", "C61-500");
      // orderDetailsAsMap.put("contractId9", "");
      // orderDetailsAsMap.put("purchaseTerms9", "BYOE");
      // orderDetailsAsMap.put("isActive9Ind", "");
      // orderDetailsAsMap.put("isActive9OldInd", "");
      // orderDetailsAsMap.put("macAddress9", "M09" + ban);
      // orderDetailsAsMap.put("manufacturer9", "DIRECTV");
      // orderDetailsAsMap.put("receiverID9", "R07" + ban);
      // orderDetailsAsMap.put("serialNumber9", "S09" + ban);
      // orderDetailsAsMap.put("accessCardNumber9", "");


      orderDetailsAsMap.put("dtaccInd", "I");
      orderDetailsAsMap.put("dtdhInd", "I");
      orderDetailsAsMap.put("dishType", "Standard");
      orderDetailsAsMap.put("apidWVB", "55" + ban);
      orderDetailsAsMap.put("dtwbInd", "I");
      orderDetailsAsMap.put("productLineWVB", "WIRELESS VIDEO BRIDGE");
      orderDetailsAsMap.put("modelNumberWVB", "WVBR0-25");
      orderDetailsAsMap.put("manufacturerWVB", "DIRECTV");
      orderDetailsAsMap.put("receiverIDWVB", "REC" + ban);
      orderDetailsAsMap.put("serialNumberWVB", "SWVB" + ban);
      orderDetailsAsMap.put("dtacrdInd", "");
      orderDetailsAsMap.put("productLinedtacrd", "XX");
      orderDetailsAsMap.put("addSrvInd", "I");
      orderDetailsAsMap.put("relocateReceiverCount", "0");
      orderDetailsAsMap.put("wholeHomeDvr", "Yes");
      orderDetailsAsMap.put("relocateDishCount", "0");
      orderDetailsAsMap.put("addInsInd", "I");
      orderDetailsAsMap.put("attDvInd", "I");
      orderDetailsAsMap.put("basePkg", "Entertainment");
      orderDetailsAsMap.put("hdAccessInd", "Y");
      orderDetailsAsMap.put("dtgp1Ind", "I");
      orderDetailsAsMap.put("dtGenrePackage1", "DIRECTVCINEMA");
      orderDetailsAsMap.put("dtgp1ContractId", "");
      orderDetailsAsMap.put("dttInd", "I");
      orderDetailsAsMap.put("countyFips", "13003");
      orderDetailsAsMap.put("purchaseTermsWvb", "Rent");
      orderDetailsAsMap.put("d2LiteEligibility", "Eligible");
      orderDetailsAsMap.put("mduConnectedProperty", "N");
      orderDetailsAsMap.put("dealerRestrictedProperty", "Y");
      orderDetailsAsMap.put("mrvIndicator", "Y");
      orderDetailsAsMap.put("swmIndicator", "MULTI-SWITCH SWM 8");
      orderDetailsAsMap.put("fourkService", "Y");
      orderDetailsAsMap.put("mpeg4RegionIndicator", "Y");
      orderDetailsAsMap.put("genieGen1Indicator", "Y");
      orderDetailsAsMap.put("nfflInd", "");
      orderDetailsAsMap.put("dealerCode", "");
      orderDetailsAsMap.put("firstName", "JON");
      orderDetailsAsMap.put("lastName", "SNOW");
      orderDetailsAsMap.put("newBAN", "");

      if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_CH)) && (StringUtils.equals(orderActionSubType,
         EnvConstants.ORDERSUBTYPE_NA)))
      {


         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "R");
         orderDetailsAsMap.put("dtstb2Ind", "R");
         orderDetailsAsMap.put("dtstb3Ind", "R");
         orderDetailsAsMap.put("dtstb4Ind", "R");
         orderDetailsAsMap.put("dtstb5Ind", "R");
         orderDetailsAsMap.put("dtstb6Ind", "R");
         orderDetailsAsMap.put("dtstb7Ind", "D");
         // orderDetailsAsMap.put("swapaction7", "");
         // orderDetailsAsMap.put("prevapid7", "");
         orderDetailsAsMap.put("dtstb8Ind", "R");
         orderDetailsAsMap.put("dtstb9Ind", "R");
         // orderDetailsAsMap.put("apid10", "10" + ban);
         // orderDetailsAsMap.put("dtstb10Ind", "");
         // orderDetailsAsMap.put("stb10Type", "DTVSDDVR");
         // orderDetailsAsMap.put("productLine10", "IRD - DVR");
         // orderDetailsAsMap.put("modelNumber10", "R15-100");
         // orderDetailsAsMap.put("contractId10", "");
         // orderDetailsAsMap.put("purchaseTerms10", "BYOE");
         // orderDetailsAsMap.put("isActive10Ind", "Y");
         // orderDetailsAsMap.put("isActive10OldInd", "N");
         // orderDetailsAsMap.put("macAddress10", "");
         // orderDetailsAsMap.put("manufacturer10", "DIRECTV");
         // orderDetailsAsMap.put("receiverID10", "R10" + ban);
         // orderDetailsAsMap.put("serialNumber10", "S10" + ban);
         // orderDetailsAsMap.put("accessCardNumber10", "A10" + ban);

         orderDetailsAsMap.put("apid11", "11" + ban);
         orderDetailsAsMap.put("dtstb11Ind", "I");
         orderDetailsAsMap.put("stb11Type", "DTVWirelessGenie");
         orderDetailsAsMap.put("productLine11", "IRD - WIRELESS CLIENT");
         orderDetailsAsMap.put("modelNumber11", "");
         orderDetailsAsMap.put("contractId11", "");
         orderDetailsAsMap.put("purchaseTerms11", "Rent");
         orderDetailsAsMap.put("isActive11Ind", "Y");
         orderDetailsAsMap.put("isActive11OldInd", "N");
         orderDetailsAsMap.put("macAddress11", "");
         orderDetailsAsMap.put("manufacturer11", "DIRECTV");
         orderDetailsAsMap.put("receiverID11", "R10" + ban);
         orderDetailsAsMap.put("serialNumber11", "S11" + ban);
         orderDetailsAsMap.put("accessCardNumber11", "A11" + ban);

         orderDetailsAsMap.put("dtwbInd", "R");
         orderDetailsAsMap.put("addSrvInd", "R");
         orderDetailsAsMap.put("addInsInd", "R");
         orderDetailsAsMap.put("attDvInd", "R");
         orderDetailsAsMap.put("dtgp1Ind", "R");
         orderDetailsAsMap.put("dttInd", "R");

      }
      else if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_CH)) && (StringUtils.equals(
         orderActionSubType, EnvConstants.ORDERSUBTYPE_AM)))
      {
         orderActionRefNumber = "B";
         orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "R");
         orderDetailsAsMap.put("dtstb2Ind", "R");
         orderDetailsAsMap.put("dtstb3Ind", "R");
         orderDetailsAsMap.put("dtstb4Ind", "R");
         orderDetailsAsMap.put("dtstb5Ind", "R");
         orderDetailsAsMap.put("dtstb6Ind", "R");
         orderDetailsAsMap.put("dtstb7Ind", "D");
         // orderDetailsAsMap.put("swapaction7", "");
         // orderDetailsAsMap.put("prevapid7", "");
         // orderDetailsAsMap.put("dtstb8Ind", "R");
         // orderDetailsAsMap.put("dtstb9Ind", "R");
         // orderDetailsAsMap.put("apid10", "10" + ban);
         // orderDetailsAsMap.put("dtstb10Ind", "");
         // orderDetailsAsMap.put("stb10Type", "DTVSDDVR");
         // orderDetailsAsMap.put("productLine10", "IRD - DVR");
         // orderDetailsAsMap.put("modelNumber10", "R15-100");
         // orderDetailsAsMap.put("contractId10", "");
         // orderDetailsAsMap.put("purchaseTerms10", "BYOE");
         // orderDetailsAsMap.put("isActive10Ind", "Y");
         // orderDetailsAsMap.put("isActive10OldInd", "N");
         // orderDetailsAsMap.put("macAddress10", "");
         // orderDetailsAsMap.put("manufacturer10", "DIRECTV");
         // orderDetailsAsMap.put("receiverID10", "R10" + ban);
         // orderDetailsAsMap.put("serialNumber10", "S10" + ban);
         // orderDetailsAsMap.put("accessCardNumber10", "A10" + ban);
         orderDetailsAsMap.put("dtwbInd", "R");
         orderDetailsAsMap.put("addSrvInd", "R");
         orderDetailsAsMap.put("addInsInd", "R");
         orderDetailsAsMap.put("attDvInd", "R");
         orderDetailsAsMap.put("dtgp1Ind", "R");
         orderDetailsAsMap.put("dttInd", "R");
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(+2)); //future DD
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-2)); //previous DD
         orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1)); // same DD

      }
      else if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_SU)) && (StringUtils.equals(
         orderActionSubType, EnvConstants.ORDERSUBTYPE_AM)))
      {
         orderActionRefNumber = "B";
         orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "M");
         orderDetailsAsMap.put("dtstb2Ind", "M");
         orderDetailsAsMap.put("dtstb3Ind", "M");
         orderDetailsAsMap.put("dtstb4Ind", "M");
         orderDetailsAsMap.put("dtstb5Ind", "M");
         orderDetailsAsMap.put("dtstb6Ind", "M");
         orderDetailsAsMap.put("dtstb7Ind", "M");
         orderDetailsAsMap.put("dtstb8Ind", "");


         orderDetailsAsMap.put("dtwbInd", "M");
         orderDetailsAsMap.put("addSrvInd", "M");
         orderDetailsAsMap.put("addInsInd", "M");
         orderDetailsAsMap.put("attDvInd", "M");
         orderDetailsAsMap.put("dtgp1Ind", "M");
         orderDetailsAsMap.put("dttInd", "M");
         orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(+2)); // future DD
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1)); //previous DD
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(0)); //same DD
      }
      else if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_SU)) && (StringUtils.equals(
         orderActionSubType, EnvConstants.ORDERSUBTYPE_NA)))
      {


         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "M");
         orderDetailsAsMap.put("dtstb2Ind", "M");
         orderDetailsAsMap.put("dtstb3Ind", "M");
         orderDetailsAsMap.put("dtstb4Ind", "M");
         orderDetailsAsMap.put("dtstb5Ind", "M");
         orderDetailsAsMap.put("dtstb6Ind", "M");
         orderDetailsAsMap.put("dtstb7Ind", "M");


         orderDetailsAsMap.put("dtwbInd", "M");
         orderDetailsAsMap.put("addSrvInd", "M");
         orderDetailsAsMap.put("addInsInd", "M");
         orderDetailsAsMap.put("attDvInd", "M");
         orderDetailsAsMap.put("dtgp1Ind", "M");
         orderDetailsAsMap.put("dttInd", "M");


      }
      else if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_RS)) && (StringUtils.equals(
         orderActionSubType, EnvConstants.ORDERSUBTYPE_NA)))
      {
         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "M");
         orderDetailsAsMap.put("dtstb2Ind", "M");
         orderDetailsAsMap.put("dtstb3Ind", "M");
         orderDetailsAsMap.put("dtstb4Ind", "M");
         orderDetailsAsMap.put("dtstb5Ind", "M");
         orderDetailsAsMap.put("dtstb6Ind", "M");
         orderDetailsAsMap.put("dtstb7Ind", "M");

         orderDetailsAsMap.put("dtwbInd", "M");
         orderDetailsAsMap.put("addSrvInd", "M");
         orderDetailsAsMap.put("addInsInd", "M");
         orderDetailsAsMap.put("attDvInd", "M");
         orderDetailsAsMap.put("dtgp1Ind", "M");
         orderDetailsAsMap.put("dttInd", "M");

      }
      else if ((StringUtils.equals(orderActionType, EnvConstants.ORDERTYPE_RS)) && (StringUtils.equals(
         orderActionSubType, EnvConstants.ORDERSUBTYPE_AM)))
      {
         orderActionRefNumber = "B";
         orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
         orderDetailsAsMap.put("dtvmInd", "M");
         orderDetailsAsMap.put("dtcpInd", "M");
         orderDetailsAsMap.put("dtstb1Ind", "M");
         orderDetailsAsMap.put("dtstb2Ind", "M");
         orderDetailsAsMap.put("dtstb3Ind", "M");
         orderDetailsAsMap.put("dtstb4Ind", "M");
         orderDetailsAsMap.put("dtstb5Ind", "M");
         orderDetailsAsMap.put("dtstb6Ind", "M");
         orderDetailsAsMap.put("dtstb7Ind", "M");

         orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(+2)); // future DD
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1)); //previous DD
         // orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-0)); //same DD
         orderDetailsAsMap.put("dtwbInd", "M");
         orderDetailsAsMap.put("addSrvInd", "M");
         orderDetailsAsMap.put("addInsInd", "M");
         orderDetailsAsMap.put("attDvInd", "M");
         orderDetailsAsMap.put("dtgp1Ind", "M");
         orderDetailsAsMap.put("dttInd", "M");

      }


      else if (orderActionType.equals("CE"))
      {
         orderDetailsAsMap.put("dtvmInd", "D");
         orderDetailsAsMap.put("dtcpInd", "D");
         orderDetailsAsMap.put("dtstb1Ind", "D");
         orderDetailsAsMap.put("dtstb2Ind", "D");
         orderDetailsAsMap.put("dtwbInd", "D");
         orderDetailsAsMap.put("addSrvInd", "D");
         orderDetailsAsMap.put("addInsInd", "D");
         orderDetailsAsMap.put("attDvInd", "D");
         orderDetailsAsMap.put("dtgp1Ind", "D");
         orderDetailsAsMap.put("dttInd", "D");
         orderDetailsAsMap.put("dtdhInd", "D");
         orderDetailsAsMap.put("dtaccInd", "D");

      }
      else if (orderActionType.equals("PV"))
      {
         orderDetailsAsMap.put("dtvmIndCM", "D");
         orderDetailsAsMap.put("largeCustomer", "Yes");
         orderDetailsAsMap.put("dtcpIndCM", "D");
         orderDetailsAsMap.put("dtstb1IndCM", "D");
         orderDetailsAsMap.put("dtstb2IndCM", "D");
         orderDetailsAsMap.put("dtstb3IndCM", "D");
         orderDetailsAsMap.put("dtstb4IndCM", "D");
         orderDetailsAsMap.put("dtstb5IndCM", "D");
         orderDetailsAsMap.put("dtstb6IndCM", "D");
         orderDetailsAsMap.put("dtstb7IndCM", "D");
         orderDetailsAsMap.put("dtstb8IndCM", "D");
         orderDetailsAsMap.put("dtstb9IndCM", "");
         orderDetailsAsMap.put("dtaccIndCM", "D");
         orderDetailsAsMap.put("dtdhIndCM", "D");
         orderDetailsAsMap.put("dtwbIndCM", "D");
         orderDetailsAsMap.put("dtacrdIndCM", "D");
         orderDetailsAsMap.put("addSrvIndCM", "D");
         orderDetailsAsMap.put("addInsIndCM", "D");
         orderDetailsAsMap.put("attDvIndCM", "D");
         orderDetailsAsMap.put("dtgp1IndCM", "D");
         orderDetailsAsMap.put("dttIndCM", "D");
         orderDetailsAsMap.put("nfflInd", "N");


         orderDetailsAsMap.put("ENV", propUtil.getEnv());
         orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);
         orderDetailsAsMap.put("origOrderActId", origOrdId);
         orderDetailsAsMap.put("orderNumber", orderNumber);
         orderDetailsAsMap.put("ban", ban);
         orderDetailsAsMap.put("orderActionType", orderActionType);
         orderDetailsAsMap.put("orderActionSubType", orderActionSubType);
         orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
         orderDetailsAsMap.put("workOrderId", origOrdId);
         orderActionRefNumber = "A";
         orderDetailsAsMap.put("tnPrefix", "2");
         orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1));
         orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(-1));
         orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(-1));
         orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(-1));
         orderDetailsAsMap.put("prevWorkOrderId", "");
         orderDetailsAsMap.put("rpiWorkOrderId", orderNumber);
         orderDetailsAsMap.put("rpiPrevWorkOrderId", "");
         orderDetailsAsMap.put("rpiWllDataOrderNumber", orderNumber);
         orderDetailsAsMap.put("rpiWllDataOrigOrderNumber", origOrdId);
         orderDetailsAsMap.put("rpiWllDataOrdActionType", orderActionType);
         orderDetailsAsMap.put("rpiWllDataOrdActSubType", orderActionSubType);
         orderDetailsAsMap.put("rpiWllDataOrdActRef", orderActionRefNumber);
         orderDetailsAsMap.put("rpiWllDataOrdActStatus", "DE");
         orderDetailsAsMap.put("rpiWllDataDueDate", Utils.getExtendedDateByDays(-1));
         orderDetailsAsMap.put("bypassReasonCode", "DO_PR");
         orderDetailsAsMap.put("postalCode", "30308");
         orderDetailsAsMap.put("city", "Los Angeles");
         orderDetailsAsMap.put("state", "CA");
         orderDetailsAsMap.put("addressId", "Hollywood Boulevard");
         orderDetailsAsMap.put("dtvmInd", "I");
         orderDetailsAsMap.put("installationType", "TECH");
         orderDetailsAsMap.put("technicianDispatchIndicator", "Install");
         orderDetailsAsMap.put("shippingIndicator", "Tech");
         orderDetailsAsMap.put("customerLine", "");
         orderDetailsAsMap.put("contractId", "");
         orderDetailsAsMap.put("propertyContractType", "NA");
         orderDetailsAsMap.put("supersedeIndicator", "");
         orderDetailsAsMap.put("largeCustomer", "Yes");

         orderDetailsAsMap.put("dtcpInd", "I");
         orderDetailsAsMap.put("versionID", "1");


         orderDetailsAsMap.put("apid1", "1" + ban);
         orderDetailsAsMap.put("dtstb1Ind", "I");
         orderDetailsAsMap.put("stb1Type", "DTVGenieLite");
         orderDetailsAsMap.put("productLine1", "IRD - GENIE LITE");
         orderDetailsAsMap.put("modelNumber1", "H44-500");
         orderDetailsAsMap.put("contractId1", "");
         orderDetailsAsMap.put("purchaseTerms1", "Rent");
         orderDetailsAsMap.put("isActive1Ind", "Y");
         orderDetailsAsMap.put("isActive1OldInd", "N");
         orderDetailsAsMap.put("macAddress1", "");
         orderDetailsAsMap.put("manufacturer1", "DIRECTV");
         orderDetailsAsMap.put("receiverID1", "R01" + ban);
         orderDetailsAsMap.put("serialNumber1", "S01" + ban);
         orderDetailsAsMap.put("accessCardNumber1", "A01" + ban);

         orderDetailsAsMap.put("apid2", "2" + ban);
         orderDetailsAsMap.put("dtstb2Ind", "I");
         orderDetailsAsMap.put("stb2Type", "DTVGenieMini");
         orderDetailsAsMap.put("productLine2", "IRD - CLIENT");
         orderDetailsAsMap.put("modelNumber2", "C41-100");
         orderDetailsAsMap.put("contractId2", "");
         orderDetailsAsMap.put("purchaseTerms2", "Rent");
         orderDetailsAsMap.put("isActive2Ind", "Y");
         orderDetailsAsMap.put("isActive2OldInd", "N");
         orderDetailsAsMap.put("macAddress2", "M02" + ban);
         orderDetailsAsMap.put("manufacturer2", "DIRECTV");
         orderDetailsAsMap.put("receiverID2", "R01" + ban);
         orderDetailsAsMap.put("serialNumber2", "S02" + ban);
         orderDetailsAsMap.put("accessCardNumber2", "");

         orderDetailsAsMap.put("apid3", "3" + ban);
         orderDetailsAsMap.put("dtstb3Ind", "I");
         orderDetailsAsMap.put("stb3Type", "DTV4KGenie");
         orderDetailsAsMap.put("productLine3", "IRD - 4K CLIENT");
         orderDetailsAsMap.put("modelNumber3", "C61K-700");
         orderDetailsAsMap.put("contractId3", "");
         orderDetailsAsMap.put("purchaseTerms3", "Rent");
         orderDetailsAsMap.put("isActive3Ind", "Y");
         orderDetailsAsMap.put("isActive3OldInd", "N");
         orderDetailsAsMap.put("macAddress3", "M03" + ban);
         orderDetailsAsMap.put("manufacturer3", "DIRECTV");
         orderDetailsAsMap.put("receiverID3", "R01" + ban);
         orderDetailsAsMap.put("serialNumber3", "S03" + ban);
         orderDetailsAsMap.put("accessCardNumber3", "");

         orderDetailsAsMap.put("apid4", "4" + ban);
         orderDetailsAsMap.put("dtstb4Ind", "I");
         orderDetailsAsMap.put("stb4Type", "DTVSDDVR");
         orderDetailsAsMap.put("productLine4", "IRD - DVR");
         orderDetailsAsMap.put("modelNumber4", "R15-500");
         orderDetailsAsMap.put("contractId4", "");
         orderDetailsAsMap.put("purchaseTerms4", "Rent");
         orderDetailsAsMap.put("isActive4Ind", "Y");
         orderDetailsAsMap.put("isActive4OldInd", "N");
         orderDetailsAsMap.put("macAddress4", "");
         orderDetailsAsMap.put("manufacturer4", "DIRECTV");
         orderDetailsAsMap.put("receiverID4", "R04" + ban);
         orderDetailsAsMap.put("serialNumber4", "S04" + ban);
         orderDetailsAsMap.put("accessCardNumber4", "A04" + ban);

         orderDetailsAsMap.put("apid5", "5" + ban);
         orderDetailsAsMap.put("dtstb5Ind", "I");
         orderDetailsAsMap.put("stb5Type", "DTVWirelessGenie");
         orderDetailsAsMap.put("productLine5", "IRD - WIRELESS CLIENT");
         orderDetailsAsMap.put("modelNumber5", "C61W-700");
         orderDetailsAsMap.put("contractId5", "");
         orderDetailsAsMap.put("purchaseTerms5", "Rent");
         orderDetailsAsMap.put("isActive5Ind", "Y");
         orderDetailsAsMap.put("isActive5OldInd", "N");
         orderDetailsAsMap.put("macAddress5", "M05" + ban);
         orderDetailsAsMap.put("manufacturer5", "DIRECTV");
         orderDetailsAsMap.put("receiverID5", "R04" + ban);
         orderDetailsAsMap.put("serialNumber5", "S05" + ban);
         orderDetailsAsMap.put("accessCardNumber5", "");

         orderDetailsAsMap.put("apid6", "6" + ban);
         orderDetailsAsMap.put("dtstb6Ind", "I");
         orderDetailsAsMap.put("stb6Type", "DTVGenieMini");
         orderDetailsAsMap.put("productLine6", "IRD - CLIENT");
         orderDetailsAsMap.put("modelNumber6", "C51-500");
         orderDetailsAsMap.put("contractId6", "");
         orderDetailsAsMap.put("purchaseTerms6", "Rent");
         orderDetailsAsMap.put("isActive6Ind", "Y");
         orderDetailsAsMap.put("isActive6OldInd", "N");
         orderDetailsAsMap.put("macAddress6", "M06" + ban);
         orderDetailsAsMap.put("manufacturer6", "DIRECTV");
         orderDetailsAsMap.put("receiverID6", "R04" + ban);
         orderDetailsAsMap.put("serialNumber6", "S06" + ban);
         orderDetailsAsMap.put("accessCardNumber6", "");

         orderDetailsAsMap.put("apid7", "7" + ban);
         orderDetailsAsMap.put("dtstb7Ind", "I");
         orderDetailsAsMap.put("stb7Type", "DTVGenie");
         orderDetailsAsMap.put("productLine7", "IRD - ADVANCED WHOLE-HOME DVR");
         orderDetailsAsMap.put("modelNumber7", "HR54-200");
         orderDetailsAsMap.put("contractId7", "");
         orderDetailsAsMap.put("purchaseTerms7", "Rent");
         orderDetailsAsMap.put("isActive7Ind", "");
         orderDetailsAsMap.put("isActive7OldInd", "");
         orderDetailsAsMap.put("macAddress7", "");
         orderDetailsAsMap.put("manufacturer7", "DIRECTV");
         orderDetailsAsMap.put("receiverID7", "R07" + ban);
         orderDetailsAsMap.put("serialNumber7", "S07" + ban);
         orderDetailsAsMap.put("accessCardNumber7", "A07" + ban);

         orderDetailsAsMap.put("apid8", "8" + ban);
         orderDetailsAsMap.put("dtstb8Ind", "I");
         orderDetailsAsMap.put("stb8Type", "DTVWirelessGenie");
         orderDetailsAsMap.put("productLine8", "IRD - WIRELESS CLIENT");
         orderDetailsAsMap.put("modelNumber8", "C41W-500");
         orderDetailsAsMap.put("contractId8", "");
         orderDetailsAsMap.put("purchaseTerms8", "Rent");
         orderDetailsAsMap.put("isActive8Ind", "");
         orderDetailsAsMap.put("isActive8OldInd", "");
         orderDetailsAsMap.put("macAddress8", "M08" + ban);
         orderDetailsAsMap.put("manufacturer8", "DIRECTV");
         orderDetailsAsMap.put("receiverID8", "R07" + ban);
         orderDetailsAsMap.put("serialNumber8", "S08" + ban);
         orderDetailsAsMap.put("accessCardNumber8", "");

         orderDetailsAsMap.put("apid9", "9" + ban);
         orderDetailsAsMap.put("dtstb9Ind", "I");
         orderDetailsAsMap.put("stb9Type", "DTVGenieMini");
         orderDetailsAsMap.put("productLine9", "IRD - CLIENT");
         orderDetailsAsMap.put("modelNumber9", "C61-500");
         orderDetailsAsMap.put("contractId9", "");
         orderDetailsAsMap.put("purchaseTerms9", "Rent");
         orderDetailsAsMap.put("isActive9Ind", "");
         orderDetailsAsMap.put("isActive9OldInd", "");
         orderDetailsAsMap.put("macAddress9", "M09" + ban);
         orderDetailsAsMap.put("manufacturer9", "DIRECTV");
         orderDetailsAsMap.put("receiverID9", "R07" + ban);
         orderDetailsAsMap.put("serialNumber9", "S09" + ban);
         orderDetailsAsMap.put("accessCardNumber9", "");


         orderDetailsAsMap.put("dtaccInd", "I");
         orderDetailsAsMap.put("dtdhInd", "I");
         orderDetailsAsMap.put("dishType", "Standard");
         orderDetailsAsMap.put("apidWVB", "55" + ban);
         orderDetailsAsMap.put("dtwbInd", "I");
         orderDetailsAsMap.put("productLineWVB", "WIRELESS VIDEO BRIDGE");
         orderDetailsAsMap.put("modelNumberWVB", "WVBR0-25");
         orderDetailsAsMap.put("manufacturerWVB", "DIRECTV");
         orderDetailsAsMap.put("receiverIDWVB", "REC" + ban);
         orderDetailsAsMap.put("serialNumberWVB", "SWVB" + ban);
         orderDetailsAsMap.put("dtacrdInd", "");
         orderDetailsAsMap.put("productLinedtacrd", "XX");
         orderDetailsAsMap.put("addSrvInd", "I");
         orderDetailsAsMap.put("relocateReceiverCount", "0");
         orderDetailsAsMap.put("wholeHomeDvr", "Yes");
         orderDetailsAsMap.put("relocateDishCount", "0");
         orderDetailsAsMap.put("addInsInd", "I");
         orderDetailsAsMap.put("attDvInd", "I");
         orderDetailsAsMap.put("basePkg", "Entertainment");
         orderDetailsAsMap.put("hdAccessInd", "Y");
         orderDetailsAsMap.put("dtgp1Ind", "I");
         orderDetailsAsMap.put("dtGenrePackage1", "DIRECTVCINEMA");
         orderDetailsAsMap.put("dtgp1ContractId", "");
         orderDetailsAsMap.put("dttInd", "I");
         orderDetailsAsMap.put("countyFips", "13003");
         orderDetailsAsMap.put("purchaseTermsWvb", "Rent");
         orderDetailsAsMap.put("d2LiteEligibility", "Eligible");
         orderDetailsAsMap.put("mduConnectedProperty", "N");
         orderDetailsAsMap.put("dealerRestrictedProperty", "Y");
         orderDetailsAsMap.put("mrvIndicator", "Y");
         orderDetailsAsMap.put("swmIndicator", "MULTI-SWITCH SWM 8");
         orderDetailsAsMap.put("fourkService", "Y");
         orderDetailsAsMap.put("mpeg4RegionIndicator", "Y");
         orderDetailsAsMap.put("genieGen1Indicator", "Y");
         orderDetailsAsMap.put("nfflInd", "N");
         orderDetailsAsMap.put("dealerCode", "1234");
         orderDetailsAsMap.put("firstName", "JON");
         orderDetailsAsMap.put("lastName", "SNOW");
         orderDetailsAsMap.put("newBAN", "");

      }
      else if (orderActionType.equals("PR"))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      if (orderActionType.equals("CH"))
      {
         orderDetailsAsMap.put("wdmInd", "R");
         orderDetailsAsMap.put("wlldInd", "R");
         orderDetailsAsMap.put("wsimInd", "R");
         orderDetailsAsMap.put("owaInd", "R");
         orderDetailsAsMap.put("wnadInd", "R");

      }
      else if (orderActionType.equals("CE"))
      {
         orderDetailsAsMap.put("wdmInd", "D");
         orderDetailsAsMap.put("wlldInd", "D");
         orderDetailsAsMap.put("wsimInd", "D");
         orderDetailsAsMap.put("owaInd", "D");
         orderDetailsAsMap.put("wnadInd", "D");

      }
      else if (orderActionType.equals("PR"))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }
      else
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");
      }

      return orderDetailsAsMap;
   }

   public Map<String, String> createIssacRequest(String isaacReqType, String ban, String origOrderId)
   {
      Map<String, String> isaacRequestMap = new HashMap<>();
      try
      {

         if (isaacReqType.equals("Initiate"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Initiate");
         }
         ;
         if (isaacReqType.equals("Process"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "H44-500");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-100");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("productNameReceiver", "DIRECTV H44-500");
            isaacRequestMap.put("productLineReceiver", "IRD - GENIE LITE");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-100");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("receiverID", "REC" + ban);
            isaacRequestMap.put("accessCardId", "ACC" + ban);
            isaacRequestMap.put("macaddress", "Mac" + ban);
            isaacRequestMap.put("serialnumber", "SER" + ban);
         }
         ;
         if (isaacReqType.equals("Swap"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "HR54-700");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-700");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-700");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("productNameReceiver", "DIRECTV HR54-700");
            isaacRequestMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            isaacRequestMap.put("swapreceiverID", "REC" + ban);
            isaacRequestMap.put("swapaccessCardId", "ACC" + ban);
            isaacRequestMap.put("receiverID", "NEW" + ban);
            isaacRequestMap.put("accessCardId", "NEW" + ban);
            isaacRequestMap.put("orderActionMode", "Swap");
         }
         ;
         if (isaacReqType.equals("Closeout"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Closeout");
         }
         ;
         logger.info("ISAAC Request details Map formed Successfully-- {}");
      }
      catch (Exception e)
      {
         logger.error("ERROR forming ISAAC Request details Map -- {}", e);
      }
      return isaacRequestMap;

   }

   public Map<String, String> createIssacRequestNFFL(String isaacReqType, String ban, String origOrderId)
   {
      Map<String, String> isaacRequestMap = new HashMap<>();
      try
      {

         if (isaacReqType.equals("Initiate"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Initiate");
         }
         ;
         if (isaacReqType.equals("Process"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "H44-500");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-100");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("productNameReceiver", "DIRECTV H44-500");
            isaacRequestMap.put("productLineReceiver", "IRD - GENIE LITE");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-100");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("receiverID", "REC" + ban);
            isaacRequestMap.put("accessCardId", "ACC" + ban);
            isaacRequestMap.put("macaddress", "Mac" + ban);
            isaacRequestMap.put("serialnumber", "SER" + ban);
         }
         ;
         if (isaacReqType.equals("Swap"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "HR54-700");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-700");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-700");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("productNameReceiver", "DIRECTV HR54-700");
            isaacRequestMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            isaacRequestMap.put("swapreceiverID", "REC" + ban);
            isaacRequestMap.put("swapaccessCardId", "ACC" + ban);
            isaacRequestMap.put("receiverID", "NEW" + ban);
            isaacRequestMap.put("accessCardId", "NEW" + ban);
            isaacRequestMap.put("orderActionMode", "Swap");
         }
         ;
         if (isaacReqType.equals("Closeout"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Closeout");
         }
         ;
         logger.info("ISAAC Request details Map formed Successfully-- {}");
      }
      catch (Exception e)
      {
         logger.error("ERROR forming ISAAC Request details Map -- {}", e);
      }
      return isaacRequestMap;

   }


   public Map<String, String> createReceiverIsaacRequest(String isaacReqType, String ban, String origOrderId)
   {
      Map<String, String> isaacRequestMap = new HashMap<>();

      isaacRequestMap.put("nfflIndicator", "Y");

      isaacRequestMap.put("dealerCode", "1234");
      try
      {

         if (isaacReqType.equals("Initiate"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Initiate");
         }
         ;
         if (isaacReqType.equals("Process"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "H44-500");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-100");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("productNameReceiver", "DIRECTV H44-500");
            isaacRequestMap.put("productLineReceiver", "IRD - GENIE LITE");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-100");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("receiverID", "REC" + ban);
            isaacRequestMap.put("accessCardId", "ACC" + ban);
            isaacRequestMap.put("macaddress", "Mac" + ban);
            isaacRequestMap.put("serialnumber", "SER" + ban);
         }
         ;
         if (isaacReqType.equals("Swap"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("dealerCode", "");
            isaacRequestMap.put("orderLineItemAction", "Add");
            isaacRequestMap.put("actionType", "Activate");
            isaacRequestMap.put("actionTypeNIRD", "Notify");
            isaacRequestMap.put("equipmentOwnership", "Own");
            isaacRequestMap.put("makeReceiver", "DIRECTV");
            isaacRequestMap.put("modelReceiver", "HR54-700");
            isaacRequestMap.put("makeClient", "DIRECTV");
            isaacRequestMap.put("modelClient", "C41-700");
            isaacRequestMap.put("productNameClient", "DIRECTV C41-700");
            isaacRequestMap.put("productLineClient", "IRD - CLIENT");
            isaacRequestMap.put("productTypeIdentifier", "Receiver");
            isaacRequestMap.put("receiverConnectionType", "broadband");
            isaacRequestMap.put("productNameNIRD", "DIRECTV WVBR0-25");
            isaacRequestMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
            isaacRequestMap.put("makeNIRD", "DIRECTV");
            isaacRequestMap.put("modelNIRD", "WVBR0-25");
            isaacRequestMap.put("productNameReceiver", "DIRECTV HR54-700");
            isaacRequestMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            isaacRequestMap.put("RepairIndicator", "");
            isaacRequestMap.put("swapreceiverID", "REC" + ban);
            isaacRequestMap.put("swapaccessCardId", "ACC" + ban);
            isaacRequestMap.put("swapMacAddress", "MAC" + ban);
            isaacRequestMap.put("swapSerailnumber", "MAC" + ban);
            isaacRequestMap.put("receiverID", "NEW" + ban);
            isaacRequestMap.put("accessCardId", "NEW" + ban);
            isaacRequestMap.put("orderActionMode", "Swap");

         }
         ;
         if (isaacReqType.equals("Closeout"))
         {
            isaacRequestMap.put("ban", ban);
            isaacRequestMap.put("originalOrderActionId", origOrderId);
            isaacRequestMap.put("orderActionMode", "Closeout");
         }
         ;
         logger.info("ISAAC Request details Map formed Successfully-- {}");
      }
      catch (Exception e)
      {
         logger.error("ERROR forming ISAAC Request details Map -- {}", e);
      }
      return isaacRequestMap;

   }

   public Map<String, String> createNextGenWirelessObjectMap(String origOrdId, String orderNumber, String ban,
      String orderType, String orderSubType, String serviceId, String orderNumberCm, String origOrdIdCm)
   {

      Map<String, String> orderDetailsAsMap = null;

      String orderActionRefNumber = "A";

      if (orderType.equals("PV"))
      {
         orderDetailsAsMap = createFwFandTOrderMap(orderSubType);
         logger.info("Completed Generate Unique Order Method for F & T ");
         logger.info("service id  " + serviceId);
         orderDetailsAsMap.put("origOrderActId", origOrdId);
         orderDetailsAsMap.put("orderNumberPvData", origOrdId);
         orderDetailsAsMap.put("serviceIdPv", serviceId);
         orderDetailsAsMap.put("serviceID", serviceId);
         orderDetailsAsMap.put("addressIdCmData", orderNumberCm);
         orderDetailsAsMap.put("addressIdPvData", orderNumber);
         orderDetailsAsMap.put("orderNumberCmData", origOrdIdCm);
         orderDetailsAsMap.put("orderNumber", orderNumber);
         orderDetailsAsMap.put("ban", ban);
      }
      else
      {
         orderDetailsAsMap = createFwOrderMap(orderType, orderSubType);
         orderDetailsAsMap.put("origOrderActId", origOrdId);
         orderDetailsAsMap.put("orderNumber", orderNumber);
         orderDetailsAsMap.put("ban", ban);
         orderDetailsAsMap.put("orderActionType", orderType);
         orderDetailsAsMap.put("orderActionSubType", orderSubType);
         orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
         orderDetailsAsMap.put("serviceID", serviceId);
         orderDetailsAsMap.put("workOrderId", orderNumber);
         orderDetailsAsMap.put("rpiWllDataOrderNumber", orderNumber);
         orderDetailsAsMap.put("rpiWllDataOrigOrderNumber", origOrdId);
      }
      orderDetailsAsMap.put("ENV", propUtil.getEnv());
      orderDetailsAsMap.put("queue", EnvConstants.QUEUE_OMS);

      return orderDetailsAsMap;

   }

   public Map<String, String> createFwFandTOrderMap(String orderSubType)
   {
      Map<String, String> orderDetailsAsMap = new HashMap<>();

      orderDetailsAsMap.put(EnvConstants.TAG_TASKNAME, "provisionTransport");
      orderDetailsAsMap.put("wdmIndPv", "I");
      orderDetailsAsMap.put("wlldIndPv", "I");
      orderDetailsAsMap.put("wsimIndPv", "I");
      orderDetailsAsMap.put("wnadIndPv", "I");
      orderDetailsAsMap.put("owaIndPv", "I");
      orderDetailsAsMap.put("wdmIndCm", "D");
      orderDetailsAsMap.put("wlldIndCm", "D");
      orderDetailsAsMap.put("wsimIndCm", "D");
      orderDetailsAsMap.put("wnadIndCm", "D");
      orderDetailsAsMap.put("owaIndCm", "D");
      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("orderActionSubTypePv", orderSubType);
      orderDetailsAsMap.put("orderActionSubTypeCm", orderSubType);
      orderDetailsAsMap.put("orderActionReferenceNumberPv", "A");
      orderDetailsAsMap.put("orderActionReferenceNumberCm", "A");
      orderDetailsAsMap.put("orderCreationDatePv", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("dueDatePv", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("orderCreationDateCm", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("dueDateCm", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("deviceIdCm", "ub327e1234");
      orderDetailsAsMap.put("deviceIdPv", "ub327e678910");
      orderDetailsAsMap.put("addressIdPvData", "");
      orderDetailsAsMap.put("addressIdCmData", "");
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("workOrderId", "123456");
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("subTransportTypeCm", "");

      return orderDetailsAsMap;
   }

   public Map<String, String> createFwOrderMap(String orderType, String orderSubType)
   {
      Map<String, String> orderDetailsAsMap = new HashMap<>();
      String orderActionRefNumber;

      if (orderType.equals("CH"))
      {
         orderDetailsAsMap.put("wdmInd", "R");
         orderDetailsAsMap.put("wlldInd", "R");
         orderDetailsAsMap.put("wsimInd", "R");
         orderDetailsAsMap.put("owaInd", "R");
         orderDetailsAsMap.put("wnadInd", "R");

      }
      else if (orderType.equals("CE"))
      {
         orderDetailsAsMap.put("wdmInd", "D");
         orderDetailsAsMap.put("wlldInd", "D");
         orderDetailsAsMap.put("wsimInd", "D");
         orderDetailsAsMap.put("owaInd", "D");
         orderDetailsAsMap.put("wnadInd", "D");

      }
      else if (orderType.equals("PR"))
      {
         orderDetailsAsMap.put("wdmInd", "I");
         orderDetailsAsMap.put("wlldInd", "I");
         orderDetailsAsMap.put("wsimInd", "I");
         orderDetailsAsMap.put("owaInd", "I");
         orderDetailsAsMap.put("wnadInd", "I");

      }
      else
      {
         orderDetailsAsMap.put("wdmInd", "");
         orderDetailsAsMap.put("wlldInd", "");
         orderDetailsAsMap.put("wsimInd", "");
         orderDetailsAsMap.put("owaInd", "");
         orderDetailsAsMap.put("wnadInd", "");
      }

      orderDetailsAsMap.put("origOrderActId", "");
      orderDetailsAsMap.put("orderNumber", "");
      orderDetailsAsMap.put("ban", "");
      orderDetailsAsMap.put("orderActionType", orderType);
      orderDetailsAsMap.put("orderActionSubType", orderSubType);

      if (orderSubType.equals("NA"))
      {
         orderActionRefNumber = "A";

      }
      else if (orderSubType.equals("AM"))
      {
         orderActionRefNumber = "B";
      }
      else
      {
         orderActionRefNumber = "C";
      }
      orderDetailsAsMap.put("orderActionReferenceNumber", orderActionRefNumber);
      orderDetailsAsMap.put("serviceID", "");
      orderDetailsAsMap.put("workOrderId", "");
      orderDetailsAsMap.put(EnvConstants.TAG_TASKNAME, "provisionTransport");
      orderDetailsAsMap.put("subTransportType", "");
      orderDetailsAsMap.put("dataSpeed", "fast");
      orderDetailsAsMap.put("deviceId", "ub327e1234");
      orderDetailsAsMap.put("tnPrefix", "2");
      orderDetailsAsMap.put("dueDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("orderCreationDate", Utils.getExtendedDateByDays(-1));
      orderDetailsAsMap.put("apt_start", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("apt_end", Utils.getExtendedDateByDays(0));
      orderDetailsAsMap.put("prevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWorkOrderId", "");
      orderDetailsAsMap.put("rpiPrevWorkOrderId", "");
      orderDetailsAsMap.put("rpiWllDataOrderNumber", "");
      orderDetailsAsMap.put("rpiWllDataOrigOrderNumber", "");
      orderDetailsAsMap.put("rpiWllDataOrdActionType", orderType);
      orderDetailsAsMap.put("rpiWllDataOrdActSubType", orderSubType);
      orderDetailsAsMap.put("rpiWllDataOrdActRef", "");
      orderDetailsAsMap.put("rpiWllDataOrdActStatus", "DE");
      orderDetailsAsMap.put("rpiWllDataDueDate", Utils.getExtendedDateByDays(-1));

      return orderDetailsAsMap;
   }

   public Map<String, String> createWllPubRgNotification(String ban, String origOrderActId, String orderActionType,
      String serviceID)
   {
      Map<String, String> pubRgMap = new HashMap<>();
      Date curDate = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy");
      String year = format.format(curDate);
      format = new SimpleDateFormat("MM");
      String month = format.format(curDate);
      format = new SimpleDateFormat("dd");
      String day = format.format(curDate);
      format = new SimpleDateFormat("HH");
      String hour = format.format(curDate);
      format = new SimpleDateFormat("mm");
      String minute = format.format(curDate);
      format = new SimpleDateFormat("ss");
      String sec = format.format(curDate);
      format = new SimpleDateFormat("SSS");
      String millisec = format.format(curDate);

      pubRgMap.put("serviceId", serviceID);
      pubRgMap.put("orderNumber", origOrderActId);
      pubRgMap.put("deviceId", origOrderActId);
      pubRgMap.put("serialNumber", origOrderActId);
      pubRgMap.put("orderActionType", orderActionType);
      pubRgMap.put("ban", ban);
      pubRgMap.put("year", year);
      pubRgMap.put("month", month);
      pubRgMap.put("day", day);
      pubRgMap.put("hour", hour);
      pubRgMap.put("minute", minute);
      pubRgMap.put("sec", sec);
      pubRgMap.put("millisec", millisec);

      return pubRgMap;
   }

   public Map<String, String> createFiedWirelessIsaacRequestMap(String ban, String mode, String imei, String iccid,
      String origOrderActId, String subTransportType)
   {
      Map<String, String> isaacRequestMap = new HashMap<>();
      isaacRequestMap.put("ban", ban);
      isaacRequestMap.put("mode", mode);
      isaacRequestMap.put("imei", imei);
      isaacRequestMap.put("iccid", iccid);
      isaacRequestMap.put("serial", origOrderActId);
      isaacRequestMap.put("subTransportType", subTransportType);

      return isaacRequestMap;
   }


   public java.util.Map<String, String> createWllRetrieveProvisioningStatusInformationRequestMap(String ban,
      String omsOrderNumber)
   {

      Map<String, String> firstRtRequestMap = new HashMap<>();

      firstRtRequestMap.put("ban", ban);
      firstRtRequestMap.put("omsOrderNumber", omsOrderNumber);

      return firstRtRequestMap;
   }

}
