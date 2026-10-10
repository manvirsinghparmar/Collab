package org.example;

import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashBoardPageTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PimPage pimPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage=new LoginPage();

    }



    @Test
    public void validateUserIsAbleToClickOnPIMLinkOnDashboard() {

        LoginPage login = new LoginPage();

        DashboardPage dashboard =
                login.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboard.clickOnPIMLink();
    }


    @AfterMethod
    public void closeBrowser() {
        teardown();
    }
}
