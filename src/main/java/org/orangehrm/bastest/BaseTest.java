package org.orangehrm.bastest;

import org.orangehrm.browserconfig.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.orangehrm.config.ConfigReader;
import org.testng.asserts.SoftAssert;


public class BaseTest {

    protected WebDriver wd;
    protected SoftAssert softAssert;

    protected final ConfigReader config = ConfigReader.getInstance();

    public void initialization() {

        wd = BrowserUtil.createDriver();
        wd.manage().window().maximize();
        wd.get(config.loginUrl());
        softAssert = new SoftAssert();
    }

    public void teardown() {
        try {
            if (softAssert != null) {
                softAssert.assertAll();
            }
        } finally {
            if (wd != null) {
                wd.quit();
            }
        }
    }

}
