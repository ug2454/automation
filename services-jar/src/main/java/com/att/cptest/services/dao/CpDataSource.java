package com.att.cptest.services.dao;

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
public class CpDataSource
{
   private final static Logger logger = Logger.getLogger(CpDataSource.class);
   private SqlClient sqlClient;
   @Autowired 
   private PropUtil propUtil;


   public CpDataSource(@Value("${dbUrl.cp}") String dbUrl, @Value("${dbUser.cp}") String dbUser, 
		   @Value("${dbPass.cp}") String dbPass)
   {
	   sqlClient = new SqlClient(dbUrl, dbUser, EncryptDecryptUtil.decryptBySysProp(dbPass,
         EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));
      

   }

   public String getSdndUnavailableMinutes(String raName) throws DAOServiceExeption
   {

	   try
	   {
		   String query = StringUtils.replaceEach(propUtil.getProperty(
				   QueryConstants.Query_Check_RA_SDND_UNAVAILABLE), new String[]
						   { "?1" }, new String[]
								   { raName });

		   logger.info("Query for checking if RA is scheduled for today:" + query);

		   CachedRowSet resultset = sqlClient.runSelect(query);
		   
		   if (resultset.next())
		   {
			   logger.info("Data is setup for RA:" + raName);
			   query = StringUtils.replaceEach(propUtil.getProperty(
					   QueryConstants.Query_Check_RA_SDND_UNAVAILABLE_MINS), new String[]
							   { "?1" }, new String[]
									   { raName });
			   logger.info("Query for getting the unplanned minutes for current date for RA "+raName+" :" + query);
			   resultset = sqlClient.runSelect(query);
			   if (resultset.next())
			   {
				   return resultset.getString(1);
			   }
			   else
			   {
				   return "";
			   }
		   }
		   else {
			   logger.error("For "+raName+" RA, unplanned hours is not setup at backend for current date.");
			   return "";
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
   
   
   public ArrayList<String> getHierarchy(String raName) throws DAOServiceExeption
   {

	   try
	   {
		   String query = StringUtils.replaceEach(propUtil.getProperty(
				   QueryConstants.Query_GET_RESOURCEHIERARCHY), new String[]
						   { "?1" }, new String[]
								   { raName });

		   logger.info("Query for getting resource hierarchy:" + query);

		   CachedRowSet resultset = sqlClient.runSelect(query);
		   ArrayList<String> result = new ArrayList<String>();
		   while (resultset.next())
		   {
			  result.add(resultset.getString(1));
			  result.add(resultset.getString(2));
			  result.add(resultset.getString(3));
			  result.add(resultset.getString(4));
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
   

}

