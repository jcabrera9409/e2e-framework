package com.surrender;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Utils {

	private static final Logger LOGGER = Logger.getLogger(Utils.class);
	
	private Utils() {}
	
	public static LinkedList<Map<String, String>> getNavigators(String value) throws Exception {
	
		Map<String, Map<String, Object>> fileMap = castYAML();
		
		LinkedList<Map<String, String>> browsers = new LinkedList<Map<String, String>>();
		
		LOGGER.info("The tests will run in the following environments");
		
		for (String plataforma : fileMap.keySet()) {
			Map<String, String> caps = new HashMap<String, String>();
			for (String key : fileMap.get(plataforma).keySet()) {
				caps.put(key, fileMap.get(plataforma).get(key).toString());
			}
			
			if((!value.equals("ALL") && plataforma.equals(value)) || (value.equals("ALL"))) {
				browsers.add(caps);
				LOGGER.info(plataforma);
			}
		}

		if(browsers.size() == 0 && Constants.ENVIROMENT.equals(Constants.LOCAL_ENVIROMENT)) {
			Map<String, String> caps = new HashMap<String, String>();
			caps.put("platform", Constants.OS_NAME);
			caps.put("browserName", Constants.BROWSER);
			caps.put("browserVersion", "");
			browsers.add(caps);
			LOGGER.info(Constants.ENVIROMENT + " -- " + Constants.BROWSER);
		}
		
		return browsers;
	}
	
	private static Map<String, Map<String, Object>> castYAML() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
	    Map<String, Map<String, Object>> fileMap = new HashMap<String, Map<String,Object>>();
	    
	    LOGGER.info("Reading platforms.yml file");
	    
		try {
			fileMap = mapper.readValue(
			    new File("src/test/resources/cucumbersauce/platforms.yml"), 
			    new TypeReference<Map<String, Map<String, Object>>>(){});
		} catch (JsonParseException e) {
			LOGGER.error(parseStackTrace(e));
		} catch (JsonMappingException e) {
			LOGGER.error(parseStackTrace(e));
		} catch (IOException e) {
			LOGGER.error(parseStackTrace(e));
		}
	    
	    return fileMap;
	}
	
	public static void saveCucumberReport() {
		
		LOGGER.info("Saving JSON file report");
		
		String randomName = "cucumber_" + UUID.randomUUID().toString() + ".json";
		
		File source = new File(Constants.CUCUMBER_REPORT_DIR + "Cucumber.json");
		File dest = new File(Constants.CUCUMBER_REPORT_DIR + randomName);
		
		LOGGER.info("Creating JSON file with the name " + randomName);
		
		if(source.exists()) {
			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				LOGGER.error(parseStackTrace(e));
			}
		}
		else {
			LOGGER.info("The temporary JSON file not exists");
		}
	}
	
	public static void removeCucumberReportTemp() {
		File source = new File(Constants.CUCUMBER_REPORT_DIR + "Cucumber.json");
		if(source.exists()) {
			if(!source.delete()) {
				LOGGER.info("The temporary JSON file could not be deleted");
			}
		}
	}
	
	public static void readCucumberOptions() {
		String tags = System.getProperty("cucumber.tags");
		if(tags != null && !tags.isEmpty()) {
			System.setProperty("cucumber.options", "--tags '" + tags + "'");
			LOGGER.info("Checking the cucumber.options property: " + System.getProperty("cucumber.options"));
		}
	}
	
	public static String parseStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
}
