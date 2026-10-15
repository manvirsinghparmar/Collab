package org.orangehrm.tests;

import org.orangehrm.bastest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.orangehrm.pages.PimPage;
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
        softAssert.assertTrue(wd.getCurrentUrl().contains("/pim/viewEmployeeList"), "Expected PIM URL, but actual URL was: " + wd.getCurrentUrl());

    }


    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
