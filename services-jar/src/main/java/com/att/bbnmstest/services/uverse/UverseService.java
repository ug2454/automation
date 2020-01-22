package com.att.bbnmstest.services.uverse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.dao.CimDAO;
import com.att.bbnmstest.services.dao.CmsDAO;
import com.att.bbnmstest.services.dispatcher.OMSPubRgDispatcher;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.helper.RequestType;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.OrderObjectMapper;
import com.att.bbnmstest.services.util.PropUtil;

@Component
@Qualifier("UverseService")
public class UverseService extends BBNMSService
{

   private final static Logger logger = Logger.getLogger(UverseService.class);
   @Autowired
   private PropUtil propUtil;

   @Autowired
   private CimDAO cimDao;

   @Autowired
   private CmsDAO cmsDao;

   @Autowired
   private OrderObjectMapper orderObjectMapper;

   @Autowired
   private OMSPubRgDispatcher omsPubRgAdapter;


   public String provisionedBan(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseBanFromOL(producttype, nti);
      return provisionedBan;
   }

   public String provisionedCircuitId(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseCircuitIdFromOL(producttype, nti);
      return provisionedBan;
   }

   public String provisionedBanGpon(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseBanFromOLGpon(producttype, nti);
      return provisionedBan;
   }

   public String provisionedCircuitIdGpon(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseCircuitIdFromOLGpon(producttype, nti);
      return provisionedBan;
   }

   public String provisionedBanFbs(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseBanFromOLFbs(producttype, nti);
      return provisionedBan;
   }

   public String provisionedCircuitIdFbs(String producttype, String nti) throws AutomationBDDServiceException
   {
      String provisionedBan = getOlDao().getUverseCircuitIdFromOLFbs(producttype, nti);
      return provisionedBan;
   }

   public boolean updateExtNodeCienaEmuxAuthstatus(String EMTCLLIName) throws AutomationBDDServiceException
   {
      return (cimDao.updateExtNodeCienaEmuxAuthstatus(EMTCLLIName) == 1);
   }

   public boolean updateFBSUpdateExtPort(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateFBSUpdateExtPort(serviceID) == 1);
   }

   public boolean updateFbsSegments(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateFbsSegments(serviceID) == 1);
   }

   public boolean updateExtServiceFbs(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateExtServiceFbs(serviceID) == 1);
   }

   public boolean updateFbsDimNumber(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateFbsDimNumber(serviceID) == 1);
   }

   public boolean updateFbsExtNodeEmuxRmonStatus(String EMTCLLIName) throws AutomationBDDServiceException
   {
      return (cimDao.updateFbsExtNodeEmuxRmonStatus(EMTCLLIName) == 1);
   }

   public boolean updateFbsExtPortAtGigaBiteth(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateFbsExtPortAtGigaBiteth(serviceID) == 1);
   }


   public boolean updateFbsExtPortRmonstatus(String serviceID) throws AutomationBDDServiceException
   {
      return (cimDao.updateFbsExtPortRmonstatus(serviceID) == 1);
   }


   public void sendRequest(String requestType, Map<String, String> orderDetailsAsMap)
      throws AutomationBDDServiceException
   {
      logger.info("Sending uverse oms request");
      RequestType.valueOf(requestType).sendOmsRequest(getOmsAdapter(), orderDetailsAsMap, propUtil.getProperty(
         EnvConstants.TEMPLATE_OMS_UVERSE_REQ));
   }

   public Map<String, String> createUverseOrder(String orderType, String orderSubType, String NTI, Map context)
      throws AutomationBDDServiceException
   {

      String orderActionRefNumber = EnvConstants.ORDERACTIONREFNUMBER;

      Map<String, String> orderNumbers = createUniqueOrdernumberAndOriginalOrderId(
         EnvConstants.GENERATE_ID_PREFIX_UVERSE);
      String orderNumber = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      String origOrdId = orderNumbers.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);
      
      //temporary piece of code added to check the ordernumber uniqueness
      logger.info("Original order id ->"+origOrdId);
      while(!getOlDao().inInEih(origOrdId)) {
    	  logger.info("Get a new order number");
    	  orderNumbers = createUniqueOrdernumberAndOriginalOrderId(
    		         EnvConstants.GENERATE_ID_PREFIX_UVERSE);
    	  origOrdId = orderNumbers.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);
      }
      logger.info("Final Original order id ->"+origOrdId);	
      //end of temp code
      
      List<String> banServiceId = getBanAndServiceId(origOrdId, orderType, NTI, context);

      if (logger.isDebugEnabled())
         logger.debug("createUverseOrder: banServiceId" + banServiceId);

      String ban = banServiceId.get(0);
      String serviceID = banServiceId.get(1);

      if (logger.isDebugEnabled())
         logger.debug("createUverseOrder: ban " + ban + " serviceID " + serviceID);

      return orderObjectMapper.createUverseOrderObjectMap(orderNumber, origOrdId, ban, serviceID, orderType,
         orderSubType, orderActionRefNumber, NTI);
   }

   private List<String> getBanAndServiceId(String origOrdId, String orderType, String NTI, Map context)
      throws AutomationBDDServiceException
   {
      String ban = null;
      String serviceID = null;

      if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_PR))
      {
         if ((StringUtils.equals(NTI, EnvConstants.NTI_FTTN)) || (StringUtils.equals(NTI, EnvConstants.NTI_IPRT))
            || (StringUtils.equals(NTI, EnvConstants.NTI_IPCO)) || (StringUtils.equals(NTI,
               EnvConstants.NTI_FTTP_GPON)))
         {
            ban = origOrdId;
            serviceID = EnvConstants.SERVICEID_L3 + ban + EnvConstants.SERVICEID_SB;
         }

         else if (orderType.equals(EnvConstants.ORDERTYPE_PR) && (NTI.equals("FTTB-C")))
         {
            ban = origOrdId;
            serviceID = "02/MCXF/" + ban + "//SW";
         }
      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH))
      {
         if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH) && (StringUtils.equals(NTI,
            EnvConstants.NTI_FTTN)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // ban = provisionedBan(EnvConstants.PRODUCTTYPE, NTI);
            // serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, NTI);
         }
         else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH) && (StringUtils.equals(NTI,
            EnvConstants.NTI_IPCO)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // ban = provisionedBan(EnvConstants.PRODUCTTYPE, NTI);
            // serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, NTI);
         }
         else if ((StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH)) && (StringUtils.equals(NTI,
            EnvConstants.NTI_FTTP_GPON)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // String FTTPGPONNTI="GPON";
            // ban = provisionedBanGpon(EnvConstants.PRODUCTTYPE, FTTPGPONNTI);
            // serviceID = provisionedCircuitIdGpon(EnvConstants.PRODUCTTYPE, FTTPGPONNTI);
         }
      }
      else if (StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE))
      {
         if ((StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE)) && (StringUtils.equals(NTI,
            EnvConstants.NTI_FTTN)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // ban = provisionedBan(EnvConstants.PRODUCTTYPE, NTI);
            // serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, NTI);
         }
         else if ((StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE)) && (StringUtils.equals(NTI,
            EnvConstants.NTI_IPCO)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // ban = provisionedBan(EnvConstants.PRODUCTTYPE, NTI);
            // serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, NTI);
         }
         else if ((StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CE)) && (StringUtils.equals(NTI,
            EnvConstants.NTI_FTTP_GPON)))
         {
            ban = (String) context.get(EnvConstants.TAG_BAN);
            serviceID = (String) context.get(EnvConstants.TAG_SERVICE_ID);
            // ban = provisionedBan(EnvConstants.PRODUCTTYPE, NTI);
            // serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, NTI);
         }
      }
      else if ((StringUtils.equals(orderType, EnvConstants.ORDERTYPE_CH)) && (NTI.equals("FTTB-C")))
      {
         String FTTBCNTI = "FTTBC";

         ban = provisionedBan(EnvConstants.PRODUCTTYPE, FTTBCNTI);
         serviceID = provisionedCircuitId(EnvConstants.PRODUCTTYPE, FTTBCNTI);

      }

      return Arrays.asList(ban, serviceID);
   }


   public Map<String, String> createFBSOrder(String orderType, String orderSubType, String NTI)
      throws AutomationBDDServiceException
   {


      String ban = null;
      String serviceID = null;
      Map<String, String> orderDetailsAsMap = new HashMap<String, String>();
      String orderActionRefNumber = EnvConstants.ORDERACTIONREFNUMBER;

      logger.info("Creating Uverse Order");
      Map<String, String> orderNumbers = createUniqueOrdernumberAndOriginalOrderId(
         EnvConstants.GENERATE_ID_PREFIX_UVERSE);
      String orderNumber = orderNumbers.get(EnvConstants.TAG_ORDER_NUMBER);
      String origOrdId = orderNumbers.get(EnvConstants.TAG_ORIG_ORDER_ACTION_ID);


      if ((orderType.equals("PR") && NTI.equals("FTTB-C")) || (orderType.equals("PR") && NTI.equals("FTTB-F")))
      {
         ban = origOrdId;
         serviceID = "02/MCXF/" + ban + "//SW";
      }
      else if (orderType.equals("CH") && (NTI.equals("FTTB-C")))
      {
         String FTTBC_NTI = "FTTBC";
         ban = provisionedBanFbs(EnvConstants.PRODUCTTYPE, FTTBC_NTI);
         serviceID = provisionedCircuitIdFbs(EnvConstants.PRODUCTTYPE, FTTBC_NTI);
      }
      else if (orderType.equals("CH") && (NTI.equals("FTTB-F")))
      {
         String FTTBC_NTI = "FTTBF";
         ban = provisionedBanFbs(EnvConstants.PRODUCTTYPE, FTTBC_NTI);
         serviceID = provisionedCircuitIdFbs(EnvConstants.PRODUCTTYPE, FTTBC_NTI);
      }

      orderDetailsAsMap = orderObjectMapper.createFBSOrderObjectMap(orderNumber, origOrdId, ban, serviceID, orderType,
         orderSubType, orderActionRefNumber, NTI);


      return orderDetailsAsMap;
   }

   public Map<String, String> createPubRgNotification(Map<String, String> orderDetailsAsMap)
   {
      Map<String, String> pubRgMap = orderObjectMapper.createPubRgNotificationMap(orderDetailsAsMap);
      return pubRgMap;
   }

   public Map<String, String> createInvalidPubRgNotification(Map<String, String> orderDetailsAsMap, String serviceID)
   {
      Map<String, String> invalidPubRgMap = orderObjectMapper.createInvalidPubRgNotificationMap(orderDetailsAsMap,
         serviceID);
      return invalidPubRgMap;
   }

   public void sendPubRgRequest(Map<String, String> pubRgMap) throws AutomationBDDServiceException
   {
      omsPubRgAdapter.sendRequest(pubRgMap, propUtil.getProperty(EnvConstants.TEMPLATE_UVERSE_PUBRG));

   }

   public Map<String, String> createPubRgNotificationChange(Map<String, String> orderDetailsAsMap)
   {
      return orderObjectMapper.createUversePubRgNotificationChangeMap(orderDetailsAsMap);
   }

   public String validateOmsTask(String orderNumber, String messageTag) throws AutomationBDDServiceException
   {
      return (getOlDao().getOmsTransactionStatusByOrdernumberAndMsgTag(orderNumber, messageTag));
   }

   public boolean updateCMS(String model, String manufacturer, String ban) throws AutomationBDDServiceException
   {
      return (cmsDao.updateCMSDb(model, manufacturer, ban) == 1);

   }

   public boolean updatefailCMS(String clli8, String vhoCode, String ban) throws AutomationBDDServiceException
   {
      return (getCmsDao().updateCMSDb(clli8, vhoCode, ban) == 1);
   }

   public void executeFbaseQueries(String emtclli, String serviceId) throws AutomationBDDServiceException
   {
      try
      {


         boolean status = updateExtNodeCienaEmuxAuthstatus(emtclli);
         if (status == true)
            logger.info("ext_node_ciena_emux Database is updated with authstatus");

         Thread.sleep(50000);

         boolean status1 = updateFBSUpdateExtPort(serviceId);
         if (status1 == true)
            logger.info("updates ext port");

         Thread.sleep(50000);

         boolean status2 = updateFbsSegments(serviceId);
         if (status2 == true)
            logger.info("updates segments");

         Thread.sleep(50000);

         boolean status3 = updateExtServiceFbs(serviceId);
         if (status3 == true)
            logger.info("updates ext service fbs");

         Thread.sleep(50000);

         boolean status4 = updateFbsDimNumber(serviceId);
         if (status4 == true)
            logger.info("updates disnumber");

         Thread.sleep(50000);

         boolean status5 = updateFbsExtNodeEmuxRmonStatus(serviceId);
         if (status5 == true)
            logger.info("updates ext node emux rmonstatus");

         Thread.sleep(50000);

         boolean status7 = updateFbsExtPortRmonstatus(serviceId);
         if (status7 == true)
            logger.info("updates rmonstatus");

      }
      catch (InterruptedException e)
      {
         throw new AutomationBDDServiceException("Error while performing thread operation", e)
         {
            @Override
            public String getDescription()
            {
               return "Thread sleep failed";
            }
         };
      }

   }


}
