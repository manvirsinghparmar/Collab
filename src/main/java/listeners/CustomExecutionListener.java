package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ArtifactManager;
import utilities.DriverManager;
import utilities.FrameworkLogger;
import utilities.ScreenshotUtil;

import java.nio.file.Path;

public class CustomExecutionListener implements ITestListener {

    private static final boolean SCREENSHOT_ON_FAILURE =
            Boolean.parseBoolean(
                    System.getProperty(
                            "framework.screenshot.onFailure", "true"));

    @Override
    public void onTestStart(ITestResult result) {
        ArtifactManager.logsDirectory();

        FrameworkLogger.setLoggingContextFromTestResult(result);

        FrameworkLogger.testStart(
                "========== Test Case Started: "
                        + testName(result) + " ==========");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            FrameworkLogger.testPass(
                    "========== Test Case Passed: "
                            + testName(result) + " ==========");
        } finally {
            FrameworkLogger.clearTestContext();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (SCREENSHOT_ON_FAILURE) {
                captureFailureScreenshot(result);
            }

            FrameworkLogger.testFail(
                    "========== Test Case Failed: "
                            + testName(result) + " ==========",
                    result.getThrowable());
        } finally {
            FrameworkLogger.clearTestContext();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            FrameworkLogger.testSkip(
                    "========== Test Case Skipped: "
                            + testName(result) + " ==========");
        } finally {
            FrameworkLogger.clearTestContext();
        }
    }

    private void captureFailureScreenshot(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {
            FrameworkLogger.warn(
                    "Skipping screenshot: driver is not available.");
            return;
        }

        try {
            Path screenshot =
                    ScreenshotUtil.captureFailureScreenshot(
                            driver, result);

            result.setAttribute(
                    "failureScreenshot", screenshot.toString());

            FrameworkLogger.screenshotSaved(
                    "Failure screenshot saved for "
                            + testName(result) + " -> " + screenshot);
        } catch (RuntimeException e) {
            FrameworkLogger.testFail(
                    "Unable to capture failure screenshot for "
                            + testName(result), e);
        }
    }

    private String testName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
    }
}