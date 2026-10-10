package org.example;

import browser.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import utilities.ConfigReader;

public class BaseTest {

    protected static WebDriver wd;
    public static WebDriverWait wait;
    SoftAssert softAssert = new SoftAssert();

    protected final ConfigReader config = ConfigReader.getInstance();

    public void initialization() {

        wd = BrowserUtil.createDriver();

        wd.manage().window().maximize();
        wd.get(config.loginUrl());
        wait = new WebDriverWait(wd, java.time.Duration.ofSeconds(20));
    }

    public void teardown() {
        wd.quit();
    }

}
