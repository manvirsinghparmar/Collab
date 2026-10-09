package org.example;

import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        DashboardPage dashboardPage =
                loginPage.clickOnlogin("Admin", "admin123");
        softAssert.assertEquals(wd.getCurrentUrl(), dashboardPage.DashboardUrl);
        softAssert.assertAll();
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        LoginPage loginPage = new LoginPage();

        loginPage.clickOnlogin("Admin", "admin1234");
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}