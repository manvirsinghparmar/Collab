package org.orangehrm.tests;

import org.orangehrm.basetest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.orangehrm.assertions.AssertionsUtil;
import org.orangehrm.pages.DashboardPage;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;
    private AssertionsUtil assertionsUtil;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
        assertionsUtil = new AssertionsUtil();

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        dashboardPage = loginPage.clickOnlogin("Admin", "admin123");
        assertionsUtil.assertContains(
                dashboardPage.getCurrentUrl(),
                dashboardPage.DashboardUrl,
                "User is not able to login with valid credentials");
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
