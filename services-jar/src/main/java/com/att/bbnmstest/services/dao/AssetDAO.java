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
public class AssetDAO
{
   private final static Logger logger = Logger.getLogger(AssetDAO.class);
   private SqlClient sqlClient;
   @Autowired
   private PropUtil propUtil;


   public AssetDAO(@Value("${dbUrl." + EnvConstants.SYS_ASSET + "}") String dbUrl, @Value("${dbUser."
      + EnvConstants.SYS_ASSET + "}") String dbUser, @Value("${dbPass." + EnvConstants.SYS_ASSET + "}") String dbPass)
   {
      sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));

   }
   
   
   public String verifyDealerCodeAsset(String ban) throws DAOServiceExeption {
		String query = null;

	      query = StringUtils.replaceEach(propUtil.getProperty(QueryConstants.Query_Key_Get_Dealer_Asset),
	         new String[]
	         { "?1" }, new String[]
	         { ban });

	      logger.info("Query in verifyDealerCodeAsset:" + query);
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


