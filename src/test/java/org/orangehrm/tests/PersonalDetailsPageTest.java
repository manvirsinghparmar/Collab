package org.orangehrm.tests;

import org.orangehrm.bastest.BaseTest;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.LoginPage;
import org.orangehrm.pages.PimPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PersonalDetailsPageTest extends BaseTest {
    private LoginPage loginPage;


    @BeforeMethod
    public void launch() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test
    @Parameters("empName")
    public void validateUserIsAbleToLandOnPersonalDetailsPageAfterClickingEditButton(@Optional("Charles") String empName) {
        LoginPage login = new LoginPage();
        DashboardPage dashboard = login.clickOnlogin(config.username(), config.password());
        PimPage pimPage = dashboard.clickOnPIMLink();
        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        String actualUrl = wd.getCurrentUrl();
        softAssert.assertTrue(actualUrl.contains("/pim/viewPersonalDetails/empNumber/"),
                "User did not land on Personal Details page. Actual URL: " + actualUrl);

    }

    @AfterMethod
    public void quit() {
        teardown();
    }

}
