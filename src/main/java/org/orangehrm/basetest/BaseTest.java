package org.orangehrm.basetest;

import org.orangehrm.assertions.AssertionsUtil;
import org.orangehrm.browserconfig.BrowserUtil;
import org.openqa.selenium.WebDriver;
import org.orangehrm.config.ConfigReader;


public class BaseTest {

    protected WebDriver wd;

    protected final ConfigReader config = ConfigReader.getInstance();

    public void initialization() {

        wd = BrowserUtil.createDriver();
        wd.manage().window().maximize();
        wd.get(config.loginUrl());
    }

    public void teardown() {
        try {
            AssertionsUtil.assertAll();
        } finally {
            if (wd != null) {
                wd.quit();
                wd = null;

            }


        }

    }
}
