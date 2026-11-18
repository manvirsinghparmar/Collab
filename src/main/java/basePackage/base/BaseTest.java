package basePackage.base;

import browser.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import config.ConfigReader;
import env.Environment;
import listeners.CustomExecutionListener;
import listeners.EventReportListener;
import utilities.DriverManager;

@Listeners({CustomExecutionListener.class, EventReportListener.class})
public class BaseTest {
    // All extending tests inherit centralized TestNG logging and reporting listeners.

    protected final ConfigReader config = ConfigReader.getInstance();
    private final DriverManager driverManager = new DriverManager();

    // Reports the configured environment, independent of the current browser URL.
    protected Environment getEnvironment() {
        return config.environment();
    }

    protected final WebDriver getDriver() {
        return driverManager.requireDriver();
    }

    public void initialization() {
        WebDriver driver = BrowserUtil.createDriver();
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.get(config.loginUrl());
    }

    public void teardown() {
        WebDriver driver = DriverManager.getDriver();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DriverManager.clear();
        }
    }

}
