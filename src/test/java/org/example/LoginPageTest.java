package org.example;

import Pages.DashboardPage;
import Pages.LoginPage;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void validateUserIsAbleToLoginWithValidCredential() {

        LoginPage login = new LoginPage(wd);

        DashboardPage dashboard =
                login.clickOnlogin("Admin", "admin123");
    }

    @Test
    public void validateUserIsNotAbleToLoginWithInvalidCredential() {

        LoginPage login = new LoginPage(wd);

        login.clickOnlogin("Admin", "admin1234");
    }
}