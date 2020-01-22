package com.att.cptest.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.bbnmstest.services.exception.AutomationBDDServiceException;
import com.att.bbnmstest.services.util.EnvConstants;
import com.att.bbnmstest.services.util.PropUtil;

@Component
@Qualifier("CpBaseServices")
public class CpBaseServices {

	private WebDriver driver;
	private ChromeOptions options;
	private FirefoxOptions fireOptions;

	@Autowired
	private PropUtil propUtil;

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		loginGlobalLogon();
		return driver;
	}

	public CpBaseServices() {
		// comment

	}

	public void loginGlobalLogon() {
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
		
	}
	
	 
}
