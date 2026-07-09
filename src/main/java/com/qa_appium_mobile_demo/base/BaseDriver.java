package com.qa_appium_mobile_demo.base;

import java.time.Duration;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.FluentWait;

import com.qa_appium_mobile_demo.utils.TestUtilities;

public class BaseDriver extends SeleniumWrapper {

	private JSONObject driverConf;
	
	public BaseDriver() {
		PropertyConfigurator.getInstance();
		driverConf = PropertyConfigurator.getDriverConfigurator();
		driver = newBrowserDriver();
	}
	
	public BaseDriver(String driverType) {
		this.driverType = driverType;
		TestUtilities.log("driverType="+this.driverType);
		PropertyConfigurator.getInstance();
		driverConf = PropertyConfigurator.getDriverConfigurator();
		driver = newBrowserDriver();
		
		TestUtilities.log("New webdriver is: "+getWebDriver());
	}
	
	@SuppressWarnings("null")
	public synchronized WebDriver newBrowserDriver() {
		WebDriver m_WebDriver = null;
		
		String os = System.getProperty("os.name").toLowerCase();
		String downloadFilePath = System.getProperty("user.dir");
		
		if (os.contains("windows")) {
			downloadFilePath = "\\downloads";
		} else {
			downloadFilePath = "/downloads";
		}
		
		HashMap<String, Object> browserPrefs = new HashMap<String, Object>();
		browserPrefs.put("profile.default_content_settings.popups", 0);
		browserPrefs.put("download.default_directory", downloadFilePath);
		browserPrefs.put("browser.show_hub_popup_on_download_start", false);
		
		if (this.driverType.equals("chrome") || this.driverType.equals("chromeHeadless")) {
			System.setProperty("webdriver.chrome.driver", driverConf.get("chromeDriverPath").toString());
			ChromeOptions chromeOptions = new ChromeOptions();
			
			if (this.driverType.equals("chromeHeadless")) {
				chromeOptions.addArguments(driverConf.get("headless").toString());
			}
			
			JSONObject arguments = (JSONObject) driverConf.get("arguments");
			
			if (this.driverType.equals("chrome")) {
				arguments.remove("windowSize");
				arguments.remove("certificate");
				arguments.remove("audio");
			}
			
			arguments.remove("scaleFactor");
			arguments.remove("debugPort");
			
			for (Object key : arguments.keySet()) {
				String keyStr = (String) key;
				Object value = arguments.get(keyStr);
				
				chromeOptions.addArguments(value.toString());
			}
			
			//chromeOptions.addExtensions(new File(driverConf.get("extensionPath").toString()));
			chromeOptions.setExperimentalOption("prefs", browserPrefs);
			
			m_WebDriver = new ChromeDriver(chromeOptions);
		} else if (this.driverType.equals("edge") || this.driverType.equals("edgeHeadless")) {
			System.setProperty("webdriver.edge.driver", driverConf.get("edgeDriverPath").toString());
			EdgeOptions edgeOptions = new EdgeOptions();
			
			if (this.driverType.equals("edgeHeadless")) {
				edgeOptions.addArguments(driverConf.get("headless").toString());
			}
			
			JSONObject arguments = (JSONObject) driverConf.get("arguments");
			
			if (this.driverType.equals("edge")) {
				arguments.remove("windowSize");
				arguments.remove("certificate");
				arguments.remove("audio");
			}
			
			for (Object key : arguments.keySet()) {
				String keyStr = (String) key;
				Object value = arguments.get(keyStr);
				
				edgeOptions.addArguments(value.toString());
			}
			
			//edgeOptions.addExtensions(new File(driverConf.get("extensionPath").toString()));
			edgeOptions.setExperimentalOption("prefs", browserPrefs);
			
			m_WebDriver = new EdgeDriver(edgeOptions);
		}
		
		m_WebDriver.manage().window().maximize();
		m_WebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		wait = new FluentWait<WebDriver>(m_WebDriver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(StaleElementReferenceException.class);
		return m_WebDriver;
	}
}
