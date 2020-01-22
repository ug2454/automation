package com.att.bbnmstest.cuke;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.services.util.EnvConstants;

@Component
public class StateContext extends LinkedHashMap<String, Object>
{

   private static final String MARK_AS_PURGED_ORDER_CONTEXT = "PURGED_ORDER_CONTEXT";
   private static final String ORDER_XML_REQUEST = "ORDER_XML_REQUEST";
   private static final String ORDER_PUB_RG_XML_REQUEST = "ORDER_PUB_RG_XML_REQUEST";
   private static final String ORDER_L1_NOTIFY_XML_REQUEST = "ORDER_L1_NOTIFY_XML_REQUEST";
   private static final String TDAR_ISSACT_REQ_XML = "TDAR_ISSACT_REQ_XML";
   private static final String WLL_SIM_ORDER_MAP = "WLL_SIM_ORDER_MAP";
   private static final String BAN = EnvConstants.TAG_BAN;
   private static final String ORIG_ORDER_ID = EnvConstants.TAG_ORIG_ORDER_ACTION_ID;

   private static final String ORDER_NUMBER = EnvConstants.TAG_ORDER_NUMBER;
   private static final String ORDER_TYPE = "orderType";
   private static final String SERVICE_ID = EnvConstants.TAG_SERVICE_ID;
   private static final String ORDER_VERSION = EnvConstants.TAG_ORDERVERSION;
   private static final String SPEED_CODE = "speedCode";
   private static final String CIRCUIT_ID = "circuitId";
   private static final String NTI = "NTI";
   private static final String TN = "tn";
   private static final String ORDER_CEASE_MOVE_ORIG_ID = EnvConstants.TAG_ORIG_ORDER_CEASE_MOVE;
   private static final String EMTCLLI = "EMTCLLIName";
   private static final String WLL_PROCESS_REQ_ORDER = "WLL_PROCESS_REQ_ORDER";
   private static final String WLL_PROCESS_RESPONSE = "WLL_PROCESS_RESPONSE";
   private static final String SUB_TRANSPORT_TYPE = "subTransportType";
   private static final String SUB_TRANSPORT_TYPE_CM = "subTransportTypeCm";

   private static final String WLL_FIRST_RT_REQUEST = "WLL_FIRST_RT_REQ_XML";
   private static final String WLL_FIRST_RT_REPSONSE = "WLL_FIRST_RT_RESPONSE";
   
   private String CP_PAGENAME="";
   
   public StateContext()
   {
   }

   public void setTdarIssacRequest(Map<String, String> value)
   {
      put(TDAR_ISSACT_REQ_XML, value);
   }

   public Map<String, String> getTdarIssacRequest()
   {
      return (Map<String, String>) get(TDAR_ISSACT_REQ_XML);
   }

   public void setWllSimOrderMap(Map<String, String> value)
   {
      put(WLL_SIM_ORDER_MAP, value);
   }

   public Map<String, String> getWllSimOrderMap()
   {
      return (Map<String, String>) get(WLL_SIM_ORDER_MAP);
   }

   public String getWllFirstRtRequest()
   {
      return (String) get(WLL_FIRST_RT_REQUEST);
   }

   public String setWllFirstRtResponse(String response)
   {

      return (String) put(WLL_FIRST_RT_REPSONSE, response);

   }


   public String getWllFirstRtRepsonse()
   {
      return (String) get(WLL_FIRST_RT_REPSONSE);
   }

   public String getEmtclli()
   {
      return getValueForOrder(EMTCLLI);
   }

   public void setEmtclli(String EMTCLLIName)
   {
      put(EMTCLLI, EMTCLLIName);
   }

   public String getCeaseMoveOrigOrderId()
   {
      return getValueForOrder(ORDER_CEASE_MOVE_ORIG_ID);
   }

   public String getOrderNumber()
   {
      return getValueForOrder(ORDER_NUMBER);
   }

   public void setOrderNumber(String orderNumber)
   {
      put(ORDER_NUMBER, orderNumber);
   }

   public String getNti()
   {
      return getValueForOrder(NTI);
   }

   public void setNti(String nti)
   {
      put(NTI, nti);
   }

   public String getBan()
   {
      return getValueForOrder(BAN);
   }

   public String getOrigOrderId()
   {
      return getValueForOrder(ORIG_ORDER_ID);
   }


   public String getOrderType()
   {
      return getValueForOrder(ORDER_TYPE);
   }

   public String getServiceId()
   {
      return getValueForOrder(SERVICE_ID);
   }

   public String getSpeedCode()
   {
      return getValueForOrder(SPEED_CODE);
   }

   public String getOrderVersion()
   {
      return getValueForOrder(ORDER_VERSION);
   }

   public Map getOrderXmlReq()
   {
      return (Map) get(ORDER_XML_REQUEST);
   }

   public Map getOrderPubRGXmlReq()
   {
      return (Map) get(ORDER_PUB_RG_XML_REQUEST);
   }

   public Map getOrderL1NotifyXmlReq()
   {
      return (Map) get(ORDER_L1_NOTIFY_XML_REQUEST);
   }

   public Map getProcessReqOrderMap()
   {
      return (Map) get(WLL_PROCESS_REQ_ORDER);
   }

   public void setProcessReqOrderReq(Map processReqOrderMap)
   {
      put(WLL_PROCESS_REQ_ORDER, processReqOrderMap);
   }

   public void setBan(String value)
   {
      put(BAN, value);
   }

   public void setOrigOrderId(String value)
   {
      put(ORIG_ORDER_ID, value);
   }

   public void setOrderType(String value)
   {
      put(ORDER_TYPE, value);
   }

   public void setServiceId(String value)
   {
      put(SERVICE_ID, value);
   }

   public void setSpeedCode(String value)
   {
      put(SPEED_CODE, value);
   }

   public void setOrderVersion(String value)
   {
      put(ORDER_VERSION, value);
   }

   public void setOrderXmlReq(Map value)
   {
      put(ORDER_XML_REQUEST, value);
   }

   public void setOrderPubRGXmlReq(Map value)
   {
      put(ORDER_PUB_RG_XML_REQUEST, value);
   }

   public void setOrderL1NotifyXmlReq(Map value)
   {
      put(ORDER_L1_NOTIFY_XML_REQUEST, value);
   }

   public void setWllProcessResponse(String response)
   {
      put(WLL_PROCESS_RESPONSE, response);
   }

   public String getWllProcessResponse()
   {
      return (String) get(WLL_PROCESS_RESPONSE);
   }

   public void setTn(String tn)
   {
      put(TN, tn);
   }

   public String getTn()
   {
      return (String) get(TN);
   }

   public String getCircuitId()
   {
      return getValueForOrder(CIRCUIT_ID);
   }

   public void setSubTransportType(String subTransportType)
   {
      if (getOrderXmlReq() == null)
      {
         return;
      }
      getOrderXmlReq().put(SUB_TRANSPORT_TYPE, subTransportType);
   }

   public String getSubTransportType()
   {
      if (getOrderXmlReq() == null)
      {
         return null;
      }
      return (String) getOrderXmlReq().get(SUB_TRANSPORT_TYPE);
   }

   public void setSubTransportTypeCm(String subTransportType)
   {
      if (getOrderXmlReq() == null)
      {
         return;
      }
      getOrderXmlReq().put(SUB_TRANSPORT_TYPE_CM, subTransportType);
   }

   public String getSubTransportTypeCm()
   {
      if (getOrderXmlReq() == null)
      {
         return null;
      }
      return (String) getOrderXmlReq().get(SUB_TRANSPORT_TYPE_CM);
   }

   public String getOrderActionType()
   {
      if (getOrderType() != null && getOrderType().contains("-"))
      {
         return getOrderType().split("-")[0];
      }
      return null;
   }


   public String getOrderSubActionType()
   {
      if (getOrderType() != null && getOrderType().contains("-"))
      {
         return getOrderType().split("-")[1];
      }
      return null;
   }

   public String getValueForOrder(Object key)
   {
      return (String) ((get(key) == null && getOrderXmlReq() != null) ? getOrderXmlReq().get(key) : get(key));
   }

   public String getCurrentOrNewOrderVersion()
   {
      if (getOrderVersion() == null)
      {
         setOrderVersion(String.valueOf(1));
      }
      return getOrderVersion();
   }

   public String getNextOrderVersion()
   {
      if (getOrderVersion() == null)
      {
         setOrderVersion(String.valueOf(1));
      }
      else
      {

         setOrderVersion(String.valueOf((Integer.parseInt(getOrderVersion()) + 1)));
      }
      return getOrderVersion();
   }


   public String getOrderActionSubType()
   {
      return (getOrderType() != null) ? getOrderType().split("-")[1] : null;
   }

   public boolean checkOrder()
   {
      return StringUtils.isNotEmpty(getOrigOrderId());
   }

   public void setAsPurgedOrderContext()
   {
      put(MARK_AS_PURGED_ORDER_CONTEXT, true);
   }

   public boolean isPurgedOrderContext()
   {
      return BooleanUtils.isTrue((Boolean) get(MARK_AS_PURGED_ORDER_CONTEXT));
   }
   
   public void setPage(String CP_PAGENAME)
   {
      this.CP_PAGENAME= CP_PAGENAME;
   }

   public String getPage()
   {
      return CP_PAGENAME;
   }

   

}
