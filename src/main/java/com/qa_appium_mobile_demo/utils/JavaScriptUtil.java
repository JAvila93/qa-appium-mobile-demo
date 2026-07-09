package com.qa_appium_mobile_demo.utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa_appium_mobile_demo.base.SeleniumWrapper;

public class JavaScriptUtil {

    static final long MAX_WAIT = 10000; // millis

    public static void scrollIntoView(SeleniumWrapper driver, WebElement element) {
        scrollIntoView(driver, element, false);
    }

    public static void scrollIntoView(SeleniumWrapper driver, WebElement element, boolean alignToTop) {
        ((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].scrollIntoView(" + alignToTop + ");", element);
        waitForAjaxComplete(driver);
    }

    public static void waitForAjaxComplete(SeleniumWrapper driver) {
        ExpectedCondition<Boolean> jQueryLoaded = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                JavascriptExecutor js = (JavascriptExecutor) d;
                return js.executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(MAX_WAIT));
        wait.until(jQueryLoaded);
    }

    public static long waitForPageReady(SeleniumWrapper driver) {
        final long WAIT = 100;
        long totalWait = 0;

        try {
            while (totalWait < WAIT) {
                Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState").equals("complete");
                if (ajaxIsComplete) {
                    break;
                } else {
                    totalWait += WAIT;
                    TestUtilities.sleepAndSwallowExceptions(WAIT);
                }
            }
        } catch (WebDriverException e) {
            TestUtilities.log("waitForPageReady caught exception " + e);
        }
        TestUtilities.log("waitForPageReady: waited for a total of " + totalWait + " msec");
        return totalWait;
    }
}
