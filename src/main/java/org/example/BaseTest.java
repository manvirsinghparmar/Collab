package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected static WebDriver wd;
    public static WebDriverWait wait;
    SoftAssert softAssert = new SoftAssert();

    private final String defaultBrowser = "CHROME";
    private final String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

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
        wait = new WebDriverWait(wd, java.time.Duration.ofSeconds(20));
    }

    public void teardown() {
        wd.quit();
    }

    public void sleep(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting after entering employee name", e);
        }
    }
}