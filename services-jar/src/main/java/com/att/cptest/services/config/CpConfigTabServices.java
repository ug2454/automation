package com.att.cptest.services.config;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.GuiConstants;
import com.att.bbnmstest.services.util.OrderObjectMapper;
import com.att.bbnmstest.services.util.PropUtil;
import com.att.bbnmstest.services.util.QueryConstants;
import com.att.cptest.services.CpGuiServices;
import com.att.cptest.services.dao.CpDataSource;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import 

@Component
@Qualifier("CpConfigTabServices")
public class CpConfigTabServices extends CpGuiServices{
	@Autowired
		private PropUtil propUtil;
	@Autowired
		private CpDataSource cpDb;
//	private ExtentHtmlReporter reporter;
//	private ExtentReports extent;
//	private ExtentTest loggerExtent;
	private int oldWidth;
	private WebElement ele=null;

	private final static Logger logger = Logger.getLogger(CpConfigTabServices.class);

	public CpConfigTabServices(@Value("${" + EnvConstants.BBNMS_LIGHTSPEED_GUIURL + "}") String url, @Value("${"
			+ EnvConstants.BBNMS_LIGHTSPEED_GUIURL_USER + "}") String user, @Value("${"
					+ EnvConstants.BBNMS_LIGHTSPEED_GUIURL_PASS + "}") String pass) throws AutomationBDDServiceException {
		super(url, user, pass);
	}
	
	
	public void setLogname(String logName) throws AutomationBDDServiceException, IOException
	{
		/*reporter=new ExtentHtmlReporter("./Reports/"+logName+".html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		loggerExtent=extent.createTest(logName);*/
	}
	
	public void navigateConfig() throws AutomationBDDServiceException, IOException
	{
		
	    
        // log method will add logs in report and provide the log steps which will come in report
		/*loggerExtent.log(Status.INFO, "Login to amazon");
   
		loggerExtent.log(Status.PASS, "Title verified");
		extent.flush();
	
    // You can call createTest method multiple times depends on your requirement
    // In our case we are calling twice which will add 2 testcases in our report
		ExtentTest logger2=extent.createTest("Logoff Test");

		logger2.log(Status.FAIL, "Title verified");

		logger2.fail("Failed because of some issues", MediaEntityBuilder.createScreenCaptureFromPath("/Users/mukeshotwani/Desktop/logo.jpg").build());

		logger2.pass("Failed because of some issues", MediaEntityBuilder.createScreenCaptureFromPath("/Users/mukeshotwani/Desktop/logo.jpg").build());

    // This will add another test in report
		extent.flush();*/
		//driver = getDriver();   
		System.out.println("in navigate to config ");
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG))));
	}

	public void navigateToSet() throws AutomationBDDServiceException
	{

		System.out.println("in navigate to set");
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG))),"isClickable", 60);
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG))));
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG_SET))));
	}

	public void addRow() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDROW))),"isClickable", 60);
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDROW))));
	}

	public void addSanitySet() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_BU))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_BU))),"IEFS");

		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_REGION))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_REGION))),"SW");

		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_AOI))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_AOI))),"AR_KS_MO_OK_MOKAE_E");

		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_AG))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_AG))),"MO_SPRINGFIELD_E");

		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_ROUTING))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_ROUTING))),"ALL");

		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_CLOCK))));
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_CLOCK_FWLL))));

		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_DAY))));
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_DAY_MON))));

		sendKeys(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_SETPERCENT))),"10");
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_ADDRSET_ADDBTN))));
	}

	public void navigateToAutoRes() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG))),"isClickable", 60);
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG))));
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_CONFIG_GENCONFIG_AUTORES))));
	}
	public void addSanityAutoRes() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_BU))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_BU))),"IEFS");
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_REGION))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_REGION))),"SW");
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_AOI))),"isClickable", 60);
		selectOptionByText(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_AOI))),"AR_KS_MO_OK_MOKAE_E");
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_AUTORES_ADDAUTO_ADDBTN))));
	}
	
	public boolean checkGuiMsg(String message) throws AutomationBDDServiceException
	{
		//explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_GUIMSG))),"isVisible", 120);
		explicitWaitWithLocator(propUtil.getProperty(GuiConstants.CP_GUIMSG),"isPresent", 120);
		String msg = getTextMsg(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_GUIMSG))));
		if(msg.contains(message))
		{
			logger.info("Pass. Message -->"+msg+ " ,is validated correctly");
			return true;
		}
		else {
			logger.error("Fail. Message -->"+msg+ " from GUI which is not correct");
			return false;
		}
	}
	
	public void delSetRow() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_FILTER_AOI))),"isClickable", 60);
		sendKeys(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_FILTER_AOI))),"AR_KS_MO_OK_MOKAE_E");
		sendKeys(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_FILTER_AG))),"MO_SPRINGFIELD_E");
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_SELECTALL))));
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_DELETEBTN))));
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_DELETEBTN_OK))),"isClickable", 60);
		clickJs(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SET_DELETE_DELETEBTN_OK))));
	}
	
	public void navigateLoanMatrix() throws AutomationBDDServiceException, IOException
	{
		driver = getDriver();
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN))));
	}
	
	public void navigateLoanGraph() throws AutomationBDDServiceException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN_LOANGRAPH))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN_LOANGRAPH))));
	}
	
	public void updateColumnWidth(String pageOption) throws AutomationBDDServiceException, IOException, InterruptedException
	{
		Thread.sleep(10000);
		WebElement resize = null;
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN_BU))),"isClickable", 60);
		ele = driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN_BU)));
		oldWidth = ele.getSize().getWidth();
		if(pageOption.equalsIgnoreCase("loan constraints")) 
			resize =  driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOAN_BU_GRID)));
		else
			resize =  driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LOANGRAPH_BU_GRID)));
		Actions action = new Actions(driver);
		action.moveToElement(resize);
		action.doubleClick().perform();
	}
	
	
	public boolean validateColumnWidth() throws AutomationBDDServiceException, IOException, InterruptedException
	{
		int newWidth = ele.getSize().getWidth();
		if(newWidth<oldWidth) {
			logger.info("Width of column was reduced from old width -->>"+oldWidth+" to new width --> "+newWidth);
			return true;
		}
		else {
			logger.error("FAILED. Width of column was not reduced from old width -->>"+oldWidth+" to new width --> "+newWidth);
			return false;
		}
	}
	
	public boolean vlidateSdndMinutes() throws AutomationBDDServiceException, IOException, InterruptedException
	{
		String uiValue = getTextMsg(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND_UNPLANNEDHOURS))));
		String expValue = sdndUnplannedMinstoHours(cpDb.getSdndUnavailableMinutes(propUtil.getProperty(EnvConstants.CP_SDND_RA)));
		logger.info("Value calculated based on DB data: "+expValue);
		if(uiValue.equals(expValue)) {
			logger.info("PASSED...Value calculated based on DB data: "+expValue+" is same as ui Value :"+uiValue);
			return true;
		}
		else
		{
			logger.error("FAILED...Value calculated based on DB data: "+expValue+" is not same as ui Value :"+uiValue);
			return false;
		}
	}
	
	
	public void navigateSdndAg() throws AutomationBDDServiceException, IOException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND))));
		
	}
	
	public void navigateHierarchy() throws AutomationBDDServiceException, IOException, InterruptedException
	{
		ArrayList<String> result = cpDb.getHierarchy(propUtil.getProperty(EnvConstants.CP_SDND_RA));
		if(result.size()==0)
			logger.error("Hierarchy is not present for the RA :"+propUtil.getProperty(EnvConstants.CP_SDND_RA));
		else {
			for (int i = 0; i<result.size()-1;i++)
			{
				click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND_HIERARCHY).replace("?1", result.get(i)))));
			}
			Thread.sleep(20000);
			click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND_HIERARCHY).replace("?1", result.get(3)))));
			logger.info("successfully navigated to AG");
		}
		
	}
	
	public boolean unprodHoursDisplayed() throws AutomationBDDServiceException, IOException, InterruptedException
	{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_SDND_UNPLANNEDHOURS))),"isClickable", 60);
		return true;
	}


	public void editLoanSdnd() {
		// TODO Auto-generated method stub
		
		
	}
	public void navigateLtm() throws AutomationBDDServiceException, IOException, InterruptedException{
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM))));
	}
	public void clickAgLtm() throws AutomationBDDServiceException, IOException, InterruptedException{
		
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_IEFS_DROPDOWN))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_FAV))));
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_IEFS_DROPDOWN)))); //BU
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_E_MIDWEST_DROPDOWN)))); //Region
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_E_MI_MICHIGAN_DROPDOWN))));  //AOI
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_E_MI_DOWNRIVER_CLICK))));   //AG
	}


	public void navigateLtmDemandDuration() throws AutomationBDDServiceException, IOException, InterruptedException{
		
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_DEMAND_SUMMARY_DROPDOWN))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_DEMAND_SUMMARY_DROPDOWN))));
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.CP_LTM_DEMAND_DURATION_CLICK))));
		
	}


	public void clickDepartDate() throws AutomationBDDServiceException {
		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_DEPARTURE_POPUP))),"isClickable", 60);
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_DEPARTURE_POPUP))));
		List<String> li = new ArrayList<String>();

	}


	public void selectRandomDate(String month,String date) throws AutomationBDDServiceException, InterruptedException {
		


		
//		String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
//		
//		click(driver.findElement(By.xpath(query)));
		
		
		
		//click arrow till getText of month xpath contains specified month
		
		
//		while(monthXpath.contains(month)) {
//			logger.info("Month Found");
//			String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
//			
//			click(driver.findElement(By.xpath(query)));
//		}
//		 {
//			click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_NEXT_MONTH))));
//			String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
//			
//			click(driver.findElement(By.xpath(query)));
//		}
//		
		
		
		
		
		List<String> monthXpath = new ArrayList<String>();
		Thread.sleep(12000);
//		explicitWait(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_POPUP_CLOSE))),"isVisible", 60);
//		
//		Thread.sleep(3000);
//		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_POPUP_CLOSE))));
		
		
		
		driver.switchTo().frame(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_POPUP_FRAME))));
		
		click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_POPUP_CLOSE))));
		
		
		
		
		for(int i=0;i<11;i++) {
		 monthXpath.add(getTextMsg(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_MONTH_CAPTION)))));
		 logger.info(monthXpath);
		 if(!(monthXpath.get(i).contains(month))) {
			 click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_NEXT_MONTH))));
		 }
//		 else {
//			 String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
//				
//				click(driver.findElement(By.xpath(query)));
//		 }
				
		}
		
//		String getMonth = getTextMsg(driver.findElement(By.xpath(monthXpath)));
//		 while(!(monthXpath[i].contains(month))){
//				logger.info(monthXpath[i]);
//					Thread.sleep(2000);
//					click(driver.findElement(By.xpath(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_NEXT_MONTH))));
//					if(monthXpath[i].contains(month))
//						break;
//
//			}
		
		
			
		
		String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
		
		click(driver.findElement(By.xpath(query)));
			
		
			
		
		
//		String query = StringUtils.replaceEach(propUtil.getProperty(GuiConstants.MAKE_MY_TRIP_SELECT_DATE_DYNAMIC),new String[]{ "?1","?2" }, new String[]{ month,date });
		
//		click(driver.findElement(By.xpath(query)));
		
		
	
		
		

		
	
	

}

}
