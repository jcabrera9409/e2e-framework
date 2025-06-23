package com.surrender;

public class Constants {
	
	private Constants() {}
	
	public static final String LOCAL_ENVIROMENT = "LOCAL";
	public static final String REMOTE_ENVIROMENT = "REMOTE";
	public static final String APPIUM_ENVIROMENT = "APPIUM";
	
	public static final String FIREFOX_BROWSER = "FIREFOX";
	public static final String CHROME_BROWSER = "CHROME";
	public static final String IE_BROWSER = "IE";
	
	public static final String ENVIROMENT = System.getProperty("environment") == null ? LOCAL_ENVIROMENT : System.getProperty("environment");
	public static final String PLATFORM = System.getProperty("platform") == null ? "windows_10_chrome_71" : System.getProperty("platform");
	public static final String SAUCE_PROXY = System.getProperty("sauceproxy") == null ? "" : System.getProperty("sauceproxy");
	public static final String SAUCE_ENDPOINT = System.getProperty("saucelabs.endpoint") == null ? "" : System.getProperty("saucelabs.endpoint");
	
	public static final String BROWSER = System.getProperty("browser") == null ? FIREFOX_BROWSER : System.getProperty("browser");
	
	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String CUCUMBER_REPORT_DIR = USER_DIR + "/target/cucumber-reports/";
	public static final String IMAGES_EVIDENCES_DIR = USER_DIR + "/target/evidences";
	
	public static final String OS_NAME = System.getProperty("os.name");
	
	public static final String APPIUM_DEVICE_NAME = System.getProperty("device");
	public static final String APPIUM_APP_PATH = System.getProperty("app") == null ? "" : System.getProperty("app");
	public static final String APPIUM_AUTOMATION_NAME = System.getProperty("automationname") == null ? "UIAutomator2" : System.getProperty("automationname");
	
	public static final String VBANK_HOST = System.getProperty("vbank.host") == null ? "localhost" : System.getProperty("vbank.host");
	public static final String VBANK_PORT = System.getProperty("vbank.port") == null ? "8080" : System.getProperty("vbank.port");
	public static final String VBANK_FOLDER = "stubs";
	
	public static final String DM_API_KEY = System.getProperty("dmapikey") == null ? "" : System.getProperty("dmapikey");
	public static final String DM_NAMESPACE = System.getProperty("dmnamespace") == null ? "" : System.getProperty("dmnamespace");
	public static final String DM_ENDPOINT = System.getProperty("datamanager.endpoint") == null ? "" : System.getProperty("datamanager.endpoint");
	
}
