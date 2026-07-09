package com.qa_appium_mobile_demo.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.qa_appium_mobile_demo.utils.LoggerUtil;

public class ExtentWrapper {
	
	private static ExtentWrapper instance;
	protected static ExtentReports report;
	protected ExtentTest test;
	protected static String filePath = System.getProperty("user.dir") + "/test-output-" + BaseTest.getSuiteId() + "/";
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	static Map<Integer, String> extentReportMap = new HashMap<Integer, String>();

	private ExtentWrapper() {}
	
	public static synchronized ExtentWrapper getInstance(String environment, String suiteName) {
		if (instance == null) {
			instance = new ExtentWrapper();
			report = getReportInstance(environment, suiteName);
		}
		return instance;
	}
	
	private static synchronized ExtentReports getReportInstance(String environment, String suiteName) {
		if (report == null) {
			createInstance(environment, suiteName);
		}
		
		return report;
	}
	
	private static synchronized ExtentReports createInstance(String environment, String suiteName) {
		String fileName = suiteName + ".html";
		report = new ExtentReports (filePath+fileName, false);
		report
		.addSystemInfo("Host Name", "Automation")
		.addSystemInfo("Environment", environment)
		.addSystemInfo("User Name", System.getProperty("user.name"));
		report.loadConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		extentReportMap.put((int) (long) (Thread.currentThread().getId()), fileName);
		return report;
	}
	
	public synchronized void endReport() {
		//String reportId = report.getReportId().toString();
		try {
			report.close();
		} catch (ExtentTestInterruptedException e) {
			LoggerUtil.logToFile("Report was not closed. Error: " + e.getLocalizedMessage());
		}
		// log closed report
	}
	
	public static synchronized ExtentReports getReport() {
		return report;
	}
	
	public synchronized String getFilePath() {
		String fileName = extentReportMap.get((int) (long) (Thread.currentThread().getId())).toString();
		return filePath+fileName;
	}
	
	public synchronized String getStatus(int i) {
		switch (i) {
		case 1:
			return "SUCCESS";
		case 2:
			return "FAILED";
		case 3:
			return "SKIP";
		case -1: 
			return "SKIP";
		default:
			return "UNKNOWN";
		}
	}
}
