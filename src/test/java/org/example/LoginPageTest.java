package org.example;

import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static pages.DashboardPage.DASHBOARD_URL;

public class LoginPageTest extends BaseTest {

    private LoginPage login;
    private DashboardPage dashboard;


    @BeforeMethod
    public void launch() {
        initialization();
        login=new LoginPage(wd);

    }

import Pages.DashboardPage;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        LoginPage login = new LoginPage(wd);

        DashboardPage dashboard =
                login.clickOnlogin("Admin", "admin123");
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredentials() {

        LoginPage login = new LoginPage(wd);

        login.clickOnlogin("Admin", "admin1234");
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}