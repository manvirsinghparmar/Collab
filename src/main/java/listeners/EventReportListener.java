package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utilities.ArtifactManager;
import utilities.FrameworkLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class EventReportListener extends TestListenerAdapter implements IExecutionListener {

    private ExtentReports extent;
    private Path reportFile;

    @Override
    public void onExecutionStart() {
        reportFile = ArtifactManager.createReportDirectory().resolve("index.html");
        ExtentSparkReporter spark = new ExtentSparkReporter(reportFile.toString());
        spark.config().setDocumentTitle("Collab Test Report");
        spark.config().setReportName("Collab Automation Results");

        extent = new ExtentReports();
        extent.setReportUsesManualConfiguration(true);
        extent.attachReporter(spark);
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onExecutionFinish() {
        // Read final results after every listener and configuration method has finished.
        // In particular, failureScreenshot must not depend on listener callback order.
        List<ITestResult> tests = new ArrayList<>();
        tests.addAll(getPassedTests());
        tests.addAll(getFailedTests());
        tests.addAll(getSkippedTests());
        tests.addAll(getFailedButWithinSuccessPercentageTests());
        writeResults(tests, false);

        List<ITestResult> configurations = new ArrayList<>(getConfigurationFailures());
        configurations.addAll(getConfigurationSkips());
        writeResults(configurations, true);

        extent.flush();
        FrameworkLogger.stepInfo("HTML test report saved -> " + reportFile);
    }

    private void writeResults(List<ITestResult> results, boolean configuration) {
        results.sort(Comparator.comparingLong(ITestResult::getStartMillis));
        for (ITestResult result : results) {
            String className = result.getTestClass().getName();
            String name = className + "." + result.getMethod().getMethodName();
            ExtentTest test = extent.createTest(
                    configuration ? "[Configuration] " + name : name,
                    result.getMethod().getDescription());
            test.assignCategory(configuration ? "Configuration" : "Test", className);

            ITestContext context = result.getTestContext();
            if (context != null) {
                test.assignCategory("Suite: " + context.getSuite().getName(),
                        "Context: " + context.getName());
            }

            Status status = reportStatus(result);
            if (result.getThrowable() != null) {
                test.log(status, result.getThrowable());
            } else {
                test.log(status, status == Status.SKIP
                        ? "Skipped; TestNG supplied no reason." : "Result: " + status);
            }

            if (!configuration && (result.getStatus() == ITestResult.FAILURE
                    || result.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE)) {
                attachFailureScreenshot(test, result);
            }

            // Entries are built after execution, so use TestNG's original timings.
            long endMillis = Math.max(result.getStartMillis(), result.getEndMillis());
            Date endTime = new Date(endMillis);
            test.getModel().setStartTime(new Date(result.getStartMillis()));
            test.getModel().setEndTime(endTime);
            test.getModel().getLogs().forEach(log -> log.setTimestamp(endTime));
        }
    }

    private Status reportStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return Status.PASS;
            case ITestResult.SKIP:
                return Status.SKIP;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
                return Status.WARNING;
            default:
                return Status.FAIL;
        }
    }

    private void attachFailureScreenshot(ExtentTest test, ITestResult result) {
        Object screenshot = result.getAttribute("failureScreenshot");
        if (screenshot == null) {
            test.info("No failure screenshot was captured.");
            return;
        }

        try {
            Path screenshotFile = Paths.get(screenshot.toString());
            String image = Base64.getEncoder().encodeToString(Files.readAllBytes(screenshotFile));
            test.addScreenCaptureFromBase64String(image, "Failure screenshot");
        } catch (IOException | RuntimeException e) {
            test.info("Failure screenshot could not be attached; see the framework log.");
            FrameworkLogger.testFail("Unable to attach failure screenshot: " + screenshot, e);
        }
    }
}
