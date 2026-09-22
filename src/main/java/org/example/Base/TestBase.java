package org.example.Base;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Config.BrowserConfiguration;
import org.example.Utils.DriverManager;
import org.example.Utils.WebDriverEvents;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class TestBase {


    public WebDriver driver;
    public String URL = "https://demo.opencart.com/en-gb?route=common/home";

    public Logger logger;
    public WebDriverWait wait;
    public WebDriverEvents events;
    public SoftAssert softAssert;

    public TestBase() {

        logger = LogManager.getLogger(getClass());
        refreshFromManager();
    }

    private void refreshFromManager() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
        this.softAssert = new SoftAssert();
    }


    private static final boolean RUN_ON_GRID = false;

    public void initialise() throws MalformedURLException {


        if (DriverManager.getDriver() != null) {
            logger.warn("initialise() called while a WebDriver instance already existed for this thread; " +
                    "closing the existing session before starting a new one.");
            tearDown();
        }

        refreshFromManager();

        WebDriver localDriver;
        if (RUN_ON_GRID) {
            try {
                String browser = "chrome";
                localDriver = new RemoteWebDriver(new URL("http://10.0.0.136:4444"), new ManageOptions().getOptions(browser));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            localDriver = BrowserConfiguration.createDriver("chrome", true);
        }
        //        Wrap the instance with EventFiringDecorator (Selenium 4)
        events = new WebDriverEvents();
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(events);
        localDriver = decorator.decorate(localDriver);

        localDriver.manage().window().maximize();
        localDriver.manage().deleteAllCookies();
        localDriver.get(URL);
        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait localWait = new WebDriverWait(localDriver, Duration.ofSeconds(10));

        DriverManager.setDriver(localDriver);
        DriverManager.setWait(localWait);
        refreshFromManager();
        softAssert = new SoftAssert();
    }

    public void tearDown() {
        WebDriver activeDriver = DriverManager.getDriver();
        if (activeDriver != null) {
            activeDriver.quit();
        }
        DriverManager.unload();

        this.driver = null;
        this.wait = null;
    }


    public void failedTestScreenShot(String testMethodName) {

        WebDriver currentDriver = DriverManager.getDriver();
        if (currentDriver == null) {
            return;
        }

        File screenshotDir = new File("./FailedTestCasesScreenshot");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
            logger.info("Created screenshot directory: " + screenshotDir.getAbsolutePath());
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        File screenShotFile = ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile,
                    new File(screenshotDir, testMethodName + "_" + timeStamp + ".png"));
            logger.info("Screenshot saved: " + testMethodName + "_" + timeStamp + ".png");
        } catch (IOException e) {
            logger.error("Failed to save screenshot for test: " + testMethodName, e);
        }

    }


}
