package com.att.bbnmstest.services.wll;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.ShellClient;
import com.att.bbnmstest.client.utils.SaltedHashCredentials;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.dao.ToolsDAO;
import com.att.bbnmstest.services.dispatcher.FirstRtDispatcher;
import com.att.bbnmstest.services.dispatcher.OMSPubRgDispatcher;
import com.att.bbnmstest.services.dispatcher.WllCsiiServiceDispatcher;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.helper.RequestType;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.OrderObjectMapper;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.Utils;
import com.att.bbnmstest.services.util.XpathConstants;


@Component
@Qualifier("FixedWirelessService")
public class FixedWirelessService extends BBNMSService
{
   private final static Logger logger = Logger.getLogger(FixedWirelessService.class);

   @Autowired
   private PropUtil propUtil;

   @Autowired
   private OrderObjectMapper orderObjectMapper;

   @Autowired
   private ToolsDAO toolsDao;

   @Autowired
   private OMSPubRgDispatcher omsPugRgAdapter;

   @Autowired
   private WllCsiiServiceDispatcher wllCsiiAdapter;

   @Autowired
   private FirstRtDispatcher firstRtAdapter;

   public Map<String, String> setUpOrder(String orderActionType, String orderActionSubType, Map context)
      throws AutomationBDDServiceException
   {

      Map<String, String> orderNumbers = createUniqueOrdernumberAndOriginalOrderId(EnvConstants.GENERATE_ID_PREFIX_WLL);
      String orderNumber = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      String origOrdId = orderNumbers.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);

      String ban = null;
      String orderNumberCm = null;
      String origOrdIdCm = null;
      String serviceId = null;

      if (orderActionType.equals("PV"))
      {
         Map<String, String> orderNumbersCm = createUniqueOrdernumberAndOriginalOrderId(
            EnvConstants.GENERATE_ID_PREFIX_WLL);
         orderNumberCm = orderNumbersCm.get(EnvConstants.TAG_ORDER_NUMBER);
         origOrdIdCm = orderNumbersCm.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);
      }

      if (orderActionType.equals("PR"))
      {
         ban = origOrdId;
         serviceId = "A8/WLXX/" + ban + "//SB";
      }
      else if (context != null)
      {
         ban = (String) context.get(EnvConstants.TAG_BAN);
         serviceId = (String) context.get(EnvConstants.TAG_SERVICE_ID);
      }

      Map<String, String> orderObjMap = orderObjectMapper.createNextGenWirelessObjectMap(origOrdId, orderNumber, ban,
         orderActionType, orderActionSubType, serviceId, orderNumberCm, origOrdIdCm);

      logger.info("Wll Order Map :" + orderObjMap);
      return orderObjMap;
   }


   public void updateOrderBanAndServiceId(String orderActionType, String ban, String serviceId, Map orderDetailsAsMap)
   {

      if (orderActionType.equals("PV"))
      {
         String serviceIdCm = serviceId;
         String serviceIdPv = "A8/WLXX/" + ban + "//SC";
         orderDetailsAsMap.put("serviceIdPv", serviceIdPv);
         orderDetailsAsMap.put("serviceIdCm", serviceIdCm);
      }
      orderDetailsAsMap.put("serviceID", serviceId);
      orderDetailsAsMap.put("ban", ban);
   }


   public Map<String, String> generateUniqueOrdernumberAndOriginalOrderIdFt(String pvOrderNumber)
      throws AutomationBDDServiceException
   {

      Map<String, String> valuesMap = new HashMap<>();

      String orderNumber = getUniqueOrderNumber(pvOrderNumber);

      String origOrdId = getUniqueOrderNumber(orderNumber);

      valuesMap.put(EnvConstants.TAG_ORDER_NUMBER, orderNumber);
      valuesMap.put(EnvConstants.TAG_ORIG_ORDER_ACTION_ID, origOrdId);

      return valuesMap;

   }

   public void sendOmsRequest(String requestType, Map<String, String> orderDetailsAsMap)
      throws AutomationBDDServiceException
   {
      String template = null;

      if (orderDetailsAsMap.containsKey("wdmIndPv"))
      {
         template = propUtil.getProperty(EnvConstants.TEMPLATE_OMS_WIRELESS_FT_REQ);
      }
      else
      {
         template = propUtil.getProperty(EnvConstants.TEMPLATE_OMS_WIRELESS_REQ);
      }


      RequestType.valueOf(requestType).sendOmsRequest(getOmsAdapter(), orderDetailsAsMap, template);
   }

   public List<String> getWllBanAndServiceFromWllSubscriptions(String subTransportType)
      throws AutomationBDDServiceException
   {
      return getOlDao().getWllBanAndServiceFromWllSubscriptions(subTransportType);
   }

   public Map<String, String> generateIccidImeiImsi() throws AutomationBDDServiceException
   {

      Map<String, String> wllSimValueMap = new HashMap<>();

      String iccid = generateIccid();

      String imei = generateImei();

      String imsi = generateImsi();

      wllSimValueMap.put("iccid", iccid);
      wllSimValueMap.put("imei", imei);
      wllSimValueMap.put("imsi", imsi);
      logger.info("Unique ICCID , IMEI, IMSI :- " + wllSimValueMap);
      return wllSimValueMap;
   }

   public String generateImsi() throws AutomationBDDServiceException
   {
      String imsi = null;
      do
      {
         imsi = toolsDao.getUniqueImsi();
      } while (!getOlDao().validateUniqueImsi(imsi));

      return imsi;
   }

   public String generateImei() throws AutomationBDDServiceException
   {
      String imei = null;
      do
      {
         imei = toolsDao.getUniqueImei();
      } while (!getOlDao().validateUniqueImeiId(imei));

      return imei;
   }

   public String generateIccid() throws AutomationBDDServiceException
   {
      String iccid = null;
      do
      {
         iccid = toolsDao.getUniqueIccid();
      } while (!getOlDao().validateUniqueIccid(iccid));

      return iccid;
   }

   public Map<String, String> generateInvalidIccidImeiImsi() throws AutomationBDDServiceException
   {

      Map<String, String> wllSimValueMap = new HashMap<>();

      wllSimValueMap.put("iccid", "11111");
      wllSimValueMap.put("imei", "11111");
      wllSimValueMap.put("imsi", "11111");
      logger.info("Unique ICCID , IMEI, IMSI :- " + wllSimValueMap);
      return wllSimValueMap;
   }

   public Map<String, String> createAndUpdateSimResponse(String orderActionType, Map<String, String> orderMap,
      Map<String, String> simValueMap) throws AutomationBDDServiceException
   {

      Map<String, String> params = new HashMap<>();

      params.put("tn", orderMap.get("tnPrefix") + orderMap.get("ban"));

      params.put("iccid", simValueMap.get("iccid"));
      params.put("imei", simValueMap.get("imei"));
      params.put("imsi", simValueMap.get("imsi"));
      params.put("statuscode", "CS");
      params.put("completedatatime", Utils.getExtendedDateByDays(0) + "Z");

      if (StringUtils.equals(EnvConstants.ORDER_TYPE_MOVE, orderActionType))
      {
         toolsDao.insertSimResponseInToolsDb("csinsm", params);
         toolsDao.updateSimResponseInToolsDb("csinsm", params);
      }
      else
      {
         toolsDao.insertSimResponseInToolsDb("csinsm", params);
         toolsDao.insertSimResponseInToolsDb("csisc", params);
         toolsDao.updateSimResponseInToolsDb("csinsm", params);
         toolsDao.updateSimResponseInToolsDb("csisc", params);
      }

      return params;
   }

   public Map<String, String> createAndUpdateSimScRespOnly(String orderActionType, Map<String, String> orderMap,
      Map<String, String> simValueMap) throws AutomationBDDServiceException
   {
      Map<String, String> params = new HashMap<>();

      params.put("tn", orderMap.get("tnPrefix") + orderMap.get("ban"));
      params.put("statuscode", "CS");
      params.put("completedatatime", Utils.getExtendedDateByDays(0) + "Z");
      toolsDao.insertSimResponseInToolsDb("csisc", params);
      toolsDao.updateSimResponseInToolsDb("csisc", params);
      return params;
   }

   public boolean updateCsiscforMoveOrder(Map<String, String> params) throws AutomationBDDServiceException
   {
      return (toolsDao.insertSimResponseInToolsDb("csisc", params) && toolsDao.updateSimResponseInToolsDb("csisc",
         params));
   }

   public Map<String, String> createWllPubRgNotification(String ban, String origOrderActId, String orderActionType,
      String serviceID)
   {
      return orderObjectMapper.createWllPubRgNotification(ban, origOrderActId, orderActionType, serviceID);
   }

   public Map<String, String> createInvalidWllPubRgNotification(String ban, String origOrderActId,
      String orderActionType, String serviceID)
   {
      Map<String, String> pubRgMap = orderObjectMapper.createWllPubRgNotification(ban, origOrderActId, orderActionType,
         serviceID);
      pubRgMap.put("vendor", "ABCD");
      pubRgMap.put("model", "ABCD");
      pubRgMap.put("deviceId", "ABCD");
      pubRgMap.put("serialNumber", "ABCD");
      pubRgMap.put("month", "13");
      return pubRgMap;
   }


   public void sendWllOmsPubRgRequest(Map orderDetailsAsMap) throws AutomationBDDServiceException
   {
      omsPugRgAdapter.sendRequest(orderDetailsAsMap, propUtil.getProperty(EnvConstants.TEMPLATE_OMS_WLL_PUBRG_REQ));
   }

   public Map<String, String> createWllIssacRequest(String origOrderActId, String ban, String mode,
      String subTransportType, Map<String, String> simValueMap)
   {
      return orderObjectMapper.createFiedWirelessIsaacRequestMap(ban, mode, simValueMap.get("imei"), simValueMap.get(
         "iccid"), origOrderActId, subTransportType);
   }


   public String sendWllIssacRequest(Map orderDetailsAsMap) throws AutomationBDDServiceException
   {
      logger.info("Send Wll Issac Request :" + orderDetailsAsMap);
      return wllCsiiAdapter.sendRequest(orderDetailsAsMap, propUtil.getProperty(
         EnvConstants.TEMPLATE_WLL_CSII_INSTALL_SERVICE_REQ));
   }

   public boolean validateWllSubscriberResumedStatus(String ban) throws AutomationBDDServiceException
   {
      return EnvConstants.STATUS_RESUMED.equals(getOlDao().getWllSubscriberStatusFromOl(ban));
   }

   public boolean validateWllSubscriberSuspendedStatus(String ban) throws AutomationBDDServiceException
   {
      return EnvConstants.STATUS_SUSPENDED.equals(getOlDao().getWllSubscriberStatusFromOl(ban));
   }

   public List<String> getWllBanServiceIdForSuspendedOrder() throws AutomationBDDServiceException
   {
      return getOlDao().getSuspendedBanAndServiceFromWllSubscription();
   }

   public boolean validateWoliInCsiWfRequest(String origOrderId, String productLine)
      throws AutomationBDDServiceException
   {

      String xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_REQ_PRODUCTLINE);
      String woli = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TAG_CREATE_FIBER_SERVICE_WORK_ORDER_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);

      if (productLine != null && woli != null)
      {
         return woli.equals(productLine);
      }

      return false;
   }


   public boolean validateSubTransportTypeFromEih(String origOrderId, String subTransportType)
      throws AutomationBDDServiceException
   {

      String xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_CSIWF_REQ_SUBTRANSPORT);
      String content = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TAG_CREATE_FIBER_SERVICE_WORK_ORDER_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      if (subTransportType != null && content != null)
      {
         return subTransportType.equals(content);
      }

      return false;
   }

   public boolean validateSubTransportTypeInFirstException(String origOrderId, String subTransportType)
      throws AutomationBDDServiceException
   {
      String xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_FIRSTFALLOUT_REQ_SUBTRANSPORT);
      String transportType = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TAG_OWA_INSTALLTION_EXCPETION_MESSAGE, EnvConstants.EIH_MESSAGE_TYPE_EXCEPTION);
      logger.info("transport type from eih is ->" + transportType);
      if (subTransportType != null && transportType != null)
      {
         return transportType.equals(subTransportType);
      }

      return false;
   }

   public boolean updateCsinsmForCeaseMoveOrder(String iccid, String imsi) throws AutomationBDDServiceException
   {
      return (toolsDao.updateCsinsmForSimIccid(iccid) && toolsDao.updateCsinmsImsiForSimIccid(imsi, iccid));
   }

   public Map<String, String> updateIccidAndImsiForCeaseMoveOrder(String ban) throws AutomationBDDServiceException
   {
      logger.info("Update Imsi And Iccid for Cease Move Order");
      Map<String, String> valueMap = getOlDao().getWllImeiIccidImsiFromWllSubscription(ban);
      logger.info("Fetched Imsi ,Iccid, Imei " + valueMap + " for Ban:" + ban);
      if (valueMap != null && !valueMap.isEmpty())
      {
         logger.info("Updated CSI_NMS Imsi ,Iccid " + valueMap + " for Ban:" + ban);
         updateCsinsmForCeaseMoveOrder(valueMap.get("iccid"), valueMap.get("imsi"));
      }
      return valueMap;
   }

   public void removeDetailsFromCsiscForTn(String tn) throws AutomationBDDServiceException
   {
      logger.info("Remove details from Switch Control for TN:" + tn);
      toolsDao.removeFromCsiscByTn(tn);
   }

   public boolean validateManageWLLProvisioningRequestAttr(String attribute, String origOrderId, String xpathKey)
      throws AutomationBDDServiceException
   {
      logger.info("Validating ManageWllProvisionRequest contains the required attribute");
      String xpath = null;
      xpath = propUtil.getProperty(xpathKey);
      String attributeFromRequest = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_MANAGEWLL_PROV_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("Attribute type value from eih is ->" + attributeFromRequest);

      if (attributeFromRequest != null)
      {
         return (attributeFromRequest.equals(attribute));
      }
      else
      {
         return false;
      }
   }

   public boolean validateProvisionWLLRequestAttr(String attribute, String origOrderId, String xpathKey)
      throws AutomationBDDServiceException
   {
      logger.info("Validating G2 ProvisionWll Request contains the required attribute");
      String xpath = null;
      xpath = propUtil.getProperty(xpathKey);
      String attributeFromRequest = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_PROV_WLL_G2_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("Attribute type value from eih is ->" + attributeFromRequest);

      if (attributeFromRequest != null)
      {
         return (attributeFromRequest.equals(attribute));
      }
      else
      {
         return false;
      }
   }

   public boolean validateAPNValuesInSwitchControlRequestForNgFw(List<String> list, String origOrderId)
      throws AutomationBDDServiceException
   {
      logger.info("Validating ManageWllProvisionRequest contains correct APN values for NGFW Order");
      String xpath = null;
      xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_DEFAULTAPN);
      String defaultApn = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_MANAGEWLL_PROV_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("default APN from eih is ->" + defaultApn);
      xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_APN1);
      String apn1 = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_MANAGEWLL_PROV_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("APN1 from eih is ->" + apn1);
      xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_APN2);
      String apn2 = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_MANAGEWLL_PROV_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("APN2 from eih is ->" + apn2);
      xpath = propUtil.getProperty(XpathConstants.XPATH_EIH_MANAGEWLLPROV_REQ_APN3);
      String apn3 = getOlDao().getXmlContentFromEihAll(origOrderId, xpath,
         EnvConstants.EIH_MESSAGE_TYPE_MANAGEWLL_PROV_REQ, EnvConstants.EIH_MESSAGE_TYPE_REQ);
      logger.info("APN3 from eih is ->" + apn3);

      List<String> expectedValues = Arrays.asList(defaultApn, apn1, apn2, apn3);
      return (list.containsAll(expectedValues));
   }

   public String fetchNextGenBanFromBatchFile()
   {

      ShellClient shellUtil = new ShellClient(propUtil.getUnixHostName(EnvConstants.SYS_OL), 22,
         new SaltedHashCredentials(propUtil.getUnixHostUser(EnvConstants.SYS_OL), propUtil.getUnixHostPrivateKey(
            EnvConstants.SYS_OL), propUtil.getUnixHostKeyPhrase(EnvConstants.SYS_OL)));

      List<String> result = shellUtil.execute(propUtil.getProperty(EnvConstants.WLL_ORDER_ID_FROM_BATCH_FILE_COMMAND));
      logger.info("Fetched Result :" + result);
      if (result != null && !result.isEmpty() && Utils.isNumeric(result.get(0)))
      {
         return result.get(0);
      }

      return null;
   }

   public boolean validateSubTransPortTypeFromSubscriptions(String origOrderId, String subtransportType)
      throws AutomationBDDServiceException
   {
      return (subtransportType != null) ? subtransportType.equals(getOlDao().getSubTransportTypeFromWllSubscription(
         origOrderId)) : false;
   }

   public boolean validateRejectReasonForProcessRequest(String origOrderId, String reason)
      throws AutomationBDDServiceException
   {
      return (reason != null) ? reason.equals(getOlDao().getRejectReasonForWllProcessReq(origOrderId)) : false;
   }

   public List<String> validatemakeModelByWllproductvalidationinfo(String nti, String subtransporttype)
      throws AutomationBDDServiceException
   {
      return getOlDao().validateMakeModel(nti, subtransporttype);


   }


   public Map<String, String> createWllRetrieveProvisioningStatusInformationRequestMap(String ban,
      String omsOrderNumber)
   {
      logger.info("Checking the creation!");
      return orderObjectMapper.createWllRetrieveProvisioningStatusInformationRequestMap(ban, omsOrderNumber);
   }


   public String sendWllRetrieveProvisioningStatusInformationRequest(Map firstRtRequestMap)
      throws AutomationBDDServiceException
   {
      logger.info("Send Wll RetrieveProvisioningStatusInformation Request :" + firstRtRequestMap);
      return firstRtAdapter.sendRequest(firstRtRequestMap, propUtil.getProperty(
         EnvConstants.TEMPLATE_FIRST_RT_REQUEST));
   } 
   
}
