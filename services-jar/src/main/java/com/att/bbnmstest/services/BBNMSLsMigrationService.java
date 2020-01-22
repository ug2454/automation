package com.att.bbnmstest.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.unix.ShellUtilCimAdmin;
import com.att.bbnmstest.services.unix.ShellUtilCimApp;
import com.att.bbnmstest.services.unix.ShellUtilOl;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.GuiConstants;
import com.att.bbnmstest.services.util.PropUtil;

@Component
@Qualifier("BBNMSLsMigrationService")
public class BBNMSLsMigrationService extends BBNMSLsGuiServices
{
  private WebDriver driver;
  private WebDriverWait wait;
  private String message;
  private final static Logger logger = Logger.getLogger(BBNMSLsMigrationService.class);
  private String moveId;
  private String[] commands;
   @Autowired
   private PropUtil propUtil;
   
   @Autowired
   private ShellUtilCimApp shellUtilCimApp;
   
   @Autowired
   private ShellUtilCimAdmin shellUtilCimAdmin;
   
   @Autowired
   private ShellUtilOl shellUtilOl;
   
   public BBNMSLsMigrationService(@Value("${" + EnvConstants.BBNMS_LIGHTSPEED_GUIURL + "}") String url, @Value("${"
      + EnvConstants.BBNMS_LIGHTSPEED_GUIURL_USER + "}") String user, @Value("${"
         + EnvConstants.BBNMS_LIGHTSPEED_GUIURL_PASS + "}") String pass) throws AutomationBDDServiceException
   {
      super(url, user, pass);
      moveId = "";
      message="";
   }
   
   public void bulkMigrationNavigate() throws AutomationBDDServiceException
   {
      driver = returnDriver();
      wait = new WebDriverWait(driver,60);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_BULKTAB))).click();
   }
   
   public void addMigration() throws AutomationBDDServiceException, InterruptedException
   {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_BULKHEADER))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_BULKHEADER))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG))).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_SELECTDROPDOWN))));
      Thread.sleep(5000);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SELECTDROPDOWN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SELECTREHOME))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_ACCESSNODECILLI))).sendKeys(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CURRENTROUTERCILLI))).sendKeys(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_CURRENTROUTER));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_TARGETROUTERCILLI))).sendKeys(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_TARGETROUTER));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_SAVEBTN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_YESBTN))).click();
      if (driver.findElements(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CONFIRMMSG))).size() > 0) {
         logger.info(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CONFIRMMSG))).getText());
         message = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CONFIRMMSG))).getText();
         moveId = message.substring(message.indexOf(':')+2);
         logger.info("Move Id ->####"+moveId+"####");
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CONFIRMCLOSEBTN))).click();
      }else
         logger.info(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_ERRORMSG))).getText());
            
   }
   
   public boolean validateAddMigration() throws AutomationBDDServiceException
   {
      if (!message.isEmpty())
      {
         if (message.contains("Rehome Move Created with Move ID")) {
            logger.info("Add migration is successful");
            return true;
         }else
         {
            logger.info("Add migration is not successful");
            return false;
         }
         
      }
      else {
         logger.error("Exception is generated and moveId is not created. Please check the console logs");
         return false;
      }
   }
   public void searchMigration() throws AutomationBDDServiceException
   {
      if(!moveId.isEmpty()) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_BULKHEADER))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_BULKHEADER))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_SEARCHMIG))).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_ADDMIG_SEARCHMIG_MOVEID))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_SEARCHMIG_MOVEID))).sendKeys(moveId);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_SEARCHMIG_SEARCHBTN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_SEARCHMIG_CLICKLINK))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
      }else {
         logger.error("Move ID is not created hence skipping the next part of Search Migration");
      }
      
   }
   
   public boolean validateMoveId() throws AutomationBDDServiceException
   {
      if(moveId.isEmpty()) {
         logger.info("MoveId is not generated");
         return false;
      }else {
         logger.info("MoveId is generated -- >> "+ moveId);
         return true;
         
      }
   }
   
   public boolean validateStepStatus(String stepName, String stepStatus,String maxTimeout) throws AutomationBDDServiceException, InterruptedException
   {
      if(!moveId.isEmpty()) {
         if(getOlDao().getMigrationStepStatus(moveId,stepName,stepStatus,maxTimeout)) {
            logger.info("For Move Id: "+moveId+" Step: "+stepName+" is "+stepStatus);
            driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
            return true;
         }else
         {
            logger.error("Migration Error message -->>"+getOlDao().getMigrationFailMessage(moveId));
            driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
            return false;
         }
   }else {
      logger.info("MoveId is generated -- >> "+ moveId);
      return true;
   }
   }
   
   public boolean preStepTwo() throws AutomationBDDServiceException, InterruptedException
   {
      boolean flag = true;
      if(!moveId.isEmpty()) {
         String step1_command = propUtil.getProperty(EnvConstants.MIGRATION_STEP1_COMMAND1);
         String finalCommand = step1_command.replace("?1", propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
         List<String> result = shellUtilCimAdmin.getClient().execute(finalCommand);
         logger.info("Running command --> "+finalCommand);
         if(!result.isEmpty())
         {
            logger.info("Message -- > "+result);
            flag = true;
         }else {
            logger.error("Error in running command.Message -- > "+result);
            flag = false;
         }
         step1_command = propUtil.getProperty(EnvConstants.MIGRATION_STEP1_COMMAND2);
         finalCommand = step1_command.replace("?1", propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
         result = shellUtilCimAdmin.getClient().execute(finalCommand);
         logger.info("Running command --> "+finalCommand);
         if(result.isEmpty())
         {
            logger.info("Message -- > "+result);
            commands = new String[2];
            commands[0] = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_SUDO);
            commands[1] = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP1_COMMAND1);
            logger.info("Running commands --> "+commands[0]+" and -- >"+commands[1]);
            shellUtilCimApp.getClient().runMultipleCommands(commands);
            logger.info("Executed script in CIM app");
            if (getCimDao().getG2Status(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE),600))
               flag = true;
            else
               flag = false;
         }else {
            logger.error("Error in running command.Message -- > "+result);
            flag = false;
         }

         return flag;
   }else {
      logger.error("MoveId is generated -- >> "+ moveId);
      return false;
   }
   }
 
   public boolean startStep() throws AutomationBDDServiceException, InterruptedException
   {
      String message="";
      if(!moveId.isEmpty()) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))));
      Thread.sleep(3000);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
      Thread.sleep(5000);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTBTN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_YES))).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_MSG))));
      message = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_MSG))).getText();
      logger.info("Message from GUI -->>"+message);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_CLOSE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
      if(message.contains("Operation Successfully Completed, Please Refresh for changes to reflect"))
         return true;
      else
         return false;
      }else {
         logger.error("Move ID is not created hence skipping the next part of Search Migration");
         return false;
      }
      
   }
   
   public boolean checkNodeStatus(String phase) throws AutomationBDDServiceException
   {
      String value = getCimDao().getMigrationNodeStatus(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
      if(phase.equalsIgnoreCase("Before"))
      {
         if(value.equals("1735000001"))
            {
               logger.info("Node is unlocked and node status is  -->>"+ value);
               return true;
            }
         else
         {
            logger.error("Error. Node is locked and node status is  -->>"+ value);
            return false;
         }
      }
      else
      {
         if(value.equals("1900000008"))
            {
               logger.info("Node is locked and node status is  -->>"+ value);
               return true;
            }
         else
         {
            logger.error("Error. Node is unlocked and node status is  -->>"+ value);
            return false;
         }
      }
   }
   
   public boolean stepTwoDbStatus(String phase) throws AutomationBDDServiceException, InterruptedException
   {
      String value = getCimDao().getMigrationOldSapStatus(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
      String valueSecond = getCimDao().getMigrationNewSapStatus(propUtil.getProperty(EnvConstants.BBNMS_MIGRATION_ACCESSNODE));
      logger.info("Output old sap-- > "+value+" and new sap --> "+valueSecond);
      if(phase.equalsIgnoreCase("Before"))
      {
         if(value.equals("0") && valueSecond.equals("0"))
            {
               logger.info("Old SAP is presesnt and new SAP is null  -->>");
               return true;
            }
         else
         {
            logger.error("Error. Old SAP is not presesnt or new SAP is not null   -->>");
            return false;
         }
      } 
      else
      {
         Thread.sleep(5000);
         if(value.equals("0") && !valueSecond.equals("0"))
            {
               logger.info("Old SAP is populated and new SAP is populated  -->>");
               return true;
            }
         else
         {
            logger.error("Error.Either Old SAP is not populated or new SAP is not populated  -->>");
            return false;
         }
      }
   }
   
   public boolean preStepSix() throws AutomationBDDServiceException, InterruptedException
   {
      boolean flag = true;
      if(!moveId.isEmpty()) {
         String step1_command = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND1);
         String finalCommand = step1_command.replace("?1", moveId);
         List<String> result = shellUtilCimAdmin.getClient().execute(finalCommand);
         logger.info("Running command --> "+finalCommand);
         if(!result.isEmpty())
         {
            logger.info("Message -- > "+result);
            flag = true;
         }else {
            logger.error("Error in running command.Message -- > "+result);
            flag = false;
         }
         result = shellUtilOl.getClient().execute(finalCommand);
         logger.info("Running command --> "+finalCommand);
         if(!result.isEmpty())
         {
            logger.info("Message -- > "+result);
            flag = true;
         }else {
            logger.error("Error in running command.Message -- > "+result);
            flag = false;
         }
         
         step1_command = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND2);
         finalCommand = step1_command.replace("?1", moveId);
         String finalCommand2= propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND3).replace("?1", moveId);
         String finalCommand3= propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND4).replace("?1", moveId);
         result = shellUtilCimAdmin.getClient().execute(finalCommand);
         List<String> result1 = shellUtilCimAdmin.getClient().execute(finalCommand2);
         List<String> result2 = shellUtilCimAdmin.getClient().execute(finalCommand3);
         logger.info("Running command --> "+finalCommand);
         logger.info("Running command --> "+finalCommand2);
         logger.info("Running command --> "+finalCommand3);
         if(!result.isEmpty() && !result1.isEmpty() && !result2.isEmpty())
         {
            logger.info("Message -- > "+result);
            logger.info("Message -- > "+result1);
            logger.info("Message -- > "+result2);
            commands = new String[3];
            commands[0] = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND5).replace("?1", moveId);
            commands[1] = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND6).replace("?1", moveId);
            commands[2] = propUtil.getProperty(EnvConstants.MIGRATION_CIMAPP_STEP6_COMMAND7).replace("?1", moveId);
            logger.info("Running commands --> "+commands[0]+" and -- >"+commands[1]+" and -- >"+commands[2]);
            shellUtilCimApp.getClient().runMultipleCommands(commands);
            logger.info("Executed script in CIM admin");
         }else {
            logger.error("Error in running command.Message -- > "+result);
            flag = false;
         }

         return flag;
   }else {
      logger.error("MoveId is generated -- >> "+ moveId);
      return false;
   }
   }
   
   public boolean startRollbackStep() throws AutomationBDDServiceException, InterruptedException
   {
      if(!moveId.isEmpty()) {
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_ROLLBACKBTN))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_YES))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_STARTSTEP_CLOSE))).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))));
      Thread.sleep(3000);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_REFRESHBTN))).click();
      logger.info("Rollback button is clicked for move id: -- >> "+ moveId);
      return true;
   }else {
      logger.error("MoveId is generated -- >> "+ moveId);
      return false;
   }
   }
   
   public boolean validateAddMigTags() throws AutomationBDDServiceException, InterruptedException
   {
      boolean flag = true;
      String currentCilliTag,currentConfigTag,targetCilliTag,targetConfigTag,targetRouterTag,currentCardImm12,currentCardImm48,targetRouterPort;
      currentCilliTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CURRENTCILLI_TAG))).getText();
      currentConfigTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CURRENTCONFIG_TAG))).getText();
      targetCilliTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_TARGETCILLI_TAG))).getText();
      targetConfigTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_TARGETCONFIG_TAG))).getText();
      targetRouterTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_TARGETROUTER_TAG))).getText();
      currentCardImm12 = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CURRENTCARD_IMM12))).getText();
      currentCardImm48 = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_CURRENTCARD_IMM48))).getText();
      targetRouterPort = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_ADDMIG_TARGETROUTERPORT))).getText();
      logger.info("Current Cilli tag from GUI --> "+currentCilliTag);
      logger.info("Current Config tag from GUI --> "+currentConfigTag);
      logger.info("Target Cilli tag from GUI --> "+targetCilliTag);
      logger.info("Target Config tag from GUI --> "+targetConfigTag);
      logger.info("Target Router tag from GUI --> "+targetRouterTag);
      logger.info("Current Card IMM12 tag from GUI --> "+currentCardImm12);
      logger.info("Current Card IMM48 tag from GUI --> "+currentCardImm48);
      logger.info("Target Router Port tag from GUI --> "+targetRouterPort);
      if(currentCilliTag.equals("Current CO Router CLLI:") && currentConfigTag.equals("Current CO Router Config:") &&
         targetCilliTag.equals("Target CO Router CLLI:") && targetConfigTag.equals("Target CO Router Config:") &&
         targetRouterTag.equals("Target CO Router:") && currentCardImm12.equals("IMM12") && 
         currentCardImm48.equals("IMM48") && targetRouterPort.equals("48"))
      {
         flag = true;
         logger.info("All tag values in Add migration GUI is correct");
      }
      else {
         flag = false;
         logger.error("Error. All tag values in Add migration GUI is not correct");
      }
      return flag;
   }
   
   public boolean validateSearchMigTags() throws AutomationBDDServiceException, InterruptedException
   {
      boolean flag = true;
      String targetCilliTag,targetPortTag;
      targetCilliTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_TARGETCILLI))).getText();
      targetPortTag = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BULKMIG_SEARCHMIG_TARGETPORT))).getText();
      logger.info("Target Cilli tag from GUI --> "+targetCilliTag);
      logger.info("Target Port tag from GUI --> "+targetPortTag);
      if(targetCilliTag.equals("Target CO Router CLLI:") && targetPortTag.equals("CO Router Target Port:"))
      {
         flag = true;
         logger.info("All tag values in Migration details GUI is correct");
      }
      else {
         flag = false;
         logger.error("Error. All tag values in Migration details GUI is not correct");
      }
      return flag;
   }
}
