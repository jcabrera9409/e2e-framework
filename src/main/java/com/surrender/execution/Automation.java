package com.surrender.execution;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.surrender.driver.DriverAbstract;
import com.surrender.Constants;
import com.surrender.Utils;

public class Automation {

	private static final Logger LOGGER = Logger.getLogger(Automation.class);
	
	protected Map<String, String> dictionary;
	
	public static LinkedList<Map<String, String>> loadEnviroments() throws Exception {
		String platform = Constants.PLATFORM;
		LinkedList<Map<String, String>> browsers = Utils.getNavigators(platform);
		return browsers;
	}
	
	public void setUpEnviroment() {
		DriverAbstract.setRemoteParameters(dictionary);
	}
	
	public void executeTest(Class<?> objClass) {
		JUnitCore junit = new JUnitCore();
		switch (Constants.ENVIROMENT) {
		case Constants.APPIUM_ENVIROMENT:
		case Constants.LOCAL_ENVIROMENT:
		case Constants.REMOTE_ENVIROMENT:
			junit.run(objClass);
			//junit.run(ParallelComputer.methods(), objClass);
			break;
		default:
			LOGGER.error("Enviroment not supported");
			break;
		}		
	}
	
	public void setCapabilities(DesiredCapabilities capabilities) {
		DriverAbstract.setUserCapabilities(capabilities);
	}
	
	@BeforeClass
	public static void beforeClass() {
		Utils.readCucumberOptions();
	}
	
	@After
	public void afterTest() throws IOException {
		Utils.saveCucumberReport();
	}
	
	@AfterClass
	public static void afterClass() {
		Utils.removeCucumberReportTemp();
	}
}
