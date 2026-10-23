package basePackage.base;

import browser.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import config.ConfigReader;
import listeners.CustomExecutionListener;
import utilities.DriverManager;

@Listeners(CustomExecutionListener.class)
public class BaseTest {
    // All extending tests inherit centralized TestNG listener behavior through this base class.

    protected static WebDriver wd;
    public static WebDriverWait wait;

    protected final ConfigReader config = ConfigReader.getInstance();

    public void initialization() {

        wd = BrowserUtil.createDriver();
        DriverManager.setDriver(wd);

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
