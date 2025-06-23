package com.surrender.steps;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.surrender.driver.AppiumDriver;
import com.surrender.driver.DriverAbstract;
import com.surrender.driver.LocalDriver;
import com.surrender.driver.RemoteDriver;
import com.jayway.jsonpath.DocumentContext;
import com.surrender.Constants;
import com.surrender.SauceUtils;

import cucumber.api.Scenario;

public class StepsBase {
	
	private static final Logger LOGGER = Logger.getLogger(StepsBase.class);
	
	private StepsBase() { }
	private DriverAbstract driverObject;
	private Map<String, DocumentContext> contexts = new HashMap<String, DocumentContext>();
	
	private WebDriver driver;
	
	public static StepsBase getInstance() {
		return new StepsBase();
	}
	
	public WebDriver beforeTest(final Scenario scenario) {
		
		if(this.driver != null) { return this.driver; }
				
		//configuring driver by enviroment
		switch (Constants.ENVIROMENT.toUpperCase()) {
		case Constants.LOCAL_ENVIROMENT:
			this.driverObject = LocalDriver.getInstance();
			break;
		
		case Constants.REMOTE_ENVIROMENT:
			this.driverObject = RemoteDriver.getInstance();
			((RemoteDriver) this.driverObject).setJobName(scenario.getName());
			break;
		case Constants.APPIUM_ENVIROMENT:
			this.driverObject = AppiumDriver.getInstance();
			break;
		default:
			LOGGER.error("Enviroment not supported");
			break;
		}
		
		this.driver = this.driverObject.loadDriver();
		
		return this.driver;
	}
	
	public void afterTest(final Scenario scenario) throws JSONException, IOException {
		
		if(this.driverObject == null) return;
		
		switch (Constants.ENVIROMENT) {
		case Constants.LOCAL_ENVIROMENT:
		case Constants.APPIUM_ENVIROMENT:
			break;

		case Constants.REMOTE_ENVIROMENT:
			final String sessionId = ((RemoteDriver) this.driverObject).getSessionId();
			LOGGER.info("sessionId: " + sessionId);
			LOGGER.info("scenario-Id: " + scenario.getId());
			LOGGER.info("scenario-Name: " + scenario.getName());
			LOGGER.info("scenario-Status: " + scenario.getStatus());
			scenario.embed(
					MessageFormat.format("'{'\"sessionId\":\"{0}\", \"browserName\": \"{1}\", \"version\": \"{2}\"'}'",
							sessionId, ((RemoteWebDriver) driver).getCapabilities().getBrowserName(),
							((RemoteWebDriver) driver).getCapabilities().getVersion()).getBytes(),
					"application/json");
	        
			SauceUtils.UpdateResults(((RemoteDriver) driverObject).getUsername(), ((RemoteDriver) driverObject).getAccessKey(), !scenario.isFailed(), sessionId);
			LOGGER.info("SauceOnDemandSessionID="+ sessionId + "job-name="+ scenario.getName());
			break;
		default:
			LOGGER.error("Enviroment not supported");
			break;
		}
		
		this.driverObject.finishDriver();
	}
		
	public Object getDataByJsonPath(String key, String jsonPath) {
		DocumentContext jsonData = contexts.get(key);
		return jsonData.read(jsonPath);
	}
	
}
