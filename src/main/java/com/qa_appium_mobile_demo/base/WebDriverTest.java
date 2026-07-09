package com.qa_appium_mobile_demo.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.qa_appium_mobile_demo.utils.LoggerUtil;
import com.qa_appium_mobile_demo.utils.TestUtilities;
import com.qa_appium_mobile_demo.utils.WebDriverUtil;

public class WebDriverTest extends BaseTest {

	public static long WEBDRIVER_TIMEOUT = 30;
	protected BaseDriver driver;
	private APIConnector piki;
	private boolean reportUpload;
	private static boolean allScreenshots;
	private boolean cleanOutput;

	private JSONObject driverConf;

	public WebDriverTest() {
		PropertyConfigurator.getInstance();
		driverConf = PropertyConfigurator.getDriverConfigurator();
	}
	
	@BeforeClass(alwaysRun=true)
	public void setupTest() {
		System.setProperty("java.net.preferIPv4Stack", "true");

		if (m_driverType == null || m_driverType.isEmpty()) {
			driver = newChromeDriver(driverConf.get("driverType").toString());
		} else {
			driver = newChromeDriver(m_driverType);
		}
		
		reportUpload = (boolean) driverConf.get("uploadReport");
		allScreenshots = (boolean) driverConf.get("allScreenshots");
		cleanOutput = (boolean) driverConf.get("cleanOutput");

		if (reportUpload) {
			piki = new APIConnector();
		}
	}
	
	public synchronized BaseDriver newChromeDriver(String driverType) {
		return new BaseDriver(driverType);
	}
	
	private synchronized ArrayList<BaseDriver> getActiveDrivers() {
		ArrayList<BaseDriver> driverList = new ArrayList<BaseDriver>();
		driverList.add(driver);
		
		return driverList;
	}
	
	@AfterClass(alwaysRun=true)
	public void quitDriver() {
		try {
			for (BaseDriver driver : getActiveDrivers()) {
				driver.getWebDriver().quit();
			}
		} catch (NullPointerException e) {
			
		} catch (UnreachableBrowserException e) {
			
		}
	}
	
	public BaseDriver getWebDriver() {
		return driver;
	}
	
	@AfterMethod(alwaysRun=true)
	public void logResult(ITestResult testResult) throws IOException {
		String error = "";
		ExtentTest test = ExtentTestWrapper.getTest();
		super.logResult(testResult);
		if (ITestResult.FAILURE == testResult.getStatus()) {
			try {
				TestUtilities.log("FAILED URL: " + driver.getCurrentUrl());
				TestUtilities.log("FAILED Page Title: " + driver.getCurrentPageTitle());
				
				Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
				String testClassName = getClassName(method);
				String classAndMethod = getClassAndMethodName(method);
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				
				String fileName = WebDriverUtil.takeScreenshot(driver, timeOfFailure, classAndMethod, testClassName);
				WebDriverUtil.savePageSourceToFile(driver, timeOfFailure, classAndMethod, testClassName);
				
				/**
				 * posible enhancement
				 * replace throwable for a less technical message
				 */
				test.log(LogStatus.FAIL, testResult.getThrowable().getMessage()+test.addScreenCapture(fileName));
				TestUtilities.log("FAILED Screenshot: " + fileName);
				List<String> lines = LoggerUtil.extractLogFile();
				String newLines = "";
				for (String line : lines) {
					newLines += String.join("", "<p>", line, "</p>");
				}
				test.log(LogStatus.INFO, "<details>"+newLines+"</details>");
			} catch (NoSuchSessionException e) {
				Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
				String classAndMethod = getClassAndMethodName(method);
				TestUtilities.log("NoSuchSessionException during: " + classAndMethod);
			}
			error = testResult.getThrowable().getLocalizedMessage();
		} else if (ITestResult.SUCCESS == testResult.getStatus()) {
			try {
				test.log(LogStatus.PASS, "Test Case Passed");
				List<String> lines = LoggerUtil.extractLogFile();
				String newLines = "";
				for (String line : lines) {
					newLines += String.join("", "<p>", line, "</p>");
				}
				test.log(LogStatus.INFO, "<details>"+newLines+"</details>");
			} catch (NoSuchSessionException e) {
				Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
				String classAndMethod = getClassAndMethodName(method);
				TestUtilities.log("NoSuchSessionException during: " + classAndMethod);
			}
		} else if (ITestResult.SKIP == testResult.getStatus()) {
			try {
				Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
				String classAndMethod = getClassAndMethodName(method);
				
				TestUtilities.log("SKIPED METHOD: " + classAndMethod);
				test.log(LogStatus.SKIP, testResult.getThrowable().getMessage());
				List<String> lines = LoggerUtil.extractLogFile();
				String newLines = "";
				for (String line : lines) {
					newLines += String.join("", "<p>", line, "</p>");
				}
				test.log(LogStatus.INFO, "<details>"+newLines+"</details>");
			} catch (NoSuchSessionException e) {
				Method method = testResult.getMethod().getConstructorOrMethod().getMethod();
				String classAndMethod = getClassAndMethodName(method);
				TestUtilities.log("NoSuchSessionException during: " + classAndMethod);
			}
			error = testResult.getThrowable().getLocalizedMessage();
		}
		
		int testId = 1;
		testId += testResult.getMethod().getPriority();
		String testName = testResult.getMethod().getConstructorOrMethod().getMethod().getName();
		String application = testResult.getMethod().getConstructorOrMethod().getMethod().getDeclaringClass().getSimpleName();
		long suiteId = getSuiteId();
		String env = getEnvironment();

		// API json
		JsonObject jsonBody = new JsonObject();
		jsonBody.addProperty("id", getAppId());
		jsonBody.addProperty("type", "TEST");
		jsonBody.addProperty("testSuiteId", suiteId);
		jsonBody.addProperty("testCase", testName);
		jsonBody.addProperty("testCaseId", getAppId()+testId);
		jsonBody.addProperty("application", application);
		jsonBody.addProperty("status", extent.getStatus(testResult.getStatus()));
		jsonBody.addProperty("error", error);
		jsonBody.addProperty("executionTimeMilliSeconds", (testResult.getEndMillis() - testResult.getStartMillis()));
		jsonBody.addProperty("environment", env);
		
		ArrayList<JsonObject> steps = new ArrayList<JsonObject>();
		List<Log> logList = test.getTest().getLogList();
		
		for (Log log : logList) {
			JsonObject stepsBody = new JsonObject();
			stepsBody.addProperty("status", log.getLogStatus().name());
			stepsBody.addProperty("details", log.getDetails());
			stepsBody.addProperty("timestamp", log.getTimestamp().toString());
			steps.add(stepsBody);
		}
		jsonBody.addProperty("steps", steps.toString());

		// suite status json
		JsonObject suiteStatus = new JsonObject();
		suiteStatus.addProperty("appId", getAppId());
		suiteStatus.addProperty("testSuiteId", getSuiteId());
		suiteStatus.addProperty("status", "RUNNING");

		if (reportUpload) {
			try {
				piki.sendTestResult(jsonBody);
			} catch (Exception e) {
			}

			try {
				piki.sendSuiteStatus(suiteStatus);
			} catch (Exception e) {
			}
		}
		ExtentTestWrapper.endTest();
	}

	@AfterSuite
	public void sendReport() throws IOException {
		extent.endReport();
		if (reportUpload) {
			JsonObject suiteStatus = new JsonObject();
			suiteStatus.addProperty("appId", getAppId());
			suiteStatus.addProperty("testSuiteId", getSuiteId());
			suiteStatus.addProperty("status", "FINISH");

			// encode html report
			String htmlFilePath = extent.getFilePath();
			File htmlFile = new File(htmlFilePath);
			String base64EncodedHtml = WebDriverUtil.encodeFileToBase64(htmlFile);

			// encode and zip screenshots
			WebDriverUtil.createScreenshotsZipFolder();

			String sshZipPath = WebDriverUtil.getScreenshotsZipFolderPath();
			File sshZipFile = new File(sshZipPath);
			String base64EncodedSshZip = WebDriverUtil.encodeFileToBase64(sshZipFile);

			// encode and zip logs
			WebDriverUtil.createLogsZipFolder();

			String lZipPath = WebDriverUtil.getLogsZipFolderPath();
			File lZipFile = new File(lZipPath);
			String base64EncodedLZip = WebDriverUtil.encodeFileToBase64(lZipFile);

			// add to json body
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("html", base64EncodedHtml);
			jsonBody.addProperty("screenshots", base64EncodedSshZip);
			jsonBody.addProperty("logs", base64EncodedLZip);
			jsonBody.addProperty("portal", m_PortalName);

			// post report
			try {
				piki.sendTestReport(jsonBody);
			} catch (Exception e) {
			}

			try {
				piki.sendSuiteStatus(suiteStatus);
			} catch (Exception e) {
			}
		}

		if (cleanOutput) {
			WebDriverUtil.cleanDirectory();
		}
	}
	
	public ExtentWrapper getWrapper() {
		return extent;
	}

	public static boolean getAllScreenshots() {
		return allScreenshots;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
