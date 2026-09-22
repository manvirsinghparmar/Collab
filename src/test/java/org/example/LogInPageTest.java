package org.example;

import org.example.Base.TestBase;
import org.example.Pages.HomePage;
import org.example.Pages.LogInPage;
import org.example.Pages.MyAccountPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class LogInPageTest extends TestBase {

    HomePage homePage;
    LogInPage logInPage;
    MyAccountPage myAccountPage;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        initialise();
        homePage = new HomePage();
    }

    @Test
    public void logInWithValidCredentials() {
        homePage.clickMyAccountButton();
        logInPage = homePage.clickLoginButton();
        myAccountPage = logInPage.login("gaurav0011@gmail.com", "123456");
        softAssert.assertEquals(myAccountPage.getTitle(), "My Account", "Title does not match - Login failed");
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }




}
