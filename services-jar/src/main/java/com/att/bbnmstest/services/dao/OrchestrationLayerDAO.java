package com.att.bbnmstest.services.dao;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.SqlClient;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.QueryConstants;
import com.att.bbnmstest.services.util.Utils;

@Component
public class OrchestrationLayerDAO
{
   private final static Logger logger = Logger.getLogger(OrchestrationLayerDAO.class);
   private SqlClient sqlClient;
   @Autowired 
   private PropUtil propUtil;


   public OrchestrationLayerDAO(@Value("${dbUrl." + EnvConstants.SYS_OL + "}") String dbUrl, @Value("${dbUser."
      + EnvConstants.SYS_OL + "}") String dbUser, @Value("${dbPass." + EnvConstants.SYS_OL + "}") String dbPass)
   {
      sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));

   }

   public String getUverseBanFromOLFbs(String producttype, String nti) throws DAOServiceExeption
   {

      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_fbs), new String[]
            { "?1", "?2" }, new String[]
            { producttype, nti });

         logger.info("Query to get Uverse BAN from OL DB :" + query);

         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }


   public String getUverseBanFromOL(String producttype, String nti) throws DAOServiceExeption
   {

      try
      {
    	  if (StringUtils.equals(nti, EnvConstants.NTI_FTTP_GPON))
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_gpon),
  		            new String[]
  		            { "?1", "?2" }, new String[]
  		            { producttype, nti });
  		  logger.info("Query to get Uverse BAN from OL DB :" + query);
  		  CachedRowSet resultset = sqlClient.runSelect(query);
  	         if (resultset.next())
  	         {
  	            return resultset.getString(1);
  	         }
  	         return null;
    	  }
    	  
    	  else if (StringUtils.equals(nti, EnvConstants.NTI_IPCO))
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_ipco),
  		            new String[]
  		            { "?1", "?2" }, new String[]
  		            { producttype, nti });
  		  logger.info("Query to get Uverse BAN from OL DB :" + query);
  		  CachedRowSet resultset = sqlClient.runSelect(query);
  	         if (resultset.next())
  	         {
  	            return resultset.getString(1);
  	         }
  	         return null;
    	  }
    	  
    	  else
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol),
    		            new String[]
    		            { "?1", "?2" }, new String[]
    		            { producttype, nti });
    		  logger.info("Query to get Uverse BAN from OL DB :" + query);
    		  CachedRowSet resultset = sqlClient.runSelect(query);
    	         if (resultset.next())
    	         {
    	            return resultset.getString(1);
    	         }
    	         return null;
    	  }
         

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getUverseBanFromOLGpon(String producttype, String nti) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_GPON),
            new String[]
            { "?1", "?2" }, new String[]
            { producttype, nti });

         logger.info("Query to get Uverse BAN from OL DB :" + query);

         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getDtvBanFromOL() throws DAOServiceExeption
   {
      try
      {
         String query = propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_BAN);
         logger.info("Query to get DTV BAN from OL DB :" + query);

         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getMaxFlowableIdFromAbstractFlow(String orderPrefix) throws DAOServiceExeption
   {
      String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_AbstractFlow_Max_FlowableId),
         "?1", orderPrefix);
      logger.info("Query in getMaxFlowableIdFromAbstractFlow():" + query);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getMaxServiceOrderFromHistoryMessage(String orderPrefix) throws DAOServiceExeption
   {
      String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_HistroyMessage_Max_ServiceOrder),
         "?1", orderPrefix);
      logger.info("Query in getMaxServiceOrderFromHistoryMessage():" + query);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getMaxOrderNumberFromEihAll(String orderPrefix) throws DAOServiceExeption
   {
      String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_EihAll_Max_OrderNumber), "?1",
         orderPrefix);
      logger.info("Query in getMaxOrderNumberFromEihAll():" + query);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getServiceOrderStatus(String ordernumber) throws DAOServiceExeption
   {
      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_Service_Order_Status),
         new String[]
         { "?1" }, new String[]
         { ordernumber });
      logger.info("Query in getServiceOrderStatus():" + query);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }

   }

   public Map<String, String> getOrderTaskStatusByOrderNumerAndTask(String ordernumber, String taskDescription)
      throws DAOServiceExeption
   {
      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_Order_Status),
         new String[]
         { "?1", "?2" }, new String[]
         { ordernumber, taskDescription });
      logger.info("Query in getOrderTaskStatusByOrderNumerAndTask():" + query);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         Map<String, String> taskValMap = new HashMap<>();

         while (resultset.next())
         {
            taskValMap.put(resultset.getString(2), resultset.getString(3));
         }
         return taskValMap;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getTransactionStatusByOrdernumberAndMsgTag(String ordernumber, String messageTag)
      throws DAOServiceExeption
   {
      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_Transport_Status),
         new String[]
         { "?1", "?2" }, new String[]
         { ordernumber, messageTag });
      logger.info("Query in getTransactionStatusByOrdernumberAndMsgTag():" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            throw new DAOServiceExeption("No  Records Found in OL");
         }

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }


   public String getCsiWfReqValidation(String xpath, String ordernumber) throws DAOServiceExeption
   {

      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_Csiwf_Prna),
         new String[]
         { "?1", "?2" }, new String[]
         { xpath, ordernumber });
      logger.info("Query to verify CSI WF request:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }

         return null;

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }


   public String getL1Acknowlegment(String ordernumber, String orderversion) throws DAOServiceExeption
   {
      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_L1_Ack), new String[]
         { "?1", "?2" }, new String[]
         { ordernumber, orderversion });

      logger.info("Query in getL1Acknowlegment():" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            return resultset.getString(1);
         }

         return null;

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getMPCFlowableIDByBan(String ban) throws DAOServiceExeption
   {
      String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_get_mpc_flowbale_id_by_ban),
         new String[]
         { "?1" }, new String[]
         { ban });

      logger.info("Query in Region :" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            return resultset.getString(1);
         }

         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getLineItemIDActivationStatusByOrderId(String ordernumber, String lineItemId) throws DAOServiceExeption
   {

      String query = StringUtils.replaceEach(propUtil.getProperty(
         QueryConstants.Query_Key_DTV_LineItem_Activation_Status_by_order), new String[]
         { "?1", "?2" }, new String[]
         { ordernumber, lineItemId });
      logger.info("Query in getLineItemIDAndIdentifierByOrderId :" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            return resultset.getString(1);
         }

         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   
   public String getLineItemIDActivationStatusIsValidByOrderId(String ordernumber, String lineItemId) throws DAOServiceExeption
   {

      String query = StringUtils.replaceEach(propUtil.getProperty(
         QueryConstants.Query_Key_DTV_LineItem_Activation_Status_IsValid_by_order), new String[]
         { "?1", "?2" }, new String[]
         { ordernumber, lineItemId });
      logger.info("Query in getLineItemIDActivationStatusIsValidByOrderId :" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            return resultset.getString(1);
         }

         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public Map<String, String> getLineItemIDAndIdentifierByOrderId(String ordernumber) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(
            QueryConstants.Query_Key_DTV_LineItem_Identifier_by_order), "?", ordernumber);
         logger.info("Query in getLineItemIDAndIdentifierByOrderId :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);

         Map<String, String> results = new java.util.HashMap<String, String>();
         while (resultset.next())
         {
            results.put(resultset.getString(1), resultset.getString(2));
         }

         return results;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public Map<String, List<String>> getLineItemIDAndIdentifierForNoWvbByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline__Apid_No_Wvb_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierForNoWvbByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
   
   public Map<String, List<String>> getLineItemIDAndIdentifierProductlineByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline__Apid_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierProductlineByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
   
   public Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline_Apid_ActionIndicator_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierActionIndicatorByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
   
   public Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorForDeactivationByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline_Apid_ActionIndicator_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierActionIndicatorByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
     
   public Map<String, List<String>> getLineItemIDAndIdentifierActionIndicatorAsMByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline_Apid_ActionIndicator_M_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierActionIndicatorAsMByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
	 
	 
	 public Map<String, List<String>> getLineItemIDAndIdentifierProductTypeByOrderId(String ordernumber) throws DAOServiceExeption
	 {
	 try
	 {
	 String query = StringUtils.replace(propUtil.getProperty(
	 QueryConstants.Query_Key_DTV_LineItem_Identifier_Productline__Apid_by_order), "?", ordernumber);
	 logger.info("Query in getLineItemIDAndIdentifierProductlineByOrderId :" + query);
	 CachedRowSet resultset = sqlClient.runSelect(query);

	 Map<String, List<String>> results = new java.util.LinkedHashMap<String, List<String>>();
	 while (resultset.next())
	 {
	 results.put(resultset.getString(1), Arrays.asList(resultset.getString(2),resultset.getString(3),resultset.getString(4)));
	 }

	 return results;
	 }
	 catch (ClientException e)
	 {
	 throw new DAOServiceExeption("Exception occured while making db call", e);
	 }
	 catch (SQLException e)
	 {
	 throw new DAOServiceExeption("Exception occured while reading resultset", e);
	 }
	 catch (Exception e)
	 {
	 throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	 }
	 }
   
   private String getWllBanAndServiceFromWllSubscriptionsQuery(String subTransportType)
   {
      if ("NextGen".equals(subTransportType))
      {
         return StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_GetWllBanFromOl), new String[]
            { "?1" }, new String[]
            { subTransportType });
      }
      else
      {
         return propUtil.getProperty(QueryConstants.Query_Key_GetWllBanFromOlForSubtransNull);
      }
   }


   public List<String> getWllBanAndServiceFromWllSubscriptions(String subTransportType) throws DAOServiceExeption
   {

      String query = getWllBanAndServiceFromWllSubscriptionsQuery(subTransportType);

      List<String> result = null;


      logger.info("Query in getBanFromOl " + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            result = new ArrayList<>();
            result.add(resultset.getString(1));
            result.add(resultset.getString(2));
            return result;
         }
         else
         {
            return result;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public List<String> getUsedBanForXgsPon() throws DAOServiceExeption
   {
      String query = propUtil.getProperty(QueryConstants.Query_Ban_Cleanup_Automation);

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         java.util.List<String> results = new java.util.ArrayList<String>();

         while (resultset.next())
         {

            results.add(resultset.getString(1));


         }

         return results;

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getWllSubscriberStatusFromOl(String ban) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_GetWllSubscriberStatus),
         new String[]
         { "?1" }, new String[]
         { ban });

      logger.info("Query in getWllSubscriberStatusFromOl:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getServiceTypefromServiceprocessinfo(String subscriptionBAN) throws DAOServiceExeption {
      String query = null;
      
      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Service_Status_From_Subscription_Search),
         new String[]
         { "?1" }, new String[]
         { subscriptionBAN });

      logger.info("Query in geServiceStatusFromSubscriptionSearch:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
      
   }

   public List<String> getSuspendedBanAndServiceFromWllSubscription() throws DAOServiceExeption
   {
      String query = null;
      List<String> result = null;

      query = propUtil.getProperty(QueryConstants.Query_Key_GetWllSuspendBanFromOl);

      logger.info("Query in getBanFromOl " + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            result = new ArrayList<>();
            result.add(resultset.getString(1));
            result.add(resultset.getString(2));

         }

         return result;

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   private String getQueryForXmlContentFromEihAll(String orderNumber, String xpath, String messageTag, String msgType)
   {

      if ("".equals(msgType))
      {
         return StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_get_eih_with_blank_msgtag),
            new String[]
            { "?1", "?2", "?3" }, new String[]
            { orderNumber, xpath, messageTag });
      }
      else
      {

         return StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_get_eih_element), new String[]
            { "?1", "?2", "?3", "?4" }, new String[]
            { orderNumber, xpath, messageTag, msgType });
      }
   }

   public String getXmlContentFromEihAll(String orderNumber, String xpath, String messageTag, String msgType)
      throws DAOServiceExeption
   {

      try
      {
         String result = null;
         String query = getQueryForXmlContentFromEihAll(orderNumber, xpath, messageTag, msgType);


         logger.info("Query to get attribute from eih " + query);

         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            result = resultset.getString(1);
         }

         return result;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }


   public int updateEligibleTimeForTask(String origOrderId, String taskId) throws DAOServiceExeption
   {
      try
      {
         String updateQuery = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Update_Eligible_Time_For_Task), new String[]
            { "?1", "?2" }, new String[]
            { origOrderId, taskId });
         logger.info("Update Eligible Time for Task Query :" + updateQuery);
         return sqlClient.runUpdateOrDelete(updateQuery);

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }

   }

   public String getUverseCircuitIdFromOL(String producttype, String nti) throws DAOServiceExeption
   {

      try
      {
    	  if (StringUtils.equals(nti, EnvConstants.NTI_FTTP_GPON))
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_gpon),
  		            new String[]
  		            { "?1", "?2" }, new String[]
  		            { producttype, nti });
  		  logger.info("Query to get Uverse BAN from OL DB :" + query);
  		  CachedRowSet resultset = sqlClient.runSelect(query);
  	         if (resultset.next())
  	         {
  	            return resultset.getString(2);
  	         }
  	         return null;
    	  }
    	  
    	  else if (StringUtils.equals(nti, EnvConstants.NTI_IPCO))
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_ipco),
  		            new String[]
  		            { "?1", "?2" }, new String[]
  		            { producttype, nti });
  		  logger.info("Query to get Uverse BAN from OL DB :" + query);
  		  CachedRowSet resultset = sqlClient.runSelect(query);
  	         if (resultset.next())
  	         {
  	            return resultset.getString(2);
  	         }
  	         return null;
    	  }
    	  
    	  else
    	  {
    		  String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol),
    		            new String[]
    		            { "?1", "?2" }, new String[]
    		            { producttype, nti });
    		  logger.info("Query to get Uverse BAN from OL DB :" + query);
    		  CachedRowSet resultset = sqlClient.runSelect(query);
    	         if (resultset.next())
    	         {
    	            return resultset.getString(2);
    	         }
    	         return null;
    	  }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getUverseCircuitIdFromOLGpon(String producttype, String nti) throws DAOServiceExeption
   {

      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_GPON),
            new String[]
            { "?1", "?2" }, new String[]
            { producttype, nti });

         logger.info("Query to get CircuitId BAN from OL DB :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(2);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public Map<String, String> getWllImeiIccidImsiFromWllSubscription(String ban) throws DAOServiceExeption
   {
      String query = null;
      Map<String, String> result = null;

      query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_Wll_Get_Imei_Iccid_Imsi_For_Ban), "?1",
         ban);

      logger.info("Query in getWllImeiIccidImsiFromWllSubscription " + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);

         if (resultset.next())
         {
            result = new HashMap<>();
            result.put("tn", resultset.getString(1));
            result.put("imei", resultset.getString(2));
            result.put("iccid", resultset.getString(3));
            result.put("imsi", resultset.getString(4));
         }

         return result;

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getOmsTransactionStatusByOrdernumberAndMsgTag(String ordernumber, String messageTag)
      throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_Transport_Status),
            new String[]
            { "?1", "?2" }, new String[]
            { ordernumber, messageTag });
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
   }

   public boolean updateCMSDb(String model, String manufacturer, String ban) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_UpdateCMS),
            new String[]
            { "?1", "?2", "?3" }, new String[]
            { model, manufacturer, ban });

         if (sqlClient.runUpdateOrDelete(query) == 1)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }

   }


   public String getSubTransportTypeFromWllSubscription(String origOrderId) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Subtransport_Type_From_Subscription), new String[]
            { "?1" }, new String[]
            { origOrderId });
         logger.info("Wll SubTrasport Type Query :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
   }

   public boolean validateUniqueIccid(String iccid) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Iccid_From_Owa_By_Iccid), new String[]
            { "?1" }, new String[]
            { iccid });
         logger.info("Get Iccid query :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return false;
         }
         return true;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
   }

   public boolean validateUniqueImeiId(String imei) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Imei_From_Owa_By_Imei), new String[]
            { "?1" }, new String[]
            { imei });
         logger.info("Get Imei Id :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return false;
         }
         return true;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
   }


   public boolean validateUniqueImsi(String imsi) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Imsi_From_Owa_By_Imsi), new String[]
            { "?1" }, new String[]
            { imsi });
         logger.info("Get Imsi Id :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return false;
         }
         return true;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }

   public String getWorkflowId(String bbnmsOrder) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Getwfid_Ol), new String[]
            { "?1" }, new String[]
            { bbnmsOrder });
         logger.info("Query after construction :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         String value = null;
         if (resultset.next())
         {
            value = resultset.getString(1);
         }
         if (resultset.wasNull())
         {
            value = ""; 
         }
         return value;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }

   public List<String> getMilestoneInfoFromDb(String bbnmsOrder) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_GetMilestoneData_Ol),
            new String[]
            { "?1" }, new String[]
            { bbnmsOrder });
         logger.info("Get Workflow Id :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         List<String> value = new ArrayList<>();
         if (resultset.next())
         {
            value.add(resultset.getString(1));
            value.add(resultset.getString(2));
         }
         if (resultset.wasNull())
         {
            value.add("no data");
         }
         return value;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }


   public String getRejectReasonForWllProcessReq(String origOrderId) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Wll_Process_Req_Reject_Reason), new String[]
            { "?1" }, new String[]
            { origOrderId });
         logger.info("Get Reject Reason Id :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         return null;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }

   public boolean updateOlSettings(String activity, String value) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Update_Ol_Settings),
            new String[]
            { "?1", "?2" }, new String[]
            { value, activity });
         logger.info("Update Settings Query :" + query);
         if (sqlClient.runUpdateOrDelete(query) == 1)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Exception occured while updating Ol Settings", e);
      }

   }

   public boolean verifyOlDb() throws DAOServiceExeption
   {

      sqlClient.ping();

      String query = propUtil.getProperty(QueryConstants.Query_Wll_Eih);
      try
      {

         if (query != null)

         {
            return true;
         }
         return false;
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Exception occured while connecting to OL Db", e);
      }

   }

   private String getQueryForValidateMakeModel(String nti, String subtransporttype)
   {
      String query = null;
      if (subtransporttype == null)
      {
         query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Validate_Make_Model_From_Wllproductvalidationinfo), new String[]
            { "?1" }, new String[]
            { nti });

         logger.info("Query to verify Make Model when Subtransporttype is Null :" + query);

      }
      else if ("NextGen".equals(subtransporttype))
      {
         query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Validate_Make_Model_From_Wllproductvalidationinfo_By_Subtransporttype), new String[]
            { "?1", "?2" }, new String[]
            { nti, subtransporttype });

         logger.info("Query to verify Make Model when Subtransporttype is NextGen :" + query);

      }

      return query;
   }

   public List<String> validateMakeModel(String nti, String subtransporttype) throws DAOServiceExeption
   {

      String query = getQueryForValidateMakeModel(nti, subtransporttype);
      List<String> result = null;

      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            result = new ArrayList<>();
            result.add(resultset.getString(1));
            result.add(resultset.getString(2));
            return result;

         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }

   }

   public String getUverseCircuitIdFromOLFbs(String producttype, String nti) throws DAOServiceExeption
   {

      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Get_Uverse_Ban_From_ol_fbs), new String[]
            { "?1", "?2" }, new String[]
            { producttype, nti });

         logger.info("Query to get CircuitId BAN from OL DB :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(2);
         }
         return null;
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }


   public String getProductNameFromOlByTdarWoliForMobileDvr(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_Woli_Mobile_DVR),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getProductNameFromOlByTdarWoliForMobileDvr:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   
   public String getValidateDelayedRemoveValue(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_DelayedRemove_Value),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getValidateDelayedRemoveColumn:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getValidateDelayedRemoveColumn(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_DelayedRemove_Column),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getValidateDelayedRemoveColumn:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   
   public String getValidateLargeCustomerColumn(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_LargeCustomer_Column),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getValidateLargeCustomerColumn:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getProductNameFromOlByTdarWoliForInternalWvb(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_Woli_InternalWvb),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getProductNameFromOlByTdarWoliForInternalWvb:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getProductNameFromOlByTdarWoli(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Fetch_DTV_Woli_Orbotal1),
         new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getProductNameFromOlByTdarWoli:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getModelForWvbFromOlByEih_All(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_MODEL_WVB), new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getModelForWvbFromOlByEih_All:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }

   public String getManufacturerForWvbFromOlByEih_All(String origOrdId) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_MANUFACTURER_WVB), new String[]
         { "?1" }, new String[]
         { origOrdId });

      logger.info("Query in getManufacturerForWvbFromOlByEih_All:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getMduConnectedProperty(String ban) throws DAOServiceExeption
   {
      String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Mdu_Connected_Property), new String[]
         { "?1" }, new String[]
         { ban });

      logger.info("Query in TDARTECHINFO:" + query);
      try
      {
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            return resultset.getString(1);
         }
         else
         {
            return null;
         }
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
   }
   
   public String getSubsBan(String orderType) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_GETBAN_OL), new String[]
            { "?1" }, new String[]
            { orderType });
         logger.info("Query after construction :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         String value = null;
         if (resultset.next())
         {
            value = resultset.getString(1);
         }
         if (resultset.wasNull())
         {
            value = ""; 
         }
         return value;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }
   
   public boolean getMigrationStepStatus(String moveId, String stepName,String expectedStatusVal,String maxTimeout) throws DAOServiceExeption, InterruptedException
   {

      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_VALIDATE_MIGRATION_STEPSTATUS_OL), new String[]
            { "?1", "?2" }, new String[]
            { moveId, stepName });
         logger.info("Query after construction :" + query);
         CachedRowSet resultset;
         String expectedStatus = expectedStatusVal;
         String failCriterion = "FAILED";
         int timeForloop = Integer.parseInt(maxTimeout);
         int k = 0;
         // ***''wait and check status until timeout
         boolean blnFlag = true;
         String strTempVal = "";
         while (blnFlag)
         {
            if (!query.isEmpty())
            {
               resultset = sqlClient.runSelect(query);

               // check if atleast 1 record exist
               if (resultset.next())
                  strTempVal = resultset.getString(1);
               else {
                  logger.error("No Record Exist's for executed query = " + query);
                  break;
               }
               
               // if DB result matches OR times-out, set EXITLOOP flag as false

               if ((strTempVal.equalsIgnoreCase(expectedStatus)) || (strTempVal.equalsIgnoreCase(failCriterion))
                  || (timeForloop < 1))
               {
                  blnFlag = false;
                  logger.info("#Verify Query Status :  " + "Timeout:" + timeForloop + "  #EXPECTED:" + expectedStatus
                     + " #ACTUAL:" + strTempVal);
               }
               else
               {

                  timeForloop = timeForloop - 1;
                  Thread.sleep(2000);
               }
            }
            else
            {
               blnFlag = false;
               logger.error("Query is blank, please check");
            }

            logger.info("#Verify Query Status :   " + "Timeout:" + timeForloop + "  #EXPECTED:" + expectedStatus
               + " #ACTUAL:" + strTempVal + " exit when: " + failCriterion);

         }

         // *****'verify and log validation step

         if (strTempVal.equalsIgnoreCase(expectedStatus))
         {

            logger.info("#VERIFY  Query Status :  " + "#STATUS: PASSED " + "  #EXPECTED:" + expectedStatus + " #ACTUAL:"
               + strTempVal);
            return true;
         }
         else
         {

            logger.error("#VERIFY  Query Status :  " + "#STATUS: FAILED " + "  #EXPECTED:" + expectedStatus
               + " #ACTUAL:" + strTempVal);
            return false;
         }
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   } 
   
   public String getMigrationFailMessage(String moveId) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_MIGRATION_FAILMESSAGE_OL), new String[]
            { "?1" }, new String[]
            { moveId });
         logger.info("Query after construction :" + query);
         CachedRowSet resultset = sqlClient.runSelect(query);
         String value = null;
         if (resultset.next())
         {
            value = resultset.getString(1);
         }
         if (resultset.wasNull())
         {
            value = ""; 
         }
         return value;
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset:", e);
      }
   }
public List<String> getMultipleDSWM30AndPowerInsertor(String origOrdId) throws DAOServiceExeption
   {
     
 String query = null;

      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_Multiple_DSWM30), new String[]
         { "?1" }, new String[]
         { origOrdId });
	   
   //   List<String> result = null;

     // logger.info("Query in getMultipleDSWM30AndPowerInsertor:" + query);
//      try
//      {
//         CachedRowSet resultset = sqlClient.runSelect(query);
//         if (resultset.next())
//         {
//        	 result =new ArrayList<>();
//
//        	 result.add(resultset.getString(1));
//                       result.add(resultset.getString(2));
//              return result;
//         }
//         
//         {
//            return null;
//         }
//      }
      try
      {
      CachedRowSet resultset = sqlClient.runSelect(query);
      List<String> list = new ArrayList<>();
      while(resultset.next())
      {

      list.add((resultset.getString(1)));
                }
      //System.out.println("Tuner Count is 32.So there are two power insertor and  multi switch");
      return list;
      }
      
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception occured while making db call", e);
      }
      catch (SQLException e)
      {
         throw new DAOServiceExeption("Exception occured while reading resultset", e);
      }
      catch (Exception e)
      {
         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
      }
      
   }

public List<String> getIssacErrorMessageForClients(String origOrdId) throws DAOServiceExeption
{
  
String query = null;

   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_Issac_ErrorMessage), new String[]
      { "?1" }, new String[]
      { origOrdId });
   logger.info("Query after construction :" + query);
	   

   try
   {
   CachedRowSet resultset = sqlClient.runSelect(query);
   List<String> list = new ArrayList<>();
   while(resultset.next())
   {

   list.add((resultset.getString(1)));
   list.add((resultset.getString(2)));
             }
   
   return list;
   }
   
   catch (ClientException e)
   {
      throw new DAOServiceExeption("Exception occured while making db call", e);
   }
   catch (SQLException e)
   {
      throw new DAOServiceExeption("Exception occured while reading resultset", e);
   }
   catch (Exception e)
   {
      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
   }
   
}

public String getRecIdForClientEih_All(String origOrdId) throws DAOServiceExeption
{
   String query = null;

   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_Rec_ID), new String[]
      { "?1" }, new String[]
      { origOrdId });

   logger.info("Query in getRecIdForClientEih_All:" + query);
   try
   {
      CachedRowSet resultset = sqlClient.runSelect(query);
      if (resultset.next())
      {
         return resultset.getString(1);
      }
      else
      {
         return null;
      }
   }
   catch (ClientException e)
   {
      throw new DAOServiceExeption("Exception occured while making db call", e);
   }
   catch (SQLException e)
   {
      throw new DAOServiceExeption("Exception occured while reading resultset", e);
   }
   catch (Exception e)
   {
      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
   }
}

public String getRecIdForClientInCHorderEih_All(String origOrdId) throws DAOServiceExeption
{
   String query = null;

   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_Rec_ID_CH_order), new String[]
      { "?1" }, new String[]
      { origOrdId });

   logger.info("Query in getRecIdForClientEih_All:" + query);
   try
   {
      CachedRowSet resultset = sqlClient.runSelect(query);
      if (resultset.next())
      {
         return resultset.getString(1);
      }
      else
      {
         return null;
      }
   }
   catch (ClientException e)
   {
      throw new DAOServiceExeption("Exception occured while making db call", e);
   }
   catch (SQLException e)
   {
      throw new DAOServiceExeption("Exception occured while reading resultset", e);
   }
   catch (Exception e)
   {
      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
   }
}

public String getValueOfGenieReceiver(String origOrdId) throws DAOServiceExeption {
	   String query = null;

	   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_Genie_Flag), new String[]
	      { "?1" }, new String[]
	      { origOrdId });

	   logger.info("Query in getGenieFlag:" + query);
	   try
	   {
	      CachedRowSet resultset = sqlClient.runSelect(query);
	      if (resultset.next())
	      {
	         return resultset.getString(1);
	      }
	      else
	      {
	         return null;
	      }
	   }
	   catch (ClientException e)
	   {
	      throw new DAOServiceExeption("Exception occured while making db call", e);
	   }
	   catch (SQLException e)
	   {
	      throw new DAOServiceExeption("Exception occured while reading resultset", e);
	   }
	   catch (Exception e)
	   {
	      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	   }
	}

public List<String> getswapActionForGenie(String origOrdId) throws DAOServiceExeption {
	   String query = null;

	   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_SwapAction_for_D_receiver), new String[]
	      { "?1" }, new String[]
	      { origOrdId });

	   logger.info("Query in getswapaction:" + query);
	   try
	      {
	      CachedRowSet resultset = sqlClient.runSelect(query);
	      List<String> list = new ArrayList<>();
	      while(resultset.next())
	      {

	      list.add((resultset.getString(2)));
	      list.add((resultset.getString(10)));
	      list.add((resultset.getString(17)));
	      list.add((resultset.getString(21)));
	                }
	      //System.out.println("Tuner Count is 32.So there are two power insertor and  multi switch");
	      return list;
	      }
	   catch (ClientException e)
	   {
	      throw new DAOServiceExeption("Exception occured while making db call", e);
	   }
	   catch (SQLException e)
	   {
	      throw new DAOServiceExeption("Exception occured while reading resultset", e);
	   }
	   catch (Exception e)
	   {
	      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	   }
	}

public String gethardRestoreforSuspendOrders(String origOrdId)  throws DAOServiceExeption {
	{
		   String query = null;

		   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_HardRestore_SU_orders), new String[]
		      { "?1" }, new String[]
		      { origOrdId });

		   logger.info("Query in getHardRestoreforSUOrders:" + query);
		   try
		   {
		      CachedRowSet resultset = sqlClient.runSelect(query);
		      if (resultset.next())
		      {
		         return resultset.getString(1);
		      }
		      else
		      {
		         return null;
		      }
		   }
		   catch (ClientException e)
		   {
		      throw new DAOServiceExeption("Exception occured while making db call", e);
		   }
		   catch (SQLException e)
		   {
		      throw new DAOServiceExeption("Exception occured while reading resultset", e);
		   }
		   catch (Exception e)
		   {
		      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
		   }
		}
}
	public String gethardSuspendforSuspendOrders(String origOrdId)  throws DAOServiceExeption {
		{
			   String query = null;

			   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_DTV_HardSuspend_SU_orders), new String[]
			      { "?1" }, new String[]
			      { origOrdId });

			   logger.info("Query in getHardSuspendforSUOrders:" + query);
			   try
			   {
			      CachedRowSet resultset = sqlClient.runSelect(query);
			      if (resultset.next())
			      {
			         return resultset.getString(1);
			      }
			      else
			      {
			         return null;
			      }
			   }
			   catch (ClientException e)
			   {
			      throw new DAOServiceExeption("Exception occured while making db call", e);
			   }
			   catch (SQLException e)
			   {
			      throw new DAOServiceExeption("Exception occured while reading resultset", e);
			   }
			   catch (Exception e)
			   {
			      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
			   }
			}


}

	public List<String> getrollbackNoOpforSUandRSorders(String origOrdId) throws DAOServiceExeption {
	   String query = null;

	   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_rollback_NoOp_SU_RS_orders), new String[]
	      { "?1" }, new String[]
	      { origOrdId });

	   logger.info("Query in getrollbackNoOpforSUandRSorders:" + query);
	   try
	      {
	      CachedRowSet resultset = sqlClient.runSelect(query);
	      List<String> list = new ArrayList<>();
	      while(resultset.next())
	      {

	      list.add((resultset.getString(1)));
	      
	                }
	      //System.out.println("Tuner Count is 32.So there are two power insertor and  multi switch");
	      return list;
	      }
	   catch (ClientException e)
	   {
	      throw new DAOServiceExeption("Exception occured while making db call", e);
	   }
	   catch (SQLException e)
	   {
	      throw new DAOServiceExeption("Exception occured while reading resultset", e);
	   }
	   catch (Exception e)
	   {
	      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	   }
	}
	public String getStatusDescription(String ban) throws DAOServiceExeption {
		 String query = StringUtils.replaceEach(propUtil.getProperty(
		         QueryConstants.Query_Key_Get_Status_Description), new String[]
		         { "?1" }, new String[]
		         { ban });
		      logger.info("Query in getStatusDescription :" + query);
		      try
		      {
		         CachedRowSet resultset = sqlClient.runSelect(query);

		         if (resultset.next())
		         {
		            return resultset.getString(1);
		         }

		         return null;
		      }
		      catch (ClientException e)
		      {
		         throw new DAOServiceExeption("Exception occured while making db call", e);
		      }
		      catch (SQLException e)
		      {
		         throw new DAOServiceExeption("Exception occured while reading resultset", e);
		      }
		      catch (Exception e)
		      {
		         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
		      }
		
	}
	public List<String> getreassignWoliForAhl(String origOrdId) throws DAOServiceExeption {
		   String query = null;

		   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_WOLI_Details), new String[]
		      { "?1" }, new String[]
		      { origOrdId });

		   logger.info("Query in getreassignWoliForAhl:" + query);
		   try
		      {
		      CachedRowSet resultset = sqlClient.runSelect(query);
		      List<String> list = new ArrayList<>();
		      while(resultset.next())
		      {

		      list.add((resultset.getString(1)));
		    
		                }
		      		      return list;
		      }
		   catch (ClientException e)
		   {
		      throw new DAOServiceExeption("Exception occured while making db call", e);
		   }
		   catch (SQLException e)
		   {
		      throw new DAOServiceExeption("Exception occured while reading resultset", e);
		   }
		   catch (Exception e)
		   {
		      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
		   }
		}

	
	public List<String> getMVSRrequestforUDAS(String origOrdId) throws DAOServiceExeption {
		   String query = null;

		   query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_MVSR_request), new String[]
		      { "?1" }, new String[]
		      { origOrdId });

		   logger.info("Query in getMVSRrequestforUDAS:" + query);
		   try
		      {
		      CachedRowSet resultset = sqlClient.runSelect(query);
		      List<String> list = new ArrayList<>();
		      while(resultset.next())
		      {

		      list.add((resultset.getString(1)));
		      
		                }
		      		      return list;
		      }
		   catch (ClientException e)
		   {
		      throw new DAOServiceExeption("Exception occured while making db call", e);
		   }
		   catch (SQLException e)
		   {
		      throw new DAOServiceExeption("Exception occured while reading resultset", e);
		   }
		   catch (Exception e)
		   {
		      throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
		   }
		}

	public String getCvoipFailedOrderno() throws DAOServiceExeption {
		{  
		      try
		      {
		         String query = propUtil.getProperty(QueryConstants.Query_Key_Fetch_CVOIP_FAILED_ORDERNO);		        
		         logger.info("Query to get CVOIP Failed BAN :" + query);

		         CachedRowSet resultset = sqlClient.runSelect(query);
		         if (resultset.next())
		         {
		            return resultset.getString(1);
		         }
		         return null;
		      }
		      catch (ClientException e)
		      {
		         throw new DAOServiceExeption("Exception occured while making db call", e);
		      }
		      catch (SQLException e)
		      {
		         throw new DAOServiceExeption("Exception occured while reading resultset", e);
		      }
		      catch (Exception e)
		      {
		         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
		      }
	}

}

	public String getInprogressOrder() throws DAOServiceExeption {
		// TODO Auto-generated method stub
		  try
	      {
	         String query = propUtil.getProperty(QueryConstants.Query_Key_Fetch_InProgress_Order);		        
	         logger.info("Query to get CVOIP Failed BAN :" + query);

	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         return null;
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}

	public String verifyDealerCodeReceiver(String ban) throws DAOServiceExeption{
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Dealer_Code_Receiver),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyDealerCodeReceiver:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}
	
	public String verifyDealerCodeSwap(String ban) throws DAOServiceExeption{
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Dealer_Code_Swap),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyDealerCodeReceiver:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}
	
	public String verifyDealerCodeHardware(String ban) throws DAOServiceExeption{
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Dealer_Code_Hardware),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyDealerCodeHardware:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}

	public String verifyDealerCodeAsset(String ban) throws DAOServiceExeption {
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Dealer_Code_Hardware),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyDealerCodeHardware:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}

	public String verifyNfflIndicator(String ban) throws DAOServiceExeption {
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Nffl_Indicator),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyNfflIndicator:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}

	public String provDtvBan() throws DAOServiceExeption {
      // TODO Auto-generated method stub
      String query = null;

         query = propUtil.getProperty(QueryConstants.Query_Key_Get_Prov_Ban_Dtv);
            

         logger.info("Query in provDtvBan:" + query);
         try
         {
            CachedRowSet resultset = sqlClient.runSelect(query);
            if (resultset.next())
            {
               return resultset.getString(1);
            }
            else
            {
               return null;
            }
         }
         catch (ClientException e)
         {
            throw new DAOServiceExeption("Exception occured while making db call", e);
         }
         catch (SQLException e)
         {
            throw new DAOServiceExeption("Exception occured while reading resultset", e);
         }
         catch (Exception e)
         {
            throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
         }
   }

   public String getbbnmsOrderNo(String ban) throws DAOServiceExeption {
      // TODO Auto-generated method stub
      String query = null;

         query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_BbnmsOrderNo),
            new String[]
            { "?1" }, new String[]
            { ban });

         logger.info("Query in verifyNfflIndicator:" + query);
         try
         {
            CachedRowSet resultset = sqlClient.runSelect(query);
            if (resultset.next())
            {
               return resultset.getString(1);
            }
            else
            {
               return null;
            }
         }
         catch (ClientException e)
         {
            throw new DAOServiceExeption("Exception occured while making db call", e);
         }
         catch (SQLException e)
         {
            throw new DAOServiceExeption("Exception occured while reading resultset", e);
         }
         catch (Exception e)
         {
            throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
         }
   }

   public String getban(String ban) throws DAOServiceExeption{
      // TODO Auto-generated method stub
      String query = null;

         query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Ban),
            new String[]
            { "?1" }, new String[]
            { ban });

         logger.info("Query in verifyNfflIndicator:" + query);
         try
         {
            CachedRowSet resultset = sqlClient.runSelect(query);
            if (resultset.next())
            {
               return resultset.getString(1);
            }
            else
            {
               return null;
            }
         }
         catch (ClientException e)
         {
            throw new DAOServiceExeption("Exception occured while making db call", e);
         }
         catch (SQLException e)
         {
            throw new DAOServiceExeption("Exception occured while reading resultset", e);
         }
         catch (Exception e)
         {
            throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
         }

	
	
}

	public String getOrderNo(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Orderno),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyNfflIndicator:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

	}

	public String dueDate(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Date),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyNfflIndicator:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(2);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

	}

	public String creationDate(String ban) throws DAOServiceExeption {
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Date),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyNfflIndicator:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

	}

	public String orderStatus(String ban) throws DAOServiceExeption {
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Order_Status),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyNfflIndicator:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

	}

	public String subscriptionInfo4K(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Subscription_Info_Values),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in subscriptionInfoValues:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

		
	}
	public String subscriptionInfoSwm(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Subscription_Info_Values),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in subscriptionInfoValues:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(2);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

		
	}
	public String subscriptionInfoMrv(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Subscription_Info_Values),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in subscriptionInfoValues:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(3);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

		
	}
	public String subscriptionInfoCounty(String ban) throws DAOServiceExeption{
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Subscription_Info_Values),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in subscriptionInfoValues:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(4);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }

		
	}

	public String getCloseoutStatus(String ban) throws DAOServiceExeption {
		// TODO Auto-generated method stub
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Closeout_Validation),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in getCloseoutStatus:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            return resultset.getString(1);
	         }
	         else
	         {
	            return null;
	         }
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}
	
	public boolean inInEih(String origOrderId) throws DAOServiceExeption {
		String query = null,result = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_EihCount),
	         new String[]
	         { "?1" }, new String[]
	         { origOrderId });

	      logger.info("Query in getCloseoutStatus:" + query);
	      try
	      {
	         CachedRowSet resultset = sqlClient.runSelect(query);
	         if (resultset.next())
	         {
	            result = resultset.getString(1);
	         }
	         else
	         {
	            result = "";
	         }
	         
	         if(result.equalsIgnoreCase("0"))
	        	 return true;
	         else
	        	 return false;
	      }
	      catch (ClientException e)
	      {
	         throw new DAOServiceExeption("Exception occured while making db call", e);
	      }
	      catch (SQLException e)
	      {
	         throw new DAOServiceExeption("Exception occured while reading resultset", e);
	      }
	      catch (Exception e)
	      {
	         throw new DAOServiceExeption("Unexpected Exception occured while making db call", e);
	      }
	}
}

