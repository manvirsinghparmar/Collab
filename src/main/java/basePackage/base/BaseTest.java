package basePackage.base;

import browser.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import config.ConfigReader;
import env.Environment;
import listeners.CustomExecutionListener;
import listeners.EventReportListener;
import utilities.DriverManager;

import java.time.Duration;

@Listeners({CustomExecutionListener.class, EventReportListener.class})
public class BaseTest {
    // All extending tests inherit centralized TestNG logging and reporting listeners.

    protected static WebDriver wd;
    public static WebDriverWait wait;

    protected final ConfigReader config = ConfigReader.getInstance();

    // Reports the configured environment, independent of the current browser URL.
    protected Environment getEnvironment() {
        return config.environment();
    }

    public void initialization() {

        wd = BrowserUtil.createDriver();
        DriverManager.setDriver(wd);
        wait = new WebDriverWait(wd, Duration.ofSeconds(15));

        wd.manage().window().maximize();
        wd.get(config.loginUrl());
    }

    public void teardown() {
        WebDriver driver = DriverManager.getDriver();
        try {
            if (driver != null) {
                driver.quit();
            } else if (wd != null) {
                wd.quit();
            }
        } finally {
            DriverManager.clear();
            wd = null;
            wait = null;
        }
    }

}
