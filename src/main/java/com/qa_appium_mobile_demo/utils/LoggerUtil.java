package com.qa_appium_mobile_demo.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Reporter;

import com.qa_appium_mobile_demo.base.BaseTest;

public class LoggerUtil {

	private static LoggerUtil instance;
	static Map<Integer, File> loggerMap = new HashMap<Integer, File>();
	
	private LoggerUtil() {};
	
	public static synchronized LoggerUtil getInstance() {
		if (instance == null) {
			instance = new LoggerUtil();
		}
		return instance;
	}
	
	public static synchronized File createLogsFile(String timeTaken, String methodAndClass) {
		String fileName;
		
		fileName = "test-output-" + BaseTest.getSuiteId() + "/logs/"+timeTaken+"_"+methodAndClass+".txt";
		File file =  new File(fileName);
		loggerMap.put((int)(long) (Thread.currentThread().getId()), file);
		return file;
	}
	
	public static synchronized void appendLogToFile(File file, String message) throws IOException {
		FileUtils.writeStringToFile(getLogFile(), message, "UTF-8", true);
	}
	
	public static synchronized File getLogFile() {
		return loggerMap.get((int) (long) (Thread.currentThread().getId()));
	}
	
	public static synchronized List<String> extractLogFile() throws IOException {
		return FileUtils.readLines(getLogFile(), "UTF-8");
	}
	
	public static synchronized void logToFile(String message) {
		try {
			appendLogToFile(getLogFile(), TestUtilities.getDateTimeThread() + message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Reporter.log(TestUtilities.getDateTimeThread() + message, true);
	}
}
