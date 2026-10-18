<<<<<<<< HEAD:src/test/java/org/orangehrm/tests/DashBoardPageTest.java
package org.orangehrm.tests;

import org.orangehrm.assertions.AssertionsUtil;
import org.orangehrm.basetest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.orangehrm.pages.PimPage;
========
package basePackage.testPages;

import basePackage.base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
>>>>>>>> origin/master:src/test/java/basePackage/testPages/DashBoardPageTest.java
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashBoardPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }


    @Test
    public void validateUserIsAbleToClickOnPIMLinkOnDashboard() {

        DashboardPage dashboard =
                loginPage.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboard.clickOnPIMLink();
        AssertionsUtil.softAssert.assertTrue(wd.getCurrentUrl().contains("/pim/viewEmployeeList"), "Expected PIM URL, but actual URL was: " + wd.getCurrentUrl());

    }


    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}

