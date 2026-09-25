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

public class PimPageTest extends BaseTest {

    private PersonalDetailsPage personalDetailsPage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private PimPage pimPage;

    @BeforeMethod
    public void launch() {
        initialization();
        loginPage=new LoginPage(wd);

    }

    @Test(priority=1)
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeWithValidEmpName(@Optional("aniket") String empName){

        LoginPage login = new LoginPage(wd);

        DashboardPage dashboard =
                login.clickOnlogin("Admin", "admin123");

        PimPage pimPage = dashboard.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();

    }

    @Test(priority = 2)
    @Parameters("empName")
    public void validateUserIsAbleToEditEmpDetails(
            @Optional("aniket") String empName) {

        LoginPage login = new LoginPage(wd);

        DashboardPage dashboard =
                login.clickOnlogin("Admin", "admin123");

        PimPage pimPage = dashboard.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.scrollToEditButton();
        pimPage.clickEditButton();

        String actualUrl = wd.getCurrentUrl();

        softAssert.assertTrue(
                actualUrl.startsWith(
                        "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/"
                ),
                "Expected Personal Details URL, but actual URL was: " + actualUrl
        );

        softAssert.assertAll();
    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }


}
