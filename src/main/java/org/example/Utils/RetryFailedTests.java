package org.example.Utils;

import org.example.Base.TestBase;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests extends TestBase implements IRetryAnalyzer {

    int cnt = 1;
    int maxCount = 2;

    @Override
    public boolean retry(ITestResult result) {
        logger.info("Retrying failed test: " + result.getMethod().getMethodName() + " for " + cnt + " time.");
        if (cnt<maxCount) {
            cnt++;
            return true;
        }
        return false;
    }
}
