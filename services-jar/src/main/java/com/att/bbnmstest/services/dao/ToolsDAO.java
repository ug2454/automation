package com.att.bbnmstest.services.dao;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.lang3.StringUtils;
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

@Component
public class ToolsDAO
{

   private final static Logger logger = Logger.getLogger(ToolsDAO.class);

   private SqlClient sqlClient;

   @Autowired
   private PropUtil propUtil;

   public ToolsDAO(@Value("${dbUrl." + EnvConstants.SYS_TOOLS + "}") String dbUrl, @Value("${dbUser."
      + EnvConstants.SYS_TOOLS + "}") String dbUser, @Value("${dbPass." + EnvConstants.SYS_TOOLS + "}") String dbPass)
   {
      sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));

   }


   public String getUniqueImei() throws DAOServiceExeption
   {

      try
      {
         String imei = null;
         String query = propUtil.getProperty(QueryConstants.Query_key_Get_Unique_Imei);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            imei = resultset.getString(1);
            Long num = Long.parseLong(imei);
            num++;
            String updateImei = num.toString();
            String updateQuery = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_key_Update_Unique_Imei),
               "?1", updateImei);
            sqlClient.runUpdateOrDelete(updateQuery);
         }
         logger.info("imei value is --" + imei);
         return imei;
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

   public String getUniqueIccid() throws DAOServiceExeption
   {
      try
      {
         String iccid = null;
         String query = propUtil.getProperty(QueryConstants.Query_key_Get_Unique_Iccid);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            iccid = resultset.getString(1);
            Long num = Long.parseLong(iccid);
            num++;
            String updateQ = num.toString();
            String updateQuery = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_key_Update_Unique_Iccid),
               "?1", updateQ);
            sqlClient.runUpdateOrDelete(updateQuery);
         }

         return iccid;
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

   public String getUniqueImsi() throws DAOServiceExeption
   {
      try
      {
         String imsi = null;
         String query = propUtil.getProperty(QueryConstants.Query_key_Get_Unique_Imsi);
         CachedRowSet resultset = sqlClient.runSelect(query);
         if (resultset.next())
         {
            imsi = resultset.getString(1);
            Long num = Long.parseLong(imsi);
            num++;
            String updateQ = num.toString();
            String updateQuery = StringUtils.replace(propUtil.getProperty(QueryConstants.Query_key_Update_Unique_Imsi),
               "?1", updateQ);
            sqlClient.runUpdateOrDelete(updateQuery);
         }
         return imsi;
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

   public boolean insertSimResponseInToolsDb(String simName, Map<String, String> params) throws DAOServiceExeption
   {
      try
      {
         String query = null;
         if (simName.equals("csinsm"))
         {
            query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_insert_rec_csinsm),
               new String[]
               { "?1" }, new String[]
               { params.get("iccid") });
            logger.info("insert query csinsm  -" + query);
            return (sqlClient.runUpdateOrDelete(query) == 1);

         }
         else if (simName.equals("csisc"))
         {
            query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_insert_rec_csisc),
               new String[]
               { "?1" }, new String[]
               { params.get("tn") });
            logger.info("insert query csisc  -" + query);
            return (sqlClient.runUpdateOrDelete(query) == 1);
         }
         else
         {
            return false;
         }
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

   public boolean updateSimResponseInToolsDb(String simName, Map<String, String> params) throws DAOServiceExeption
   {
      try
      {

         String query = null;
         if (simName.equals("csinsm"))
         {
            query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_update_rec_csinms_A),
               new String[]
               { "?1", "?2" }, new String[]
               { params.get("imsi"), params.get("iccid") });
            logger.info("updateSimResponseInToolsDb:csinsm:  " + query);
            if (sqlClient.runUpdateOrDelete(query) == 1)
            {

               query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_update_rec_csinms_U),
                  new String[]
                  { "?1", "?2" }, new String[]
                  { params.get("imsi"), params.get("iccid") });
               logger.info("updateSimResponseInToolsDb:csinsm:  " + query);
               return (sqlClient.runUpdateOrDelete(query) == 1);

            }
            else
            {
               return false;
            }
         }
         else if (simName.equals("csisc"))
         {
            query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_key_update_rec_csisc),
               new String[]
               { "?1", "?2", "?3", "?4", "?5" }, new String[]
               { params.get("statuscode"), params.get("tn"), params.get("iccid"), params.get("imsi"), params.get(
                  "completedatatime") });
            logger.info("updateSimResponseInToolsDb:csisc:  " + query);
            return (sqlClient.runUpdateOrDelete(query) == 1);

         }
         else
         {
            return false;
         }
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

   public boolean updateCsinsmForSimIccid(String iccid) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Update_Csinsm_For_Sim_Iccid), new String[]
            { "?1" }, new String[]
            { iccid });
         logger.info("updateCsinmsForSimIccid:" + query);
         if (sqlClient.runUpdateOrDelete(query) >= 1)
         {
            return true;
         }
         else
         {
            return false;
         }

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


   public boolean updateCsinmsImsiForSimIccid(String imsi, String iccid) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(
            QueryConstants.Query_Key_Update_Csinsm_Imsi_For_Sim_Iccid), new String[]
            { "?1", "?2" }, new String[]
            { imsi, iccid });
         logger.info("updateCsinmsIsimForSimIccid:" + query);
         if (sqlClient.runUpdateOrDelete(query) >= 1)
         {
            return true;
         }
         else
         {
            return false;
         }

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

   public boolean removeFromCsiscByTn(String tn) throws DAOServiceExeption
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Remove_From_Csics_By_Tn),
            new String[]
            { "?1" }, new String[]
            { tn });
         logger.info("removeFromCsiscByTn:" + query);
         if (sqlClient.runUpdateOrDelete(query) >= 1)
         {
            return true;
         }
         else
         {
            return false;
         }

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
}
