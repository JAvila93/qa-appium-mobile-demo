package com.qa_appium_mobile_demo.base;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.relevantcodes.extentreports.LogStatus;
import com.qa_appium_mobile_demo.utils.JavaScriptUtil;
import com.qa_appium_mobile_demo.utils.LoggerUtil;
import com.qa_appium_mobile_demo.utils.TestUtilities;
import com.qa_appium_mobile_demo.utils.WebDriverUtil;

public class SeleniumWrapper {

	protected String driverType;
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	
	protected ExtentWrapper extent;
	
	LoggerUtil logger = LoggerUtil.getInstance();
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	public void quit() {
		driver.quit();
	}
	
	public Options manage() {
		return driver.manage();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	public void close() {
		driver.close();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public Navigation navigate() {
		return driver.navigate();
	}

	public void waitForNumberOfWindowsToDecrease(int currentSize) {
		wait.until(ExpectedConditions.numberOfWindowsToBe(currentSize - 1));
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public String getPageSource() {
		return driver.getPageSource();
	}
	
	private String getTitle() {
		return driver.getTitle();
	}
	
	private WebElement findElement(By locator) {
		LoggerUtil.logToFile("Searching element: " + locator);
		return driver.findElement(locator);
	}
	
	@SuppressWarnings("unused")
	private List<WebElement> findElements(By locator) {
		LoggerUtil.logToFile("Searching elements: " + locator);
		return driver.findElements(locator);
	}

	public boolean isElementClickable(By locator) {
		return isElementClickable(findElement(locator), WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public boolean isElementClickable(By locator, long waitTime) {
		boolean result = false;
		LoggerUtil.logToFile("Checking is element clickable: " + locator);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			LoggerUtil.logToFile("Element clickable: " + locator);
			result = true;
		} catch (NoSuchElementException | TimeoutException e) {
			LoggerUtil.logToFile("Element not clickable: " + locator);
			return false;
		}
		return result;
	}

	public boolean isElementClickable(WebElement element) {
		return isElementClickable(element, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public boolean isElementClickable(WebElement element, long waitTime) {
		boolean result = false;
		LoggerUtil.logToFile("Checking is element clickable");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LoggerUtil.logToFile("Element clickable");
			result = true;
		} catch (NoSuchElementException | TimeoutException e) {
			LoggerUtil.logToFile("Element not clickable");
			return false;
		}
		return result;
	}

	public boolean isElementPresent(By locator) {
		return isElementPresent(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public boolean isElementPresent(By locator, long waitTime) {
		boolean result = false;
		LoggerUtil.logToFile("Checking is element present: " + locator);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			LoggerUtil.logToFile("Element is present: " + locator);
			result = true;
		} catch (NoSuchElementException | TimeoutException e) {
			LoggerUtil.logToFile("Element not present: " + locator);
		}
		return result;
	}

	public boolean isElementDisplayed(By locator) {
		return isElementDisplayed(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public boolean isElementDisplayed(By locator, long waitTime) {
		LoggerUtil.logToFile("Checking if element is displayed: " + locator);
		try {
			WebElement element = findElement(locator);
			element.isDisplayed();
			LoggerUtil.logToFile("Element is displayed");
			return true;
		} catch (NoSuchElementException e) {
			LoggerUtil.logToFile("Element not displayed: " + locator);
			return false;
		}
	}

	public void waitForElementNotPresent(By locator) {
		waitForElementNotPresent(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public void waitForElementNotPresent(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for element not present: " + locator);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresent(By locator) {
		return waitForElementPresent(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public WebElement waitForElementPresent(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for element present: " + locator);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForElementVisible(By locator) {
		waitForElementVisible(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public void waitForElementVisible(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for element visible: " + locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public boolean waitForElementNotVisible(By locator) {
		LoggerUtil.logToFile("Waiting for element not visible: " + locator);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public boolean waitForElementNotVisible(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for element not visible: " + locator);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public WebElement waitForElementClickable(By locator) {
		return waitForElementClickable(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public WebElement waitForElementClickable(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for element clickable: " + locator);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); 
		LoggerUtil.logToFile("Element clickable: " + locator);
		return element;
	}

	public WebElement waitForElementClickable(WebElement element) {
		return waitForElementClickable(element, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public WebElement waitForElementClickable(WebElement element, long waitTime) {
		LoggerUtil.logToFile("Waiting for element clickable");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		LoggerUtil.logToFile("Element clickable");
		return element;
	}

	public List<WebElement> waitForAllElementsToBePresent(By locator) {
		return waitForAllElementsToBePresent(locator, WebDriverTest.WEBDRIVER_TIMEOUT);
	}

	public List<WebElement> waitForAllElementsToBePresent(By locator, long waitTime) {
		LoggerUtil.logToFile("Waiting for all elements to be present: " + locator);
		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		LoggerUtil.logToFile("Elements present: " + locator);
		return elements;
	}

	public List<WebElement> waitForAllSubElementsToBePresent(By parent, By child) {
		LoggerUtil.logToFile("Waiting for all sub elements to be present: " + child);
		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parent, child));
		LoggerUtil.logToFile("Sub elements present: " + child);
		return elements;
	}

	public String getText(WebElement element) {
		LoggerUtil.logToFile("Get element text");
		return element.getText();
	}

	public String getText(By locator) {
		LoggerUtil.logToFile("Get element text: " + locator);
		return findElement(locator).getText();
	}

	public void type(String input, By locator, boolean hidden) {
		type(locator, input, hidden);
	}

	public void type(String input, By locator, boolean hidden, String description) {
		type(locator, input, hidden, description);
	}

	public void type(By locator, String input, boolean hidden) {
		type(locator, input, true, false, hidden);
	}

	public void type(By locator, String input, boolean hidden, String description) {
		type(locator, input, true, false, hidden, description);
	}

	/*
	 * Add Logger to steps
	 */
	public void type(WebElement element, String input, boolean withScroll, boolean sendSlower, boolean hidden) {
		waitForElementClickable(element);
		String elementDescription = null;
		try {
			LoggerUtil.logToFile("Send keys to element");
			if (withScroll) {
				JavaScriptUtil.scrollIntoView(this, element);
			}

			// clear input before sending keys
			element.clear();

			elementDescription = getElementDescription(element, element.toString());

			// slow down method if neccesary
			if (sendSlower) {
				for (char letter : input.toCharArray()) {
					element.sendKeys(String.valueOf(letter));
					TestUtilities.sleepAndSwallowExceptions(50);
				}
			} else {
				element.sendKeys(input);
			}

			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "type_on_element";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				}
			} else {
				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + elementDescription);
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + elementDescription);
				}
			}
		} catch (Exception e) {
			if (!hidden) {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text '" + input + "' to element: " + elementDescription);
			} else {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text to element: " + elementDescription);
			}
		}
	}

	public void type(By locator, String input, boolean withScroll, boolean sendSlower, boolean hidden) {
		WebElement element = waitForElementClickable(locator);
		String elementDescription = null;
		try {
			LoggerUtil.logToFile("Send keys to element");
			if (withScroll) {
				JavaScriptUtil.scrollIntoView(this, element);
			}

			// clear input before sending keys
			element.clear();

			elementDescription = getElementDescription(element, locator.toString());

			// slow down method if neccesary
			if (sendSlower) {
				for (char letter : input.toCharArray()) {
					element.sendKeys(String.valueOf(letter));
					TestUtilities.sleepAndSwallowExceptions(50);
				}
			} else {
				element.sendKeys(input);
			}

			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "type_on_element";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				}
			} else {
				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + elementDescription);
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + elementDescription);
				}
			}
		} catch (Exception e) {
			if (!hidden) {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text '" + input + "' to element: " + elementDescription);
			} else {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text to element: " + elementDescription);
			}
		}
	}

	public void type(By locator, String input, boolean withScroll, boolean sendSlower, boolean hidden, String description) {
		WebElement element = waitForElementClickable(locator);
		try {
			LoggerUtil.logToFile("Send keys to element");
			if (withScroll) {
				JavaScriptUtil.scrollIntoView(this, element);
			}

			// clear input before sending keys
			element.clear();

			// slow down method if neccesary
			if (sendSlower) {
				for (char letter : input.toCharArray()) {
					element.sendKeys(String.valueOf(letter));
					TestUtilities.sleepAndSwallowExceptions(50);
				}
			} else {
				element.sendKeys(input);
			}

			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "type_on_element";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + description+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + description+ExtentTestWrapper.getTest().addScreenCapture(fileName));
				}
			} else {
				if (!hidden) {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text '" + input + "' to element: " + description);
				} else {
					ExtentTestWrapper.log(LogStatus.PASS, "Input text to element: " + description);
				}
			}
		} catch (Exception e) {
			if (!hidden) {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text '" + input + "' to element: " + description);
			} else {
				ExtentTestWrapper.log(LogStatus.FAIL, "Failed send text to element: " + description);
			}
		}
	}

	public void click(WebElement element) {
		click(element, WebDriverTest.WEBDRIVER_TIMEOUT, true);
	}

	public void click(By locator) {
		click(locator, WebDriverTest.WEBDRIVER_TIMEOUT, true);
	}

	public void click(By locator, long waitTime) {
		click(locator, waitTime, true);
	}

	public void click(By locator, String description) {
		click(locator, description, WebDriverTest.WEBDRIVER_TIMEOUT, false);
	}

	public void click(WebElement element, long waitTime, boolean scrollToElement) {
		if (scrollToElement) {
			JavaScriptUtil.scrollIntoView(this, element);
		}
		element.click();
	}

	public void click(By locator, long waitTime, boolean scrollToElement) {
		WebElement element = waitForElementClickable(locator, waitTime);
		String elementDescription = null;
		try {
			LoggerUtil.logToFile("Click element: " + locator);
			if (scrollToElement) {
				JavaScriptUtil.scrollIntoView(this, element);
			}

			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "click_on_element";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				elementDescription = getElementDescription(element, locator.toString());
				element.click();

				ExtentTestWrapper.log(LogStatus.PASS, "Click element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
			} else {
				elementDescription = getElementDescription(element, locator.toString());
				element.click();

				ExtentTestWrapper.log(LogStatus.PASS, "Click element: " + elementDescription);
			}
		} catch (Exception e) {
			ExtentTestWrapper.log(LogStatus.FAIL, "Element not clickable: " + elementDescription);
			//org.testng.Assert.fail(e.getMessage());
		}
	}

	public void click(By locator, String description, long waitTime, boolean scrollToElement) {
		WebElement element = waitForElementClickable(locator, waitTime);
		String elementDescription = description;
		try {
			LoggerUtil.logToFile("Click element: " + locator);
			if (scrollToElement) {
				JavaScriptUtil.scrollIntoView(this, element);
			}

			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "click_on_element";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				element.click();

				ExtentTestWrapper.log(LogStatus.PASS, "Click element: " + elementDescription+ExtentTestWrapper.getTest().addScreenCapture(fileName));
			} else {
				element.click();

				ExtentTestWrapper.log(LogStatus.PASS, "Click element: " + elementDescription);
			}
		} catch (Exception e) {
			ExtentTestWrapper.log(LogStatus.FAIL, "Element not clickable: " + elementDescription);
			//org.testng.Assert.fail(e.getMessage());
		}
	}

	public void clickSubElement(By parentLocator, By childLocator) {
		clickSubElement(waitForElementClickable(parentLocator), childLocator);
	}

	public void clickSubElement(By parentLocator, By childLocator, long waitTime) {
		clickSubElement(waitForElementClickable(parentLocator, waitTime), childLocator, waitTime);
	}

	public void clickSubElement(WebElement parentElement, By childLocator) {
		WebElement subElement = waitForElementClickable(parentElement.findElement(childLocator));
		LoggerUtil.logToFile("Click subelement: " + childLocator);
		JavaScriptUtil.scrollIntoView(this, subElement);

		click(subElement);
	}

	public void clickSubElement(WebElement parentElement, By childLocator, long waitTime) {
		WebElement subElement = waitForElementClickable(parentElement.findElement(childLocator), waitTime);
		LoggerUtil.logToFile("Click subelement: " + childLocator);
		JavaScriptUtil.scrollIntoView(this, subElement);

		click(subElement);
	}

	public void get(String url) {
		try {
			LoggerUtil.logToFile("get URL= "+url);
			driver.get(url);
			ExtentTestWrapper.log(LogStatus.PASS, "Navigate to: " + url);
		} catch (Exception e ) {
			LoggerUtil.logToFile("Failed to get URL= " + url);
			ExtentTestWrapper.log(LogStatus.FAIL, "Failed navigate to: " + url);
			LoggerUtil.logToFile("Fail stackTrace= " + e.getMessage());
		}
	}
	
	public String getURL() {
		LoggerUtil.logToFile("Get current URL: " + getCurrentUrl());
		return getCurrentUrl();
	}
	
	public String getCurrentPageTitle() {
		LoggerUtil.logToFile("Get current page title: " + getTitle());
		return getTitle();
	}

	public boolean isElementVisible(By locator) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		} catch (StaleElementReferenceException | NoSuchElementException e) {
			return false;
		}
	}

	public void selectFromDropdown(By locator, String option, String description) {
		WebElement element = waitForElementClickable(locator);

		Select dropdownSelector = new Select(element);

		JavaScriptUtil.scrollIntoView(this, element);

		dropdownSelector.selectByVisibleText(option);

		LoggerUtil.logToFile("Selected '" + option + "' from " + description + " selector");
		try {
			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "select_dropdown";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				ExtentTestWrapper.log(LogStatus.PASS, "Selected '" + option + "' from " + description + " selector"+ExtentTestWrapper.getTest().addScreenCapture(fileName));
			} else {
				ExtentTestWrapper.log(LogStatus.PASS, "Selected '" + option + "' from " + description + " selector");
			}
		} catch (Exception e) {
			ExtentTestWrapper.log(LogStatus.FAIL, "Could not select '" + option + "' from " + description + " selector");
		}
	}

	public void selectFromDropdownByValue(By locator, String value, String description) {
		WebElement element = waitForElementClickable(locator);

		Select dropdownSelector = new Select(element);

		JavaScriptUtil.scrollIntoView(this, element);

		dropdownSelector.selectByValue(value);

		LoggerUtil.logToFile("Selected '" + value + "' from " + description + " selector");
		try {
			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "select_dropdown";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				ExtentTestWrapper.log(LogStatus.PASS, "Selected '" + value + "' from " + description + " selector"+ExtentTestWrapper.getTest().addScreenCapture(fileName));
			} else {
				ExtentTestWrapper.log(LogStatus.PASS, "Selected '" + value + "' from " + description + " selector");
			}
		} catch (Exception e) {
			ExtentTestWrapper.log(LogStatus.FAIL, "Could not select '" + value + "' from " + description + " selector");
		}
	}

	public void selectFromDropdownByIndex(By locator, int index, String description) {
		WebElement element = waitForElementClickable(locator);

		Select dropdownSelector = new Select(element);

		JavaScriptUtil.scrollIntoView(this, element);

		dropdownSelector.selectByIndex(index);

		LoggerUtil.logToFile("Selected option '" + index + "' from " + description + " selector");
		try {
			if (WebDriverTest.getAllScreenshots()) {
				String ssName = "select_dropdown";
				String timeOfFailure = TestUtilities.getTimeMmDdEeHhMmSs();
				String fileName = WebDriverUtil.takeScreenshot(this, timeOfFailure, ssName, "");
				WebDriverUtil.savePageSourceToFile(this, timeOfFailure, ssName, "");

				ExtentTestWrapper.log(LogStatus.PASS, "Selected option '" + index + "' from " + description + " selector"+ExtentTestWrapper.getTest().addScreenCapture(fileName));
			} else {
				ExtentTestWrapper.log(LogStatus.PASS, "Selected option '" + index + "' from " + description + " selector");
			}
		} catch (Exception e) {
			ExtentTestWrapper.log(LogStatus.FAIL, "Could not select option '" + index + "' from " + description + " selector");
		}
	}

	public void waitForNumberOfWindowsToIncrease(int lastSize) {
		wait.until(ExpectedConditions.numberOfWindowsToBe(lastSize+1));
	}

	public String getElementNameOrId(WebElement element) {
		String identifier = "";
		if (element.getAccessibleName() == null || element.getAccessibleName().trim().length() == 0) {
			if (element.getAttribute("id") == null || element.getAttribute("id").trim().length() == 0) {
				identifier = element.getAttribute("id");
			}
		} else {
			identifier = element.getAccessibleName();
		}
		return identifier;
	}

	public String getElementDescription(WebElement element, String locator) {
		String elementDescriptor = "No usefull descriptor found for element";
		try {
			if (element.getTagName().contentEquals("a") && !element.getText().isEmpty()) {
				elementDescriptor = element.getText();
			} else if (element.getTagName().contentEquals("a") && element.getText().isEmpty()) {
				elementDescriptor = element.getAttribute("title");
			} else if (element.getTagName().contentEquals("a") && element.getAttribute("title").isEmpty()) {
				String[] segments = locator.split("'");
				elementDescriptor = segments[1];
			} else if (element.getTagName().contentEquals("img")) {
				if (element.getAttribute("title").isEmpty()) {
					String[] segments = locator.split("'");
					elementDescriptor = segments[1];
				} else {
					elementDescriptor = element.getAttribute("title");
				}
			} else if (element.getTagName().contentEquals("div")) {
				elementDescriptor = element.getText();
			} else if (element.getTagName().contentEquals("button")) {
				elementDescriptor = element.getText();
			} else if (element.getTagName().contentEquals("datepicker")) {
				elementDescriptor = element.getText();
			} else if (element.getTagName().contentEquals("input")) {
				String elementId = element.getAttribute("id");
				if (element.getAttribute("title").isEmpty()) {
					String[] descriptionArray = elementId.split(".");
					String[] cleanedDescription = descriptionArray[1].split("_form");
					elementDescriptor = cleanedDescription[1];
				}
			}

			if (elementDescriptor.contains("No usefull descriptor found for element") || elementDescriptor.isEmpty()) {
				if (!element.getAccessibleName().isEmpty() && element.getAccessibleName().length() <= 30) {
					elementDescriptor = element.getAccessibleName();
				}
				else if (!element.getAttribute("placeholder").isEmpty()) {
					elementDescriptor = element.getAttribute("placeholder");
				}
				else if (!element.getAttribute("name").isEmpty()) {
					elementDescriptor = element.getAttribute("name");
				}
				else if (!element.getAttribute("id").isEmpty()) {
					elementDescriptor = element.getAttribute("id");
				}
				else if (!element.getAttribute("value").isEmpty()) {
					elementDescriptor = element.getAttribute("value");
				}
				else if (!element.getText().isEmpty()) {
					elementDescriptor = element.getText();
				}
				else if (!element.getAttribute("title").isEmpty()) {
					elementDescriptor = element.getAttribute("title");
				}
				else {}
			}
		} catch (Exception e) {
			LoggerUtil.logToFile("unable to get any element descriptor successfully");
			//e.getMessage();
		}
		return elementDescriptor;
	}
}
