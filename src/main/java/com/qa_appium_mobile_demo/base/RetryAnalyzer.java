package com.qa_appium_mobile_demo.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int counter = 0;
    private int retryLimit = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (counter < retryLimit) {
                counter++;
                return true;
            }
        }
        return false;
    }
}
