package org.example;

import pages.DashboardPage;
import pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private LoginPage login;
    private DashboardPage dashboard;


    @BeforeMethod
    public void launch() {
        initialization();
        login=new LoginPage(wd);

    }

    @Test
    public void validateUserIsAbleToLoginWithValidCredentials() {

        LoginPage login = new LoginPage(wd);

        DashboardPage dashboard =
                login.clickOnlogin("Admin", "admin123");
        softAssert.assertEquals(wd.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        softAssert.assertAll();
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