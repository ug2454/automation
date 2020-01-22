package com.att.bbnmstest.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.att.bbnmstest.client.exception.ShellClientException;
import com.att.bbnmstest.client.utils.SaltedHashCredentials;
import com.att.bbnmstest.client.utils.UserCredentials;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class ShellClient
{
   private final String username;
   private final String password;
   private final String hostname;
   private final Integer port ;
   private final String privateKey;
   private final String passPhrase;

   public ShellClient(String hostname, UserCredentials credentials)
   {
      this(hostname, 22, credentials.getUsername(), credentials.getPassword(), null, null);
   }


   public ShellClient(String hostname, int port, SaltedHashCredentials credentials)
   {
      this(hostname, port, credentials.getUsername(), null, credentials.getPrivateKey(), credentials.getPassphrase());
   }

   private ShellClient(String hostname, int port, String username, String password, String privateKey, String passPhrase)
   {
      this.port = port;
      this.hostname = hostname;
      this.username = username;
      this.password = password;
      this.privateKey = privateKey;
      this.passPhrase = passPhrase;
   }

   public void move(String command)
   {
      execute(command);
   }

   public void executeScript(String command)
   {
      execute(command);
   }

   public String fileExist(String command, String pattern)
   {
      List<String> output = execute(command);
      if (CollectionUtils.isNotEmpty(output))
      {
         for (String content : output)
         {
            String[] fileNames = content.split("\r\n");
            for (String fileName : fileNames)
            {
               if (StringUtils.contains(fileName, pattern))
               {
                  return StringUtils.substring(fileName, fileName.lastIndexOf("/") + 1);
               }
            }

         }
      }
      return null;
   }

   public List<String> execute(String command)
   {
      ChannelExec channelExec = null;
      Session session = null;
      List<String> result = new ArrayList<>();

      try
      {
         session = getSession();
         session.connect();

         channelExec = (ChannelExec) session.openChannel("exec");

         channelExec.setCommand(command);
         channelExec.setInputStream(null);
         channelExec.setErrStream(System.err);
         channelExec.setPty(true);
         InputStream in = channelExec.getInputStream();
         channelExec.connect();
         int count = 1;
         byte[] tmp = new byte[1024];
         while (true)
         {
            count++;
            while (in.available() > 0)
            {
               int i = in.read(tmp, 0, 1024);
               if (i < 0)
                  break;
               result.add(new String(tmp, 0, i));
            }
            if (channelExec.isClosed())
            {
               break;
            }

            Thread.sleep(1000);

            if (count > 5)
            {
               break;
            }
         }

      }
      catch (JSchException | IOException | InterruptedException e)
      {
         throw new ShellClientException("Exception while executing commands", e);
      }

      return result;
   }

   public void put(String sourcefileName, String remoteFileName)
   {
      ChannelSftp channelSftp = null;
      Session session = null;
      InputStream targetStream = null;

      try
      {
         File file = new File(sourcefileName);
         targetStream = new FileInputStream(file);
         session = getSession();
         session.connect();

         channelSftp = (ChannelSftp) session.openChannel("sftp");

         channelSftp.connect();
         channelSftp.put(targetStream, remoteFileName);
      }
      catch (JSchException | IOException | SftpException e)
      {
         throw new ShellClientException("Exception while executing commands", e);
      }

      finally
      {
         if (channelSftp != null)
         {
            channelSftp.disconnect();
         }
         if (session != null)
         {
            session.disconnect();
         }
         if (targetStream != null)
         {
            try
            {
               targetStream.close();
            }
            catch (IOException e)
            {
               throw new ShellClientException("Exception while closing connection", e);
            }
         }
      }
   }

   public void runMultipleCommands(String[] commands)
   {
      Session session = null;
      Channel channel = null;
      try
      {
         session = getSession();
         session.connect();
         channel = session.openChannel("shell");
         PrintStream shellStream = new PrintStream(channel.getOutputStream());
         channel.connect();
         Thread.sleep(5000);
         for (String command : commands)
         {
            shellStream.println(command);
            shellStream.flush();
            Thread.sleep(5000);
         }

         Thread.sleep(5000);


      }
      catch (JSchException | IOException | InterruptedException e)
      {
         throw new ShellClientException("Exception while executing commands", e);
      }

      finally
      {
         if (channel != null)
         {
            channel.disconnect();
         }
         if (session != null)
         {
            session.disconnect();
         }

      }
   }

   private Session getSession() throws JSchException
   {
      Session session;
      JSch jsch = new JSch();

      session = jsch.getSession(username, hostname, port);

      if (this.passPhrase == null && privateKey != null)
      {
         jsch.addIdentity(privateKey);
      }
      else if (this.passPhrase != null && privateKey != null)
      {
         jsch.addIdentity(privateKey, this.passPhrase);
      }
      else
      {
         session.setPassword(password);
         session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
      }

      session.setConfig("StrictHostKeyChecking", "no");
      return session;
   }

}
