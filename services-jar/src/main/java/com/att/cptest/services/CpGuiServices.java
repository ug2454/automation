package com.att.cptest.services;

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
import com.att.cptest.services.util.CpBrowserFns;

//@Component
//@Qualifier("CpGuiServices")
public class CpGuiServices extends CpBrowserFns
{

   private final String guiUrl;
   private final String guiPass;
   private final String guiUser;
   private Date dNow;
   private SimpleDateFormat ft;
   private WebDriverWait wait;
   private List<String> listDesc;
   private List<String> dbValues;
   private String dbWfStatus, dbWfDesc;
   private String productType;
   private String ban = "506598517";

   //private WebDriver driver;
   private List<WebElement> li;
   private ChromeOptions options;
   private FirefoxOptions fireOptions;

   private final static Logger logger = Logger.getLogger(CpGuiServices.class);

   @Autowired
   private PropUtil propUtil;
   
  // @Autowired
  // private CpBaseServices cpBaseServices;

   public CpGuiServices(String url, String user, String pass) throws AutomationBDDServiceException
   {
      this.guiUrl = url;
      this.guiPass = pass;
      this.guiUser = user;
      //driver = getDriver();
   }
   
   
   public WebDriver createDriver() {
		if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKBROWSER).equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHROMEBROWSERPATH));
			if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_HEADLESSENABLED).equalsIgnoreCase("Yes")) {
				options = new ChromeOptions();
				options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
						"--ignore-certificate-errors");
				driver = new ChromeDriver(options);
			} else
				driver = new ChromeDriver();
		} else if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_CHECKBROWSER).equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_FIREFOXBROWSERPATH));
			if (propUtil.getProperty(EnvConstants.BBNMS_LIGHTSPEED_GUI_HEADLESSENABLED).equalsIgnoreCase("Yes")) {
				fireOptions = new FirefoxOptions();
				fireOptions.setHeadless(true);
				driver = new FirefoxDriver(fireOptions);
			} else
				driver = new FirefoxDriver();
		}
		setDriver(driver);
		return driver;	
	}
   public void loginGui() throws AutomationBDDServiceException {
		wait = new WebDriverWait(driver, 60);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(guiUrl);
		logger.info("GUI URL -> " + guiUrl);
		driver.manage().window().maximize();
		
//		  sendKeys(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGIN_UNAME))),guiUser);
//		  sendKeys(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants. GUI_LOGIN_PASS))), EncryptDecryptUtil.decryptBySysProp(guiPass,
//		  EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));
//		  click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGIN_SUBMIT))));
//		  click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GUI_LOGINSUCCESS_OK))));
	  
		 
//		explicitWait((driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_USERNAME)))), "isClickable",
//				60);
//		sendKeys((driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_USERNAME)))), guiUser);
//
//		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_NEXT))));
//
//		explicitWait((driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_PASSWORD)))), "isClickable",
//				60);
//		sendKeys((driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_PASSWORD)))),
//				EncryptDecryptUtil.decryptBySysProp(guiPass, EnvConstants.BDD_AUTOMATION_ENCRYPT_DECRYPT_KEY));
//		
//		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.GMAIL_PASSWORD_NEXT))));

	}

   public void navigateCp() throws AutomationBDDServiceException
   {
	   explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_ICON))),"isClickable", 60);
	   click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_ICON))));
   }
   public void quitCpGui() throws AutomationBDDServiceException
   {
	   driver.quit();
   }
   public WebDriver getLastDriver() {
		return driver;
	}

}
