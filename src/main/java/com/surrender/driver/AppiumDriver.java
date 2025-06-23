package com.surrender.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.surrender.Constants;
import com.surrender.Utils;

import io.appium.java_client.android.AndroidDriver;

public class AppiumDriver extends DriverAbstract {

	private static final Logger LOGGER = Logger.getLogger(AppiumDriver.class);
	
	private final static String URL = "http://localhost:4723/wd/hub";
	
	private AppiumDriver() { }
	
	public static AppiumDriver getInstance() {
		return new AppiumDriver();
	}
	
	@Override
	public WebDriver loadDriver() {
		try {
			
			LOGGER.info("Loading Appium driver instace");
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", Constants.APPIUM_DEVICE_NAME);
			capabilities.setCapability("app", Constants.APPIUM_APP_PATH);
			capabilities.setCapability("autoGrantPermissions", "true");
			capabilities.setCapability("automationName", Constants.APPIUM_AUTOMATION_NAME);
			capabilities.setCapability("newCommandTimeout", 180);
			
			this.driver = new AndroidDriver<>(new URL(URL), capabilities);
			this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						
		} catch (MalformedURLException e) {
			LOGGER.error(Utils.parseStackTrace(e));
		}
		
		return this.driver;
	}

}
