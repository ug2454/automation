package com.att.bbnmstest.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

import com.att.bbnmstest.client.exception.ClientException;
import com.att.bbnmstest.client.exception.SqlClientException;


public class SqlClient
{

   private final String dbUrl;
   private final String dbUsr;
   private final String dbPswd;


   public SqlClient(String dbUrl, String dbUsr, String dbPswd)
   {
      this.dbUrl = dbUrl;
      this.dbUsr = dbUsr;
      this.dbPswd = dbPswd;
   }


   public CachedRowSet runSelect(String sql) throws ClientException
   {
      try (Connection con = DriverManager.getConnection(dbUrl, dbUsr, dbPswd);)
      {
         CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
         PreparedStatement pstmt1 = con.prepareStatement(sql);
         ResultSet resultSet = pstmt1.executeQuery();
         crs.populate(resultSet);
         return crs;
      }
      catch (SQLException e)
      {
         throw new SqlClientException("Exection while execution query:" + sql, e);
      }
      catch (Exception e)
      {
         throw new SqlClientException("Unexpected Exection while execution query:" + sql, e);
      }
   }

   public int runUpdateOrDelete(String sql) throws ClientException
   {
      try (Connection con = DriverManager.getConnection(dbUrl, dbUsr, dbPswd);)
      {

         PreparedStatement pstmt1 = con.prepareStatement(sql);
         int count = pstmt1.executeUpdate();

         return count;
      }
      catch (SQLException e)
      {
         throw new SqlClientException("Exection while execution query:" + sql, e);
      }
      catch (Exception e)
      {
         throw new SqlClientException("Unexpected Exection while execution query:" + sql, e);
      }

   }

   public void ping() throws ClientException
   {

      try (Connection con = DriverManager.getConnection(dbUrl, dbUsr, dbPswd);)
      {

         RowSetProvider.newFactory().createCachedRowSet();
      }
      catch (SQLException e)
      {
         throw new SqlClientException("Ping Request Failed", e);
      }
      catch (Exception e)
      {
         throw new SqlClientException("Ping Request Failed", e);
      }

   }


}
