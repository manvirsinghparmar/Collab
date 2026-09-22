package org.example.Utils;

import org.example.Base.TestBase;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener extends TestBase implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(".................Test started................: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("*************************Test succeeded******************: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("!!!!!!!!!!!!!!!!!!Test failed!!!!!!!!!!!!!!!!!!: " + result.getName());
        failedTestScreenShot(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("^^^^^^^^^^^^^^^^^^Test skipped^^^^^^^^^^^^^^^^^^: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }


    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }


    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }


}
