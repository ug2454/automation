package com.att.bbnmstest.client.utils;


import org.jasypt.util.text.BasicTextEncryptor;


public class EncryptDecryptUtil
{

   public static String decrypt(String input, String key)
   {
      try
      {
         BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
         textEncryptor.setPassword(key);
         String plainText = textEncryptor.decrypt(input);
         return plainText;
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Exception occurd while decryption", e);
      }
   }

   public static String decryptBySysProp(String input, String propName)
   {
      String masterKey = System.getProperty(propName);
      return decrypt(input, masterKey);
   }

   public static String encrypt(String input, String key)
   {
      try
      {
         BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
         textEncryptor.setPassword(key);
         String myEncryptedText = textEncryptor.encrypt(input);
         return myEncryptedText;
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Exception occurd while encryption", e);
      }
   }
public static void main(String[] args)
{
   String encryptedPassword = encrypt("GARLICjunior1995","kf#78B");
   
   System.out.println(encryptedPassword);
   System.out.println(decrypt("bQ5ZvlOz2gNa4i7Y3teqm3QjDDm2RiAJ","kf#78B"));
}
}
