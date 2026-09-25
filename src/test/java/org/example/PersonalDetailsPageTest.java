package org.example;

import pages.DashboardPage;
import pages.LoginPage;
import pages.PersonalDetailsPage;
import pages.PimPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PersonalDetailsPageTest extends BaseTest {
    private PersonalDetailsPage personalDetailsPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PimPage pimPage;



    @BeforeMethod
    public void launch() {
        initialization();
        loginPage=new LoginPage(wd);
    }

    @AfterMethod
    public void quit() {
        teardown();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("Charles") String empName) {
        LoginPage login = new LoginPage(wd);
        DashboardPage dashboard = login.clickOnlogin("Admin", "admin123");
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        String actualUrl = wd.getCurrentUrl();
        softAssert.assertTrue( actualUrl.contains("/pim/viewPersonalDetails/empNumber/"),
                "User did not land on Personal Details page. Actual URL: " + actualUrl);
        softAssert.assertAll();

    }

}
