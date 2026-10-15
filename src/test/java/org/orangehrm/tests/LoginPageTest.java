package org.orangehrm.tests;

import org.orangehrm.bastest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        DashboardPage dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        softAssert.assertEquals(wd.getCurrentUrl(), dashboardPage.DashboardUrl);

    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        loginPage.clickOnlogin("Admin", "admin1234");
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
