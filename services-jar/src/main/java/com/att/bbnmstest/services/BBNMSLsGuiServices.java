package com.att.bbnmstest.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.client.utils.EncryptDecryptUtil;
import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.exception.DAOServiceExeption;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.GuiConstants;
import com.att.bbnmstest.services.util.PropUtil;

@Component
@Qualifier("BBNMSLsGuiServices")
public class BBNMSLsGuiServices extends BBNMSService
{

   private final String guiUrl;
   private final String guiPass;
   private final String guiUser;
   private String bbnmsOrder;
   private Date dNow;
   private SimpleDateFormat ft;
   private WebDriverWait wait;
   private List<String> listDesc;
   private List<String> dbValues;
   private String ordSearchWfStatus, ordSearchWfDesc;
   private String dbWfStatus, dbWfDesc;
   private String productType;
   private String ban = "506598517";

   private WebDriver driver;
   private List<WebElement> li;
   private ChromeOptions options;
   private FirefoxOptions fireOptions;

   private final static Logger logger = Logger.getLogger(BBNMSLsGuiServices.class);

   @Autowired
   private PropUtil propUtil;

   public BBNMSLsGuiServices(@Value("${" + EnvConstants.BBNMS_LIGHTSPEED_GUIURL + "}") String url, @Value("${"
      + EnvConstants.BBNMS_LIGHTSPEED_GUIURL_USER + "}") String user, @Value("${"
         + EnvConstants.BBNMS_LIGHTSPEED_GUIURL_PASS + "}") String pass) throws AutomationBDDServiceException
   {
      this.guiUrl = url;
      this.guiPass = pass;
      this.guiUser = user;
   }

   public void loginGui(String prodType) throws AutomationBDDServiceException
   {

      productType = prodType;
      if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKBROWSER).equalsIgnoreCase("chrome"))
      {
         System.setProperty("webdriver.chrome.driver", propUtil.getProperty(
            EnvConstants.BBNMS_LIGHTSPEED_GUI_CHROMEBROWSERPATH));
         if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_HEADLESSENABLED).equalsIgnoreCase("Yes"))
         {
            options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
               "--ignore-certificate-errors");
            driver = new ChromeDriver(options);
         }
         else
            driver = new ChromeDriver();
      }
      else if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKBROWSER).equalsIgnoreCase("firefox"))
      {
         System.setProperty("webdriver.gecko.driver", propUtil.getProperty(
            EnvConstants.BBNMS_LIGHTSPEED_GUI_FIREFOXBROWSERPATH));
         if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_HEADLESSENABLED).equalsIgnoreCase("Yes"))
         {
            fireOptions = new FirefoxOptions();
            fireOptions.setHeadless(true);
            driver = new FirefoxDriver(fireOptions);
         }
         else
            driver = new FirefoxDriver();
      }

      wait = new WebDriverWait(driver, 60);
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.get(guiUrl);
      logger.info("GUI URL -> "+guiUrl);
      driver.manage().window().maximize();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGIN_UNAME))).sendKeys(guiUser);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGIN_PASS))).sendKeys(EncryptDecryptUtil
         .decryptBySysProp(guiPass, EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGIN_SUBMIT))).click();
      driver.findElement(By.name(propUtil.getProperty(GuiConstants.GUI_LOGINSUCCESS_OK))).click();

   }

   public void orderSearchNavigate() throws AutomationBDDServiceException
   {
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SERVMANAGE_ORDERSEARCH))).click();
   }
   
   public void subscriptionSearchNavigate() throws AutomationBDDServiceException
   {
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SEARCH))).click();
   }

   public void orderSearchOptions(String prodType) throws AutomationBDDServiceException, InterruptedException
   {

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();
      if (prodType.equals("Uverse"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_ATT))).click();
      else if (prodType.equals("DTV"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_DTV))).click();
      else if (prodType.equals("WLL"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_WLL))).click();

      if (prodType.equals("Uverse"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP_HSIA))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE_OMS))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_CREATEDATEFROM))).click();
      }
      else if (prodType.equals("DTV"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_OMS))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_CREATEDATEFROM)))
            .click();

      }
      else if (prodType.equals("WLL"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SOURCE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SOURCE_OMS))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_CREATEDATEFROM)))
            .click();
      }
      String str = getDate(10);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace("#dateVal#", str))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace(
         "#dateVal#", str))).click();

      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();

      Thread.sleep(20000);
      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
      li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
      bbnmsOrder = li.get(0).getText();
   }
   
   public void getOrderScreenDetails() throws AutomationBDDServiceException, InterruptedException
   {
      listDesc = validateStatusDescriptionMilestone(getOlDao().getWorkflowId(bbnmsOrder));

      ordSearchWfStatus = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_WFSTATUS)))
         .getText();
      ordSearchWfDesc = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_WFDESC)))
         .getText();
   }

   public void dtvOrderSearch(String prodType) throws AutomationBDDServiceException, InterruptedException
   {

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();


      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_DTV))).click();


      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_OMS))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_CREATEDATEFROM)))
         .click();

      String str = getDate(3);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace("#dateVal#", str))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace(
         "#dateVal#", str))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN)))
      .click();

   }
   
   public void wllOrderSearch(String prodType) throws InterruptedException, AutomationBDDServiceException {
	   

	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
	         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
	      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();
	      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_WLL))).click();
	      
	         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SOURCE))).click();
	         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SOURCE_OMS))).click();
	         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_SOURCE_CREATEDATEFROM)))
	            .click();
	      
	      String str = getDate(5);
	      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
	         GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace("#dateVal#", str))));
	      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace(
	         "#dateVal#", str))).click();

	      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();

	      Thread.sleep(20000);
	      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
	      li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
	      bbnmsOrder = li.get(0).getText();
	   
	   }
   
   
   public void uverseOrderSearch(String prodType) throws InterruptedException, AutomationBDDServiceException {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_ATT))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP_HSIA))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE_OMS))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_CREATEDATEFROM))).click();
         
         
         String str = getDate(10);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
            GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace("#dateVal#", str))));
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_CREATEDATE_DATE).replace(
            "#dateVal#", str))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();

         Thread.sleep(20000);
         ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
         li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
         bbnmsOrder = li.get(0).getText();
   }


   public void uverseFttnBpOrderSearch(String prodType) throws InterruptedException, AutomationBDDServiceException {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_ATT))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SERVICEDROP_HSIA))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_SOURCE_OMS))).click();
         
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_NTI))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_NTI_DROPDOWN_FTTN_BP))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_TYPE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_TYPE_DROPDOWN))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ACTIVITY_DROPDOWN))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ACTIVITY_DROPDOWN_CVOIP))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SERVICES))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SERVICES_CVOIP))).click();

         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();

         Thread.sleep(20000);
         ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
         li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
         bbnmsOrder = li.get(0).getText();
   }
   
   public boolean getSearchResultValidate() throws AutomationBDDServiceException
   {
      if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKMILESTONEDB).equalsIgnoreCase("Yes"))
      {
         dbValues = getOlDao().getMilestoneInfoFromDb(bbnmsOrder);
         dbWfStatus = dbValues.get(0);
         dbWfDesc = dbValues.get(1);
      }
      /* validate order search values */
      boolean status = true;
      logger.info("#" + ordSearchWfStatus + "#");
      logger.info("#" + ordSearchWfDesc + "#");
      if (ordSearchWfStatus.equals(""))
      {
         ordSearchWfStatus = "null";
         dbWfStatus = "null";
      }
      if (ordSearchWfDesc.equals(""))
      {
         ordSearchWfDesc = "null";
         dbWfDesc = "null";
      }

      if (!ordSearchWfStatus.equals(listDesc.get(0) + "") || !ordSearchWfDesc.equals(listDesc.get(1) + ""))
      {
         status = false;
         logger.info("Error: Value in GUI for Status: " + ordSearchWfStatus + " and from MicroService " + listDesc.get(
            0));
         logger.info("Error: Value in GUI for Description: " + ordSearchWfDesc + " and from MicroService " + listDesc
            .get(1));

      }
      else
      {
         logger.info("Pass: Value in GUI for Status: " + ordSearchWfStatus + " and from MicroService " + listDesc.get(
            0));
         logger.info("Pass : Value in GUI for Description: " + ordSearchWfDesc + " and from MicroService " + listDesc
            .get(1));
      }

      if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKMILESTONEDB).equalsIgnoreCase("Yes"))
      {
         if (!dbWfStatus.equals(listDesc.get(0) + "") || !dbWfDesc.equals(listDesc.get(1) + ""))
         {
            status = false;
            logger.info("Error: Value in DB for Status: " + dbValues.get(0) + "" + " and from MicroService " + listDesc
               .get(0));
            logger.info("Error: Value in DB for Description: " + dbValues.get(1) + "" + " and from MicroService "
               + listDesc.get(1));

         }
         else
         {
            logger.info("Pass: Value in DB for Status: " + dbValues.get(0) + "" + " and from MicroService " + listDesc
               .get(0));
            logger.info("Pass : Value in DB for Description: " + dbValues.get(1) + "" + " and from MicroService "
               + listDesc.get(1));
         }
      }

      return status;

   }

   public void clickOrderLink() throws AutomationBDDServiceException
   {

      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", li.get(0));
      if (productType.equals("Uverse"))
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
            GuiConstants.GUI_ORDERSUMMARY_ATT_WFDESC))));
      else if (productType.equals("DTV"))
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
            GuiConstants.GUI_ORDERSUMMARY_DTV_WFDESC))));
      else if (productType.equals("WLL"))
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
            GuiConstants.GUI_ORDERSUMMARY_WLL_WFDESC))));

   }

   public boolean validateOrderSummary() throws AutomationBDDServiceException
   {
      boolean status = true;
      String dummyString = "", substring = "", result = "";

      if (productType.equals("Uverse"))
         result = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_ATT_WFDESC)))
            .getText();
      else if (productType.equals("DTV"))
         result = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_DTV_WFDESC)))
            .getText();
      else if (productType.equals("WLL"))
         result = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_WLL_WFDESC)))
            .getText();

      if (ordSearchWfDesc.equals("null"))
      {
         substring = result.substring(0, nthIndexOf(result, ']', 2));
         dummyString = "[ MAIN ] - - [ #WfStatus# ]";
         dummyString = dummyString.replace("#WfStatus#", listDesc.get(0));
         logger.info("GUI:#" + substring + "#");
         logger.info("Dummy:#" + dummyString + "#");
      }
      else
      {
         if (productType.equals("Uverse"))
            /*
             * if(result.indexOf(',') > -1) substring = result.substring(0, result.indexOf(',')); else
             */
            substring = result.substring(0, nthIndexOf(result, ']', 2));
         else
            substring = result;
         dummyString = "[ MAIN ] - #WfDesc# - [ #WfStatus# ]";
         dummyString = dummyString.replace("#WfDesc#", listDesc.get(1));
         dummyString = dummyString.replace("#WfStatus#", listDesc.get(0));
         logger.info("GUI:#" + substring + "#");
         logger.info("Dummy:#" + dummyString + "#");
      }

      if (!dummyString.trim().equals(substring.trim()))
      {
         status = false;
         logger.info("Error: Value in GUI for Description: " + substring + " and from MicroService " + dummyString);
      }
      else
         logger.info("Pass : Value in GUI for Description: " + substring + " and from MicroService " + dummyString);

      return status;

   }

   public String getDate(int offset) throws AutomationBDDServiceException
   {
      dNow = new Date();
      ft = new SimpleDateFormat("dd");
      String dateVal = ft.format(dNow);
      int dateInt = Integer.parseInt(dateVal);
      if (dateInt >= 1 && dateInt <= offset)
      {
         dateInt = 28 - dateInt;
         if (productType.equals("Uverse"))
            driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_CREATEDATE_PRVBTN)))
               .click();
         else if (productType.equals("DTV") || productType.equals("WLL"))
            driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DTV_CREATEDATE_PRVBTN)))
               .click();
      }
      else
         dateInt = dateInt - offset;
      if ((dateInt + "").length() == 1)
         return "0" + dateInt + "";
      else
         return dateInt + "";
   }

   public void batchNavigate() throws AutomationBDDServiceException
   {
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SERVMANAGE_BATCHRESUB))).click();
   }

   public void batchSearchOptions(String prodType) throws AutomationBDDServiceException
   {

      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_PRODDROP))).click();
      if (prodType.equals("Uverse"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_PRODDROP_ATT))).click();
      else if (prodType.equals("DTV"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_PRODDROP_DTV))).click();
      else if (prodType.equals("WLL"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_PRODDROP_WLL))).click();

      if (prodType.equals("Uverse"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ATT_SERVICEDROP))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ATT_SERVICEDROP_HSIA))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_CREATEDATEFROM))).click();
      }
      else if (prodType.equals("DTV") || prodType.equals("WLL"))
      {
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
            GuiConstants.GUI_BATCH_ALLPROD_CREATEDATEFROM))));
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_CREATEDATEFROM))).click();
      }

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_BATCH_ALLPROD_CREATEDATEFROM_DATE).replace("#dateVal#", getDate(3)))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_CREATEDATEFROM_DATE).replace(
         "#dateVal#", getDate(3)))).click();

      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_SEARCHBTN))).click();
      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
      li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
      bbnmsOrder = li.get(0).getText();

      listDesc = validateStatusDescriptionMilestone(getOlDao().getWorkflowId(bbnmsOrder));

      ordSearchWfStatus = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_WFSTATUS)))
         .getText();
      ordSearchWfDesc = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_BATCH_ALLPROD_WFDESC)))
         .getText();
   }

   public void setNtiAndSubTransport(String nti, String subTransport) throws AutomationBDDServiceException, InterruptedException
   {
      if (nti.equals("none") && subTransport.equals("NextGen"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_NTI))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_NTI_NONE))).click();
         driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SUBTRANSPORTTYPE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SUBTRANSPORTTYPE_NEXTGEN))).click();
      }
      else if (nti.equals("none") && subTransport.equals("NA"))
      {
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_NTI))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_NTI_NONE))).click();
         driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SUBTRANSPORTTYPE))).click();
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WLL_SUBTRANSPORTTYPE_NA))).click();
      }
      else
      {
         driver.findElement(By.xpath(propUtil.getProperty("wllnti"))).click();
         driver.findElement(By.xpath(propUtil.getProperty("wllntinone"))).click();
         driver.findElement(By.xpath(propUtil.getProperty("wllsubtranstype"))).click();
         driver.findElement(By.xpath(propUtil.getProperty("wllsubtransportnone"))).click();
      }

       driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();

//      Thread.sleep(20000);
//      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
//      li = driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK)));
//      bbnmsOrder = li.get(0).getText();
   }

   public void selectSoFromDropdown()
   {
      driver.findElement(By.xpath(propUtil.getProperty("subsearch.productWll"))).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty("wllbbnmsorderinput"))));
   }

   public void clickGuiItem(String xpath)
   {
      driver.findElement(By.xpath(xpath)).click();
   }

   public boolean validateSubscriptionSummaryPage(String elementValue, String elementName)
   {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty("jumptospan"))));
      logger.info("element value -> " + driver.findElement(By.xpath(propUtil.getProperty(elementName))).getText());

      if (elementValue.equals("Blank"))
      {
         elementValue = "";
      }

      return (driver.findElement(By.xpath(propUtil.getProperty(elementName))).getText().equals(elementValue));

   }

   public boolean getSearchStatus()
   {
      String status = driver.findElement(By.xpath(propUtil.getProperty("wllordersearchsuccessbanner"))).getText();
      return (status.contains("Successfully retrieved"));
   }

   public void clickDataOrder() throws AutomationBDDServiceException
   {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         "wllordersearchrefreshbtn"))));
      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
      List<WebElement> recordCount = driver.findElements(By.id("orderBtn"));
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", recordCount.get(0));
      waitFor();
   }

   public void verifyServiceOrderPage()
   {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_WLL_ORDER_SUMMARY_PAGE))));
   }


   public String matchAttribute(String subTransportType)
   {
      return (driver.findElement(By.xpath(propUtil.getProperty("wllordersummary_subtransport_val"))).getText());
   }

   public void waitFor() throws AutomationBDDServiceException
   {
      /*
       * synchronized (getWebDriverWait()) { getWebDriverWait().wait(10); }
       */

      driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
   }

   public int nthIndexOf(String text, char needle, int n)
   {
      for (int i = 0; i < text.length(); i++)
      {
         if (text.charAt(i) == needle)
         {
            n--;
            if (n == 0)
            {
               return i + 1;
            }
         }
      }
      return -1;
   }

   public void quitGui() throws AutomationBDDServiceException
   {
      driver.quit();
   }

   public void clickDtvOrderLink() throws AutomationBDDServiceException, InterruptedException
   {

	   Thread.sleep(2000);
	      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLICKORDER))).click();
   }

   public void clickDtvBan() throws AutomationBDDServiceException
   {

      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_ORDERSEARCH_CLICKBAN))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLICKBAN))).click();
   }
   public void userClickWorkflow() throws AutomationBDDServiceException{
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_WORKFLOW))));
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW))).click();
	   
   }
   
   public void verifyConnectedPropert() throws AutomationBDDServiceException{
	   
	  String value =  getOlDao().getMduConnectedProperty(ban);
	  String value1 = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_MDU_CONNECTED_PROPERTY))).getText();
	  
	  System.out.println("value ="+value);
	  System.out.println("value1 ="+value1);
	  
	  
	  if(value1.equals(value)) {
		  System.out.println("Value matched");
	  }
	  else
		  System.out.println("value not matched");
	  
	   
   }
   public void searchDtvOrderByBan() throws AutomationBDDServiceException{
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_PRODDROP))));
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP))).click();


		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_DTV))).click();

		    
	   
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_BANFIELD_DTV))));
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERNUMBERINPUT))).sendKeys(ban);
	   
	   
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SUCCEED)))
	      .click();
	   
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN)))
	      .click();
	
	   
   }
   public boolean guiLoadValidate() throws AutomationBDDServiceException
	{
		if(driver.findElements(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).size()!=0)
			return true;
		else
			return false;
	}
	public boolean validateTopMsg() throws AutomationBDDServiceException
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
				GuiConstants.GUI_TOP_MESSAGE))));  	  
		String valueFromGui = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_TOP_MESSAGE))).getText();
		logger.info("element value from GUI -> " + valueFromGui);
		if(valueFromGui.contains("Successfully retrieved"))
			return true;
		else
			return false;
	}

	public boolean validateResultDisp() throws AutomationBDDServiceException
	{   
		if(driver.findElements(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK))).size()!=0)
			return true;
		else
			return false;
	}
	public boolean validatePageDetails(String page) throws AutomationBDDServiceException, InterruptedException
	{   
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
				GuiConstants.GUI_PAGE_TITLE))));  	  
		String valueFromGui = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_PAGE_TITLE))).getText();
		logger.info("element value from GUI -> " + valueFromGui);
		if (page.equals("order summary")){
			if(valueFromGui.equalsIgnoreCase("Order Details"))
				return true;
			else
				return false;
		}else{
			if(valueFromGui.equalsIgnoreCase("Subscription Details"))
				return true;
			else
				return false;
		}

	}
	public void clickBanLink() throws AutomationBDDServiceException
	{
		if (productType.equals("Uverse"))
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_ATT_BANLINK))).click();
		else 
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_OTHERS_BANLINK))).click();
	}
	
	public void clickDtvWoli() throws AutomationBDDServiceException{
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
			         GuiConstants.GUI_ORDERSEARCH_CLICKWOLI))));
			      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLICKWOLI))).click();

		  }
	
	public void subsSearchNavigate() throws AutomationBDDServiceException
   {
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).click();
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SERVMANAGE_SUBSEARCH))).click();
   }
	
	public void banEnterAndSearch() throws AutomationBDDServiceException
   {
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_SUBSEARCH_PRODDROPDOWN))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSEARCH_PRODDROPDOWN))).click();
      logger.info("productType ->" +productType);
	   if (productType.equals("Uverse"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_ATT))).click();
      else if (productType.equals("DTV"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_DTV))).click();
      else if (productType.equals("WLL"))
         driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_PRODDROP_WLL))).click();
	   
	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_SUBSEARCH_BANINPUT))));
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSEARCH_BANINPUT))).sendKeys(ban);
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();
   }
	
	public void clickPendingOrder() throws AutomationBDDServiceException
   {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
         GuiConstants.GUI_SUBSUMMARY_PENDINGORDER))));
      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSUMMARY_PENDINGORDER))).click();
      logger.info("Clicked on pending order");
   }
	
	public void getSubsBan(String prodType) throws AutomationBDDServiceException
   {
	   productType = prodType;
	   if(productType.equals("Uverse"))
	      ban=getOlDao().getSubsBan("ServiceOrder");
	   else if (productType.equals("DTV"))
	      ban=getOlDao().getSubsBan("DtvOrder");
	   else if (productType.equals("WLL"))
         ban=getOlDao().getSubsBan("WllOrder");
	   logger.info("BAN -> "+ban+" for product -> "+productType);
   }
	
	
	public void clickAdminPortSwap() throws AutomationBDDServiceException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ADMINSEARCH_CLICKADMIN))));
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLICKADMIN))).click();
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP))).click();

		      
	}
	public void enableBbexpressPortSwap() throws AutomationBDDServiceException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ADMINSEARCH_CLICKNO))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLICKNO))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_SUBMIT))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_CONFIRMYES))).click();
		
		
	}
	public boolean validateSuccessMessageEnable() throws AutomationBDDServiceException{
		String successMsg=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_VALIDATEMSG))).getText();
		String successMsgOnScreen = "BBExpress Port Swap Successfully Enabled";
		System.out.println(successMsg);
		System.out.println(successMsgOnScreen);
		if(successMsg.contains(successMsgOnScreen));
		System.out.println("Success msg matched");
		return true;
	}
	
	public void disableBbexpressPortSwap() throws AutomationBDDServiceException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ADMINSEARCH_CLICKYES))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLICKYES))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_INPUTFIELD))).sendKeys("test");
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_SUBMIT))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_CONFIRMYES))).click();

	}
	public boolean validateSuccessMessageDisable() throws AutomationBDDServiceException{
		String successMsg=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_VALIDATEMSG))).getText();
		String successMsgOnScreen = "BBExpress Port Swap Successfully Disabled";
		System.out.println(successMsg);
		System.out.println(successMsgOnScreen);
		if(successMsg.contains(successMsgOnScreen));
		System.out.println("Success msg matched");
		return true;
	}

	public WebDriver returnDriver() {
      return driver;
   }
	
	public void reassignClientScreen() throws AutomationBDDServiceException{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_REASSIGNSCREEN))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REASSIGNSCREEN))).click();
		
		
	}

	public void dropdownReceiver() throws AutomationBDDServiceException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_DROPDOWN_RECEIVER))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DROPDOWN_RECEIVER))).click();
		
		
	}

	public void clickDeactivateorReassign(String status) throws AutomationBDDServiceException, InterruptedException {
		//String statusOnGui=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLIENT_STATUS))).getText();
		
		if(status.equals("Active")) {
			Thread.sleep(2000);
		      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
		      
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_DEACTIVATE))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_DEACTIVATE))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))).click();
		}
		else if(status.equals("InActive")) {
			
			Thread.sleep(2000);
		      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
			         GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVERDROPDOWN))));
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVERDROPDOWN))).click();
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVER))).click();
			
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLICK_REASSIGN))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
			         GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))));
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))).click();
			
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
			         GuiConstants.GUI_ORDERSEARCH_REFRESH))));
			
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH))).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH))).click();
		}
		
	}

	public boolean inactiveStatus() throws AutomationBDDServiceException, InterruptedException {
		
		Thread.sleep(5000);
		String statusOnGui=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLIENT_STATUS))).getText();
		String status = "InActive";
		System.out.println(statusOnGui);
		System.out.println(status);
		if(statusOnGui.contains(status)) {
		System.out.println("Successfully Validated");
		return true;
		}
		else {
		return false;
		}
		
		
	}

	public void clickRefresh() throws AutomationBDDServiceException, InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_REFRESH))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH))).click();
		Thread.sleep(5000);
		//String statusOnGui=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_CLIENT_STATUS))).getText();
		
		
		
	}

	public void selectReceiver() throws AutomationBDDServiceException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVERDROPDOWN))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVERDROPDOWN))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_SELECT_RECEIVER))).click();
		
		
		
		
		
	}

	public void clickReassignButton() throws AutomationBDDServiceException, InterruptedException {
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLICK_REASSIGN))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))).click();
		
		Thread.sleep(8000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_REFRESH))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH))).click();
		
		
	}

	
	public void verifyStatusDescription() throws AutomationBDDServiceException {
		String status = getOlDao().getStatusDescription(ban);
		logger.info(status);
				driver.quit();
		
	}

	

	public void clickLatestOrder() throws AutomationBDDServiceException, InterruptedException{
		
		String orderno=getOlDao().getCvoipFailedOrderno();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_BANFIELD))));
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_BANFIELD))).sendKeys(orderno);
	   
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();
		
		Thread.sleep(5000);
		String successMsg=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_VALIDATEMSG))).getText();
		if(successMsg.contains("Successfully")) {
			logger.info("Valid search");
		}
		
	   
		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
		
		driver.findElement(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK))).click();
		
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY))));
		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY))).click();
		
		
		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_CVOIP_ACT_CHECKBOX))));
		
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_CVOIP_ACT_CHECKBOX))).click();
		
		
		
		
		
	}

	public void sortByFailed() throws AutomationBDDServiceException {
		
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_SORTBY_RESULT))).click();
		
	}

	public boolean verifyFailedActivity() throws AutomationBDDServiceException{
		
		((JavascriptExecutor) driver).executeScript("scroll(250, 450);");
		String result=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_RESULT_FAILED))).getText();
		String activityName=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_ACTIVITY_NAME))).getText();
		
		logger.info(result);
		logger.info(activityName);
		
		if(!result.isEmpty() && !activityName.isEmpty()) {
			return true;
		}
		else
			return false;
		
	}

	public boolean verifyInprogressActivity() throws AutomationBDDServiceException{
		// TODO Auto-generated method stub
		((JavascriptExecutor) driver).executeScript("scroll(250, 450);");
		String result=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_RESULT_INPROGRESS))).getText();
		String activityName=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDERDETAILS_HISTORY_ACTIVITY_NAME_INPROGRESS))).getText();
		
		logger.info(result);
		logger.info(activityName);
		
		if(!result.isEmpty() && !activityName.isEmpty()) {
			return true;
		}
		else
			return false;
		
	}

	public void searchInprogress() throws AutomationBDDServiceException, InterruptedException{
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_TYPE_DROPDOWN))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_TYPE_CHANGE))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDER_STATUS_DROPDOWN))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ORDER_STATUS_INPROGRESS))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ACTIVITY_DROPDOWN))).click();
//		
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ACTIVITY_HOLD_FOR_ACTIVATION))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ACTIVITY_STATUS_DROP))).click();
//		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
//		
//				driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ATT_ACTIVITYSTATUS_FAILED))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN)))
//	      .click();
//		Thread.sleep(2000);
//		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
//		
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CREATIONDATE))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CREATIONDATE))).click();
		
		String orderno = getOlDao().getInprogressOrder();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_BANFIELD))));
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_BANFIELD))).sendKeys(orderno);
	   String order = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_BANFIELD))).getAttribute("value");
	   logger.info(order);
//	   Select selectItem = new Select(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_BANFIELD))));
//		selectItem.getFirstSelectedOption().getText();
//		
//		logger.info(selectItem);
//		
//		Thread.sleep(2000);
		
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_SEARCHBTN))).click();
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("scroll(0, 250);");

		driver.findElement(By.id(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_ALLPROD_ORDERLINK))).click();
		
	}

	public boolean verifyReset() throws AutomationBDDServiceException, InterruptedException{
		// TODO Auto-generated method stub
		Thread.sleep(2000);
		((JavascriptExecutor) driver).executeScript("scroll(0, 500);");
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW_ACTIVITY_DROPDOWN))).click();
		String origStatus=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW_STATUS_BUTTON))).getText();
		logger.info(origStatus);
		
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW_STATUS_BUTTON))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW_STATUS_BUTTON_FAILED))).click();
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_RESET))).click();
//		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_REFRESH_BUTTON))).click();
//		Thread.sleep(2000);
//		((JavascriptExecutor) driver).executeScript("scroll(0, 850);");
		Thread.sleep(5000);
		String status2=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_WORKFLOW_STATUS_BUTTON))).getText();
		logger.info(status2);
		
		if(origStatus.equals(status2)) {
			logger.info("verified");
			return true;
		}
		else {
			return false;
		}
		
	}

	public void verifyHistoryTab() throws AutomationBDDServiceException, InterruptedException{
		// TODO Auto-generated method stub
		Thread.sleep(3000);
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_HISTORY_TAB))).click();
		String successMsg=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ADMINSEARCH_PORTSWAP_VALIDATEMSG))).getText();
		if(successMsg.contains("Successfully")) {
			logger.info("Valid search");
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_FULL_BUTTON))));
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_FULL_BUTTON))).click();
		
		Thread.sleep(2000);
		String noOpColumn = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_NO_OP_COLUMN))).getText();
		if(noOpColumn.equals("No op"))
		{
			logger.info("no op column is added");
		}
		
		Thread.sleep(2000);
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_HISTORY_ACTIVITY_DROPDOWN))).click();
		
		Thread.sleep(2000);
		String activityName=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_HISTORY_SELECT_ACTIVITYNAME))).getText();
		
		
		Thread.sleep(2000);
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_HISTORY_SELECT_ACTIVITY))).click();
		
		Thread.sleep(2000);
		String activityName1=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_HISTORY_SELECT_ACTIVITYCOLUMN))).getText();
		
		if(activityName.equals(activityName1)) {
			logger.info("Activity name matches");
		}
		
		
		
		
		
	}

	public void verifyOrderSummary() throws AutomationBDDServiceException{
		// TODO Auto-generated method stub
		String verifyBan= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_BAN))).getText();
		String verifyBan1=getOlDao().getban(ban);
		
		if(verifyBan.equals(verifyBan1)) {
			logger.info("Ban verified");
		}
		
		String verifyBbnmsOrderno= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_BBNMSORDERNO))).getText();
		String bbnmsOrderNo=getOlDao().getbbnmsOrderNo(ban);
		
		if(verifyBbnmsOrderno.equals(bbnmsOrderNo)) {
			logger.info("Bbnms order no verified");
		}
		
		String verifyOrderno= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_ORDERNO))).getText();
		String orderno=getOlDao().getOrderNo(ban);
		
		if(verifyOrderno.equals(orderno)) {
			logger.info("order no verified");
		}
		
		String verifyDueDate= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_DUEDATE))).getText();
		String dueDate=getOlDao().dueDate(ban);
		
		logger.info(verifyDueDate);
		logger.info(dueDate);
		
		String verifyCreationDate= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_CREATIONDATE))).getText();
		String creationDate = getOlDao().creationDate(ban);
		
		logger.info(verifyCreationDate);
		logger.info(creationDate);
		
		String verifyOrderStatus= driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSUMMARY_ORDERSTATUS))).getText();
		verifyOrderStatus=verifyOrderStatus.toUpperCase();
		String orderStatus=getOlDao().orderStatus(ban);
		
		if(verifyOrderStatus.equals(orderStatus)) {
			logger.info("Order Status verified");
		}
	}

	public void subscriptionSearch() throws AutomationBDDServiceException{
		// TODO Auto-generated method stub
		driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_START_HEADER_SERVMANAGE))).click();
	      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SEARCH))).click();
		
		
	}

	public void subscriptionSearchDtvBan() throws AutomationBDDServiceException {
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_SUBSCRIPTION_PRODDROP))));
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_PRODDROP))).click();


		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_PRODDROP_DTV))).click();
		      
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_BAN))).sendKeys(ban);
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SEARCHBUTTON))).click();
		      
		
		
		
	}

	public void verifySubscriptionInfo() throws AutomationBDDServiceException {
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_SUBSCRIPTION_SUBSCRIPTIONINFO))));
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SUBSCRIPTIONINFO))).click();
		      
		      
		      
		      String fourKService =driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTIONINFO_4KSERVICE))).getText();
		      String fourKServiceDb=getOlDao().subscriptionInfo4K(ban);
		      
		      String swmInd=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTIONINFO_SWMIND))).getText();
		      String swmIndDb=getOlDao().subscriptionInfoSwm(ban);
		      
		      String mrvInd=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTIONINFO_MRVIND))).getText();
		      String mrvIndDb=getOlDao().subscriptionInfoMrv(ban);
		      
		      String countyFips=driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTIONINFO_COUNTYFIPS))).getText();
		      String countyFipsDb=getOlDao().subscriptionInfoCounty(ban);
		      
		      logger.info(fourKService);
		      logger.info(fourKServiceDb);
		      logger.info(swmInd);
		      logger.info(swmIndDb);
		      logger.info(mrvInd);
		      logger.info(mrvIndDb);
		      logger.info(countyFips);
		      logger.info(countyFipsDb);
		      
		      
		      
	}

	public void closeoutWoliButton() throws AutomationBDDServiceException{
		// TODO Auto-generated method stub
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propUtil.getProperty(
		         GuiConstants.GUI_ORDERSEARCH_CLOSEOUTWOLI))));
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CLOSEOUTWOLI))).click();
		      driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_ORDERSEARCH_CONFIRMATIONBUTTON))).click();
		      
		      
		      String status = getOlDao().getCloseoutStatus(ban);
		      logger.info(status);
		
	}

	
	
	public void clickDetailsTab() throws InterruptedException {
	   Thread.sleep(20000);
      ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSUMMARY_DETAILS_TAB))).click();
	   
	}
	
	
	public void clickRawDataTab() throws InterruptedException, AutomationBDDServiceException {
	  driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSUMMARY_RAW_DATA_TAB))).click();
	  Thread.sleep(20000);
	  ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
	  driver.findElement(By.id(propUtil.getProperty(GuiConstants.GUI_SUBSUMMARY_RAW_DATA_LINK))).click();
	  validateTopMsg();
	 
	}
	
	public void enterBanInSubscriptionSearch() throws DAOServiceExeption{
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_BAN_INPUT))).sendKeys("998610023");
	 //  Thread.sleep(20000);
	   
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SEARCH_CLICK))).click();
	}
	
	public void validateSubscriptionSummaryPageByBanClick() throws InterruptedException {
	   Thread.sleep(20000);
	     ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_BAN_CLICK))).click();
	}
	public void clickOnSubscriptionInfoTab() throws InterruptedException {
	   Thread.sleep(20000);
	     ((JavascriptExecutor) driver).executeScript("scroll(0, 250);");
	     driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_INFO_TAB))).click();
	}
	public boolean validateServiceTypeInSubscriptionSearch() throws DAOServiceExeption, InterruptedException {
	   Thread.sleep(20000);
	   driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_SEARCH_RESULT_TAB))).click(); 
   String subscriptionBan = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_SUBSCRIPTION_BAN_CLICK))).getText();
     logger.info(subscriptionBan);
      String serviceType = getOlDao().getServiceTypefromServiceprocessinfo(subscriptionBan);
      logger.info(serviceType);
      if(subscriptionBan.equals(serviceType)) {
         return true;
      }else
         return false;
	}
}
