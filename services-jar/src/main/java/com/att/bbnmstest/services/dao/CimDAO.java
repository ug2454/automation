package com.att.bbnmstest.services.dao;

import static com.att.bbnmstest.services.util.EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY;
import static com.att.bbnmstest.services.util.EnvConstants.SYS_CIM;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.SqlClient;
import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.QueryConstants;

@Component
public class CimDAO
{

   private final static Logger logger = Logger.getLogger(CimDAO.class);

   private final SqlClient sqlClient;

   @Autowired
   private PropUtil propUtil;

   public CimDAO(@Value("${dbUrl." + SYS_CIM + "}") String dbUrl, @Value("${dbUser." + SYS_CIM + "}") String dbUser,
      @Value("${dbPass." + SYS_CIM + "}") String dbPass)
   {
      sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));

   }


   public int updateExtNodeCienaEmuxAuthstatus(String EMTCLLIName) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(
            QueryConstants.Query_Key_FBS_update_ext_node_ciena_emux_authstatus), "?1", EMTCLLIName);
         logger.info("Update Query running :" + query);

         return sqlClient.runUpdateOrDelete(query);
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

   public int updateFBSUpdateExtPort(String serviceID) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_FBS_update_ext_port), "?1",
            serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateFbsSegments(String serviceID) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_FBS_segments), "?1",
            serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateExtServiceFbs(String serviceID) throws AutomationBDDServiceException
   {
      try
      {

         String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_FBS_update_ext_service_fbs),
            "?1", serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateFbsDimNumber(String serviceID) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_Key_FBS_update_dimnumber), "?1",
            serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateFbsExtNodeEmuxRmonStatus(String EMTCLLIName) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(
            QueryConstants.Query_Key_FBS_Update_ext_node_ciena_emux_rmonstatus), "?1", EMTCLLIName);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateFbsExtPortAtGigaBiteth(String serviceID) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(
            QueryConstants.Query_Key_FBS_Update_ext_port_at_gigabiteth), "?1", serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);
      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public int updateFbsExtPortRmonstatus(String serviceID) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replace(propUtil.getProperty(
            QueryConstants.Query_Key_FBS_Update_ext_port_rmonstatus), "?1", serviceID);
         logger.info("Update Query running :" + query);
         return sqlClient.runUpdateOrDelete(query);

      }
      catch (ClientException e)
      {
         throw new DAOServiceExeption("Exception making db call", e);
      }
   }

   public boolean getG2Status(String node, int maxTimeout) throws DAOServiceExeption, InterruptedException
   {

      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_VALIDATE_MIGRATION_G2STATUS_CIM), new String[]
            { "?1"}, new String[]
            {node });
         logger.info("Query after construction :" + query);
         CachedRowSet resultset;
         String expectedStatus = "YES";
         String failCriterion = "FAILED";
         int timeForloop = maxTimeout;
         //int k = 0;
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
                  Thread.sleep(1000);
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

   public String getMigrationNodeStatus(String node) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_MIGRATION_NODESTATUS_CIM), new String[]
            { "?1" }, new String[]
            { node });
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
   
   public String getMigrationOldSapStatus(String node) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_MIGRATION_OLDSAP_CIM), new String[]
            { "?1" }, new String[]
            { node });
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
   
   public String getMigrationNewSapStatus(String node) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_MIGRATION_NEWSAP_CIM), new String[]
            { "?1" }, new String[]
            { node });
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
}