package com.qa_appium_mobile_demo.utils;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.qa_appium_mobile_demo.base.SeleniumWrapper;

public class WindowHelper {

    public static void switchAndCloseOldTab(SeleniumWrapper driver) {
        ArrayList<String> oldTab = new ArrayList<String>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(oldTab.get(1));
    }

    public static String swithToNewWindow(SeleniumWrapper driver) {
        String originalWindowHandle = driver.getWindowHandle();
        Set<String> availableWindows = driver.getWindowHandles();

        LoggerUtil.logToFile("switchToNewWindow() availableWindows=" + availableWindows);
        if (!availableWindows.isEmpty()) {
            for (String windowId : availableWindows) {
                if (!windowId.equals(originalWindowHandle)) {
                    driver.switchTo().window(windowId);
                    LoggerUtil.logToFile("switchedToNewWindow()=" + windowId);
                }
            }
        }
        return originalWindowHandle;
    }

    public static void closeCurrentSwitchToWindowByHandle(SeleniumWrapper driver, String windowHandle) {
        driver.close();
        LoggerUtil.logToFile("This is the new window handle " + windowHandle);
        driver.switchTo().window(windowHandle);
    }

    public static void switchToWindowByHandle(SeleniumWrapper driver, String windowHandle) {
        Set<String> availableWindows = driver.getWindowHandles();

        LoggerUtil.logToFile("switchToNewWindow() availableWindows=" + availableWindows);
        driver.switchTo().window(windowHandle);
        LoggerUtil.logToFile("switchedToWindow()=" + windowHandle);
    }

    public static void closeWindowByHandle(SeleniumWrapper driver, String windowHandle) {
        String originalWindowHandle = driver.getWindowHandle();
        driver.switchTo().window(windowHandle);
        closeCurrentSwitchToWindowByHandle(driver, originalWindowHandle);
    }

    public static void switchToIframe(SeleniumWrapper driver, By iFrameSelector) {
        driver.switchTo().frame(driver.waitForElementPresent(iFrameSelector));
    }

    public static void goToMostParentFrame(SeleniumWrapper driver) {
        driver.switchTo().defaultContent();
    }

    public static String ceateAndSwitchToNewWindow(SeleniumWrapper driver) {
        String originalWindowHandle = driver.getWindowHandle();
        ((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");

        Set<String> availableWindows = driver.getWindowHandles();
        for (String windowId : availableWindows) {
            if (!windowId.equals(originalWindowHandle)) {
                driver.switchTo().window(windowId);
                LoggerUtil.logToFile("switchedToWindow()=" + windowId);
            }
        }
        return originalWindowHandle;
    }
}
