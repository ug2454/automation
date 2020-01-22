package com.att.bbnmstest.services.dtv;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.utils.Wait;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.dispatcher.IssacDispatcher;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.bbnmstest.services.helper.RequestType;
import com.att.bbnmstest.services.helper.SupplierPredicateTdarEnum;
import com.att.bbnmstest.services.helper.SupplierPredicateTdarSwapEnum;
import com.att.bbnmstest.services.helper.TDARRequestType;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.OrderObjectMapper;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.XpathConstants;

@Component
@Qualifier("DTVService")
public class DTVService extends BBNMSService
{
   private final static Logger logger = Logger.getLogger(DTVService.class);

   @Autowired
   private IssacDispatcher issacAdapter;


   @Autowired
   private PropUtil propUtil;

   @Autowired
   private OrderObjectMapper orderObjectMapper;


   public Map<String, String> setUpOrder(String orderActionType, String orderActionSubType, Map context)
      throws AutomationBDDServiceException
   {
      String ban = null;
      Map<String, String> orderNumbers = createUniqueOrdernumberAndOriginalOrderId(EnvConstants.GENERATE_ID_PREFIX_DTV);
      String orderNumber = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      String origOrdId = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      if (orderActionType.equals("CH") || orderActionType.equals("PV") || orderActionType.equals("SU")
         || orderActionType.equals("RS") || orderActionType.equals("CE"))
      {
         ban = (String) context.get(EnvConstants.TAG_BAN);
      }
      else if (orderActionType.equals("SU") && orderActionSubType.equals("AM"))
      {
         orderNumber = (String) context.get(EnvConstants.TAG_ORDER_NUMBER);
         origOrdId = (String) context.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);
         ban = (String) context.get(EnvConstants.TAG_BAN);
      }
      else
      {
         ban = origOrdId;
      }
      Map<String, String> orderObjMap = orderObjectMapper.createDTVOrderObjectMap(orderNumber, origOrdId, ban,
         orderActionType, orderActionSubType);


      return orderObjMap;
   }

   public Map<String, String> setUpAmmendOrder(String orderActionType, String orderActionSubType, Map context)
      throws AutomationBDDServiceException
   {
      String ban = null;
      String orderNumber = null;
      String origOrdId = null;

      if ((orderActionType.equals("SU") && orderActionSubType.equals("AM")) || (orderActionType.equals("RS")
         && orderActionSubType.equals("AM")) || ((orderActionType.equals("CH") && orderActionSubType.equals("AM"))))
      {
         orderNumber = (String) context.get(EnvConstants.TAG_ORDER_NUMBER);
         origOrdId = (String) context.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);
         ban = (String) context.get(EnvConstants.TAG_BAN);
      }
      else
      {
         ban = origOrdId;
      }
      Map<String, String> orderObjMap = orderObjectMapper.createDTVMultipleOrderObjectMap(orderNumber, origOrdId, ban,
         orderActionType, orderActionSubType);


      return orderObjMap;
   }

   public Map<String, String> setUpDTVOrder(String orderActionType, String orderActionSubType, Map context)
      throws AutomationBDDServiceException
   {
      String ban = null;
      Map<String, String> orderNumbers = createUniqueOrdernumberAndOriginalOrderId(EnvConstants.GENERATE_ID_PREFIX_DTV);
      String orderNumber = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      String origOrdId = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      if (orderActionType.equals("CH") || orderActionType.equals("PV") || orderActionType.equals("SU")
         || orderActionType.equals("RS") || orderActionType.equals("CE"))
      {
         ban = (String) context.get(EnvConstants.TAG_BAN);
      }
      else
      {
         ban = origOrdId;
      }
      Map<String, String> orderObjMap = orderObjectMapper.createDTVMultipleOrderObjectMap(orderNumber, origOrdId, ban,
         orderActionType, orderActionSubType);


      return orderObjMap;
   }

   public String provisionedBan() throws AutomationBDDServiceException
   {
      logger.info("Get Ban From OL");
      return (getOlDao().getDtvBanFromOL());

   }

   public void sendOmsRequest(String requestType, Map orderDetailsAsMap) throws AutomationBDDServiceException
   {

      RequestType.valueOf(requestType).sendOmsRequest(getOmsAdapter(), orderDetailsAsMap, propUtil.getProperty(
         EnvConstants.TEMPLATE_OMS_DTV));
   }

   public void sendOmsFTRequest(String requestType, Map orderDetailsAsMap) throws AutomationBDDServiceException
   {
      RequestType.valueOf(requestType).sendOmsRequest(getOmsAdapter(), orderDetailsAsMap, propUtil.getProperty(
         EnvConstants.TEMPLATE_OMS_DTV_FT));
   }

   public void addTechAtributes(Map<String, String> orderDetailsAsMap, String techDispatchInd, String installationType)
   {
      if (orderDetailsAsMap == null)
      {
         return;
      }
      orderDetailsAsMap.put("technicianDispatchIndicator", techDispatchInd);
      orderDetailsAsMap.put("installationType", installationType);
   }

   public void addSwapAction(Map<String, String> orderDetailsAsMap, String dtstb1Ind, String dtstb6Ind,
      String swapaction1, String swapaction6)
   {
      if (orderDetailsAsMap == null)
      {
         return;
      }
      orderDetailsAsMap.put("dtstb1Ind", dtstb1Ind);
      orderDetailsAsMap.put("dtstb6Ind", dtstb6Ind);
      orderDetailsAsMap.put("swapaction1", swapaction1);
      orderDetailsAsMap.put("swapaction1", swapaction6);
   }

   public void updatePreviousAPID(Map<String, String> orderDetailsAsMap, String prevapid6)
   {
      if (orderDetailsAsMap == null)
      {
         return;
      }
      orderDetailsAsMap.put("prevapid6", prevapid6);
   }

   public Map createIssacRequest(String isaacReqType, String ban, String origOrderId)
   {
      return orderObjectMapper.createIssacRequest(isaacReqType, ban, origOrderId);
   }

   public Map createIssacRequestNFFL(String isaacReqType, String ban, String origOrderId)
   {
      return orderObjectMapper.createIssacRequestNFFL(isaacReqType, ban, origOrderId);
   }

   public Map createReceiverIsaacRequest(String isaacReqType, String ban, String origOrderId)
   {
      return orderObjectMapper.createReceiverIsaacRequest(isaacReqType, ban, origOrderId);
   }

   public void sendIssacRequest(String requestType, Map orderMap) throws AutomationBDDServiceException
   {
      String template = null;

      switch (requestType)
      {
         case TDARRequestType.Req_Type_Initiate:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_INITIATE_REQ);
            break;
         case TDARRequestType.Req_Type_Process:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_REQ);
            break;
         case TDARRequestType.Req_Type_Swap:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_REQ);
            break;
         case TDARRequestType.Req_Type_Closeout:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_CLOSEOUT_REQ);
            break;
         default:
            template = null;
      }

      TDARRequestType.valueOf(requestType).sendIssacRequest(issacAdapter, orderMap, template);
   }

   public void sendIssacNoWvbRequest(String requestType, Map orderMap) throws AutomationBDDServiceException
   {
      String template = null;

      switch (requestType)
      {
         case TDARRequestType.Req_Type_Initiate:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_INITIATE_REQ);
            break;
         case TDARRequestType.Req_Type_Process:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_No_Wvb_REQ);
            break;
         case TDARRequestType.Req_Type_Swap:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_REQ);
            break;
         case TDARRequestType.Req_Type_Closeout:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_CLOSEOUT_REQ);
            break;
         default:
            template = null;
      }

      TDARRequestType.valueOf(requestType).sendIssacRequest(issacAdapter, orderMap, template);
   }

   public void sendUpdateIssacRequest(String requestType, Map orderMap) throws AutomationBDDServiceException
   {
      String template = null;

      switch (requestType)
      {
         case TDARRequestType.Req_Type_Initiate:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_INITIATE_REQ);
            break;
         case TDARRequestType.Req_Type_Process:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_UPDATE_REQ);
            break;
         case TDARRequestType.Req_Type_Swap:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_UPDATE_REQ);
            break;
         case TDARRequestType.Req_Type_Closeout:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_CLOSEOUT_REQ);
            break;
         default:
            template = null;
      }

      TDARRequestType.valueOf(requestType).sendIssacRequest(issacAdapter, orderMap, template);
   }

   public void sendRepairIsaacRequest(String requestType, Map orderMap) throws AutomationBDDServiceException
   {
      String template = null;

      switch (requestType)
      {
         case TDARRequestType.Req_Type_Initiate:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_INITIATE_REPAIR_REQ);
            break;
         case TDARRequestType.Req_Type_Process:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_REQ);
            break;
         case TDARRequestType.Req_Type_Swap:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_PROCESS_REQ);
            break;
         case TDARRequestType.Req_Type_Closeout:
            template = propUtil.getProperty(EnvConstants.TEMPLATE_DTV_CLOSEOUT_REPAIR_REQ);
            break;
         default:
            template = null;
      }

      TDARRequestType.valueOf(requestType).sendIssacRequest(issacAdapter, orderMap, template);
   }

   @SuppressWarnings("unchecked")
   public void sendProcessIssacRequestForLineItemsAndIdentifiers(String requestType, String origOrderId, String ban,
      Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));


         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("serialnumber", "SW2" + ban);
               orderMap.put("productNameNIRD", "DIRECTV WVBR0-25");
               orderMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
               orderMap.put("makeNIRD", "DIRECTV");
               orderMap.put("modelNIRD", "WVBR0-25");
               orderMap.put("macaddress", "MW2" + ban);
               orderMap.put("receiverID", "RW2" + ban);

               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }

            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue() + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("accessCardId", "A03" + ban);

         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }

   @SuppressWarnings("unchecked")
   public void sendProcessIssacRequestForLineItemsAndIdentifiersNFFL(String requestType, String origOrderId, String ban,
      Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         orderMap.put("nfflInd", "Y");
         orderMap.put("dealerCode", "1234");

         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("serialnumber", "SW2" + ban);
               orderMap.put("productNameNIRD", "DIRECTV WVBR0-25");
               orderMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
               orderMap.put("makeNIRD", "DIRECTV");
               orderMap.put("modelNIRD", "WVBR0-25");
               orderMap.put("macaddress", "MW2" + ban);
               orderMap.put("receiverID", "RW2" + ban);

               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }

            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue() + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("accessCardId", "A03" + ban);

         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }

   @SuppressWarnings("unchecked")
   public void sendSwapRequestForLineItemsAndIdentifiersNFFL(String requestType, String origOrderId, String ban,
      Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         orderMap.put("nfflInd", "Y");
         orderMap.put("dealerCode", "1234");

         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("serialnumber", "SW2" + ban);
               orderMap.put("productNameNIRD", "DIRECTV WVBR0-25");
               orderMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
               orderMap.put("makeNIRD", "DIRECTV");
               orderMap.put("modelNIRD", "WVBR0-25");
               orderMap.put("macaddress", "MW2" + ban);
               orderMap.put("receiverID", "RW2" + ban);

               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }

            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue() + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("accessCardId", "A03" + ban);

         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForWvb(String requestType, String origOrderId,
      Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, String> lineItemAndIdentifiers = getLineItemIDAndIdentifierByOrderId(origOrderId);

      Boolean harwarefound = false;

      for (Map.Entry<String, String> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue());
         if (entry.getValue().equals("Hardware") && harwarefound)
         {
            continue;
         }

         if (entry.getValue().equals("Hardware"))
         {
            harwarefound = true;
         }
         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleDeActivationOfClientsOfI(String requestType,
      String origOrderId, String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");


         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("11" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R10" + ban);
            orderMap.put("macaddress", "M11" + ban);
            orderMap.put("serialnumber", "S11" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("12" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M12" + ban);
            orderMap.put("serialnumber", "S12" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else
         {

            continue;
         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleDeActivationOfClients(String requestType,
      String origOrderId, String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierActionIndicatorByOrderId(
         origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");


         // For Productline != Client :

         // if("IRD - GENIE LITE".equals(entry.getValue().get(1))&&
         // ("1" + ban).equals(entry.getValue().get(2)))
         //
         //
         // {
         // orderMap.put("actionType", "Deactivate");
         // orderMap.put("makeReceiver", "DIRECTV");
         // orderMap.put("modelReceiver", "H44-500");
         // orderMap.put("productNameReceiver", "DIRECTV H44-500");
         // orderMap.put("productLineReceiver", "IRD - GENIE LITE");
         // orderMap.put("receiverID", "R01" + ban);
         // orderMap.put("serialnumber","S01" + ban);
         // orderMap.put("accessCardId","A01" + ban);
         // }

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else
         {

            continue;
         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForDeActivationOfClients(String requestType,
      String origOrderId, String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierActionIndicatorByOrderId(
         origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("lineItemID", "Sahoo");
            orderMap.put("macaddress", "MS8" + ban);
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Deactivate");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else
         {

            continue;
         }


         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForReActivationOfClients(String requestType,
      String origOrderId, String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierActionIndicatorAsMByOrderId(
         origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Activate");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("actionType", "Activate");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else
         {

            continue;
         }


         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendSwapIssacRequestForG1ToG2(String requestType, String origOrderId, String ban, Map orderMap)
      throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("11" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M98" + ban);
            orderMap.put("serialnumber", "S98" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("swapreceiverID", "R07" + ban);
            orderMap.put("swapMacAddress", "M08" + ban);

         }


         else
         {

            continue;
         }


         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfullofIsvalid(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }


   public void sendDoaSwapIssacRequest(String requestType, String origOrderId, String ban, Map orderMap)
      throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", "Sahoo");
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         orderMap.put("nfflInd", "Y");
         orderMap.put("dealerCode", "1234");


         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("lineItemID", "Sahoo");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M99" + ban);
            orderMap.put("serialnumber", "S99" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
            orderMap.put("swapreceiverID", "R02" + ban);
            orderMap.put("swapMacAddress", "M02" + ban);

         }


         else
         {

            continue;
         }


         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfullofIsvalid(origOrderId, "Sahoo"))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }


   public void sendUpdateIssacRequest(String requestType, String origOrderId, String ban, Map orderMap)
      throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("lineItemID", "Sahoo");
            orderMap.put("actionType", "Notify");
            orderMap.put("receiverID", "R98" + ban);
            orderMap.put("macaddress", "M98" + ban);
            orderMap.put("serialnumber", "S98" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("swapreceiverID", "R07" + ban);
            orderMap.put("swapMacAddress", "M08" + ban);

         }

         else
         {

            continue;
         }


         sendUpdateIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfullofIsvalid(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }


   public void sendMultipleRepairSwapIssacRequest(String requestType, String origOrderId, String ban, Map orderMap)
      throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         orderMap.put("RepairIndicator", "True");
         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)))
         {

            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("macaddress", "M98" + ban);
            orderMap.put("serialnumber", "S98" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("swapreceiverID", "R07" + ban);
            orderMap.put("swapMacAddress", "M08" + ban);

         }


         else
         {

            continue;
         }


         sendRepairIsaacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfullofIsvalid(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiersForAHLHardware(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         logger.info("Sending request for Clients:" + "[" + entry.getValue().get(1) + "]");

         if (entry.getValue().get(0).equals("Hardware"))
         {

            if (("POWER INSERTER MULTI-SWITCH").equals(entry.getValue().get(1)))
            {
               orderMap.put("actionType", "Notify");
               orderMap.put("serialnumber", "SW7" + ban);
               orderMap.put("productNameNIRD", "POWER INSERTER MULTI-SWITCH");
               orderMap.put("productLineNIRD", "POWER INSERTER MULTI-SWITCH");
               orderMap.put("makeNIRD", "POWER INSERTER MULTI-SWITCH");
               orderMap.put("modelNIRD", "POWER INSERTER MULTI-SWITCH");
               orderMap.put("macaddress", "MW7" + ban);
               orderMap.put("receiverID", "Rw7" + ban);
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");

            }
            else if (("MULTI-SWITCH").equals(entry.getValue().get(1)))
            {
               orderMap.put("actionType", "Notify");
               orderMap.put("serialnumber", "08S" + ban);
               orderMap.put("productNameNIRD", "MULTI-SWITCH DSWM 30");
               orderMap.put("productLineNIRD", "MULTI-SWITCH");
               orderMap.put("makeNIRD", "MULTI-SWITCH DSWM 30");
               orderMap.put("modelNIRD", "MULTI-SWITCH DSWM 30");
               orderMap.put("macaddress", "08M" + ban);
               orderMap.put("receiverID", "08W" + ban);
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");

            }
            else if (("KA/KU LNB").equals(entry.getValue().get(1)))
            {
               orderMap.put("actionType", "Notify");
               orderMap.put("serialnumber", "09M" + ban);
               orderMap.put("productNameNIRD", "KA/KU LNB 5");
               orderMap.put("productLineNIRD", "KA/KU LNB");
               orderMap.put("makeNIRD", "KA/KU LNB 5");
               orderMap.put("modelNIRD", "KA/KU LNB 5");
               orderMap.put("macaddress", "09M" + ban);
               orderMap.put("receiverID", "09R" + ban);
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");

            }
            else if (("MISCELLANEOUS HARDWARE").equals(entry.getValue().get(1)))
            {
               orderMap.put("actionType", "Notify");
               orderMap.put("serialnumber", "10S" + ban);
               orderMap.put("productNameNIRD", "ODU HARDWARE");
               orderMap.put("productLineNIRD", "MISCELLANEOUS HARDWARE");
               orderMap.put("makeNIRD", "ODU HARDWARE");
               orderMap.put("modelNIRD", "ODU HARDWARE");
               orderMap.put("macaddress", "10M" + ban);
               orderMap.put("receiverID", "10R" + ban);
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");

            }
            else if (("MOBILE DVR").equals(entry.getValue().get(1)))
            {
               orderMap.put("actionType", "Notify");
               orderMap.put("serialnumber", "SW8" + ban);
               orderMap.put("productNameNIRD", "MOBILE DVR");
               orderMap.put("productLineNIRD", "MOBILE DVR");
               orderMap.put("makeNIRD", "MOBILE DVR");
               orderMap.put("modelNIRD", "MOBILE DVR");
               orderMap.put("macaddress", "MW8" + ban);
               orderMap.put("receiverID", "Rw8" + ban);
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");

            }

            else
            {

               continue;
            }

            sendIssacRequest(requestType, orderMap);

         }


         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForStbs(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);
      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));


         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("macaddress", ban + "GHI");

               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }

            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue() + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);

         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("macaddress", "M03" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");

         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("accessCardId", "A04" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");

         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(
            2)))
         {


            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("receiverID", "R04" + ban);
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("accessCardId", "A07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR54-200");
            orderMap.put("productNameReceiver", "DIRECTV HR54-200");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         // else if( entry.getValue().get(0).equals("Hardware") && ("DIGITAL SWM LNB").equals(entry.getValue().get(1)))
         // {
         // orderMap.put("actionType", "Notify");
         // orderMap.put("serialnumber", "SW7" + ban);
         // orderMap.put("productNameNIRD", "DIGITAL SWM LNB");
         // orderMap.put("productLineNIRD", "DIGITAL SWM LNB");
         // orderMap.put("makeNIRD", "DIGITAL SWM LNB");
         // orderMap.put("modelNIRD", "DIGITAL SWM LNB");
         // orderMap.put("macaddress", "MW7" + ban);
         // orderMap.put("receiverID", "Rw7" + ban);
         // logger.info("Skipping request for Hardware:"+"["+entry.getValue().get(0)+"]");
         //
         // }
         // else {
         // continue ;
         //
         // }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForStbsNFFL(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);
      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         orderMap.put("nfflInd", "Y");
         orderMap.put("dealerCode", "1234");

         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("macaddress", ban + "GHI");

               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }

            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue() + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);

         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("macaddress", "M03" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");

         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("accessCardId", "A04" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");

         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(
            2)))
         {


            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("receiverID", "R04" + ban);
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("accessCardId", "A07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR54-200");
            orderMap.put("productNameReceiver", "DIRECTV HR54-200");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         // else if( entry.getValue().get(0).equals("Hardware") && ("DIGITAL SWM LNB").equals(entry.getValue().get(1)))
         // {
         // orderMap.put("actionType", "Notify");
         // orderMap.put("serialnumber", "SW7" + ban);
         // orderMap.put("productNameNIRD", "DIGITAL SWM LNB");
         // orderMap.put("productLineNIRD", "DIGITAL SWM LNB");
         // orderMap.put("makeNIRD", "DIGITAL SWM LNB");
         // orderMap.put("modelNIRD", "DIGITAL SWM LNB");
         // orderMap.put("macaddress", "MW7" + ban);
         // orderMap.put("receiverID", "Rw7" + ban);
         // logger.info("Skipping request for Hardware:"+"["+entry.getValue().get(0)+"]");
         //
         // }
         // else {
         // continue ;
         //
         // }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiersForProcessParllel(String requestType, String ban,
      String origOrderId, Map orderMapOrig) throws AutomationBDDServiceException
   {


      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductTypeByOrderId(origOrderId);

      Boolean harwarefound = false;


      java.util.List<Thread> threads = new java.util.ArrayList<Thread>();

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {
         Map orderMap = (Map) ((HashMap<String, String>) orderMapOrig).clone();
         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {

               orderMap.put("serialnumber", "SW2" + ban);
               orderMap.put("productNameNIRD", "DIRECTV WVBR0-25");
               orderMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
               orderMap.put("makeNIRD", "DIRECTV");
               orderMap.put("modelNIRD", "WVBR0-25");
               orderMap.put("macaddress", "MW2" + ban);
               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(1) + "]");
            }
            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(1) + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("macaddress", "M03" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");

         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("accessCardId", "A04" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");

         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(
            2)))
         {


            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("receiverID", "R04" + ban);
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("accessCardId", "A07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR54-200");
            orderMap.put("productNameReceiver", "DIRECTV HR54-200");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         threads.add(new Thread()
         {
            @Override
            public void run()
            {
               try
               {
                  sendIssacRequest(requestType, orderMap);
               }
               catch (AutomationBDDServiceException e)
               {
                  throw new IllegalStateException(e);
               }
            }
         });


      }

      for (Thread t : threads)
      {
         t.start();
      }

      for (Map.Entry<String, List<String>> entry : ((Map<String, List<String>>) threads).entrySet())
      {

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }

   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiersForMultipleStbs(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);


      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));


         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {

               orderMap.put("serialnumber", "SW2" + ban);
               orderMap.put("productNameNIRD", "DIRECTV WVBR0-25");
               orderMap.put("productLineNIRD", "WIRELESS VIDEO BRIDGE");
               orderMap.put("makeNIRD", "DIRECTV");
               orderMap.put("modelNIRD", "WVBR0-25");
               orderMap.put("macaddress", "MW2" + ban);
               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(1) + "]");
            }
            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(1) + "]");
               continue;
            }
         }


         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("macaddress", "M03" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");

         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("accessCardId", "A04" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");

         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(
            2)))
         {


            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("receiverID", "R04" + ban);
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("accessCardId", "A07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR54-200");
            orderMap.put("productNameReceiver", "DIRECTV HR54-200");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("10" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R10" + ban);
            orderMap.put("accessCardId", "A10" + ban);
            orderMap.put("serialnumber", "S10" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-100");
            orderMap.put("productNameReceiver", "DIRECTV R15-100");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("11" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R10" + ban);
            orderMap.put("macaddress", "M11" + ban);
            orderMap.put("serialnumber", "S11" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("12" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R10" + ban);
            orderMap.put("macaddress", "M12" + ban);
            orderMap.put("serialnumber", "S12" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("13" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R13" + ban);
            orderMap.put("accessCardId", "A13" + ban);
            orderMap.put("serialnumber", "S13" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-100");
            orderMap.put("productNameReceiver", "DIRECTV R15-100");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("14" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R13" + ban);
            orderMap.put("macaddress", "M14" + ban);
            orderMap.put("serialnumber", "S14" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-400");
            orderMap.put("productNameClient", "DIRECTV C61W-400");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("15" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R13" + ban);
            orderMap.put("macaddress", "M15" + ban);
            orderMap.put("serialnumber", "S15" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C31-700");
            orderMap.put("productNameClient", "DIRECTV C31-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && ("16" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R16" + ban);
            orderMap.put("accessCardId", "A16" + ban);
            orderMap.put("serialnumber", "S16" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR10-250");
            orderMap.put("productNameReceiver", "DIRECTV HR10-250");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("17" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R16" + ban);
            orderMap.put("macaddress", "M17" + ban);
            orderMap.put("serialnumber", "S17" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-100");
            orderMap.put("productNameClient", "DIRECTV C41W-100");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("18" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R16" + ban);
            orderMap.put("macaddress", "M18" + ban);
            orderMap.put("serialnumber", "S18" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("19" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R19" + ban);
            orderMap.put("accessCardId", "A19" + ban);
            orderMap.put("serialnumber", "S19" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H20-600");
            orderMap.put("productNameReceiver", "DIRECTV H20-600");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("20" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R19" + ban);
            orderMap.put("macaddress", "M20" + ban);
            orderMap.put("serialnumber", "S20" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2017 4K");
            orderMap.put("productNameClient", "SAMSUNG 2017 4K");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("21" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R19" + ban);
            orderMap.put("macaddress", "M21" + ban);
            orderMap.put("serialnumber", "S21" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-500");
            orderMap.put("productNameClient", "DIRECTV C41-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("22" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R22" + ban);
            orderMap.put("accessCardId", "A22" + ban);
            orderMap.put("serialnumber", "S22" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("23" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R22" + ban);
            orderMap.put("macaddress", "M23" + ban);
            orderMap.put("serialnumber", "S23" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("24" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R22" + ban);
            orderMap.put("macaddress", "M24" + ban);
            orderMap.put("serialnumber", "S24" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-100");
            orderMap.put("productNameClient", "DIRECTV C51-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("25" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R25" + ban);
            orderMap.put("accessCardId", "A25" + ban);
            orderMap.put("serialnumber", "S25" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("26" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R25" + ban);
            orderMap.put("macaddress", "M26" + ban);
            orderMap.put("serialnumber", "S26" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("27" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R25" + ban);
            orderMap.put("macaddress", "M27" + ban);
            orderMap.put("serialnumber", "S27" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-100");
            orderMap.put("productNameClient", "DIRECTV C51-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("28" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R28" + ban);
            orderMap.put("accessCardId", "A28" + ban);
            orderMap.put("serialnumber", "S28" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-300");
            orderMap.put("productNameReceiver", "DIRECTV R16-300");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("29" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R28" + ban);
            orderMap.put("macaddress", "M29" + ban);
            orderMap.put("serialnumber", "S29" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("30" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R28" + ban);
            orderMap.put("macaddress", "M30" + ban);
            orderMap.put("serialnumber", "S30" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-700");
            orderMap.put("productNameClient", "DIRECTV C51-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("31" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R31" + ban);
            orderMap.put("accessCardId", "A31" + ban);
            orderMap.put("serialnumber", "S31" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-100");
            orderMap.put("productNameReceiver", "DIRECTV R15-100");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("32" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R31" + ban);
            orderMap.put("macaddress", "M32" + ban);
            orderMap.put("serialnumber", "S32" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("33" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R31" + ban);
            orderMap.put("macaddress", "M33" + ban);
            orderMap.put("serialnumber", "S33" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("34" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R34" + ban);
            orderMap.put("accessCardId", "A34" + ban);
            orderMap.put("serialnumber", "S34" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H20-600");
            orderMap.put("productNameReceiver", "DIRECTV H20-600");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("35" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R34" + ban);
            orderMap.put("macaddress", "M35" + ban);
            orderMap.put("serialnumber", "S35" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2013");
            orderMap.put("productNameClient", "SAMSUNG 2013");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("36" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R34" + ban);
            orderMap.put("macaddress", "M36" + ban);
            orderMap.put("serialnumber", "S36" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("37" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R37" + ban);
            orderMap.put("accessCardId", "A37" + ban);
            orderMap.put("serialnumber", "S37" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "COM23-600");
            orderMap.put("productNameReceiver", "DIRECTV COM23-600");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("38" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R37" + ban);
            orderMap.put("macaddress", "M38" + ban);
            orderMap.put("serialnumber", "S38" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2014");
            orderMap.put("productNameClient", "SAMSUNG 2014");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("39" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R37" + ban);
            orderMap.put("macaddress", "M39" + ban);
            orderMap.put("serialnumber", "S39" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("40" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R40" + ban);
            orderMap.put("accessCardId", "A40" + ban);
            orderMap.put("serialnumber", "S40" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "COM23-600");
            orderMap.put("productNameReceiver", "DIRECTV COM23-600");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("41" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R40" + ban);
            orderMap.put("macaddress", "M41" + ban);
            orderMap.put("serialnumber", "S41" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2014");
            orderMap.put("productNameClient", "SAMSUNG 2014");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("42" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R40" + ban);
            orderMap.put("macaddress", "M42" + ban);
            orderMap.put("serialnumber", "S42" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("43" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R43" + ban);
            orderMap.put("accessCardId", "A43" + ban);
            orderMap.put("serialnumber", "S43" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H20-600");
            orderMap.put("productNameReceiver", "DIRECTV H20-600");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("44" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R43" + ban);
            orderMap.put("macaddress", "M44" + ban);
            orderMap.put("serialnumber", "S44" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("45" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R43" + ban);
            orderMap.put("macaddress", "M45" + ban);
            orderMap.put("serialnumber", "S45" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("46" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R46" + ban);
            orderMap.put("accessCardId", "A46" + ban);
            orderMap.put("serialnumber", "S46" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("47" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R46" + ban);
            orderMap.put("macaddress", "M47" + ban);
            orderMap.put("serialnumber", "S47" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-400");
            orderMap.put("productNameClient", "DIRECTV C61W-400");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("48" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R46" + ban);
            orderMap.put("macaddress", "M48" + ban);
            orderMap.put("serialnumber", "S48" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-700");
            orderMap.put("productNameClient", "DIRECTV C61-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("49" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R49" + ban);
            orderMap.put("accessCardId", "A49" + ban);
            orderMap.put("serialnumber", "S49" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("50" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R49" + ban);
            orderMap.put("macaddress", "M50" + ban);
            orderMap.put("serialnumber", "S50" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-400");
            orderMap.put("productNameClient", "DIRECTV C61W-400");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("51" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R49" + ban);
            orderMap.put("macaddress", "M51" + ban);
            orderMap.put("serialnumber", "S51" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-700");
            orderMap.put("productNameClient", "DIRECTV C61-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("52" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R52" + ban);
            orderMap.put("accessCardId", "A52" + ban);
            orderMap.put("serialnumber", "S52" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("53" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R52" + ban);
            orderMap.put("macaddress", "M53" + ban);
            orderMap.put("serialnumber", "S53" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-400");
            orderMap.put("productNameClient", "DIRECTV C61W-400");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("54" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R52" + ban);
            orderMap.put("macaddress", "M54" + ban);
            orderMap.put("serialnumber", "S54" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-700");
            orderMap.put("productNameClient", "DIRECTV C61-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && (ban + "55").equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R55" + ban);
            orderMap.put("accessCardId", "A55" + ban);
            orderMap.put("serialnumber", "S55" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("56" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R55" + ban);
            orderMap.put("macaddress", "M56" + ban);
            orderMap.put("serialnumber", "S56" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("57" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R55" + ban);
            orderMap.put("macaddress", "M57" + ban);
            orderMap.put("serialnumber", "S57" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-700");
            orderMap.put("productNameClient", "DIRECTV C61-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && ("58" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R58" + ban);
            orderMap.put("accessCardId", "A58" + ban);
            orderMap.put("serialnumber", "S58" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "C41W-100");
            orderMap.put("productNameReceiver", "DIRECTV C41W-100");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("59" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R58" + ban);
            orderMap.put("macaddress", "M59" + ban);
            orderMap.put("serialnumber", "S59" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("60" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R58" + ban);
            orderMap.put("macaddress", "M60" + ban);
            orderMap.put("serialnumber", "S60" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && ("61" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R61" + ban);
            orderMap.put("accessCardId", "A61" + ban);
            orderMap.put("serialnumber", "S61" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR21-700");
            orderMap.put("productNameReceiver", "DIRECTV HR21-700");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("62" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R61" + ban);
            orderMap.put("macaddress", "M62" + ban);
            orderMap.put("serialnumber", "S62" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("63" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R61" + ban);
            orderMap.put("macaddress", "M63" + ban);
            orderMap.put("serialnumber", "S63" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("64" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R64" + ban);
            orderMap.put("accessCardId", "A64" + ban);
            orderMap.put("serialnumber", "S64" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("65" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R64" + ban);
            orderMap.put("macaddress", "M65" + ban);
            orderMap.put("serialnumber", "S65" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("66" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R64" + ban);
            orderMap.put("macaddress", "M66" + ban);
            orderMap.put("serialnumber", "S66" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("67" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R67" + ban);
            orderMap.put("accessCardId", "A67" + ban);
            orderMap.put("serialnumber", "S67" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-500");
            orderMap.put("productNameReceiver", "DIRECTV R16-500");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("68" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R67" + ban);
            orderMap.put("macaddress", "M68" + ban);
            orderMap.put("serialnumber", "S68" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("69" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R67" + ban);
            orderMap.put("macaddress", "M69" + ban);
            orderMap.put("serialnumber", "S69" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("70" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R70" + ban);
            orderMap.put("accessCardId", "A70" + ban);
            orderMap.put("serialnumber", "S70" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R22-100");
            orderMap.put("productNameReceiver", "DIRECTV R22-100");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("71" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R70" + ban);
            orderMap.put("macaddress", "M71" + ban);
            orderMap.put("serialnumber", "S71" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("72" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R70" + ban);
            orderMap.put("macaddress", "M72" + ban);
            orderMap.put("serialnumber", "S72" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("73" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R73" + ban);
            orderMap.put("accessCardId", "A73" + ban);
            orderMap.put("serialnumber", "S73" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-200");
            orderMap.put("productNameReceiver", "DIRECTV H24-200");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("74" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R73" + ban);
            orderMap.put("macaddress", "M74" + ban);
            orderMap.put("serialnumber", "S74" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2015 4K");
            orderMap.put("productNameClient", "SAMSUNG 2015 4K");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("75" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R73" + ban);
            orderMap.put("macaddress", "M75" + ban);
            orderMap.put("serialnumber", "S75" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("76" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R76" + ban);
            orderMap.put("accessCardId", "A76" + ban);
            orderMap.put("serialnumber", "S76" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-200");
            orderMap.put("productNameReceiver", "DIRECTV H24-200");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("77" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R76" + ban);
            orderMap.put("macaddress", "M77" + ban);
            orderMap.put("serialnumber", "S77" + ban);
            orderMap.put("makeClient", "SONY");
            orderMap.put("modelClient", "2017 4K");
            orderMap.put("productNameClient", "SONY 2017 4K");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("78" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R76" + ban);
            orderMap.put("macaddress", "M78" + ban);
            orderMap.put("serialnumber", "S78" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-500");
            orderMap.put("productNameClient", "DIRECTV C41-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("79" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R79" + ban);
            orderMap.put("accessCardId", "A79" + ban);
            orderMap.put("serialnumber", "S79" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H20-999");
            orderMap.put("productNameReceiver", "DIRECTV H20-999");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("80" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R79" + ban);
            orderMap.put("macaddress", "M80" + ban);
            orderMap.put("serialnumber", "S80" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-500");
            orderMap.put("productNameClient", "DIRECTV C41-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("81" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R79" + ban);
            orderMap.put("macaddress", "M81" + ban);
            orderMap.put("serialnumber", "S81" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("82" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R82" + ban);
            orderMap.put("accessCardId", "A82" + ban);
            orderMap.put("serialnumber", "S82" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-300");
            orderMap.put("productNameReceiver", "DIRECTV R16-300");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("83" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R82" + ban);
            orderMap.put("macaddress", "M83" + ban);
            orderMap.put("serialnumber", "S83" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("84" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R82" + ban);
            orderMap.put("macaddress", "M84" + ban);
            orderMap.put("serialnumber", "S84" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("85" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R85" + ban);
            orderMap.put("accessCardId", "A85" + ban);
            orderMap.put("serialnumber", "S85" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R22-100");
            orderMap.put("productNameReceiver", "DIRECTV R22-100");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("86" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R85" + ban);
            orderMap.put("macaddress", "M86" + ban);
            orderMap.put("serialnumber", "S86" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("87" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R85" + ban);
            orderMap.put("macaddress", "M87" + ban);
            orderMap.put("serialnumber", "S87" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("88" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R88" + ban);
            orderMap.put("accessCardId", "A88" + ban);
            orderMap.put("serialnumber", "S88" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-300");
            orderMap.put("productNameReceiver", "DIRECTV R15-300");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("89" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R88" + ban);
            orderMap.put("macaddress", "M89" + ban);
            orderMap.put("serialnumber", "S89" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("90" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R88" + ban);
            orderMap.put("macaddress", "M90" + ban);
            orderMap.put("serialnumber", "S90" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-100");
            orderMap.put("productNameClient", "DIRECTV C51-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("91" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R91" + ban);
            orderMap.put("accessCardId", "A91" + ban);
            orderMap.put("serialnumber", "S91" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R16-300");
            orderMap.put("productNameReceiver", "DIRECTV R16-300");
            orderMap.put("productLineReceiver", "IRD - DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("92" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R91" + ban);
            orderMap.put("macaddress", "M92" + ban);
            orderMap.put("serialnumber", "S92" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-400");
            orderMap.put("productNameClient", "DIRECTV C61W-400");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("93" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R91" + ban);
            orderMap.put("macaddress", "M93" + ban);
            orderMap.put("serialnumber", "S93" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-700");
            orderMap.put("productNameClient", "DIRECTV C41-700");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("94" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R94" + ban);
            orderMap.put("accessCardId", "A94" + ban);
            orderMap.put("serialnumber", "S94" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-200");
            orderMap.put("productNameReceiver", "DIRECTV H24-200");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("95" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R94" + ban);
            orderMap.put("macaddress", "M95" + ban);
            orderMap.put("serialnumber", "S95" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2015 4K");
            orderMap.put("productNameClient", "SAMSUNG 2015 4K");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("96" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R94" + ban);
            orderMap.put("macaddress", "M96" + ban);
            orderMap.put("serialnumber", "S96" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }
         else if ("IRD - KA/KU".equals(entry.getValue().get(1)) && ("97" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R97" + ban);
            orderMap.put("accessCardId", "A97" + ban);
            orderMap.put("serialnumber", "S97" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-200");
            orderMap.put("productNameReceiver", "DIRECTV H24-200");
            orderMap.put("productLineReceiver", "IRD - KA/KU");
         }
         else if ("RVU CLIENT TV".equals(entry.getValue().get(1)) && ("98" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R97" + ban);
            orderMap.put("macaddress", "M98" + ban);
            orderMap.put("serialnumber", "S98" + ban);
            orderMap.put("makeClient", "SAMSUNG");
            orderMap.put("modelClient", "2015 4K");
            orderMap.put("productNameClient", "SAMSUNG 2015 4K");
            orderMap.put("productLineClient", "RVU CLIENT TV");
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("99" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R97" + ban);
            orderMap.put("macaddress", "M99" + ban);
            orderMap.put("serialnumber", "S99" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-100");
            orderMap.put("productNameClient", "DIRECTV C61-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }


   public void sendProcessIssacRequestForLineItemsAndIdentifiersForReceiver(String requestType, String origOrderId,
      Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, String> lineItemAndIdentifiers = getLineItemIDAndIdentifierByOrderId(origOrderId);

      Boolean harwarefound = false;

      for (Map.Entry<String, String> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue());
         if (entry.getValue().equals("Hardware") && harwarefound)
         {
            continue;
         }

         if (entry.getValue().equals("Hardware"))
         {
            harwarefound = true;
         }
         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }

   public java.util.Map<String, String> getLineItemIDAndIdentifierByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierByOrderId(origOrderId);
   }

   public boolean isLineItemActivationSuccessfull(String origOrderId, String lineItemId)
   {
      Map<String, String> valuesMap = new HashMap<String, String>();
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrderId);
      valuesMap.put("lineItemId", lineItemId);

      SupplierPredicateTdarEnum supplierPredicate = SupplierPredicateTdarEnum.LineItemActivationStatus;
      return Wait.untilCondition(supplierPredicate.getSupplier(getOlDao(), valuesMap), supplierPredicate.getPredicate(
         EnvConstants.STATUS_SUCCESS), 1, 5, MINUTES.toSeconds(2));
   }

   public boolean isLineItemActivationSuccessfullofIsvalid(String origOrderId, String lineItemId)
   {
      Map<String, String> valuesMap = new HashMap<String, String>();
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrderId);
      valuesMap.put("lineItemId", lineItemId);

      SupplierPredicateTdarSwapEnum supplierPredicate = SupplierPredicateTdarSwapEnum.LineItemActivationStatus1;
      return Wait.untilCondition(supplierPredicate.getSupplier(getOlDao(), valuesMap), supplierPredicate.getPredicate(
         EnvConstants.STATUS_SUCCESS), 1, 5, MINUTES.toSeconds(2));
   }

   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierProductTypeByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierProductTypeByOrderId(origOrderId);

   }

   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierForNoWvbByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierForNoWvbByOrderId(origOrderId);
   }


   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierProductlineByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);
   }

   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierActionIndicatorByOrderId(origOrderId);
   }

   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorForDeactivationByOrderId(
      String origOrderId) throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierActionIndicatorForDeactivationByOrderId(origOrderId);
   }

   public java.util.Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorAsMByOrderId(String origOrderId)
      throws AutomationBDDServiceException
   {
      return getOlDao().getLineItemIDAndIdentifierActionIndicatorAsMByOrderId(origOrderId);
   }

   public boolean validateProductNameByOrderIdForMobileDvr(String origOrdId, String Productname)
      throws AutomationBDDServiceException

   {
      String prdName = getOlDao().getProductNameFromOlByTdarWoliForMobileDvr(origOrdId);

      if (prdName.equals(Productname))
      {
         return true;

      }
      else
      {

         return false;
      }

   }

   public boolean validateDelayedRemoveValue(String origOrdId, String delayedremove)
      throws AutomationBDDServiceException

   {
      String DelayedRemove = getOlDao().getValidateDelayedRemoveValue(origOrdId);

      if (DelayedRemove.equals(delayedremove))
      {
         return true;

      }
      else
      {

         return false;
      }

   }

   public boolean validateDelayedRemoveColumn(String origOrdId, String delayedremove)
      throws AutomationBDDServiceException

   {
      String DelayedRemove = getOlDao().getValidateDelayedRemoveColumn(origOrdId);

      if (DelayedRemove.equals(delayedremove))
      {
         return true;

      }
      else
      {

         return false;
      }

   }


   public boolean validateLargeCustomerColumn(String origOrdId, String largecustomer)
      throws AutomationBDDServiceException

   {
      String LargeCustomer = getOlDao().getValidateLargeCustomerColumn(origOrdId);

      if (LargeCustomer.equals(largecustomer))
      {
         return true;

      }
      else
      {

         return false;
      }

   }

   public boolean validateProductNameByOrderIdForInternalWvb(String origOrdId, String Productname)
      throws AutomationBDDServiceException

   {
      String prdName = getOlDao().getProductNameFromOlByTdarWoliForInternalWvb(origOrdId);

      if (prdName.equals(Productname))
      {
         return true;

      }
      else
      {

         return false;
      }

   }

   public boolean validateProductNameByOrderIdForRB5(String origOrdId, String productName)
      throws AutomationBDDServiceException

   {
      String prdName = getOlDao().getProductNameFromOlByTdarWoli(origOrdId);

      if (prdName.equals(productName))
      {
         return true;

      }
      else
      {

         return false;
      }

   }

   public String validateManufacturerByOrderIdForPonr(String origOrdId) throws AutomationBDDServiceException
   {


      String manufacturer = getOlDao().getManufacturerForWvbFromOlByEih_All(origOrdId);
      logger.info("Manufacturer for WVB is " + manufacturer);
      return manufacturer;
   }

   public String validateModelByOrderIdForPonr(String origOrdId) throws AutomationBDDServiceException

   {
      String model = getOlDao().getModelForWvbFromOlByEih_All(origOrdId);
      logger.info("Model for WVB is " + model);

      return model;
   }

   public void validateWoliCompletionTime(String origOrdId) throws AutomationBDDServiceException
   {
      // ShellClient shellUtil;
      // shellUtil = new ShellClient("nmsls3j1.snt.bst.bls.com", 22, new SaltedHashCredentials("ug2454",
      // "H:\\Documents\\PuTTY\\PuTTY\\privatekey2.ppk", "Techm@123"));
      // System.out.println("connection established!");


      // String commands= "cd /opt/app/bbnmsls/ol/log ; grep -l 'Woli Completion deadline time = \" +init\"
      // workflow.log";
      //
      // System.out.println(shellUtil.execute(commands));


   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForMulGenie(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);
      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("macAddress", ban + "GHI");
               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }
            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");


               continue;
            }
         }


         if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(
            2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("accessCardId", "A03" + ban);


         }


         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("macaddress", "M04" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }


         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-100");
            orderMap.put("productNameReceiver", "DIRECTV HR24-100");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("accessCardId", "A07" + ban);


         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("accessCardId", "A08" + ban);


         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("accessCardId", "A09" + ban);


         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("10" + ban).equals(entry
            .getValue().get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R10" + ban);
            orderMap.put("serialnumber", "S10" + ban);
            orderMap.put("accessCardId", "A10" + ban);


         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("11" + ban).equals(entry
            .getValue().get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R11" + ban);
            orderMap.put("serialnumber", "S11" + ban);
            orderMap.put("accessCardId", "A11" + ban);


         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("12" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R11" + ban);
            orderMap.put("serialnumber", "S12" + ban);
            orderMap.put("macaddress", "M12" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("13" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R11" + ban);
            orderMap.put("serialnumber", "S13" + ban);
            orderMap.put("macaddress", "M13" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }


         sendIssacRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }

   }

   public void sendProcessIssacRequestForLineItemsAndIdentifiersForMulStbs(String requestType, String origOrderId,
      String ban, Map orderMap) throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierProductlineByOrderId(origOrderId);
      boolean hardwarefound = false;

      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));
         if (entry.getValue().get(0).equals("Hardware"))
         {
            if ("WIRELESS VIDEO BRIDGE".equals(entry.getValue().get(1)) && ("55" + ban).equals(entry.getValue().get(2)))
            {
               orderMap.put("macAddress", ban + "GHI");
               logger.info("Sending request for Hardware:" + "[" + entry.getValue().get(0) + "]");
            }
            else
            {
               logger.info("Skipping request for Hardware:" + "[" + entry.getValue().get(0) + "]");


               continue;
            }
         }


         if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(
            2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("accessCardId", "A03" + ban);

         }


         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("macaddress", "M04" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }


         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R03" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - HD/DVR COMBO".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H24-100");
            orderMap.put("productNameReceiver", "DIRECTV HR24-100");
            orderMap.put("productLineReceiver", "IRD - HD/DVR COMBO");
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("accessCardId", "A07" + ban);


         }
         // OMS CH
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(2)))


         {

            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("receiverID", "R08" + ban);
            orderMap.put("macaddress", "M09" + ban);
         }
         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR34-700");
            orderMap.put("productNameReceiver", "DIRECTV HR34-700");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
            orderMap.put("receiverID", "R08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("accessCardId", "A08" + ban);

         }

         // OMS CH

         sendIssacRequest(requestType, orderMap);
         try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation failed");
         }
      }
   }

   public List validateMultipleDSWM30AndPowerInsertor(String origOrdId) throws AutomationBDDServiceException
   {
      List<String> DigitalSWM = getOlDao().getMultipleDSWM30AndPowerInsertor(origOrdId);
      logger.info("Details of tdarworkorderlineiteminfo are ");
      for (String list : DigitalSWM)
      {
         // logger.info("Details of tdarworkorderlineiteminfo are " + DigitalSWM );
         logger.info(list);

      }
      return DigitalSWM;
   }

   public List ValidateISAACErrorMessageForClients(String origOrdId) throws AutomationBDDServiceException
   {
      List<String> StatusDescription = getOlDao().getIssacErrorMessageForClients(origOrdId);
      logger.info("Details of tdarworkorderlineiteminfo are ");
      for (String list : StatusDescription)
      {
         // logger.info("Error msg of tdarissactransactioninfo is " + No Equipment found );
         logger.info(list);

      }
      return StatusDescription;
   }

   public String validateRecIdInPONR(String origOrdId) throws AutomationBDDServiceException
   {
      String receiverIDPRorder = getOlDao().getRecIdForClientEih_All(origOrdId);
      logger.info("Receiver Id for client is " + receiverIDPRorder);
      return receiverIDPRorder;

   }

   public String validateRecIdInPONRCHorders(String origOrdId) throws AutomationBDDServiceException
   {
      String receiverIDCHorder = getOlDao().getRecIdForClientInCHorderEih_All(origOrdId);
      logger.info("Receiver Id for client in CH order is " + receiverIDCHorder);
      return receiverIDCHorder;

   }

   public boolean validateValueOfGenieReceiver(String origOrdId, String genieFlag) throws AutomationBDDServiceException
   {
      String GenieReceiver = getOlDao().getValueOfGenieReceiver(origOrdId);
      logger.info("Value of Genie flag is " + genieFlag);

      if (GenieReceiver.equals(genieFlag))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public List swapActionForGenie(String origOrdId) throws AutomationBDDServiceException
   {
      List<String> SwapAction = getOlDao().getswapActionForGenie(origOrdId);
      for (String list : SwapAction)
      {
         // logger.info("Details of tdarworkorderlineiteminfo are " + DigitalSWM );
         logger.info(list);

      }
      return SwapAction;

   }

   public String hardRestoreforSuspendOrders(String origOrdId) throws AutomationBDDServiceException
   {
      String hardRestore = getOlDao().gethardRestoreforSuspendOrders(origOrdId);
      logger.info("Udas interface will send the tag " + hardRestore);
      return hardRestore;

   }

   public String hardSuspendforSuspendOrders(String origOrdId) throws AutomationBDDServiceException
   {
      String hardSuspend = getOlDao().gethardRestoreforSuspendOrders(origOrdId);
      logger.info("Udas interface will send the tag  " + hardSuspend);
      return hardSuspend;

   }

   public List rollbackNoOpforSUandRSorders(String origOrdId) throws AutomationBDDServiceException
   {
      logger.info("There is no RollbackManageVideoServiceOrder activity.So rollback is no-op");
      List<String> rollback_NoOp = getOlDao().getrollbackNoOpforSUandRSorders(origOrdId);
      for (String list : rollback_NoOp)
      {

         logger.info(list);

      }

      return rollback_NoOp;

   }

   public List reassignWoliForAhl(String origOrdId) throws AutomationBDDServiceException
   {
      logger.info("AHL will generate the WOLI - ");
      List<String> reassignWoli = getOlDao().getreassignWoliForAhl(origOrdId);
      for (String list : reassignWoli)
      {

         logger.info(list);

      }


      return reassignWoli;
   }

   public List MVSRrequestforUDAS(String origOrdId) throws AutomationBDDServiceException
   {
      logger.info("The 'D' receiver has been rollback - ");
      List<String> addReceiver = getOlDao().getMVSRrequestforUDAS(origOrdId);
      for (String list : addReceiver)
      {

         logger.info(list);

      }
      return addReceiver;
   }

   public void verifyDealerCodeReceiver(String ban) throws AutomationBDDServiceException
   {
      // TODO Auto-generated method stub
      String dealerCode = getOlDao().verifyDealerCodeReceiver(ban);
      logger.info(dealerCode);
      logger.info("Dealer Code Present in Receiver");

   }

   public void verifyDealerCodeHardware(String ban) throws AutomationBDDServiceException
   {
      // TODO Auto-generated method stub
      String dealerCode = getOlDao().verifyDealerCodeHardware(ban);
      logger.info(dealerCode);
      logger.info("Dealer Code Present in Hardware");

   }

   public void verifyDealerCodeAsset(String ban) throws AutomationBDDServiceException
   {
      // TODO Auto-generated method stub
      String dealerCode = getAssetDao().verifyDealerCodeAsset(ban);
      logger.info(dealerCode);
      logger.info("Dealer Code Present in Asset DB");

   }

   public void verifyDealerandNFFL(String ban, String orderno) throws AutomationBDDServiceException
   {
      // TODO Auto-generated method stub
      String dealerCode = getOlDao().verifyDealerCodeReceiver(ban);
      logger.info(dealerCode);
      logger.info("Dealer Code Present");

      String nfflIndicator = getOlDao().verifyNfflIndicator(orderno);
      logger.info(nfflIndicator);
      logger.info("NFFL Indicator Present");

   }

   public void sendProcessActivationAndNotify(String requestType, String origOrderId, String ban, Map orderMap)
      throws AutomationBDDServiceException
   {

      Map<String, List<String>> lineItemAndIdentifiers = getLineItemIDAndIdentifierForNoWvbByOrderId(origOrderId);


      for (Map.Entry<String, List<String>> entry : lineItemAndIdentifiers.entrySet())
      {

         orderMap.put("lineItemID", entry.getKey());
         orderMap.put("productTypeIdentifier", entry.getValue().get(0));

         if ("IRD - GENIE LITE".equals(entry.getValue().get(1)) && ("1" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "H44-500");
            orderMap.put("productNameReceiver", "DIRECTV H44-500");
            orderMap.put("productLineReceiver", "IRD - GENIE LITE");
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S01" + ban);
            orderMap.put("accessCardId", "A01" + ban);
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("2" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S02" + ban);
            orderMap.put("macaddress", "M02" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41-100");
            orderMap.put("productNameClient", "DIRECTV C41-100");
            orderMap.put("productLineClient", "IRD - CLIENT");

         }
         else if ("IRD - 4K CLIENT".equals(entry.getValue().get(1)) && ("3" + ban).equals(entry.getValue().get(2)))


         {
            orderMap.put("receiverID", "R01" + ban);
            orderMap.put("serialnumber", "S03" + ban);
            orderMap.put("macaddress", "M03" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61K-700");
            orderMap.put("productNameClient", "DIRECTV C61K-700");
            orderMap.put("productLineClient", "IRD - 4K CLIENT");

         }
         else if ("IRD - DVR".equals(entry.getValue().get(1)) && ("4" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("accessCardId", "A04" + ban);
            orderMap.put("serialnumber", "S04" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "R15-500");
            orderMap.put("productNameReceiver", "DIRECTV R15-500");
            orderMap.put("productLineReceiver", "IRD - DVR");

         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("5" + ban).equals(entry.getValue().get(
            2)))
         {


            orderMap.put("macaddress", "M05" + ban);
            orderMap.put("serialnumber", "S05" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61W-700");
            orderMap.put("productNameClient", "DIRECTV C61W-700");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
            orderMap.put("receiverID", "R04" + ban);
         }
         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("6" + ban).equals(entry.getValue().get(2)))
         {

            orderMap.put("receiverID", "R04" + ban);
            orderMap.put("macaddress", "M06" + ban);
            orderMap.put("serialnumber", "S06" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C51-500");
            orderMap.put("productNameClient", "DIRECTV C51-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }

         else if ("IRD - ADVANCED WHOLE-HOME DVR".equals(entry.getValue().get(1)) && ("7" + ban).equals(entry.getValue()
            .get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("accessCardId", "A07" + ban);
            orderMap.put("serialnumber", "S07" + ban);
            orderMap.put("makeReceiver", "DIRECTV");
            orderMap.put("modelReceiver", "HR54-200");
            orderMap.put("productNameReceiver", "DIRECTV HR54-200");
            orderMap.put("productLineReceiver", "IRD - ADVANCED WHOLE-HOME DVR");
         }
         else if ("IRD - WIRELESS CLIENT".equals(entry.getValue().get(1)) && ("8" + ban).equals(entry.getValue().get(
            2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M08" + ban);
            orderMap.put("serialnumber", "S08" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C41W-500");
            orderMap.put("productNameClient", "DIRECTV C41W-500");
            orderMap.put("productLineClient", "IRD - WIRELESS CLIENT");
         }

         else if ("IRD - CLIENT".equals(entry.getValue().get(1)) && ("9" + ban).equals(entry.getValue().get(2)))
         {
            orderMap.put("receiverID", "R07" + ban);
            orderMap.put("macaddress", "M09" + ban);
            orderMap.put("serialnumber", "S09" + ban);
            orderMap.put("makeClient", "DIRECTV");
            orderMap.put("modelClient", "C61-500");
            orderMap.put("productNameClient", "DIRECTV C61-500");
            orderMap.put("productLineClient", "IRD - CLIENT");
         }


         sendIssacNoWvbRequest(requestType, orderMap);

         if (!isLineItemActivationSuccessfull(origOrderId, entry.getKey()))
         {
            throw new IllegalStateException("For Order:" + origOrderId + ", lineItemId:" + entry.getKey()
               + " Activation faled");
         }
      }
   }

   public void verifyDealerCodeSwap(String ban) throws AutomationBDDServiceException
   {
      // TODO Auto-generated method stub
      String dealerCode = getOlDao().verifyDealerCodeSwap(ban);
      logger.info(dealerCode);
      logger.info("Dealer Code Present in Hardware");

   }

   public void addDealerCode(Map orderDetailsAsMap, String nfflInd, String dealerCode)
   {
      // TODO Auto-generated method stub
      if (orderDetailsAsMap == null)
      {
         return;
      }
      orderDetailsAsMap.put("nfflInd", nfflInd);
      orderDetailsAsMap.put("dealerCode", dealerCode);
   }

   public boolean updateFipsAtributes(Map<String, String> orderDetailsAsMap, String fipsValue)
   {
      if (orderDetailsAsMap == null)
      {
         logger.error("orderMap is null. please check");
         return false;
      }
      else
      {
         orderDetailsAsMap.put("countyFips", fipsValue);
         logger.info("countyFips attribute value is updated with value - " + fipsValue);
         return true;
      }
   }

   public boolean checkOmsStatus(String expectedVal, String orderNumber) throws DAOServiceExeption, InterruptedException
   {
      String value = "";
      Thread.sleep(20000);
      value = getOlDao().getXmlContentFromEihAll(orderNumber, propUtil.getProperty(
         XpathConstants.XPATH_EIH_PT_RESPONSE_STATUS), "provisionTransport Response", "");
      if (expectedVal.equalsIgnoreCase("Accepts"))
      {
         if (value.equals("Success"))
         {
            logger.info("For Order Number : " + orderNumber + " PT response status is " + value);
            return true;
         }
         else
         {
            logger.error("Error. For Order Number : " + orderNumber + " PT response status is " + value);
            return false;
         }
      }
      else
      {
         if (value.equals("Fail"))
         {
            logger.info("For Order Number : " + orderNumber + " PT response status is " + value);
            value = getOlDao().getXmlContentFromEihAll(orderNumber, propUtil.getProperty(
               XpathConstants.XPATH_EIH_PT_RESPONSE_REASON), "provisionTransport Response", "");
            logger.info("Error Reason is : " + value);
            return true;
         }
         else
         {
            logger.error("Error. For Order Number : " + orderNumber + " PT response status is " + value);
            return false;
         }
      }
   }

   public void updateStbActionIndicator(Map<String, String> orderDetailsAsMap, String actionIndicator)
   {
      if (orderDetailsAsMap == null)
      {
         return;
      }
      orderDetailsAsMap.put("dtstb1Ind", actionIndicator);
      orderDetailsAsMap.put("dtstb2Ind", actionIndicator);
   }


}

