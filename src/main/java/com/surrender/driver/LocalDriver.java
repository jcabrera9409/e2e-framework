package com.surrender.driver;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.surrender.Constants;

public class LocalDriver extends DriverAbstract {

	private static final Logger LOGGER = Logger.getLogger(LocalDriver.class);
	
	private static final String EXTENSION = Constants.OS_NAME.toLowerCase().contains("win") ? ".exe" : "";
	
	private static final String FIREFOXDRIVERPATH = "src/test/resources/webdrivers/geckodriver" + EXTENSION;
	private static final String CHROMEDRIVERPATH = "src/test/resources/webdrivers/chromedriver" + EXTENSION;
	private static final String IEDRIVERPATH = "src/test/resources/webdrivers/IEDriverServer" + EXTENSION;
	
	private LocalDriver() { }
	
	public static LocalDriver getInstance() {
		return new LocalDriver();
	}
	
	@Override
	public WebDriver loadDriver() {
		
		switch (Constants.BROWSER.toUpperCase()) {
		case Constants.FIREFOX_BROWSER:
			
			LOGGER.info("Loading Firefox driver instance");
			
			System.setProperty("webdriver.gecko.driver", FIREFOXDRIVERPATH);
			if(_userCapabilities != null) {
				this.driver = new FirefoxDriver(new FirefoxOptions(_userCapabilities));
			}
			else {
				this.driver = new FirefoxDriver();	
			}
			break;

		case Constants.CHROME_BROWSER:
			
			LOGGER.info("Loading Chrome driver instance");
			
			System.setProperty("webdriver.chrome.driver", CHROMEDRIVERPATH);
			if(_userCapabilities != null) {
				ChromeOptions opts = new ChromeOptions();
				opts.merge(_userCapabilities);
				this.driver = new ChromeDriver(opts);	
			}
			else {
				this.driver = new ChromeDriver();
			}
			break;
			
		case Constants.IE_BROWSER:
			LOGGER.info("Loading IE driver instance");
			
			System.setProperty("webdriver.ie.driver", IEDRIVERPATH);
			if(_userCapabilities != null) {
				InternetExplorerOptions opts = new InternetExplorerOptions();
				opts.merge(_userCapabilities);
				this.driver = new InternetExplorerDriver(opts);
			}
			else {
				this.driver = new InternetExplorerDriver();
			}
			break;
			
		default:
			LOGGER.error("Browser not supported. You must enter a valid browser in the -Dbrowser property.");
			break;
		}
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		return driver;
	}

}
