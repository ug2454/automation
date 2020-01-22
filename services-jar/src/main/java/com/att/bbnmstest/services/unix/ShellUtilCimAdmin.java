package com.att.bbnmstest.services.unix;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.ShellClient;
import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.client.utils.SaltedHashCredentials;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.util.EnvConstants;

@Component
@Qualifier("ShellUtilCimAdmin")
public class ShellUtilCimAdmin  extends BBNMSService
{
   private ShellClient shellUtil;

   public ShellUtilCimAdmin(@Value("${" + EnvConstants.BBNMS_CIMADMIN_HOSTNAME + "}") String hostname, @Value("${"
      + EnvConstants.BBNMS_UNIX_USERNAME + "}") String username, @Value("${" + EnvConstants.BBNMS_UNIX_KEY
         + "}") String privateKey,@Value("${" + EnvConstants.BBNMS_UNIX_PASSPHRASE
            + "}") String passphrase)
   {
      shellUtil = new ShellClient(hostname, 22, new SaltedHashCredentials(username, privateKey,EncryptDecryptUtil.decryptBySysProp(passphrase, EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY) ));
   }
   
   public ShellClient getClient() {
      return shellUtil;
      
   }
}
