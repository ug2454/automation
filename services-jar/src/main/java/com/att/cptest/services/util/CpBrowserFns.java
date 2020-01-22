package com.att.cptest.services.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.att.cptest.services.CpBaseServices;

public class CpBrowserFns extends CpCalculations{

	public WebDriver  driver;
	private WebDriverWait wait;
	private Select select;
	
	public CpBrowserFns()
	{
		/*super();
		driver = getDriver();*/
		
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	
	public void click(WebElement ele) {
		ele.click();
	}
	public void sendKeys(WebElement ele,String keys) {
		ele.sendKeys(keys);
	}
	
	public void explicitWait(WebElement ele,String condition,int timeout)
	{
		wait = new WebDriverWait(driver, timeout);
		if(condition.equalsIgnoreCase("isClickable"))
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		else if(condition.equalsIgnoreCase("isVisible"))
			wait.until(ExpectedConditions.visibilityOf(ele));
	}
	public void explicitWaitWithLocator(String locator,String condition,int timeout)
	{
		wait = new WebDriverWait(driver, timeout);
		if(condition.equalsIgnoreCase("isPresent"))
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}
	
	public void clickJs(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
	}
	
	
	public void selectOptionByText(WebElement ele,String option) {
		select = new Select(ele);
		select.selectByVisibleText(option);
	}
	
	public String getTextMsg(WebElement ele) {
		return ele.getText().trim();
	}
	
	public void executeJs(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('unselectable'); return arguments[0];", ele);
	}
	
	
}
