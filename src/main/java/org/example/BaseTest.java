package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver wd;

    private final String defaultBrowser = "CHROME";

    private final String url =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeMethod
    public void initialization() {

        switch (defaultBrowser) {

            case "CHROME":

                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("autofill.profile_enabled", false);

                options.setExperimentalOption("prefs", prefs);

                wd = new ChromeDriver(options);

                break;

            case "EDGE":

                wd = WebDriverManager.edgedriver().create();

                break;

            case "FIREFOX":

                wd = WebDriverManager.firefoxdriver().create();

                break;

            default:

                throw new IllegalArgumentException("Invalid Browser");
        }

        wd.manage().window().maximize();
        wd.get(url);
    }

    @AfterMethod
    public void teardown() {

        if (wd != null) {
            wd.close();
            wd = null;
        }
    }
}