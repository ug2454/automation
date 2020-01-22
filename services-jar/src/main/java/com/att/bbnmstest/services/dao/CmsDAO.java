package com.att.bbnmstest.services.dao;

import java.security.cert.CertPathValidatorException.Reason;
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
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.QueryConstants;

@Component
public class CmsDAO
{

   private final static Logger logger = Logger.getLogger(CmsDAO.class);

   private SqlClient sqlClient;

   @Autowired
   private PropUtil propUtil;


   public CmsDAO(@Value("${dbUrl." + EnvConstants.SYS_CMS + "}") String dbUrl, @Value("${dbUser." + EnvConstants.SYS_CMS
      + "}") String dbUser, @Value("${dbPass." + EnvConstants.SYS_CMS + "}") String dbPass)
   {
      sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));

   }


   public int updateCMSDb(String model, String manufacturer, String ban) throws AutomationBDDServiceException
   {
      try
      {
         String query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Uverse_UpdateCMS),
            new String[]
            { "?1", "?2", "?3" }, new String[]
            { model, manufacturer, ban });

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


}

