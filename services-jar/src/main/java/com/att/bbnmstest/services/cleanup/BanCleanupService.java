package com.att.bbnmstest.services.cleanup;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.ShellClient;
import com.att.bbnmstest.client.utils.SaltedHashCredentials;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;

@Component
@Qualifier("BanCleanupService")
public class BanCleanupService extends BBNMSService
{

   private final static Logger logger = Logger.getLogger(BBNMSService.class);

   private ShellClient shellUtil;

   @Autowired
   private PropUtil propUtil;

   private String[] commands;

   public BanCleanupService(@Value("${" + EnvConstants.BAN_CLEANUP_HOSTNAME + "}") String hostname, @Value("${"
      + EnvConstants.BAN_CLEANUP_USERNAME + "}") String username, @Value("${" + EnvConstants.BAN_CLEANUP_PRIVATEKEY
         + "}") String privateKey)
   {
      shellUtil = new ShellClient(hostname, 22, new SaltedHashCredentials(username, privateKey, null));
   }


   public void initCommands()
   {
      commands = new String[4];
      commands[0] = propUtil.getProperty(EnvConstants.BAN_CLEANUP_COMMAND1);
      commands[1] = propUtil.getProperty(EnvConstants.BAN_CLEANUP_COMMAND2);
   }

   public void banCleanup(String nti) throws AutomationBDDServiceException
   {
      initCommands();
      java.util.List<String> results = null;

      String BAN_CLEAN_UP_CMD = propUtil.getProperty(EnvConstants.BAN_CLEANUP_COMMAND3);

      if (nti.equals(propUtil.getProperty(EnvConstants.BAN_CLEANUP_NTI)))
      {
         results = getOlDao().getUsedBanForXgsPon();
      }
      else
      {
         throw new AutomationBDDServiceException("Ban Clean Up for Requested NTI[" + nti + "] not implemented")
         {
            @Override
            public String getDescription()
            {
               return "Unsupported NTI Exception";
            }

         };
      }
      String createInputFileCmd = "printf '"; // s+" \n ' > "+fileName+".txt";
      for (String s : results)
      {
         createInputFileCmd = createInputFileCmd + s + "\\n";
      }

      String fileName = "ban_clean_" + new java.util.Date().getTime();
      createInputFileCmd = createInputFileCmd + "' > ../input_data/" + fileName + ".txt";
      commands[2] = createInputFileCmd;
      String banCleanUpCmd = BAN_CLEAN_UP_CMD.replace("?1", propUtil.getEnv()).replace("?2", fileName);
      commands[3] = banCleanUpCmd;
      logger.info("session created.");
      shellUtil.runMultipleCommands(commands);
      logger.info("Ban Cleanup Executed .");

   }

}
