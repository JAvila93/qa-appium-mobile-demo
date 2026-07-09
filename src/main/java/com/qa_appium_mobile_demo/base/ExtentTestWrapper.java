package com.qa_appium_mobile_demo.base;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestWrapper {

	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	private static final ExtentReports report = ExtentWrapper.getReport();
	
	public static synchronized ExtentTest startTest(String testName) {
		ExtentTest test = report.startTest(testName);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		// log created test
		return test;
	}
	
	public static synchronized void endTest() {
		//String testName = getTest().getTest().getName();
		// log ending test
		report.endTest(getTest());
		report.flush();
		// log ended test
	}
	
	public static synchronized ExtentTest getTest() {
		return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}
	
	public static synchronized void log(LogStatus status, String details) {
		// log add to test
		getTest().log(status, details);
	}
	
}
