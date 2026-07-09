package com.qa_appium_mobile_demo.base;

import java.io.IOException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa_appium_mobile_demo.utils.LoggerUtil;
import com.qa_appium_mobile_demo.utils.TestUtilities;

public abstract class BaseTest {

	protected String m_Environment;
	protected String m_AppId;
	protected String m_PortalName;
	protected static long m_SuiteId;
	protected ExtentWrapper extent;
	private JSONObject driverConf;
	private String env;
	protected String m_driverType;
	
	public LoggerUtil logger;

	public BaseTest() {
		PropertyConfigurator.getInstance();
		driverConf = PropertyConfigurator.getDriverConfigurator();
		env = driverConf.get("environment").toString();
	}
	
	@BeforeClass(alwaysRun=true)
	@Parameters({"appId"})
	public void setupTest(@Optional String appId) {
		m_Environment = env;
		
		m_AppId = appId;
		m_SuiteId = TestUtilities.getDateTimeInMillis();
		TestUtilities.log("SuiteId=" + m_SuiteId);
		TestUtilities.log("AppId=" + m_AppId);
		TestUtilities.log("Environment=" + m_Environment);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void logTestMethod(Method method) {
		logger = LoggerUtil.getInstance();
		String reportFileName = getClassName(method);
		LoggerUtil.createLogsFile(TestUtilities.getTimeMmDdEeHhMmSs(), getClassAndMethodName(method));
		LoggerUtil.logToFile("STARTING METHOD="+ getClassAndMethodName(method));
		extent = ExtentWrapper.getInstance(m_Environment, reportFileName);
		ExtentTestWrapper.startTest(getClassAndMethodName(method));
	}
	
	@AfterMethod(alwaysRun=true)
	public void logResult(ITestResult testResult) throws IOException {
		String testName = ExtentTestWrapper.getTest().getTest().getName();
		// log testName
		int testId = testResult.getMethod().getPriority()+1;
		String newTestName = m_AppId + testId + " - " + testName;
		ExtentTestWrapper.getTest().getTest().setName(newTestName);
		
		Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
		String classAndMethod = getClassAndMethodName(method);
		int statusCode = testResult.getStatus();
		
		String statusString = "UNKOWN";
		if (ITestResult.FAILURE == statusCode) {
			statusString = "FAILED";
		} else if (ITestResult.SKIP == statusCode) {
			statusString = "SKIPED";
		} else if (ITestResult.SUCCESS == statusCode) {
			statusString = "PASSED";
		} else if (ITestResult.CREATED == statusCode) {
			statusString = "SKIPED";
		}
		
		LoggerUtil.logToFile("FINISHED METHOD " + statusString + " " + classAndMethod);
	}
	
	public String getClassName(Method method) {
		return method.getDeclaringClass().getSimpleName();
	}
	
	public String getClassAndMethodName(Method method) {
		return method.getDeclaringClass().getSimpleName() + "." + method.getName();
	}
	
	public String getEnvironment() {
		return m_Environment;
	}
	
	public String getAppId() {
		return m_AppId;
	}
	
	public static long getSuiteId() {
		return m_SuiteId;
	}

	public synchronized void setPortalName(String portal) {
		m_PortalName = portal;
	}

	public synchronized void setDriverType(String driverType) {
		m_driverType = driverType;
	}
}
