package com.att.bbnmstest.client.utils;

public class SaltedHashCredentials extends UserCredentials
{

   private String privateKey;
   private String passphrase;

   public SaltedHashCredentials(String username, String privateKey, String passphrase)
   {
      super(username, null);
      this.privateKey = privateKey;
      this.passphrase = passphrase;
   }

   public String getPrivateKey()
   {
      return privateKey;
   }

   public void setPrivateKey(String privateKey)
   {
      this.privateKey = privateKey;
   }

   public String getPassphrase()
   {
      return passphrase;
   }

   public void setPassphrase(String passphrase)
   {
      this.passphrase = passphrase;
   }

}
