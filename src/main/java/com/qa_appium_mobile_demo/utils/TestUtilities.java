package com.qa_appium_mobile_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Reporter;

public class TestUtilities {

	public static String getTimeMmDdEeHhMmSs() {
		String stringPattern = "MM-dd EEE HH.mm.ss";
		SimpleDateFormat sdf = new SimpleDateFormat(stringPattern);
		return sdf.format(new Date());
	}
	
	public static String getTimeYYYYMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getTime() {
		String stringPattern = "MM-dd EEE HH.mm.ss";
		SimpleDateFormat sdf = new SimpleDateFormat(stringPattern);
		return sdf.format(new Date());
	}
	
	public static String getDateTimeThread() {
		return getTimeYYYYMMddHHmmss()+" thread " + Thread.currentThread().getId() + " ";
	}
	
	public static long getDateTimeInMillis() {
		return System.currentTimeMillis();
	}
	
	public static void log(String message) {
		Reporter.log(getDateTimeThread() + message, true);
	}
	
	public static void sleepAndSwallowExceptions(long msecToSleep) {
		String caller = getCallerClassMethod();
		log("sleepAndSwallowExceptions: called by " + caller);
		try {
			log("Sleeping for " + msecToSleep + " msec");
			Thread.sleep(msecToSleep);
		} catch (InterruptedException e) {
			
		}
	}
	
	public static String getCallerClassMethod() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[3];
		return stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
	}
}
