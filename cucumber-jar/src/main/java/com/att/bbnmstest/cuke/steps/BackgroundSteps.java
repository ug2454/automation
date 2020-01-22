package com.att.bbnmstest.cuke.steps;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.att.bbnmstest.services.BBNMSLsGuiServices;
import com.att.bbnmstest.services.BBNMSLsMigrationService;
import com.att.bbnmstest.services.BBNMSService;
import com.att.bbnmstest.services.cleanup.BanCleanupService;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.cptest.services.CpBaseServices;
import com.att.cptest.services.CpGuiServices;
import com.att.cptest.services.config.CpConfigTabServices;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

@Ignore
public class BackgroundSteps extends BaseStep
{
   private final static Logger logger = Logger.getLogger(BackgroundSteps.class);

   @Autowired
   private PropUtil propUtil;

   @Autowired
   @Qualifier("BanCleanupService")
   private BanCleanupService banCleanup;

   @Autowired
   @Qualifier("BBNMSService")
   private BBNMSService bbnmsService;

   @Autowired
   @Qualifier("BBNMSLsGuiServices")
   private BBNMSLsGuiServices guiService;
   
   @Autowired
   @Qualifier("CpConfigTabServices")
   private CpConfigTabServices cpGuiServices;

   @Autowired
   @Qualifier("BBNMSLsMigrationService")
   private BBNMSLsMigrationService migrationService;

   @Given("^Perform context cleanup$")
   public void cleanContext()
   {
      logger.debug("Perform clean up of context");
      getContext().clear();
   }

   @And("^Set (.+) to (.*)$")
   public void setProperty(String key, String value)
   {
      propUtil.setProperty(key, value);

      logger.info("Intialized " + key + " to " + propUtil.getProperty(key));
   }

   @And("^Perform Ban Cleanup for (.+)$")
   public void banCleanup(String nti) throws Exception
   {
      logger.info("ban Cleanup execution Starts!");
      banCleanup.banCleanup(nti);
   }

   @And("^Set up dmaap Kafka adapter$")
   public void setUpDmaapKafkaAdapter()
   {
      logger.info("Performing a seek to end to read latest messages generated from this point!");
      bbnmsService.seekToEndDmaapKafkaClient();
   }

   @After("@Gui")
   public void tearDownGui() throws AutomationBDDServiceException
   {
      logger.info("Gui Tear Down");
      guiService.quitGui();
   }
   
   
   @After("@Cpgui")
   public void tearDownCpGui() throws AutomationBDDServiceException
   {
      logger.info("Gui Tear Down");
      cpGuiServices.quitCpGui();
   }

   @After("@Migration")
   public void tearDownMigGui() throws AutomationBDDServiceException
   {
      logger.info("Gui Tear Down");
      migrationService.quitGui();
   }
}
