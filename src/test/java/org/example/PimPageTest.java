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
        loginPage = new LoginPage();

    }

    @Test(priority = 1)
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeWithValidEmpName(@Optional("Charles") String empName) {

        LoginPage loginPage = new LoginPage();

        DashboardPage dashboardPage =
                loginPage.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboardPage.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();

    }

    @Test(priority = 2)
    @Parameters("empName")
    public void validateUserIsAbleToEditEmpDetails(@Optional("Charles") String empName) {

        LoginPage loginPage = new LoginPage();

        DashboardPage dashboardPage =
                loginPage.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboardPage.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickEditButton();
        softAssert.assertTrue(
                wd.getCurrentUrl().contains("/pim/viewPersonalDetails/empNumber/"),
                "Expected Personal Details URL, but actual URL was: " + wd.getCurrentUrl()
        );
        softAssert.assertAll();

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToDeleteEmpDetails(@Optional("bala") String empName) {

        LoginPage loginPage = new LoginPage();

        DashboardPage dashboardPage =
                loginPage.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboardPage.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickDeleteButton();
        pimPage.clickDeleteRecordButton();

    }

    @Test
    @Parameters("empName")
    public void validateUserIsAbleToSearchForEmployeeDoesNotExistAndReset(@Optional("Ana") String empName) {

        LoginPage loginPage = new LoginPage();

        DashboardPage dashboardPage =
                loginPage.clickOnlogin(config.username(), config.password());

        PimPage pimPage = dashboardPage.clickOnPIMLink();

        pimPage.enterEmployeeName(empName);
        pimPage.clickSubmitButton();
        pimPage.clickResetButton();


    }

    @AfterMethod
    public void closeBrowser() {
        teardown();
    }


}
