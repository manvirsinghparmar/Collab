package org.example.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventReportListener extends TestListenerAdapter {



    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String repName = "My Account Portal " + "---------" + timeStamp + ".html";

        String projectPath = System.getProperty("user.dir");
        sparkReporter = new ExtentSparkReporter(projectPath + "/Extent Reports/" + repName);
        sparkReporter.config().setReportName("My Account Portal Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }
    @Override
    public void onTestSuccess(ITestResult tr) {
        test = extent.createTest(tr.getName());
        test.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        test = extent.createTest(tr.getName());
        test.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        test = extent.createTest(tr.getName());
        test.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();

    }
}
